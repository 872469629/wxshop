package com.thinkgem.jeesite.modules.prod.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.commission.service.WsCommissionService;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberRewardLog;
import com.thinkgem.jeesite.modules.member.service.WsMemberRewardLogService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.dao.WsConsulationDao;
import com.thinkgem.jeesite.modules.prod.entity.WsConsulation;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.sys.service.SysParamService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 评论回复管理Service
 * @author 大胖老师
 * @version 2017-11-14
 */
@Service
@Transactional(readOnly = true)
public class WsConsulationService extends CrudService<WsConsulationDao, WsConsulation> {
	
	@Autowired
	private WsOrderItemService wsOrderItemService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@Autowired
	private WsMemberRewardLogService wsMemberRewardLogService;
	
	@Autowired
	private WsCommissionService wsCommissionService;

	public WsConsulation get(String id) {
		return super.get(id);
	}
	
	public List<WsConsulation> findList(WsConsulation wsConsulation) {
		return super.findList(wsConsulation);
	}
	
	public Page<WsConsulation> findPage(Page<WsConsulation> page, WsConsulation wsConsulation) {
		return super.findPage(page, wsConsulation);
	}
	
	@Transactional(readOnly = false)
	public void save(WsConsulation wsConsulation) {
		super.save(wsConsulation);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsConsulation wsConsulation) {
		super.delete(wsConsulation);
	}
	
	@Transactional(readOnly = false)
	public void saveOrderConsulation(WsMember member,List<WsConsulation> wsConsulationList) {
		String orderId="";
		for (WsConsulation wsConsulation:wsConsulationList) {
			wsConsulation.setMemberId(member.getId());
			save(wsConsulation);
			orderId=wsConsulation.getOrderId();
		}
		WsOrder wsOrder=wsOrderService.get(orderId);
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_FINSH);
		wsOrderService.save(wsOrder);
		//完成时分佣记录结算
		wsCommissionService.updateCommission(wsOrder);
		//商品分销奖励 订单金额*分销比例
		if(wsOrder.getRuid()!=null && StringUtils.isNotEmpty(wsOrder.getRuid().getId())) {
			String scale=sysParamService.getByParamCode(WsConstant.AWARD_SCALE).getParamValue();
			wsMemberService.lock(wsOrder.getRuid().getId());
			WsMember recommondMemeber=wsMemberService.get(wsOrder.getRuid().getId());
			recommondMemeber.setBalance(recommondMemeber.getBalance().add(wsOrder.getReallyPrice().multiply(new BigDecimal(scale))));
			recommondMemeber.setAwardProd(recommondMemeber.getAwardProd().add(wsOrder.getReallyPrice().multiply(new BigDecimal(scale))));
			wsMemberService.save(recommondMemeber);
			//生成会员奖励记录
			WsMemberRewardLog wsMemberRewardLog=new WsMemberRewardLog();
			wsMemberRewardLog.setWsMember(recommondMemeber);
			wsMemberRewardLog.setRewardMoney(wsOrder.getReallyPrice().multiply(new BigDecimal(scale)));
			wsMemberRewardLog.setRewardScore(0);
			wsMemberRewardLog.setRechargeTime(new Date());
			wsMemberRewardLog.setScore(recommondMemeber.getScore());
			wsMemberRewardLog.setBalance(recommondMemeber.getBalance());
			wsMemberRewardLog.setRewardType(WsConstant.REWARD_RECOMMEND);
			wsMemberRewardLog.setRewardDesc("推广赠送奖励："+wsMemberRewardLog.getRewardMoney());
			wsMemberRewardLogService.save(wsMemberRewardLog);
		}
		//推广人分销奖励 产品分销金额*产品数量
		if(StringUtils.isNotEmpty(member.getRecommendMemberId())) {
			BigDecimal awardMoney=new BigDecimal(0);
			List<WsOrderItem> wsOrderItemList=wsOrderItemService.findList(new WsOrderItem(new WsOrder(orderId)));
			for (WsOrderItem wsOrderItem:wsOrderItemList) {
				WsProdSku wsProdSku=wsProdSkuService.get(wsOrderItem.getSkuId());
				awardMoney=awardMoney.add(wsProdSku.getRewardMoney().multiply(new BigDecimal(wsOrderItem.getQuantity())));
			}
			wsMemberService.lock(member.getRecommendMemberId());
			WsMember recommondMemeber=wsMemberService.get(member.getRecommendMemberId());
			recommondMemeber.setBalance(recommondMemeber.getBalance().add(awardMoney));
			recommondMemeber.setAwardFriend(recommondMemeber.getAwardFriend().add(awardMoney));
			wsMemberService.save(recommondMemeber);
			//生成会员奖励记录
			WsMemberRewardLog wsMemberRewardLog=new WsMemberRewardLog();
			wsMemberRewardLog.setWsMember(recommondMemeber);
			wsMemberRewardLog.setRewardMoney(awardMoney);
			wsMemberRewardLog.setRewardScore(0);
			wsMemberRewardLog.setRechargeTime(new Date());
			wsMemberRewardLog.setScore(recommondMemeber.getScore());
			wsMemberRewardLog.setBalance(recommondMemeber.getBalance());
			wsMemberRewardLog.setRewardType(WsConstant.REWARD_RECOMMEND);
			wsMemberRewardLog.setRewardDesc("推广赠送奖励：："+awardMoney);
			wsMemberRewardLogService.save(wsMemberRewardLog);
		}
	}
	
}