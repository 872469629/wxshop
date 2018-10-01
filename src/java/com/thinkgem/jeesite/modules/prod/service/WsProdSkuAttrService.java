package com.thinkgem.jeesite.modules.prod.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.prod.dao.WsProdAttrivalueDao;
import com.thinkgem.jeesite.modules.prod.dao.WsProdSkuAttrDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttribute;
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttrivalue;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSkuAttr;

/**
 * 产品sku属性Service
 * @author 大胖老师
 * @version 2017-10-02
 */
@Service
@Transactional(readOnly = true)
public class WsProdSkuAttrService extends CrudService<WsProdSkuAttrDao, WsProdSkuAttr> {
	
	@Autowired
	private WsProdAttributeService wsProdAttributeService;
	
	@Autowired
	private WsProdAttrivalueDao wsProdAttrivalueDao;

	public WsProdSkuAttr get(String id) {
		return super.get(id);
	}
	
	public List<WsProdSkuAttr> findList(WsProdSkuAttr wsProdSkuAttr) {
		return super.findList(wsProdSkuAttr);
	}
	
	public List<WsProdSkuAttr> findAttrbuteIdList(WsProdSkuAttr wsProdSkuAttr) {
		return dao.findAttrbuteIdList(wsProdSkuAttr);
	}
	
	public Page<WsProdSkuAttr> findPage(Page<WsProdSkuAttr> page, WsProdSkuAttr wsProdSkuAttr) {
		return super.findPage(page, wsProdSkuAttr);
	}
	
	@Transactional(readOnly = false)
	public void save(WsProdSkuAttr wsProdSkuAttr) {
		super.save(wsProdSkuAttr);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsProdSkuAttr wsProdSkuAttr) {
		super.delete(wsProdSkuAttr);
	}
	
	@Transactional(readOnly = false)
	public void deleteByProId(WsProdSkuAttr wsProdSkuAttr) {
		dao.deleteByProId(wsProdSkuAttr);
	}
	
	/**
	 * 保存sku属性，并生成属性链接树
	 * @param attrList
	 * @return
	 */
	public void saveList(List<WsProdSkuAttr> attrList,String prodId){
		for (WsProdSkuAttr attr:attrList) {
			List<WsProdSkuAttr> wsProdSkuAttrList=new ArrayList<WsProdSkuAttr>();
			if(StringUtils.isNotEmpty(attr.getAttrbuteValue())){
				if(attr.getAttrbuteValue().equals("null")){
					continue;
				}
				//保存单选，复选下拉框的属性值
				String [] attrStr=attr.getAttrbuteValue().split(",");
				for (int i = 0; i < attrStr.length; i++) {
					WsProdSkuAttr wsProdSkuAttr=new WsProdSkuAttr();
					wsProdSkuAttr.setProdId(prodId);
					wsProdSkuAttr.setAttrbuteId(attr.getAttrbuteId());
					WsProdAttribute wsProdAttribute=wsProdAttributeService.get(attr.getAttrbuteId());
					wsProdSkuAttr.setAttrbuteName(wsProdAttribute.getAttrName());
					WsProdAttrivalue wsProdAttrivalue=wsProdAttrivalueDao.get(attrStr[i]);
					wsProdSkuAttr.setAttrbuteValue(wsProdAttrivalue.getId());
					wsProdSkuAttr.setAttrbuteValueName(wsProdAttrivalue.getAttrvalueValue());
					wsProdSkuAttrList.add(wsProdSkuAttr);
					super.save(wsProdSkuAttr);
				}
			}else{
				//保存输入框的属性值
				if(StringUtils.isEmpty(attr.getAttrbuteValueName())&&attr.getAttrbuteValue().equals("null")){
					continue;
				}
				WsProdSkuAttr wsProdSkuAttr=new WsProdSkuAttr();
				wsProdSkuAttr.setProdId(prodId);
				wsProdSkuAttr.setAttrbuteId(attr.getAttrbuteId());
				WsProdAttribute wsProdAttribute=wsProdAttributeService.get(attr.getAttrbuteId());
				wsProdSkuAttr.setAttrbuteName(wsProdAttribute.getAttrName());
				wsProdSkuAttr.setAttrbuteValueName(attr.getAttrbuteValueName());
				wsProdSkuAttrList.add(wsProdSkuAttr);
				super.save(wsProdSkuAttr);
			}
		}
	}
	
}