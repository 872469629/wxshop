package com.thinkgem.jeesite.modules.inter.web;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberRewardLog;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.member.service.WsMemberRewardLogService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.member.service.WsMessageRecordService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.entity.WsConsulation;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.service.WsConsulationService;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuService;
import com.thinkgem.jeesite.modules.sys.service.SysParamService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 评论页面接口
 * @author 大胖老师
 * @version 2017-11-15
 */
@Controller
@RequestMapping(value = "${wxPath}/consulation")
public class ConsulationController extends BaseController {            
	
	@Autowired
	private WsConsulationService wsConsulationService;
	
	@Autowired
	private WsOrderItemService wsOrderItemService;
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;
	
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
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
			 * 查询用户所有评论
			 */
			WsConsulation wsConsulation =new WsConsulation();
			wsConsulation.setMemberId(member.getId());
			List<WsConsulation> wsConsulationList=wsConsulationService.findList(wsConsulation);
			for (WsConsulation consulation:wsConsulationList) {
				consulation.setThumb(UrlUtils.getNetUrl(consulation.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsConsulationList",wsConsulationList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("consulation/index",e);
		}
		return data;
	}
	
	/**
	 * 订单评价页面接口
	 */
	@RequestMapping(value = "findConsulactionOrder")
	@ResponseBody
	@CrossOrigin
	public Map findConsulactionOrder(String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 查询需要评价的商品列表
			 */
			List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(new WsOrderItem(new WsOrder(orderId)));
			for (WsOrderItem wsOrderItem:wsOrderItemList) {
				wsOrderItem.setThumb(UrlUtils.getNetUrl(wsOrderItem.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsOrderItemList",wsOrderItemList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("consulation/findConsulactionOrder",e);
		}
		return data;
	}
	
	/**
	 * 保存评论的数据
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	@CrossOrigin
	public Map save(@RequestBody List<WsConsulation> wsConsulationList,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			wsConsulationService.saveOrderConsulation(member, wsConsulationList);
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("consulation/save",e);
		}
		return data;
	}
	
	/**
	 * 删除评论的数据
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@CrossOrigin
	public Map delete(String consulationId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			WsConsulation wsConsulation=wsConsulationService.get(consulationId);
			/**
			 * 判断当前操作人和评价人是否一致
			 */
			if(!wsConsulation.getMemberId().equals(member.getId())){
				throw new Exception("禁止非法删除!");
			}
			wsConsulationService.delete(wsConsulation);
			/**
			 * 查询用户所有评论
			 */
			wsConsulation =new WsConsulation();
			wsConsulation.setMemberId(member.getId());
			List<WsConsulation> wsConsulationList=wsConsulationService.findList(wsConsulation);
			for (WsConsulation consulation:wsConsulationList) {
				consulation.setThumb(UrlUtils.getNetUrl(consulation.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsConsulationList",wsConsulationList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("consulation/save",e);
		}
		return data;
	}

}