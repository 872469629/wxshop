package com.thinkgem.jeesite.modules.inter.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 分销接口
 * @author 分销开发者
 * @version 2018-10-6
 */
@Controller
@RequestMapping(value = "${wxPath}/commission")
public class CommissionController extends BaseController {
	
	@Autowired
	private WsMemberService wsMemberService;

	/**
	 * 小成为分享者的下限
	 */
	@RequestMapping(value = "toBeFromUser")
	@ResponseBody
	@CrossOrigin
	public Map toBeFromUser(String userId,String fromUserId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WsMember user = wsMemberService.get(userId);
			if (user != null && user.getAgentParent() == null) {
				WsMember fromUser = wsMemberService.get(fromUserId);
				if (fromUser != null && "1".equals(fromUser.getIsAgent())) {//分享者必须是代理商才可以成为他的下限
					user.setAgentParent(fromUser);
					wsMemberService.save(user);
				}
			}
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/toBeFromUser",e);
		}
		return data;
	}
	
	/**
	 * 我的团队
	 */
	@RequestMapping(value = "myGroup")
	@ResponseBody
	@CrossOrigin
	public Map myGroup(String userId, String type, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map data=new HashMap();
		try{
			WsMember user = wsMemberService.get(userId);
			List<WsMember> nextGroup = wsMemberService.getNextGroup(user);
			if ("1".equals(type)) {
				data.put("nextGroup",nextGroup);
			}else if ("2".equals(type)) {
				
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("member",user);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("usercenter/toBeFromUser",e);
		}
		return data;
	}

}
