package com.thinkgem.jeesite.modules.prod.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 购物车管理Entity
 * @author 大胖老师
 * @version 2017-11-13
 */
public class WsCart extends DataEntity<WsCart> {
	
	private static final long serialVersionUID = 1L;
	private String memberId;		// 用户
	private String productId;		// 购物车产品
	private String skuId;		// 产品sku
	private String thumb;		// 主图
	private int quantity;		// 数量
	private String activityId;		// 活动id
	private BigDecimal discountAmount;		// 优惠金额=通过活动后优惠的金额
	private BigDecimal unitPrice;		// 单价
	private BigDecimal price;		// 总价格=数量和单价的乘积
	private String state;		// 购物车商品状态0失效1生效
	private String skuSpec; //sku规格
	private String title;
	private BigDecimal unitDefaultPrice;
	public WsCart() {
		super();
	}

	public WsCart(String id){
		super(id);
	}

	@Length(min=0, max=255, message="用户长度必须介于 0 和 255 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=11, message="购物车产品长度必须介于 0 和 11 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=255, message="产品sku长度必须介于 0 和 255 之间")
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

	@Length(min=0, max=64, message="活动id长度必须介于 0 和 64 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	
	@Length(min=0, max=10, message="购物车商品状态0失效1生效长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSkuSpec() {
		return skuSpec;
	}

	public void setSkuSpec(String skuSpec) {
		this.skuSpec = skuSpec;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getUnitDefaultPrice() {
		return unitDefaultPrice;
	}

	public void setUnitDefaultPrice(BigDecimal unitDefaultPrice) {
		this.unitDefaultPrice = unitDefaultPrice;
	}
	
	
	
}