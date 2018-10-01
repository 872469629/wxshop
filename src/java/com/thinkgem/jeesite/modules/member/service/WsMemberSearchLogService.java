package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.entity.WsMemberSearchLog;
import com.thinkgem.jeesite.modules.member.dao.WsMemberSearchLogDao;

/**
 * 用户搜索记录管理Service
 * @author 大胖老师
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberSearchLogService extends CrudService<WsMemberSearchLogDao, WsMemberSearchLog> {

	public WsMemberSearchLog get(String id) {
		return super.get(id);
	}
	
	public List<WsMemberSearchLog> findList(WsMemberSearchLog wsMemberSearchLog) {
		return super.findList(wsMemberSearchLog);
	}
	
	public Page<WsMemberSearchLog> findPage(Page<WsMemberSearchLog> page, WsMemberSearchLog wsMemberSearchLog) {
		return super.findPage(page, wsMemberSearchLog);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMemberSearchLog wsMemberSearchLog) {
		super.save(wsMemberSearchLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMemberSearchLog wsMemberSearchLog) {
		super.delete(wsMemberSearchLog);
	}
	
}