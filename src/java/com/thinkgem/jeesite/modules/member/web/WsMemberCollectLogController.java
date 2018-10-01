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
import com.thinkgem.jeesite.modules.member.entity.WsMemberCollectLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberCollectLogService;

/**
 * 用户收藏记录Controller
 * @author 大胖老师
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMemberCollectLog")
public class WsMemberCollectLogController extends BaseController {

	@Autowired
	private WsMemberCollectLogService wsMemberCollectLogService;
	
	@ModelAttribute
	public WsMemberCollectLog get(@RequestParam(required=false) String id) {
		WsMemberCollectLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberCollectLogService.get(id);
		}
		if (entity == null){
			entity = new WsMemberCollectLog();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMemberCollectLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMemberCollectLog wsMemberCollectLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMemberCollectLog> page = wsMemberCollectLogService.findPage(new Page<WsMemberCollectLog>(request, response), wsMemberCollectLog); 
		model.addAttribute("page", page);
		return "modules/member/wsMemberCollectLogList";
	}

	@RequiresPermissions("member:wsMemberCollectLog:view")
	@RequestMapping(value = "form")
	public String form(WsMemberCollectLog wsMemberCollectLog, Model model) {
		model.addAttribute("wsMemberCollectLog", wsMemberCollectLog);
		return "modules/member/wsMemberCollectLogForm";
	}

	@RequiresPermissions("member:wsMemberCollectLog:edit")
	@RequestMapping(value = "save")
	public String save(WsMemberCollectLog wsMemberCollectLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMemberCollectLog)){
			return form(wsMemberCollectLog, model);
		}
		wsMemberCollectLogService.save(wsMemberCollectLog);
		addMessage(redirectAttributes, "保存用户收藏记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberCollectLog/?repage";
	}
	
	@RequiresPermissions("member:wsMemberCollectLog:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMemberCollectLog wsMemberCollectLog, RedirectAttributes redirectAttributes) {
		wsMemberCollectLogService.delete(wsMemberCollectLog);
		addMessage(redirectAttributes, "删除用户收藏记录成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberCollectLog/?repage";
	}

}