package com.thinkgem.jeesite.modules.order.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCoupon;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturn;

/**
 * 订单Entity
 * @author water
 * @version 2017-10-07
 */
public class WsOrder extends DataEntity<WsOrder> {
	
	private static final long serialVersionUID = 1L;
	private String orderSn;		// 订单号
	private String orderState;		// 订单状态
	private String jsPayState;		//前端支付标识，非最终结算依据
	private String buysWords;		// 买家留言
	private WsMember memberId;		// 用户
	private WsMember ruid;
	private WsMemberCoupon wsMemberCoupon;
	private String logisticsMethod;		// 物流方式
	private Date paytime;		// 付款时间
	private String payment;		// 付款方式
	private String trackingno;		// 快递单号
	private String express;		// 发货快递公司
	private BigDecimal postage;		// 邮费
	private String mrankId;
	private BigDecimal mrankScale;
	private BigDecimal mrankMoney;
	private BigDecimal price;		// 商品总价格
	private BigDecimal reallyPrice;		// 实际支付价格
	private int score;		// 赠送积分
	private WsAddress address;		// 收货地址
	private String prepayId;
	private Date prepayDate;
	private WsReturn wsReturn;
	private Date sendTime;
	private Date receviceTime;
	private String returnState;
	private List<WsOrderItem> wsOrderItemList = Lists.newArrayList();		// 子表列表
	
	public WsOrder() {
		super();
	}

	public WsOrder(String id){
		super(id);
	}

	@Length(min=0, max=255, message="订单号长度必须介于 0 和 255 之间")
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@Length(min=0, max=255, message="订单状态长度必须介于 0 和 255 之间")
	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	
	
	
	public WsMember getMemberId() {
		return memberId;
	}

	public void setMemberId(WsMember memberId) {
		this.memberId = memberId;
	}

	@Length(min=0, max=255, message="物流方式长度必须介于 0 和 255 之间")
	public String getLogisticsMethod() {
		return logisticsMethod;
	}

	public void setLogisticsMethod(String logisticsMethod) {
		this.logisticsMethod = logisticsMethod;
	}
	
	
	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	@Length(min=0, max=255, message="付款方式长度必须介于 0 和 255 之间")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	@Length(min=0, max=255, message="快递单号长度必须介于 0 和 255 之间")
	public String getTrackingno() {
		return trackingno;
	}

	public void setTrackingno(String trackingno) {
		this.trackingno = trackingno;
	}
	
	@Length(min=0, max=255, message="发货快递公司长度必须介于 0 和 255 之间")
	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public WsAddress getAddress() {
		return address;
	}

	public void setAddress(WsAddress address) {
		this.address = address;
	}
	
	public List<WsOrderItem> getWsOrderItemList() {
		return wsOrderItemList;
	}

	public void setWsOrderItemList(List<WsOrderItem> wsOrderItemList) {
		this.wsOrderItemList = wsOrderItemList;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public Date getPrepayDate() {
		return prepayDate;
	}

	public void setPrepayDate(Date prepayDate) {
		this.prepayDate = prepayDate;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
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

	public WsMemberCoupon getWsMemberCoupon() {
		return wsMemberCoupon;
	}

	public void setWsMemberCoupon(WsMemberCoupon wsMemberCoupon) {
		this.wsMemberCoupon = wsMemberCoupon;
	}

	public String getJsPayState() {
		return jsPayState;
	}

	public void setJsPayState(String jsPayState) {
		this.jsPayState = jsPayState;
	}

	public String getBuysWords() {
		return buysWords;
	}

	public void setBuysWords(String buysWords) {
		this.buysWords = buysWords;
	}

	public WsReturn getWsReturn() {
		return wsReturn;
	}

	public void setWsReturn(WsReturn wsReturn) {
		this.wsReturn = wsReturn;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReceviceTime() {
		return receviceTime;
	}

	public void setReceviceTime(Date receviceTime) {
		this.receviceTime = receviceTime;
	}

	public String getReturnState() {
		return returnState;
	}

	public void setReturnState(String returnState) {
		this.returnState = returnState;
	}

	public String getMrankId() {
		return mrankId;
	}

	public void setMrankId(String mrankId) {
		this.mrankId = mrankId;
	}

	public BigDecimal getMrankScale() {
		return mrankScale;
	}

	public void setMrankScale(BigDecimal mrankScale) {
		this.mrankScale = mrankScale;
	}

	public BigDecimal getMrankMoney() {
		return mrankMoney;
	}

	public void setMrankMoney(BigDecimal mrankMoney) {
		this.mrankMoney = mrankMoney;
	}

	public WsMember getRuid() {
		return ruid;
	}

	public void setRuid(WsMember ruid) {
		this.ruid = ruid;
	}
	
	
}