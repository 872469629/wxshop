package com.thinkgem.jeesite.modules.prod.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 品牌Entity
 * @author water
 * @version 2017-08-08
 */
public class WsBrand extends DataEntity<WsBrand> {
	
	private static final long serialVersionUID = 1L;
	private String cnname;		// 中文名称
	private String enname;		// 英文名称
	private String description;		// 描述
	private String logo;		// logo
	private String state;		// 状态
	private String websiteurl;		// 官网地址
	private String brandstory;		// 品牌故事
	
	public WsBrand() {
		super();
	}

	public WsBrand(String id){
		super(id);
	}

	@Length(min=0, max=64, message="中文名称长度必须介于 0 和 64 之间")
	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	
	@Length(min=0, max=64, message="英文名称长度必须介于 0 和 64 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	
	@Length(min=0, max=2500, message="描述长度必须介于 0 和 2500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="logo长度必须介于 0 和 255 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=255, message="官网地址长度必须介于 0 和 255 之间")
	public String getWebsiteurl() {
		return websiteurl;
	}

	public void setWebsiteurl(String websiteurl) {
		this.websiteurl = websiteurl;
	}
	
	@Length(min=0, max=2500, message="品牌故事长度必须介于 0 和 2500 之间")
	public String getBrandstory() {
		return brandstory;
	}

	public void setBrandstory(String brandstory) {
		this.brandstory = brandstory;
	}
	
}