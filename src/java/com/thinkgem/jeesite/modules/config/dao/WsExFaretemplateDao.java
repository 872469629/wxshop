package com.thinkgem.jeesite.modules.config.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.config.entity.WsExFaretemplate;

/**
 * 快递模版DAO接口
 * @author water
 * @version 2017-10-03
 */
@MyBatisDao
public interface WsExFaretemplateDao extends CrudDao<WsExFaretemplate> {
	
}