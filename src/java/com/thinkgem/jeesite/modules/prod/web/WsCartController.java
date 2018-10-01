package com.thinkgem.jeesite.modules.prod.web;

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
import com.thinkgem.jeesite.modules.prod.entity.WsCart;
import com.thinkgem.jeesite.modules.prod.service.WsCartService;

/**
 * 购物车管理Controller
 * @author 大胖老师
 * @version 2017-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsCart")
public class WsCartController extends BaseController {

	@Autowired
	private WsCartService wsCartService;
	
	@ModelAttribute
	public WsCart get(@RequestParam(required=false) String id) {
		WsCart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsCartService.get(id);
		}
		if (entity == null){
			entity = new WsCart();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsCart:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsCart wsCart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsCart> page = wsCartService.findPage(new Page<WsCart>(request, response), wsCart); 
		model.addAttribute("page", page);
		return "modules/prod/wsCartList";
	}

	@RequiresPermissions("prod:wsCart:view")
	@RequestMapping(value = "form")
	public String form(WsCart wsCart, Model model) {
		model.addAttribute("wsCart", wsCart);
		return "modules/prod/wsCartForm";
	}

	@RequiresPermissions("prod:wsCart:edit")
	@RequestMapping(value = "save")
	public String save(WsCart wsCart, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsCart)){
			return form(wsCart, model);
		}
		wsCartService.save(wsCart);
		addMessage(redirectAttributes, "保存购物车管理成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsCart/?repage";
	}
	
	@RequiresPermissions("prod:wsCart:edit")
	@RequestMapping(value = "delete")
	public String delete(WsCart wsCart, RedirectAttributes redirectAttributes) {
		wsCartService.delete(wsCart);
		addMessage(redirectAttributes, "删除购物车管理成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsCart/?repage";
	}

}