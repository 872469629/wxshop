package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 音频素材Entity
 * @author 大胖老师
 * @version 2017-09-29
 */
public class WxMaterialAudio extends DataEntity<WxMaterialAudio> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 音频名称
	private String audioUrl;		// 音频URL
	private String mediaId;		// 音频media_id
	
	public WxMaterialAudio() {
		super();
	}

	public WxMaterialAudio(String id){
		super(id);
	}

	@Length(min=0, max=255, message="音频名称长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="音频URL长度必须介于 0 和 255 之间")
	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
	
	@Length(min=0, max=255, message="音频media_id长度必须介于 0 和 255 之间")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}