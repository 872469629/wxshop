package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 图片素材Entity
 * @author 大胖老师
 * @version 2017-09-28
 */
public class WxMaterialPhoto extends DataEntity<WxMaterialPhoto> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 图片标题
	private String imgUrl;		// 图片URL
	private String mediaId;		// 图片media_id
	
	public WxMaterialPhoto() {
		super();
	}

	public WxMaterialPhoto(String id){
		super(id);
	}

	@Length(min=0, max=255, message="图片标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="图片URL长度必须介于 0 和 255 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Length(min=0, max=255, message="图片media_id长度必须介于 0 和 255 之间")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}