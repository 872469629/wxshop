package com.thinkgem.jeesite.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 底部链接Entity
 * @author HF
 * @version 2017-08-09
 */
public class WsLink extends DataEntity<WsLink> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private WsLinkCategory linkCategoryId;		// 分类
	private String linkHref;		// 链接地址
	private String state;		// 状态
	
	public WsLink() {
		super();
	}

	public WsLink(String id){
		super(id);
	}

	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	public WsLinkCategory getLinkCategoryId() {
		return linkCategoryId;
	}

	public void setLinkCategoryId(WsLinkCategory linkCategoryId) {
		this.linkCategoryId = linkCategoryId;
	}

	@Length(min=0, max=100, message="链接地址长度必须介于 0 和 100 之间")
	public String getLinkHref() {
		return linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}