package com.thinkgem.jeesite.modules.rebate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rebate.dao.WsAgentRebateConfigDao;
import com.thinkgem.jeesite.modules.rebate.entity.WsAgentRebateConfig;

/**
 * 代理商分销配置Service
 * 
 * @author 分销系统开发者
 * @version 2018-10-03
 */
@Service
@Transactional(readOnly = true)
public class WsAgentRebateConfigService extends CrudService<WsAgentRebateConfigDao, WsAgentRebateConfig> {

	public WsAgentRebateConfig get(String id) {
		return super.get(id);
	}

	public List<WsAgentRebateConfig> findList(WsAgentRebateConfig wsAgentRebateConfig) {
		return super.findList(wsAgentRebateConfig);
	}

	public Page<WsAgentRebateConfig> findPage(Page<WsAgentRebateConfig> page, WsAgentRebateConfig wsAgentRebateConfig) {
		return super.findPage(page, wsAgentRebateConfig);
	}

	@Transactional(readOnly = false)
	public void save(WsAgentRebateConfig wsAgentRebateConfig) {
		super.save(wsAgentRebateConfig);
	}

	@Transactional(readOnly = false)
	public void delete(WsAgentRebateConfig wsAgentRebateConfig) {
		super.delete(wsAgentRebateConfig);
	}

	public WsAgentRebateConfig findByStatus() {
		WsAgentRebateConfig entity = new WsAgentRebateConfig();
		entity.setStatus("1");
		List<WsAgentRebateConfig> findList = dao.findList(entity);
		if (findList != null && findList.size() > 0) {
			return findList.get(0);
		}
		return null;
	}

}