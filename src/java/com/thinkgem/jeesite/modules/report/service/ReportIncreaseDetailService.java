package com.thinkgem.jeesite.modules.report.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.report.entity.ReportIncreaseDetail;
import com.thinkgem.jeesite.modules.report.dao.ReportIncreaseDetailDao;

/**
 * 每日新增数据明细Service
 * @author 大胖老师
 * @version 2018-01-01
 */
@Service
@Transactional(readOnly = true)
public class ReportIncreaseDetailService extends CrudService<ReportIncreaseDetailDao, ReportIncreaseDetail> {

	public ReportIncreaseDetail get(String id) {
		return super.get(id);
	}
	
	public List<ReportIncreaseDetail> findList(ReportIncreaseDetail reportIncreaseDetail) {
		return super.findList(reportIncreaseDetail);
	}
	
	public Page<ReportIncreaseDetail> findPage(Page<ReportIncreaseDetail> page, ReportIncreaseDetail reportIncreaseDetail) {
		return super.findPage(page, reportIncreaseDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportIncreaseDetail reportIncreaseDetail) {
		super.save(reportIncreaseDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportIncreaseDetail reportIncreaseDetail) {
		super.delete(reportIncreaseDetail);
	}
	
}