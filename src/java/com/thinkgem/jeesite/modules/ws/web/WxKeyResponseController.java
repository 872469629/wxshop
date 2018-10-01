package com.thinkgem.jeesite.modules.ws.web;

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
import com.thinkgem.jeesite.modules.ws.entity.WxKeyResponse;
import com.thinkgem.jeesite.modules.ws.service.WxKeyResponseService;

/**
 * 关键字回复Controller
 * @author 大胖老师
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxKeyResponse")
public class WxKeyResponseController extends BaseController {

	@Autowired
	private WxKeyResponseService wxKeyResponseService;
	
	@ModelAttribute
	public WxKeyResponse get(@RequestParam(required=false) String id) {
		WxKeyResponse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxKeyResponseService.get(id);
		}
		if (entity == null){
			entity = new WxKeyResponse();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxKeyResponse:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxKeyResponse wxKeyResponse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxKeyResponse> page = wxKeyResponseService.findPage(new Page<WxKeyResponse>(request, response), wxKeyResponse); 
		model.addAttribute("page", page);
		return "modules/ws/wxKeyResponseList";
	}

	@RequiresPermissions("ws:wxKeyResponse:view")
	@RequestMapping(value = "form")
	public String form(WxKeyResponse wxKeyResponse, Model model) {
		model.addAttribute("wxKeyResponse", wxKeyResponse);
		return "modules/ws/wxKeyResponseForm";
	}

	@RequiresPermissions("ws:wxKeyResponse:edit")
	@RequestMapping(value = "save")
	public String save(WxKeyResponse wxKeyResponse, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxKeyResponse)){
			return form(wxKeyResponse, model);
		}
		wxKeyResponseService.save(wxKeyResponse);
		addMessage(redirectAttributes, "保存关键字回复成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxKeyResponse/?repage";
	}
	
	@RequiresPermissions("ws:wxKeyResponse:edit")
	@RequestMapping(value = "delete")
	public String delete(WxKeyResponse wxKeyResponse, RedirectAttributes redirectAttributes) {
		wxKeyResponseService.delete(wxKeyResponse);
		addMessage(redirectAttributes, "删除关键字回复成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxKeyResponse/?repage";
	}

}