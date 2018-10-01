package com.thinkgem.jeesite.modules.config.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.config.entity.WsAnnouncement;

/**
 * 公告管理DAO接口
 * @author water
 * @version 2018-05-10
 */
@MyBatisDao
public interface WsAnnouncementDao extends CrudDao<WsAnnouncement> {
	
}