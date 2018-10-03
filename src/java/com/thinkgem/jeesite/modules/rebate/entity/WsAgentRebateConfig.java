package com.thinkgem.jeesite.modules.rebate.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 代理商分销配置Entity
 * @author 分销系统开发者
 * @version 2018-10-03
 */
public class WsAgentRebateConfig extends DataEntity<WsAgentRebateConfig> {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal level1proportion;		// level1proportion
	private BigDecimal level2proportion;		// level2proportion
	private BigDecimal level3proportion;		// level3proportion
	private String status;		// status
	private String formulary1;		// formulary1
	private String formulary2;		// formulary2
	private String formulary3;		// formulary3
	private BigDecimal level1promotion;		// 一级推广返利
	private BigDecimal level2promotion;		// 二级推广返利
	private BigDecimal level3promotion;		// 三级推广返利
	
	public WsAgentRebateConfig() {
		super();
	}

	public WsAgentRebateConfig(String id){
		super(id);
	}

	
	@Length(min=0, max=11, message="status长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="formulary1长度必须介于 0 和 255 之间")
	public String getFormulary1() {
		return formulary1;
	}

	public void setFormulary1(String formulary1) {
		this.formulary1 = formulary1;
	}
	
	@Length(min=0, max=255, message="formulary2长度必须介于 0 和 255 之间")
	public String getFormulary2() {
		return formulary2;
	}

	public void setFormulary2(String formulary2) {
		this.formulary2 = formulary2;
	}
	
	@Length(min=0, max=255, message="formulary3长度必须介于 0 和 255 之间")
	public String getFormulary3() {
		return formulary3;
	}

	public void setFormulary3(String formulary3) {
		this.formulary3 = formulary3;
	}

	public BigDecimal getLevel1proportion() {
		return level1proportion;
	}

	public void setLevel1proportion(BigDecimal level1proportion) {
		this.level1proportion = level1proportion;
	}

	public BigDecimal getLevel2proportion() {
		return level2proportion;
	}

	public void setLevel2proportion(BigDecimal level2proportion) {
		this.level2proportion = level2proportion;
	}

	public BigDecimal getLevel3proportion() {
		return level3proportion;
	}

	public void setLevel3proportion(BigDecimal level3proportion) {
		this.level3proportion = level3proportion;
	}

	public BigDecimal getLevel1promotion() {
		return level1promotion;
	}

	public void setLevel1promotion(BigDecimal level1promotion) {
		this.level1promotion = level1promotion;
	}

	public BigDecimal getLevel2promotion() {
		return level2promotion;
	}

	public void setLevel2promotion(BigDecimal level2promotion) {
		this.level2promotion = level2promotion;
	}

	public BigDecimal getLevel3promotion() {
		return level3promotion;
	}

	public void setLevel3promotion(BigDecimal level3promotion) {
		this.level3promotion = level3promotion;
	}
	
}