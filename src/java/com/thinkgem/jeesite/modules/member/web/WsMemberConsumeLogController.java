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
import com.thinkgem.jeesite.modules.member.entity.WsMemberConsumeLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberConsumeLogService;

/**
 * 用户消费记录Controller
 * @author zengyq
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMemberConsumeLog")
public class WsMemberConsumeLogController extends BaseController {

	@Autowired
	private WsMemberConsumeLogService wsMemberConsumeLogService;
	
	@ModelAttribute
	public WsMemberConsumeLog get(@RequestParam(required=false) String id) {
		WsMemberConsumeLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberConsumeLogService.get(id);
		}
		if (entity == null){
			entity = new WsMemberConsumeLog();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMemberConsumeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMemberConsumeLog wsMemberConsumeLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMemberConsumeLog> page = wsMemberConsumeLogService.findPage(new Page<WsMemberConsumeLog>(request, response), wsMemberConsumeLog); 
		model.addAttribute("page", page);
		return "modules/member/wsMemberConsumeLogList";
	}

	@RequiresPermissions("member:wsMemberConsumeLog:view")
	@RequestMapping(value = "form")
	public String form(WsMemberConsumeLog wsMemberConsumeLog, Model model) {
		model.addAttribute("wsMemberConsumeLog", wsMemberConsumeLog);
		return "modules/member/wsMemberConsumeLogForm";
	}

	@RequiresPermissions("member:wsMemberConsumeLog:edit")
	@RequestMapping(value = "save")
	public String save(WsMemberConsumeLog wsMemberConsumeLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMemberConsumeLog)){
			return form(wsMemberConsumeLog, model);
		}
		wsMemberConsumeLogService.save(wsMemberConsumeLog);
		addMessage(redirectAttributes, "保存用户消费记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberConsumeLog/?repage";
	}
	
	@RequiresPermissions("member:wsMemberConsumeLog:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMemberConsumeLog wsMemberConsumeLog, RedirectAttributes redirectAttributes) {
		wsMemberConsumeLogService.delete(wsMemberConsumeLog);
		addMessage(redirectAttributes, "删除用户消费记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberConsumeLog/?repage";
	}

}