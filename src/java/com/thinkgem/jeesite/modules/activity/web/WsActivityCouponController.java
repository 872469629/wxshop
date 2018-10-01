package com.thinkgem.jeesite.modules.activity.web;

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
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCoupon;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCouponProd;
import com.thinkgem.jeesite.modules.activity.service.WsActivityCouponProdService;
import com.thinkgem.jeesite.modules.activity.service.WsActivityCouponService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 优惠券活动Controller
 * @author 大胖老师
 * @version 2017-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/activity/wsActivityCoupon")
public class WsActivityCouponController extends BaseController {

	@Autowired
	private WsActivityCouponService wsActivityCouponService;
	
	@Autowired
	private WsActivityCouponProdService wsActivityCouponProdService;
	
	@ModelAttribute
	public WsActivityCoupon get(@RequestParam(required=false) String id) {
		WsActivityCoupon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsActivityCouponService.get(id);
		}
		if (entity == null){
			entity = new WsActivityCoupon();
		}
		return entity;
	}
	
	@RequiresPermissions("activity:wsActivityCoupon:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsActivityCoupon wsActivityCoupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsActivityCoupon> page = wsActivityCouponService.findPage(new Page<WsActivityCoupon>(request, response), wsActivityCoupon); 
		model.addAttribute("page", page);
		return "modules/activity/wsActivityCouponList";
	}

	@RequiresPermissions("activity:wsActivityCoupon:view")
	@RequestMapping(value = "form")
	public String form(WsActivityCoupon wsActivityCoupon, Model model) {
		WsActivityCouponProd wsActivityCouponProd=new WsActivityCouponProd();
		wsActivityCouponProd.setWsActivityCoupon(wsActivityCoupon);
		String prodRelation="";
		List<WsActivityCouponProd> wsActivityCouponProdList=wsActivityCouponProdService.findList(wsActivityCouponProd);
		for (WsActivityCouponProd prod:wsActivityCouponProdList) {
			prodRelation=prodRelation+prod.getProdId()+",";
		}
		if(StringUtils.isNotEmpty(prodRelation)){
			prodRelation=prodRelation.substring(0, prodRelation.length()-1);
		}
		wsActivityCoupon.setProdRelation(prodRelation);
		model.addAttribute("wsActivityCoupon", wsActivityCoupon);
		return "modules/activity/wsActivityCouponForm";
	}

	@RequiresPermissions("activity:wsActivityCoupon:edit")
	@RequestMapping(value = "save")
	public String save(WsActivityCoupon wsActivityCoupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsActivityCoupon)){
			return form(wsActivityCoupon, model);
		}
		if(wsActivityCoupon!=null && StringUtils.isEmpty(wsActivityCoupon.getState())){
			wsActivityCoupon.setState(WsConstant.VALID);
		}
		wsActivityCouponService.save(wsActivityCoupon);
		addMessage(redirectAttributes, "保存优惠券活动成功");
		return "redirect:"+Global.getAdminPath()+"/activity/wsActivityCoupon/?repage";
	}
	
	@RequiresPermissions("activity:wsActivityCoupon:edit")
	@RequestMapping(value = "delete")
	public String delete(WsActivityCoupon wsActivityCoupon, RedirectAttributes redirectAttributes) {
		wsActivityCouponService.delete(wsActivityCoupon);
		addMessage(redirectAttributes, "删除优惠券活动成功");
		return "redirect:"+Global.getAdminPath()+"/activity/wsActivityCoupon/?repage";
	}

}