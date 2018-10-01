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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.report.entity.ReportProductSel;
import com.thinkgem.jeesite.modules.report.entity.ReportUserNum;
import com.thinkgem.jeesite.modules.report.service.ReportProductSelService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/**
 * 热销产品Controller
 * @author water
 * @version 2017-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/report/reportProductSel")
public class ReportProductSelController extends BaseController {

	@Autowired
	private ReportProductSelService reportProductSelService;
	
	@ModelAttribute
	public ReportProductSel get(@RequestParam(required=false) String id) {
		ReportProductSel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportProductSelService.get(id);
		}
		if (entity == null){
			entity = new ReportProductSel();
		}
		return entity;
	}
	
	@RequiresPermissions("report:reportProductSel:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportProductSel reportProductSel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportProductSel> page = reportProductSelService.findPage(new Page<ReportProductSel>(request, response), reportProductSel); 
		model.addAttribute("page", page);
		return "modules/report/reportProductSelList";
	}
	
	
	@RequiresPermissions("report:reportProductSel:view")
	@RequestMapping(value = "report")
	public void report(ReportProductSel reportProductSel,HttpServletRequest request, HttpServletResponse response)    
	          throws IOException, JRException {
		String prdname = request.getParameter("prdname");
		System.out.println("=========="+prdname);
		//如果查询日期为空，则默认查询昨天的数据
		if(reportProductSel.getCountDate()==null){
//			reportProductSel.setCountDate(DateUtils.getBetweenDateStr(-1,"yyyy-MM-dd"));
			reportProductSel.setProductName(prdname);
		}
	
		String ctxpath = request.getSession().getServletContext().getRealPath("/jasper/reprot_prodsel.jasper");            
		List<ReportProductSel> reportProductSelList= reportProductSelService.findList(reportProductSel);
		
		
	    File reFile = new File(ctxpath);    
	    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportProductSelList); 
	    JRBeanCollectionDataSource dsCrosstab = new JRBeanCollectionDataSource(reportProductSelList);
	    Map parameters = new HashMap();//根据变量来查询   
	    parameters.put("productSelList", dsCrosstab);
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reFile.getPath());  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);    
	    request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint); 
	    response.setContentType("application/pdf;charset=utf-8");
	    JRPdfExporter exporter = new JRPdfExporter();   
	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
	    exporter.exportReport();
	    
	    
	    /**
	     * html 样式的文件
	     */
//	    JRHtmlExporter exporter1 = new JRHtmlExporter();   
//	    //这里也是解决HTML报表图片不显示的 
//	    exporter1.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath()+"/servlets/image?image=");  	    
//	    //输出为html格式的报表文件    	
//	    exporter1.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);    
//	    exporter1.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE); 
//	    //移除设计报表中的空白行 
////	    exporter1.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);    
//	    exporter1.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "utf-8");  
//	    exporter1.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM,response.getOutputStream());
//	    exporter1.exportReport();   
	    
	    
	    
	     
	 
	    
	    
	}  
	
	
	
	
	

	@RequiresPermissions("report:reportProductSel:view")
	@RequestMapping(value = "form")
	public String form(ReportProductSel reportProductSel, Model model) {
		model.addAttribute("reportProductSel", reportProductSel);
		return "modules/report/reportProductSelForm";
	}

	@RequiresPermissions("report:reportProductSel:edit")
	@RequestMapping(value = "save")
	public String save(ReportProductSel reportProductSel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportProductSel)){
			return form(reportProductSel, model);
		}
		reportProductSelService.save(reportProductSel);
		addMessage(redirectAttributes, "保存热销产品成功");
		return "redirect:"+Global.getAdminPath()+"/report/reportProductSel/?repage";
	}
	
	
	
	
	@RequiresPermissions("report:reportProductSel:edit")
	@RequestMapping(value = "delete")
	public String delete(ReportProductSel reportProductSel, RedirectAttributes redirectAttributes) {
		reportProductSelService.delete(reportProductSel);
		addMessage(redirectAttributes, "删除热销产品成功");
		return "redirect:"+Global.getAdminPath()+"/report/reportProductSel/?repage";
	}

}