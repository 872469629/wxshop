package com.thinkgem.jeesite.modules.sys.task;

import java.util.List;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.entity.WsConsulation;
import com.thinkgem.jeesite.modules.prod.service.WsConsulationService;
import com.thinkgem.jeesite.modules.sys.entity.SysScheduleJob;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 每日自动扫描订单状态，帮助用户自动确认收货、自动评价
 * 超时退款时间后确认不可退款状态
 */
public class WsJobOrderState {
	
	private static WsOrderService wsOrderService = SpringContextHolder.getBean("wsOrderService");
	
	private static WsOrderItemService wsOrderItemService = SpringContextHolder.getBean("wsOrderItemService");
	
	private static WsConsulationService wsConsulationService = SpringContextHolder.getBean("wsConsulationService");

	
	/**
	 * 发货七天后由定时任务自动确认收货
	 */
	public void orderRecevice(SysScheduleJob sysScheduleJob){
		WsOrder wsOrder=new WsOrder();
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_RECEVIED);
		//日期与七天前的日期进行比较
		wsOrder.setSendTime(DateUtils.getBetweenDate(-7));
		List<WsOrder> wsOrderList=wsOrderService.findOverTimeList(wsOrder);
		for (WsOrder order:wsOrderList) {
			//将状态修改为待评价
			order.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
			wsOrderService.save(order);
		}
	}
	
	/**
	 * 收货七天后由定时任务自动评价
	 */
	public void orderConsulation(SysScheduleJob sysScheduleJob){
		WsOrder wsOrder=new WsOrder();
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
		//日期与七天前的日期进行比较
		wsOrder.setSendTime(DateUtils.getBetweenDate(-7));
		List<WsOrder> wsOrderList=wsOrderService.findOverTimeList(wsOrder);
		for (WsOrder order:wsOrderList) {
			//将状态修改为待评价
			order.setOrderState(WsConstant.ORDER_STATE_WAITE_FINSH);
			wsOrderService.save(order);
			/**
			 * 循环订单的每个产品，增加一个默认好评五星的评价记录
			 */
			List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(new WsOrderItem(order));
			for(WsOrderItem item:wsOrderItemList){
				WsConsulation wsConsulation=new WsConsulation();
				wsConsulation.setProductId(item.getWsProduct().getId());
				wsConsulation.setOrderId(order.getId());
				wsConsulation.setMemberId(order.getMemberId().getId());
				wsConsulation.setConsulationContent("默认好评");
				wsConsulation.setProdConsulationLevel(5);
				wsConsulation.setLogisticsConsulationLevel(5);
				wsConsulationService.save(wsConsulation);
			}
		}
	}
	
	/**
	 * 收货七天后由定时任务修改不可退款状态
	 */
	public void orderReturnState(SysScheduleJob sysScheduleJob){
		/**
		 * 待评价订单超过七日不可退款
		 */
		WsOrder wsOrder=new WsOrder();
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
		wsOrder.setReturnState(WsConstant.ORDER_CAN_RETURN);
		//日期与七天前的日期进行比较
		wsOrder.setSendTime(DateUtils.getBetweenDate(-7));
		List<WsOrder> wsEvaluationOrderList=wsOrderService.findOverTimeList(wsOrder);
		for (WsOrder order:wsEvaluationOrderList) {
			//将订单退款状态改为不可退款
			wsOrder.setReturnState(WsConstant.ORDER_NOT_RETURN);
			wsOrderService.save(order);
		}
		/**
		 * 已完成订单超过七日不可退款
		 */
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_FINSH);
		wsOrder.setReturnState(WsConstant.ORDER_CAN_RETURN);
		//日期与七天前的日期进行比较
		wsOrder.setSendTime(DateUtils.getBetweenDate(-7));
		List<WsOrder> wsFinshOrderList=wsOrderService.findOverTimeList(wsOrder);
		for (WsOrder order:wsFinshOrderList) {
			//将订单退款状态改为不可退款
			wsOrder.setReturnState(WsConstant.ORDER_NOT_RETURN);
			wsOrderService.save(order);
		}
	}
}
