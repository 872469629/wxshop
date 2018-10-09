package com.thinkgem.jeesite.modules.inter.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;
import com.thinkgem.jeesite.modules.commission.service.WsCommissionService;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.config.service.WsExFaretemplateService;
import com.thinkgem.jeesite.modules.config.service.WsMrankService;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;
import com.thinkgem.jeesite.modules.member.entity.WsMemberRewardLog;
import com.thinkgem.jeesite.modules.member.service.WsAddressService;
import com.thinkgem.jeesite.modules.member.service.WsMemberCouponService;
import com.thinkgem.jeesite.modules.member.service.WsMemberRewardLogService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.prod.utils.ProdUtils;
import com.thinkgem.jeesite.modules.ws.entity.WxAccount;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.pay.WXPay;
import com.thinkgem.jeesite.modules.wx.pay.WXPayConfigImpl;
import com.thinkgem.jeesite.modules.wx.pay.WXPayConstants;
import com.thinkgem.jeesite.modules.wx.pay.WXPayConstants.SignType;
import com.thinkgem.jeesite.modules.wx.pay.WXPayUtil;

@Service
@Transactional(readOnly = true)
public class PayService extends BaseService{
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@Autowired
	private WsAddressService wsAddressService;
	
	@Autowired
	private WsOrderItemService wsOrderItemService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsMemberCouponService wsMemberCouponService;
	
	@Autowired
	private WsMemberRewardLogService wsMemberRewardLogService;
	
	@Autowired
	private WsExFaretemplateService wsExFaretemplateService;
	
	@Autowired
	private WsMrankService wsMrankService;
	
	@Autowired
	private WsCommissionService wsCommissionService;

	@Transactional(readOnly = false)
	public Map orderPay(WsMember member,List<WsOrderItem> wsOrderItems,String ip,String notify) throws Exception {
		Map data=new HashMap();
		/**
		 * 查询用户地址信息
		 */
		WsAddress wsAddress=new WsAddress();
		wsAddress.setIsDefault(WsConstant.YES);
		wsAddress.setWsMember(member);
		List<WsAddress> wsAddressList=wsAddressService.findList(wsAddress);
		if(wsAddressList==null || wsAddressList.size()==0){
			throw new ServiceException("用户收货不能为空，请返回上级选择收货地址!");
		}
		wsAddress=wsAddressList.get(0);
		/**
		 * 生成订单信息
		 */
		WsOrder wsOrder=new WsOrder();
		List<WsOrderItem> wsOrderItemList=new ArrayList<WsOrderItem>();
		Date now=new Date();
		String body="";
		BigDecimal totalMoney=new BigDecimal(0);
		//从订单确认页面进入订单支付页面，需要重新生成订单信息
		wsOrder=new WsOrder();
		wsOrder.setMemberId(member);
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_PAY);
		wsOrder.setReturnState(WsConstant.ORDER_CAN_RETURN);
		wsOrder.setOrderSn(ProdUtils.getDateSeq(WsConstant.ORDER_SEQ));
		wsOrder.setAddress(wsAddress);
		wsOrderService.save(wsOrder);
		String orderId=wsOrder.getId();
		wsOrderItemList=new ArrayList<WsOrderItem>();
		for (WsOrderItem item : wsOrderItems) {
			WsOrderItem wsOrderItem=new WsOrderItem();
			WsProdSku wsProdSku=wsProdSkuService.get(item.getSkuId());
			WsProduct wsProduct=wsProductService.get(wsProdSku.getWsProduct().getId());
			wsOrderItem.setWsOrder(wsOrder);
			wsOrderItem.setWsProduct(wsProduct);
			wsOrderItem.setSkuId(item.getSkuId());
			wsOrderItem.setSkuSpec(wsProdSku.getSkuName());
			wsOrderItem.setThumb(wsProduct.getProdImage());
			wsOrderItem.setQuantity(item.getQuantity());
			wsOrderItem.setUnitPrice(wsProdSku.getPrice());
			wsOrderItem.setReallyUnitPrice(wsProdSku.getReallyPrice());
			wsOrderItem.setReallyPrice(wsProdSku.getReallyPrice().multiply(new BigDecimal(item.getQuantity())));
			wsOrderItem.setWsProdSku(wsProdSku);
			wsOrderItemService.save(wsOrderItem);
			wsOrderItemList.add(wsOrderItem);
			if(StringUtils.isEmpty(body)&&StringUtils.isNotEmpty(wsProduct.getTitle())){
				body=wsProduct.getTitle();
			}
			totalMoney=totalMoney.add(wsOrderItem.getReallyUnitPrice().multiply(new BigDecimal(wsOrderItem.getQuantity())));
		}
		/**
		 * 计算优惠券优惠后的价格
		 */
		WsMemberCoupon wsMemberCoupon=wsMemberCouponService.reductionCoupon(member, totalMoney);
		if(wsMemberCoupon!=null && StringUtils.isNotEmpty(wsMemberCoupon.getId())){
			totalMoney=totalMoney.subtract(wsMemberCoupon.getCouponMoney());
			wsOrder.setWsMemberCoupon(wsMemberCoupon);
			wsMemberCoupon.setState(WsConstant.COUPON_USED);
			wsMemberCouponService.save(wsMemberCoupon);
		}
		/**
		 * 计算快递费用
		 */
		wsOrder.setReallyPrice(totalMoney);
		wsOrder.setWsOrderItemList(wsOrderItemList);
		BigDecimal postage=wsExFaretemplateService.countExFareMoney(wsOrder);
		wsOrder.setPostage(postage);
		totalMoney=totalMoney.add(postage);
		/**
		 * 计算会员等级折扣
		 */
		if(StringUtils.isNotEmpty(member.getMemberRankId())){
			WsMrank wsMrank=wsMrankService.get(member.getMemberRankId());
			if(wsMrank!=null){
				BigDecimal scaleMoney=totalMoney.subtract(totalMoney.multiply(wsMrank.getScale()));
				totalMoney=totalMoney.multiply(wsMrank.getScale());
				wsOrder.setMrankId(wsMrank.getId());
				wsOrder.setMrankScale(wsMrank.getScale());
				wsOrder.setMrankMoney(scaleMoney);
			}
		}
		wsOrder.setScore(totalMoney.multiply(new BigDecimal(100)).intValue());
		wsOrder.setReallyPrice(totalMoney);
		/**
		 * 保存订单数据
		 */
		wsOrderService.save(wsOrder);
		
