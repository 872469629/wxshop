package com.thinkgem.jeesite.modules.ws.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMenu;

/**
 * 微信菜单DAO接口
 * @author 大胖老师
 * @version 2017-09-29
 */
@MyBatisDao
public interface WxMenuDao extends TreeDao<WxMenu> {
	
}