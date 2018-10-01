package com.thinkgem.jeesite.modules.member.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.member.dao.WsMessageDao;
import com.thinkgem.jeesite.modules.member.entity.WsMessage;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 系统消息Service
 * @author 大胖老师
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsMessageService extends CrudService<WsMessageDao, WsMessage> {
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;

	public WsMessage get(String id) {
		return super.get(id);
	}
	
	public List<WsMessage> findList(WsMessage wsMessage) {
		return super.findList(wsMessage);
	}
	
	public Page<WsMessage> findPage(Page<WsMessage> page, WsMessage wsMessage) {
		return super.findPage(page, wsMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMessage wsMessage) {
		wsMessage.setSendDate(new Date());
		wsMessage.setSender(UserUtils.getUser().getName());
		super.save(wsMessage);
		List<WsMrank> wsMrankList=wsMessage.getWsMrankList();
		for (WsMrank wsMrank : wsMrankList) {
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberRankId(wsMrank.getId());
			wsMessageRecord.setMessageId(wsMessage.getId());
			wsMessageRecord.setDelFlag(wsMessageRecord.DEL_FLAG_NORMAL);
			wsMessageRecord.setReadFlag(WsConstant.NO);
			wsMessageRecordService.insertByMrank(wsMessageRecord);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMessage wsMessage) {
		super.delete(wsMessage);
	}
	
}