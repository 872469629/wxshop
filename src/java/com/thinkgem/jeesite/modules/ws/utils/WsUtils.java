package com.thinkgem.jeesite.modules.ws.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SLEmojiFilter;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.config.service.WsMrankService;
import com.thinkgem.jeesite.modules.inter.entity.CityArea;
import com.thinkgem.jeesite.modules.inter.entity.CitySelect;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.ws.entity.Oauth2Token;
import com.thinkgem.jeesite.modules.ws.entity.WxAccount;
import com.thinkgem.jeesite.modules.ws.service.WxAccountService;
import com.thinkgem.jeesite.modules.wx.core.common.util.WeixinUtil;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;
import com.thinkgem.jeesite.modules.wx.wxbase.wxtoken.JwTokenAPI;
import com.thinkgem.jeesite.modules.wx.wxuser.user.JwUserAPI;
import com.thinkgem.jeesite.modules.wx.wxuser.user.model.Wxuser;

import net.sf.json.JSONObject;

public class WsUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WsUtils.class);

	private static WxAccountService wxAccountService = SpringContextHolder.getBean("wxAccountService");
	
	private static WsMemberService wsMemberService = SpringContextHolder.getBean("wsMemberService");
	
	private static WsMrankService wsMrankService = SpringContextHolder.getBean("wsMrankService");
	
	private static AreaService areaService = SpringContextHolder.getBean("areaService");
	
	/**
	 * 获取用户的openid
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getOpenId(HttpServletRequest request,HttpServletResponse response) {
		String openId = "";
		try {
			openId = (String) UserUtils.getSession().getAttribute("openid");
			if(StringUtils.isNotEmpty(openId)){
				return openId;
			}
			/**
			 * 如果用户的openid不存在，则先获取code
			 */
			String code = request.getParameter("code");
			WxAccount account=getAccount();
			if (StringUtils.isEmpty(code)||code.equals("null")) {
				return "";
			}
			/**
			 * 根据code,重新请求获取openid
			 */
			if (StringUtils.isNotEmpty(code)&&!code.equals("null")) {
				Oauth2Token ot=getOauth2Token(account.getAccountAppid(), account.getAccountAppsecret(), code);
				if(ot!=null){
					openId = ot.getOpenId();
					UserUtils.getSession().setAttribute("openId", openId);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
				return openId;
		//		return "oiSj30i5GnAggsDaYoIQexFpluqA";
	}

	public static Oauth2Token getOauth2Token(String appId, String appScecret, String code) throws Exception {
		Oauth2Token oauth2Token = null;
		JSONObject jsonObject = null;
		String requestUrl = WsApiConstant.oauth_token_url.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appScecret);
		requestUrl = requestUrl.replace("CODE", code);
		try {
			jsonObject = HttpClientUtil.httpRequest(requestUrl, "POST", null);
			oauth2Token = new Oauth2Token();
			if(jsonObject.containsKey("openid")){
				oauth2Token.setOpenId(jsonObject.getString("openid"));
			}		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return oauth2Token;

	}
	/**
	 * 获取微信access_token
	 */
	public static String getAccessToken(){
		String token="";
		try {
			WxAccount account=getAccount();
			//获取accessToken
			token=(String) CacheUtils.get("access_token");
			long lastTime=0;
			Date nowDate=new Date();
			if(StringUtils.isEmpty(token)){
				token=account.getAccountAccesstoken();
			}else{
				lastTime=(long) CacheUtils.get("access_time");
			}
			//如果accessToken不存在，则重新获取
			if(StringUtils.isEmpty(token)||(nowDate.getTime()-lastTime)/1000>7000){
				token=JwTokenAPI.getAccessToken(account.getAccountAppid(), account.getAccountAppsecret());
				CacheUtils.put("access_token", token);
				CacheUtils.put("access_time", nowDate.getTime());
				account.setAccountAccesstoken(token);
				account.setAccountAccesstokenTime(nowDate);
				wxAccountService.save(account);
			}
		} catch (WexinReqException e) {
			logger.error(e.getMessage());
		}
		return token;
	}
	
	public static WxAccount getAccount(){
		WxAccount account=(WxAccount)WsCacheUtils.get("account");
		if(account==null){
			account=wxAccountService.getWxAccount();
			WsCacheUtils.put("account", account);
		}
		if(account==null){
			account=new WxAccount();
		}
		return account;
	}
	
	/**
	 * 获取省市区联动数据
	 * @return
	 */
	public static List<CitySelect> findAreaList(){
		List<CitySelect> citySelectList=(List<CitySelect>)WsCacheUtils.get("citySelectList");
		if(citySelectList==null){
			citySelectList=new ArrayList<CitySelect>();
			Area areaCondition=new Area();
			List<Area> areaList=areaService.findList(areaCondition);
			for (Area province :areaList) {
				if(province.getParentIds().equals("0,")){
					CitySelect citySelect=new CitySelect();
					citySelect.setId(province.getId());
					citySelect.setN(province.getName());
					List<CityArea> c=new ArrayList<CityArea>();
					for (Area city :areaList) {
						if(city.getParent().getId().equals(province.getId())){
							CityArea cityArea=new CityArea();
							cityArea.setId(city.getId());
							cityArea.setN(city.getName());
							List<String> a=new ArrayList<String>();
							for (Area area :areaList) {
								if(area.getParent().getId().equals(city.getId())){
									a.add(area.getName());
								}
							}
							cityArea.setA(a);
							c.add(cityArea);
						}
					}
					citySelect.setC(c);
					citySelectList.add(citySelect);
				}
			}
			WsCacheUtils.put("citySelectList", citySelectList);
		}
		return citySelectList;
	}
	
	
	public static WsMember getMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		WsMember member = (WsMember) UserUtils.getSession().getAttribute("member");
		if(member!=null){
			return member;
		}
		//判断是否小程序登录,小程序传递user_id参数，微信请勿传递此参数
		String userId=request.getParameter("user_id");
		if(StringUtils.isNotEmpty(userId)){
			return wsMemberService.get(userId);
		}
		String openId=WsUtils.getOpenId(request, response);
		if(StringUtils.isEmpty(openId)){
			throw new WxException("请使用微信登录!");
		}
		member=wsMemberService.getByOpenId(openId);
		if(member==null){
			member=new WsMember();
			WsMrank wsMrank=new WsMrank();
			wsMrank.setIsDefault(WsConstant.YES);
			List<WsMrank> wsMrankList=wsMrankService.findList(wsMrank);
			if(wsMrankList!=null && wsMrankList.size()>0){
				member.setMemberRankId(wsMrankList.get(0).getId());
				member.setMemberRankName(wsMrankList.get(0).getName());
			}
			member.setOpenId(openId);
			Wxuser wxUser=JwUserAPI.getWxuser(WsUtils.getAccessToken(), openId);
			//过滤昵称中的图片
			if(wxUser!=null && StringUtils.isNotEmpty(wxUser.getNickname())){
				member.setNickname(SLEmojiFilter.filterEmoji(wxUser.getNickname()));
			}
			if(wxUser!=null && StringUtils.isNotEmpty(wxUser.getHeadimgurl())){
				member.setHeadimgurl(wxUser.getHeadimgurl());
			}
			wsMemberService.save(member);
		}
		UserUtils.getSession().setAttribute("member", member);
		return member;
	}
	
	public static void main(String[] args) {
		WsUtils.getAccessToken();
	}

	public static String getJsApiTicket(){
		String jsApiTicket=(String) CacheUtils.get("jsApiTicket");
		long lastTime=0;
		if(StringUtils.isNotEmpty(jsApiTicket)){
			lastTime=(long) CacheUtils.get("jsApiTicket_time");
		}
		Date nowDate=new Date();
		//如果accessToken不存在，则重新获取
		if(StringUtils.isEmpty(jsApiTicket)||(nowDate.getTime()-lastTime)/1000>7000){
			String requestUrl=WsApiConstant.jsapiticket_token_url.replace("ACCESS_TOKEN", getAccessToken());
			JSONObject jsonObject = HttpClientUtil.httpRequest(requestUrl, "GET", null);
			if(jsonObject.containsKey("ticket")){
				jsApiTicket=jsonObject.getString("ticket");
				CacheUtils.put("jsApiTicket", jsApiTicket);
				CacheUtils.put("jsApiTicket_time", nowDate.getTime());
			}	
		}
		return jsApiTicket;
	}
	
	/**
	 * 小程序端获取用户信息
	 */
	public static synchronized WsMember getWcxMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		WxAccount account=getAccount();
		String code = request.getParameter("code");
		String fromUserId = request.getParameter("fromUserId");
		logger.info("来源id：" + fromUserId);
		String url=WsApiConstant.jscode2session.replaceAll("JSCODE",code);
		url=url.replaceAll("APPID", account.getWcxAppid()).replaceAll("SECRET", account.getWcxAppsecret());
		JSONObject result=HttpClientUtil.httpRequest(url, "POST", null);
		if (result.has("errcode") && !result.getString("errcode").equals("0")) {
			logger.error("获取用户信息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			throw new WexinReqException("获取第三方平台access_token！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
		}
		String openId=result.getString("openid");
		WsMember member=wsMemberService.getByOpenId(openId);
		if(member==null){
			member=new WsMember();
			member.setBalance(BigDecimal.ZERO);
			WsMrank wsMrank=new WsMrank();
			wsMrank.setIsDefault(WsConstant.YES);
			List<WsMrank> wsMrankList=wsMrankService.findList(wsMrank);
			if(wsMrankList!=null && wsMrankList.size()>0){
				member.setMemberRankId(wsMrankList.get(0).getId());
				member.setMemberRankName(wsMrankList.get(0).getName());
			}
			member.setOpenId(openId);
			if (StringUtils.isNotEmpty(fromUserId) && !"undefined".equals(fromUserId)) {
				WsMember fromUser = wsMemberService.get(fromUserId);
				if (fromUser != null && "1".equals(fromUser.getIsAgent())) {//分享者必须是代理商才可以成为他的下限
					member.setAgentParent(fromUser);
					member.setJoinAgentTime(new Date());
				}
			}
			wsMemberService.save(member);
		}
		//测试时固定返回用户，不用和微信端交互
		return member;
	}
}
