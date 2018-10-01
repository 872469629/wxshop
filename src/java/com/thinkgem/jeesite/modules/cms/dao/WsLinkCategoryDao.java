package com.thinkgem.jeesite.modules.cms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.WsLinkCategory;

/**
 * 链接类型表DAO接口
 * @author HF
 * @version 2017-08-08
 */
@MyBatisDao
public interface WsLinkCategoryDao extends CrudDao<WsLinkCategory> {
	
}