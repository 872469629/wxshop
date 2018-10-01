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
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialText;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialTextService;

/**
 * 文本素材Controller
 * @author 大胖老师
 * @version 2017-09-28
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMaterialText")
public class WxMaterialTextController extends BaseController {

	@Autowired
	private WxMaterialTextService wxMaterialTextService;
	
	@ModelAttribute
	public WxMaterialText get(@RequestParam(required=false) String id) {
		WxMaterialText entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMaterialTextService.get(id);
		}
		if (entity == null){
			entity = new WxMaterialText();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxMaterialText:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMaterialText wxMaterialText, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialText> page = wxMaterialTextService.findPage(new Page<WxMaterialText>(request, response), wxMaterialText); 
		model.addAttribute("page", page);
		return "modules/ws/wxMaterialTextList";
	}
	
	@RequiresPermissions("ws:wxMaterialText:view")
	@RequestMapping(value = "templateList")
	public String templateList(WxMaterialText wxMaterialText, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialText> page = wxMaterialTextService.findPage(new Page<WxMaterialText>(request, response), wxMaterialText); 
		model.addAttribute("page", page);
		return "modules/ws/wxTemplateTextList";
	}

	@RequiresPermissions("ws:wxMaterialText:view")
	@RequestMapping(value = "form")
	public String form(WxMaterialText wxMaterialText, Model model) {
		model.addAttribute("wxMaterialText", wxMaterialText);
		return "modules/ws/wxMaterialTextForm";
	}

	@RequiresPermissions("ws:wxMaterialText:edit")
	@RequestMapping(value = "save")
	public String save(WxMaterialText wxMaterialText, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMaterialText)){
			return form(wxMaterialText, model);
		}
		wxMaterialTextService.save(wxMaterialText);
		addMessage(redirectAttributes, "保存文本素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialText/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialText:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMaterialText wxMaterialText, RedirectAttributes redirectAttributes) {
		wxMaterialTextService.delete(wxMaterialText);
		addMessage(redirectAttributes, "删除文本素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialText/?repage";
	}

}