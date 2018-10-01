package com.thinkgem.jeesite.modules.member.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;

/**
 * 会员资料DAO接口
 * @author zengyq
 * @version 2017-10-09
 */
@MyBatisDao
public interface WsAddressDao extends CrudDao<WsAddress> {
	
	/**
	 * 根据条件根据当前地址是否默认地址
	 * @param wsAddress
	 */
	public void updateDefault(WsAddress wsAddress);
}