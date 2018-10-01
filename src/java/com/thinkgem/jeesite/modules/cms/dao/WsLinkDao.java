package com.thinkgem.jeesite.modules.cms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.WsLink;

/**
 * 底部链接DAO接口
 * @author HF
 * @version 2017-08-09
 */
@MyBatisDao
public interface WsLinkDao extends CrudDao<WsLink> {
	
}