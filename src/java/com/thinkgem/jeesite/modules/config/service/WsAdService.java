package com.thinkgem.jeesite.modules.config.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.config.dao.WsAdDao;
import com.thinkgem.jeesite.modules.config.entity.WsAd;

/**
 * 图片管理Service
 * @author water
 * @version 2017-10-01
 */
@Service
@Transactional(readOnly = true)
public class WsAdService extends CrudService<WsAdDao, WsAd> {

	public WsAd get(String id) {
		return super.get(id);
	}
	
	public List<WsAd> findList(WsAd wsAd) {
		return super.findList(wsAd);
	}
	
	public Page<WsAd> findPage(Page<WsAd> page, WsAd wsAd) {
		return super.findPage(page, wsAd);
	}
	
	@Transactional(readOnly = false)
	public void save(WsAd wsAd) {
		super.save(wsAd);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsAd wsAd) {
		super.delete(wsAd);
	}
	
}