package com.thinkgem.jeesite.modules.member.entity;


import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员资料Entity
 * @author zengyq
 * @version 2017-10-09
 */
public class WsMemberRewardLog extends DataEntity<WsMemberRewardLog> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名 父类
	private BigDecimal rewardMoney;		// 奖励金额
	private int rewardScore;		// 奖励积分
	private Date rechargeTime;		// 奖励时间
	private int score;		// 剩余积分
	private BigDecimal balance;		// 剩余金额
	private String rewardType;		// 奖励方式
	private String rewardDesc;		// 奖励描述
	private String rewardProdId;		// 分销商品ID
	private String rewardProdName;		// 分销商品名称
	
	public WsMemberRewardLog() {
		super();
	}

	public WsMemberRewardLog(String id){
		super(id);
	}

	public WsMemberRewardLog(WsMember wsMember){
		this.wsMember = wsMember;
	}

	@Length(min=1, max=64, message="用户名长度必须介于 1 和 64 之间")
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	
	@Length(min=0, max=10, message="奖励方式长度必须介于 0 和 10 之间")
	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	
	@Length(min=0, max=255, message="奖励描述长度必须介于 0 和 255 之间")
	public String getRewardDesc() {
		return rewardDesc;
	}

	public void setRewardDesc(String rewardDesc) {
		this.rewardDesc = rewardDesc;
	}
	
	@Length(min=0, max=64, message="分销商品ID长度必须介于 0 和 64 之间")
	public String getRewardProdId() {
		return rewardProdId;
	}

	public void setRewardProdId(String rewardProdId) {
		this.rewardProdId = rewardProdId;
	}
	
	@Length(min=0, max=512, message="分销商品名称长度必须介于 0 和 512 之间")
	public String getRewardProdName() {
		return rewardProdName;
	}

	public void setRewardProdName(String rewardProdName) {
		this.rewardProdName = rewardProdName;
	}

	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

	public int getRewardScore() {
		return rewardScore;
	}

	public void setRewardScore(int rewardScore) {
		this.rewardScore = rewardScore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}