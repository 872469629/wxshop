package com.thinkgem.jeesite.modules.withdrawal.web;

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
import com.thinkgem.jeesite.modules.withdrawal.entity.WsWithdrawal;
import com.thinkgem.jeesite.modules.withdrawal.service.WsWithdrawalService;

/**
 * 提现Controller
 * @author 分销系统开发者
 * @version 2018-10-07
 */
@Controller
@RequestMapping(value = "${adminPath}/withdrawal/wsWithdrawal")
public class WsWithdrawalController extends BaseController {

	@Autowired
	private WsWithdrawalService wsWithdrawalService;
	
	@ModelAttribute
	public WsWithdrawal get(@RequestParam(required=false) String id) {
		WsWithdrawal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsWithdrawalService.get(id);
		}
		if (entity == null){
			entity = new WsWithdrawal();
		}
		return entity;
	}
	
	@RequiresPermissions("withdrawal:wsWithdrawal:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsWithdrawal wsWithdrawal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsWithdrawal> page = wsWithdrawalService.findPage(new Page<WsWithdrawal>(request, response), wsWithdrawal); 
		model.addAttribute("page", page);
		return "modules/withdrawal/wsWithdrawalList";
	}

	@RequiresPermissions("withdrawal:wsWithdrawal:view")
	@RequestMapping(value = "form")
	public String form(WsWithdrawal wsWithdrawal, Model model) {
		model.addAttribute("wsWithdrawal", wsWithdrawal);
		return "modules/withdrawal/wsWithdrawalForm";
	}

	@RequiresPermissions("withdrawal:wsWithdrawal:edit")
	@RequestMapping(value = "save")
	public String save(WsWithdrawal wsWithdrawal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsWithdrawal)){
			return form(wsWithdrawal, model);
		}
		wsWithdrawalService.audit(wsWithdrawal);
		addMessage(redirectAttributes, "保存提现成功");
		return "redirect:"+Global.getAdminPath()+"/withdrawal/wsWithdrawal/?repage";
	}
	
	@RequiresPermissions("withdrawal:wsWithdrawal:edit")
	@RequestMapping(value = "delete")
	public String delete(WsWithdrawal wsWithdrawal, RedirectAttributes redirectAttributes) {
		wsWithdrawalService.delete(wsWithdrawal);
		addMessage(redirectAttributes, "删除提现成功");
		return "redirect:"+Global.getAdminPath()+"/withdrawal/wsWithdrawal/?repage";
	}

}