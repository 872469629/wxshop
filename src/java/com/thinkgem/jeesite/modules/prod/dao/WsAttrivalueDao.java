package com.thinkgem.jeesite.modules.prod.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsAttrivalue;

/**
 * 产品属性DAO接口
 * @author water
 * @version 2017-08-10
 */
@MyBatisDao
public interface WsAttrivalueDao extends CrudDao<WsAttrivalue> {
	
}