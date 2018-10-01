package com.thinkgem.jeesite.modules.member.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;

/**
 * 会员资料Entity
 * @author zengyq
 * @version 2017-10-09
 */
public class WsMemberConsumeLog extends DataEntity<WsMemberConsumeLog> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名 父类
	private WsOrder wsOrder;		// 订单id
	private String consumeScore;		// 订单消费积分
	private String consumeMoney;		// 订单消费金额
	private Date consumeTime;		// 消费时间
	private String score;		// 剩余积分
	private String balance;		// 剩余金额
	private String payment;		// 付款方式
	
	public WsMemberConsumeLog() {
		super();
	}

	public WsMemberConsumeLog(String id){
		super(id);
	}

	public WsMemberConsumeLog(WsMember wsMember){
		this.wsMember = wsMember;
	}

	@Length(min=1, max=64, message="用户名长度必须介于 1 和 64 之间")
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}
	
	
	
	public WsOrder getWsOrder() {
		return wsOrder;
	}

	public void setWsOrder(WsOrder wsOrder) {
		this.wsOrder = wsOrder;
	}
	

	@Length(min=0, max=11, message="订单消费积分长度必须介于 0 和 11 之间")
	public String getConsumeScore() {
		return consumeScore;
	}

	public void setConsumeScore(String consumeScore) {
		this.consumeScore = consumeScore;
	}
	
	@Length(min=0, max=11, message="订单消费金额长度必须介于 0 和 11 之间")
	public String getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(String consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
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