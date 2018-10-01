package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.dao.WxAccountDao;
import com.thinkgem.jeesite.modules.ws.entity.WxAccount;

/**
 * 微信账号配置Service
 * @author 大胖老师
 * @version 2017-09-26
 */
@Service
@Transactional(readOnly = true)
public class WxAccountService extends CrudService<WxAccountDao, WxAccount> {

	public WxAccount get(String id) {
		return super.get(id);
	}
	
	public List<WxAccount> findList(WxAccount wxAccount) {
		return super.findList(wxAccount);
	}
	
	public WxAccount getWxAccount() {
		WxAccount wxAccount=new WxAccount();
		List<WxAccount> accounts=dao.findList(new WxAccount());
		if(accounts!=null && accounts.size()>0){
			wxAccount=accounts.get(0);
		}
		return wxAccount;
	}
	
	public Page<WxAccount> findPage(Page<WxAccount> page, WxAccount wxAccount) {
		return super.findPage(page, wxAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(WxAccount wxAccount) {
		super.save(wxAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxAccount wxAccount) {
		super.delete(wxAccount);
	}
	
}