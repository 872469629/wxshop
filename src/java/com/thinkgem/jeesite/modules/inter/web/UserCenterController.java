package com.thinkgem.jeesite.modules.inter.web;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.SmsUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCollectLog;
import com.thinkgem.jeesite.modules.member.entity.WsMemberVisitLog;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.member.service.WsMemberCollectLogService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.member.service.WsMemberVisitLogService;
import com.thinkgem.jeesite.modules.member.service.WsMessageRecordService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.sys.entity.SysParam;
import com.thinkgem.jeesite.modules.sys.service.SysParamService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

import groovy.util.logging.Slf4j;

/**
 * 个人中心接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/usercenter")
public class UserCenterController extends BaseController {
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;
	
	@Autowired
	private WsMemberVisitLogService wsMemberVisitLogService;
	
	@Autowired
	private WsMemberCollectLogService wsMemberCollectLogService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Serializable> data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 查询当前用户待付款订单
			 */
			WsOrder wsOrder=new WsOrder();
			wsOrder.setMemberId(member);
			System.out.println("=========="+member.getId());
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_PAY);
			List<WsOrder> waiePayOrderList = wsOrderService.findList(wsOrder);
			/**
			 * 查询当前用户待发货订单
			 */
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_SEND);
			List<WsOrder> waieSendOrderList = wsOrderService.findList(wsOrder);
			/**
			 * 查询当前用户待收货订单
			 */
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_RECEVIED);
			List<WsOrder> waieReceviedOrderList = wsOrderService.findList(wsOrder);
			/**
			 * 查询当前用户待评价订单
			 */
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_EVALUATION);
			List<WsOrder> waieEvaluationOrderList = wsOrderService.findList(wsOrder);
			/**
			 * 退款中的订单
			 */
			wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_BACK);
			List<WsOrder> waieBackOrderList = wsOrderService.findList(wsOrder);
			/**
			 * 查询客服电话
			 */
			String tel="";
			SysParam sysParam=new SysParam();
			sysParam.setParamCode(WsConstant.TEL);
			List<SysParam> sysParamList=sysParamService.findList(sysParam);
			if(sysParamList!=null && sysParamList.size()>0 && StringUtils.isNotEmpty(sysParamList.get(0).getParamValue())){
				tel=sysParamList.get(0).getParamValue();
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("member",member);
			data.put("waitPayOrderNum",waiePayOrderList.size());
			data.put("waitSendOrderNum",waieSendOrderList.size());
			data.put("waitReceviedOrderNum",waieReceviedOrderList.size());
			data.put("waitEvakyatuibOrderNum",waieEvaluationOrderList.size());
			data.put("waitBackOrderNum",waieBackOrderList.size());
			data.put("tel",tel);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter",e);
		}
		return data;
	}
	
	/**
	 * 关于我们
	 */
	@RequestMapping(value = "about")
	@ResponseBody
	@CrossOrigin
	public Map about(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 查询关于我们
			 */
			String aboutMsg="";
			SysParam sysParam=new SysParam();
			sysParam.setParamCode(WsConstant.ABOUT);
			List<SysParam> sysParamList=sysParamService.findList(sysParam);
			if(sysParamList!=null && sysParamList.size()>0 && StringUtils.isNotEmpty(sysParamList.get(0).getParamValue())){
				aboutMsg=sysParamList.get(0).getParamValue();
			}
			/**
			 * 查询客服电话
			 */
			String tel="";
			sysParam.setParamCode(WsConstant.TEL);
			sysParamList=sysParamService.findList(sysParam);
			if(sysParamList!=null && sysParamList.size()>0 && StringUtils.isNotEmpty(sysParamList.get(0).getParamValue())){
				tel=sysParamList.get(0).getParamValue();
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("aboutMsg",aboutMsg);
			data.put("tel",tel);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/about",e);
		}
		return data;
	}
	
	/**
	 * 用户足迹
	 */
	@RequestMapping(value = "userFoot")
	@ResponseBody
	@CrossOrigin
	public Map userFoot(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 查询用户访问记录
			 */
			WsMemberVisitLog wsMemberVisitLog=new WsMemberVisitLog();
			wsMemberVisitLog.setWsMember(member);
			Page<WsMemberVisitLog> page=wsMemberVisitLogService.findPage(new Page<WsMemberVisitLog>(), wsMemberVisitLog);
			for (WsMemberVisitLog visitLog:page.getList()) {
				visitLog.getWsProduct().setProdImage(UrlUtils.getNetUrl(visitLog.getWsProduct().getProdImage()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsMemberVisitLogList",page.getList());
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/userFoot",e);
		}
		return data;
	}
	
	/**
	 * 查询用户收藏
	 */
	@RequestMapping(value = "userCollect")
	@ResponseBody
	@CrossOrigin
	public Map userCollect(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 查询用户访问记录
			 */
			WsMemberCollectLog wsMemberCollectLog=new WsMemberCollectLog();
			wsMemberCollectLog.setWsMember(member);
			List<WsMemberCollectLog> wsMemberCollectLogList=wsMemberCollectLogService.findList(wsMemberCollectLog);
			for (WsMemberCollectLog collectLog:wsMemberCollectLogList) {
				collectLog.getWsProduct().setProdImage(UrlUtils.getNetUrl(collectLog.getWsProduct().getProdImage()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsMemberCollectLogList",wsMemberCollectLogList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/userCollect",e);
		}
		return data;
	}
	
	/**
	 * 添加用户收藏
	 */
	@RequestMapping(value = "addUserCollect")
	@ResponseBody
	@CrossOrigin
	public Map addUserCollect(String prodId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 新增用户收藏记录
			 */
			WsMemberCollectLog wsMemberCollectLog=new WsMemberCollectLog();
			wsMemberCollectLog.setWsMember(member);
			wsMemberCollectLog.setWsProduct(new WsProduct(prodId));
			List<WsMemberCollectLog> wsMemberCollectLogList=wsMemberCollectLogService.findList(wsMemberCollectLog);
			if(wsMemberCollectLogList!=null && wsMemberCollectLogList.size()>0){
				wsMemberCollectLog=wsMemberCollectLogList.get(0);
			}
			wsMemberCollectLog.setCollectDate(new Date());
			wsMemberCollectLogService.save(wsMemberCollectLog);
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/addUserCollect",e);
		}
		return data;
	}
	
	/**
	 * 小程序获取用户信息
	 */
	@RequestMapping(value = "getWcxUser")
	@ResponseBody
	@CrossOrigin
	public Map getWcxUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WsMember member=WsUtils.getWcxMember(request, response);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("userInfo",member);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/addUserCollect",e);
		}
		return data;
	}
	
	/**
	 * 小程序保存用户信息
	 */
	@RequestMapping(value = "saveWcxUser")
	@ResponseBody
	@CrossOrigin
	public Map saveWcxUser(String nickName,String avatarUrl,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WsMember member=WsUtils.getMember(request, response);
			member.setNickname(nickName);
			member.setHeadimgurl(avatarUrl);
			wsMemberService.save(member);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("userInfo",member);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/addUserCollect",e);
		}
		return data;
	}
	
	/**
	 * 短信发送
	 */
	@RequestMapping(value = "sendSms")
	@ResponseBody
	@CrossOrigin
	public Map sendSms(String phone, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data = new HashMap();
		try {
			if (StringUtils.isEmpty(phone)) {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", "手机号码不能为空");
				return data;
			}
			String randomSMSCode = StringUtils.getRandomSMSCode();
			String sCode = "session_" + phone;
			request.getSession().setAttribute(sCode, randomSMSCode);
			SendSmsResponse sendSms = SmsUtils.sendSms(phone, randomSMSCode);
			if ("OK".equals(sendSms.getCode())) {
				logger.info("验证码："+randomSMSCode);
				data.put("ret", InterConstant.RET_SUCCESS);
				data.put("msg", "发送成功");
			} else {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", sendSms.getMessage());
				logger.info("发送短信失败："+sendSms.getMessage());
			}
		} catch (WxException e) {
			data.put("ret", InterConstant.RET_WX);
			data.put("appid", WsUtils.getAccount().getAccountAppid());
		} catch (Exception e) {
			data.put("ret", InterConstant.RET_FAILED);
			data.put("msg", "服务器异常");
			logger.error("usercenter/sendSms", e);
		}
		return data;
	}
	
	/**
	 * 绑定手机号
	 */
	@RequestMapping(value = "bindPhone")
	@ResponseBody
	@CrossOrigin
	public Map bindPhone(String phone, String code, HttpServletRequest request, HttpServletResponse response) {
		Map data = new HashMap();
		try {
			/**
			 * 获取用户信息
			 */
			WsMember member = WsUtils.getMember(request, response);
			if (member == null) {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", "用户未登录，不能绑定手机号");
				return data;
			}
			if (StringUtils.isEmpty(phone)) {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", "手机号码不能为空");
				return data;
			}
			if (StringUtils.isEmpty(code)) {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", "验证码不能为空");
				return data;
			}
			String sessionCode = (String) request.getSession().getAttribute("session_" + phone);
			if (StringUtils.isEmpty(sessionCode)) {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", "验证码已过期");
				return data;
			}
			if (!sessionCode.equals(code)) {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", "验证码已过期");
				return data;
			}
			member.setMobile(phone);
			int update = wsMemberService.update(member);
			if (update > 0) {
				data.put("ret", InterConstant.RET_SUCCESS);
				data.put("msg", "绑定成功");
			} else {
				data.put("ret", InterConstant.RET_FAILED);
				data.put("msg", "绑定失败");
			}
		} catch (WxException e) {
			data.put("ret", InterConstant.RET_WX);
			data.put("appid", WsUtils.getAccount().getAccountAppid());
		} catch (Exception e) {
			data.put("ret", InterConstant.RET_FAILED);
			data.put("msg", "服务器异常");
			logger.error("usercenter/bindPhone", e);
		}
		return data;
	}
	
}