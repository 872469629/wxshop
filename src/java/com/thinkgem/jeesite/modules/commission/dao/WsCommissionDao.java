package com.thinkgem.jeesite.modules.commission.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;

/**
 * 分销明细DAO接口
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@MyBatisDao
public interface WsCommissionDao extends CrudDao<WsCommission> {
	
}