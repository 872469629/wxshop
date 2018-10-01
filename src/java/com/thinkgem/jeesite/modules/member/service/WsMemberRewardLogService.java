package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.entity.WsMemberRewardLog;
import com.thinkgem.jeesite.modules.member.dao.WsMemberRewardLogDao;

/**
 * 会员奖励记录Service
 * @author zengyq
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberRewardLogService extends CrudService<WsMemberRewardLogDao, WsMemberRewardLog> {

	public WsMemberRewardLog get(String id) {
		return super.get(id);
	}
	
	public WsMemberRewardLog getSumReward(WsMemberRewardLog wsMemberRewardLog) {
		return dao.getSumReward(wsMemberRewardLog);
	}
	
	public List<WsMemberRewardLog> findList(WsMemberRewardLog wsMemberRewardLog) {
		return super.findList(wsMemberRewardLog);
	}
	
	public Page<WsMemberRewardLog> findPage(Page<WsMemberRewardLog> page, WsMemberRewardLog wsMemberRewardLog) {
		return super.findPage(page, wsMemberRewardLog);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMemberRewardLog wsMemberRewardLog) {
		super.save(wsMemberRewardLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMemberRewardLog wsMemberRewardLog) {
		super.delete(wsMemberRewardLog);
	}
	
}