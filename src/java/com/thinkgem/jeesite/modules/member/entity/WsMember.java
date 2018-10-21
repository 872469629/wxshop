package com.thinkgem.jeesite.modules.member.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 会员资料Entity
 * @author zengyq
 * @version 2017-10-09
 */
public class WsMember extends DataEntity<WsMember> {
	
	private static final long serialVersionUID = 1L;
	private Integer ids;
	private String username;		// 用户名
	private String password;		// 密码
	private String nickname;		// 昵称
	private String headimgurl;		// 用户头像
	private String openId;		// 微信端唯一标识=个人在微信中的id
	private String token;		// 授权访问token
	private String mobile;		// 手机
	private String memberRankId;		// 会员级别ID
	private String memberRankName;		// 会员级别名称
	private int score;		// 积分
	private BigDecimal balance;		// 余额
	private BigDecimal awardFriend;
	private BigDecimal awardProd;
	private String awardQrCode;
	private WsAddress wsAddress;		// 默认收货地址
	private Date lockedDate;		// 锁定日期
	private String isLocked;		// 是否锁定
	private String lockFlag;		// 锁定标识
	private String email;		// 邮箱
	private String birth;		// 生日
	private String recommendMemberId;		// 推荐人ID
	private List<WsAddress> wsAddressList = Lists.newArrayList();		// 子表列表
	private List<WsMemberAttr> wsMemberAttrList = Lists.newArrayList();		// 子表列表
	private List<WsMemberConsumeLog> wsMemberConsumeLogList = Lists.newArrayList();		// 子表列表
	private List<WsMemberCoupon> wsMemberCouponList = Lists.newArrayList();		// 子表列表
	private List<WsMemberRechargeLog> wsMemberRechargeLogList = Lists.newArrayList();		// 子表列表
	private List<WsMemberRewardLog> wsMemberRewardLogList = Lists.newArrayList();		// 子表列表
	private String isAgent;//是否是代理商
	private WsMember agentParent;//上一级代理商id
	private int toAgentNum;//成为代理商购买商品的件数
	private Date joinAgentTime;		// 成为下限的时间
	private Date becomeAgentTime;		// 成为代理商的时间
	private BigDecimal frozenAmount;		// 冻结金额
	private BigDecimal totalPromotion;//总推广金额
	private BigDecimal totalConsume;//总消费金额
	
	public WsMember() {
		super();
	}

	public WsMember(String id){
		super(id);
	}

	public Integer getIds() {
		return ids;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}

	@Length(min=1, max=64, message="用户名长度必须介于 1 和 64 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=1, max=64, message="密码长度必须介于 1 和 64 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=255, message="昵称长度必须介于 0 和 255 之间")
	public String getNickname() {
		if(StringUtils.isEmpty(nickname)){
			return "";
		}
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=200, message="微信端唯一标识=个人在微信中的id长度必须介于 0 和 200 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=200, message="授权访问token长度必须介于 0 和 200 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Length(min=0, max=64, message="手机长度必须介于 0 和 64 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemberRankId() {
		return memberRankId;
	}

	public void setMemberRankId(String memberRankId) {
		this.memberRankId = memberRankId;
	}

	@Length(min=0, max=50, message="会员级别名称长度必须介于 0 和 50 之间")
	public String getMemberRankName() {
		return memberRankName;
	}

	public void setMemberRankName(String memberRankName) {
		this.memberRankName = memberRankName;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public WsAddress getWsAddress() {
		return wsAddress;
	}

	public void setWsAddress(WsAddress wsAddress) {
		this.wsAddress = wsAddress;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}
	
	@Length(min=0, max=1, message="是否锁定长度必须介于 0 和 1 之间")
	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	
	@Length(min=0, max=1, message="锁定标识长度必须介于 0 和 1 之间")
	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}
	
	@Length(min=0, max=50, message="邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=20, message="生日长度必须介于 0 和 20 之间")
	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	@Length(min=0, max=64, message="推荐人ID长度必须介于 0 和 64 之间")
	public String getRecommendMemberId() {
		return recommendMemberId;
	}

	public void setRecommendMemberId(String recommendMemberId) {
		this.recommendMemberId = recommendMemberId;
	}
	
	public List<WsAddress> getWsAddressList() {
		return wsAddressList;
	}

	public void setWsAddressList(List<WsAddress> wsAddressList) {
		this.wsAddressList = wsAddressList;
	}
	public List<WsMemberAttr> getWsMemberAttrList() {
		return wsMemberAttrList;
	}

	public void setWsMemberAttrList(List<WsMemberAttr> wsMemberAttrList) {
		this.wsMemberAttrList = wsMemberAttrList;
	}
	public List<WsMemberConsumeLog> getWsMemberConsumeLogList() {
		return wsMemberConsumeLogList;
	}

	public void setWsMemberConsumeLogList(List<WsMemberConsumeLog> wsMemberConsumeLogList) {
		this.wsMemberConsumeLogList = wsMemberConsumeLogList;
	}
	public List<WsMemberCoupon> getWsMemberCouponList() {
		return wsMemberCouponList;
	}

	public void setWsMemberCouponList(List<WsMemberCoupon> wsMemberCouponList) {
		this.wsMemberCouponList = wsMemberCouponList;
	}
	public List<WsMemberRechargeLog> getWsMemberRechargeLogList() {
		return wsMemberRechargeLogList;
	}

	public void setWsMemberRechargeLogList(List<WsMemberRechargeLog> wsMemberRechargeLogList) {
		this.wsMemberRechargeLogList = wsMemberRechargeLogList;
	}
	public List<WsMemberRewardLog> getWsMemberRewardLogList() {
		return wsMemberRewardLogList;
	}

	public void setWsMemberRewardLogList(List<WsMemberRewardLog> wsMemberRewardLogList) {
		this.wsMemberRewardLogList = wsMemberRewardLogList;
	}

	public String getHeadimgurl() {
		if(StringUtils.isEmpty(headimgurl)){
			return "";
		}
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public BigDecimal getAwardFriend() {
		return awardFriend;
	}

	public void setAwardFriend(BigDecimal awardFriend) {
		this.awardFriend = awardFriend;
	}

	public BigDecimal getAwardProd() {
		return awardProd;
	}

	public void setAwardProd(BigDecimal awardProd) {
		this.awardProd = awardProd;
	}

	public String getAwardQrCode() {
		return awardQrCode;
	}

	public void setAwardQrCode(String awardQrCode) {
		this.awardQrCode = awardQrCode;
	}

	public String getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	public WsMember getAgentParent() {
		return agentParent;
	}

	public void setAgentParent(WsMember agentParent) {
		this.agentParent = agentParent;
	}

	public int getToAgentNum() {
		return toAgentNum;
	}

	public void setToAgentNum(int toAgentNum) {
		this.toAgentNum = toAgentNum;
	}

	public Date getJoinAgentTime() {
		return joinAgentTime;
	}

	public void setJoinAgentTime(Date joinAgentTime) {
		this.joinAgentTime = joinAgentTime;
	}

	public Date getBecomeAgentTime() {
		return becomeAgentTime;
	}

	public void setBecomeAgentTime(Date becomeAgentTime) {
		this.becomeAgentTime = becomeAgentTime;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public BigDecimal getTotalPromotion() {
		return totalPromotion;
	}

	public void setTotalPromotion(BigDecimal totalPromotion) {
		this.totalPromotion = totalPromotion;
	}

	public BigDecimal getTotalConsume() {
		return totalConsume;
	}

	public void setTotalConsume(BigDecimal totalConsume) {
		this.totalConsume = totalConsume;
	}
	
	
	
}