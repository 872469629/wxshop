package com.thinkgem.jeesite.modules.prod.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prod.dao.WsProdSkuDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;

/**
 * 产品skuService
 * @author water
 * @version 2017-08-10
 */
@Service
@Transactional(readOnly = true)
public class WsProdSkuService extends CrudService<WsProdSkuDao, WsProdSku> {
	
	public WsProdSku get(String id) {
		WsProdSku wsProdSku = super.get(id);
		return wsProdSku;
	}
	
	public List<WsProdSku> findList(WsProdSku wsProdSku) {
		return super.findList(wsProdSku);
	}
	
	public Page<WsProdSku> findPage(Page<WsProdSku> page, WsProdSku wsProdSku) {
		return super.findPage(page, wsProdSku);
	}
	
	@Transactional(readOnly = false)
	public void save(WsProdSku wsProdSku) {
		super.save(wsProdSku);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsProdSku wsProdSku) {
		super.delete(wsProdSku);
	}
	
	@Transactional(readOnly = false)
	public void deleteByProId(WsProdSku wsProdSku) {
		dao.deleteByProId(wsProdSku);
	}
	
	
}