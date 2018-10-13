package com.thinkgem.jeesite.modules.commission.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.commission.dao.WsCommissionDao;
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;
import com.thinkgem.jeesite.modules.member.dao.WsMemberDao;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.rebate.entity.WsAgentRebateConfig;
import com.thinkgem.jeesite.modules.rebate.service.WsAgentRebateConfigService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 分销明细Service
 * 
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@Service
@Transactional(readOnly = false)
public class WsCommissionService extends CrudService<WsCommissionDao, WsCommission> {

	@Autowired
//	private wsMemberDao wsMemberDao;
	private WsMemberDao wsMemberDao;

	@Autowired
	private WsAgentRebateConfigService wsAgentRebateConfigService;

	@Autowired
	private WsCommissionService wsCommissionService;
	
	@Autowired
	private WsOrderItemService wsOrderItemService;

	public WsCommission get(String id) {
		return super.get(id);
	}

	public List<WsCommission> findList(WsCommission wsCommission) {
		return super.findList(wsCommission);
	}

	public Page<WsCommission> findPage(Page<WsCommission> page, WsCommission wsCommission) {
		return super.findPage(page, wsCommission);
	}

	@Transactional(readOnly = false)
	public void save(WsCommission wsCommission) {
		super.save(wsCommission);
	}

	@Transactional(readOnly = false)
	public void delete(WsCommission wsCommission) {
		super.delete(wsCommission);
	}

