package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialText;
import com.thinkgem.jeesite.modules.ws.dao.WxMaterialTextDao;

/**
 * 文本素材Service
 * @author 大胖老师
 * @version 2017-09-28
 */
@Service
@Transactional(readOnly = true)
public class WxMaterialTextService extends CrudService<WxMaterialTextDao, WxMaterialText> {

	public WxMaterialText get(String id) {
		return super.get(id);
	}
	
	public List<WxMaterialText> findList(WxMaterialText wxMaterialText) {
		return super.findList(wxMaterialText);
	}
	
	public Page<WxMaterialText> findPage(Page<WxMaterialText> page, WxMaterialText wxMaterialText) {
		return super.findPage(page, wxMaterialText);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMaterialText wxMaterialText) {
		super.save(wxMaterialText);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMaterialText wxMaterialText) {
		super.delete(wxMaterialText);
	}
	
}