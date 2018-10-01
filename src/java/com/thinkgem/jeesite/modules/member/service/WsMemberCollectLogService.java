package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCollectLog;
import com.thinkgem.jeesite.modules.member.dao.WsMemberCollectLogDao;

/**
 * 用户收藏记录Service
 * @author 大胖老师
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberCollectLogService extends CrudService<WsMemberCollectLogDao, WsMemberCollectLog> {

	public WsMemberCollectLog get(String id) {
		return super.get(id);
	}
	
	public List<WsMemberCollectLog> findList(WsMemberCollectLog wsMemberCollectLog) {
		return super.findList(wsMemberCollectLog);
	}
	
	public Page<WsMemberCollectLog> findPage(Page<WsMemberCollectLog> page, WsMemberCollectLog wsMemberCollectLog) {
		return super.findPage(page, wsMemberCollectLog);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMemberCollectLog wsMemberCollectLog) {
		super.save(wsMemberCollectLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMemberCollectLog wsMemberCollectLog) {
		super.delete(wsMemberCollectLog);
	}
	
}