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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.prod.entity.WsBrand;
import com.thinkgem.jeesite.modules.prod.service.WsBrandService;

/**
 * 品牌Controller
 * @author water
 * @version 2017-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsBrand")
public class WsBrandController extends BaseController {

	@Autowired
	private WsBrandService wsBrandService;
	
	@ModelAttribute
	public WsBrand get(@RequestParam(required=false) String id) {
		WsBrand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsBrandService.get(id);
		}
		if (entity == null){
			entity = new WsBrand();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsBrand:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsBrand wsBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsBrand> page = wsBrandService.findPage(new Page<WsBrand>(request, response), wsBrand); 
		model.addAttribute("page", page);
		return "modules/prod/wsBrandList";
	}
	
	@RequestMapping(value = "dialogList")
	public String dialogList(WsBrand wsBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsBrand> page = wsBrandService.findPage(new Page<WsBrand>(request, response), wsBrand); 
		model.addAttribute("page", page);
		return "modules/prod/wsBrandDialogList";
	}
	
	@RequestMapping(value = "brandAdSelectList")
	public String brandAdSelectList(WsBrand wsBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsBrand> page = wsBrandService.findPage(new Page<WsBrand>(request, response), wsBrand); 
		model.addAttribute("page", page);
        return "modules/prod/wsBrandSelectList";
	}

	@RequiresPermissions("prod:wsBrand:view")
	@RequestMapping(value = "form")
	public String form(WsBrand wsBrand, Model model) {
		model.addAttribute("wsBrand", wsBrand);
		return "modules/prod/wsBrandForm";
	}

	@RequiresPermissions("prod:wsBrand:edit")
	@RequestMapping(value = "save")
	public String save(WsBrand wsBrand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsBrand)){
			return form(wsBrand, model);
		}
		wsBrandService.save(wsBrand);
		addMessage(redirectAttributes, "保存品牌成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsBrand/?repage";
	}
	
	@RequiresPermissions("prod:wsBrand:edit")
	@RequestMapping(value = "delete")
	public String delete(WsBrand wsBrand, RedirectAttributes redirectAttributes) {
		wsBrandService.delete(wsBrand);
		addMessage(redirectAttributes, "删除品牌成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsBrand/?repage";
	}

}