package com.thinkgem.jeesite.modules.member.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 通知通告发送管理Entity
 * @author 通知通告发送管理
 * @version 2017-11-17
 */
public class WsMessageRecord extends DataEntity<WsMessageRecord> {
	
	private static final long serialVersionUID = 1L;
	private WsMessage wsMessage;
	private String messageId;		// 消息ID
	private String memberRankId;		// 会员等级id
	private String memberId;		// 接受会员id
	private String readFlag;		// 阅读标记
	private Date readDate;		// 阅读时间
	
	public WsMessageRecord() {
		super();
	}

	public WsMessageRecord(String id){
		super(id);
	}

	@Length(min=0, max=64, message="消息ID长度必须介于 0 和 64 之间")
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	@Length(min=0, max=64, message="会员等级id长度必须介于 0 和 64 之间")
	public String getMemberRankId() {
		return memberRankId;
	}

	public void setMemberRankId(String memberRankId) {
		this.memberRankId = memberRankId;
	}
	
	@Length(min=0, max=64, message="接受会员id长度必须介于 0 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=1, message="阅读标记长度必须介于 0 和 1 之间")
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public WsMessage getWsMessage() {
		return wsMessage;
	}

	public void setWsMessage(WsMessage wsMessage) {
		this.wsMessage = wsMessage;
	}
	
}