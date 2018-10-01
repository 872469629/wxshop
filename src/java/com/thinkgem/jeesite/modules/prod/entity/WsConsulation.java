package com.thinkgem.jeesite.modules.prod.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 评论回复管理Entity
 * @author 大胖老师
 * @version 2017-11-14
 */
public class WsConsulation extends DataEntity<WsConsulation> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 评论产品ID
	private String productTitle;		// 评论产品标题
	private String thumb;		// 评论产品图片
	private BigDecimal reallyUnitPrice;		// 评论产品价格
	private String orderId;		// 定单ID
	private String memberId;		// 评论会员
	private String nickname;		// 评论昵称
	private String headimgurl;		// 评论头像
	private String consulationContent;		// 内容
	private int prodConsulationLevel;		// 商品评分
	private int logisticsConsulationLevel;		// 物流评分
	private String forconsulation;		// 评论回复
	private String consulationImages;		// 评论图片
	
	public WsConsulation() {
		super();
	}

	public WsConsulation(String id){
		super(id);
	}

	@Length(min=0, max=64, message="评论产品长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=64, message="定单ID长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="评论会员长度必须介于 0 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=1500, message="评论回复长度必须介于 0 和 1500 之间")
	public String getForconsulation() {
		return forconsulation;
	}

	public void setForconsulation(String forconsulation) {
		this.forconsulation = forconsulation;
	}
	
	public String getConsulationImages() {
		return consulationImages;
	}

	public void setConsulationImages(String consulationImages) {
		this.consulationImages = consulationImages;
	}

	public String getConsulationContent() {
		return consulationContent;
	}

	public void setConsulationContent(String consulationContent) {
		this.consulationContent = consulationContent;
	}

	public int getProdConsulationLevel() {
		return prodConsulationLevel;
	}

	public void setProdConsulationLevel(int prodConsulationLevel) {
		this.prodConsulationLevel = prodConsulationLevel;
	}

	public int getLogisticsConsulationLevel() {
		return logisticsConsulationLevel;
	}

	public void setLogisticsConsulationLevel(int logisticsConsulationLevel) {
		this.logisticsConsulationLevel = logisticsConsulationLevel;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public BigDecimal getReallyUnitPrice() {
		return reallyUnitPrice;
	}

	public void setReallyUnitPrice(BigDecimal reallyUnitPrice) {
		this.reallyUnitPrice = reallyUnitPrice;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
}