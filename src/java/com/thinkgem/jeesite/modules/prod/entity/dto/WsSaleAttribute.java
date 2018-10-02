package com.thinkgem.jeesite.modules.prod.entity.dto;

import java.util.List;

/**
 * 销售属性
 *
 */
public class WsSaleAttribute {

	private String attrbuteId;

	private String attrbuteName;

	private String prodId;

	private List<ValueAttrbute> valueAttrbutes;

	public String getAttrbuteId() {
		return attrbuteId;
	}

	public void setAttrbuteId(String attrbuteId) {
		this.attrbuteId = attrbuteId;
	}

	public String getAttrbuteName() {
		return attrbuteName;
	}

	public void setAttrbuteName(String attrbuteName) {
		this.attrbuteName = attrbuteName;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public List<ValueAttrbute> getValueAttrbutes() {
		return valueAttrbutes;
	}

	public void setValueAttrbutes(List<ValueAttrbute> valueAttrbutes) {
		this.valueAttrbutes = valueAttrbutes;
	}

}
