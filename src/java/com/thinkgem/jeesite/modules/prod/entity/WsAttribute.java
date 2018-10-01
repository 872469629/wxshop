package com.thinkgem.jeesite.modules.prod.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品属性Entity
 * @author water
 * @version 2017-08-10
 */
/**
 * @author Administrator
 *
 */
public class WsAttribute extends DataEntity<WsAttribute> {
	
	private static final long serialVersionUID = 1L;
	private WsProdCategory prodCategoryId;		// 所属分类
	private String attrName;		// 属性名称
	private String attrType;		// 属性类型
	private String inputType;		// 输入类型
	private String isSearch;		// 是否搜索
	private String isRequire;		// 是否必填
	private String state;		// 状态
	private String sort;		// 排序
	private List<WsAttrivalue> wsAttrivalueList = Lists.newArrayList();		// 子表列表
	
	public WsAttribute() {
		super();
	}

	public WsAttribute(String id){
		super(id);
	}

	
	
	public WsProdCategory getProdCategoryId() {
		return prodCategoryId;
	}

	public void setProdCategoryId(WsProdCategory prodCategoryId) {
		this.prodCategoryId = prodCategoryId;
	}

	@Length(min=0, max=128, message="属性名称长度必须介于 0 和 128 之间")
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	
	@Length(min=0, max=10, message="属性类型长度必须介于 0 和 10 之间")
	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	
	@Length(min=0, max=10, message="输入类型长度必须介于 0 和 10 之间")
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	
	@Length(min=0, max=10, message="是否搜索长度必须介于 0 和 10 之间")
	public String getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}
	
	@Length(min=0, max=10, message="是否必填长度必须介于 0 和 10 之间")
	public String getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=10, message="排序长度必须介于 0 和 10 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public List<WsAttrivalue> getWsAttrivalueList() {
		return wsAttrivalueList;
	}

	public void setWsAttrivalueList(List<WsAttrivalue> wsAttrivalueList) {
		this.wsAttrivalueList = wsAttrivalueList;
	}
}