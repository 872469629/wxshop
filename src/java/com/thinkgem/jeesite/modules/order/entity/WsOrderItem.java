package com.thinkgem.jeesite.modules.order.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;

/**
 * 订单Entity
 * @author water
 * @version 2017-10-07
 */
public class WsOrderItem extends DataEntity<WsOrderItem> {
	
	private static final long serialVersionUID = 1L;
	private String memberId;
	private WsOrder wsOrder;		// 订单号 父类
	private WsProduct wsProduct;		// 产品id
	private String skuId;		// skuid
	private String skuSpec;   //规格
	private String thumb;		// 主图
	private int quantity;		// 数量
	private BigDecimal unitPrice;		// 商品单价
	private BigDecimal reallyUnitPrice;		// 实际商品单价
	private BigDecimal reallyPrice;		// 实际支付总金额
	private BigDecimal rewardMoney;		// 分销金额
	private WsProdSku wsProdSku;		// 产品sku
	
	public WsOrderItem() {
		super();
	}

	public WsOrderItem(String id){
		super(id);
	}

	public WsOrderItem(WsOrder wsOrder){
		this.wsOrder = wsOrder;
	}
	
	public WsProduct getWsProduct() {
		return wsProduct;
	}

	public void setWsProduct(WsProduct wsProduct) {
		this.wsProduct = wsProduct;
	}
	
	@Length(min=0, max=64, message="skuid长度必须介于 0 和 64 之间")
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	@Length(min=0, max=255, message="主图长度必须介于 0 和 255 之间")
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public WsProdSku getWsProdSku() {
		return wsProdSku;
	}

	public void setWsProdSku(WsProdSku wsProdSku) {
		this.wsProdSku = wsProdSku;
	}

	public String getSkuSpec() {
		return skuSpec;
	}

	public void setSkuSpec(String skuSpec) {
		this.skuSpec = skuSpec;
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

	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

	public WsOrder getWsOrder() {
		return wsOrder;
	}

	public void setWsOrder(WsOrder wsOrder) {
		this.wsOrder = wsOrder;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	
	
}