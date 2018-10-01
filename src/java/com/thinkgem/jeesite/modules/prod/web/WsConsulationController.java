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
import com.thinkgem.jeesite.modules.prod.entity.WsConsulation;
import com.thinkgem.jeesite.modules.prod.service.WsConsulationService;

/**
 * 评论回复管理Controller
 * @author 大胖老师
 * @version 2017-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsConsulation")
public class WsConsulationController extends BaseController {

	@Autowired
	private WsConsulationService wsConsulationService;
	
	@ModelAttribute
	public WsConsulation get(@RequestParam(required=false) String id) {
		WsConsulation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsConsulationService.get(id);
		}
		if (entity == null){
			entity = new WsConsulation();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsConsulation:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsConsulation wsConsulation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsConsulation> page = wsConsulationService.findPage(new Page<WsConsulation>(request, response), wsConsulation); 
		model.addAttribute("page", page);
		return "modules/prod/wsConsulationList";
	}

	@RequiresPermissions("prod:wsConsulation:view")
	@RequestMapping(value = "form")
	public String form(WsConsulation wsConsulation, Model model) {
		model.addAttribute("wsConsulation", wsConsulation);
		return "modules/prod/wsConsulationForm";
	}

	@RequiresPermissions("prod:wsConsulation:edit")
	@RequestMapping(value = "save")
	public String save(WsConsulation wsConsulation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsConsulation)){
			return form(wsConsulation, model);
		}
		wsConsulationService.save(wsConsulation);
		addMessage(redirectAttributes, "保存评论回复管理成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsConsulation/?repage";
	}
	
	@RequiresPermissions("prod:wsConsulation:edit")
	@RequestMapping(value = "delete")
	public String delete(WsConsulation wsConsulation, RedirectAttributes redirectAttributes) {
		wsConsulationService.delete(wsConsulation);
		addMessage(redirectAttributes, "删除评论回复管理成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsConsulation/?repage";
	}

}