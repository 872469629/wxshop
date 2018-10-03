package com.thinkgem.jeesite.modules.commission.service;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.commission.dao.WsCommissionDao;
import com.thinkgem.jeesite.modules.commission.entity.WsCommission;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
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
	public void commission(WsOrderItem item, WsMember wsMember) {
		WsCommission commission = new WsCommission();
		commission.setMemberId(wsMember.getId());
		commission.setOrderId(item.getWsOrder().getId());
		commission.setOrderItemId(item.getId());
		if (StringUtils.isNotEmpty(wsMember.getAgentParent())) {
			WsMember parent = wsMemberService.get(wsMember.getAgentParent());//上级
			if (parent != null) {
				commission.setMemberParent(parent.getId());
				WsAgentRebateConfig config = wsAgentRebateConfigService.findByStatus();//分销配置
				if (StringUtils.isNotEmpty(parent.getAgentParent())) {
					WsMember parentParent = wsMemberService.get(parent.getAgentParent());//上上级
					if (parentParent != null) {
						commission.setMemberParentParent(parentParent.getId());
						if (StringUtils.isNotEmpty(parentParent.getAgentParent())) {
							WsMember parentParentParent = wsMemberService.get(parentParent.getAgentParent());//上上上级
							if (parentParentParent != null) {
								commission.setMemberParentParentParent(parentParentParent.getId());
								if (config != null) {
									String formulary3 = config.getFormulary3();
									formulary3 = formulary3.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y", String.valueOf(config.getLevel3proportion()));
									//返利
									BigDecimal decimal3 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary3);
									commission.setAgent3Consume(decimal3);
									commission.setAgent3ConsumeScale(decimal3);
									parentParentParent.setBalance(parentParentParent.getBalance().add(config.getLevel3promotion()));
									wsMemberService.save(parentParentParent);
								}
							}
						}
						if (config != null) {
							String formulary2 = config.getFormulary2();
							formulary2 = formulary2.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y", String.valueOf(config.getLevel2proportion()));
							//返利
							BigDecimal decimal2 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary2);
							commission.setAgent2Consume(decimal2);
							commission.setAgent2ConsumeScale(config.getLevel2proportion());
							parentParent.setBalance(parentParent.getBalance().add(decimal2));
							wsMemberService.save(parentParent);
						}
					}
				}
				if (config != null) {
					String formulary1 = config.getFormulary1();
					formulary1 = formulary1.replaceAll("X", String.valueOf(item.getReallyPrice())).replaceAll("Y", String.valueOf(config.getLevel1proportion()));
					//返利
					BigDecimal decimal1 = com.thinkgem.jeesite.common.utils.StringUtils.eval(formulary1);
					commission.setAgent1Consume(decimal1);
					commission.setAgent1ConsumeScale(config.getLevel1proportion());
					parent.setBalance(parent.getBalance().add(decimal1));
					wsMemberService.save(parent);
				}
			}
		}
		commission.setStatus("1");
		wsCommissionService.save(commission);
	}

	/**
	 * 非代理商用户成为代理商时推广返利
	 */
	public void toAgentCommission(WsOrderItem item, WsMember wsMember) {
		WsCommission commission = new WsCommission();
		commission.setMemberId(wsMember.getId());
		commission.setOrderId(item.getWsOrder().getId());
		commission.setOrderItemId(item.getId());
		if (StringUtils.isNotEmpty(wsMember.getAgentParent())) {
			WsMember parent = wsMemberService.get(wsMember.getAgentParent());//上级
			if (parent != null) {
				commission.setMemberParent(parent.getId());
				WsAgentRebateConfig config = wsAgentRebateConfigService.findByStatus();//分销配置
				if (StringUtils.isNotEmpty(parent.getAgentParent())) {
					WsMember parentParent = wsMemberService.get(parent.getAgentParent());//上上级
					if (parentParent != null) {
						commission.setMemberParentParent(parentParent.getId());
						if (StringUtils.isNotEmpty(parentParent.getAgentParent())) {
							WsMember parentParentParent = wsMemberService.get(parentParent.getAgentParent());//上上上级
							if (parentParentParent != null) {
								commission.setMemberParentParentParent(parentParentParent.getId());
								if (config != null) {
									parentParentParent.setBalance(parentParentParent.getBalance().add(config.getLevel3promotion()));
									wsMemberService.save(parentParentParent);
									commission.setAgent3Promotion(config.getLevel3promotion());
								}
							}
						}
						if (config != null) {
							parentParent.setBalance(parentParent.getBalance().add(config.getLevel2promotion()));
							wsMemberService.save(parentParent);
							commission.setAgent2Promotion(config.getLevel2promotion());
						}
					}
				}
				if (config != null) {
					parent.setBalance(parent.getBalance().add(config.getLevel1promotion()));
					wsMemberService.save(parent);
					commission.setAgent1Promotion(config.getLevel1promotion());
				}
			}
		}
		commission.setStatus("1");
		wsCommissionService.save(commission);
	}
	
}