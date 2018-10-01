package com.thinkgem.jeesite.modules.config.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公告管理Entity
 * @author water
 * @version 2018-05-10
 */
public class WsAnnouncement extends DataEntity<WsAnnouncement> {
	
	private static final long serialVersionUID = 1L;
	private String annoucetype;		// 公告类别
	private String title;		// 标题
	private String isShow;		// 是否显示
	private String content;		// 内容
	
	public WsAnnouncement() {
		super();
	}

	public WsAnnouncement(String id){
		super(id);
	}

	@Length(min=0, max=50, message="公告类别长度必须介于 0 和 50 之间")
	public String getAnnoucetype() {
		return annoucetype;
	}

	public void setAnnoucetype(String annoucetype) {
		this.annoucetype = annoucetype;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=5, message="是否显示长度必须介于 0 和 5 之间")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@Length(min=0, max=1024, message="内容长度必须介于 0 和 1024 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}