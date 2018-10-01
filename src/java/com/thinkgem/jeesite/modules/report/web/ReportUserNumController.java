package com.thinkgem.jeesite.modules.report.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.report.entity.ReportUserNum;
import com.thinkgem.jeesite.modules.report.service.ReportUserNumService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/**
 * 用户数据统计Controller
 * @author 大胖老师
 * @version 2017-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/report/reportUserNum")
public class ReportUserNumController extends BaseController {

	@Autowired
	private ReportUserNumService reportUserNumService;
	
	@ModelAttribute
	public ReportUserNum get(@RequestParam(required=false) String id) {
		ReportUserNum entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportUserNumService.get(id);
		}
		if (entity == null){
			entity = new ReportUserNum();
		}
		return entity;
	}
	
	@RequiresPermissions("report:reportUserNum:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportUserNum reportUserNum, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportUserNum> page = reportUserNumService.findPage(new Page<ReportUserNum>(request, response), reportUserNum); 
		model.addAttribute("page", page);
		return "modules/report/reportUserNumList";
	}

	@RequiresPermissions("report:reportUserNum:view")
	@RequestMapping(value = "form")
	public String form(ReportUserNum reportUserNum, Model model) {
		model.addAttribute("reportUserNum", reportUserNum);
		return "modules/report/reportUserNumForm";
	}

	@RequiresPermissions("report:reportUserNum:edit")
	@RequestMapping(value = "save")
	public String save(ReportUserNum reportUserNum, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportUserNum)){
			return form(reportUserNum, model);
		}
		reportUserNumService.save(reportUserNum);
		addMessage(redirectAttributes, "保存用户数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/report/reportUserNum/?repage";
	}
	
	@RequiresPermissions("report:reportUserNum:edit")
	@RequestMapping(value = "delete")
	public String delete(ReportUserNum reportUserNum, RedirectAttributes redirectAttributes) {
		reportUserNumService.delete(reportUserNum);
		addMessage(redirectAttributes, "删除用户数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/report/reportUserNum/?repage";
	}
	
	@RequiresPermissions("report:reportUserNum:view")
	@RequestMapping(value = "report")
	public void report(ReportUserNum reportUserNum,HttpServletRequest request, HttpServletResponse response)    
	          throws IOException, JRException {
		//如果查询日期为空，则默认查询昨天的数据
		if(reportUserNum.getCountDate()==null){
			reportUserNum.setCountDate(DateUtils.getBetweenDateStr(-1,"yyyy-MM-dd"));
		}
		String ctxpath = request.getSession().getServletContext().getRealPath("/jasper/report_user_num.jasper");            
		List<ReportUserNum> reportUserNumList= reportUserNumService.findList(reportUserNum);
	    File reFile = new File(ctxpath);    
	    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportUserNumList); 
	    JRBeanCollectionDataSource dsCrosstab = new JRBeanCollectionDataSource(reportUserNumList);
	    Map parameters = new HashMap();//根据变量来查询   
	    parameters.put("userNumPie", dsCrosstab);
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reFile.getPath());  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);    
	    request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint); 
	    response.setContentType("application/pdf;charset=utf-8");
	    JRPdfExporter exporter = new JRPdfExporter();   
	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
	    exporter.exportReport();
	    
	}  

}