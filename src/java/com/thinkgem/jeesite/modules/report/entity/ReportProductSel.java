package com.thinkgem.jeesite.modules.report.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 热销产品Entity
 * @author water
 * @version 2017-11-07
 */
public class ReportProductSel extends DataEntity<ReportProductSel> {
	
	private static final long serialVersionUID = 1L;
	private String countDate;		// 统计日期
	private String productId;		// 产品id
	private String productName;		// 产品名称
	private String selNum;		// 销售数量
	private String selMoney;		// 销售金额
	private String percent;		// 当日销量百分比
	
	public ReportProductSel() {
		super();
	}

	public ReportProductSel(String id){
		super(id);
	}

	@Length(min=0, max=20, message="统计日期长度必须介于 0 和 20 之间")
	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}
	
	@Length(min=0, max=64, message="产品id长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=64, message="产品名称长度必须介于 0 和 64 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=11, message="销售数量长度必须介于 0 和 11 之间")
	public String getSelNum() {
		return selNum;
	}

	public void setSelNum(String selNum) {
		this.selNum = selNum;
	}
	
	@Length(min=0, max=11, message="销售金额长度必须介于 0 和 11 之间")
	public String getSelMoney() {
		return selMoney;
	}

	public void setSelMoney(String selMoney) {
		this.selMoney = selMoney;
	}
	
	@Length(min=0, max=11, message="当日销量百分比长度必须介于 0 和 11 之间")
	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
	
}