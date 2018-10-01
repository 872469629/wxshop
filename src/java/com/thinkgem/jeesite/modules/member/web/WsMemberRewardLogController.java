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
import com.thinkgem.jeesite.modules.member.entity.WsMemberRewardLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberRewardLogService;

/**
 * 会员奖励记录Controller
 * @author zengyq
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMemberRewardLog")
public class WsMemberRewardLogController extends BaseController {

	@Autowired
	private WsMemberRewardLogService wsMemberRewardLogService;
	
	@ModelAttribute
	public WsMemberRewardLog get(@RequestParam(required=false) String id) {
		WsMemberRewardLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberRewardLogService.get(id);
		}
		if (entity == null){
			entity = new WsMemberRewardLog();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMemberRewardLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMemberRewardLog wsMemberRewardLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMemberRewardLog> page = wsMemberRewardLogService.findPage(new Page<WsMemberRewardLog>(request, response), wsMemberRewardLog); 
		model.addAttribute("page", page);
		return "modules/member/wsMemberRewardLogList";
	}

	@RequiresPermissions("member:wsMemberRewardLog:view")
	@RequestMapping(value = "form")
	public String form(WsMemberRewardLog wsMemberRewardLog, Model model) {
		model.addAttribute("wsMemberRewardLog", wsMemberRewardLog);
		return "modules/member/wsMemberRewardLogForm";
	}

	@RequiresPermissions("member:wsMemberRewardLog:edit")
	@RequestMapping(value = "save")
	public String save(WsMemberRewardLog wsMemberRewardLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMemberRewardLog)){
			return form(wsMemberRewardLog, model);
		}
		wsMemberRewardLogService.save(wsMemberRewardLog);
		addMessage(redirectAttributes, "保存会员奖励记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberRewardLog/?repage";
	}
	
	@RequiresPermissions("member:wsMemberRewardLog:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMemberRewardLog wsMemberRewardLog, RedirectAttributes redirectAttributes) {
		wsMemberRewardLogService.delete(wsMemberRewardLog);
		addMessage(redirectAttributes, "删除会员奖励记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberRewardLog/?repage";
	}

}