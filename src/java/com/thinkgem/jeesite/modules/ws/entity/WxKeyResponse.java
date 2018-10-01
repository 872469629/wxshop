package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 关键字回复Entity
 * @author 大胖老师
 * @version 2017-09-29
 */
public class WxKeyResponse extends DataEntity<WxKeyResponse> {
	
	private static final long serialVersionUID = 1L;
	private String keyword;		// 关键字
	private String msgType;		// 消息类型
	private String materialId;		// 素材id
	private String materialTitle;		// 素材标题
	
	public WxKeyResponse() {
		super();
	}

	public WxKeyResponse(String id){
		super(id);
	}

	@Length(min=0, max=255, message="关键字长度必须介于 0 和 255 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Length(min=0, max=255, message="消息类型长度必须介于 0 和 255 之间")
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