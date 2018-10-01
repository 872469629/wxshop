package com.thinkgem.jeesite.modules.member.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户搜索记录管理Entity
 * @author 大胖老师
 * @version 2017-12-09
 */
public class WsMemberSearchLog extends DataEntity<WsMemberSearchLog> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名
	private String searchLable;		// 搜索记录
	private Date lastSearchDate;		// 最近搜索日期
	private int searchNum;		// 搜索次数
	
	public WsMemberSearchLog() {
		super();
	}

	public WsMemberSearchLog(String id){
		super(id);
	}

	@NotNull(message="用户名不能为空")
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}
	
	@Length(min=0, max=2000, message="搜索记录长度必须介于 0 和 2000 之间")
	public String getSearchLable() {
		return searchLable;
	}

	public void setSearchLable(String searchLable) {
		this.searchLable = searchLable;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastSearchDate() {
		return lastSearchDate;
	}

	public void setLastSearchDate(Date lastSearchDate) {
		this.lastSearchDate = lastSearchDate;
	}

	public int getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(int searchNum) {
		this.searchNum = searchNum;
	}
	
	
	
}