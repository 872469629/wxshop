package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.entity.WxMassMsg;
import com.thinkgem.jeesite.modules.ws.dao.WxMassMsgDao;

/**
 * 群发微信Service
 * @author 大胖老师
 * @version 2017-12-31
 */
@Service
@Transactional(readOnly = true)
public class WxMassMsgService extends CrudService<WxMassMsgDao, WxMassMsg> {

	public WxMassMsg get(String id) {
		return super.get(id);
	}
	
	public List<WxMassMsg> findList(WxMassMsg wxMassMsg) {
		return super.findList(wxMassMsg);
	}
	
	public Page<WxMassMsg> findPage(Page<WxMassMsg> page, WxMassMsg wxMassMsg) {
		return super.findPage(page, wxMassMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMassMsg wxMassMsg) {
		super.save(wxMassMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMassMsg wxMassMsg) {
		super.delete(wxMassMsg);
	}
	
}