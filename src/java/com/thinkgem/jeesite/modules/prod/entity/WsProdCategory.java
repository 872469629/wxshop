package com.thinkgem.jeesite.modules.prod.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 产品分类Entity
 * @author water
 * @version 2017-08-11
 */
public class WsProdCategory extends TreeEntity<WsProdCategory> {
	
	private static final long serialVersionUID = 1L;
	private boolean isParent;		//
	private WsProdCategory parent;		// 父类
	private String parentIds;		// 所有父类
	private String name;		// 分类名称
	private String title;		// 分类描述
	private String imageUrl;		// 图片地址
	
	public WsProdCategory() {
		super();
	}

	public WsProdCategory(String id){
		super(id);
	}

	@JsonBackReference
	public WsProdCategory getParent() {
		return parent;
	}

	public void setParent(WsProdCategory parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="所有父类长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=100, message="分类名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=500, message="分类描述长度必须介于 0 和 500 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="图片地址长度必须介于 0 和 255 之间")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	

	
	
	
	
}