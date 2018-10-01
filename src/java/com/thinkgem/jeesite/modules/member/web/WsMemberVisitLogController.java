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
import com.thinkgem.jeesite.modules.member.entity.WsMemberVisitLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberVisitLogService;

/**
 * 用户访问记录Controller
 * @author 大胖老师
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMemberVisitLog")
public class WsMemberVisitLogController extends BaseController {

	@Autowired
	private WsMemberVisitLogService wsMemberVisitLogService;
	
	@ModelAttribute
	public WsMemberVisitLog get(@RequestParam(required=false) String id) {
		WsMemberVisitLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberVisitLogService.get(id);
		}
		if (entity == null){
			entity = new WsMemberVisitLog();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMemberVisitLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMemberVisitLog wsMemberVisitLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMemberVisitLog> page = wsMemberVisitLogService.findPage(new Page<WsMemberVisitLog>(request, response), wsMemberVisitLog); 
		model.addAttribute("page", page);
		return "modules/member/wsMemberVisitLogList";
	}

	@RequiresPermissions("member:wsMemberVisitLog:view")
	@RequestMapping(value = "form")
	public String form(WsMemberVisitLog wsMemberVisitLog, Model model) {
		model.addAttribute("wsMemberVisitLog", wsMemberVisitLog);
		return "modules/member/wsMemberVisitLogForm";
	}

	@RequiresPermissions("member:wsMemberVisitLog:edit")
	@RequestMapping(value = "save")
	public String save(WsMemberVisitLog wsMemberVisitLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMemberVisitLog)){
			return form(wsMemberVisitLog, model);
		}
		wsMemberVisitLogService.save(wsMemberVisitLog);
		addMessage(redirectAttributes, "保存用户访问记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberVisitLog/?repage";
	}
	
	@RequiresPermissions("member:wsMemberVisitLog:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMemberVisitLog wsMemberVisitLog, RedirectAttributes redirectAttributes) {
		wsMemberVisitLogService.delete(wsMemberVisitLog);
		addMessage(redirectAttributes, "删除用户访问记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberVisitLog/?repage";
	}

}