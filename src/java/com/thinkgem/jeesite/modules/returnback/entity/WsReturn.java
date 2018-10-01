package com.thinkgem.jeesite.modules.returnback.entity;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;

/**
 * 退货管理Entity
 * @author 大胖老师
 * @version 2017-12-02
 */
public class WsReturn extends DataEntity<WsReturn> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;
	private String orderSn;		// 订单号
	private String refundSn;
	private String refundId;
	private String state;		// 1退货待审核2退货待退款3退货成功退货4退货取消5拒绝
	private String reason;		// 退货原因
	private WsMemberCoupon wsMemberCoupon;
	private BigDecimal orderAmount;		// 订单金额
	private BigDecimal returnAmount;		// 实际退货金额
	private int returnScore;		// 退货扣除积分
	private List<WsReturnItem> wsReturnItemList = Lists.newArrayList();		// 子表列表
	
	public WsReturn() {
		super();
	}

	public WsReturn(String id){
		super(id);
	}

	@Length(min=0, max=255, message="订单号长度必须介于 0 和 255 之间")
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@Length(min=0, max=255, message="1退货待审核2退货待退款3退货成功退货4退货取消5拒绝长度必须介于 0 和 255 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public List<WsReturnItem> getWsReturnItemList() {
		return wsReturnItemList;
	}

	public void setWsReturnItemList(List<WsReturnItem> wsReturnItemList) {
		this.wsReturnItemList = wsReturnItemList;
	}

	public String getRefundSn() {
		return refundSn;
	}

	public void setRefundSn(String refundSn) {
		this.refundSn = refundSn;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public int getReturnScore() {
		return returnScore;
	}

	public void setReturnScore(int returnScore) {
		this.returnScore = returnScore;
	}

	public WsMemberCoupon getWsMemberCoupon() {
		return wsMemberCoupon;
	}

	public void setWsMemberCoupon(WsMemberCoupon wsMemberCoupon) {
		this.wsMemberCoupon = wsMemberCoupon;
	}

	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}

}