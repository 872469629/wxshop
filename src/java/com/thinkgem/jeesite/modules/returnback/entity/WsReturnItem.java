package com.thinkgem.jeesite.modules.returnback.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;

/**
 * 退货管理Entity
 * @author 大胖老师
 * @version 2017-11-26
 */
public class WsReturnItem extends DataEntity<WsReturnItem> {
	
	private static final long serialVersionUID = 1L;
	private WsReturn wsReturn;		// 退货id 父类
	private WsOrderItem wsOrderItem;		
	private String orderId;
	private String reason;
	private WsProduct wsProduct;		// 产品
	private String skuId;		// sku_id
	private String skuName;		// sku规格名称
	private String thumb;		// 主图
	private int quantity;		// 退货数量
	private String activityId;		// 退货参与活动id
	private String favorableDesc;		// 退货扣除优惠说明
	private BigDecimal unitPrice;		// 购买单价
	private BigDecimal reallyUnitPrice;		// 实际退货单价
	private BigDecimal reallyPrice;		// 退货总价
	
	public WsReturnItem() {
		super();
	}

	public WsReturnItem(String id){
		super(id);
	}

	public WsReturnItem(WsReturn wsReturn){
		this.wsReturn = wsReturn;
	}
	
	public WsReturn getWsReturn() {
		return wsReturn;
	}

	public void setWsReturn(WsReturn wsReturn) {
		this.wsReturn = wsReturn;
	}
	
	public WsProduct getWsProduct() {
		return wsProduct;
	}

	public void setWsProduct(WsProduct wsProduct) {
		this.wsProduct = wsProduct;
	}

	@Length(min=0, max=64, message="sku_id长度必须介于 0 和 64 之间")
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	@Length(min=0, max=500, message="sku规格名称长度必须介于 0 和 500 之间")
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	
	@Length(min=0, max=255, message="主图长度必须介于 0 和 255 之间")
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	@Length(min=0, max=64, message="退货参与活动id长度必须介于 0 和 64 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=100, message="退货扣除优惠说明长度必须介于 0 和 100 之间")
	public String getFavorableDesc() {
		return favorableDesc;
	}

	public void setFavorableDesc(String favorableDesc) {
		this.favorableDesc = favorableDesc;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getReallyUnitPrice() {
		return reallyUnitPrice;
	}

	public void setReallyUnitPrice(BigDecimal reallyUnitPrice) {
		this.reallyUnitPrice = reallyUnitPrice;
	}

	public BigDecimal getReallyPrice() {
		return reallyPrice;
	}

	public void setReallyPrice(BigDecimal reallyPrice) {
		this.reallyPrice = reallyPrice;
	}

	public WsOrderItem getWsOrderItem() {
		return wsOrderItem;
	}

	public void setWsOrderItem(WsOrderItem wsOrderItem) {
		this.wsOrderItem = wsOrderItem;
	}
	
}