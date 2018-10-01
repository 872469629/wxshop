package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 关注欢迎语Entity
 * @author 大胖老师
 * @version 2017-09-29
 */
public class WxSubscribe extends DataEntity<WxSubscribe> {
	
	private static final long serialVersionUID = 1L;
	private String msgType;		// 消息类型
	private String materialId;		// 素材ID
	private String materialTitle;		// 素材名称
	
	public WxSubscribe() {
		super();
	}

	public WxSubscribe(String id){
		super(id);
	}

	@Length(min=0, max=255, message="消息类型长度必须介于 0 和 255 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=255, message="素材ID长度必须介于 0 和 255 之间")
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	@Length(min=0, max=255, message="素材名称长度必须介于 0 和 255 之间")
	public String getMaterialTitle() {
		return materialTitle;
	}

	public void setMaterialTitle(String materialTitle) {
		this.materialTitle = materialTitle;
	}
	
}