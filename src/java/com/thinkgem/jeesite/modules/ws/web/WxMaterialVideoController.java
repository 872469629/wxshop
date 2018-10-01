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
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialVideo;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialVideoService;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;

/**
 * 视频素材Controller
 * @author 大胖老师
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMaterialVideo")
public class WxMaterialVideoController extends BaseController {

	@Autowired
	private WxMaterialVideoService wxMaterialVideoService;
	
	@ModelAttribute
	public WxMaterialVideo get(@RequestParam(required=false) String id) {
		WxMaterialVideo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMaterialVideoService.get(id);
		}
		if (entity == null){
			entity = new WxMaterialVideo();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxMaterialVideo:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMaterialVideo wxMaterialVideo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialVideo> page = wxMaterialVideoService.findPage(new Page<WxMaterialVideo>(request, response), wxMaterialVideo); 
		model.addAttribute("page", page);
		return "modules/ws/wxMaterialVideoList";
	}
	
	@RequiresPermissions("ws:wxMaterialVideo:view")
	@RequestMapping(value = "templateList")
	public String templateList(WxMaterialVideo wxMaterialVideo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialVideo> page = wxMaterialVideoService.findPage(new Page<WxMaterialVideo>(request, response), wxMaterialVideo); 
		model.addAttribute("page", page);
		return "modules/ws/wxTemplateVideoList";
	}

	@RequiresPermissions("ws:wxMaterialVideo:view")
	@RequestMapping(value = "form")
	public String form(WxMaterialVideo wxMaterialVideo, Model model) {
		model.addAttribute("wxMaterialVideo", wxMaterialVideo);
		return "modules/ws/wxMaterialVideoForm";
	}

	@RequiresPermissions("ws:wxMaterialVideo:edit")
	@RequestMapping(value = "save")
	public String save(WxMaterialVideo wxMaterialVideo, Model model, RedirectAttributes redirectAttributes) throws WexinReqException {
		if (!beanValidator(model, wxMaterialVideo)){
			return form(wxMaterialVideo, model);
		}
		wxMaterialVideoService.synDelAudio(wxMaterialVideo);
		String mediaId=wxMaterialVideoService.synAddVideo(wxMaterialVideo);
		wxMaterialVideo.setMediaId(mediaId);
		wxMaterialVideoService.save(wxMaterialVideo);
		addMessage(redirectAttributes, "保存视频素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialVideo/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialVideo:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMaterialVideo wxMaterialVideo, RedirectAttributes redirectAttributes) throws WexinReqException {
		wxMaterialVideoService.synDelAudio(wxMaterialVideo);
		wxMaterialVideoService.delete(wxMaterialVideo);
		addMessage(redirectAttributes, "删除视频素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialVideo/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialVideo:edit")
	@RequestMapping(value = "synVideo")
	public String synVideo(WxMaterialVideo wxMaterialVideo, Model model, RedirectAttributes redirectAttributes) throws WexinReqException {
		wxMaterialVideo=wxMaterialVideoService.get(wxMaterialVideo.getId());
		wxMaterialVideoService.synDelAudio(wxMaterialVideo);
		String mediaId=wxMaterialVideoService.synAddVideo(wxMaterialVideo);
		wxMaterialVideo.setMediaId(mediaId);
		wxMaterialVideoService.save(wxMaterialVideo);
		addMessage(redirectAttributes, "同步素材到微信成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialVideo/?repage";
	}

}