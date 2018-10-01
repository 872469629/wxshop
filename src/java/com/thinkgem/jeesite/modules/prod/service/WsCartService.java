package com.thinkgem.jeesite.modules.prod.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prod.dao.WsCartDao;
import com.thinkgem.jeesite.modules.prod.entity.WsCart;

/**
 * 购物车管理Service
 * @author 大胖老师
 * @version 2017-11-13
 */
@Service
@Transactional(readOnly = true)
public class WsCartService extends CrudService<WsCartDao, WsCart> {

	public WsCart get(String id) {
		return super.get(id);
	}
	
	public List<WsCart> findList(WsCart wsCart) {
		return super.findList(wsCart);
	}
	
	public Page<WsCart> findPage(Page<WsCart> page, WsCart wsCart) {
		return super.findPage(page, wsCart);
	}
	
	@Transactional(readOnly = false)
	public void save(WsCart wsCart) {
		super.save(wsCart);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsCart wsCart) {
		super.delete(wsCart);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(List<WsCart> wsCarts) {
		for (WsCart wsCart:wsCarts) {
			delete(wsCart);
		}
	}
	
}