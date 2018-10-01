package com.thinkgem.jeesite.modules.report.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户数据统计Entity
 * @author 大胖老师
 * @version 2017-11-06
 */
public class ReportUserNum extends DataEntity<ReportUserNum> {
	
	private static final long serialVersionUID = 1L;
	private String countDate;		// 统计日期
	private int userCount;		// 用户数量
	private int addUserCount;		// 新增用户数量
	private int selUserCount;		// 消费用户数量
	private int visitUserCount;		// 访问人数
	private String mrankNanme;		// 用户等级
	
	public ReportUserNum() {
		super();
	}

	public ReportUserNum(String id){
		super(id);
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getAddUserCount() {
		return addUserCount;
	}

	public void setAddUserCount(int addUserCount) {
		this.addUserCount = addUserCount;
	}

	public int getSelUserCount() {
		return selUserCount;
	}

	public void setSelUserCount(int selUserCount) {
		this.selUserCount = selUserCount;
	}

	public int getVisitUserCount() {
		return visitUserCount;
	}

	public void setVisitUserCount(int visitUserCount) {
		this.visitUserCount = visitUserCount;
	}

	public String getMrankNanme() {
		return mrankNanme;
	}

	public void setMrankNanme(String mrankNanme) {
		this.mrankNanme = mrankNanme;
	}
	
	
	
	
}