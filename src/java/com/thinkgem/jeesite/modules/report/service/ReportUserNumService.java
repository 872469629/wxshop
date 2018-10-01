package com.thinkgem.jeesite.modules.report.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.report.entity.ReportUserNum;
import com.thinkgem.jeesite.modules.report.dao.ReportUserNumDao;

/**
 * 用户数据统计Service
 * @author 大胖老师
 * @version 2017-11-06
 */
@Service
@Transactional(readOnly = true)
public class ReportUserNumService extends CrudService<ReportUserNumDao, ReportUserNum> {

	public ReportUserNum get(String id) {
		return super.get(id);
	}
	
	public List<ReportUserNum> findList(ReportUserNum reportUserNum) {
		return super.findList(reportUserNum);
	}
	
	public Page<ReportUserNum> findPage(Page<ReportUserNum> page, ReportUserNum reportUserNum) {
		return super.findPage(page, reportUserNum);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportUserNum reportUserNum) {
		super.save(reportUserNum);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportUserNum reportUserNum) {
		super.delete(reportUserNum);
	}
	
}