package com.thinkgem.jeesite.modules.member.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员资料Entity
 * @author zengyq
 * @version 2017-10-09
 */
public class WsMemberRechargeLog extends DataEntity<WsMemberRechargeLog> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名 父类
	private String rechargeMoney;		// 充值金额
	private Date rechargeTime;		// 充值时间
	private String score;		// 剩余积分
	private String balance;		// 剩余金额
	private String payment;		// 付款方式
	
	public WsMemberRechargeLog() {
		super();
	}

	public WsMemberRechargeLog(String id){
		super(id);
	}

	public WsMemberRechargeLog(WsMember wsMember){
		this.wsMember = wsMember;
	}

	@Length(min=1, max=64, message="用户名长度必须介于 1 和 64 之间")
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}
	
	@Length(min=0, max=11, message="充值金额长度必须介于 0 和 11 之间")
	public String getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(String rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	
	@Length(min=0, max=11, message="剩余积分长度必须介于 0 和 11 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@Length(min=0, max=11, message="剩余金额长度必须介于 0 和 11 之间")
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	@Length(min=0, max=255, message="付款方式长度必须介于 0 和 255 之间")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
}