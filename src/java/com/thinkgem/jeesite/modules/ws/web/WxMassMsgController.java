package com.thinkgem.jeesite.modules.ws.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ws.entity.WxMassMsg;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialAudio;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNewsItem;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialPhoto;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialText;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialVideo;
import com.thinkgem.jeesite.modules.ws.service.WxMassMsgService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialAudioService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialNewsItemService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialPhotoService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialTextService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialVideoService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;
import com.thinkgem.jeesite.modules.wx.wxsendmsg.JwSendMessageAPI;
import com.thinkgem.jeesite.modules.wx.wxsendmsg.model.WxArticle;

/**
 * 群发微信Controller
 * @author 大胖老师
 * @version 2017-12-31
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMassMsg")
public class WxMassMsgController extends BaseController {

	@Autowired
	private WxMassMsgService wxMassMsgService;
	@Autowired
	private WxMaterialTextService wxMaterialTextService;
	@Autowired
	private WxMaterialPhotoService wxMaterialPhotoService;
	@Autowired
	private WxMaterialAudioService wxMaterialAudioService;
	@Autowired
	private WxMaterialVideoService wxMaterialVideoService;

	@Autowired
	private WxMaterialNewsItemService wxMaterialNewsItemService;
	
	@ModelAttribute
	public WxMassMsg get(@RequestParam(required=false) String id) {
		WxMassMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMassMsgService.get(id);
		}
		if (entity == null){
			entity = new WxMassMsg();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxMassMsg:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMassMsg wxMassMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMassMsg> page = wxMassMsgService.findPage(new Page<WxMassMsg>(request, response), wxMassMsg); 
		model.addAttribute("page", page);
		return "modules/ws/wxMassMsgList";
	}

	@RequiresPermissions("ws:wxMassMsg:view")
	@RequestMapping(value = "form")
	public String form(WxMassMsg wxMassMsg, Model model) {
		model.addAttribute("wxMassMsg", wxMassMsg);
		return "modules/ws/wxMassMsgForm";
	}

	@RequiresPermissions("ws:wxMassMsg:edit")
	@RequestMapping(value = "save")
	public String save(WxMassMsg wxMassMsg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMassMsg)){
			return form(wxMassMsg, model);
		}
		try {
			wxMassMsgService.save(wxMassMsg);
			//群发文本消息
			if(wxMassMsg.getMsgType().equals(WsConstant.MSG_TYPE_TEXT)){
				WxMaterialText wxMaterialText=wxMaterialTextService.get(wxMassMsg.getMaterialId());
				JwSendMessageAPI.sendMessageToGroupOrAllWithText(WsUtils.getAccessToken(), WsConstant.TRUE, null, wxMaterialText.getTextContent());
			}
			//群发图片消息
			if(wxMassMsg.getMsgType().equals(WsConstant.MSG_TYPE_IMAGE)){
				WxMaterialPhoto wxMaterialPhoto=wxMaterialPhotoService.get(wxMassMsg.getMaterialId());
				JwSendMessageAPI.sendMessageToGroupOrAllWithMediaId(WsUtils.getAccessToken(), WsConstant.TRUE, null, wxMaterialPhoto.getMediaId(),WsConstant.MSG_TYPE_IMAGE);
			}
			//群发音频消息
			if(wxMassMsg.getMsgType().equals(WsConstant.MSG_TYPE_VOICE)){
				WxMaterialAudio wxMaterialAudio=wxMaterialAudioService.get(wxMassMsg.getMaterialId());
				JwSendMessageAPI.sendMessageToGroupOrAllWithMediaId(WsUtils.getAccessToken(), WsConstant.TRUE, null, wxMaterialAudio.getMediaId(),WsConstant.MSG_TYPE_VOICE);
			}
			//群发视频消息
			if(wxMassMsg.getMsgType().equals(WsConstant.MSG_TYPE_VIDEO)){
				WxMaterialVideo WxMaterialVideo=wxMaterialVideoService.get(wxMassMsg.getMaterialId());
				JwSendMessageAPI.sendMessageToGroupOrAllWithMediaId(WsUtils.getAccessToken(), WsConstant.TRUE, null, WxMaterialVideo.getMediaId(),WsConstant.MSG_TYPE_VIDEO);
			}
			//群发图文消息
			if(wxMassMsg.getMsgType().equals(WsConstant.MSG_TYPE_NEWS)){
				List<WxArticle> wxArticles=new ArrayList<WxArticle>();
				WxMaterialNewsItem newsItem=new WxMaterialNewsItem();
				newsItem.setNewsId(wxMassMsg.getMaterialId());
				List<WxMaterialNewsItem> wxMaterialNewsItemList = wxMaterialNewsItemService.findList(newsItem);
				for (WxMaterialNewsItem news : wxMaterialNewsItemList) {
					WxArticle article = new WxArticle();
					article.setAuthor(news.getAuthor());
					article.setContent(news.getContent());
					article.setDigest(news.getDescription());
					article.setShow_cover_pic(WsConstant.YES);
					article.setTitle(news.getTitle());
					int index = news.getImageUrl().indexOf(Global.USERFILES_BASE_URL);
					if(index >= 0) {
						String fileName = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL +news.getImageUrl().substring(index + Global.USERFILES_BASE_URL.length());
						article.setFileName(fileName);
						article.setFilePath("");
					}
					wxArticles.add(article);
				}
				JwSendMessageAPI.sendMessageToGroupOrAllWithArticles(WsUtils.getAccessToken(), WsConstant.TRUE, null, wxArticles);
			}
		} catch (WexinReqException e) {
			model.addAttribute("message", e.getMessage());
			logger.error("wxMassMsg:save",e);
			return form(wxMassMsg, model);
		}
		addMessage(redirectAttributes, "保存群发微信成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMassMsg/?repage";
	}
	
	@RequiresPermissions("ws:wxMassMsg:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMassMsg wxMassMsg, RedirectAttributes redirectAttributes) {
		wxMassMsgService.delete(wxMassMsg);
		addMessage(redirectAttributes, "删除群发微信成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMassMsg/?repage";
	}

}