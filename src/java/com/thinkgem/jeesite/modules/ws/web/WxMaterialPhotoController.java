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
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialPhoto;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialPhotoService;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;

/**
 * 图片素材Controller
 * @author 大胖老师
 * @version 2017-09-28
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMaterialPhoto")
public class WxMaterialPhotoController extends BaseController {

	@Autowired
	private WxMaterialPhotoService wxMaterialPhotoService;
	
	@ModelAttribute
	public WxMaterialPhoto get(@RequestParam(required=false) String id) {
		WxMaterialPhoto entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMaterialPhotoService.get(id);
		}
		if (entity == null){
			entity = new WxMaterialPhoto();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxMaterialPhoto:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMaterialPhoto wxMaterialPhoto, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialPhoto> page = wxMaterialPhotoService.findPage(new Page<WxMaterialPhoto>(request, response), wxMaterialPhoto); 
		model.addAttribute("page", page);
		return "modules/ws/wxMaterialPhotoList";
	}
	
	@RequiresPermissions("ws:wxMaterialPhoto:view")
	@RequestMapping(value = "templateList")
	public String templateList(WxMaterialPhoto wxMaterialPhoto, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialPhoto> page = wxMaterialPhotoService.findPage(new Page<WxMaterialPhoto>(request, response), wxMaterialPhoto); 
		model.addAttribute("page", page);
		return "modules/ws/wxTemplatePhotoList";
	}

	@RequiresPermissions("ws:wxMaterialPhoto:view")
	@RequestMapping(value = "form")
	public String form(WxMaterialPhoto wxMaterialPhoto, Model model) {
		model.addAttribute("wxMaterialPhoto", wxMaterialPhoto);
		return "modules/ws/wxMaterialPhotoForm";
	}

	@RequiresPermissions("ws:wxMaterialPhoto:edit")
	@RequestMapping(value = "save")
	public String save(WxMaterialPhoto wxMaterialPhoto, Model model, RedirectAttributes redirectAttributes) throws WexinReqException {
		if (!beanValidator(model, wxMaterialPhoto)){
			return form(wxMaterialPhoto, model);
		}
		wxMaterialPhotoService.synDelPhoto(wxMaterialPhoto);
		String mediaId=wxMaterialPhotoService.synAddPhoto(wxMaterialPhoto);
		wxMaterialPhoto.setMediaId(mediaId);
		wxMaterialPhotoService.save(wxMaterialPhoto);
		addMessage(redirectAttributes, "保存图片素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialPhoto/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialPhoto:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMaterialPhoto wxMaterialPhoto, RedirectAttributes redirectAttributes) throws WexinReqException {
		wxMaterialPhotoService.synDelPhoto(wxMaterialPhoto);
		wxMaterialPhotoService.delete(wxMaterialPhoto);
		addMessage(redirectAttributes, "删除图片素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialPhoto/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialPhoto:edit")
	@RequestMapping(value = "synPhoto")
	public String synPhoto(WxMaterialPhoto wxMaterialPhoto, Model model, RedirectAttributes redirectAttributes) throws WexinReqException {
		wxMaterialPhoto = wxMaterialPhotoService.get(wxMaterialPhoto.getId());
		wxMaterialPhotoService.synDelPhoto(wxMaterialPhoto);
		String mediaId=wxMaterialPhotoService.synAddPhoto(wxMaterialPhoto);
		wxMaterialPhoto.setMediaId(mediaId);
		wxMaterialPhotoService.save(wxMaterialPhoto);
		addMessage(redirectAttributes, "同步素材到微信成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialPhoto/?repage";
	}

}