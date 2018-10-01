package com.thinkgem.jeesite.modules.member.web;

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
import com.thinkgem.jeesite.modules.member.entity.WsMemberRechargeLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberRechargeLogService;

/**
 * 用户充值记录Controller
 * @author zengyq
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMemberRechargeLog")
public class WsMemberRechargeLogController extends BaseController {

	@Autowired
	private WsMemberRechargeLogService wsMemberRechargeLogService;
	
	@ModelAttribute
	public WsMemberRechargeLog get(@RequestParam(required=false) String id) {
		WsMemberRechargeLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberRechargeLogService.get(id);
		}
		if (entity == null){
			entity = new WsMemberRechargeLog();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMemberRechargeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMemberRechargeLog wsMemberRechargeLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMemberRechargeLog> page = wsMemberRechargeLogService.findPage(new Page<WsMemberRechargeLog>(request, response), wsMemberRechargeLog); 
		model.addAttribute("page", page);
		return "modules/member/wsMemberRechargeLogList";
	}

	@RequiresPermissions("member:wsMemberRechargeLog:view")
	@RequestMapping(value = "form")
	public String form(WsMemberRechargeLog wsMemberRechargeLog, Model model) {
		model.addAttribute("wsMemberRechargeLog", wsMemberRechargeLog);
		return "modules/member/wsMemberRechargeLogForm";
	}

	@RequiresPermissions("member:wsMemberRechargeLog:edit")
	@RequestMapping(value = "save")
	public String save(WsMemberRechargeLog wsMemberRechargeLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMemberRechargeLog)){
			return form(wsMemberRechargeLog, model);
		}
		wsMemberRechargeLogService.save(wsMemberRechargeLog);
		addMessage(redirectAttributes, "保存用户充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberRechargeLog/?repage";
	}
	
	@RequiresPermissions("member:wsMemberRechargeLog:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMemberRechargeLog wsMemberRechargeLog, RedirectAttributes redirectAttributes) {
		wsMemberRechargeLogService.delete(wsMemberRechargeLog);
		addMessage(redirectAttributes, "删除用户充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberRechargeLog/?repage";
	}

}