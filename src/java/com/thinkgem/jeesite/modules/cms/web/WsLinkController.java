package com.thinkgem.jeesite.modules.cms.web;

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

import com.thinkgem.jeesite.modules.cms.entity.WsLink;
import com.thinkgem.jeesite.modules.cms.entity.WsLinkCategory;
import com.thinkgem.jeesite.modules.cms.service.WsLinkCategoryService;
import com.thinkgem.jeesite.modules.cms.service.WsLinkService;

/**
 * 底部链接Controller
 * @author HF
 * @version 2017-08-09
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/wsLink")
public class WsLinkController extends BaseController {

	@Autowired
	private WsLinkService wsLinkService;
	
	@Autowired
	private WsLinkCategoryService wsLinkCategoryService;
	@ModelAttribute
	public WsLink get(@RequestParam(required=false) String id) {
		WsLink entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsLinkService.get(id);
		}
		if (entity == null){
			entity = new WsLink();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:wsLink:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsLink wsLink, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("linkcategorylist",wsLinkCategoryService.findList(new WsLinkCategory()));
		Page<WsLink> page = wsLinkService.findPage(new Page<WsLink>(request, response), wsLink); 
		model.addAttribute("page", page);
		
		
		return "modules/cms/wsLinkList";
	}

	@RequiresPermissions("cms:wsLink:view")
	@RequestMapping(value = "form")
	public String form(WsLink wsLink, Model model) {
		model.addAttribute("wsLink", wsLink);
		model.addAttribute("linkcategorylist",wsLinkCategoryService.findList(new WsLinkCategory()));
		return "modules/cms/wsLinkForm";
	}

	@RequiresPermissions("cms:wsLink:edit")
	@RequestMapping(value = "save")
	public String save(WsLink wsLink, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsLink)){
			return form(wsLink, model);
		}
		wsLinkService.save(wsLink);
		addMessage(redirectAttributes, "保存底部链接成功");
		return "redirect:"+Global.getAdminPath()+"/cms/wsLink/?repage";
	}
	
	@RequiresPermissions("cms:wsLink:edit")
	@RequestMapping(value = "delete")
	public String delete(WsLink wsLink, RedirectAttributes redirectAttributes) {
		wsLinkService.delete(wsLink);
		addMessage(redirectAttributes, "删除底部链接成功");
		return "redirect:"+Global.getAdminPath()+"/cms/wsLink/?repage";
	}

}