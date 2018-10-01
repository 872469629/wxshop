package com.thinkgem.jeesite.modules.member.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;

/**
 * 系统消息Entity
 * @author 大胖老师
 * @version 2017-10-09
 */
public class WsMessage extends DataEntity<WsMessage> {
	
	private static final long serialVersionUID = 1L;
	private String msgType;		// 接收人阅读时间
	private Date sendDate;		// 消息发送时间
	private String sender;		// 发送人=1可以是系统2也可以是用户之间发送消息
	private String title;		// 标题
	private String msgContent;		// 发送信件内容
	private List<WsMrank> wsMrankList=new ArrayList<WsMrank>();
	
	public WsMessage() {
		super();
	}

	public WsMessage(String id){
		super(id);
	}
	
	
	@Length(min=0, max=255, message="发送人=1可以是系统2也可以是用户之间发送消息长度必须介于 0 和 255 之间")
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public List<WsMrank> getWsMrankList() {
		return wsMrankList;
	}

	public void setWsMrankList(List<WsMrank> wsMrankList) {
		this.wsMrankList = wsMrankList;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
}