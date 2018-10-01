package com.thinkgem.jeesite.modules.config.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 图片管理Entity
 * @author water
 * @version 2017-10-01
 */
public class WsAd extends DataEntity<WsAd> {
	
	private static final long serialVersionUID = 1L;
	private String imgType;		// 图片类型
	private String imgTitle;		// 图片标题
	private String imgUrl;		// 图片地址
	private String imgHref;		// 图片跳转地址
	private int priority;		// 优先级
	private int clickNum;		// 点击次数
	private String urlType; //关联类型1产品2活动3品牌4自定义
	private String imgHrefTitle; 
	public WsAd() {
		super();
	}

	public WsAd(String id){
		super(id);
	}

	@Length(min=0, max=10, message="图片类型长度必须介于 0 和 10 之间")
	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	
	@Length(min=0, max=100, message="图片标题长度必须介于 0 和 100 之间")
	public String getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}
	
	@Length(min=0, max=100, message="图片地址长度必须介于 0 和 100 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Length(min=0, max=100, message="图片跳转地址长度必须介于 0 和 100 之间")
	public String getImgHref() {
		return imgHref;
	}

	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
	}
	
	

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getImgHrefTitle() {
		return imgHrefTitle;
	}

	public void setImgHrefTitle(String imgHrefTitle) {
		this.imgHrefTitle = imgHrefTitle;
	}

	
	
	
}