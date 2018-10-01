package com.thinkgem.jeesite.modules.prod.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品信息Entity
 * @author 大胖老师
 * @version 2017-10-01
 */
public class WsProdSku extends DataEntity<WsProdSku> {
	
	private static final long serialVersionUID = 1L;
	private WsProduct wsProduct;		// 产品ID 父类
	private String skuName;		// sku名称
	private String attributeValues;		// 销售属性id
	private String attrivalueValues;		// 销售属性值id
	private long surplusQuantity;		// 剩余数量
	private BigDecimal price;		// 价格
	private BigDecimal reallyPrice;		// 实际价格
	private BigDecimal rewardMoney;		// 分销金额
	private String state;		// 状态0无效1有效
	
	public WsProdSku() {
		super();
	}

	public WsProdSku(String id){
		super(id);
	}

	public WsProdSku(WsProduct wsProduct){
		this.wsProduct = wsProduct;
	}
	
	public WsProduct getWsProduct() {
		return wsProduct;
	}

	public void setWsProduct(WsProduct wsProduct) {
		this.wsProduct = wsProduct;
	}

	@Length(min=0, max=512, message="sku名称长度必须介于 0 和 512 之间")
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	
	@Length(min=0, max=512, message="销售属性id长度必须介于 0 和 512 之间")
	public String getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(String attributeValues) {
		this.attributeValues = attributeValues;
	}
	
	public long getSurplusQuantity() {
		return surplusQuantity;
	}

	public void setSurplusQuantity(long surplusQuantity) {
		this.surplusQuantity = surplusQuantity;
	}

	@Length(min=0, max=10, message="状态0无效1有效长度必须介于 0 和 10 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAttrivalueValues() {
		return attrivalueValues;
	}

	public void setAttrivalueValues(String attrivalueValues) {
		this.attrivalueValues = attrivalueValues;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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
	
	
}