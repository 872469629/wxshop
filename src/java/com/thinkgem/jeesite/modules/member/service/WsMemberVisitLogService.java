package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.entity.WsMemberVisitLog;
import com.thinkgem.jeesite.modules.member.dao.WsMemberVisitLogDao;

/**
 * 用户访问记录Service
 * @author 大胖老师
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberVisitLogService extends CrudService<WsMemberVisitLogDao, WsMemberVisitLog> {

	public WsMemberVisitLog get(String id) {
		return super.get(id);
	}
	
	public List<WsMemberVisitLog> findList(WsMemberVisitLog wsMemberVisitLog) {
		return super.findList(wsMemberVisitLog);
	}
	
	public Page<WsMemberVisitLog> findPage(Page<WsMemberVisitLog> page, WsMemberVisitLog wsMemberVisitLog) {
		return super.findPage(page, wsMemberVisitLog);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMemberVisitLog wsMemberVisitLog) {
		super.save(wsMemberVisitLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMemberVisitLog wsMemberVisitLog) {
		super.delete(wsMemberVisitLog);
	}
	
}