		/**
		 * 获取预付款Id
		 */
		String nonceStr=WXPayUtil.generateNonceStr();
		WxAccount account=WsUtils.getAccount();
		WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		WXPay wxpay = new WXPay(config);
        HashMap<String, String> preData = new HashMap<String, String>();
        preData.put("appid", account.getAccountAppid());
        preData.put("mch_id", account.getMchId());
        preData.put("device_info", "WEB");
        preData.put("nonce_str", nonceStr);
        preData.put("body", body);
        preData.put("out_trade_no", wsOrder.getOrderSn());
        preData.put("fee_type", "CNY");
        preData.put("total_fee", String.valueOf(totalMoney.multiply(new BigDecimal(100)).intValue()));
        preData.put("spbill_create_ip", ip);
        preData.put("notify_url", notify);
        preData.put("trade_type", "JSAPI");
        preData.put("product_id", wsOrder.getId());
        preData.put("openid", member.getOpenId());
        //prepay_id的有效时间是两个小时，判断prepay_id是否已经失效，如果失效，则需要重新获取
        Map<String, String> r = wxpay.unifiedOrder(preData);
		if(StringUtils.isNotEmpty(r.get("prepay_id"))){
			data.put("package","prepay_id="+r.get("prepay_id"));
			wsOrder.setPrepayId(r.get("prepay_id"));
			wsOrder.setPrepayDate(now);
			wsOrderService.save(wsOrder);
		}	
		data.put("appId",account.getAccountAppid());
		data.put("timeStamp",(System.currentTimeMillis()/1000)+"");
		data.put("nonceStr",nonceStr);
		data.put("signType",WXPayConstants.HMACSHA256);
		data.put("paySign",WXPayUtil.generateSignature(data, account.getPayKey(), SignType.HMACSHA256));
		data.put("ret",InterConstant.RET_SUCCESS);
		wsOrder=wsOrderService.get(orderId);
		WsOrderItem wsOrderItem=new WsOrderItem();
		wsOrderItem.setWsOrder(new WsOrder(orderId));
		wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
		for (WsOrderItem item:wsOrderItemList) {
			item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
		}
		data.put("wsOrderItemList",wsOrderItemList);
		data.put("wsOrder",wsOrder);		
		data.put("wsAddress",wsAddress);
		data.put("totalMoney",wsOrder.getReallyPrice());
		if(StringUtils.isEmpty(wsOrder.getExpress())||wsOrder.getExpress().equals("0")){
			data.put("expressWay","全国包邮");
		}else{
			data.put("expressWay","邮费自理");
		}
		return data;
	}
	
	@Transactional(readOnly = false)
	public Map orderPayById(String orderId,WsMember member,String ip,String notify) throws Exception {
		Map data=new HashMap();
		/**
		 * 查询用户地址信息
		 */
		WsOrder wsOrder=wsOrderService.get(orderId);
		WsAddress wsAddress=wsOrder.getAddress();
		wsAddress.setIsDefault(WsConstant.YES);
		wsAddress.setWsMember(member);
		/**
		 * 生成订单信息
		 */
		List<WsOrderItem> wsOrderItemList=new ArrayList<WsOrderItem>();
		Date now=new Date();
		String body="";
		BigDecimal totalMoney=wsOrder.getReallyPrice();
		//从个人中心的待付款进入订单付款页面，则直接查询此订单信息
		WsOrderItem wsOrderItem=new WsOrderItem();
		wsOrderItem.setWsOrder(new WsOrder(orderId));
		wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
		for (WsOrderItem item : wsOrderItemList) {
			WsProduct wsProduct=item.getWsProduct();
			if(StringUtils.isEmpty(body)&&StringUtils.isNotEmpty(wsProduct.getTitle())){
				body=wsProduct.getTitle();
			}
		}
		/**
		 * 获取预付款Id
		 */
		String nonceStr=WXPayUtil.generateNonceStr();
		WxAccount account=WsUtils.getAccount();
		WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		WXPay wxpay = new WXPay(config);
        HashMap<String, String> preData = new HashMap<String, String>();
        preData.put("appid", account.getAccountAppid());
        preData.put("mch_id", account.getMchId());
        preData.put("device_info", "WEB");
        preData.put("nonce_str", nonceStr);
        preData.put("body", body);
        preData.put("out_trade_no", wsOrder.getOrderSn());
        preData.put("fee_type", "CNY");
        preData.put("total_fee", String.valueOf(totalMoney.multiply(new BigDecimal(100)).intValue()));
        preData.put("spbill_create_ip", ip);
        preData.put("notify_url", notify);
        preData.put("trade_type", "JSAPI");
        preData.put("product_id", wsOrder.getId());
        preData.put("openid", member.getOpenId());
        //判断是否已经超过2个小时未付款，如果超过两个小时未付款，需要从新获取prepay_id
        if(wsOrder.getPrepayDate() == null || DateUtils.pastMinutes(wsOrder.getPrepayDate())>=120){
	        Map<String, String> r = wxpay.unifiedOrder(preData);
			if(StringUtils.isNotEmpty(r.get("prepay_id"))){
				data.put("package","prepay_id="+r.get("prepay_id"));
				wsOrder.setPrepayId(r.get("prepay_id"));
				wsOrder.setPrepayDate(now);
				wsOrderService.save(wsOrder);
			}	
        }else{
        	data.put("package","prepay_id="+wsOrder.getPrepayId());
        }
		data.put("appId",account.getAccountAppid());
		data.put("timeStamp",(System.currentTimeMillis()/1000)+"");
		data.put("nonceStr",nonceStr);
		data.put("signType",WXPayConstants.HMACSHA256);
		data.put("paySign",WXPayUtil.generateSignature(data, account.getPayKey(), SignType.HMACSHA256));
		data.put("ret",InterConstant.RET_SUCCESS);
		wsOrder=wsOrderService.get(orderId);
		wsOrderItem=new WsOrderItem();
		wsOrderItem.setWsOrder(new WsOrder(orderId));
		wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
		for (WsOrderItem item:wsOrderItemList) {
			item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
		}
		data.put("wsOrderItemList",wsOrderItemList);
		data.put("wsOrder",wsOrder);		
		data.put("wsAddress",wsAddress);
		data.put("totalMoney",totalMoney);
		if(wsOrder.getPostage().compareTo(new BigDecimal(0))==0){
			data.put("expressWay","全国包邮");
		}else{
			data.put("expressWay","邮费自理");
		}
		return data;
	}
	
	
	@Transactional(readOnly = false)
	public void payNotify(String outTradeNo,String totalFee) throws Exception {
    	WsOrder wsOrder=new WsOrder();
    	wsOrder.setOrderSn(outTradeNo);
    	List<WsOrder> wsOrderList=wsOrderService.findList(wsOrder);
    	for (WsOrder order:wsOrderList) {
			if(order.getReallyPrice().multiply(new BigDecimal(100)).compareTo(new BigDecimal(totalFee))==0){
				order.setOrderState(WsConstant.ORDER_STATE_WAITE_SEND);
				order.setPaytime(new Date());
				wsOrderService.save(order);
				WsMember wsMember=wsMemberService.get(order.getMemberId());
				//减少库存
				WsOrderItem wsOrderItem=new WsOrderItem();
				wsOrderItem.setWsOrder(new WsOrder(order.getId()));
				List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
				for(WsOrderItem item:wsOrderItemList){
					WsProdSku sku=wsProdSkuService.get(item.getSkuId());
					sku.setSurplusQuantity(sku.getSurplusQuantity()-item.getQuantity());
					wsProdSkuService.save(sku);
					try{
						//商品销售数量增加
						WsProduct wsProduct = sku.getWsProduct();
						wsProduct = wsProductService.get(wsProduct.getId());
						wsProduct.setSelNum(wsProduct.getSelNum()+item.getQuantity());
						wsProductService.save(wsProduct);
						
						//代理商购买返利
						if(wsMember.getIsAgent()!=null && "1".equals(wsMember.getIsAgent())){
							WsCommission commission = new WsCommission();
							wsCommissionService.commission(commission, item, wsMember, item.getQuantity());
							wsCommissionService.save(commission);
						}else{
							//如果该用户不是代理商，并且购买的商品是分销商品
							if (item.getWsProduct() != null && StringUtils.isNotEmpty(item.getWsProduct().getId())) {
								if ("1".equals(wsProductService.get(item.getWsProduct().getId()).getIsAgentProduct())) {//该商品是分销商品
									wsMember.setToAgentNum(wsMember.getToAgentNum() + item.getQuantity());
									if (wsMember.getToAgentNum() >= 2) {
										wsMember.setIsAgent("1");
										wsMember.setBecomeAgentTime(new Date());
										//分销记录
										WsCommission commission = new WsCommission();
										wsCommissionService.toAgentCommission(commission, item, wsMember);
										//如果大于2箱，就要算消费返利
										if (wsMember.getToAgentNum() > 2) {
											wsCommissionService.commission(commission, item, wsMember, wsMember.getToAgentNum() - 2);
										}
										wsCommissionService.save(commission);
									}
									wsMemberService.save(wsMember);
									
								}
							}
						}
					}catch(Exception e){
						logger.error("代理商购买返利异常：",e);
					}
					
				}
				//根据购买金额赠送用户积分
				wsMember.setScore(wsMember.getScore()+Integer.valueOf(totalFee));
				wsMemberService.save(wsMember);
				//生成会员奖励记录
				WsMemberRewardLog wsMemberRewardLog=new WsMemberRewardLog();
				wsMemberRewardLog.setWsMember(wsMember);
				wsMemberRewardLog.setRewardMoney(new BigDecimal(0));
				wsMemberRewardLog.setRewardScore(Integer.valueOf(totalFee));
				wsMemberRewardLog.setRechargeTime(new Date());
				wsMemberRewardLog.setScore(wsMember.getScore());
				wsMemberRewardLog.setBalance(wsMember.getBalance());
				wsMemberRewardLog.setRewardType(WsConstant.REWARD_BUY);
				wsMemberRewardLog.setRewardDesc("购买赠送积分："+totalFee);
				wsMemberRewardLogService.save(wsMemberRewardLog);
				/**
				 * 计算会员的总积分，如果满足会员等级条件，则进行晋升
				 */
				WsMemberRewardLog rewardLog=wsMemberRewardLogService.getSumReward(wsMemberRewardLog);
				WsMrank wsMrank=wsMrankService.getMrankByScore(rewardLog.getRewardScore());
				if(wsMrank!=null && !wsMrank.getId().equals(wsMember.getMemberRankId())){
					wsMember.setMemberRankId(wsMrank.getId());
					wsMember.setMemberRankName(wsMrank.getName());
					wsMemberService.save(wsMember);
				}
			}
		}
	}
	
}
