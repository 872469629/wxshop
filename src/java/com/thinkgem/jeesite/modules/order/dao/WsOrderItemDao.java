package com.thinkgem.jeesite.modules.order.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;

/**
 * 订单DAO接口
 * @author water
 * @version 2017-10-07
 */
@MyBatisDao
public interface WsOrderItemDao extends CrudDao<WsOrderItem> {
	
}