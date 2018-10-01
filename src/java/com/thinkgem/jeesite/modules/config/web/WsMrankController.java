package com.thinkgem.jeesite.modules.config.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.config.service.WsMrankService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;


/**
 * 会员等级Controller
 * @author water
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/config/wsMrank")
public class WsMrankController extends BaseController {

	@Autowired
	private WsMrankService wsMrankService;
	
	@ModelAttribute
	public WsMrank get(@RequestParam(required=false) String id) {
		WsMrank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMrankService.get(id);
		}
		if (entity == null){
			entity = new WsMrank();
		}
		return entity;
	}
	
	@RequiresPermissions("config:wsMrank:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMrank wsMrank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMrank> pageContion=new Page<WsMrank>(request, response);
		pageContion.setOrderBy("amount");
		Page<WsMrank> page = wsMrankService.findPage(pageContion, wsMrank); 
		model.addAttribute("page", page);
		return "modules/config/wsMrankList";
	}

	@RequiresPermissions("config:wsMrank:view")
	@RequestMapping(value = "form")
	public String form(WsMrank wsMrank, Model model) {
		model.addAttribute("wsMrank", wsMrank);
		return "modules/config/wsMrankForm";
	}

	@RequiresPermissions("config:wsMrank:edit")
	@RequestMapping(value = "save")
	public String save(WsMrank wsMrank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMrank)){
			return form(wsMrank, model);
		}
		WsMrank mrank=new WsMrank();
		mrank.setIsDefault(WsConstant.YES);
		List<WsMrank> mrankList= wsMrankService.findList(mrank);
		if(wsMrank.getIsDefault().equals(WsConstant.NO)&&(mrankList==null||mrankList.size()==0)){
			addMessage(model, "请先设置默认会员等级后再修改此默认会员信息");
			return form(wsMrank, model);
		}
		wsMrankService.save(wsMrank);
		addMessage(redirectAttributes, "保存会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsMrank/?repage";
	}
	
	@RequiresPermissions("config:wsMrank:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMrank wsMrank, RedirectAttributes redirectAttributes) {
		if (wsMrank.getIsDefault().equals(WsConstant.YES)){
			addMessage(redirectAttributes, "请先设置默认会员等级后再删除此配置");
			return "redirect:"+Global.getAdminPath()+"/config/wsMrank/?repage";
		}
		wsMrankService.delete(wsMrank);
		addMessage(redirectAttributes, "删除会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsMrank/?repage";
	}

	//zengyq.20171009 会员等级下拉选项
	@ResponseBody
	//@RequiresPermissions("member:wsMember:view")
	//@RequiresPermissions("config:wsMrank:view")
	@RequestMapping(value = {"listWsMrank"})
	public List<WsMrank> listWsMrank() {
		WsMrank wsMrank = new WsMrank();
		wsMrank.setDelFlag(wsMrank.DEL_FLAG_NORMAL);
		return wsMrankService.findList(wsMrank);
	}
	
}