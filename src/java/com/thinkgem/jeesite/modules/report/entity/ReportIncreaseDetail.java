package com.thinkgem.jeesite.modules.report.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 每日新增数据明细Entity
 * @author 大胖老师
 * @version 2018-01-01
 */
public class ReportIncreaseDetail extends DataEntity<ReportIncreaseDetail> {
	
	private static final long serialVersionUID = 1L;
	private String countDate;		// 统计日期
	private int addUserCount;		// 新增用户数量
	private BigDecimal selMoney;		// 新增收入
	private int orderCount;		// 订单数量
	private BigDecimal backMoney;		// 退款金额
	private int visitMemberCount;		// 访问用户
	
	public ReportIncreaseDetail() {
		super();
	}

	public ReportIncreaseDetail(String id){
		super(id);
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public int getAddUserCount() {
		return addUserCount;
	}

	public void setAddUserCount(int addUserCount) {
		this.addUserCount = addUserCount;
	}

	public BigDecimal getSelMoney() {
		return selMoney;
	}

	public void setSelMoney(BigDecimal selMoney) {
		this.selMoney = selMoney;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(BigDecimal backMoney) {
		this.backMoney = backMoney;
	}

	public int getVisitMemberCount() {
		return visitMemberCount;
	}

	public void setVisitMemberCount(int visitMemberCount) {
		this.visitMemberCount = visitMemberCount;
	}

	
	
}