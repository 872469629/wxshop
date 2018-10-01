package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNews;
import com.thinkgem.jeesite.modules.ws.dao.WxMaterialNewsDao;

/**
 * 图文素材Service
 * @author 大胖老师
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class WxMaterialNewsService extends CrudService<WxMaterialNewsDao, WxMaterialNews> {

	public WxMaterialNews get(String id) {
		return super.get(id);
	}
	
	public List<WxMaterialNews> findList(WxMaterialNews wxMaterialNews) {
		return super.findList(wxMaterialNews);
	}
	
	public Page<WxMaterialNews> findPage(Page<WxMaterialNews> page, WxMaterialNews wxMaterialNews) {
		return super.findPage(page, wxMaterialNews);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMaterialNews wxMaterialNews) {
		super.save(wxMaterialNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMaterialNews wxMaterialNews) {
		super.delete(wxMaterialNews);
	}
	
}