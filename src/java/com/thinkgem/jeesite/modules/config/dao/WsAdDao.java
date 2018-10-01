package com.thinkgem.jeesite.modules.config.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.config.entity.WsAd;


/**
 * 图片管理DAO接口
 * @author water
 * @version 2017-10-01
 */
@MyBatisDao
public interface WsAdDao extends CrudDao<WsAd> {
	
}