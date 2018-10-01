package com.thinkgem.jeesite.modules.config.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 快递模版Entity
 * @author water
 * @version 2017-10-03
 */
public class WsExInclpostageproviso extends DataEntity<WsExInclpostageproviso> {
	
	private static final long serialVersionUID = 1L;
	private WsExFaretemplate fareId;		// 模版表外键 父类
	private String regionId;		// 包邮地区
	private String regionName;		// 包邮地区名称
	private BigDecimal amount;		// 包邮金额
	
	public WsExInclpostageproviso() {
		super();
	}

	public WsExInclpostageproviso(String id){
		super(id);
	}

	public WsExInclpostageproviso(WsExFaretemplate fareId){
		this.fareId = fareId;
	}

	@Length(min=0, max=64, message="模版表外键长度必须介于 0 和 64 之间")
	public WsExFaretemplate getFareId() {
		return fareId;
	}

	public void setFareId(WsExFaretemplate fareId) {
		this.fareId = fareId;
	}
	
	@Length(min=0, max=64, message="包邮地区长度必须介于 0 和 64 之间")
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}