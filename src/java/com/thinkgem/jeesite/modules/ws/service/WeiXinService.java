package com.thinkgem.jeesite.modules.ws.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.SLEmojiFilter;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.config.service.WsMrankService;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.ws.entity.WxKeyResponse;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialAudio;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNewsItem;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialPhoto;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialText;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialVideo;
import com.thinkgem.jeesite.modules.ws.entity.WxMenu;
import com.thinkgem.jeesite.modules.ws.entity.WxSubscribe;
import com.thinkgem.jeesite.modules.ws.resp.Article;
import com.thinkgem.jeesite.modules.ws.resp.Image;
import com.thinkgem.jeesite.modules.ws.resp.ImageMessageResp;
import com.thinkgem.jeesite.modules.ws.resp.NewsMessageResp;
import com.thinkgem.jeesite.modules.ws.resp.TextMessageResp;
import com.thinkgem.jeesite.modules.ws.resp.Video;
import com.thinkgem.jeesite.modules.ws.resp.VideoMessageResp;
import com.thinkgem.jeesite.modules.ws.resp.Voice;
import com.thinkgem.jeesite.modules.ws.resp.VoiceMessageResp;
import com.thinkgem.jeesite.modules.ws.utils.MessageUtil;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;
import com.thinkgem.jeesite.modules.wx.wxuser.user.JwUserAPI;
import com.thinkgem.jeesite.modules.wx.wxuser.user.model.Wxuser;

@Service
@Transactional(readOnly = true)
public class WeiXinService extends BaseService{
	
	@Autowired
	private WxSubscribeService wxSubscribeService;
	
	@Autowired
	private WxKeyResponseService wxKeyResponseService;
	
	@Autowired
	private WxMaterialTextService wxMaterialTextService;
	
	@Autowired
	private WxMaterialPhotoService wxMaterialPhotoService;
	
	@Autowired
	private WxMaterialVideoService wxMaterialVideoService;
	
	@Autowired
	private WxMaterialAudioService wxMaterialAudioService;
	
	@Autowired
	private WxMaterialNewsItemService wxMaterialNewsItemService;
	
	@Autowired
	private WxMenuService wxMenuService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsMrankService wsMrankService;
	
