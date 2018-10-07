package com.thinkgem.jeesite.modules.commission.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.tools.internal.ws.resources.WscompileMessages;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.commission.dao.WsCommissionDao;
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.rebate.entity.WsAgentRebateConfig;
import com.thinkgem.jeesite.modules.rebate.service.WsAgentRebateConfigService;

/**
 * 分销明细Service
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@Service
@Transactional(readOnly = true)
public class WsCommissionService extends CrudService<WsCommissionDao, WsCommission> {
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsAgentRebateConfigService wsAgentRebateConfigService;
	
	@Autowired
	private WsCommissionService wsCommissionService;

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
	public void commission(WsCommission commission, WsOrderItem item, WsMember wsMember,Integer quantity) {
		WsAgentRebateConfig config = wsAgentRebateConfigService.findByStatus();//分销配置
		if (commission == null) {
			commission = new WsCommission();
		}
		//如果是固定返利，那么还要*数量
		if (config != null && config.getFormulary1() != null && config.getFormulary1().indexOf("X") == -1) {
			config.setLevel1proportion(config.getLevel1proportion().multiply(new BigDecimal(quantity)));
			config.setLevel2proportion(config.getLevel2proportion().multiply(new BigDecimal(quantity)));
			config.setLevel3proportion(config.getLevel3proportion().multiply(new BigDecimal(quantity)));
		}
		commission.setMemberId(wsMember);
		commission.setOrderId(item.getWsOrder());
		commission.setOrderItemId(item);
		if (wsMember.getAgentParent() != null) {
			WsMember parent = wsMemberService.get(wsMember.getAgentParent().getId());//上级
			if (parent != null) {
				commission.setMemberParent(parent);
				if (parent.getAgentParent() != null) {
					WsMember parentParent = wsMemberService.get(parent.getAgentParent().getId());//上上级
					if (parentParent != null) {
						commission.setMemberParentParent(parentParent);
						if (parentParent.getAgentParent() != null) {
							WsMember parentParentParent = wsMemberService.get(parentParent.getAgentParent().getId());// 上上上级
							if (parentParentParent != null) {
								commission.setMemberParentParentParent(parentParentParent);
								if (config != null) {
									commission.setMpppBalanceBefore(parentParentParent.getBalance());
									String formulary3 = config.getFormulary3();
									formulary3 = formulary3.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y", String.valueOf(config.getLevel3proportion()));
									//返利
									BigDecimal decimal3 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary3);
									commission.setAgent3Consume(decimal3);
									commission.setAgent3ConsumeScale(config.getLevel3proportion());
									parentParentParent.setBalance(parentParentParent.getBalance().add(config.getLevel3proportion()));
									wsMemberService.save(parentParentParent);
									commission.setMpppBalanceAfter(parentParentParent.getBalance());
								}
							}
						}
						if (config != null) {
							commission.setMppBalanceBefore(parentParent.getBalance());
							String formulary2 = config.getFormulary2();
							formulary2 = formulary2.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y", String.valueOf(config.getLevel2proportion()));
							//返利
							BigDecimal decimal2 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary2);
							commission.setAgent2Consume(decimal2);
							commission.setAgent2ConsumeScale(config.getLevel2proportion());
							parentParent.setBalance(parentParent.getBalance().add(decimal2));
							wsMemberService.save(parentParent);
							commission.setMppBalanceAfter(parentParent.getBalance());
						}
					}
				}
				if (config != null) {
					commission.setMpBalanceBefore(parent.getBalance());
					String formulary1 = config.getFormulary1();
					formulary1 = formulary1.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y", String.valueOf(config.getLevel1proportion()));
					//返利
					BigDecimal decimal1 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary1);
					commission.setAgent1Consume(decimal1);
					commission.setAgent1ConsumeScale(config.getLevel1proportion());
					parent.setBalance(parent.getBalance().add(decimal1));
					wsMemberService.save(parent);
					commission.setMpBalanceAfter(parent.getBalance());
				}
				//只有上级有代理商的时候，才需要保存分销明细表
				commission.setStatus("1");
			}
		}
	}

	/**
	 * 非代理商用户成为代理商时推广返利
	 */
	public void toAgentCommission(WsCommission commission, WsOrderItem item, WsMember wsMember) {
		WsAgentRebateConfig config = wsAgentRebateConfigService.findByStatus();//分销配置
		if (commission == null) {
			commission = new WsCommission();
		}
		commission.setMemberId(wsMember);
		commission.setOrderId(item.getWsOrder());
		commission.setOrderItemId(item);
		if (wsMember.getAgentParent() != null) {
			WsMember parent = wsMemberService.get(wsMember.getAgentParent().getId());//上级
			if (parent != null) {
				commission.setMemberParent(parent);
				if (parent.getAgentParent() != null) {
					WsMember parentParent = wsMemberService.get(parent.getAgentParent().getId());//上上级
					if (parentParent != null) {
						commission.setMemberParentParent(parentParent);
						if (parentParent.getAgentParent() != null) {
							WsMember parentParentParent = wsMemberService.get(parentParent.getAgentParent().getId());//上上上级
							if (parentParentParent != null) {
								commission.setMemberParentParentParent(parentParentParent);
								if (config != null) {
									commission.setMpppBalanceBefore(parentParentParent.getBalance());
									parentParentParent.setBalance(parentParentParent.getBalance().add(config.getLevel3promotion()));
									wsMemberService.save(parentParentParent);
									commission.setAgent3Promotion(config.getLevel3promotion());
									commission.setMpppBalanceAfter(parentParentParent.getBalance());
								}
							}
						}
						if (config != null) {
							commission.setMppBalanceBefore(parentParent.getBalance());
							parentParent.setBalance(parentParent.getBalance().add(config.getLevel2promotion()));
							wsMemberService.save(parentParent);
							commission.setAgent2Promotion(config.getLevel2promotion());
							commission.setMppBalanceAfter(parentParent.getBalance());
						}
					}
				}
				if (config != null) {
					commission.setMpBalanceBefore(parent.getBalance());
					parent.setBalance(parent.getBalance() != null ? parent.getBalance().add(config.getLevel1promotion())
							: config.getLevel1promotion());
					wsMemberService.save(parent);
					commission.setAgent1Promotion(config.getLevel1promotion());
					commission.setMpBalanceAfter(parent.getBalance());
				}
				//只有上级有代理商的时候，才需要保存分销明细表
				commission.setStatus("1");
				commission.setCreateDate(new Date());
				commission.setUpdateDate(new Date());
			}
		}
	}

	/**
	 * 根据用户id集合查找所有订单总价格
	 */
	public BigDecimal findPriceByMembers(List<WsMember> members){
		List<WsCommission> findPriceByMembers = dao.findPriceByMembers(members);
		if (findPriceByMembers != null && findPriceByMembers.size() > 0) {
			BigDecimal big = BigDecimal.ZERO;
			for(WsCommission c : findPriceByMembers){
				if (c.getOrderId() != null) {
					big = big.add(c.getOrderId().getReallyPrice());
				}
			}
			return big;
		}
		return null;
	}
	
	/**
	 * 根据购买者id集合查找所有分销记录
	 */
	public List<WsCommission> findCommissionByMembers(WsCommission wsCommission){
		return dao.findCommissionByMembers(wsCommission);
	}
	
}