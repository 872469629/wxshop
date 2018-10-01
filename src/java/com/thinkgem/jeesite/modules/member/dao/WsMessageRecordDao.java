package com.thinkgem.jeesite.modules.member.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;

/**
 * 通知通告发送管理DAO接口
 * @author 通知通告发送管理
 * @version 2017-11-17
 */
@MyBatisDao
public interface WsMessageRecordDao extends CrudDao<WsMessageRecord> {
	
	public int findCount(WsMessageRecord wsMessageRecord);
	
	public void insertByMrank(WsMessageRecord wsMessageRecord);
}