package com.thinkgem.jeesite.modules.report.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.report.entity.ReportUserNum;

/**
 * 用户数据统计DAO接口
 * @author 大胖老师
 * @version 2017-11-06
 */
@MyBatisDao
public interface ReportUserNumDao extends CrudDao<ReportUserNum> {
	
}