package com.thinkgem.jeesite.modules.member.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;

/**
 * 用户收藏记录Entity
 * @author 大胖老师
 * @version 2017-12-09
 */
public class WsMemberCollectLog extends DataEntity<WsMemberCollectLog> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名
	private WsProduct wsProduct;		// 产品id
	private Date collectDate;		// 收藏日期
	
	public WsMemberCollectLog() {
		super();
	}

	public WsMemberCollectLog(String id){
		super(id);
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
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
	
	
	
}