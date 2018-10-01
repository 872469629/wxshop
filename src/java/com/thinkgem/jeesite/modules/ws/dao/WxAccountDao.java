package com.thinkgem.jeesite.modules.ws.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ws.entity.WxAccount;

/**
 * 微信账号配置DAO接口
 * @author 大胖老师
 * @version 2017-09-26
 */
@MyBatisDao
public interface WxAccountDao extends CrudDao<WxAccount> {
	
}