package com.thinkgem.jeesite.modules.member.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCoupon;
import com.thinkgem.jeesite.modules.activity.service.WsActivityCouponService;
import com.thinkgem.jeesite.modules.member.dao.WsMemberCouponDao;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 用户优惠券Service
 * @author zengyq
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberCouponService extends CrudService<WsMemberCouponDao, WsMemberCoupon> {
	
	@Autowired
	private WsActivityCouponService wsActivityCouponService;
	
	@Autowired
	private WsMemberService wsMemberService;

	public WsMemberCoupon get(String id) {
		return super.get(id);
	}
	
	public List<WsMemberCoupon> findList(WsMemberCoupon wsMemberCoupon) {
		return super.findList(wsMemberCoupon);
	}
	
	public Page<WsMemberCoupon> findPage(Page<WsMemberCoupon> page, WsMemberCoupon wsMemberCoupon) {
		return super.findPage(page, wsMemberCoupon);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMemberCoupon wsMemberCoupon) {
		super.save(wsMemberCoupon);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMemberCoupon wsMemberCoupon) {
		super.delete(wsMemberCoupon);
	}
	
	/**
	 * 查询可使用的优惠券
	 * @param wsMember
	 * @param totalMoney
	 * @return
	 */
	@Transactional(readOnly = false)
	public WsMemberCoupon reductionCoupon(WsMember wsMember,BigDecimal totalMoney) {
		WsMemberCoupon wsMemberCoupon=new WsMemberCoupon();
		wsMemberCoupon.setWsMember(wsMember);
		wsMemberCoupon.setState(WsConstant.COUPON_TO_USE);
		wsMemberCoupon.setConditionMoney(totalMoney);
		Page<WsMemberCoupon> page=new Page<WsMemberCoupon>();
		page.setOrderBy("a.coupon_money desc");
		wsMemberCoupon.setPage(page);
		List<WsMemberCoupon> wsMemberCouponList=super.findList(wsMemberCoupon);
		if(wsMemberCouponList!=null && wsMemberCouponList.size()>0){
			wsMemberCoupon=wsMemberCouponList.get(0);
		}
		return wsMemberCoupon;
	}
	
	@Transactional(readOnly = false)
	public void saveCoupon(String couponId,WsMember member) throws Exception {
		//查询优惠券信息
		WsActivityCoupon wsActivityCoupon=wsActivityCouponService.get(couponId);
		//校验用户积分是否可以兑换
		if(member.getScore()<wsActivityCoupon.getScore()){
			throw new Exception("用户积分不足，不能兑换");
		}
		/**
		 * 更新用户积分
		 */
		member.setScore(member.getScore()-wsActivityCoupon.getScore());
		wsMemberService.save(member);
		/**
		 * 新增用户优惠券
		 */
		WsMemberCoupon wsMemberCoupon=new WsMemberCoupon(member);
		wsMemberCoupon.setWsActivityCoupon(wsActivityCoupon);
		wsMemberCoupon.setCouponMoney(wsActivityCoupon.getFaceValue());
		wsMemberCoupon.setConditionMoney(wsActivityCoupon.getOrderMoney());
		wsMemberCoupon.setStartTime(wsActivityCoupon.getStarttime());
		wsMemberCoupon.setEndTime(wsActivityCoupon.getEndtime());
		wsMemberCoupon.setState(WsConstant.COUPON_TO_USE);
		save(wsMemberCoupon);
	}
}