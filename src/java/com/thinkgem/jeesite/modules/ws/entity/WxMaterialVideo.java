package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 视频素材Entity
 * @author 大胖老师
 * @version 2017-09-29
 */
public class WxMaterialVideo extends DataEntity<WxMaterialVideo> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 视频标题
	private String videoUrl;		// 视频
	private String mediaId;		// 视频media_id
	private String description;
	public WxMaterialVideo() {
		super();
	}

	public WxMaterialVideo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="视频标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="视频长度必须介于 0 和 255 之间")
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	@Length(min=0, max=255, message="视频media_id长度必须介于 0 和 255 之间")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}