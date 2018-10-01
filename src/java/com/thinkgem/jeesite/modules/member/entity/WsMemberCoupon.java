package com.thinkgem.jeesite.modules.member.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCoupon;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;

/**
 * 会员资料Entity
 * @author zengyq
 * @version 2017-10-09
 */
public class WsMemberCoupon extends DataEntity<WsMemberCoupon> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户ID 父类
	private WsActivityCoupon wsActivityCoupon;
	private BigDecimal couponMoney;		// 优惠金额
	private BigDecimal conditionMoney;		// 满足多少金额优惠
	private Date startTime;		// 起始时间
	private Date endTime;		// 结束时间
	private String state;		// 状态
	private Date useTime;		// 支付时间
	private WsOrder wsOrder;		// 订单ID
	
	public WsMemberCoupon() {
		super();
	}

	public WsMemberCoupon(String id){
		super(id);
	}

	public WsMemberCoupon(WsMember wsMember){
		this.wsMember = wsMember;
	}

	@Length(min=1, max=64, message="用户ID长度必须介于 1 和 64 之间")
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public WsOrder getWsOrder() {
		return wsOrder;
	}

	public void setWsOrder(WsOrder wsOrder) {
		this.wsOrder = wsOrder;
	}

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public BigDecimal getConditionMoney() {
		return conditionMoney;
	}

	public void setConditionMoney(BigDecimal conditionMoney) {
		this.conditionMoney = conditionMoney;
	}

	public WsActivityCoupon getWsActivityCoupon() {
		return wsActivityCoupon;
	}

	public void setWsActivityCoupon(WsActivityCoupon wsActivityCoupon) {
		this.wsActivityCoupon = wsActivityCoupon;
	}
	
	
}