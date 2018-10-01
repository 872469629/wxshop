package com.thinkgem.jeesite.modules.ws.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialVideo;

/**
 * 视频素材DAO接口
 * @author 大胖老师
 * @version 2017-09-29
 */
@MyBatisDao
public interface WxMaterialVideoDao extends CrudDao<WxMaterialVideo> {
	
}