package com.thinkgem.jeesite.modules.commission.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 分销明细Entity
 * @author 分销系统开发者
 * @version 2018-10-03
 */
public class WsCommission extends DataEntity<WsCommission> {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal agent1Consume;		// 一级消费返利金额
	private BigDecimal agent1ConsumeScale;		// 一级消费返利金额比例
	private BigDecimal agent2Consume;		// 二级消费返利金额
	private BigDecimal agent2ConsumeScale;		// 一级消费返利金额比例
	private BigDecimal agent3Consume;		// 三级消费返利金额
	private BigDecimal agent3ConsumeScale;		// 一级消费返利金额比例
	private String status;		// status
	private BigDecimal agent1Promotion;		// 一级代理商的推广金额
	private BigDecimal agent2Promotion;		// 一级代理商的推广金额
	private String memberId;		// 购买者用户id
	private BigDecimal agent3Promotion;		// 一级代理商的推广金额
	private String memberParent;		// 上一级代理商用户id
	private String orderItemId;		// 订单明细id
	private String orderId;		// 订单id
	private String memberParentParent;		// 上上级代理商用户id
	private String memberParentParentParent;		// member_parent_parent_parent
	
	public WsCommission() {
		super();
	}

	public WsCommission(String id){
		super(id);
	}

	
	@Length(min=0, max=11, message="status长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="购买者用户id长度必须介于 0 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=64, message="上一级代理商用户id长度必须介于 0 和 64 之间")
	public String getMemberParent() {
		return memberParent;
	}

	public void setMemberParent(String memberParent) {
		this.memberParent = memberParent;
	}
	
	@Length(min=0, max=64, message="订单明细id长度必须介于 0 和 64 之间")
	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	@Length(min=0, max=64, message="上上级代理商用户id长度必须介于 0 和 64 之间")
	public String getMemberParentParent() {
		return memberParentParent;
	}

	public void setMemberParentParent(String memberParentParent) {
		this.memberParentParent = memberParentParent;
	}
	
	@Length(min=0, max=64, message="member_parent_parent_parent长度必须介于 0 和 64 之间")
	public String getMemberParentParentParent() {
		return memberParentParentParent;
	}

	public void setMemberParentParentParent(String memberParentParentParent) {
		this.memberParentParentParent = memberParentParentParent;
	}

	public BigDecimal getAgent1Consume() {
		return agent1Consume;
	}

	public void setAgent1Consume(BigDecimal agent1Consume) {
		this.agent1Consume = agent1Consume;
	}

	public BigDecimal getAgent1ConsumeScale() {
		return agent1ConsumeScale;
	}

	public void setAgent1ConsumeScale(BigDecimal agent1ConsumeScale) {
		this.agent1ConsumeScale = agent1ConsumeScale;
	}

	public BigDecimal getAgent2Consume() {
		return agent2Consume;
	}

	public void setAgent2Consume(BigDecimal agent2Consume) {
		this.agent2Consume = agent2Consume;
	}

	public BigDecimal getAgent2ConsumeScale() {
		return agent2ConsumeScale;
	}

	public void setAgent2ConsumeScale(BigDecimal agent2ConsumeScale) {
		this.agent2ConsumeScale = agent2ConsumeScale;
	}

	public BigDecimal getAgent3Consume() {
		return agent3Consume;
	}

	public void setAgent3Consume(BigDecimal agent3Consume) {
		this.agent3Consume = agent3Consume;
	}

	public BigDecimal getAgent3ConsumeScale() {
		return agent3ConsumeScale;
	}

	public void setAgent3ConsumeScale(BigDecimal agent3ConsumeScale) {
		this.agent3ConsumeScale = agent3ConsumeScale;
	}

	public BigDecimal getAgent1Promotion() {
		return agent1Promotion;
	}

	public void setAgent1Promotion(BigDecimal agent1Promotion) {
		this.agent1Promotion = agent1Promotion;
	}

	public BigDecimal getAgent2Promotion() {
		return agent2Promotion;
	}

	public void setAgent2Promotion(BigDecimal agent2Promotion) {
		this.agent2Promotion = agent2Promotion;
	}

	public BigDecimal getAgent3Promotion() {
		return agent3Promotion;
	}

	public void setAgent3Promotion(BigDecimal agent3Promotion) {
		this.agent3Promotion = agent3Promotion;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}