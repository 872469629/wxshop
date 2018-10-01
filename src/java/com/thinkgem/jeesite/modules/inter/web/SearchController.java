package com.thinkgem.jeesite.modules.inter.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberSearchLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberSearchLogService;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 搜索页面接口
 * @author 大胖老师
 * @version 2017-11-15
 */
@Controller
@RequestMapping(value = "${wxPath}/search")
public class SearchController extends BaseController {
	
	@Autowired
	private WsMemberSearchLogService wsMemberSearchLogService;              
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WsMember wsMember=WsUtils.getMember(request, response);
			WsMemberSearchLog wsMemberSearchLog =new WsMemberSearchLog();
			wsMemberSearchLog.setWsMember(wsMember);
			Page<WsMemberSearchLog> page=wsMemberSearchLogService.findPage(new Page<WsMemberSearchLog>(), wsMemberSearchLog);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsMemberSearchLogList",page.getList());
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("search",e);
		}
		return data;
	}

}