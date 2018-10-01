package com.thinkgem.jeesite.modules.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.member.dao.WsAddressDao;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 用户地址Service
 * @author 大胖老师
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class WsAddressService extends CrudService<WsAddressDao, WsAddress> {
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private WsMemberService wsMemberService;

	public WsAddress get(String id) {
		return super.get(id);
	}
	
	public List<WsAddress> findList(WsAddress wsAddress) {
		return super.findList(wsAddress);
	}
	
	public Page<WsAddress> findPage(Page<WsAddress> page, WsAddress wsAddress) {
		return super.findPage(page, wsAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(WsAddress wsAddress) {
		super.save(wsAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsAddress wsAddress) {
		super.delete(wsAddress);
	}
	
	/**
	 * 根据条件根据当前地址是否默认地址
	 * @param wsAddress
	 */
	@Transactional(readOnly = false)
	public void updateDefault(WsAddress wsAddress) {
		dao.updateDefault(wsAddress);
	}
	
	@Transactional(readOnly = false)
	public List<WsAddress> userDelete(String id,WsMember member) {
		delete(new WsAddress(id));
		/**
		 * 查询用户地址列表
		 */
		WsAddress wsAddress=new WsAddress();
		wsAddress.setWsMember(member);
		List<WsAddress> wsAddressList= findList(wsAddress);
		if(wsAddressList!=null && wsAddressList.size()>0){
			WsAddress address=wsAddressList.get(0);
			address.setIsDefault(WsConstant.YES);
			save(address);
			member.setWsAddress(address);
			wsMemberService.save(member);
		}
		return wsAddressList;
	}
	
	@Transactional(readOnly = false)
	public void userSave(WsAddress wsAddress,WsMember member) {
		WsAddress address=new WsAddress();
		address.setWsMember(member);
		address.setIsDefault(WsConstant.NO);
		updateDefault(address);
		wsAddress.setWsMember(member);
		wsAddress.setIsDefault(WsConstant.YES);
		//设置省份ID
		Area area=new Area();
		area.setName(wsAddress.getCity().split(" ")[0]);
		List<Area> areaList=areaService.findList(area);
		wsAddress.setArea(areaList.get(0));
		save(wsAddress);
	}
	
	
	@Transactional(readOnly = false)
	public void userSaveDefault(String id,WsMember member) {
		member.setWsAddress(new WsAddress(id));
		wsMemberService.save(member);
		//重置默认地址为当前保存的地址
		WsAddress address=new WsAddress();
		address.setWsMember(member);
		address.setIsDefault(WsConstant.NO);
		updateDefault(address);
		if(StringUtils.isNotEmpty(id)){
			WsAddress addr=get(id);
			addr.setIsDefault(WsConstant.YES);
			save(addr);
		}
	}
}