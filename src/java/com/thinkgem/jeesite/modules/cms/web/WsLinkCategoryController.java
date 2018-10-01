package com.thinkgem.jeesite.modules.cms.web;

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
import com.thinkgem.jeesite.modules.cms.entity.WsLinkCategory;
import com.thinkgem.jeesite.modules.cms.service.WsLinkCategoryService;

/**
 * 链接类型表Controller
 * @author HF
 * @version 2017-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/wsLinkCategory")
public class WsLinkCategoryController extends BaseController {

	@Autowired
	private WsLinkCategoryService wsLinkCategoryService;
	
	@ModelAttribute
	public WsLinkCategory get(@RequestParam(required=false) String id) {
		WsLinkCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsLinkCategoryService.get(id);
		}
		if (entity == null){
			entity = new WsLinkCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:wsLinkCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsLinkCategory wsLinkCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsLinkCategory> page = wsLinkCategoryService.findPage(new Page<WsLinkCategory>(request, response), wsLinkCategory); 
		model.addAttribute("page", page);
		return "modules/cms/wsLinkCategoryList";
	}

	@RequiresPermissions("cms:wsLinkCategory:view")
	@RequestMapping(value = "form")
	public String form(WsLinkCategory wsLinkCategory, Model model) {
		model.addAttribute("wsLinkCategory", wsLinkCategory);
		return "modules/cms/wsLinkCategoryForm";
	}

	@RequiresPermissions("cms:wsLinkCategory:edit")
	@RequestMapping(value = "save")
	public String save(WsLinkCategory wsLinkCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsLinkCategory)){
			return form(wsLinkCategory, model);
		}
		wsLinkCategoryService.save(wsLinkCategory);
		addMessage(redirectAttributes, "保存链接类型表成功");
		return "redirect:"+Global.getAdminPath()+"/cms/wsLinkCategory/?repage";
	}
	
	@RequiresPermissions("cms:wsLinkCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(WsLinkCategory wsLinkCategory, RedirectAttributes redirectAttributes) {
		wsLinkCategoryService.delete(wsLinkCategory);
		addMessage(redirectAttributes, "删除链接类型表成功");
		return "redirect:"+Global.getAdminPath()+"/cms/wsLinkCategory/?repage";
	}

}