package com.thinkgem.jeesite.modules.config.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.config.entity.WsActivity;

/**
 * 活动配置DAO接口
 * @author 大胖老师
 * @version 2017-10-05
 */
@MyBatisDao
public interface WsActivityDao extends CrudDao<WsActivity> {
	
}