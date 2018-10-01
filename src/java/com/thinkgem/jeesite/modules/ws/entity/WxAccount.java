package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信账号配置Entity
 * @author 大胖老师
 * @version 2017-09-26
 */
public class WxAccount extends DataEntity<WxAccount> {
	
	private static final long serialVersionUID = 1L;
	private String accountName;		// 公众帐号名称
	private String accountNumber;		// 公众微信号
	private String accountToken;		// 公众帐号TOKEN
	private String accountType;		// 公众号类型
	private String accountAppid;		// 公众帐号APPID
	private String accountAppsecret;		// 公众帐号APPSECRET
	private String wcxAppid;		// 小程序APPID
	private String wcxAppsecret;		// 小程序APPSECRET
	private String accountAccesstoken;		// ACCESS_TOKEN
	private String encodingAesKey;		// 加密字符串
	private String mchId;		// 商户号
	private String certPath;		//证书存放地址
	private String payKey;		// 支付密钥
	private Date accountAccesstokenTime;		// ACCESS_TOKEN_Time
	private String apiTicket;		// api_ticket
	private Date apiTickettTime;		// api_tickett_time
	private String jsapiTicket;		// jsapi_ticket
	private Date jsapiTicketTime;		// jsapi_ticket_time
	
	public WxAccount() {
		super();
	}

	public WxAccount(String id){
		super(id);
	}

	@Length(min=0, max=200, message="公众帐号名称长度必须介于 0 和 200 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Length(min=0, max=200, message="公众微信号长度必须介于 0 和 200 之间")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@Length(min=0, max=200, message="公众帐号TOKEN长度必须介于 0 和 200 之间")
	public String getAccountToken() {
		return accountToken;
	}

	public void setAccountToken(String accountToken) {
		this.accountToken = accountToken;
	}
	
	@Length(min=0, max=50, message="公众号类型长度必须介于 0 和 50 之间")
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@Length(min=0, max=200, message="公众帐号APPID长度必须介于 0 和 200 之间")
	public String getAccountAppid() {
		return accountAppid;
	}

	public void setAccountAppid(String accountAppid) {
		this.accountAppid = accountAppid;
	}
	
	@Length(min=0, max=500, message="公众帐号APPSECRET长度必须介于 0 和 500 之间")
	public String getAccountAppsecret() {
		return accountAppsecret;
	}

	public void setAccountAppsecret(String accountAppsecret) {
		this.accountAppsecret = accountAppsecret;
	}
	
	@Length(min=0, max=1000, message="ACCESS_TOKEN长度必须介于 0 和 1000 之间")
	public String getAccountAccesstoken() {
		return accountAccesstoken;
	}

	public void setAccountAccesstoken(String accountAccesstoken) {
		this.accountAccesstoken = accountAccesstoken;
	}
	
	@Length(min=0, max=100, message="加密字符串长度必须介于 0 和 100 之间")
	public String getEncodingAesKey() {
		return encodingAesKey;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}
	
	@Length(min=0, max=200, message="api_ticket长度必须介于 0 和 200 之间")
	public String getApiTicket() {
		return apiTicket;
	}

	public void setApiTicket(String apiTicket) {
		this.apiTicket = apiTicket;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApiTickettTime() {
		return apiTickettTime;
	}

	public void setApiTickettTime(Date apiTickettTime) {
		this.apiTickettTime = apiTickettTime;
	}
	
	@Length(min=0, max=200, message="jsapi_ticket长度必须介于 0 和 200 之间")
	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJsapiTicketTime() {
		return jsapiTicketTime;
	}

	public void setJsapiTicketTime(Date jsapiTicketTime) {
		this.jsapiTicketTime = jsapiTicketTime;
	}

	public Date getAccountAccesstokenTime() {
		return accountAccesstokenTime;
	}

	public void setAccountAccesstokenTime(Date accountAccesstokenTime) {
		this.accountAccesstokenTime = accountAccesstokenTime;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getWcxAppid() {
		return wcxAppid;
	}

	public void setWcxAppid(String wcxAppid) {
		this.wcxAppid = wcxAppid;
	}

	public String getWcxAppsecret() {
		return wcxAppsecret;
	}

	public void setWcxAppsecret(String wcxAppsecret) {
		this.wcxAppsecret = wcxAppsecret;
	}
	
	
}