package com.thinkgem.jeesite.modules.activity.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 优惠券活动Entity
 * @author 大胖老师
 * @version 2017-11-10
 */
public class WsActivityCouponProd extends DataEntity<WsActivityCouponProd> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;		// 产品ID
	private WsActivityCoupon wsActivityCoupon;		// 活动id 父类
	
	public WsActivityCouponProd() {
		super();
	}

	public WsActivityCouponProd(String id){
		super(id);
	}

	public WsActivityCouponProd(WsActivityCoupon wsActivityCoupon){
		this.wsActivityCoupon = wsActivityCoupon;
	}

	@Length(min=0, max=64, message="产品ID长度必须介于 0 和 64 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public WsActivityCoupon getWsActivityCoupon() {
		return wsActivityCoupon;
	}

	public void setWsActivityCoupon(WsActivityCoupon wsActivityCoupon) {
		this.wsActivityCoupon = wsActivityCoupon;
	}
	
	
}