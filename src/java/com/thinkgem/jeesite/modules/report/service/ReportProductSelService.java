package com.thinkgem.jeesite.modules.report.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.report.entity.ReportProductSel;
import com.thinkgem.jeesite.modules.report.dao.ReportProductSelDao;

/**
 * 热销产品Service
 * @author water
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class ReportProductSelService extends CrudService<ReportProductSelDao, ReportProductSel> {

	public ReportProductSel get(String id) {
		return super.get(id);
	}
	
	public List<ReportProductSel> findList(ReportProductSel reportProductSel) {
		return super.findList(reportProductSel);
	}
	
	public Page<ReportProductSel> findPage(Page<ReportProductSel> page, ReportProductSel reportProductSel) {
		return super.findPage(page, reportProductSel);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportProductSel reportProductSel) {
		super.save(reportProductSel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportProductSel reportProductSel) {
		super.delete(reportProductSel);
	}
	
}