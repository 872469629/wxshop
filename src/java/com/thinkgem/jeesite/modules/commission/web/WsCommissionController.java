package com.thinkgem.jeesite.modules.commission.web;

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
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;
import com.thinkgem.jeesite.modules.commission.service.WsCommissionService;

/**
 * 分销明细Controller
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@Controller
@RequestMapping(value = "${adminPath}/commission/wsCommission")
public class WsCommissionController extends BaseController {

	@Autowired
	private WsCommissionService wsCommissionService;
	
	@ModelAttribute
	public WsCommission get(@RequestParam(required=false) String id) {
		WsCommission entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsCommissionService.get(id);
		}
		if (entity == null){
			entity = new WsCommission();
		}
		return entity;
	}
	
	@RequiresPermissions("commission:wsCommission:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsCommission wsCommission, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsCommission> page = wsCommissionService.findPage(new Page<WsCommission>(request, response), wsCommission); 
		model.addAttribute("page", page);
		return "modules/commission/wsCommissionList";
	}

	@RequiresPermissions("commission:wsCommission:view")
	@RequestMapping(value = "form")
	public String form(WsCommission wsCommission, Model model) {
		model.addAttribute("wsCommission", wsCommission);
		return "modules/commission/wsCommissionForm";
	}

	@RequiresPermissions("commission:wsCommission:edit")
	@RequestMapping(value = "save")
	public String save(WsCommission wsCommission, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsCommission)){
			return form(wsCommission, model);
		}
		wsCommissionService.save(wsCommission);
		addMessage(redirectAttributes, "保存分销明细成功");
		return "redirect:"+Global.getAdminPath()+"/commission/wsCommission/?repage";
	}
	
	@RequiresPermissions("commission:wsCommission:edit")
	@RequestMapping(value = "delete")
	public String delete(WsCommission wsCommission, RedirectAttributes redirectAttributes) {
		wsCommissionService.delete(wsCommission);
		addMessage(redirectAttributes, "删除分销明细成功");
		return "redirect:"+Global.getAdminPath()+"/commission/wsCommission/?repage";
	}

}