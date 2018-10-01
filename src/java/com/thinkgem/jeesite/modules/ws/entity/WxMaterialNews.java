package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 图文素材Entity
 * @author 大胖老师
 * @version 2017-09-29
 */
public class WxMaterialNews extends DataEntity<WxMaterialNews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 图文标题
	
	public WxMaterialNews() {
		super();
	}

	public WxMaterialNews(String id){
		super(id);
	}

	@Length(min=1, max=255, message="图文标题长度必须介于 1 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}