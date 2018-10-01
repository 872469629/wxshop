package com.thinkgem.jeesite.modules.returnback.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;
import com.thinkgem.jeesite.modules.member.service.WsMemberCouponService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.prod.utils.ProdUtils;
import com.thinkgem.jeesite.modules.returnback.dao.WsReturnDao;
import com.thinkgem.jeesite.modules.returnback.dao.WsReturnItemDao;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturn;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturnItem;
import com.thinkgem.jeesite.modules.sys.utils.SeqUtils;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.wx.pay.WXPay;
import com.thinkgem.jeesite.modules.wx.pay.WXPayConfigImpl;

/**
 * 退货管理Service
 * @author 大胖老师
 * @version 2017-12-02
 */
@Service
@Transactional(readOnly = true)
public class WsReturnService extends CrudService<WsReturnDao, WsReturn> {
	@Autowired
	private WsMemberCouponService wsMemberCouponService;

	@Autowired
	private WsReturnItemDao wsReturnItemDao;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsReturnItemService wsReturnItemService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private WsOrderItemService wsOrderItemService;
	
	public WsReturn get(String id) {
		WsReturn wsReturn = super.get(id);
		wsReturn.setWsReturnItemList(wsReturnItemDao.findList(new WsReturnItem(wsReturn)));
		return wsReturn;
	}
	
	public int findCount(WsReturn wsReturn) {
		return dao.findCount(wsReturn);
	}
	
	public List<WsReturn> findList(WsReturn wsReturn) {
		return super.findList(wsReturn);
	}
	
	public Page<WsReturn> findPage(Page<WsReturn> page, WsReturn wsReturn) {
		return super.findPage(page, wsReturn);
	}
	
	@Transactional(readOnly = false)
	public void saveWsReturn(WsReturn wsReturn) {
		super.save(wsReturn);
		backCoupon(wsReturn);
	}
	
	/**
	 * 微信退款(需要微信证书)
	 * @param wsReturn
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public void saveWsReturnOrder(WsReturn wsReturn) throws Exception {
		wsReturn.setRefundSn(ProdUtils.getDateSeq(WsConstant.RETURN_SEQ));
		WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		WXPay wxpay = new WXPay(config);
		//调用微信退款接口
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", wsReturn.getOrderSn());
        data.put("out_refund_no", wsReturn.getRefundSn());
        data.put("total_fee", wsReturn.getOrderAmount().toString());
        data.put("refund_fee", wsReturn.getReturnAmount().toString());
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());
        Map<String, String> wxReturnMap=wxpay.refund(data);
        if(wxReturnMap.get("return_code").equals("FAIL")){
        	throw new Exception(wxReturnMap.get("return_msg"));
        }
        wsReturn.setRefundId(wxReturnMap.get("refund_id"));
		super.save(wsReturn);
		backCoupon(wsReturn);
	}
	
	@Transactional(readOnly = false)
	public void save(WsReturn wsReturn) {
		super.save(wsReturn);
		for (WsReturnItem wsReturnItem : wsReturn.getWsReturnItemList()){
			if (wsReturnItem.getId() == null){
				continue;
			}
			if (WsReturnItem.DEL_FLAG_NORMAL.equals(wsReturnItem.getDelFlag())){
				if (StringUtils.isBlank(wsReturnItem.getId())){
					wsReturnItem.setWsReturn(wsReturn);
					wsReturnItem.preInsert();
					wsReturnItemDao.insert(wsReturnItem);
				}else{
					wsReturnItem.preUpdate();
					wsReturnItemDao.update(wsReturnItem);
				}
			}else{
				wsReturnItemDao.delete(wsReturnItem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WsReturn wsReturn) {
		super.delete(wsReturn);
		wsReturnItemDao.delete(new WsReturnItem(wsReturn));
	}
	
	/**
	 * 退款后返还用户优惠券
	 * @param wsReturn
	 */
	@Transactional(readOnly = false)
	public void backCoupon(WsReturn wsReturn) { 
		if(wsReturn.getWsMemberCoupon()!=null && StringUtils.isNotEmpty(wsReturn.getWsMemberCoupon().getId())){
			WsMemberCoupon wsMemberCoupon=wsMemberCouponService.get(wsReturn.getWsMemberCoupon().getId());
			wsMemberCoupon.setState(WsConstant.COUPON_TO_USE);
			wsMemberCouponService.save(wsMemberCoupon);
		}
	}
	
	@Transactional(readOnly = false)
	public void saveUserReturn(List<WsReturnItem> wsReturnItems,WsMember member) {
		/**
		 * 生成退款订单信息
		 */
		String orderId=wsReturnItems.get(0).getOrderId();
		String reason=wsReturnItems.get(0).getReason();
		WsOrder wsOrder=wsOrderService.get(orderId);
		WsReturn wsReturn=new WsReturn();
		wsReturn.setOrderSn(wsOrder.getOrderSn());
		wsReturn.setOrderAmount(wsOrder.getReallyPrice());
		wsReturn.setWsMemberCoupon(wsOrder.getWsMemberCoupon());
		wsReturn.setWsMember(member);
		wsReturn.setReason(reason);
		wsReturn.setRefundSn(String.valueOf(SeqUtils.getNextSeq("returnSeq")));
		wsReturn.setState(WsConstant.RETURN_ORDER_STATE_WX_WAITE);
		save(wsReturn);
		/**
		 * 订单保存退款信息
		 */
		wsOrder.setWsReturn(wsReturn);
		wsOrderService.save(wsOrder);
		/**
		 * 保存退款明细
		 */
		BigDecimal totalMoney=new BigDecimal(0);
		for (WsReturnItem wsReturnItem:wsReturnItems) {
			WsOrderItem item=wsOrderItemService.get(wsReturnItem.getWsOrderItem().getId());
			wsReturnItem.setWsReturn(wsReturn);
			wsReturnItem.setWsProduct(item.getWsProduct());
			wsReturnItem.setUnitPrice(item.getUnitPrice());
			wsReturnItem.setReallyUnitPrice(item.getReallyUnitPrice());
			wsReturnItem.setReallyPrice(item.getReallyPrice());
			wsReturnItemService.save(wsReturnItem);
			totalMoney=totalMoney.add(item.getReallyUnitPrice().multiply(new BigDecimal(wsReturnItem.getQuantity())));
		}
		wsReturn.setReturnScore(totalMoney.intValue());
		wsReturn.setReturnAmount(totalMoney);
		save(wsReturn);
	}
	
}