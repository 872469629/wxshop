package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.dao.WsMessageRecordDao;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;

/**
 * 系统消息Service
 * @author 大胖老师
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsMessageRecordService extends CrudService<WsMessageRecordDao, WsMessageRecord> {

	public WsMessageRecord get(String id) {
		return super.get(id);
	}
	
	public List<WsMessageRecord> findList(WsMessageRecord wsMessageRecord) {
		return super.findList(wsMessageRecord);
	}
	
	public int findCount(WsMessageRecord wsMessageRecord) {
		return dao.findCount(wsMessageRecord);
	}
	
	public Page<WsMessageRecord> findPage(Page<WsMessageRecord> page, WsMessageRecord wsMessageRecord) {
		return super.findPage(page, wsMessageRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMessageRecord wsMessageRecord) {
		super.save(wsMessageRecord);
	}
	
	@Transactional(readOnly = false)
	public void insertByMrank(WsMessageRecord wsMessageRecord) {
		dao.insertByMrank(wsMessageRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMessageRecord wsMessageRecord) {
		super.delete(wsMessageRecord);
	}
	
}