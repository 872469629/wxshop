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
import com.thinkgem.jeesite.modules.prod.entity.WsProdSkuAttr;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuAttrService;

/**
 * 产品sku属性Controller
 * @author 大胖老师
 * @version 2017-10-02
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsProdSkuAttr")
public class WsProdSkuAttrController extends BaseController {

	@Autowired
	private WsProdSkuAttrService wsProdSkuAttrService;
	
	@ModelAttribute
	public WsProdSkuAttr get(@RequestParam(required=false) String id) {
		WsProdSkuAttr entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsProdSkuAttrService.get(id);
		}
		if (entity == null){
			entity = new WsProdSkuAttr();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsProdSkuAttr:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsProdSkuAttr wsProdSkuAttr, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsProdSkuAttr> page = wsProdSkuAttrService.findPage(new Page<WsProdSkuAttr>(request, response), wsProdSkuAttr); 
		model.addAttribute("page", page);
		return "modules/prod/wsProdSkuAttrList";
	}

	@RequiresPermissions("prod:wsProdSkuAttr:view")
	@RequestMapping(value = "form")
	public String form(WsProdSkuAttr wsProdSkuAttr, Model model) {
		model.addAttribute("wsProdSkuAttr", wsProdSkuAttr);
		return "modules/prod/wsProdSkuAttrForm";
	}

	@RequiresPermissions("prod:wsProdSkuAttr:edit")
	@RequestMapping(value = "save")
	public String save(WsProdSkuAttr wsProdSkuAttr, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsProdSkuAttr)){
			return form(wsProdSkuAttr, model);
		}
		wsProdSkuAttrService.save(wsProdSkuAttr);
		addMessage(redirectAttributes, "保存产品sku属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdSkuAttr/?repage";
	}
	
	@RequiresPermissions("prod:wsProdSkuAttr:edit")
	@RequestMapping(value = "delete")
	public String delete(WsProdSkuAttr wsProdSkuAttr, RedirectAttributes redirectAttributes) {
		wsProdSkuAttrService.delete(wsProdSkuAttr);
		addMessage(redirectAttributes, "删除产品sku属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdSkuAttr/?repage";
	}

}