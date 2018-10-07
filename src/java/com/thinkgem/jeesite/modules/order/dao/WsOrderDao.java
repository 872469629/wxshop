package com.thinkgem.jeesite.modules.order.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;

/**
 * 订单DAO接口
 * @author water
 * @version 2017-10-07
 */
@MyBatisDao
public interface WsOrderDao extends CrudDao<WsOrder> {
		
	public int updatSaveRemark(WsOrder wsOrder);
		
	public WsOrder getByOrderSn(String id);
	
	public int findCount(WsOrder wsOrder);
	
	public List<WsOrder> findOverTimeList(WsOrder wsOrder);
	
	/**
	 * 根据用户id集合查找所有订单总价格
	 */
	public List<WsOrder> findPriceByMembers(@Param("members") List<WsMember> members);
	
}