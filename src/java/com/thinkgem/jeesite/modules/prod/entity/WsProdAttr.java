package com.thinkgem.jeesite.modules.prod.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品属性Entity
 * @author 大胖老师
 * @version 2017-10-02
 */
public class WsProdAttr extends DataEntity<WsProdAttr> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品id
	private String attrbuteCode;		// 属性编号
	private String attrbuteName;		// 属性名
	private String attrbuteDesc;		// 属性描述
	
	public WsProdAttr() {
		super();
	}

	public WsProdAttr(String id){
		super(id);
	}

	@Length(min=0, max=64, message="产品id长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=50, message="属性编号长度必须介于 0 和 50 之间")
	public String getAttrbuteCode() {
		return attrbuteCode;
	}

	public void setAttrbuteCode(String attrbuteCode) {
		this.attrbuteCode = attrbuteCode;
	}
	
	@Length(min=0, max=200, message="属性名长度必须介于 0 和 200 之间")
	public String getAttrbuteName() {
		return attrbuteName;
	}

	public void setAttrbuteName(String attrbuteName) {
		this.attrbuteName = attrbuteName;
	}
	
	@Length(min=0, max=200, message="属性描述长度必须介于 0 和 200 之间")
	public String getAttrbuteDesc() {
		return attrbuteDesc;
	}

	public void setAttrbuteDesc(String attrbuteDesc) {
		this.attrbuteDesc = attrbuteDesc;
	}
	
}