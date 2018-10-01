package com.thinkgem.jeesite.modules.prod.web;

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
import com.thinkgem.jeesite.modules.prod.entity.WsProdCategory;
import com.thinkgem.jeesite.modules.prod.service.WsProdCategoryService;

/**
 * 产品分类Controller
 * @author water
 * @version 2017-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsProdCategory")
public class WsProdCategoryController extends BaseController {

	@Autowired
	private WsProdCategoryService wsProdCategoryService;
	
	@ModelAttribute
	public WsProdCategory get(@RequestParam(required=false) String id) {
		WsProdCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsProdCategoryService.get(id);
		}
		if (entity == null){
			entity = new WsProdCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsProdCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsProdCategory wsProdCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<WsProdCategory> list = wsProdCategoryService.findList(wsProdCategory); 
		for (WsProdCategory prodCategory:list) {
			if(prodCategory.getParent().getId().equals("0")){
				prodCategory.setIsParent(true);
			}else{
				prodCategory.setIsParent(false);
			}
		}
		model.addAttribute("list", list);
		return "modules/prod/wsProdCategoryList";
	}

	@RequiresPermissions("prod:wsProdCategory:view")
	@RequestMapping(value = "form")
	public String form(WsProdCategory wsProdCategory, Model model) {
		if (wsProdCategory.getParent()!=null && StringUtils.isNotBlank(wsProdCategory.getParent().getId())){
			wsProdCategory.setParent(wsProdCategoryService.get(wsProdCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(wsProdCategory.getId())){
				WsProdCategory wsProdCategoryChild = new WsProdCategory();
				wsProdCategoryChild.setParent(new WsProdCategory(wsProdCategory.getParent().getId()));
				List<WsProdCategory> list = wsProdCategoryService.findList(wsProdCategory); 
				if (list.size() > 0){
					wsProdCategory.setSort(list.get(list.size()-1).getSort());
					if (wsProdCategory.getSort() != null){
						wsProdCategory.setSort(wsProdCategory.getSort() + 30);
					}
				}
			}
		}
		if (wsProdCategory.getSort() == null){
			wsProdCategory.setSort(30);
		}
		model.addAttribute("wsProdCategory", wsProdCategory);
		return "modules/prod/wsProdCategoryForm";
	}

	@RequiresPermissions("prod:wsProdCategory:edit")
	@RequestMapping(value = "save")
	public String save(WsProdCategory wsProdCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsProdCategory)){
			return form(wsProdCategory, model);
		}
		wsProdCategoryService.save(wsProdCategory);
		addMessage(redirectAttributes, "保存产品分类成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdCategory/?repage";
	}
	
	@RequiresPermissions("prod:wsProdCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(WsProdCategory wsProdCategory, RedirectAttributes redirectAttributes) {
		wsProdCategoryService.delete(wsProdCategory);
		addMessage(redirectAttributes, "删除产品分类成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProdCategory/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<WsProdCategory> list = wsProdCategoryService.findList(new WsProdCategory());
		for (int i=0; i<list.size(); i++){
			WsProdCategory e = list.get(i);
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
	
}