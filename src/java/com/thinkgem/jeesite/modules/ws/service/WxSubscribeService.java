package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.entity.WxSubscribe;
import com.thinkgem.jeesite.modules.ws.dao.WxSubscribeDao;

/**
 * 关注欢迎语Service
 * @author 大胖老师
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class WxSubscribeService extends CrudService<WxSubscribeDao, WxSubscribe> {

	public WxSubscribe get(String id) {
		return super.get(id);
	}
	
	public List<WxSubscribe> findList(WxSubscribe wxSubscribe) {
		return super.findList(wxSubscribe);
	}
	
	public Page<WxSubscribe> findPage(Page<WxSubscribe> page, WxSubscribe wxSubscribe) {
		return super.findPage(page, wxSubscribe);
	}
	
	@Transactional(readOnly = false)
	public void save(WxSubscribe wxSubscribe) {
		super.save(wxSubscribe);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxSubscribe wxSubscribe) {
		super.delete(wxSubscribe);
	}
	
}