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
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttr;
import com.thinkgem.jeesite.modules.prod.service.WsProdAttrService;

/**
 * 产品属性Controller
 * @author 大胖老师
 * @version 2017-10-02
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsProdAttr")
public class WsProdAttrController extends BaseController {

	@Autowired
	private WsProdAttrService wsProdAttrService;
	
	@ModelAttribute
	public WsProdAttr get(@RequestParam(required=false) String id) {
		WsProdAttr entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsProdAttrService.get(id);
		}
		if (entity == null){
			entity = new WsProdAttr();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsProdAttr:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsProdAttr wsProdAttr, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsProdAttr> page = wsProdAttrService.findPage(new Page<WsProdAttr>(request, response), wsProdAttr); 
		model.addAttribute("page", page);
		return "modules/prod/wsProdAttrList";
	}

	@RequiresPermissions("prod:wsProdAttr:view")
	@RequestMapping(value = "form")
	public String form(WsProdAttr wsProdAttr, Model model) {
		model.addAttribute("wsProdAttr", wsProdAttr);
		return "modules/prod/wsProdAttrForm";
	}

	@RequiresPermissions("prod:wsProdAttr:edit")
	@RequestMapping(value = "save")
	public String save(WsProdAttr wsProdAttr, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsProdAttr)){
			return form(wsProdAttr, model);
		}
		wsProdAttrService.save(wsProdAttr);
		addMessage(redirectAttributes, "保存产品属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdAttr/?repage";
	}
	
	@RequiresPermissions("prod:wsProdAttr:edit")
	@RequestMapping(value = "delete")
	public String delete(WsProdAttr wsProdAttr, RedirectAttributes redirectAttributes) {
		wsProdAttrService.delete(wsProdAttr);
		addMessage(redirectAttributes, "删除产品属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdAttr/?repage";
	}

}