	/**
	 * 代理商购买返利
	 */
	public void commission(WsCommission commission, WsOrderItem item, WsMember wsMember, Integer quantity) {
		WsAgentRebateConfig config = wsAgentRebateConfigService.findByStatus();// 分销配置
		if (config == null) {
			throw new RuntimeException("代理商配置出错");
		}
		if (commission == null) {
			commission = new WsCommission();
		}
		// 提交订单会生成分销记录，但是字段都还是空
		if (commission.getStatus() == null) {
			commission.setStatus(WsConstant.NO);
		}
		WsMember parent = null;
		WsMember parentParent = null;
		WsMember parentParentParent = null;
		// 如果是固定返利，那么还要*数量
		if (config != null && config.getFormulary1() != null && config.getFormulary1().indexOf("X") == -1) {
			config.setLevel1proportion(config.getLevel1proportion().multiply(new BigDecimal(quantity)));
			config.setLevel2proportion(config.getLevel2proportion().multiply(new BigDecimal(quantity)));
			config.setLevel3proportion(config.getLevel3proportion().multiply(new BigDecimal(quantity)));
		}
		if (wsMember.getAgentParent() != null) {
			parent = wsMemberDao.get(wsMember.getAgentParent().getId());// 上级
			if (parent != null) {
				if (parent.getAgentParent() != null) {
					parentParent = wsMemberDao.get(parent.getAgentParent().getId());// 上上级
					if (parentParent != null) {
						if (parentParent.getAgentParent() != null) {
							parentParentParent = wsMemberDao.get(parentParent.getAgentParent().getId());// 上上上级
						}
					}
				}
			}
		}
		if (parent != null) {// 上级
			commission.setMemberParent(parent);
			commission.setMpBalanceBefore(parent.getBalance());
			String formulary1 = config.getFormulary1();
			formulary1 = formulary1.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y",
					String.valueOf(config.getLevel1proportion()));
			// 返利
			BigDecimal decimal1 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary1);
			commission.setAgent1Consume(decimal1);
			commission.setAgent1ConsumeScale(config.getLevel1proportion());
			commission.setMpBalanceAfter(parent.getBalance());
			//只有完成订单的时候，分销才会生效
			if (commission.getStatus() == WsConstant.YES) {
				parent.setBalance(parent.getBalance().add(decimal1));
				parent.setTotalConsume(
						parent.getTotalConsume() != null ? parent.getTotalConsume().add(decimal1) : decimal1);// 累加消费
				wsMemberDao.update(parent);
			}
		}
		if (parentParent != null) {// 上上级
			commission.setMemberParentParent(parentParent);
			commission.setMppBalanceBefore(parentParent.getBalance());
			String formulary2 = config.getFormulary2();
			formulary2 = formulary2.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y",
					String.valueOf(config.getLevel2proportion()));
			// 返利
			BigDecimal decimal2 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary2);
			commission.setAgent2Consume(decimal2);
			commission.setAgent2ConsumeScale(config.getLevel2proportion());
			commission.setMppBalanceAfter(parentParent.getBalance());
			//只有完成订单的时候，分销才会生效
			if (commission.getStatus() == WsConstant.YES) {
				parentParent.setBalance(parentParent.getBalance().add(decimal2));
				parentParent.setTotalConsume(
						parentParent.getTotalConsume() != null ? parentParent.getTotalConsume().add(decimal2) : decimal2);// 累加消费
				wsMemberDao.update(parentParent);
			}
		}
		if (parentParentParent != null) {// 上上上级
			commission.setMemberParentParentParent(parentParentParent);
			commission.setMpppBalanceBefore(parentParentParent.getBalance());
			String formulary3 = config.getFormulary3();
			formulary3 = formulary3.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y",
					String.valueOf(config.getLevel3proportion()));
			// 返利
			BigDecimal decimal3 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary3);
			commission.setAgent3Consume(decimal3);
			commission.setAgent3ConsumeScale(config.getLevel3proportion());
			commission.setMpppBalanceAfter(parentParentParent.getBalance());
			//只有完成订单的时候，分销才会生效
			if (commission.getStatus() == WsConstant.YES) {
				parentParentParent.setBalance(parentParentParent.getBalance().add(config.getLevel3proportion()));
				parentParentParent.setTotalConsume(parentParentParent.getTotalConsume() != null
						? parentParentParent.getTotalConsume().add(decimal3) : decimal3);// 累加消费
				wsMemberDao.update(parentParentParent);
			}
		}
		commission.setMemberId(wsMember);
		commission.setOrderId(item.getWsOrder());
		commission.setOrderItemId(item);
		if (commission.getCreateDate() == null) {
			commission.setCreateDate(new Date());
		}
		commission.setUpdateDate(new Date());
	}

	/**
	 * 非代理商用户成为代理商时推广返利
	 */
	public void toAgentCommission(WsCommission commission, WsOrderItem item, WsMember wsMember) {
		WsAgentRebateConfig config = wsAgentRebateConfigService.findByStatus();// 分销配置
		if (config == null) {
			throw new RuntimeException("代理商配置出错");
		}
		if (commission == null) {
			commission = new WsCommission();
		}
		// 提交订单会生成分销记录，但是字段都还是空
		if (commission.getStatus() == null) {
			commission.setStatus(WsConstant.NO);
		}
		WsMember parent = null;
		WsMember parentParent = null;
		WsMember parentParentParent = null;
		if (wsMember.getAgentParent() != null) {
			parent = wsMemberDao.get(wsMember.getAgentParent().getId());// 上级
			if (parent != null) {
				if (parent.getAgentParent() != null) {
					parentParent = wsMemberDao.get(parent.getAgentParent().getId());// 上上级
					if (parentParent != null) {
						if (parentParent.getAgentParent() != null) {
							parentParentParent = wsMemberDao.get(parentParent.getAgentParent().getId());// 上上上级
						}
					}
				}
			}
		}
		if (parent != null) {// 上级
			commission.setMemberParent(parent);
			commission.setMpBalanceBefore(parent.getBalance());
			commission.setAgent1Promotion(config.getLevel1promotion());
			commission.setMpBalanceAfter(parent.getBalance());
			//只有完成订单的时候，分销才会生效
			if (commission.getStatus() == WsConstant.YES) {
				parent.setBalance(parent.getBalance() != null ? parent.getBalance().add(config.getLevel1promotion())
						: config.getLevel1promotion());
				parent.setTotalPromotion(parent.getTotalPromotion() != null
						? parent.getTotalPromotion().add(config.getLevel1promotion()) : config.getLevel1promotion());// 累加推广
			}
			wsMemberDao.update(parent);
		}
		if (parentParent != null) {// 上上级
			commission.setMemberParentParent(parentParent);
			commission.setMppBalanceBefore(parentParent.getBalance());
			commission.setAgent2Promotion(config.getLevel2promotion());
			commission.setMppBalanceAfter(parentParent.getBalance());
			//只有完成订单的时候，分销才会生效
			if (commission.getStatus() == WsConstant.YES) {
				parentParent.setBalance(parentParent.getBalance().add(config.getLevel2promotion()));
				parentParent.setTotalPromotion(parentParent.getTotalPromotion() != null
						? parentParent.getTotalPromotion().add(config.getLevel2promotion()) : config.getLevel2promotion());// 累加推广
				wsMemberDao.update(parentParent);
			}
		}
		if (parentParentParent != null) {// 上上上级
			commission.setMemberParentParentParent(parentParentParent);
			commission.setMpppBalanceBefore(parentParentParent.getBalance());
			commission.setAgent3Promotion(config.getLevel3promotion());
			commission.setMpppBalanceAfter(parentParentParent.getBalance());
			//只有完成订单的时候，分销才会生效
			if (commission.getStatus() == WsConstant.YES) {
				parentParentParent.setBalance(parentParentParent.getBalance().add(config.getLevel3promotion()));
				parentParentParent.setTotalPromotion(parentParentParent.getTotalPromotion() != null
						? parentParentParent.getTotalPromotion().add(config.getLevel3promotion())
								: config.getLevel3promotion());// 累加推广
				wsMemberDao.update(parentParentParent);
			}
		}
		commission.setMemberId(wsMember);
		commission.setOrderId(item.getWsOrder());
		commission.setOrderItemId(item);
		if (commission.getCreateDate() == null) {
			commission.setCreateDate(new Date());
		}
		commission.setUpdateDate(new Date());
	}

	/**
	 * 根据购买者id集合查找所有分销记录
	 */
	public List<WsCommission> findCommissionByMembers(WsMember currMember, String orderBy, String type,List<String> statusList,String status,String level) {
		return dao.findCommissionByMembers(currMember, orderBy, type, statusList, status, level);
	}

	/**
	 * 完成时分佣记录结算
	 */
	public void updateCommission(WsOrder wsOrder) {
		WsMember wsMember = wsMemberDao.get(wsOrder.getMemberId());
		if (wsMember == null)
			return;
		List<WsOrderItem> wsOrderItemList = wsOrderItemService.findList(new WsOrderItem(wsOrder));
		WsCommission entity = new WsCommission();
		entity.setOrderId(wsOrder);
		List<WsCommission> list = dao.findList(entity);
		if (list != null) {
			for (WsCommission c : list) {
				c.setStatus("1");
				WsOrderItem item = wsOrderItemService.get(c.getOrderItemId().getId());
				wsCommissionService.commission(c, item, wsMember, item.getQuantity());
				super.save(c);
			}
			// 如果该用户不是代理商，并且购买的商品是分销商品，校验是否达到要求，如果达到要求的数量，即可送人头钱
			if (wsMember.getIsAgent() == null || WsConstant.NO.equals(wsMember.getIsAgent())) {
				int agentNum = wsMember.getToAgentNum();
				for (WsOrderItem wsOrderItem : wsOrderItemList) {
					if (WsConstant.YES.equals(wsOrderItem.getIsAgentProduct())) {
						agentNum += wsOrderItem.getQuantity();
					}
				}
				wsMember.setToAgentNum(agentNum);
				if (agentNum >= 2) {// 成为代理商
					wsMember.setIsAgent(WsConstant.YES);
					wsMember.setBecomeAgentTime(new Date());
					for (WsCommission c : list) {
						for (WsOrderItem wsOrderItem : wsOrderItemList) {
							if (c.getOrderItemId().getId().equals(wsOrderItem.getId())) {
								// 增加推广金额/人头钱
								wsCommissionService.toAgentCommission(c, wsOrderItem, wsMember);
								super.save(c);
							}
						}
					}
				}
				wsMemberDao.update(wsMember);
			}
		}
	} 

}