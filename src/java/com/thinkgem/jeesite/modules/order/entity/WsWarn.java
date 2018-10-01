package com.thinkgem.jeesite.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商城异常数据告警Entity
 * @author 大胖老师
 * @version 2018-01-01
 */
public class WsWarn extends DataEntity<WsWarn> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 预警类型
	private String warnContent;		// 预计内容
	private String level;		// 预警级别
	private String state;		// 预警状态
	
	public WsWarn() {
		super();
	}

	public WsWarn(String id){
		super(id);
	}

	@Length(min=0, max=50, message="预警类型长度必须介于 0 和 50 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=2000, message="预计内容长度必须介于 0 和 2000 之间")
	public String getWarnContent() {
		return warnContent;
	}

	public void setWarnContent(String warnContent) {
		this.warnContent = warnContent;
	}
	
	@Length(min=0, max=1, message="预警级别长度必须介于 0 和 1 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=10, message="预警状态长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}