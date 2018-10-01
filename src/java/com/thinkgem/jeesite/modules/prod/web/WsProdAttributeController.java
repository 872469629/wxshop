package com.thinkgem.jeesite.modules.prod.web;

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
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttribute;
import com.thinkgem.jeesite.modules.prod.service.WsProdAttributeService;

/**
 * 产品属性Controller
 * @author water
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsProdAttribute")
public class WsProdAttributeController extends BaseController {

	@Autowired
	private WsProdAttributeService wsProdAttributeService;
	
	@ModelAttribute
	public WsProdAttribute get(@RequestParam(required=false) String id) {
		WsProdAttribute entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsProdAttributeService.get(id);
		}
		if (entity == null){
			entity = new WsProdAttribute();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsProdAttribute:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsProdAttribute wsProdAttribute, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsProdAttribute> page = wsProdAttributeService.findPage(new Page<WsProdAttribute>(request, response), wsProdAttribute); 
		model.addAttribute("page", page);
		return "modules/prod/wsProdAttributeList";
	}

	@RequiresPermissions("prod:wsProdAttribute:view")
	@RequestMapping(value = "form")
	public String form(WsProdAttribute wsProdAttribute, Model model) {
		model.addAttribute("wsProdAttribute", wsProdAttribute);
		return "modules/prod/wsProdAttributeForm";
	}

	@RequiresPermissions("prod:wsProdAttribute:edit")
	@RequestMapping(value = "save")
	public String save(WsProdAttribute wsProdAttribute, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsProdAttribute)){
			return form(wsProdAttribute, model);
		}
		wsProdAttributeService.save(wsProdAttribute);
		addMessage(redirectAttributes, "保存产品属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdAttribute/?repage";
	}
	
	@RequiresPermissions("prod:wsProdAttribute:edit")
	@RequestMapping(value = "delete")
	public String delete(WsProdAttribute wsProdAttribute, RedirectAttributes redirectAttributes) {
		wsProdAttributeService.delete(wsProdAttribute);
		addMessage(redirectAttributes, "删除产品属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdAttribute/?repage";
	}

}