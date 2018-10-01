package com.thinkgem.jeesite.modules.config.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.config.dao.WsMrankDao;
import com.thinkgem.jeesite.modules.config.entity.WsMrank;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 会员等级Service
 * @author water
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class WsMrankService extends CrudService<WsMrankDao, WsMrank> {

	public WsMrank get(String id) {
		return super.get(id);
	}
	
	public List<WsMrank> findList(WsMrank wsMrank) {
		return super.findList(wsMrank);
	}
	
	public Page<WsMrank> findPage(Page<WsMrank> page, WsMrank wsMrank) {
		return super.findPage(page, wsMrank);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMrank wsMrank) {
		/**
		 * 判断是否已经存在默认的会员等级，如果已存在，则修改此默认会员等级为非默认会员等级
		 */
		if(wsMrank.getIsDefault().equals(WsConstant.YES)){
			WsMrank mrank=new WsMrank();
			mrank.setIsDefault(WsConstant.YES);
			List<WsMrank> mrankList= findList(mrank);
			for (WsMrank rank:mrankList) {
				if(!rank.getId().equals(wsMrank.getId())){
					rank.setIsDefault(WsConstant.NO);
					save(rank);
				}
			}
		}
		//保存会员等级信息
		super.save(wsMrank);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMrank wsMrank) {
		super.delete(wsMrank);
	}
	
	//求出满足积分条件的最优惠会员等级
	@Transactional(readOnly = false)
	public WsMrank getMrankByScore(int score) {
		//循环所有的会员等级进行判断，找出最符合条件的会员等级
		List<WsMrank> wsMrankList=findList(new WsMrank());
		WsMrank wsMrank=null;
		for (WsMrank mrank:wsMrankList) {
			if(mrank.getAmount()<=score){
				if(wsMrank==null){
					wsMrank=mrank;
				}else{
					//找出折扣最低的会员等级
					if(wsMrank.getScale().compareTo(mrank.getScale())>=0){
						wsMrank=mrank;
					}
				}
			}
		}
		return wsMrank;
	}
}