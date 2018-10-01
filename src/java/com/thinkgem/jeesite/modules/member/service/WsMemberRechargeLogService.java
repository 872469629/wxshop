package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.entity.WsMemberRechargeLog;
import com.thinkgem.jeesite.modules.member.dao.WsMemberRechargeLogDao;

/**
 * 用户充值记录Service
 * @author zengyq
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberRechargeLogService extends CrudService<WsMemberRechargeLogDao, WsMemberRechargeLog> {

	public WsMemberRechargeLog get(String id) {
		return super.get(id);
	}
	
	public List<WsMemberRechargeLog> findList(WsMemberRechargeLog wsMemberRechargeLog) {
		return super.findList(wsMemberRechargeLog);
	}
	
	public Page<WsMemberRechargeLog> findPage(Page<WsMemberRechargeLog> page, WsMemberRechargeLog wsMemberRechargeLog) {
		return super.findPage(page, wsMemberRechargeLog);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMemberRechargeLog wsMemberRechargeLog) {
		super.save(wsMemberRechargeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMemberRechargeLog wsMemberRechargeLog) {
		super.delete(wsMemberRechargeLog);
	}
	
}