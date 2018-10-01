package com.thinkgem.jeesite.modules.ws.web;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.SeqUtils;
import com.thinkgem.jeesite.modules.ws.entity.WxMenu;
import com.thinkgem.jeesite.modules.ws.service.WxMenuService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 微信菜单Controller
 * @author 大胖老师
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMenu")
public class WxMenuController extends BaseController {

	@Autowired
	private WxMenuService wxMenuService;
	
	@ModelAttribute
	public WxMenu get(@RequestParam(required=false) String id) {
		WxMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMenuService.get(id);
		}
		if (entity == null){
			entity = new WxMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("ws:wxMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMenu wxMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<WxMenu> list = wxMenuService.findList(wxMenu); 
		for (WxMenu menu:list) {
			if(menu.getParent().getId().equals("0")){
				menu.setIsParent(true);
			}else{
				menu.setIsParent(false);
			}
		}
		model.addAttribute("list", list);
		return "modules/ws/wxMenuList";
	}

	@RequiresPermissions("ws:wxMenu:view")
	@RequestMapping(value = "form")
	public String form(WxMenu wxMenu, Model model) {
		if (wxMenu.getParent()!=null && StringUtils.isNotBlank(wxMenu.getParent().getId())){
			wxMenu.setParent(wxMenuService.get(wxMenu.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(wxMenu.getId())){
				WxMenu wxMenuChild = new WxMenu();
				wxMenuChild.setParent(new WxMenu(wxMenu.getParent().getId()));
				List<WxMenu> list = wxMenuService.findList(wxMenu); 
				if (list.size() > 0){
					wxMenu.setSort(list.get(list.size()-1).getSort());
					if (wxMenu.getSort() != null){
						wxMenu.setSort(wxMenu.getSort() + 30);
					}
				}
			}
		}
		if (wxMenu.getSort() == null){
			wxMenu.setSort(30);
		}
		model.addAttribute("wxMenu", wxMenu);
		return "modules/ws/wxMenuForm";
	}

	@RequiresPermissions("ws:wxMenu:edit")
	@RequestMapping(value = "save")
	public String save(WxMenu wxMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMenu)){
			return form(wxMenu, model);
		}
		if(StringUtils.isEmpty(wxMenu.getId())){
			wxMenu.setMenuKey(String.valueOf(SeqUtils.getNextSeq(WsConstant.MENU_SEQ)));
		}
		wxMenuService.save(wxMenu);
		addMessage(redirectAttributes, "保存微信菜单成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMenu/?repage";
	}
	
	@RequiresPermissions("ws:wxMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMenu wxMenu, RedirectAttributes redirectAttributes) {
		wxMenuService.delete(wxMenu);
		addMessage(redirectAttributes, "删除微信菜单成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMenu/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		WxMenu menu=new WxMenu();
		menu.setParent(new WxMenu("0"));
		List<WxMenu> list = wxMenuService.findList(menu);
		for (int i=0; i<list.size(); i++){
			WxMenu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "sysMenuButton")
	public Map<String, String> sysMenuButton(WxMenu wxMenu, Model model) {
		Map<String, String> map = Maps.newHashMap();
		map.put("ret", "1");
		try{
			wxMenuService.sysMenuButton(wxMenu);
		}catch(Exception e){
			map.put("ret", "0");
			map.put("message", e.getMessage());
		}finally {
			return map;
		}
	}
}