package com.thinkgem.jeesite.modules.wx.wxsendmsg.test;

import com.thinkgem.jeesite.modules.wx.wxsendmsg.JwSendMessageAPI;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		String newAccessToken = "?";
		String url = JwSendMessageAPI.uploadImg(newAccessToken, "C:/Users/zhangdaihao/Desktop/2.png");
		
		long end = System.currentTimeMillis();
		long times = end - start;
		System.out.println("总耗时"+times+"毫秒");
	}
}
