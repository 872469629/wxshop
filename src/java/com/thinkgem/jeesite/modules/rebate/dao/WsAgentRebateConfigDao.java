package com.thinkgem.jeesite.modules.rebate.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rebate.entity.WsAgentRebateConfig;

/**
 * 代理商分销配置DAO接口
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@MyBatisDao
public interface WsAgentRebateConfigDao extends CrudDao<WsAgentRebateConfig> {
	
}