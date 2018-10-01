package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.entity.WxKeyResponse;
import com.thinkgem.jeesite.modules.ws.dao.WxKeyResponseDao;

/**
 * 关键字回复Service
 * @author 大胖老师
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class WxKeyResponseService extends CrudService<WxKeyResponseDao, WxKeyResponse> {

	public WxKeyResponse get(String id) {
		return super.get(id);
	}
	
	public List<WxKeyResponse> findList(WxKeyResponse wxKeyResponse) {
		return super.findList(wxKeyResponse);
	}
	
	public Page<WxKeyResponse> findPage(Page<WxKeyResponse> page, WxKeyResponse wxKeyResponse) {
		return super.findPage(page, wxKeyResponse);
	}
	
	@Transactional(readOnly = false)
	public void save(WxKeyResponse wxKeyResponse) {
		super.save(wxKeyResponse);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxKeyResponse wxKeyResponse) {
		super.delete(wxKeyResponse);
	}
	
}