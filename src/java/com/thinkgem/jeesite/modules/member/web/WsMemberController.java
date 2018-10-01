package com.thinkgem.jeesite.modules.member.web;

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
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.config.service.WsMrankService;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberAttr;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;

/**
 * 会员资料Controller
 * @author zengyq
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/member/wsMember")
public class WsMemberController extends BaseController {

	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsMrankService wsMrankService;
	
	@ModelAttribute
	public WsMember get(@RequestParam(required=false) String id) {
		WsMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMemberService.get(id);
		}
		if (entity == null){
			entity = new WsMember();
		}
		return entity;
	}
	
	@RequiresPermissions("member:wsMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMember wsMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMember> page = wsMemberService.findPage(new Page<WsMember>(request, response), wsMember); 
		List <WsMrank> listWsMrank = wsMrankService.findList(new WsMrank());
		model.addAttribute("page", page);
		model.addAttribute("listWsMrank",listWsMrank);
		return "modules/member/wsMemberList";
	}

	@RequiresPermissions("member:wsMember:view")
	@RequestMapping(value = "form")
	public String form(WsMember wsMember, Model model) {
		List<WsMemberAttr> wsMemberAttrList = wsMember.getWsMemberAttrList();
		List <WsMrank> listWsMrank = wsMrankService.findList(new WsMrank());
		model.addAttribute("listWsMrank",listWsMrank);
		model.addAttribute("wsMember", wsMember);
		model.addAttribute("wsMemberAttrList", wsMemberAttrList);
		return "modules/member/wsMemberForm";
	}

	@RequiresPermissions("member:wsMember:view")
	@RequestMapping(value = {"list1"})
	public String list1(WsMember wsMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("wsMember.id", wsMember.getId());
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberCoupon/?repage";
	}
	@RequiresPermissions("member:wsMember:view")
	@RequestMapping(value = {"list2"})
	public String list2(WsMember wsMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("wsMember.id", wsMember.getId());
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberConsumeLog/?repage";
	}
	@RequiresPermissions("member:wsMember:view")
	@RequestMapping(value = {"list3"})
	public String list3(WsMember wsMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("wsMember.id", wsMember.getId());
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberRechargeLog/?repage";
	}
	@RequiresPermissions("member:wsMember:view")
	@RequestMapping(value = {"list4"})
	public String list4(WsMember wsMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("wsMember.id", wsMember.getId());
		return "redirect:"+Global.getAdminPath()+"/member/wsMemberRewardLog/?repage";
	}

	@RequiresPermissions("member:wsMember:edit")
	@RequestMapping(value = "save")
	public String save(WsMember wsMember, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMember)){
			return form(wsMember, model);
		}
		wsMember.setMemberRankName(wsMrankService.get(wsMember.getMemberRankId()).getName());
		wsMemberService.save(wsMember);
		addMessage(redirectAttributes, "保存会员资料成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMember/?repage";
	}
	
	@RequiresPermissions("member:wsMember:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMember wsMember, RedirectAttributes redirectAttributes) {
		wsMemberService.delete(wsMember);
		addMessage(redirectAttributes, "删除会员资料成功");
		return "redirect:"+Global.getAdminPath()+"/member/wsMember/?repage";
	}


}