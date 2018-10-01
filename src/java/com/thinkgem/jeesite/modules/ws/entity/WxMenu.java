package com.thinkgem.jeesite.modules.ws.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 微信菜单Entity
 * @author 大胖老师
 * @version 2017-09-29
 */
public class WxMenu extends TreeEntity<WxMenu> {
	
	private static final long serialVersionUID = 1L;
	private boolean isParent;		//
	private WxMenu parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 菜单名称
	private String menuType;		// 菜单类型
	private String msgType;		// 消息类型
	private String menuKey;		// click菜单事件
	private String materialId;		// 模板id
	private String materialTitle;		// 模板标题
	private String url;		// 网页链接类URL
	private String orders;		// 菜单顺序
	
	public WxMenu() {
		super();
	}

	public WxMenu(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public WxMenu getParent() {
		return parent;
	}

	public void setParent(WxMenu parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=255, message="菜单名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="菜单类型长度必须介于 0 和 255 之间")
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	@Length(min=0, max=255, message="消息类型长度必须介于 0 和 255 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=255, message="click菜单事件长度必须介于 0 和 255 之间")
	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}
	
	@Length(min=0, max=255, message="模板id长度必须介于 0 和 255 之间")
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	@Length(min=0, max=255, message="模板标题长度必须介于 0 和 255 之间")
	public String getMaterialTitle() {
		return materialTitle;
	}

	public void setMaterialTitle(String materialTitle) {
		this.materialTitle = materialTitle;
	}
	
	@Length(min=0, max=255, message="网页链接类URL长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=11, message="菜单顺序长度必须介于 0 和 11 之间")
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	
}