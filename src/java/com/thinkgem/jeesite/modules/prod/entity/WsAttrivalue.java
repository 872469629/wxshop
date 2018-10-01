package com.thinkgem.jeesite.modules.prod.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品属性Entity
 * @author water
 * @version 2017-08-10
 */
public class WsAttrivalue extends DataEntity<WsAttrivalue> {
	
	private static final long serialVersionUID = 1L;
	private WsAttribute attributeId;		// 属性名 父类
	private String attrvalueValue;		// 属性值名称
	private String state;		// 状态
	private String sort;		// 排序
	
	public WsAttrivalue() {
		super();
	}

	public WsAttrivalue(String id){
		super(id);
	}

	public WsAttrivalue(WsAttribute attributeId){
		this.attributeId = attributeId;
	}

	@Length(min=0, max=64, message="属性名长度必须介于 0 和 64 之间")
	public WsAttribute getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(WsAttribute attributeId) {
		this.attributeId = attributeId;
	}
	
	@Length(min=0, max=256, message="属性值名称长度必须介于 0 和 256 之间")
	public String getAttrvalueValue() {
		return attrvalueValue;
	}

	public void setAttrvalueValue(String attrvalueValue) {
		this.attrvalueValue = attrvalueValue;
	}
	
	@Length(min=0, max=255, message="状态长度必须介于 0 和 255 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=64, message="排序长度必须介于 0 和 64 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}