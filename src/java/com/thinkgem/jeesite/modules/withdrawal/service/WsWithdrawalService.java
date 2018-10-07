package com.thinkgem.jeesite.modules.withdrawal.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.member.dao.WsMemberDao;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.withdrawal.dao.WsWithdrawalDao;
import com.thinkgem.jeesite.modules.withdrawal.entity.WsWithdrawal;

/**
 * 提现Service
 * @author 分销系统开发者
 * @version 2018-10-07
 */
@Service
@Transactional(readOnly = true)
public class WsWithdrawalService extends CrudService<WsWithdrawalDao, WsWithdrawal> {
	
	@Autowired
	private WsMemberDao wsMemberDao;

	public WsWithdrawal get(String id) {
		return super.get(id);
	}
	
	public List<WsWithdrawal> findList(WsWithdrawal wsWithdrawal) {
		return super.findList(wsWithdrawal);
	}
	
	public Page<WsWithdrawal> findPage(Page<WsWithdrawal> page, WsWithdrawal wsWithdrawal) {
		return super.findPage(page, wsWithdrawal);
	}
	
	@Transactional(readOnly = false)
	public void save(WsWithdrawal wsWithdrawal) {
		super.save(wsWithdrawal);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsWithdrawal wsWithdrawal) {
		super.delete(wsWithdrawal);
	}

	/**
	 * 提现
	 */
	@Transactional(readOnly = false)
	public void tx(WsMember user, BigDecimal amount) {
		WsWithdrawal entity = new WsWithdrawal();
		entity.setCreateDate(new Date());
		entity.setUpdateDate(new Date());
		entity.setAmount(amount);
		entity.setAvailableAmt(user.getBalance());
		entity.setStatus("0");
		entity.setMemberId(user);
		super.save(entity);
		user.setBalance(user.getBalance().subtract(amount));
		user.setFrozenAmount(user.getFrozenAmount() != null ? user.getFrozenAmount().add(amount) : amount);
		wsMemberDao.update(user);
	}

	/**
	 * 审核
	 */
	@Transactional(readOnly = false)
	public void audit(WsWithdrawal wsWithdrawal) {
		wsWithdrawal.setProcessDate(new Date());
		WsMember memberId = wsWithdrawal.getMemberId();
		WsMember wsMember = wsMemberDao.get(memberId.getId());
		wsMember.setFrozenAmount(wsMember.getFrozenAmount().subtract(wsWithdrawal.getAmount()));
		if ("1".equals(wsWithdrawal.getStatus())) {//成功
			
		} else if ("2".equals(wsWithdrawal.getStatus())) {// 无效
			wsMember.setBalance(wsMember.getBalance().add(wsWithdrawal.getAmount()));
		}
		super.save(wsWithdrawal);
		wsMemberDao.update(wsMember);
	}
	
}