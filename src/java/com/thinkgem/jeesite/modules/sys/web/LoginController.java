/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsWarn;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.order.service.WsWarnService;
import com.thinkgem.jeesite.modules.report.entity.ReportIncreaseDetail;
import com.thinkgem.jeesite.modules.report.service.ReportIncreaseDetailService;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturn;
import com.thinkgem.jeesite.modules.returnback.service.WsReturnService;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private WsReturnService wsReturnService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private ReportIncreaseDetailService reportIncreaseDetailService;
	
	@Autowired
	private WsWarnService wsWarnService;
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			UserUtils.getSession().setAttribute("user", UserUtils.getUser());
			return "redirect:" + adminPath;
		}
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/jeesite/src/main/webapp/WEB-INF/lib/jeesite.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response,String menuId,Model model) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}
		model.addAttribute("menuId",menuId);
		String tabmode = CookieUtils.getCookie(request, "tabmode");
		UserUtils.getSession().setAttribute("user", UserUtils.getUser());
		if(StringUtils.isNotEmpty(tabmode) && tabmode.equals("1")){
			return "modules/sys/sysIndex2";
		}
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	/**
	 * 系统首页
	 */
	@RequestMapping(value = "${adminPath}/indexFrame", method = RequestMethod.GET)
	public String indexFrame(HttpServletRequest request, HttpServletResponse response, Model model) {
		WsOrder wsOrder=new WsOrder();
		/**
		 * 查询订单总量
		 */
		int totalNum=wsOrderService.findCount(wsOrder);
		model.addAttribute("totalNum", totalNum);
		/**
		 * 查询待付款订单总量
		 */ 
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_PAY);
		int waitPayNum=wsOrderService.findCount(wsOrder);
		model.addAttribute("waitPayNum", waitPayNum);
		/**
		 * 查询待发货的订单
		 */
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_SEND);
		int waitSendNum=wsOrderService.findCount(wsOrder);
		model.addAttribute("waitSendNum", waitSendNum);
		/**
		 * 查询待收货的订单
		 */
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_RECEVIED);
		int waitReceviedNum=wsOrderService.findCount(wsOrder);
		model.addAttribute("waitReceviedNum", waitReceviedNum);
		/**
		 * 查询待评价的订单
		 */
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
		int waitEvaluationNum=wsOrderService.findCount(wsOrder);
		model.addAttribute("waitEvaluationNum", waitEvaluationNum);
		/**
		 * 查询退款定单
		 */
		WsReturn wsReturn=new WsReturn();
		wsReturn.setState(WsConstant.RETURN_ORDER_STATE_WX_WAITE);
		int wxWaitNum=wsReturnService.findCount(wsReturn);
		model.addAttribute("wxWaitNum", wxWaitNum);
		/**
		 * 查询已完成的订单
		 */
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_FINSH);
		int waitFinshNum=wsOrderService.findCount(wsOrder);
		model.addAttribute("waitFinshNum", waitFinshNum);
		/**
		 * 查询已取消的订单
		 */
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_CANCEL);
		int waitCancelNum=wsOrderService.findCount(wsOrder);
		model.addAttribute("waitCancelNum", waitCancelNum);
		/**
		 * 查询昨日新增数据
		 */
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		Date time=cal.getTime();
		String yestoday=new SimpleDateFormat("yyyy-MM-dd").format(time);
		ReportIncreaseDetail reportIncreaseDetail=new ReportIncreaseDetail();
		reportIncreaseDetail.setCountDate(yestoday);
		List<ReportIncreaseDetail> reportIncreaseDetailList=reportIncreaseDetailService.findList(reportIncreaseDetail);
		if(reportIncreaseDetailList!=null && reportIncreaseDetailList.size()>0){
			model.addAttribute("memberIncreased", reportIncreaseDetailList.get(0));
		}else{
			reportIncreaseDetail.setSelMoney(new BigDecimal(0));
			model.addAttribute("memberIncreased", reportIncreaseDetail);
		}
		/**
		 * 查询系统提示信息
		 */
		WsWarn wsWarn=new WsWarn();
		wsWarn.setState(WsConstant.WARN_STATE_WATIE);
		wsWarn.setType(WsConstant.WARN_TYPE_NOMAL);
		List<WsWarn> wsWarnNormalList=wsWarnService.findList(wsWarn);
		model.addAttribute("wsWarnNormalList", wsWarnNormalList);
		/**
		 * 查询系统预警信息
		 */
		wsWarn.setType(WsConstant.WARN_TYPE_EXCEPTION);
		List<WsWarn> wsWarnExceptionList=wsWarnService.findList(wsWarn);
		model.addAttribute("wsWarnExceptionList", wsWarnExceptionList);
		return "modules/sys/indexFrame";
	}
}
