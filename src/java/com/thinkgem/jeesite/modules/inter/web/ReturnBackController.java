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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturnItem;
import com.thinkgem.jeesite.modules.returnback.service.WsReturnItemService;
import com.thinkgem.jeesite.modules.returnback.service.WsReturnService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 申请退款
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/returnBack")
public class ReturnBackController extends BaseController {
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsReturnService wsReturnService;
	
	@Autowired
	private WsReturnItemService wsReturnItemService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private WsOrderItemService wsOrderItemService;
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取申请退款的订单
			 */
			WsOrder wsOrder=wsOrderService.get(orderId);
			if(!wsOrder.getMemberId().getId().equals(member.getId())){
				throw new Exception("非法请求，请勿操作!");
			}
			if(wsOrder.getWsReturn()!=null && StringUtils.isNotEmpty(wsOrder.getWsReturn().getId())){
				throw new Exception("已经申请退款，无法再次操作!");
			}
			WsOrderItem wsOrderItem=new WsOrderItem(wsOrder);
			List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(wsOrderItem);
			//判断产品是否支持退款，如果不支持，则不再申请退款页展示
			for (WsOrderItem item:wsOrderItemList) {
				WsProduct wsProduct=wsProductService.get(item.getWsProduct().getId());
				if(wsProduct.getIsReturn().equals(WsConstant.NO)){
					wsOrderItemList.remove(item);
				}
				item.setThumb(UrlUtils.getNetUrl(item.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsOrderItemList",wsOrderItemList);
			data.put("wsOrder",wsOrder);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("index",e);
		}
		return data;
	}

	@RequestMapping(value = "saveReturnBack")
	@ResponseBody
	@CrossOrigin
	public Map saveReturnBack(@RequestBody List<WsReturnItem> wsReturnItems,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			wsReturnService.saveUserReturn(wsReturnItems, member);
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("index",e);
		}
		return data;
	}


}