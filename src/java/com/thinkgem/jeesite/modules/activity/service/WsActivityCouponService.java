package com.thinkgem.jeesite.modules.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.dao.WsActivityCouponDao;
import com.thinkgem.jeesite.modules.activity.dao.WsActivityCouponProdDao;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCoupon;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCouponProd;

/**
 * 优惠券活动Service
 * @author 大胖老师
 * @version 2017-11-10
 */
@Service
@Transactional(readOnly = true)
public class WsActivityCouponService extends CrudService<WsActivityCouponDao, WsActivityCoupon> {

	@Autowired
	private WsActivityCouponProdDao wsActivityCouponProdDao;
	
	public WsActivityCoupon get(String id) {
		WsActivityCoupon wsActivityCoupon = super.get(id);
		wsActivityCoupon.setWsActivityCouponProdList(wsActivityCouponProdDao.findList(new WsActivityCouponProd(wsActivityCoupon)));
		return wsActivityCoupon;
	}
	
	public List<WsActivityCoupon> findList(WsActivityCoupon wsActivityCoupon) {
		return super.findList(wsActivityCoupon);
	}
	
	public Page<WsActivityCoupon> findPage(Page<WsActivityCoupon> page, WsActivityCoupon wsActivityCoupon) {
		return super.findPage(page, wsActivityCoupon);
	}
	
	@Transactional(readOnly = false)
	public void save(WsActivityCoupon wsActivityCoupon) {
		super.save(wsActivityCoupon);
		String [] prodRelations=wsActivityCoupon.getProdRelation().split(",");
		wsActivityCouponProdDao.deleteByCouponId(new WsActivityCouponProd(wsActivityCoupon));
		for (int i = 0; i < prodRelations.length; i++) {
			WsActivityCouponProd wsActivityCouponProd=new WsActivityCouponProd();
			wsActivityCouponProd.setProdId(prodRelations[i]);
			wsActivityCouponProd.setWsActivityCoupon(wsActivityCoupon);
			wsActivityCouponProd.preInsert();
			wsActivityCouponProdDao.insert(wsActivityCouponProd);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WsActivityCoupon wsActivityCoupon) {
		super.delete(wsActivityCoupon);
		wsActivityCouponProdDao.delete(new WsActivityCouponProd(wsActivityCoupon));
	}
	
}