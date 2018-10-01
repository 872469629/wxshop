package com.thinkgem.jeesite.modules.config.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.config.dao.WsActivityDao;
import com.thinkgem.jeesite.modules.config.entity.WsActivity;

/**
 * 活动配置Service
 * @author 大胖老师
 * @version 2017-10-05
 */
@Service
@Transactional(readOnly = true)
public class WsActivityService extends CrudService<WsActivityDao, WsActivity> {
	
	public WsActivity get(String id) {
		WsActivity wsActivity = super.get(id);
		return wsActivity;
	}
	
	public List<WsActivity> findList(WsActivity wsActivity) {
		return super.findList(wsActivity);
	}
	
	public Page<WsActivity> findPage(Page<WsActivity> page, WsActivity wsActivity) {
		return super.findPage(page, wsActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(WsActivity wsActivity) {
		super.save(wsActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsActivity wsActivity) {
		super.delete(wsActivity);
	}
	
}