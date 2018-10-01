package com.thinkgem.jeesite.modules.inter.web;

import java.util.Date;
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
import com.thinkgem.jeesite.modules.member.entity.WsMemberCollectLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberCollectLogService;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 收藏接口
 * @author 大胖老师
 * @version 2017-11-15
 */
@Controller
@RequestMapping(value = "${wxPath}/collect")
public class CollectController extends BaseController {            
	
	@Autowired
	private WsMemberCollectLogService wsMemberCollectLogService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@RequestMapping(value = "addCollect")
	@ResponseBody
	@CrossOrigin
	public Map addCollect(String prodId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 新增用户收藏记录
			 */
			WsProduct wsProduct=wsProductService.get(prodId);
			WsMemberCollectLog wsMemberCollectLog=new WsMemberCollectLog();
			wsMemberCollectLog.setWsProduct(wsProduct);
			wsMemberCollectLog.setWsMember(member);
			List<WsMemberCollectLog> wsMemberCollectLogList=wsMemberCollectLogService.findList(wsMemberCollectLog);
			int wsMemberCollectLogNum=wsMemberCollectLogList.size();
			if(wsMemberCollectLogList!=null && wsMemberCollectLogList.size()>0){
				wsMemberCollectLog=wsMemberCollectLogList.get(0);
			}
			wsMemberCollectLog.setCollectDate(new Date());
			wsMemberCollectLogService.save(wsMemberCollectLog);
			/**
			 * 重新计算收藏数量
			 */
			wsMemberCollectLog=new WsMemberCollectLog();
			wsMemberCollectLog.setWsProduct(wsProduct);
			wsMemberCollectLogList=wsMemberCollectLogService.findList(wsMemberCollectLog);
			wsMemberCollectLogNum=wsMemberCollectLogList.size();
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsMemberCollectLogNum",wsMemberCollectLogNum);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("collect/addCollect",e);
		}
		return data;
	}
	
	/**
	 * 取消用户收藏
	 */
	@RequestMapping(value = "cancelCollect")
	@ResponseBody
	@CrossOrigin
	public Map cancelCollect(String collectId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			WsMemberCollectLog wsMemberCollectLog=wsMemberCollectLogService.get(collectId);
			/**
			 * 判断当前操作人和收藏人是否一致
			 */
			if(!wsMemberCollectLog.getWsMember().getId().equals(member.getId())){
				throw new Exception("禁止非法删除!");
			}
			wsMemberCollectLogService.delete(wsMemberCollectLog);
			/**
			 * 查询用户所有评论
			 */
			wsMemberCollectLog =new WsMemberCollectLog();
			wsMemberCollectLog.setWsMember(member);
			List<WsMemberCollectLog> wsMemberCollectLogList=wsMemberCollectLogService.findList(wsMemberCollectLog);
			for (WsMemberCollectLog collectLog:wsMemberCollectLogList) {
				collectLog.getWsProduct().setProdImage(UrlUtils.getNetUrl(collectLog.getWsProduct().getProdImage()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsMemberCollectLogList",wsMemberCollectLogList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("collect/cancelCollect",e);
		}
		return data;
	}

}