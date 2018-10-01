package com.thinkgem.jeesite.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.WsWarn;
import com.thinkgem.jeesite.modules.order.dao.WsWarnDao;

/**
 * 商城异常数据告警Service
 * @author 大胖老师
 * @version 2018-01-01
 */
@Service
@Transactional(readOnly = true)
public class WsWarnService extends CrudService<WsWarnDao, WsWarn> {

	public WsWarn get(String id) {
		return super.get(id);
	}
	
	public List<WsWarn> findList(WsWarn wsWarn) {
		return super.findList(wsWarn);
	}
	
	public Page<WsWarn> findPage(Page<WsWarn> page, WsWarn wsWarn) {
		return super.findPage(page, wsWarn);
	}
	
	@Transactional(readOnly = false)
	public void save(WsWarn wsWarn) {
		super.save(wsWarn);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsWarn wsWarn) {
		super.delete(wsWarn);
	}
	
}