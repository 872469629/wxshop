package com.thinkgem.jeesite.modules.order.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.WsWarn;

/**
 * 商城异常数据告警DAO接口
 * @author 大胖老师
 * @version 2018-01-01
 */
@MyBatisDao
public interface WsWarnDao extends CrudDao<WsWarn> {
	
}