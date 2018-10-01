package com.thinkgem.jeesite.modules.prod.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsBrand;

/**
 * 品牌DAO接口
 * @author water
 * @version 2017-08-08
 */
@MyBatisDao
public interface WsBrandDao extends CrudDao<WsBrand> {
	
}