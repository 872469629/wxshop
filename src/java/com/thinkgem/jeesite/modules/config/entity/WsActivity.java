package com.thinkgem.jeesite.modules.config.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活动配置Entity
 * @author 大胖老师
 * @version 2017-10-05
 */
public class WsActivity extends DataEntity<WsActivity> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 活动编号
	private String title;		// 活动标题
	private String activityType;		// 活动类型
	private String activityContent;		// 活动内容
	private String implClass;		// 活动实现类
	private String frontPage;		// 活动界面
	private Integer priority;		// 优先级
	
	public WsActivity() {
		super();
	}

	public WsActivity(String id){
		super(id);
	}

	@Length(min=0, max=11, message="活动编号长度必须介于 0 和 11 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="活动标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=10, message="活动类型长度必须介于 0 和 10 之间")
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	@Length(min=0, max=1000, message="活动内容长度必须介于 0 和 1000 之间")
	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}
	
	@Length(min=0, max=200, message="活动实现类长度必须介于 0 和 200 之间")
	public String getImplClass() {
		return implClass;
	}

	public void setImplClass(String implClass) {
		this.implClass = implClass;
	}
	
	@Length(min=0, max=200, message="活动界面长度必须介于 0 和 200 之间")
	public String getFrontPage() {
		return frontPage;
	}

	public void setFrontPage(String frontPage) {
		this.frontPage = frontPage;
	}
	
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}