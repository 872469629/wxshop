package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.entity.WsMemberConsumeLog;
import com.thinkgem.jeesite.modules.member.dao.WsMemberConsumeLogDao;

/**
 * 用户消费记录Service
 * @author zengyq
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberConsumeLogService extends CrudService<WsMemberConsumeLogDao, WsMemberConsumeLog> {

	public WsMemberConsumeLog get(String id) {
		return super.get(id);
	}
	
	public List<WsMemberConsumeLog> findList(WsMemberConsumeLog wsMemberConsumeLog) {
		return super.findList(wsMemberConsumeLog);
	}
	
	public Page<WsMemberConsumeLog> findPage(Page<WsMemberConsumeLog> page, WsMemberConsumeLog wsMemberConsumeLog) {
		return super.findPage(page, wsMemberConsumeLog);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMemberConsumeLog wsMemberConsumeLog) {
		super.save(wsMemberConsumeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMemberConsumeLog wsMemberConsumeLog) {
		super.delete(wsMemberConsumeLog);
	}
	
}