package com.thinkgem.jeesite.modules.withdrawal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.withdrawal.entity.WsWithdrawal;

/**
 * 提现DAO接口
 * @author 分销系统开发者
 * @version 2018-10-07
 */
@MyBatisDao
public interface WsWithdrawalDao extends CrudDao<WsWithdrawal> {
	
}