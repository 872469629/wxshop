package com.thinkgem.jeesite.modules.prod.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSkuAttr;

/**
 * 产品sku属性DAO接口
 * @author 大胖老师
 * @version 2017-10-02
 */
@MyBatisDao
public interface WsProdSkuAttrDao extends CrudDao<WsProdSkuAttr> {
	
	public List<WsProdSkuAttr> findAttrbuteIdList(WsProdSkuAttr wsProdSkuAttr);
	
	public void deleteByProId(WsProdSkuAttr wsProdSkuAttr);
	
}