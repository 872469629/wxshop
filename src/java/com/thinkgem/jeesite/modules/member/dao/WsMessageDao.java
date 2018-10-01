package com.thinkgem.jeesite.modules.member.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.member.entity.WsMessage;

/**
 * 系统消息DAO接口
 * @author 大胖老师
 * @version 2017-10-09
 */
@MyBatisDao
public interface WsMessageDao extends CrudDao<WsMessage> {
	
}