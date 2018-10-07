package com.thinkgem.jeesite.modules.inter.web;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;
import com.thinkgem.jeesite.modules.commission.service.WsCommissionService;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
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
	
	@Autowired
	private WsCommissionService wsCommissionService;
	
	@Autowired
	private WsOrderService wsOrderService;

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
			if ("1".equals(type)) {
				List<WsMember> nextGroup = wsMemberService.findNextGroup(user);
				data.put("nextGroup",nextGroup);
				//查找销售总金额
				BigDecimal amount = wsCommissionService.findPriceByMembers(nextGroup);
				data.put("amount", amount != null ? amount : "0.00");
			}else if ("2".equals(type)) {
				List<WsMember> nextGroup = wsMemberService.findNextGroup(user);
				if (nextGroup != null && nextGroup.size() > 0) {
					nextGroup = wsMemberService.findNextGroupByList(nextGroup);
					data.put("nextGroup",nextGroup);
					//查找销售总金额
					BigDecimal amount = wsCommissionService.findPriceByMembers(nextGroup);
					data.put("amount", amount != null ? amount : "0.00");
				}
			}else if ("3".equals(type)) {
				List<WsMember> nextGroup = wsMemberService.findNextGroup(user);
				if (nextGroup != null && nextGroup.size() > 0) {
					nextGroup = wsMemberService.findNextGroupByList(nextGroup);
					if (nextGroup != null && nextGroup.size() > 0) {
						nextGroup = wsMemberService.findNextGroupByList(nextGroup);
						data.put("nextGroup",nextGroup);
						//查找销售总金额
						BigDecimal amount = wsCommissionService.findPriceByMembers(nextGroup);
						data.put("amount", amount != null ? amount : "0.00");
					}
				}
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
	
	/**
	 * 消费奖金
	 * @param level 1.一级，2.二级，3.三级
	 * @param type 1.推荐，2.消费
	 */
	@RequestMapping(value = "myConsumeBouns")
	@ResponseBody
	@CrossOrigin
	public Map myConsumeBouns(String userId, String type, String level, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WsMember user = wsMemberService.get(userId);
			if ("1".equals(level)) {
				List<WsMember> nextGroup = wsMemberService.findNextGroup(user);
				//查找一级消费
				List<WsCommission> commissions = wsCommissionService.findCommissionByMembers(nextGroup);
				BigDecimal big = BigDecimal.ZERO;
				if (commissions != null && commissions.size() > 0) {
					for(WsCommission c : commissions){
						if ("1".equals(type)) {
							big = big.add(c.getAgent1Promotion());
						}else if ("2".equals(type)) {
							big = big.add(c.getAgent1Consume());
						}
					}
					data.put("amount", big);
					data.put("list", commissions(commissions, type));
				}else{
					data.put("amount", "0.00");
				}
			}else if ("2".equals(level)) {
				List<WsMember> nextGroup = wsMemberService.findNextGroup(user);
				if (nextGroup != null && nextGroup.size() > 0) {
					nextGroup = wsMemberService.findNextGroupByList(nextGroup);
					//查找二级消费
					List<WsCommission> commissions = wsCommissionService.findCommissionByMembers(nextGroup);
					BigDecimal big = BigDecimal.ZERO;
					if (commissions != null && commissions.size() > 0) {
						for(WsCommission c : commissions){
							if ("1".equals(type)) {
								big = big.add(c.getAgent2Promotion());
							}else if ("2".equals(type)) {
								big = big.add(c.getAgent2Consume());
							}
						}
						data.put("amount", big);
						data.put("list", commissions(commissions, level));
					}else{
						data.put("amount", "0.00");
					}
				}
			}else if ("3".equals(level)) {
				List<WsMember> nextGroup = wsMemberService.findNextGroup(user);
				if (nextGroup != null && nextGroup.size() > 0) {
					nextGroup = wsMemberService.findNextGroupByList(nextGroup);
					if (nextGroup != null && nextGroup.size() > 0) {
						nextGroup = wsMemberService.findNextGroupByList(nextGroup);
						//查找三级消费
						List<WsCommission> commissions = wsCommissionService.findCommissionByMembers(nextGroup);
						BigDecimal big = BigDecimal.ZERO;
						if (commissions != null && commissions.size() > 0) {
							for(WsCommission c : commissions){
								if ("1".equals(type)) {
									big = big.add(c.getAgent3Promotion());
								}else if ("2".equals(type)) {
									big = big.add(c.getAgent3Consume());
								}
							}
							data.put("amount", big);
							data.put("list", commissions(commissions, level));
						}else{
							data.put("amount", "0.00");
						}
					}
				}
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
	
	public List<Map<String,Object>> commissions(List<WsCommission> commissions,String level){
		List<Map<String,Object>> list = new ArrayList<>();
		if (commissions != null && commissions.size() > 0) {
			for(WsCommission c : commissions){
				Map<String,Object> map = new HashMap<>();
				map.put("headimgurl", c.getMemberId() != null ? c.getMemberId().getHeadimgurl() : "");
				map.put("nickname", c.getMemberId() != null ? c.getMemberId().getNickname() : "");
				map.put("totalQuantity", c.getOrderId() != null ? c.getOrderId().getTotalQuantity() : "");
				map.put("reallyPrice", c.getOrderId() != null ? c.getOrderId().getReallyPrice() : "");
				map.put("date", DateUtils.formatDate(c.getUpdateDate(), "yyyy-MM-dd"));
				if ("1".equals(level)) {
					map.put("agentConsume", c.getOrderId() != null ? c.getOrderId().getTotalAgent1Consume() : "0.00");
					map.put("agentPromotion",c.getOrderId() != null ? c.getOrderId().getTotalAgent1Promotion() : "0.00");
				} else if ("2".equals(level)) {
					map.put("agentConsume", c.getOrderId() != null ? c.getOrderId().getTotalAgent2Consume() : "0.00");
					map.put("agentPromotion",c.getOrderId() != null ? c.getOrderId().getTotalAgent2Promotion() : "0.00");
				} else if ("3".equals(level)) {
					map.put("agentConsume", c.getOrderId() != null ? c.getOrderId().getTotalAgent3Consume() : "0.00");
					map.put("agentPromotion",c.getOrderId() != null ? c.getOrderId().getTotalAgent3Promotion() : "0.00");
				}
				list.add(map);
			}
		}
		return list;
	}

}
