package com.thinkgem.jeesite.modules.order.web;

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
import com.thinkgem.jeesite.modules.order.entity.WsWarn;
import com.thinkgem.jeesite.modules.order.service.WsWarnService;

/**
 * 商城异常数据告警Controller
 * @author 大胖老师
 * @version 2018-01-01
 */
@Controller
@RequestMapping(value = "${adminPath}/order/wsWarn")
public class WsWarnController extends BaseController {

	@Autowired
	private WsWarnService wsWarnService;
	
	@ModelAttribute
	public WsWarn get(@RequestParam(required=false) String id) {
		WsWarn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsWarnService.get(id);
		}
		if (entity == null){
			entity = new WsWarn();
		}
		return entity;
	}
	
	@RequiresPermissions("order:wsWarn:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsWarn wsWarn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsWarn> page = wsWarnService.findPage(new Page<WsWarn>(request, response), wsWarn); 
		model.addAttribute("page", page);
		return "modules/order/wsWarnList";
	}

	@RequiresPermissions("order:wsWarn:view")
	@RequestMapping(value = "form")
	public String form(WsWarn wsWarn, Model model) {
		model.addAttribute("wsWarn", wsWarn);
		return "modules/order/wsWarnForm";
	}

	@RequiresPermissions("order:wsWarn:edit")
	@RequestMapping(value = "save")
	public String save(WsWarn wsWarn, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsWarn)){
			return form(wsWarn, model);
		}
		wsWarnService.save(wsWarn);
		addMessage(redirectAttributes, "保存商城异常数据告警成功");
		return "redirect:"+Global.getAdminPath()+"/order/wsWarn/?repage";
	}
	
	@RequiresPermissions("order:wsWarn:edit")
	@RequestMapping(value = "delete")
	public String delete(WsWarn wsWarn, RedirectAttributes redirectAttributes) {
		wsWarnService.delete(wsWarn);
		addMessage(redirectAttributes, "删除商城异常数据告警成功");
		return "redirect:"+Global.getAdminPath()+"/order/wsWarn/?repage";
	}

}