package com.thinkgem.jeesite.modules.withdrawal.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.member.entity.WsMember;

/**
 * 提现Entity
 * @author 分销系统开发者
 * @version 2018-10-07
 */
public class WsWithdrawal extends DataEntity<WsWithdrawal> {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal amount;		// 提取金额
	private BigDecimal availableAmt;		// 当前金额
	private Date processDate;		// 处理时间
	private String status;		// 0.提现中，1.成功，2.无效
	private WsMember memberId;		// member_id
	
	public WsWithdrawal() {
		super();
	}

	public WsWithdrawal(String id){
		super(id);
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAvailableAmt() {
		return availableAmt;
	}

	public void setAvailableAmt(BigDecimal availableAmt) {
		this.availableAmt = availableAmt;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public WsMember getMemberId() {
		return memberId;
	}

	public void setMemberId(WsMember memberId) {
		this.memberId = memberId;
	}
	
}