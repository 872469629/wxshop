package com.thinkgem.jeesite.modules.config.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.config.entity.WsAnnouncement;
import com.thinkgem.jeesite.modules.config.dao.WsAnnouncementDao;

/**
 * 公告管理Service
 * @author water
 * @version 2018-05-10
 */
@Service
@Transactional(readOnly = true)
public class WsAnnouncementService extends CrudService<WsAnnouncementDao, WsAnnouncement> {

	public WsAnnouncement get(String id) {
		return super.get(id);
	}
	
	public List<WsAnnouncement> findList(WsAnnouncement wsAnnouncement) {
		return super.findList(wsAnnouncement);
	}
	
	public Page<WsAnnouncement> findPage(Page<WsAnnouncement> page, WsAnnouncement wsAnnouncement) {
		return super.findPage(page, wsAnnouncement);
	}
	
	@Transactional(readOnly = false)
	public void save(WsAnnouncement wsAnnouncement) {
		super.save(wsAnnouncement);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsAnnouncement wsAnnouncement) {
		super.delete(wsAnnouncement);
	}
	
}