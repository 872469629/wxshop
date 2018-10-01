package com.thinkgem.jeesite.modules.member.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;

/**
 * 用户访问记录Entity
 * @author 大胖老师
 * @version 2017-12-09
 */
public class WsMemberVisitLog extends DataEntity<WsMemberVisitLog> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名
	private WsProduct wsProduct;		// 产品
	private Date lastVisitDate;		// 最近搜索日期
	private int visitNum;		// 访问次数
	
	public WsMemberVisitLog() {
		super();
	}

	public WsMemberVisitLog(String id){
		super(id);
	}
	
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}

	public WsProduct getWsProduct() {
		return wsProduct;
	}

	public void setWsProduct(WsProduct wsProduct) {
		this.wsProduct = wsProduct;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastVisitDate() {
		return lastVisitDate;
	}

	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}
	
	
	
}