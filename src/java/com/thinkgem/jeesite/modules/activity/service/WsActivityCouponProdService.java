package com.thinkgem.jeesite.modules.activity.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.dao.WsActivityCouponProdDao;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCouponProd;

/**
 * 优惠券活动Service
 * @author 大胖老师
 * @version 2017-11-10
 */
@Service
@Transactional(readOnly = true)
public class WsActivityCouponProdService extends CrudService<WsActivityCouponProdDao, WsActivityCouponProd> {


	
	public WsActivityCouponProd get(String id) {
		WsActivityCouponProd wsActivityCouponProd = super.get(id);
		return wsActivityCouponProd;
	}
	
	public List<WsActivityCouponProd> findList(WsActivityCouponProd wsActivityCouponProd) {
		return super.findList(wsActivityCouponProd);
	}
	
	public Page<WsActivityCouponProd> findPage(Page<WsActivityCouponProd> page, WsActivityCouponProd wsActivityCouponProd) {
		return super.findPage(page, wsActivityCouponProd);
	}
	
	@Transactional(readOnly = false)
	public void save(WsActivityCouponProd wsActivityCouponProd) {
		super.save(wsActivityCouponProd);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsActivityCouponProd wsActivityCouponProd) {
		super.delete(wsActivityCouponProd);
	}
	
}