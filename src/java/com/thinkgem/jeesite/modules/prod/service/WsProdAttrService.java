package com.thinkgem.jeesite.modules.prod.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttr;
import com.thinkgem.jeesite.modules.prod.dao.WsProdAttrDao;

/**
 * 产品属性Service
 * @author 大胖老师
 * @version 2017-10-02
 */
@Service
@Transactional(readOnly = true)
public class WsProdAttrService extends CrudService<WsProdAttrDao, WsProdAttr> {

	public WsProdAttr get(String id) {
		return super.get(id);
	}
	
	public List<WsProdAttr> findList(WsProdAttr wsProdAttr) {
		return super.findList(wsProdAttr);
	}
	
	public Page<WsProdAttr> findPage(Page<WsProdAttr> page, WsProdAttr wsProdAttr) {
		return super.findPage(page, wsProdAttr);
	}
	
	@Transactional(readOnly = false)
	public void save(WsProdAttr wsProdAttr) {
		super.save(wsProdAttr);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsProdAttr wsProdAttr) {
		super.delete(wsProdAttr);
	}
	
}