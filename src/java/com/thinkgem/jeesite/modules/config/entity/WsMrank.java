package com.thinkgem.jeesite.modules.config.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员等级Entity
 * @author water
 * @version 2017-09-30
 */
public class WsMrank extends DataEntity<WsMrank> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 级别名称
	private BigDecimal scale;		// 折扣
	private String isDefault;		// 是否默认级别
	private int amount;		// 需要积分
	
	public WsMrank() {
		super();
	}

	public WsMrank(String id){
		super(id);
	}

	@Length(min=0, max=50, message="级别名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	@Length(min=0, max=1, message="是否默认级别长度必须介于 0 和 1 之间")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	
}