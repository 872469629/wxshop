package com.thinkgem.jeesite.modules.prod.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.prod.dao.WsProdAttributeDao;
import com.thinkgem.jeesite.modules.prod.dao.WsProdAttrivalueDao;
import com.thinkgem.jeesite.modules.prod.dao.WsProdSkuAttrDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttribute;
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttrivalue;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSkuAttr;

/**
 * 产品属性Service
 * @author water
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class WsProdAttributeService extends CrudService<WsProdAttributeDao, WsProdAttribute> {

	@Autowired
	private WsProdAttrivalueDao wsProdAttrivalueDao;
	
	@Autowired
	private WsProdSkuAttrDao wsProdSkuAttrDao;
	
	public WsProdAttribute get(String id) {
		WsProdAttribute wsProdAttribute = super.get(id);
		wsProdAttribute.setWsProdAttrivalueList(wsProdAttrivalueDao.findList(new WsProdAttrivalue(wsProdAttribute)));
		return wsProdAttribute;
	}
	
	public List<WsProdAttribute> findList(WsProdAttribute wsProdAttribute) {
		return super.findList(wsProdAttribute);
	}
	
	/**
	 * 根据产品和分类，获取当前产品分类的属性和当前产品的配置值
	 * @param wsProdAttribute
	 * @param productId
	 * @return
	 */
	public List<WsProdAttribute> findSkuVauleList(WsProdAttribute wsProdAttribute,String productId,List<WsProdSkuAttr> skuAttrs) {
		List<WsProdAttribute> wsProdAttributeList=findList(wsProdAttribute);
		for (WsProdAttribute attrbute:wsProdAttributeList) {
			WsProdAttrivalue attrValue=new WsProdAttrivalue();
			attrValue.setAttributeId(attrbute);
			List<WsProdAttrivalue> wsProdAttrivalueList = wsProdAttrivalueDao.findList(attrValue);
			attrbute.setWsProdAttrivalueList(wsProdAttrivalueList);
			WsProdSkuAttr wsProdSkuAttr=new WsProdSkuAttr();
			wsProdSkuAttr.setProdId(productId);
			wsProdSkuAttr.setAttrbuteId(attrbute.getId());
			String defauleValue="";
			for (WsProdSkuAttr skuAttr:skuAttrs) {
				if(attrbute.getId().equals(skuAttr.getAttrbuteId())){
					if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValue())){
						defauleValue=defauleValue+skuAttr.getAttrbuteValue()+",";
					}else{
						defauleValue+=skuAttr.getAttrbuteValueName();
					}
				}
			}
			attrbute.setDefauleValue(defauleValue);
		}
		return wsProdAttributeList;
	}
	
	/**
	 * 根据产品和分类，获取当前产品分类的属性和当前产品的配置值
	 * @param wsProdAttribute
	 * @param productId
	 * @return
	 */
	public List<WsProdAttribute> findVauleList(WsProdAttribute wsProdAttribute,String productId) {
		List<WsProdAttribute> wsProdAttributeList=findList(wsProdAttribute);
		for (WsProdAttribute attrbute:wsProdAttributeList) {
			/**
			 * 查询attribute属性对应的attrvalue属性值
			 */
			WsProdAttrivalue attrValue=new WsProdAttrivalue();
			attrValue.setAttributeId(attrbute);
			List<WsProdAttrivalue> wsProdAttrivalueList = wsProdAttrivalueDao.findList(attrValue);
			attrbute.setWsProdAttrivalueList(wsProdAttrivalueList);
			/**
			 * 查询已录入数据的属性值，如果产品id为空，则新增数据录入值为空
			 */
			String defauleValue="";
			if(StringUtils.isNotEmpty(productId)){
				WsProdSkuAttr wsProdSkuAttr=new WsProdSkuAttr();
				wsProdSkuAttr.setProdId(productId);
				wsProdSkuAttr.setAttrbuteId(attrbute.getId());
				List<WsProdSkuAttr> skuAttrs=wsProdSkuAttrDao.findList(wsProdSkuAttr);
				for (WsProdSkuAttr skuAttr:skuAttrs) {
					if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValue())){
						defauleValue=defauleValue+skuAttr.getAttrbuteValue()+",";
					}else{
						defauleValue+=skuAttr.getAttrbuteValueName();
					}
				}
			}
			attrbute.setDefauleValue(defauleValue);
		}
		return wsProdAttributeList;
	}
	
	public Page<WsProdAttribute> findPage(Page<WsProdAttribute> page, WsProdAttribute wsProdAttribute) {
		return super.findPage(page, wsProdAttribute);
	}
	
	@Transactional(readOnly = false)
	public void save(WsProdAttribute wsProdAttribute) {
		super.save(wsProdAttribute);
		for (WsProdAttrivalue wsProdAttrivalue : wsProdAttribute.getWsProdAttrivalueList()){
			if (wsProdAttrivalue.getId() == null){
				continue;
			}
			if (WsProdAttrivalue.DEL_FLAG_NORMAL.equals(wsProdAttrivalue.getDelFlag())){
				if (StringUtils.isBlank(wsProdAttrivalue.getId())){
					wsProdAttrivalue.setAttributeId(wsProdAttribute);
					wsProdAttrivalue.preInsert();
					wsProdAttrivalueDao.insert(wsProdAttrivalue);
				}else{
					wsProdAttrivalue.preUpdate();
					wsProdAttrivalueDao.update(wsProdAttrivalue);
				}
			}else{
				wsProdAttrivalueDao.delete(wsProdAttrivalue);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WsProdAttribute wsProdAttribute) {
		super.delete(wsProdAttribute);
		wsProdAttrivalueDao.delete(new WsProdAttrivalue(wsProdAttribute));
	}
	
}