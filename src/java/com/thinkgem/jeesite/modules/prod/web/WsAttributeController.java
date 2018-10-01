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
import com.thinkgem.jeesite.modules.prod.entity.WsAttribute;
import com.thinkgem.jeesite.modules.prod.service.WsAttributeService;

/**
 * 产品属性Controller
 * @author water
 * @version 2017-08-10
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsAttribute")
public class WsAttributeController extends BaseController {

	@Autowired
	private WsAttributeService wsAttributeService;
	
	@ModelAttribute
	public WsAttribute get(@RequestParam(required=false) String id) {
		WsAttribute entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsAttributeService.get(id);
		}
		if (entity == null){
			entity = new WsAttribute();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsAttribute:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsAttribute wsAttribute, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsAttribute> page = wsAttributeService.findPage(new Page<WsAttribute>(request, response), wsAttribute); 
		model.addAttribute("page", page);
		return "modules/prod/wsAttributeList";
	}

	@RequiresPermissions("prod:wsAttribute:view")
	@RequestMapping(value = "form")
	public String form(WsAttribute wsAttribute, Model model) {
		model.addAttribute("wsAttribute", wsAttribute);
		return "modules/prod/wsAttributeForm";
	}

	@RequiresPermissions("prod:wsAttribute:edit")
	@RequestMapping(value = "save")
	public String save(WsAttribute wsAttribute, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsAttribute)){
			return form(wsAttribute, model);
		}
		wsAttributeService.save(wsAttribute);
		addMessage(redirectAttributes, "保存产品属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsAttribute/?repage";
	}
	
	@RequiresPermissions("prod:wsAttribute:edit")
	@RequestMapping(value = "delete")
	public String delete(WsAttribute wsAttribute, RedirectAttributes redirectAttributes) {
		wsAttributeService.delete(wsAttribute);
		addMessage(redirectAttributes, "删除产品属性成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsAttribute/?repage";
	}

}