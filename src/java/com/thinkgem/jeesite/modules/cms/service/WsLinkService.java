package com.thinkgem.jeesite.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cms.entity.WsLink;
import com.thinkgem.jeesite.modules.cms.dao.WsLinkDao;

/**
 * 底部链接Service
 * @author HF
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true)
public class WsLinkService extends CrudService<WsLinkDao, WsLink> {

	public WsLink get(String id) {
		return super.get(id);
	}
	
	public List<WsLink> findList(WsLink wsLink) {
		return super.findList(wsLink);
	}
	
	public Page<WsLink> findPage(Page<WsLink> page, WsLink wsLink) {
		return super.findPage(page, wsLink);
	}
	
	@Transactional(readOnly = false)
	public void save(WsLink wsLink) {
		super.save(wsLink);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsLink wsLink) {
		super.delete(wsLink);
	}
	
}