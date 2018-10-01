package com.thinkgem.jeesite.modules.config.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 快递模版Entity
 * @author water
 * @version 2017-10-03
 */
public class WsExCarrymode extends DataEntity<WsExCarrymode> {
	
	private static final long serialVersionUID = 1L;
	private WsExFaretemplate wsExFaretemplate;		// 模版外键 父类
	private String region;		// 地区
	private String regionName;		// 地区名称
	private BigDecimal firstPiece;		// 首件数量
	private BigDecimal firstWeight;		// 首件重量
	private BigDecimal firstBulk;		// 首体积
	private BigDecimal firstAmount;		// 首费用
	private BigDecimal secondPiece;		// 续件
	private BigDecimal secondWeight;		// 续重量
	private BigDecimal secondBulk;		// 续体积
	private BigDecimal secondAmount;		// 续费
	private String isDefault;		// 是否默认=如果是默认的话，对未指明运费的地区按照默认的运费方式计算
	
	public WsExCarrymode() {
		super();
	}

	public WsExCarrymode(String id){
		super(id);
	}

	public WsExCarrymode(WsExFaretemplate wsExFaretemplate){
		this.wsExFaretemplate = wsExFaretemplate;
	}
	
	public WsExFaretemplate getWsExFaretemplate() {
		return wsExFaretemplate;
	}

	public void setWsExFaretemplate(WsExFaretemplate wsExFaretemplate) {
		this.wsExFaretemplate = wsExFaretemplate;
	}

	@Length(min=0, max=64, message="地区长度必须介于 0 和 64 之间")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	@Length(min=0, max=255, message="是否默认=如果是默认的话，对未指明运费的地区按照默认的运费方式计算长度必须介于 0 和 255 之间")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public BigDecimal getFirstPiece() {
		return firstPiece;
	}

	public void setFirstPiece(BigDecimal firstPiece) {
		this.firstPiece = firstPiece;
	}

	public BigDecimal getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}

	public BigDecimal getFirstBulk() {
		return firstBulk;
	}

	public void setFirstBulk(BigDecimal firstBulk) {
		this.firstBulk = firstBulk;
	}

	public BigDecimal getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(BigDecimal firstAmount) {
		this.firstAmount = firstAmount;
	}

	public BigDecimal getSecondPiece() {
		return secondPiece;
	}

	public void setSecondPiece(BigDecimal secondPiece) {
		this.secondPiece = secondPiece;
	}

	public BigDecimal getSecondWeight() {
		return secondWeight;
	}

	public void setSecondWeight(BigDecimal secondWeight) {
		this.secondWeight = secondWeight;
	}

	public BigDecimal getSecondBulk() {
		return secondBulk;
	}

	public void setSecondBulk(BigDecimal secondBulk) {
		this.secondBulk = secondBulk;
	}

	public BigDecimal getSecondAmount() {
		return secondAmount;
	}

	public void setSecondAmount(BigDecimal secondAmount) {
		this.secondAmount = secondAmount;
	}
	
}