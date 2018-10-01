package com.thinkgem.jeesite.modules.member.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Area;

/**
 * 会员资料Entity
 * @author zengyq
 * @version 2017-10-09
 */
public class WsAddress extends DataEntity<WsAddress> {
	
	private static final long serialVersionUID = 1L;
	private WsMember wsMember;		// 用户名 父类
	private String consignee;		// 收货人名称
	private String tel;		// 联系电话
	private String zipCode;		// 邮编
	private String city;		// 省市区客户联动选择
	private String address;		// 收货地址
	private String isDefault;		//是否默认收货地址
	private Area area;
	public WsAddress() {
		super();
	}

	public WsAddress(String id){
		super(id);
	}

	public WsAddress(WsMember wsMember){
		this.wsMember = wsMember;
	}

	@Length(min=0, max=64, message="用户名长度必须介于 0 和 64 之间")
	public WsMember getWsMember() {
		return wsMember;
	}

	public void setWsMember(WsMember wsMember) {
		this.wsMember = wsMember;
	}
	
	@Length(min=0, max=50, message="收货人名称长度必须介于 0 和 50 之间")
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	@Length(min=0, max=30, message="联系电话长度必须介于 0 和 30 之间")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Length(min=0, max=20, message="邮编长度必须介于 0 和 20 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Length(min=0, max=128, message="省市区客户联动选择长度必须介于 0 和 128 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=1000, message="收货地址长度必须介于 0 和 1000 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	
	
}