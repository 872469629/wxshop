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
 * 
 * 推广奖励定时任务
 */
public class WsJobOrderAward {
	
	private static WsOrderService wsOrderService = SpringContextHolder.getBean("wsOrderService");
	
	private static WsOrderItemService wsOrderItemService = SpringContextHolder.getBean("wsOrderItemService");
	
	private static WsConsulationService wsConsulationService = SpringContextHolder.getBean("wsConsulationService");

	
	/**
	 * 收货7天没有退款，则自动分配系统推广奖励
	 */
	public void orderAward(SysScheduleJob sysScheduleJob){
		
	}

}
