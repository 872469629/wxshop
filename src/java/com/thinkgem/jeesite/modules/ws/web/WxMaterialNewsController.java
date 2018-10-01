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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNews;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialNewsService;

/**
 * 图文素材Controller
 * @author 大胖老师
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMaterialNews")
public class WxMaterialNewsController extends BaseController {

	@Autowired
	private WxMaterialNewsService wxMaterialNewsService;
	
	@ModelAttribute
	public WxMaterialNews get(@RequestParam(required=false) String id) {
		WxMaterialNews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMaterialNewsService.get(id);
		}
		if (entity == null){
			entity = new WxMaterialNews();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxMaterialNews:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMaterialNews wxMaterialNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialNews> page = wxMaterialNewsService.findPage(new Page<WxMaterialNews>(request, response), wxMaterialNews); 
		model.addAttribute("page", page);
		return "modules/ws/wxMaterialNewsList";
	}
	
	@RequiresPermissions("ws:wxMaterialNews:view")
	@RequestMapping(value = "templateList")
	public String templateList(WxMaterialNews wxMaterialNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialNews> page = wxMaterialNewsService.findPage(new Page<WxMaterialNews>(request, response), wxMaterialNews); 
		model.addAttribute("page", page);
		return "modules/ws/wxTemplateNewsList";
	}

	@RequiresPermissions("ws:wxMaterialNews:view")
	@RequestMapping(value = "form")
	public String form(WxMaterialNews wxMaterialNews, Model model) {
		model.addAttribute("wxMaterialNews", wxMaterialNews);
		return "modules/ws/wxMaterialNewsForm";
	}

	@RequiresPermissions("ws:wxMaterialNews:edit")
	@RequestMapping(value = "save")
	public String save(WxMaterialNews wxMaterialNews, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMaterialNews)){
			return form(wxMaterialNews, model);
		}
		wxMaterialNewsService.save(wxMaterialNews);
		addMessage(redirectAttributes, "保存图文素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialNews/?repage";
	}
	
	@RequiresPermissions("ws:wxMaterialNews:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMaterialNews wxMaterialNews, RedirectAttributes redirectAttributes) {
		wxMaterialNewsService.delete(wxMaterialNews);
		addMessage(redirectAttributes, "删除图文素材成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialNews/?repage";
	}

}