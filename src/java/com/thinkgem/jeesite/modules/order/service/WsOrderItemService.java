package com.thinkgem.jeesite.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.dao.WsOrderItemDao;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;

/**
 * 订单Service
 * @author water
 * @version 2017-10-07
 */
@Service
@Transactional(readOnly = true)
public class WsOrderItemService extends CrudService<WsOrderItemDao, WsOrderItem> {
	
	public WsOrderItem get(String id) {
		WsOrderItem wsOrderItem = super.get(id);
		return wsOrderItem;
	}
	
	public List<WsOrderItem> findList(WsOrderItem wsOrderItem) {
		return super.findList(wsOrderItem);
	}
	
	public Page<WsOrderItem> findPage(Page<WsOrderItem> page, WsOrderItem wsOrderItem) {
		return super.findPage(page, wsOrderItem);
	}
	
	@Transactional(readOnly = false)
	public void save(WsOrderItem wsOrderItem) {
		super.save(wsOrderItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsOrderItem wsOrderItem) {
		super.delete(wsOrderItem);
	}
	
}