package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 群发微信Entity
 * @author 大胖老师
 * @version 2017-12-31
 */
public class WxMassMsg extends DataEntity<WxMassMsg> {
	
	private static final long serialVersionUID = 1L;
	private String isToAll;		// 是否发送给所有人true,false
	private String userGroup;		// 分组号
	private String mediaId;		// 临时mediaid，有效时间三天
	private Date mediaDate;		// medua生成时间，超过三天，重新生成
	private String msgType;		// 消息类型1文本2图文3图片4音频5视频6链接
	private String materialId;		// 素材id
	private String materialTitle;		// 素材标题
	
	public WxMassMsg() {
		super();
	}

	public WxMassMsg(String id){
		super(id);
	}

	@Length(min=0, max=10, message="是否发送给所有人true,false长度必须介于 0 和 10 之间")
	public String getIsToAll() {
		return isToAll;
	}

	public void setIsToAll(String isToAll) {
		this.isToAll = isToAll;
	}
	
	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	@Length(min=0, max=100, message="临时mediaid，有效时间三天长度必须介于 0 和 100 之间")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMediaDate() {
		return mediaDate;
	}

	public void setMediaDate(Date mediaDate) {
		this.mediaDate = mediaDate;
	}
	
	@Length(min=0, max=255, message="消息类型1文本2图文3图片4音频5视频6链接长度必须介于 0 和 255 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=64, message="素材id长度必须介于 0 和 64 之间")
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	@Length(min=0, max=255, message="素材标题长度必须介于 0 和 255 之间")
	public String getMaterialTitle() {
		return materialTitle;
	}

	public void setMaterialTitle(String materialTitle) {
		this.materialTitle = materialTitle;
	}
	
}