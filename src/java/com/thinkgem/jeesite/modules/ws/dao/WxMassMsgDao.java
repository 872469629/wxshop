package com.thinkgem.jeesite.modules.ws.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMassMsg;

/**
 * 群发微信DAO接口
 * @author 大胖老师
 * @version 2017-12-31
 */
@MyBatisDao
public interface WxMassMsgDao extends CrudDao<WxMassMsg> {
	
}