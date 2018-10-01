/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web.front;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.utils.WiexinSignUtil;
import com.thinkgem.jeesite.modules.ws.service.WeiXinService;

/**
 * 测试Controller
 * @author 大胖老师
 * @version 2017-10-28
 */
@Controller
@RequestMapping(value = "${frontPath}/weixin")
public class WeixinController extends BaseController {
	
	@Autowired
	private WeiXinService weiXinService;

	/**
	 * 
	 * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。 
	 * @param timestamp 时间戳
	 * @param nonce 随机数 
	 * @param echostr 随机数 
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String get(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request) {
		
		System.out.println("=============================================== get start");
		for (Object o : request.getParameterMap().keySet()){
			System.out.println(o + " = " + request.getParameter((String)o));
		}
		System.out.println("=============================================== get end");
		
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (WiexinSignUtil.checkSignature(signature, timestamp, nonce)) {  
        	return echostr;
        }

		return "";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void post(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String respMessage = weiXinService.autoResponse(request);
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}
	
	
}
