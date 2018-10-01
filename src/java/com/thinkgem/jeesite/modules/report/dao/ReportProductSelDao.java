package com.thinkgem.jeesite.modules.report.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.report.entity.ReportProductSel;

/**
 * 热销产品DAO接口
 * @author water
 * @version 2017-11-07
 */
@MyBatisDao
public interface ReportProductSelDao extends CrudDao<ReportProductSel> {
	
}