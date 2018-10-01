package com.thinkgem.jeesite.modules.report.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.report.entity.ReportIncreaseDetail;

/**
 * 每日新增数据明细DAO接口
 * @author 大胖老师
 * @version 2018-01-01
 */
@MyBatisDao
public interface ReportIncreaseDetailDao extends CrudDao<ReportIncreaseDetail> {
	
}