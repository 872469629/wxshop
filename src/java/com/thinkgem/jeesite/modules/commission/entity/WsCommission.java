package com.thinkgem.jeesite.modules.commission.entity;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;

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
	private WsMember memberId;		// 购买者用户id
	private BigDecimal agent3Promotion;		// 一级代理商的推广金额
	private WsMember memberParent;		// 上一级代理商用户id
	private WsOrderItem orderItemId;		// 订单明细id
	private WsOrder orderId;		// 订单id
	private WsMember memberParentParent;		// 上上级代理商用户id
	private WsMember memberParentParentParent;		// member_parent_parent_parent
	private BigDecimal mpBalanceBefore;		// 上一级用户变化之前的余额
	private BigDecimal mppBalanceBefore;		// 上上级用户变化之前的余额
	private BigDecimal mpppBalanceBefore;		// 上上上级用户变化之前的余额
	private BigDecimal mpBalanceAfter;		// 上一级用户变化之后的余额
	private BigDecimal mppBalanceAfter;		// 上上级用户变化之后的余额
	private BigDecimal mpppBalanceAfter;		// 上上上级用户变化之后的余额
	
	private BigDecimal agent1PromotionStart;//查询条件
	private BigDecimal agent1PromotionEnd;//查询条件
	private BigDecimal agent1ConsumeStart;//查询条件
	private BigDecimal agent1ConsumeEnd;//查询条件
	
	private BigDecimal agent2PromotionStart;//查询条件
	private BigDecimal agent2PromotionEnd;//查询条件
	private BigDecimal agent2ConsumeStart;//查询条件
	private BigDecimal agent2ConsumeEnd;//查询条件
	
	private BigDecimal agent3PromotionStart;//查询条件
	private BigDecimal agent3PromotionEnd;//查询条件
	private BigDecimal agent3ConsumeStart;//查询条件
	private BigDecimal agent3ConsumeEnd;//查询条件
	
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
	public WsMember getMemberId() {
		return memberId;
	}

	public void setMemberId(WsMember memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=64, message="上一级代理商用户id长度必须介于 0 和 64 之间")
	public WsMember getMemberParent() {
		return memberParent;
	}

	public void setMemberParent(WsMember memberParent) {
		this.memberParent = memberParent;
	}
	
	@Length(min=0, max=64, message="订单明细id长度必须介于 0 和 64 之间")
	public WsOrderItem getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(WsOrderItem orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	@Length(min=0, max=64, message="上上级代理商用户id长度必须介于 0 和 64 之间")
	public WsMember getMemberParentParent() {
		return memberParentParent;
	}

	public void setMemberParentParent(WsMember memberParentParent) {
		this.memberParentParent = memberParentParent;
	}
	
	@Length(min=0, max=64, message="member_parent_parent_parent长度必须介于 0 和 64 之间")
	public WsMember getMemberParentParentParent() {
		return memberParentParentParent;
	}

	public void setMemberParentParentParent(WsMember memberParentParentParent) {
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

	public WsOrder getOrderId() {
		return orderId;
	}

	public void setOrderId(WsOrder orderId) {
		this.orderId = orderId;
	}


	public BigDecimal getMpBalanceBefore() {
		return mpBalanceBefore;
	}

	public void setMpBalanceBefore(BigDecimal mpBalanceBefore) {
		this.mpBalanceBefore = mpBalanceBefore;
	}

	public BigDecimal getMppBalanceBefore() {
		return mppBalanceBefore;
	}

	public void setMppBalanceBefore(BigDecimal mppBalanceBefore) {
		this.mppBalanceBefore = mppBalanceBefore;
	}

	public BigDecimal getMpppBalanceBefore() {
		return mpppBalanceBefore;
	}

	public void setMpppBalanceBefore(BigDecimal mpppBalanceBefore) {
		this.mpppBalanceBefore = mpppBalanceBefore;
	}

	public BigDecimal getMpBalanceAfter() {
		return mpBalanceAfter;
	}

	public void setMpBalanceAfter(BigDecimal mpBalanceAfter) {
		this.mpBalanceAfter = mpBalanceAfter;
	}

	public BigDecimal getMppBalanceAfter() {
		return mppBalanceAfter;
	}

	public void setMppBalanceAfter(BigDecimal mppBalanceAfter) {
		this.mppBalanceAfter = mppBalanceAfter;
	}

	public BigDecimal getMpppBalanceAfter() {
		return mpppBalanceAfter;
	}

	public void setMpppBalanceAfter(BigDecimal mpppBalanceAfter) {
		this.mpppBalanceAfter = mpppBalanceAfter;
	}

	public BigDecimal getAgent1ConsumeStart() {
		return agent1ConsumeStart;
	}

	public void setAgent1ConsumeStart(BigDecimal agent1ConsumeStart) {
		this.agent1ConsumeStart = agent1ConsumeStart;
	}

	public BigDecimal getAgent1ConsumeEnd() {
		return agent1ConsumeEnd;
	}

	public void setAgent1ConsumeEnd(BigDecimal agent1ConsumeEnd) {
		this.agent1ConsumeEnd = agent1ConsumeEnd;
	}

	public BigDecimal getAgent2ConsumeStart() {
		return agent2ConsumeStart;
	}

	public void setAgent2ConsumeStart(BigDecimal agent2ConsumeStart) {
		this.agent2ConsumeStart = agent2ConsumeStart;
	}

	public BigDecimal getAgent2ConsumeEnd() {
		return agent2ConsumeEnd;
	}

	public void setAgent2ConsumeEnd(BigDecimal agent2ConsumeEnd) {
		this.agent2ConsumeEnd = agent2ConsumeEnd;
	}

	public BigDecimal getAgent3ConsumeStart() {
		return agent3ConsumeStart;
	}

	public void setAgent3ConsumeStart(BigDecimal agent3ConsumeStart) {
		this.agent3ConsumeStart = agent3ConsumeStart;
	}

	public BigDecimal getAgent3ConsumeEnd() {
		return agent3ConsumeEnd;
	}

	public void setAgent3ConsumeEnd(BigDecimal agent3ConsumeEnd) {
		this.agent3ConsumeEnd = agent3ConsumeEnd;
	}

	public BigDecimal getAgent1PromotionStart() {
		return agent1PromotionStart;
	}

	public void setAgent1PromotionStart(BigDecimal agent1PromotionStart) {
		this.agent1PromotionStart = agent1PromotionStart;
	}

	public BigDecimal getAgent1PromotionEnd() {
		return agent1PromotionEnd;
	}

	public void setAgent1PromotionEnd(BigDecimal agent1PromotionEnd) {
		this.agent1PromotionEnd = agent1PromotionEnd;
	}

	public BigDecimal getAgent2PromotionStart() {
		return agent2PromotionStart;
	}

	public void setAgent2PromotionStart(BigDecimal agent2PromotionStart) {
		this.agent2PromotionStart = agent2PromotionStart;
	}

	public BigDecimal getAgent2PromotionEnd() {
		return agent2PromotionEnd;
	}

	public void setAgent2PromotionEnd(BigDecimal agent2PromotionEnd) {
		this.agent2PromotionEnd = agent2PromotionEnd;
	}

	public BigDecimal getAgent3PromotionStart() {
		return agent3PromotionStart;
	}

	public void setAgent3PromotionStart(BigDecimal agent3PromotionStart) {
		this.agent3PromotionStart = agent3PromotionStart;
	}

	public BigDecimal getAgent3PromotionEnd() {
		return agent3PromotionEnd;
	}

	public void setAgent3PromotionEnd(BigDecimal agent3PromotionEnd) {
		this.agent3PromotionEnd = agent3PromotionEnd;
	}

}