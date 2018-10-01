package com.thinkgem.jeesite.modules.ws.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNewsItem;

/**
 * 图文素材明细DAO接口
 * @author 大胖老师
 * @version 2017-10-29
 */
@MyBatisDao
public interface WxMaterialNewsItemDao extends CrudDao<WxMaterialNewsItem> {
	
}