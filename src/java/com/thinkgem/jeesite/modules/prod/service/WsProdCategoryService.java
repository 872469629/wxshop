package com.thinkgem.jeesite.modules.prod.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.prod.entity.WsProdCategory;
import com.thinkgem.jeesite.modules.prod.dao.WsProdCategoryDao;

/**
 * 产品分类Service
 * @author water
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class WsProdCategoryService extends TreeService<WsProdCategoryDao, WsProdCategory> {

	public WsProdCategory get(String id) {
		return super.get(id);
	}
	
	public List<WsProdCategory> findList(WsProdCategory wsProdCategory) {
		if (StringUtils.isNotBlank(wsProdCategory.getParentIds())){
			wsProdCategory.setParentIds(","+wsProdCategory.getParentIds()+",");
		}
		return super.findList(wsProdCategory);
	}
	
	public List<WsProdCategory> findChildList(WsProdCategory wsProdCategory) {
		if (StringUtils.isNotBlank(wsProdCategory.getParentIds())){
			wsProdCategory.setParentIds(","+wsProdCategory.getParentIds()+",");
		}
		List<WsProdCategory> wsProdCategoryList=new ArrayList<WsProdCategory>();;
		for(WsProdCategory cat:super.findList(wsProdCategory)){
			if(!cat.getParentId().equals("0")){
				wsProdCategoryList.add(cat);
			}
		}
		return wsProdCategoryList;
	}
	
	@Transactional(readOnly = false)
	public void save(WsProdCategory wsProdCategory) {
		super.save(wsProdCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsProdCategory wsProdCategory) {
		super.delete(wsProdCategory);
	}
	
}