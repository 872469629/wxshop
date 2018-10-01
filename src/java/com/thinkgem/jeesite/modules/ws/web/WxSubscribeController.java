package com.thinkgem.jeesite.modules.ws.web;

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
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialAudio;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNews;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialPhoto;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialText;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialVideo;
import com.thinkgem.jeesite.modules.ws.entity.WxSubscribe;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialAudioService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialNewsService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialPhotoService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialTextService;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialVideoService;
import com.thinkgem.jeesite.modules.ws.service.WxSubscribeService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 关注欢迎语Controller
 * @author 大胖老师
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxSubscribe")
public class WxSubscribeController extends BaseController {

	@Autowired
	private WxSubscribeService wxSubscribeService;
	
	@Autowired
	private WxMaterialTextService wxMaterialTextService;
	
	@Autowired
	private WxMaterialPhotoService wxMaterialPhotoService;
	
	@Autowired
	private WxMaterialVideoService wxMaterialVideoService;
	
	@Autowired
	private WxMaterialAudioService wxMaterialAudioService;
	
	@Autowired
	private WxMaterialNewsService wxMaterialNewsService;
	
	@ModelAttribute
	public WxSubscribe get(@RequestParam(required=false) String id) {
		WxSubscribe entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxSubscribeService.get(id);
		}
		if (entity == null){
			entity = new WxSubscribe();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxSubscribe:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxSubscribe wxSubscribe, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxSubscribe> page = wxSubscribeService.findPage(new Page<WxSubscribe>(request, response), wxSubscribe); 
		model.addAttribute("page", page);
		return "modules/ws/wxSubscribeList";
	}

	@RequiresPermissions("ws:wxSubscribe:view")
	@RequestMapping(value = "form")
	public String form(WxSubscribe wxSubscribe, Model model) {
		model.addAttribute("wxSubscribe", wxSubscribe);
		return "modules/ws/wxSubscribeForm";
	}

	@RequiresPermissions("ws:wxSubscribe:edit")
	@RequestMapping(value = "save")
	public String save(WxSubscribe wxSubscribe, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxSubscribe)){
			return form(wxSubscribe, model);
		}
		wxSubscribeService.save(wxSubscribe);
		addMessage(redirectAttributes, "保存关注欢迎语成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxSubscribe/?repage";
	}
	
	@RequiresPermissions("ws:wxSubscribe:edit")
	@RequestMapping(value = "delete")
	public String delete(WxSubscribe wxSubscribe, RedirectAttributes redirectAttributes) {
		wxSubscribeService.delete(wxSubscribe);
		addMessage(redirectAttributes, "删除关注欢迎语成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxSubscribe/?repage";
	}
	
	@RequiresPermissions("ws:wxSubscribe:edit")
	@RequestMapping(value = "templateForm")
	public String templateForm(String msgType , HttpServletRequest request, HttpServletResponse response, Model model) {
		//文本模板选择
		if(msgType.equals(WsConstant.MSG_TYPE_TEXT)){
			WxMaterialText wxMaterialText=new WxMaterialText();
			Page<WxMaterialText> page = wxMaterialTextService.findPage(new Page<WxMaterialText>(request, response), wxMaterialText); 
			model.addAttribute("page", page);
			model.addAttribute("wxMaterialText", wxMaterialText);
			return "modules/ws/wxTemplateTextList";
		}
		if(msgType.equals(WsConstant.MSG_TYPE_IMAGE)){
			WxMaterialPhoto wxMaterialPhoto=new WxMaterialPhoto();
			Page<WxMaterialPhoto> page = wxMaterialPhotoService.findPage(new Page<WxMaterialPhoto>(request, response), wxMaterialPhoto); 
			model.addAttribute("page", page);
			model.addAttribute("wxMaterialPhoto", wxMaterialPhoto);
			return "modules/ws/wxTemplatePhotoList";
		}
		if(msgType.equals(WsConstant.MSG_TYPE_VOICE)){
			WxMaterialAudio wxMaterialAudio=new WxMaterialAudio();
			Page<WxMaterialAudio> page = wxMaterialAudioService.findPage(new Page<WxMaterialAudio>(request, response), wxMaterialAudio); 
			model.addAttribute("page", page);
			model.addAttribute("wxMaterialAudio", wxMaterialAudio);
			return "modules/ws/wxTemplateAudioList";
		}
		if(msgType.equals(WsConstant.MSG_TYPE_VIDEO)){
			WxMaterialVideo wxMaterialVideo=new WxMaterialVideo();
			Page<WxMaterialVideo> page = wxMaterialVideoService.findPage(new Page<WxMaterialVideo>(request, response), wxMaterialVideo); 
			model.addAttribute("page", page);
			model.addAttribute("wxMaterialVideo", wxMaterialVideo);
			return "modules/ws/wxTemplateVideoList";
		}
		if(msgType.equals(WsConstant.MSG_TYPE_NEWS)){
			WxMaterialNews wxMaterialNews=new WxMaterialNews();
			Page<WxMaterialNews> page = wxMaterialNewsService.findPage(new Page<WxMaterialNews>(request, response), wxMaterialNews); 
			model.addAttribute("page", page);
			model.addAttribute("wxMaterialNews", wxMaterialNews);
			return "modules/ws/wxTemplateNewsList";
		}
		return "modules/ws/wxTemplateTextList";
	}

}