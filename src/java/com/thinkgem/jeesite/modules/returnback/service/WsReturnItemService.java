package com.thinkgem.jeesite.modules.returnback.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.returnback.dao.WsReturnItemDao;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturnItem;

/**
 * 退货管理Service
 * @author 大胖老师
 * @version 2017-12-02
 */
@Service
@Transactional(readOnly = true)
public class WsReturnItemService extends CrudService<WsReturnItemDao, WsReturnItem> {
	
	public WsReturnItem get(String id) {
		WsReturnItem wsReturnItem = super.get(id);
		return wsReturnItem;
	}
	
	public List<WsReturnItem> findList(WsReturnItem wsReturnItem) {
		return super.findList(wsReturnItem);
	}
	
	public Page<WsReturnItem> findPage(Page<WsReturnItem> page, WsReturnItem wsReturnItem) {
		return super.findPage(page, wsReturnItem);
	}

	
	@Transactional(readOnly = false)
	public void save(WsReturnItem wsReturnItem) {
		super.save(wsReturnItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsReturnItem wsReturnItem) {
		super.delete(wsReturnItem);
	}
	
}