package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNewsItem;
import com.thinkgem.jeesite.modules.ws.dao.WxMaterialNewsItemDao;

/**
 * 图文素材明细Service
 * @author 大胖老师
 * @version 2017-10-29
 */
@Service
@Transactional(readOnly = true)
public class WxMaterialNewsItemService extends CrudService<WxMaterialNewsItemDao, WxMaterialNewsItem> {

	public WxMaterialNewsItem get(String id) {
		return super.get(id);
	}
	
	public List<WxMaterialNewsItem> findList(WxMaterialNewsItem wxMaterialNewsItem) {
		return super.findList(wxMaterialNewsItem);
	}
	
	public Page<WxMaterialNewsItem> findPage(Page<WxMaterialNewsItem> page, WxMaterialNewsItem wxMaterialNewsItem) {
		return super.findPage(page, wxMaterialNewsItem);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMaterialNewsItem wxMaterialNewsItem) {
		super.save(wxMaterialNewsItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMaterialNewsItem wxMaterialNewsItem) {
		super.delete(wxMaterialNewsItem);
	}
	
}