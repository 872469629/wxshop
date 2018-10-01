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
import com.thinkgem.jeesite.modules.ws.service.WxMaterialAudioService;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;

/**
 * 音频素材Controller
 * @author 大胖老师
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMaterialAudio")
public class WxMaterialAudioController extends BaseController {

	@Autowired
	private WxMaterialAudioService wxMaterialAudioService;
	
	@ModelAttribute
	public WxMaterialAudio get(@RequestParam(required=false) String id) {
		WxMaterialAudio entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMaterialAudioService.get(id);
		}
		if (entity == null){
			entity = new WxMaterialAudio();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxMaterialAudio:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMaterialAudio wxMaterialAudio, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialAudio> page = wxMaterialAudioService.findPage(new Page<WxMaterialAudio>(request, response), wxMaterialAudio); 
		model.addAttribute("page", page);
		return "modules/ws/wxMaterialAudioList";
	}
	
	@RequiresPermissions("ws:wxMaterialAudio:view")
	@RequestMapping(value = "templateList")
	public String templateList(WxMaterialAudio wxMaterialAudio, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialAudio> page = wxMaterialAudioService.findPage(new Page<WxMaterialAudio>(request, response), wxMaterialAudio); 
		model.addAttribute("page", page);
		return "modules/ws/wxTemplateAudioList";
	}

	@RequiresPermissions("ws:wxMaterialAudio:view")
	@RequestMapping(value = "form")
	public String form(WxMaterialAudio wxMaterialAudio, Model model) {
		model.addAttribute("wxMaterialAudio", wxMaterialAudio);
		return "modules/ws/wxMaterialAudioForm";
	}

	@RequiresPermissions("ws:wxMaterialAudio:edit")
	@RequestMapping(value = "save")
	public String save(WxMaterialAudio wxMaterialAudio, Model model, RedirectAttributes redirectAttributes) throws WexinReqException {
		if (!beanValidator(model, wxMaterialAudio)){
			return form(wxMaterialAudio, model);
		}
		wxMaterialAudioService.synDelAudio(wxMaterialAudio);
		String mediaId=wxMaterialAudioService.synAddAudio(wxMaterialAudio);
		wxMaterialAudio.setMediaId(mediaId);
		wxMaterialAudioService.save(wxMaterialAudio);
		addMessage(redirectAttributes, "保存音频素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialAudio/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialAudio:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMaterialAudio wxMaterialAudio, RedirectAttributes redirectAttributes) throws WexinReqException {
		wxMaterialAudioService.synDelAudio(wxMaterialAudio);
		wxMaterialAudioService.delete(wxMaterialAudio);
		addMessage(redirectAttributes, "删除音频素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialAudio/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialAudio:edit")
	@RequestMapping(value = "synAudio")
	public String synAudio(WxMaterialAudio wxMaterialAudio, Model model, RedirectAttributes redirectAttributes) throws WexinReqException {
		wxMaterialAudio=wxMaterialAudioService.get(wxMaterialAudio.getId());
		wxMaterialAudioService.synDelAudio(wxMaterialAudio);
		String mediaId=wxMaterialAudioService.synAddAudio(wxMaterialAudio);
		wxMaterialAudio.setMediaId(mediaId);
		wxMaterialAudioService.save(wxMaterialAudio);
		addMessage(redirectAttributes, "同步素材到微信成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialAudio/?repage";
	}

}