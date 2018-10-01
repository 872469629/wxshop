package com.thinkgem.jeesite.modules.wx.third.model;

import com.thinkgem.jeesite.modules.wx.core.req.model.WeixinReqParam;


/**
 * 获取预授权码
 * @author zhangdaihao
 *
 */
public class GetPreAuthCodeParam  extends WeixinReqParam{

	private String component_appid;
	public String getComponent_appid() {
		return component_appid;
	}
	public void setComponent_appid(String component_appid) {
		this.component_appid = component_appid;
	}
	
}
