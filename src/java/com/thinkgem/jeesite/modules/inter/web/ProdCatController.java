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
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.member.service.WsMessageRecordService;
import com.thinkgem.jeesite.modules.prod.entity.WsProdCategory;
import com.thinkgem.jeesite.modules.prod.service.WsProdCategoryService;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 产品分类接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/prodCat")
public class ProdCatController extends BaseController {
	
	@Autowired
	private WsProdCategoryService wsProdCategoryService;
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 查询分类
			 */
			WsProdCategory wsProdCategory=new WsProdCategory();
			List<WsProdCategory> wsProdCategoryList=wsProdCategoryService.findList(wsProdCategory);
			for (WsProdCategory prodCat:wsProdCategoryList) {
				prodCat.setImageUrl(UrlUtils.getNetUrl(prodCat.getImageUrl()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsProdCategoryList",wsProdCategoryList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("prodCat",e);
		}
		return data;
	}



}