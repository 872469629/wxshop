package com.thinkgem.jeesite.modules.config.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 快递模版Entity
 * @author water
 * @version 2017-10-03
 */
public class WsExFaretemplate extends DataEntity<WsExFaretemplate> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 模版名称
	private String dispatchTime;		// 发货时间
	private String shopAddr;		// 宝贝地址
	private String valuationModel;		// 计价方式
	private String isInclPostAgeByif;		// 指定条件包邮
	private String carryWay;		// 运送方式
	private List<WsExCarrymode> wsExCarrymodeList = Lists.newArrayList();		// 子表列表
	private List<WsExInclpostageproviso> wsExInclpostageprovisoList = Lists.newArrayList();		// 子表列表
	
	public WsExFaretemplate() {
		super();
	}

	public WsExFaretemplate(String id){
		super(id);
	}

	@Length(min=0, max=100, message="模版名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="发货时间长度必须介于 0 和 50 之间")
	public String getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	
	@Length(min=0, max=2500, message="宝贝地址长度必须介于 0 和 2500 之间")
	public String getShopAddr() {
		return shopAddr;
	}

	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}
	
	@Length(min=0, max=10, message="计价方式长度必须介于 0 和 10 之间")
	public String getValuationModel() {
		return valuationModel;
	}

	public void setValuationModel(String valuationModel) {
		this.valuationModel = valuationModel;
	}
	
	@Length(min=0, max=255, message="指定条件包邮长度必须介于 0 和 255 之间")
	public String getIsInclPostAgeByif() {
		return isInclPostAgeByif;
	}

	public void setIsInclPostAgeByif(String isInclPostAgeByif) {
		this.isInclPostAgeByif = isInclPostAgeByif;
	}
	
	@Length(min=0, max=255, message="运送方式长度必须介于 0 和 255 之间")
	public String getCarryWay() {
		return carryWay;
	}

	public void setCarryWay(String carryWay) {
		this.carryWay = carryWay;
	}
	
	public List<WsExCarrymode> getWsExCarrymodeList() {
		return wsExCarrymodeList;
	}

	public void setWsExCarrymodeList(List<WsExCarrymode> wsExCarrymodeList) {
		this.wsExCarrymodeList = wsExCarrymodeList;
	}
	public List<WsExInclpostageproviso> getWsExInclpostageprovisoList() {
		return wsExInclpostageprovisoList;
	}

	public void setWsExInclpostageprovisoList(List<WsExInclpostageproviso> wsExInclpostageprovisoList) {
		this.wsExInclpostageprovisoList = wsExInclpostageprovisoList;
	}
}