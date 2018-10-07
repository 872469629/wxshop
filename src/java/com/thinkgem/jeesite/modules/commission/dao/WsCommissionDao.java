package com.thinkgem.jeesite.modules.commission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;

/**
 * 分销明细DAO接口
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@MyBatisDao
public interface WsCommissionDao extends CrudDao<WsCommission> {

	/**
	 * 根据用户id集合查找所有订单总价格
	 */
	List<WsCommission> findPriceByMembers(@Param("members") List<WsMember> members);

	/**
	 * 根据购买者id集合查找所有分销记录
	 */
	List<WsCommission> findCommissionByMembers(@Param("members") List<WsMember> members);
	
}