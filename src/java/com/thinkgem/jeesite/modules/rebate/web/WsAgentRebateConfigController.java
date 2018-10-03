package com.thinkgem.jeesite.modules.rebate.web;

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
import com.thinkgem.jeesite.modules.rebate.entity.WsAgentRebateConfig;
import com.thinkgem.jeesite.modules.rebate.service.WsAgentRebateConfigService;

/**
 * 代理商分销配置Controller
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rebate/wsAgentRebateConfig")
public class WsAgentRebateConfigController extends BaseController {

	@Autowired
	private WsAgentRebateConfigService wsAgentRebateConfigService;
	
	@ModelAttribute
	public WsAgentRebateConfig get(@RequestParam(required=false) String id) {
		WsAgentRebateConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsAgentRebateConfigService.get(id);
		}
		if (entity == null){
			entity = new WsAgentRebateConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("rebate:wsAgentRebateConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsAgentRebateConfig wsAgentRebateConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsAgentRebateConfig> page = wsAgentRebateConfigService.findPage(new Page<WsAgentRebateConfig>(request, response), wsAgentRebateConfig); 
		model.addAttribute("page", page);
		return "modules/rebate/wsAgentRebateConfigList";
	}

	@RequiresPermissions("rebate:wsAgentRebateConfig:view")
	@RequestMapping(value = "form")
	public String form(WsAgentRebateConfig wsAgentRebateConfig, Model model) {
		model.addAttribute("wsAgentRebateConfig", wsAgentRebateConfig);
		return "modules/rebate/wsAgentRebateConfigForm";
	}

	@RequiresPermissions("rebate:wsAgentRebateConfig:edit")
	@RequestMapping(value = "save")
	public String save(WsAgentRebateConfig wsAgentRebateConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsAgentRebateConfig)){
			return form(wsAgentRebateConfig, model);
		}
		wsAgentRebateConfigService.save(wsAgentRebateConfig);
		addMessage(redirectAttributes, "保存代理商分销配置成功");
		return "redirect:"+Global.getAdminPath()+"/rebate/wsAgentRebateConfig/?repage";
	}
	
	@RequiresPermissions("rebate:wsAgentRebateConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(WsAgentRebateConfig wsAgentRebateConfig, RedirectAttributes redirectAttributes) {
		wsAgentRebateConfigService.delete(wsAgentRebateConfig);
		addMessage(redirectAttributes, "删除代理商分销配置成功");
		return "redirect:"+Global.getAdminPath()+"/rebate/wsAgentRebateConfig/?repage";
	}

}