package com.thinkgem.jeesite.modules.prod.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.prod.entity.WsAttribute;
import com.thinkgem.jeesite.modules.prod.dao.WsAttributeDao;
import com.thinkgem.jeesite.modules.prod.entity.WsAttrivalue;
import com.thinkgem.jeesite.modules.prod.dao.WsAttrivalueDao;

/**
 * 产品属性Service
 * @author water
 * @version 2017-08-10
 */
@Service
@Transactional(readOnly = true)
public class WsAttributeService extends CrudService<WsAttributeDao, WsAttribute> {

	@Autowired
	private WsAttrivalueDao wsAttrivalueDao;
	
	public WsAttribute get(String id) {
		WsAttribute wsAttribute = super.get(id);
		wsAttribute.setWsAttrivalueList(wsAttrivalueDao.findList(new WsAttrivalue(wsAttribute)));
		return wsAttribute;
	}
	
	public List<WsAttribute> findList(WsAttribute wsAttribute) {
		return super.findList(wsAttribute);
	}
	
	public Page<WsAttribute> findPage(Page<WsAttribute> page, WsAttribute wsAttribute) {
		return super.findPage(page, wsAttribute);
	}
	
	@Transactional(readOnly = false)
	public void save(WsAttribute wsAttribute) {
		super.save(wsAttribute);
		for (WsAttrivalue wsAttrivalue : wsAttribute.getWsAttrivalueList()){
			if (wsAttrivalue.getId() == null){
				continue;
			}
			if (WsAttrivalue.DEL_FLAG_NORMAL.equals(wsAttrivalue.getDelFlag())){
				if (StringUtils.isBlank(wsAttrivalue.getId())){
					wsAttrivalue.setAttributeId(wsAttribute);
					wsAttrivalue.preInsert();
					wsAttrivalueDao.insert(wsAttrivalue);
				}else{
					wsAttrivalue.preUpdate();
					wsAttrivalueDao.update(wsAttrivalue);
				}
			}else{
				wsAttrivalueDao.delete(wsAttrivalue);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WsAttribute wsAttribute) {
		super.delete(wsAttribute);
		wsAttrivalueDao.delete(new WsAttrivalue(wsAttribute));
	}
	
}