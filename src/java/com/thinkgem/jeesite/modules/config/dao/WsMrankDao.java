package com.thinkgem.jeesite.modules.config.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;


/**
 * 会员等级DAO接口
 * @author water
 * @version 2017-09-30
 */
@MyBatisDao
public interface WsMrankDao extends CrudDao<WsMrank> {
	
}