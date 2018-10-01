package com.thinkgem.jeesite.modules.member.web;

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
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;
import com.thinkgem.jeesite.modules.member.service.WsMemberCouponService;

/**
 * 用户优惠券Controller
 * @author zengyq
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMemberCoupon")
public class WsMemberCouponController extends BaseController {

	@Autowired
	private WsMemberCouponService wsMemberCouponService;
	
	@ModelAttribute
	public WsMemberCoupon get(@RequestParam(required=false) String id) {
		WsMemberCoupon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberCouponService.get(id);
		}
		if (entity == null){
			entity = new WsMemberCoupon();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMemberCoupon:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMemberCoupon wsMemberCoupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMemberCoupon> page = wsMemberCouponService.findPage(new Page<WsMemberCoupon>(request, response), wsMemberCoupon); 
		model.addAttribute("page", page);
		return "modules/member/wsMemberCouponList";
	}

	@RequiresPermissions("member:wsMemberCoupon:view")
	@RequestMapping(value = "form")
	public String form(WsMemberCoupon wsMemberCoupon, Model model) {
		model.addAttribute("wsMemberCoupon", wsMemberCoupon);
		return "modules/member/wsMemberCouponForm";
	}

	@RequiresPermissions("member:wsMemberCoupon:edit")
	@RequestMapping(value = "save")
	public String save(WsMemberCoupon wsMemberCoupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMemberCoupon)){
			return form(wsMemberCoupon, model);
		}
		wsMemberCouponService.save(wsMemberCoupon);
		addMessage(redirectAttributes, "保存用户优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberCoupon/?repage";
	}
	
	@RequiresPermissions("member:wsMemberCoupon:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMemberCoupon wsMemberCoupon, RedirectAttributes redirectAttributes) {
		wsMemberCouponService.delete(wsMemberCoupon);
		addMessage(redirectAttributes, "删除用户优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberCoupon/?repage";
	}

}