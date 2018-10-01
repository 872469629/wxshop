package com.thinkgem.jeesite.modules.inter.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMessage;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.member.service.WsMessageRecordService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 微信消息页面接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/msg")
public class MessageController extends BaseController {
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			 * 获取未读的通告消息
			 */
			WsMessage wsMessage=new WsMessage();
			wsMessage.setMsgType(WsConstant.WS_MSG_TYPE_NOTIFY);
			wsMessageRecord.setWsMessage(wsMessage);
			int notifymessagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 获取未读的互动消息
			 */
			wsMessageRecord.getWsMessage().setMsgType(WsConstant.WS_MSG_TYPE_NOTERACT);
			int noteractmessagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 获取未读的交易消息
			 */
			wsMessageRecord.getWsMessage().setMsgType(WsConstant.WS_MSG_TYPE_BUSINESS);
			int businessmessagenum=wsMessageRecordService.findCount(wsMessageRecord);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("notifymessagenum",notifymessagenum);
			data.put("noteractmessagenum",noteractmessagenum);
			data.put("businessmessagenum",businessmessagenum);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("message",e);
		}
		return data;
	}

	
	@RequestMapping(value = "list")
	@ResponseBody
	@CrossOrigin
	public Map list(String msgType,HttpServletRequest request, HttpServletResponse response, Model model) {
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
			 * 获取消息列表
			 */
			wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			WsMessage wsMessage=new WsMessage();
			if(StringUtils.isNotEmpty(msgType)){
				wsMessage.setMsgType(msgType);
				wsMessageRecord.setWsMessage(wsMessage);
			}	
			List<WsMessageRecord> wsMessageRecordList=wsMessageRecordService.findList(wsMessageRecord);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsMessageRecordList",wsMessageRecordList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("message/list",e);
		}
		return data;
	}
	
	@RequestMapping(value = "detail")
	@ResponseBody
	@CrossOrigin
	public Map detail(String msgId,HttpServletRequest request, HttpServletResponse response, Model model) {
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
			 * 获取未读的通告消息
			 */
			wsMessageRecord=wsMessageRecordService.get(msgId);
			wsMessageRecord.setReadFlag(WsConstant.YES);
			wsMessageRecord.setReadDate(new Date());
			wsMessageRecordService.save(wsMessageRecord);
			String msgTypeLable=DictUtils.getDictLabel(wsMessageRecord.getWsMessage().getMsgType(), "ws_msg_type", "系统通知");
			String sendDate=DateUtils.formatDate(wsMessageRecord.getWsMessage().getSendDate(),"yyyy-MM-dd HH:mm:ss");
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsMessageRecord",wsMessageRecord);
			data.put("msgTypeLable",msgTypeLable);
			data.put("sendDate",sendDate);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("message/detail",e);
		}
		return data;
	}


}