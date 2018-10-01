package com.thinkgem.jeesite.modules.ws.web;

import java.util.List;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ws.entity.WxAccount;
import com.thinkgem.jeesite.modules.ws.service.WxAccountService;
import com.thinkgem.jeesite.modules.ws.utils.WsCacheUtils;

/**
 * 微信账号配置Controller
 * @author 大胖老师
 * @version 2017-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxAccount")
public class WxAccountController extends BaseController {

	@Autowired
	private WxAccountService wxAccountService;
	
	@ModelAttribute
	public WxAccount get(@RequestParam(required=false) String id) {
		WxAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxAccountService.get(id);
		}
		if (entity == null){
			entity = new WxAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount); 
		model.addAttribute("page", page);
		return "modules/ws/wxAccountList";
	}

	@RequiresPermissions("ws:wxAccount:view")
	@RequestMapping(value = "form")
	public String form(WxAccount wxAccount, Model model) {
		List<WxAccount> wxAccounts=wxAccountService.findList(wxAccount);
		if(wxAccounts!=null && wxAccounts.size()>0){
			wxAccount=wxAccounts.get(0);
		}
		model.addAttribute("wxAccount", wxAccount);
		return "modules/ws/wxAccountForm";
	}

	@RequiresPermissions("ws:wxAccount:edit")
	@RequestMapping(value = "save")
	public String save(WxAccount wxAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxAccount)){
			return form(wxAccount, model);
		}
		wxAccountService.save(wxAccount);
		WsCacheUtils.put("account", wxAccount);
		addMessage(redirectAttributes, "保存微信账号配置成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxAccount/form?repage";
	}
	
	@RequiresPermissions("ws:wxAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(WxAccount wxAccount, RedirectAttributes redirectAttributes) {
		wxAccountService.delete(wxAccount);
		addMessage(redirectAttributes, "删除微信账号配置成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxAccount/?repage";
	}

}