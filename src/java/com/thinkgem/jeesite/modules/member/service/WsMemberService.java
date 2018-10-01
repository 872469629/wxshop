package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.member.dao.WsAddressDao;
import com.thinkgem.jeesite.modules.member.dao.WsMemberAttrDao;
import com.thinkgem.jeesite.modules.member.dao.WsMemberConsumeLogDao;
import com.thinkgem.jeesite.modules.member.dao.WsMemberCouponDao;
import com.thinkgem.jeesite.modules.member.dao.WsMemberDao;
import com.thinkgem.jeesite.modules.member.dao.WsMemberRechargeLogDao;
import com.thinkgem.jeesite.modules.member.dao.WsMemberRewardLogDao;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberAttr;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 会员资料Service
 * @author zengyq
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsMemberService extends CrudService<WsMemberDao, WsMember> {

	@Autowired
	private WsAddressDao wsAddressDao;
	@Autowired
	private WsMemberAttrDao wsMemberAttrDao;
	@Autowired
	private WsMemberConsumeLogDao wsMemberConsumeLogDao;
	@Autowired
	private WsMemberCouponDao wsMemberCouponDao;
	@Autowired
	private WsMemberRechargeLogDao wsMemberRechargeLogDao;
	@Autowired
	private WsMemberRewardLogDao wsMemberRewardLogDao;
	
	public WsMember get(String id) {
		WsMember wsMember = super.get(id);
		wsMember.setWsAddressList(wsAddressDao.findList(new WsAddress(wsMember)));
		wsMember.setWsMemberAttrList(wsMemberAttrDao.findList(new WsMemberAttr(wsMember)));
//		// zengyq.20171009 预留扩展
//		wsMember.setWsMemberConsumeLogList(wsMemberConsumeLogDao.findList(new WsMemberConsumeLog(wsMember)));
//		wsMember.setWsMemberCouponList(wsMemberCouponDao.findList(new WsMemberCoupon(wsMember)));
//		wsMember.setWsMemberRechargeLogList(wsMemberRechargeLogDao.findList(new WsMemberRechargeLog(wsMember)));
//		wsMember.setWsMemberRewardLogList(wsMemberRewardLogDao.findList(new WsMemberRewardLog(wsMember)));
		return wsMember;
	}
	
	public WsMember getByOpenId(String openId){
		return dao.getByOpenId(openId);
	}
	
	public List<WsMember> findList(WsMember wsMember) {
		return super.findList(wsMember);
	}
	
	public int findCount(WsMember wsMember) {
		return dao.findCount(wsMember);
	}
	
	public Page<WsMember> findPage(Page<WsMember> page, WsMember wsMember) {
		return super.findPage(page, wsMember);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMember wsMember) {
		super.save(wsMember);
		UserUtils.getSession().setAttribute("member", null);
		for (WsAddress wsAddress : wsMember.getWsAddressList()){
			if (wsAddress.getId() == null){
				continue;
			}
			if (WsAddress.DEL_FLAG_NORMAL.equals(wsAddress.getDelFlag())){
				if (StringUtils.isBlank(wsAddress.getId())){
					wsAddress.setWsMember(wsMember);
					wsAddress.preInsert();
					wsAddressDao.insert(wsAddress);
				}else{
					wsAddress.preUpdate();
					wsAddressDao.update(wsAddress);
				}
			}else{
				wsAddressDao.delete(wsAddress);
			}
		}
		for (WsMemberAttr wsMemberAttr : wsMember.getWsMemberAttrList()){
			if (wsMemberAttr.getId() == null){
				continue;
			}
			if (WsMemberAttr.DEL_FLAG_NORMAL.equals(wsMemberAttr.getDelFlag())){
				if (StringUtils.isBlank(wsMemberAttr.getId())){
					wsMemberAttr.setWsMember(wsMember);
					wsMemberAttr.preInsert();
					wsMemberAttrDao.insert(wsMemberAttr);
				}else{
					wsMemberAttr.preUpdate();
					wsMemberAttrDao.update(wsMemberAttr);
				}
			}else{
				wsMemberAttrDao.delete(wsMemberAttr);
			}
		}
//		// zengyq.20171009 预留扩展
//		for (WsMemberConsumeLog wsMemberConsumeLog : wsMember.getWsMemberConsumeLogList()){
//			if (wsMemberConsumeLog.getId() == null){
//				continue;
//			}
//			if (WsMemberConsumeLog.DEL_FLAG_NORMAL.equals(wsMemberConsumeLog.getDelFlag())){
//				if (StringUtils.isBlank(wsMemberConsumeLog.getId())){
//					wsMemberConsumeLog.setWsMember(wsMember);
//					wsMemberConsumeLog.preInsert();
//					wsMemberConsumeLogDao.insert(wsMemberConsumeLog);
//				}else{
//					wsMemberConsumeLog.preUpdate();
//					wsMemberConsumeLogDao.update(wsMemberConsumeLog);
//				}
//			}else{
//				wsMemberConsumeLogDao.delete(wsMemberConsumeLog);
//			}
//		}
//		for (WsMemberCoupon wsMemberCoupon : wsMember.getWsMemberCouponList()){
//			if (wsMemberCoupon.getId() == null){
//				continue;
//			}
//			if (WsMemberCoupon.DEL_FLAG_NORMAL.equals(wsMemberCoupon.getDelFlag())){
//				if (StringUtils.isBlank(wsMemberCoupon.getId())){
//					wsMemberCoupon.setWsMember(wsMember);
//					wsMemberCoupon.preInsert();
//					wsMemberCouponDao.insert(wsMemberCoupon);
//				}else{
//					wsMemberCoupon.preUpdate();
//					wsMemberCouponDao.update(wsMemberCoupon);
//				}
//			}else{
//				wsMemberCouponDao.delete(wsMemberCoupon);
//			}
//		}
//		for (WsMemberRechargeLog wsMemberRechargeLog : wsMember.getWsMemberRechargeLogList()){
//			if (wsMemberRechargeLog.getId() == null){
//				continue;
//			}
//			if (WsMemberRechargeLog.DEL_FLAG_NORMAL.equals(wsMemberRechargeLog.getDelFlag())){
//				if (StringUtils.isBlank(wsMemberRechargeLog.getId())){
//					wsMemberRechargeLog.setWsMember(wsMember);
//					wsMemberRechargeLog.preInsert();
//					wsMemberRechargeLogDao.insert(wsMemberRechargeLog);
//				}else{
//					wsMemberRechargeLog.preUpdate();
//					wsMemberRechargeLogDao.update(wsMemberRechargeLog);
//				}
//			}else{
//				wsMemberRechargeLogDao.delete(wsMemberRechargeLog);
//			}
//		}
//		for (WsMemberRewardLog wsMemberRewardLog : wsMember.getWsMemberRewardLogList()){
//			if (wsMemberRewardLog.getId() == null){
//				continue;
//			}
//			if (WsMemberRewardLog.DEL_FLAG_NORMAL.equals(wsMemberRewardLog.getDelFlag())){
//				if (StringUtils.isBlank(wsMemberRewardLog.getId())){
//					wsMemberRewardLog.setWsMember(wsMember);
//					wsMemberRewardLog.preInsert();
//					wsMemberRewardLogDao.insert(wsMemberRewardLog);
//				}else{
//					wsMemberRewardLog.preUpdate();
//					wsMemberRewardLogDao.update(wsMemberRewardLog);
//				}
//			}else{
//				wsMemberRewardLogDao.delete(wsMemberRewardLog);
//			}
//		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMember wsMember) {
		super.delete(wsMember);
		wsAddressDao.delete(new WsAddress(wsMember));
		wsMemberAttrDao.delete(new WsMemberAttr(wsMember));
//		// zengyq.20171009 预留扩展
//		wsMemberConsumeLogDao.delete(new WsMemberConsumeLog(wsMember));
//		wsMemberCouponDao.delete(new WsMemberCoupon(wsMember));
//		wsMemberRechargeLogDao.delete(new WsMemberRechargeLog(wsMember));
//		wsMemberRewardLogDao.delete(new WsMemberRewardLog(wsMember));
	}
	
	@Transactional(readOnly = false)
	public void lock(String id) {
		dao.lock(id);
	}

	public int update(WsMember member) {
		return dao.update(member);
	}
}