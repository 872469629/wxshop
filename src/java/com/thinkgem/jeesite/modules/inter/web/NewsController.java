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

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialNewsItem;
import com.thinkgem.jeesite.modules.ws.service.WxMaterialNewsItemService;

/**
 * 图文页面接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/news")
public class NewsController extends BaseController {
	
	@Autowired
	private WxMaterialNewsItemService wxMaterialNewsItemService;
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WxMaterialNewsItem wxMaterialNewsItem=wxMaterialNewsItemService.get(id);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wxMaterialNewsItem",wxMaterialNewsItem);
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
		}
		return data;
	}


}