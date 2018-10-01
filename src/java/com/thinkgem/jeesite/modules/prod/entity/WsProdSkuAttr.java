package com.thinkgem.jeesite.modules.prod.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品sku属性Entity
 * @author 大胖老师
 * @version 2017-10-02
 */
public class WsProdSkuAttr extends DataEntity<WsProdSkuAttr> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;        //产品id
	private String attrbuteId;		// 属性id
	private String attrbuteName;		// 属性名=比如内存
	private String attrbuteValue;		// 属性值ID
	private String attrbuteValueName;		// 属性值名称=比如16
	
	public WsProdSkuAttr() {
		super();
	}

	public WsProdSkuAttr(String id){
		super(id);
	}
	
	@Length(min=0, max=30, message="属性id长度必须介于 0 和 30 之间")
	public String getAttrbuteId() {
		return attrbuteId;
	}

	public void setAttrbuteId(String attrbuteId) {
		this.attrbuteId = attrbuteId;
	}
	
	@Length(min=0, max=100, message="属性名=比如内存长度必须介于 0 和 100 之间")
	public String getAttrbuteName() {
		return attrbuteName;
	}

	public void setAttrbuteName(String attrbuteName) {
		this.attrbuteName = attrbuteName;
	}
	
	@Length(min=0, max=100, message="属性值ID长度必须介于 0 和 100 之间")
	public String getAttrbuteValue() {
		return attrbuteValue;
	}

	public void setAttrbuteValue(String attrbuteValue) {
		this.attrbuteValue = attrbuteValue;
	}
	
	@Length(min=0, max=100, message="属性值名称=比如16长度必须介于 0 和 100 之间")
	public String getAttrbuteValueName() {
		return attrbuteValueName;
	}

	public void setAttrbuteValueName(String attrbuteValueName) {
		this.attrbuteValueName = attrbuteValueName;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

}