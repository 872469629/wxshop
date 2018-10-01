package com.thinkgem.jeesite.modules.ws.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNewsItem;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialNewsItemService;

/**
 * 图文素材明细Controller
 * @author 大胖老师
 * @version 2017-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/ws/wxMaterialNewsItem")
public class WxMaterialNewsItemController extends BaseController {

	@Autowired
	private WxMaterialNewsItemService wxMaterialNewsItemService;
	
	@ModelAttribute
	public WxMaterialNewsItem get(@RequestParam(required=false) String id) {
		WxMaterialNewsItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMaterialNewsItemService.get(id);
		}
		if (entity == null){
			entity = new WxMaterialNewsItem();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(WxMaterialNewsItem wxMaterialNewsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMaterialNewsItem> page = wxMaterialNewsItemService.findPage(new Page<WxMaterialNewsItem>(request, response), wxMaterialNewsItem); 
		model.addAttribute("page", page);
		return "modules/ws/wxMaterialNewsItemList";
	}

	@RequestMapping(value = "form")
	public String form(WxMaterialNewsItem wxMaterialNewsItem, Model model) {
		model.addAttribute("wxMaterialNewsItem", wxMaterialNewsItem);
		return "modules/ws/wxMaterialNewsItemForm";
	}

	@RequestMapping(value = "save")
	public String save(WxMaterialNewsItem wxMaterialNewsItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMaterialNewsItem)){
			return form(wxMaterialNewsItem, model);
		}
		if(!checkItemsCount(wxMaterialNewsItem.getNewsId())){
			addMessage(model, "每条图文的素材不能超过8条");
			return form(wxMaterialNewsItem, model);
		}
		wxMaterialNewsItemService.save(wxMaterialNewsItem);
		addMessage(redirectAttributes, "保存图文素材明细成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialNewsItem/?repage&&newsId="+wxMaterialNewsItem.getNewsId();
	}
	
	@RequestMapping(value = "delete")
	public String delete(WxMaterialNewsItem wxMaterialNewsItem, RedirectAttributes redirectAttributes) {
		wxMaterialNewsItemService.delete(wxMaterialNewsItem);
		addMessage(redirectAttributes, "删除图文素材明细成功");
		return "redirect:"+Global.getAdminPath()+"/ws/wxMaterialNewsItem/?repage";
	}
	
	private boolean checkItemsCount(String newsId){
		/**
		 * 校验每条图文的素材不能超过8条
		 */
		WxMaterialNewsItem item=new WxMaterialNewsItem();
		item.setNewsId(newsId);
		List<WxMaterialNewsItem> itemList=wxMaterialNewsItemService.findList(item);
		if(itemList!=null && itemList.size()>8){
			return false;
		}
		return true;
	}

}