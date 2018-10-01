package com.thinkgem.jeesite.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 链接类型表Entity
 * @author HF
 * @version 2017-08-08
 */
public class WsLinkCategory extends DataEntity<WsLinkCategory> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String state;		// 0失效1有效
	
	public WsLinkCategory() {
		super();
	}

	public WsLinkCategory(String id){
		super(id);
	}

	@Length(min=0, max=64, message="name长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=10, message="0失效1有效长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}