	@Transactional(readOnly = false)
	public String autoResponse(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String msgId = requestMap.get("MsgId");
			//消息内容
			String content = requestMap.get("Content");
			logger.info("------------微信客户端发送请求---------------------   |   fromUserName:"+fromUserName+"   |   ToUserName:"+toUserName+"   |   msgType:"+msgType+"   |   msgId:"+msgId+"   |   content:"+content);
			// 默认回复此文本消息
			TextMessageResp textMessage = new TextMessageResp();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setContent(WsConstant.DEFAULT_REPLY_MSG);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respMessage = doTextReply(requestMap, textMessage, respMessage, toUserName, fromUserName, respContent);
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				//【微信触发类型】图片消息
				respContent = "您发送的是图片消息！"+WsConstant.DEFAULT_REPLY_MSG;
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				//【微信触发类型】地理位置消息
				respContent = "您发送的是地理位置消息！"+WsConstant.DEFAULT_REPLY_MSG;
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				//【微信触发类型】链接消息
				respContent = "您发送的是链接消息！"+WsConstant.DEFAULT_REPLY_MSG;
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				//【微信触发类型】音频消息
				respContent = "您发送的是音频消息！"+WsConstant.DEFAULT_REPLY_MSG;
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				//【微信触发类型】事件推送
				// 事件类型
				String eventType = requestMap.get("Event");
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respMessage = doSubscribe(requestMap, textMessage, respMessage, toUserName, fromUserName, respContent);
				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					respMessage = doMenuClick(requestMap, textMessage, respMessage, toUserName, fromUserName, respContent);
				}else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return respMessage;
	}
	
	//文本消息处理
	private String doTextReply(Map<String, String> requestMap,TextMessageResp textMessage ,String respMessage
			,String toUserName,String fromUserName,String respContent){
		String content = requestMap.get("Content");
		WxKeyResponse wxKeyResponse=new WxKeyResponse();
		wxKeyResponse.setKeyword(content);
		List<WxKeyResponse> WxKeyResponseList=wxKeyResponseService.findList(wxKeyResponse);
		if(WxKeyResponseList!=null && WxKeyResponseList.size()>0){
			WxKeyResponse response=WxKeyResponseList.get(0);
			String type = response.getMsgType();
			//文本消息文本回复
			if (WsConstant.MSG_TYPE_TEXT.equals(type)) {
				respMessage=getTextReply(response.getMaterialId(), textMessage);
			}
			//图片消息回复
			if (WsConstant.MSG_TYPE_IMAGE.equals(type)) {
				respMessage=getImageReply(response.getMaterialId(), fromUserName, toUserName);
			}
			//音频消息回复
			if (WsConstant.MSG_TYPE_VOICE.equals(type)) {
				respMessage=getVoiceReply(response.getMaterialId(), fromUserName, toUserName);
			}
			//视频消息视频回复
			if (WsConstant.MSG_TYPE_VIDEO.equals(type)) {
				respMessage=getVideoReply(response.getMaterialId(), fromUserName, toUserName);
			}
			//图文消息图文回复
			if (WsConstant.MSG_TYPE_NEWS.equals(type)) {
				respMessage=getNewsReply(response.getMaterialId(), fromUserName, toUserName);
			}
		}
		return respMessage;
	}
	
	//订阅消息处理
	private String doSubscribe(Map<String, String> requestMap,TextMessageResp textMessage ,String respMessage
			,String toUserName,String fromUserName,String respContent) throws WexinReqException{
		/**
		 * 判断是否邀请推广，如果是邀请推广，则记录推广人
		 */
		String eventType = requestMap.get("EventKey");
		if(StringUtils.isNotEmpty(eventType)){
			String [] eventTypes=eventType.split("_");//推荐人参数
			if(eventTypes!=null && eventTypes.length==2){
				WsMember member=wsMemberService.getByOpenId(fromUserName);//订阅人
				if(member==null){
					member=new WsMember();
					WsMrank wsMrank=new WsMrank();
					wsMrank.setIsDefault(WsConstant.YES);
					List<WsMrank> wsMrankList=wsMrankService.findList(wsMrank);
					if(wsMrankList!=null && wsMrankList.size()>0){
						member.setMemberRankId(wsMrankList.get(0).getId());
						member.setMemberRankName(wsMrankList.get(0).getName());
					}
					member.setOpenId(fromUserName);
					Wxuser wxUser=JwUserAPI.getWxuser(WsUtils.getAccessToken(), fromUserName);
					//过滤昵称中的图片
					member.setNickname(SLEmojiFilter.filterEmoji(wxUser.getNickname()));
					member.setHeadimgurl(wxUser.getHeadimgurl());
					member.setRecommendMemberId(eventTypes[1]);
					wsMemberService.save(member);
				}else{
					//判断是否是自己扫描自己的二维码，如果不是，则记录推荐人
					if(!member.getId().equals(eventTypes[1])){
						member.setRecommendMemberId(eventTypes[1]);
						wsMemberService.save(member);
					}
				}

			}
		}
		/**
		 * 根据商户配置，回复对应信息
		 */
		List<WxSubscribe> wxSubscribeList=wxSubscribeService.findList(new WxSubscribe());
		if(wxSubscribeList!=null && wxSubscribeList.size()>0){
			WxSubscribe wxSubscribe=wxSubscribeList.get(0);
			String type = wxSubscribe.getMsgType();
			//订阅文本回复
			if (WsConstant.MSG_TYPE_TEXT.equals(type)) {
				respMessage=getTextReply(wxSubscribe.getMaterialId(), textMessage);
			}
			//订阅图片回复
			if (WsConstant.MSG_TYPE_IMAGE.equals(type)) {
				respMessage=getImageReply(wxSubscribe.getMaterialId(), fromUserName, toUserName);
			}
			//订阅语音回复
			if (WsConstant.MSG_TYPE_VOICE.equals(type)) {
				respMessage=getVoiceReply(wxSubscribe.getMaterialId(), fromUserName, toUserName);
			}
			//订阅视频回复
			if (WsConstant.MSG_TYPE_VIDEO.equals(type)) {
				respMessage=getVideoReply(wxSubscribe.getMaterialId(), fromUserName, toUserName);
			}
			//订阅图文回复
			if (WsConstant.MSG_TYPE_NEWS.equals(type)) {
				respMessage=getNewsReply(wxSubscribe.getMaterialId(), fromUserName, toUserName);
			}
		}
		return respMessage;
	}
	
	//菜单点击消息处理
	private String doMenuClick(Map<String, String> requestMap,TextMessageResp textMessage ,String respMessage
			,String toUserName,String fromUserName,String respContent){
		String key = requestMap.get("EventKey");
		WxMenu wxMenu=new WxMenu();
		wxMenu.setMenuKey(key);
		List<WxMenu> wxMenuList=wxMenuService.findList(wxMenu);
		if(wxMenuList!=null && wxMenuList.size()>0){
			WxMenu menu=wxMenuList.get(0);
			String type = menu.getMsgType();
			//菜单点击文本回复
			if (WsConstant.MSG_TYPE_TEXT.equals(type)) {
				respMessage=getTextReply(menu.getMaterialId(), textMessage);
			}
			//菜单点击图片回复
			if (WsConstant.MSG_TYPE_IMAGE.equals(type)) {
				respMessage=getImageReply(menu.getMaterialId(), fromUserName, toUserName);
			}
			//菜单点击语音回复
			if (WsConstant.MSG_TYPE_VOICE.equals(type)) {
				respMessage=getVoiceReply(menu.getMaterialId(), fromUserName, toUserName);
			}
			//菜单点击视频回复
			if (WsConstant.MSG_TYPE_VIDEO.equals(type)) {
				respMessage=getVideoReply(menu.getMaterialId(), fromUserName, toUserName);
			}
			//菜单点击图文回复
			if (WsConstant.MSG_TYPE_NEWS.equals(type)) {
				respMessage=getNewsReply(menu.getMaterialId(), fromUserName, toUserName);
			}
		}
		return respMessage;
	}
	//获取文本回复
	private String getTextReply(String materialId,TextMessageResp textMessage){
		WxMaterialText text=wxMaterialTextService.get(materialId);
		textMessage.setContent(text.getTextContent());
		return MessageUtil.textMessageToXml(textMessage);
	}
	//获取图片回复
	private String getImageReply(String materialId,String fromUserName,String toUserName){
		ImageMessageResp imageMessage=new ImageMessageResp();
		imageMessage.setToUserName(fromUserName);
		imageMessage.setFromUserName(toUserName);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setMsgType(WsConstant.MSG_TYPE_IMAGE);
		Image image=new Image();
		WxMaterialPhoto photo = wxMaterialPhotoService.get(materialId);
		image.setMediaId(photo.getMediaId());
		imageMessage.setImage(image);
		return MessageUtil.imageMessageToXml(imageMessage);
	}
	//获取音频素材回复
	private String getVoiceReply(String materialId,String fromUserName,String toUserName){
		VoiceMessageResp voiceMessage=new VoiceMessageResp();
		voiceMessage.setToUserName(fromUserName);
		voiceMessage.setFromUserName(toUserName);
		voiceMessage.setCreateTime(new Date().getTime());
		voiceMessage.setMsgType(WsConstant.MSG_TYPE_VOICE);
		Voice voice=new Voice();
		WxMaterialAudio audio = wxMaterialAudioService.get(materialId);
		voice.setMediaId(audio.getMediaId());
		voiceMessage.setVoice(voice);
		return MessageUtil.voiceMessageToXml(voiceMessage);
	}
	//获取视频素材回复
	private String getVideoReply(String materialId,String fromUserName,String toUserName){
		VideoMessageResp videoMessageResp=new VideoMessageResp();
		videoMessageResp.setToUserName(fromUserName);
		videoMessageResp.setFromUserName(toUserName);
		videoMessageResp.setCreateTime(new Date().getTime());
		videoMessageResp.setMsgType(WsConstant.MSG_TYPE_VIDEO);
		Video video=new Video();
		WxMaterialVideo wxVideo = wxMaterialVideoService.get(materialId);
		video.setMediaId(wxVideo.getMediaId());
		video.setTitle(wxVideo.getTitle());
		video.setDescription(wxVideo.getDescription());
		videoMessageResp.setVideo(video);
		return MessageUtil.videoMessageToXml(videoMessageResp);
	}
	//获取图文素材回复
	private String getNewsReply(String materialId,String fromUserName,String toUserName){
		NewsMessageResp newsMessageResp=new NewsMessageResp();
		newsMessageResp.setToUserName(fromUserName);
		newsMessageResp.setFromUserName(toUserName);
		newsMessageResp.setCreateTime(new Date().getTime());
		newsMessageResp.setMsgType(WsConstant.MSG_TYPE_NEWS);
		WxMaterialNewsItem newsItem=new WxMaterialNewsItem();
		List<Article> articleList = new ArrayList<Article>();
		newsItem.setNewsId(materialId);
		List<WxMaterialNewsItem> wxMaterialNewsItemList = wxMaterialNewsItemService.findList(newsItem);
		for (WxMaterialNewsItem news : wxMaterialNewsItemList) {
			Article article = new Article();
			article.setTitle(news.getTitle());
			article.setPicUrl(UrlUtils.getNetUrl(news.getImageUrl()));
			String url = "";
			if (StringUtils.isEmpty(news.getUrl())) {
				url = Global.getWxDoMain()+"/newdetali.html?id="+ news.getId();
			} else {
				url = news.getUrl();
			}
			article.setUrl(url);
			article.setDescription(news.getDescription());
			articleList.add(article);
		}
		newsMessageResp.setArticleCount(wxMaterialNewsItemList.size());
		newsMessageResp.setArticles(articleList);
		return MessageUtil.newsMessageToXml(newsMessageResp);
	}
	
}
