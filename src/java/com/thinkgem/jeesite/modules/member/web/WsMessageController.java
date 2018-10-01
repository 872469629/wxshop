package com.thinkgem.jeesite.modules.member.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.config.service.WsMrankService;
import com.thinkgem.jeesite.modules.member.entity.WsMessage;
import com.thinkgem.jeesite.modules.member.service.WsMessageService;

/**
 * 系统消息Controller
 * @author 大胖老师
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMessage")
public class WsMessageController extends BaseController {

	@Autowired
	private WsMessageService wsMessageService;
	
	@Autowired
	private WsMrankService wsMrankService;
	
	@ModelAttribute
	public WsMessage get(@RequestParam(required=false) String id) {
		WsMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMessageService.get(id);
		}
		if (entity == null){
			entity = new WsMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMessage wsMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMessage> page = wsMessageService.findPage(new Page<WsMessage>(request, response), wsMessage); 
		model.addAttribute("page", page);
		return "modules/member/wsMessageList";
	}

	@RequiresPermissions("member:wsMessage:view")
	@RequestMapping(value = "form")
	public String form(WsMessage wsMessage, Model model) {
		model.addAttribute("wsMessage", wsMessage);
		WsMrank wsMrank=new WsMrank();
		List<WsMrank> wsMrankList = wsMrankService.findList(wsMrank);
		model.addAttribute("wsMrankList", wsMrankList);
		return "modules/member/wsMessageForm";
	}

	@RequiresPermissions("member:wsMessage:edit")
	@RequestMapping(value = "save")
	public String save(WsMessage wsMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMessage)){
			return form(wsMessage, model);
		}
		wsMessageService.save(wsMessage);
		addMessage(redirectAttributes, "保存系统消息成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMessage/?repage";
	}
	
	@RequiresPermissions("member:wsMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMessage wsMessage, RedirectAttributes redirectAttributes) {
		wsMessageService.delete(wsMessage);
		addMessage(redirectAttributes, "删除系统消息成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMessage/?repage";
	}

	/**
	 * 获取会员JSON数据。
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		WsMrank wsMrank = new WsMrank();
		wsMrank.setDelFlag(wsMrank.DEL_FLAG_NORMAL);
		List<WsMrank> list = wsMrankService.findList(wsMrank);
		for (int i=0; i<list.size(); i++){
			WsMrank e = list.get(i);
            if (true){
                Map<String, Object> map = Maps.newHashMap();
                map.put("pId", "0");
                map.put("pIds", "0,");
                map.put("id", e.getId());
                map.put("name", e.getName());
                map.put("isParent", false);
                mapList.add(map);
            }
		}
		return mapList;
	}
}