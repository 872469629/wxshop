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
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.withdrawal.entity.WsWithdrawal;
import com.thinkgem.jeesite.modules.withdrawal.service.WsWithdrawalService;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 提现
 * @author 分销开发者
 * @version 2018-10-6
 */
@Controller
@RequestMapping(value = "${wxPath}/withdrawal")
public class WithdrawalController extends BaseController {
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsWithdrawalService wsWithdrawalService;
	
	@Autowired
	private WsOrderService wsOrderService;

	/**
	 * 提现
	 */
	@RequestMapping(value = "tx")
	@ResponseBody
	@CrossOrigin
	public Map tx(String userId, BigDecimal amount, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map data=new HashMap();
		try{
			logger.info("tx.userId:" + userId + ",amount:" + amount);
			WsMember user = wsMemberService.get(userId);
			if (user.getBalance() != null) {
				if (user.getBalance().doubleValue() < amount.doubleValue()) {
					data.put("ret",InterConstant.RET_WX);
					data.put("msg","输入金额不能超过最大提现金额");
				}else{
					wsWithdrawalService.tx(user,amount);
					data.put("ret",InterConstant.RET_SUCCESS);
					data.put("msg","成功");
					data.put("member",user);
				}
			}else{
				data.put("ret",InterConstant.RET_FAILED);
				data.put("msg","提取失败");
			}
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
	 * 提现记录列表
	 */
	@RequestMapping(value = "withdrawalList")
	@ResponseBody
	@CrossOrigin
	public Map withdrawalList(String userId, String status,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			if ("-1".equals(status) || "".equals(status)) {
				status = null;
			}
			logger.info("tx.userId:" + userId );
			WsMember user = wsMemberService.get(userId);
			WsWithdrawal entity = new WsWithdrawal();
			entity.setStatus(status);
			List<WsWithdrawal> ws = wsWithdrawalService.findList(entity);
			List<Map<String,Object>> list = new ArrayList<>();
			if (ws != null && ws.size() > 0) {
				ws.forEach(item->{
					Map<String,Object> map = new HashMap<>();
					map.put("id", item.getId());
					map.put("amount", item.getAmount());
					map.put("nickname", item.getMemberId().getNickname());
					map.put("status", item.getStatus());
					map.put("date",
							item.getProcessDate() != null
									? DateUtils.formatDate(item.getProcessDate(), "yyyy-MM-dd HH:mm")
									: DateUtils.formatDate(item.getCreateDate(), "yyyy-MM-dd HH:mm"));
					list.add(map);
				});
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("msg","成功");
			data.put("list",list);
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
