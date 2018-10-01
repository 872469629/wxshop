package com.thinkgem.jeesite.modules.prod.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdCategory;

/**
 * 产品分类DAO接口
 * @author water
 * @version 2017-08-11
 */
@MyBatisDao
public interface WsProdCategoryDao extends TreeDao<WsProdCategory> {
	
}