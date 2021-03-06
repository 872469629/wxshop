package com.thinkgem.jeesite.modules.member.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;

/**
 * 会员资料DAO接口
 * @author zengyq
 * @version 2017-10-09
 */
@MyBatisDao
public interface WsMemberCouponDao extends CrudDao<WsMemberCoupon> {
	
}