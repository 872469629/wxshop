package com.thinkgem.jeesite.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cms.entity.WsLinkCategory;
import com.thinkgem.jeesite.modules.cms.dao.WsLinkCategoryDao;

/**
 * 链接类型表Service
 * @author HF
 * @version 2017-08-08
 */
@Service
@Transactional(readOnly = true)
public class WsLinkCategoryService extends CrudService<WsLinkCategoryDao, WsLinkCategory> {

	public WsLinkCategory get(String id) {
		return super.get(id);
	}
	
	public List<WsLinkCategory> findList(WsLinkCategory wsLinkCategory) {
		return super.findList(wsLinkCategory);
	}
	
	public Page<WsLinkCategory> findPage(Page<WsLinkCategory> page, WsLinkCategory wsLinkCategory) {
		return super.findPage(page, wsLinkCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(WsLinkCategory wsLinkCategory) {
		super.save(wsLinkCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsLinkCategory wsLinkCategory) {
		super.delete(wsLinkCategory);
	}
	
}