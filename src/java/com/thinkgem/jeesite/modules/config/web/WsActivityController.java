package com.thinkgem.jeesite.modules.config.web;

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
import com.thinkgem.jeesite.modules.config.entity.WsActivity;
import com.thinkgem.jeesite.modules.config.service.WsActivityService;
import com.thinkgem.jeesite.modules.prod.entity.WsBrand;

/**
 * 活动配置Controller
 * @author 大胖老师
 * @version 2017-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/config/wsActivity")
public class WsActivityController extends BaseController {

	@Autowired
	private WsActivityService wsActivityService;
	
	@ModelAttribute
	public WsActivity get(@RequestParam(required=false) String id) {
		WsActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsActivityService.get(id);
		}
		if (entity == null){
			entity = new WsActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("config:wsActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsActivity wsActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsActivity> page = wsActivityService.findPage(new Page<WsActivity>(request, response), wsActivity); 
		model.addAttribute("page", page);
		return "modules/config/wsActivityList";
	}
	
	@RequestMapping(value = "activityAdSelectList")
	public String activityAdSelectList(WsActivity wsActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsActivity> page = wsActivityService.findPage(new Page<WsActivity>(request, response), wsActivity); 
		model.addAttribute("page", page);
        return "modules/config/wsActivitySelectList";
	}

	@RequiresPermissions("config:wsActivity:view")
	@RequestMapping(value = "form")
	public String form(WsActivity wsActivity, Model model) {
		model.addAttribute("wsActivity", wsActivity);
		return "modules/config/wsActivityForm";
	}

	@RequiresPermissions("config:wsActivity:edit")
	@RequestMapping(value = "save")
	public String save(WsActivity wsActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsActivity)){
			return form(wsActivity, model);
		}
		wsActivityService.save(wsActivity);
		addMessage(redirectAttributes, "保存活动配置成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsActivity/?repage";
	}
	
	@RequiresPermissions("config:wsActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(WsActivity wsActivity, RedirectAttributes redirectAttributes) {
		wsActivityService.delete(wsActivity);
		addMessage(redirectAttributes, "删除活动配置成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsActivity/?repage";
	}

}