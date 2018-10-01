package com.thinkgem.jeesite.common.utils.excel;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.thinkgem.jeesite.common.web.Servlets;

public class UrlUtils {

	public static String getNetUrl(String localUrl){
		if(StringUtils.isEmpty(localUrl)||localUrl.contains("http://")||localUrl.contains("https://")){
			return localUrl;
		}
		HttpServletRequest request=Servlets.getRequest();
		String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+localUrl;
		return url;
	}
}
