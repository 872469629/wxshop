package com.thinkgem.jeesite.modules.wx.wxsendmsg.model.auto;

import java.util.List;

import com.thinkgem.jeesite.modules.wx.wxsendmsg.model.WxArticleConfig;

/**
 * 关键字回复内容
 * @author luobaoli
 *
 */
public class ReplyListInfo extends AutoReplyInfo {
	/** 回复信息 */
	private List<WxArticleConfig> news_info;

	public List<WxArticleConfig> getNews_info() {
		return news_info;
	}

	public void setNews_info(List<WxArticleConfig> news_info) {
		this.news_info = news_info;
	}
}
