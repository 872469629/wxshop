package com.thinkgem.jeesite.modules.prod.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prod.entity.WsBrand;
import com.thinkgem.jeesite.modules.prod.dao.WsBrandDao;

/**
 * 品牌Service
 * @author water
 * @version 2017-08-08
 */
@Service
@Transactional(readOnly = true)
public class WsBrandService extends CrudService<WsBrandDao, WsBrand> {

	public WsBrand get(String id) {
		return super.get(id);
	}
	
	public List<WsBrand> findList(WsBrand wsBrand) {
		return super.findList(wsBrand);
	}
	
	public Page<WsBrand> findPage(Page<WsBrand> page, WsBrand wsBrand) {
		return super.findPage(page, wsBrand);
	}
	
	@Transactional(readOnly = false)
	public void save(WsBrand wsBrand) {
		super.save(wsBrand);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsBrand wsBrand) {
		super.delete(wsBrand);
	}
	
}