package com.thinkgem.jeesite.modules.ws.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialPhoto;

/**
 * 图片素材DAO接口
 * @author 大胖老师
 * @version 2017-09-28
 */
@MyBatisDao
public interface WxMaterialPhotoDao extends CrudDao<WxMaterialPhoto> {
	
}