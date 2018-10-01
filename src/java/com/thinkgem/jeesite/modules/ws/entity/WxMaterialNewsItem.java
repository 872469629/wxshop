package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 图文素材明细Entity
 * @author 大胖老师
 * @version 2017-10-29
 */
public class WxMaterialNewsItem extends DataEntity<WxMaterialNewsItem> {
	
	private static final long serialVersionUID = 1L;
	private String newsId;		// 图片ID
	private String newsTitle;		// 图片标题
	private String newType;		// 图文类型
	private String author;		// 作者
	private String title;		// 标题
	private String description;		// 消息摘要描述
	private String imageUrl;		// 封面图片地址
	private String content;		// 图文内容
	private String url;		// 外部URL
	private Integer orders;		// 图文顺序
	
	public WxMaterialNewsItem() {
		super();
	}

	public WxMaterialNewsItem(String id){
		super(id);
	}

	@Length(min=0, max=32, message="图片ID长度必须介于 0 和 32 之间")
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	@Length(min=0, max=255, message="图文类型长度必须介于 0 和 255 之间")
	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}
	
	@Length(min=0, max=255, message="作者长度必须介于 0 和 255 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="消息摘要描述长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="封面图片地址长度必须介于 0 和 255 之间")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="外部URL长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	
	
	
}