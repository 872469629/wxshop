package com.thinkgem.jeesite.modules.member.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.member.entity.WsMemberVisitLog;

/**
 * 用户访问记录DAO接口
 * @author 大胖老师
 * @version 2017-12-09
 */
@MyBatisDao
public interface WsMemberVisitLogDao extends CrudDao<WsMemberVisitLog> {
	
}