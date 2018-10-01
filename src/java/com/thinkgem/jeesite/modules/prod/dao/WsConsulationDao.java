package com.thinkgem.jeesite.modules.prod.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsConsulation;

/**
 * 评论回复管理DAO接口
 * @author 大胖老师
 * @version 2017-11-14
 */
@MyBatisDao
public interface WsConsulationDao extends CrudDao<WsConsulation> {
	
}