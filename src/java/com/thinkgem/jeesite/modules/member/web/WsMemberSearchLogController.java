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
import com.thinkgem.jeesite.modules.member.entity.WsMemberSearchLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberSearchLogService;

/**
 * 用户搜索记录管理Controller
 * @author 大胖老师
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMemberSearchLog")
public class WsMemberSearchLogController extends BaseController {

	@Autowired
	private WsMemberSearchLogService wsMemberSearchLogService;
	
	@ModelAttribute
	public WsMemberSearchLog get(@RequestParam(required=false) String id) {
		WsMemberSearchLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberSearchLogService.get(id);
		}
		if (entity == null){
			entity = new WsMemberSearchLog();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMemberSearchLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMemberSearchLog wsMemberSearchLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMemberSearchLog> page = wsMemberSearchLogService.findPage(new Page<WsMemberSearchLog>(request, response), wsMemberSearchLog); 
		model.addAttribute("page", page);
		return "modules/member/wsMemberSearchLogList";
	}

	@RequiresPermissions("member:wsMemberSearchLog:view")
	@RequestMapping(value = "form")
	public String form(WsMemberSearchLog wsMemberSearchLog, Model model) {
		model.addAttribute("wsMemberSearchLog", wsMemberSearchLog);
		return "modules/member/wsMemberSearchLogForm";
	}

	@RequiresPermissions("member:wsMemberSearchLog:edit")
	@RequestMapping(value = "save")
	public String save(WsMemberSearchLog wsMemberSearchLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMemberSearchLog)){
			return form(wsMemberSearchLog, model);
		}
		wsMemberSearchLogService.save(wsMemberSearchLog);
		addMessage(redirectAttributes, "保存用户搜索记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberSearchLog/?repage";
	}
	
	@RequiresPermissions("member:wsMemberSearchLog:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMemberSearchLog wsMemberSearchLog, RedirectAttributes redirectAttributes) {
		wsMemberSearchLogService.delete(wsMemberSearchLog);
		addMessage(redirectAttributes, "删除用户搜索记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberSearchLog/?repage";
	}

}