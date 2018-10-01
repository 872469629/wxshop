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
import com.thinkgem.jeesite.modules.config.entity.WsAnnouncement;
import com.thinkgem.jeesite.modules.config.service.WsAnnouncementService;

/**
 * 公告管理Controller
 * @author water
 * @version 2018-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/config/wsAnnouncement")
public class WsAnnouncementController extends BaseController {

	@Autowired
	private WsAnnouncementService wsAnnouncementService;
	
	@ModelAttribute
	public WsAnnouncement get(@RequestParam(required=false) String id) {
		WsAnnouncement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsAnnouncementService.get(id);
		}
		if (entity == null){
			entity = new WsAnnouncement();
		}
		return entity;
	}
	
	@RequiresPermissions("config:wsAnnouncement:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsAnnouncement wsAnnouncement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsAnnouncement> page = wsAnnouncementService.findPage(new Page<WsAnnouncement>(request, response), wsAnnouncement); 
		model.addAttribute("page", page);
		return "modules/config/wsAnnouncementList";
	}

	@RequiresPermissions("config:wsAnnouncement:view")
	@RequestMapping(value = "form")
	public String form(WsAnnouncement wsAnnouncement, Model model) {
		model.addAttribute("wsAnnouncement", wsAnnouncement);
		return "modules/config/wsAnnouncementForm";
	}

	@RequiresPermissions("config:wsAnnouncement:edit")
	@RequestMapping(value = "save")
	public String save(WsAnnouncement wsAnnouncement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsAnnouncement)){
			return form(wsAnnouncement, model);
		}
		wsAnnouncementService.save(wsAnnouncement);
		addMessage(redirectAttributes, "保存公告管理成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsAnnouncement/?repage";
	}
	
	@RequiresPermissions("config:wsAnnouncement:edit")
	@RequestMapping(value = "delete")
	public String delete(WsAnnouncement wsAnnouncement, RedirectAttributes redirectAttributes) {
		wsAnnouncementService.delete(wsAnnouncement);
		addMessage(redirectAttributes, "删除公告管理成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsAnnouncement/?repage";
	}

}