package com.thinkgem.jeesite.modules.ws.utils;

public class WsApiConstant {
	
	//获取用户静默授权
	public final static String code_token_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&response_type=code&scope=snsapi_base&state=State#wechat_redirect";

	
	//获取openid
	public final static String oauth_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	public final static String jsapiticket_token_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	public final static String qr_code_url="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	public final static String jscode2session="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

}
