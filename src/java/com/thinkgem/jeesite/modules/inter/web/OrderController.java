package com.thinkgem.jeesite.modules.inter.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.entity.WsExFaretemplate;
import com.thinkgem.jeesite.modules.config.service.WsExFaretemplateService;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsAddressService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.entity.WsWarn;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.order.service.WsWarnService;
import com.thinkgem.jeesite.modules.prod.entity.WsCart;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturn;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturnItem;
import com.thinkgem.jeesite.modules.returnback.service.WsReturnItemService;
import com.thinkgem.jeesite.modules.returnback.service.WsReturnService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 订单页面
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/order")
public class OrderController extends BaseController {
	
	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsAddressService wsAddressService;
	
	@Autowired
	private WsOrderItemService wsOrderItemService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private WsReturnService wsReturnService;
	
	@Autowired
	private WsReturnItemService wsReturnItemService;
	
	@Autowired
	private WsExFaretemplateService wsExFaretemplateService;
	
	@Autowired
	private WsWarnService wsWarnService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	/**
	 * 订单确认页查询接口
	 * @param wsCarts 购物车选中产品
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(@RequestBody List<WsCart> wsCarts,String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			String expressWay="全国包邮";
			WsAddress wsAddress=new WsAddress();
			wsAddress.setIsDefault(WsConstant.YES);
			wsAddress.setWsMember(member);
			List<WsAddress> wsAddressList=wsAddressService.findList(wsAddress);
			List<WsOrderItem> wsOrderItemList=new ArrayList<WsOrderItem>();
			if(StringUtils.isNotEmpty(orderId)){
				WsOrder wsOrder=wsOrderService.get(orderId);
				if(!wsOrder.getExpress().equals(WsConstant.EXPRESS_FREE)){
					WsExFaretemplate wsExFaretemplate=wsExFaretemplateService.get(wsOrder.getExpress());
					expressWay=wsExFaretemplate.getName();
				}
				WsOrderItem wsOrderItem=new WsOrderItem();
				wsOrderItem.setWsOrder(wsOrder);
				wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
			}else{
				/**
				 * 根据购物车选中的商品生成订单确认页
				 */
				for (WsCart cart : wsCarts) {
					WsOrderItem wsOrderItem=new WsOrderItem();
					WsProdSku wsProdSku=wsProdSkuService.get(cart.getSkuId());
					WsProduct wsProduct=wsProductService.get(wsProdSku.getWsProduct().getId());
					if(wsProdSku.getSurplusQuantity()<cart.getQuantity()){
						throw new Exception(wsProduct.getTitle()+"库存不足");
					}
					/**
					 * 去第一个快递模板进行展示
					 */
					if(!wsProduct.getExpressId().equals(WsConstant.EXPRESS_FREE)
							&&expressWay.equals("全国包邮")){
						expressWay="邮费自理";
					}
					wsOrderItem.setWsProduct(wsProduct);
					wsOrderItem.setSkuId(cart.getSkuId());
					wsOrderItem.setSkuSpec(wsProdSku.getSkuName());
					wsOrderItem.setThumb(wsProduct.getProdImage());
					wsOrderItem.setQuantity(cart.getQuantity());
					wsOrderItem.setUnitPrice(wsProdSku.getPrice());
					wsOrderItem.setReallyUnitPrice(wsProdSku.getReallyPrice());
					wsOrderItem.setReallyPrice(wsProdSku.getReallyPrice());
					wsOrderItem.setWsProdSku(wsProdSku);
					wsOrderItemList.add(wsOrderItem);
				}
			}
			/**
			 * 转换图片地址
			 */
			for(WsOrderItem item:wsOrderItemList){
				item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsOrderItemList",wsOrderItemList);
			data.put("expressWay",expressWay);
			if(wsAddressList!=null && wsAddressList.size()>0){
				data.put("wsAddress",wsAddressList.get(0));
			}
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("order",e);
		}
		return data;
	}
	
	@RequestMapping(value = "list")
	@ResponseBody
	@CrossOrigin
	public Map list(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 查询全部订单
			 */
			WsOrder wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			List<WsOrder> wsAllOrderList=wsOrderService.findList(wsOrder);
			List<WsOrderItem> wsAllOrderItemList=new ArrayList<WsOrderItem>();
			for (WsOrder order:wsAllOrderList) {
				WsOrderItem wsOrderItem=new WsOrderItem();
				wsOrderItem.setWsOrder(order);
				List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
				for(WsOrderItem item:wsOrderItemList){
					item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
				}
				wsAllOrderItemList.addAll(wsOrderItemList);
			}
			/**
			 * 查询待付款的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_PAY);
			List<WsOrder> wsWaitPayOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询待发货的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_SEND);
			List<WsOrder> wsWaitSendOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询待收货的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_RECEVIED);
			List<WsOrder> wsWaitReceviedOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询待评价的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
			List<WsOrder> wsWaitEvaluationOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询已完成的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_FINSH);
			List<WsOrder> wsFinshOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询退款定单
			 */
			WsReturn wsReturn=new WsReturn();
			wsReturn.setWsMember(member);
			List<WsReturn> wsReturnList=wsReturnService.findList(wsReturn);
			List<WsReturnItem> wsAllReturnItemList=new ArrayList<WsReturnItem>();
			for(WsReturn turn:wsReturnList){
				WsReturnItem wsReturnItem=new WsReturnItem();
				wsReturnItem.setWsReturn(turn);
				List<WsReturnItem> wsReturnItemList=wsReturnItemService.findList(wsReturnItem);
				for (WsReturnItem item:wsReturnItemList) {
					item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
				}
				wsAllReturnItemList.addAll(wsReturnItemList);
				turn.setState(DictUtils.getDictLabel(turn.getState(), "return_state", ""));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsAllOrderList",wsAllOrderList);
			data.put("wsAllOrderItemList",wsAllOrderItemList);
			data.put("wsWaitPayOrderList",wsWaitPayOrderList);
			data.put("wsWaitSendOrderList",wsWaitSendOrderList);
			data.put("wsWaitReceviedOrderList",wsWaitReceviedOrderList);
			data.put("wsWaitEvaluationOrderList",wsWaitEvaluationOrderList);
			data.put("wsFinshOrderList",wsFinshOrderList);
			data.put("wsAllReturnItemList",wsAllReturnItemList);
			data.put("wsReturnList",wsReturnList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("order/list",e);
		}
		return data;
	}
	
	/**
	 * 确认收货
	 */
	@RequestMapping(value = "orderRecevied")
	@ResponseBody
	@CrossOrigin
	public Map orderRecevied(String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 确定收货状态
			 */
			WsOrder reciveOrder=wsOrderService.get(orderId);
			reciveOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
			reciveOrder.setReceviceTime(new Date());
			wsOrderService.save(reciveOrder);
			/**
			 * 查询全部订单
			 */
			WsOrder wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			List<WsOrder> wsAllOrderList=wsOrderService.findList(wsOrder);
			List<WsOrderItem> wsAllOrderItemList=new ArrayList<WsOrderItem>();
			for (WsOrder order:wsAllOrderList) {
				WsOrderItem wsOrderItem=new WsOrderItem();
				wsOrderItem.setWsOrder(order);
				List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
				wsAllOrderItemList.addAll(wsOrderItemList);
			}
			/**
			 * 查询待付款的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_PAY);
			List<WsOrder> wsWaitPayOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询待发货的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_SEND);
			List<WsOrder> wsWaitSendOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询待收货的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_RECEVIED);
			List<WsOrder> wsWaitReceviedOrderList=wsOrderService.findList(wsOrder);
			wsWaitReceviedOrderList.addAll(wsWaitSendOrderList);
			/**
			 * 查询待评价的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
			List<WsOrder> wsWaitEvaluationOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询已完成的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_FINSH);
			List<WsOrder> wsFinshOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询退款定单
			 */
			WsReturn wsReturn=new WsReturn();
			wsReturn.setWsMember(member);
			List<WsReturn> wsReturnList=wsReturnService.findList(wsReturn);
			List<WsReturnItem> wsAllReturnItemList=new ArrayList<WsReturnItem>();
			for(WsReturn turn:wsReturnList){
				WsReturnItem wsReturnItem=new WsReturnItem();
				wsReturnItem.setWsReturn(turn);
				List<WsReturnItem> wsReturnItemList=wsReturnItemService.findList(wsReturnItem);
				for (WsReturnItem item:wsReturnItemList) {
					item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
				}
				wsAllReturnItemList.addAll(wsReturnItemList);
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsAllOrderList",wsAllOrderList);
			data.put("wsAllOrderItemList",wsAllOrderItemList);
			data.put("wsWaitPayOrderList",wsWaitPayOrderList);
			data.put("wsWaitSendOrderList",wsWaitSendOrderList);
			data.put("wsWaitReceviedOrderList",wsWaitReceviedOrderList);
			data.put("wsWaitEvaluationOrderList",wsWaitEvaluationOrderList);
			data.put("wsFinshOrderList",wsFinshOrderList);
			data.put("wsAllReturnItemList",wsAllReturnItemList);
			data.put("wsReturnList",wsReturnList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("order/orderRecevied",e);
		}
		return data;
	}
	
	/**
	 * 取消订单
	 */
	@RequestMapping(value = "cancelOrder")
	@ResponseBody
	@CrossOrigin
	public Map cancelOrder(String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 取消订单
			 */
			wsOrderService.cancelOrder(orderId);
			/**
			 * 查询全部订单
			 */
			WsOrder wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			List<WsOrder> wsAllOrderList=wsOrderService.findList(wsOrder);
			List<WsOrderItem> wsAllOrderItemList=new ArrayList<WsOrderItem>();
			for (WsOrder order:wsAllOrderList) {
				WsOrderItem wsOrderItem=new WsOrderItem();
				wsOrderItem.setWsOrder(order);
				List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
				wsAllOrderItemList.addAll(wsOrderItemList);
			}
			/**
			 * 查询待付款的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_PAY);
			List<WsOrder> wsWaitPayOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询待发货的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_SEND);
			List<WsOrder> wsWaitSendOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询待收货的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_RECEVIED);
			List<WsOrder> wsWaitReceviedOrderList=wsOrderService.findList(wsOrder);
			wsWaitReceviedOrderList.addAll(wsWaitSendOrderList);
			/**
			 * 查询待评价的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
			List<WsOrder> wsWaitEvaluationOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询已完成的订单
			 */
			wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_FINSH);
			List<WsOrder> wsFinshOrderList=wsOrderService.findList(wsOrder);
			/**
			 * 查询退款定单
			 */
			WsReturn wsReturn=new WsReturn();
			wsReturn.setWsMember(member);
			List<WsReturn> wsReturnList=wsReturnService.findList(wsReturn);
			List<WsReturnItem> wsAllReturnItemList=new ArrayList<WsReturnItem>();
			for(WsReturn turn:wsReturnList){
				WsReturnItem wsReturnItem=new WsReturnItem();
				wsReturnItem.setWsReturn(turn);
				List<WsReturnItem> wsReturnItemList=wsReturnItemService.findList(wsReturnItem);
				for (WsReturnItem item:wsReturnItemList) {
					item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
				}
				wsAllReturnItemList.addAll(wsReturnItemList);
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsAllOrderList",wsAllOrderList);
			data.put("wsAllOrderItemList",wsAllOrderItemList);
			data.put("wsWaitPayOrderList",wsWaitPayOrderList);
			data.put("wsWaitSendOrderList",wsWaitSendOrderList);
			data.put("wsWaitReceviedOrderList",wsWaitReceviedOrderList);
			data.put("wsWaitEvaluationOrderList",wsWaitEvaluationOrderList);
			data.put("wsFinshOrderList",wsFinshOrderList);
			data.put("wsAllReturnItemList",wsAllReturnItemList);
			data.put("wsReturnList",wsReturnList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("order/cancelOrder",e);
		}
		return data;
	}
	
	/**
	 * 发货提醒
	 */
	@RequestMapping(value = "warnOrder")
	@ResponseBody
	@CrossOrigin
	public Map warnOrder(String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WsOrder wsOrder=wsOrderService.get(orderId);
			WsWarn wsWarn=new WsWarn();
			wsWarn.setType("1");
			wsWarn.setLevel("1");
			wsWarn.setState("1");
			wsWarn.setWarnContent("订单"+wsOrder.getOrderSn()+",客户已经提醒发货，请及时发货");
			wsWarnService.save(wsWarn);
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("order/cancelOrder",e);
		}
		return data;
	}
	
	
	/**
	 * 订单确认页查询接口,由于获取用户方式不同，小程序专用
	 * @param wsCarts 购物车选中产品
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "wxcIndex")
	@ResponseBody
	@CrossOrigin
	public Map wxcIndex(@RequestBody List<WsCart> wsCarts,String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			String memberId=wsCarts.get(0).getMemberId();
			WsMember member=wsMemberService.get(memberId);
			String expressWay="全国包邮";
			WsAddress wsAddress=new WsAddress();
			wsAddress.setIsDefault(WsConstant.YES);
			wsAddress.setWsMember(member);
			List<WsAddress> wsAddressList=wsAddressService.findList(wsAddress);
			List<WsOrderItem> wsOrderItemList=new ArrayList<WsOrderItem>();
			if(StringUtils.isNotEmpty(orderId)){
				WsOrder wsOrder=wsOrderService.get(orderId);
				if(!wsOrder.getExpress().equals(WsConstant.EXPRESS_FREE)){
					WsExFaretemplate wsExFaretemplate=wsExFaretemplateService.get(wsOrder.getExpress());
					expressWay=wsExFaretemplate.getName();
				}
				WsOrderItem wsOrderItem=new WsOrderItem();
				wsOrderItem.setWsOrder(wsOrder);
				wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
			}else{
				/**
				 * 根据购物车选中的商品生成订单确认页
				 */
				for (WsCart cart : wsCarts) {
					WsOrderItem wsOrderItem=new WsOrderItem();
					WsProdSku wsProdSku=wsProdSkuService.get(cart.getSkuId());
					WsProduct wsProduct=wsProductService.get(wsProdSku.getWsProduct().getId());
					if(wsProdSku.getSurplusQuantity()<cart.getQuantity()){
						throw new Exception(wsProduct.getTitle()+"库存不足");
					}
					/**
					 * 去第一个快递模板进行展示
					 */
					if(!wsProduct.getExpressId().equals(WsConstant.EXPRESS_FREE)
							&&expressWay.equals("全国包邮")){
						expressWay="邮费自理";
					}
					wsOrderItem.setWsProduct(wsProduct);
					wsOrderItem.setSkuId(cart.getSkuId());
					wsOrderItem.setSkuSpec(wsProdSku.getSkuName());
					wsOrderItem.setThumb(wsProduct.getProdImage());
					wsOrderItem.setQuantity(cart.getQuantity());
					wsOrderItem.setUnitPrice(wsProdSku.getPrice());
					wsOrderItem.setReallyUnitPrice(wsProdSku.getReallyPrice());
					wsOrderItem.setReallyPrice(wsProdSku.getReallyPrice());
					wsOrderItem.setWsProdSku(wsProdSku);
					wsOrderItemList.add(wsOrderItem);
				}
			}
			/**
			 * 转换图片地址
			 */
			for(WsOrderItem item:wsOrderItemList){
				item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsOrderItemList",wsOrderItemList);
			data.put("expressWay",expressWay);
			if(wsAddressList!=null && wsAddressList.size()>0){
				data.put("wsAddress",wsAddressList.get(0));
			}
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("order",e);
		}
		return data;
	}
	
	/**
	 * 小程序订单确认页查询接口
	 * @param wsCarts 购物车选中产品
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "wcxindex")
	@ResponseBody
	@CrossOrigin
	public Map wcxindex(@RequestBody List<WsCart> wsCarts,String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			String memberId=wsCarts.get(0).getMemberId();
			WsMember member=wsMemberService.get(memberId);
			String expressWay="全国包邮";
			WsAddress wsAddress=new WsAddress();
			wsAddress.setIsDefault(WsConstant.YES);
			wsAddress.setWsMember(member);
			List<WsAddress> wsAddressList=wsAddressService.findList(wsAddress);
			List<WsOrderItem> wsOrderItemList=new ArrayList<WsOrderItem>();
			if(StringUtils.isNotEmpty(orderId)){
				WsOrder wsOrder=wsOrderService.get(orderId);
				if(!wsOrder.getExpress().equals(WsConstant.EXPRESS_FREE)){
					WsExFaretemplate wsExFaretemplate=wsExFaretemplateService.get(wsOrder.getExpress());
					expressWay=wsExFaretemplate.getName();
				}
				WsOrderItem wsOrderItem=new WsOrderItem();
				wsOrderItem.setWsOrder(wsOrder);
				wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
			}else{
				/**
				 * 根据购物车选中的商品生成订单确认页
				 */
				for (WsCart cart : wsCarts) {
					WsOrderItem wsOrderItem=new WsOrderItem();
					WsProdSku wsProdSku=wsProdSkuService.get(cart.getSkuId());
					WsProduct wsProduct=wsProductService.get(wsProdSku.getWsProduct().getId());
					if(wsProdSku.getSurplusQuantity()<cart.getQuantity()){
						throw new Exception(wsProduct.getTitle()+"库存不足");
					}
					/**
					 * 去第一个快递模板进行展示
					 */
					if(!wsProduct.getExpressId().equals(WsConstant.EXPRESS_FREE)
							&&expressWay.equals("全国包邮")){
						expressWay="邮费自理";
					}
					wsOrderItem.setWsProduct(wsProduct);
					wsOrderItem.setSkuId(cart.getSkuId());
					wsOrderItem.setSkuSpec(wsProdSku.getSkuName());
					wsOrderItem.setThumb(wsProduct.getProdImage());
					wsOrderItem.setQuantity(cart.getQuantity());
					wsOrderItem.setUnitPrice(wsProdSku.getPrice());
					wsOrderItem.setReallyUnitPrice(wsProdSku.getReallyPrice());
					wsOrderItem.setReallyPrice(wsProdSku.getReallyPrice());
					wsOrderItem.setWsProdSku(wsProdSku);
					wsOrderItemList.add(wsOrderItem);
				}
			}
			/**
			 * 转换图片地址
			 */
			for(WsOrderItem item:wsOrderItemList){
				item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsOrderItemList",wsOrderItemList);
			data.put("expressWay",expressWay);
			if(wsAddressList!=null && wsAddressList.size()>0){
				data.put("wsAddress",wsAddressList.get(0));
			}
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("order",e);
		}
		return data;
	}
}