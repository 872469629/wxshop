package com.thinkgem.jeesite.modules.report.web;

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
import com.thinkgem.jeesite.modules.report.entity.ReportIncreaseDetail;
import com.thinkgem.jeesite.modules.report.service.ReportIncreaseDetailService;

/**
 * 每日新增数据明细Controller
 * @author 大胖老师
 * @version 2018-01-01
 */
@Controller
@RequestMapping(value = "${adminPath}/report/reportIncreaseDetail")
public class ReportIncreaseDetailController extends BaseController {

	@Autowired
	private ReportIncreaseDetailService reportIncreaseDetailService;
	
	@ModelAttribute
	public ReportIncreaseDetail get(@RequestParam(required=false) String id) {
		ReportIncreaseDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportIncreaseDetailService.get(id);
		}
		if (entity == null){
			entity = new ReportIncreaseDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("report:reportIncreaseDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportIncreaseDetail reportIncreaseDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportIncreaseDetail> page = reportIncreaseDetailService.findPage(new Page<ReportIncreaseDetail>(request, response), reportIncreaseDetail); 
		model.addAttribute("page", page);
		return "modules/report/reportIncreaseDetailList";
	}

	@RequiresPermissions("report:reportIncreaseDetail:view")
	@RequestMapping(value = "form")
	public String form(ReportIncreaseDetail reportIncreaseDetail, Model model) {
		model.addAttribute("reportIncreaseDetail", reportIncreaseDetail);
		return "modules/report/reportIncreaseDetailForm";
	}

	@RequiresPermissions("report:reportIncreaseDetail:edit")
	@RequestMapping(value = "save")
	public String save(ReportIncreaseDetail reportIncreaseDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportIncreaseDetail)){
			return form(reportIncreaseDetail, model);
		}
		reportIncreaseDetailService.save(reportIncreaseDetail);
		addMessage(redirectAttributes, "保存每日新增数据明细成功");
		return "redirect:"+Global.getAdminPath()+"/report/reportIncreaseDetail/?repage";
	}
	
	@RequiresPermissions("report:reportIncreaseDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(ReportIncreaseDetail reportIncreaseDetail, RedirectAttributes redirectAttributes) {
		reportIncreaseDetailService.delete(reportIncreaseDetail);
		addMessage(redirectAttributes, "删除每日新增数据明细成功");
		return "redirect:"+Global.getAdminPath()+"/report/reportIncreaseDetail/?repage";
	}

}