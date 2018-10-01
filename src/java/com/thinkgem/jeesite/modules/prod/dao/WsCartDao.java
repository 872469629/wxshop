package com.thinkgem.jeesite.modules.prod.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prod.entity.WsCart;

/**
 * 购物车管理DAO接口
 * @author 大胖老师
 * @version 2017-11-13
 */
@MyBatisDao
public interface WsCartDao extends CrudDao<WsCart> {
	
}