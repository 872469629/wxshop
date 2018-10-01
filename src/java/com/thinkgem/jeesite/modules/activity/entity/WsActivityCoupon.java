package com.thinkgem.jeesite.modules.activity.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 优惠券活动Entity
 * @author 大胖老师
 * @version 2017-11-10
 */
public class WsActivityCoupon extends DataEntity<WsActivityCoupon> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 优惠券名称
	private String couponContent;		// 优惠券详细描述
	private BigDecimal orderMoney;		// 订单金额满足多少才能使用
	private BigDecimal faceValue;		// 面值
	private Integer countNum;		// 优惠券总量
	private Integer surplusNum;		// 已发放数量
	private String prodType;		// 产品类型1全场通用2指定商品
	private int score;       //兑换积分
	private Date endtime;		// 活动结束时间
	private Date starttime;		// 活动开始时间
	private String state;		// 0失效1有效=活动的状态，可以手动立即停止活动也可以等活动时间到状态自动变为失效
	private List<WsActivityCouponProd> wsActivityCouponProdList = Lists.newArrayList();		// 子表列表
	private String prodRelation;
	
	public WsActivityCoupon() {
		super();
	}

	public WsActivityCoupon(String id){
		super(id);
	}

	@Length(min=0, max=100, message="优惠券名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCouponContent() {
		return couponContent;
	}

	public void setCouponContent(String couponContent) {
		this.couponContent = couponContent;
	}
	
	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
	
	public Integer getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}
	
	@Length(min=0, max=10, message="产品类型1全场通用2指定商品长度必须介于 0 和 10 之间")
	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	
	@Length(min=0, max=10, message="0失效1有效=活动的状态，可以手动立即停止活动也可以等活动时间到状态自动变为失效长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public List<WsActivityCouponProd> getWsActivityCouponProdList() {
		return wsActivityCouponProdList;
	}

	public void setWsActivityCouponProdList(List<WsActivityCouponProd> wsActivityCouponProdList) {
		this.wsActivityCouponProdList = wsActivityCouponProdList;
	}

	public String getProdRelation() {
		return prodRelation;
	}

	public void setProdRelation(String prodRelation) {
		this.prodRelation = prodRelation;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

	
	
}