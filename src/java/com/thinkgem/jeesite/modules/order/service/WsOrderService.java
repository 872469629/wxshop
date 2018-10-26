package com.thinkgem.jeesite.modules.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.commission.service.WsCommissionService;
import com.thinkgem.jeesite.modules.inter.service.PayService;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;
import com.thinkgem.jeesite.modules.member.service.WsMemberCouponService;
import com.thinkgem.jeesite.modules.order.dao.WsOrderDao;
import com.thinkgem.jeesite.modules.order.dao.WsOrderItemDao;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 订单Service
 * @author water
 * @version 2017-10-07
 */
@Service
@Transactional(readOnly = true)
public class WsOrderService extends CrudService<WsOrderDao, WsOrder> {

	@Autowired
	private WsOrderItemDao wsOrderItemDao;
	
	@Autowired
	private WsMemberCouponService wsMemberCouponService;
	
	@Autowired
	private WsOrderDao wsOrderDao;
	
	@Autowired
	private WsCommissionService wsCommissionService;
	
	@Autowired
	private PayService payService;
	
	public WsOrder get(String id) {
		WsOrder wsOrder = super.get(id);
		wsOrder.setWsOrderItemList(wsOrderItemDao.findList(new WsOrderItem(wsOrder)));
		return wsOrder;
	}
	
	public WsOrder getByOrderSn(String id) {
		WsOrder wsOrder = dao.getByOrderSn(id);
		return wsOrder;
	}
	
	public List<WsOrder> findList(WsOrder wsOrder) {
		return super.findList(wsOrder);
	}
	
	public List<WsOrder> findOverTimeList(WsOrder wsOrder) {
		return dao.findOverTimeList(wsOrder);
	}
	
	public int findCount(WsOrder wsOrder) {
		return dao.findCount(wsOrder);
	}
	
	public Page<WsOrder> findPage(Page<WsOrder> page, WsOrder wsOrder) {
		return super.findPage(page, wsOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(WsOrder wsOrder) {
		super.save(wsOrder);
		for (WsOrderItem wsOrderItem : wsOrder.getWsOrderItemList()){
			if (wsOrderItem.getId() == null){
				continue;
			}
			if (WsOrderItem.DEL_FLAG_NORMAL.equals(wsOrderItem.getDelFlag())){
				if (StringUtils.isBlank(wsOrderItem.getId())){
					wsOrderItem.setWsOrder(wsOrder);
					wsOrderItem.preInsert();
					wsOrderItemDao.insert(wsOrderItem);
				}else{
					wsOrderItem.preUpdate();
					wsOrderItemDao.update(wsOrderItem);
				}
			}else{
				wsOrderItemDao.delete(wsOrderItem);
			}
		}
	}
	
	
	@Transactional(readOnly = false)
	public void saveSend(WsOrder wsOrder) {
		super.save(wsOrder);
	}
	
	
	@Transactional(readOnly = false)
	public void saveRemark(WsOrder wsOrder) {
		wsOrderDao.updatSaveRemark(wsOrder);
	}
	
	
	
	
	
	@Transactional(readOnly = false)
	public void closeOrder(WsOrder wsOrder) {
		super.save(wsOrder);
	}
	
	
	
	@Transactional(readOnly = false)
	public void delete(WsOrder wsOrder) {
		super.delete(wsOrder);
		wsOrderItemDao.delete(new WsOrderItem(wsOrder));
	}
	
	@Transactional(readOnly = false)
	public void cancelOrder(String orderId) {
		WsOrder cancelOrder=get(orderId);
		cancelOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_CANCEL);
		save(cancelOrder);
		/**
		 * 如果订单已经使用了优惠券，则返还优惠券
		 */
		if(cancelOrder.getWsMemberCoupon()!=null && StringUtils.isNotEmpty(cancelOrder.getWsMemberCoupon().getId())){
			WsMemberCoupon wsMemberCoupon=wsMemberCouponService.get(cancelOrder.getWsMemberCoupon().getId());
			wsMemberCoupon.setState(WsConstant.COUPON_TO_USE);
			wsMemberCouponService.save(wsMemberCoupon);
		}
	}

	/**
	 * 支付完成订单
	 */
	@Transactional(readOnly = false)
	public void payFinished(WsOrder wsOrder) {
		wsOrder = super.get(wsOrder.getId());
		try {
			payService.payNotify(wsOrder.getOrderSn(),
					wsOrder.getReallyPrice().multiply(new BigDecimal(100)).intValue() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 确认收货
	 * 目前确认收货相当于完成订单
	 */
	@Transactional(readOnly = false)
	public void orderRecevied(WsOrder reciveOrder) {
		//完成时分佣记录结算
		wsCommissionService.updateCommission(reciveOrder);
		super.save(reciveOrder);	
	}
	
}