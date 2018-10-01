package com.thinkgem.jeesite.modules.prod.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttr;

/**
 * 产品属性DAO接口
 * @author 大胖老师
 * @version 2017-10-02
 */
@MyBatisDao
public interface WsProdAttrDao extends CrudDao<WsProdAttr> {
	
}