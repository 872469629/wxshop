package com.thinkgem.jeesite.modules.member.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员资料Entity
 * @author zengyq
 * @version 2017-10-09
 */
public class WsMemberAttr extends DataEntity<WsMemberAttr> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名 父类
	private String attrCode;		// 用户属性
	private String attrName;		// 用户属性名
	private String attrValue;		// 用户属性值
	private String attrDesc;		// 用户属性描述
	private String state;		// 用户属性状态
	
	public WsMemberAttr() {
		super();
	}

	public WsMemberAttr(String id){
		super(id);
	}

	public WsMemberAttr(WsMember wsMember){
		this.wsMember = wsMember;
	}

	@Length(min=0, max=64, message="用户名长度必须介于 0 和 64 之间")
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}
	
	@Length(min=0, max=30, message="用户属性长度必须介于 0 和 30 之间")
	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}
	
	@Length(min=0, max=50, message="用户属性名长度必须介于 0 和 50 之间")
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	
	@Length(min=0, max=50, message="用户属性值长度必须介于 0 和 50 之间")
	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	
	@Length(min=0, max=200, message="用户属性描述长度必须介于 0 和 200 之间")
	public String getAttrDesc() {
		return attrDesc;
	}

	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}
	
	@Length(min=0, max=1, message="用户属性状态长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}