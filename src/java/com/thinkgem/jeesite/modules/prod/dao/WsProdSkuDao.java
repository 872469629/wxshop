package com.thinkgem.jeesite.modules.prod.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;

/**
 * 产品信息DAO接口
 * @author 大胖老师
 * @version 2017-10-01
 */
@MyBatisDao
public interface WsProdSkuDao extends CrudDao<WsProdSku> {
	
	public void deleteByProId(WsProdSku wsProdSku);
	
}