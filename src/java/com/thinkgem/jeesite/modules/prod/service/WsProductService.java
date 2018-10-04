package com.thinkgem.jeesite.modules.prod.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.modules.prod.dao.WsProdAttrivalueDao;
import com.thinkgem.jeesite.modules.prod.dao.WsProdSkuDao;
import com.thinkgem.jeesite.modules.prod.dao.WsProductDao;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSkuAttr;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 产品信息Service
 * @author 大胖老师
 * @version 2017-10-01
 */
@Service
@Transactional(readOnly = true)
public class WsProductService extends CrudService<WsProductDao, WsProduct> {

	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@Autowired
	private WsProdSkuAttrService wsProdSkuAttrService;
	
	@Autowired
	private WsProdSkuDao wsProdSkuDao;
	
	@Autowired
	private WsProdAttrivalueDao wsProdAttrivalueDao;
	
	public WsProduct get(String id) {
		WsProduct wsProduct = super.get(id);
		wsProduct.setWsProdSkuList(wsProdSkuService.findList(new WsProdSku(wsProduct)));
		return wsProduct;
	}
	
	public List<WsProduct> findList(WsProduct wsProduct) {
		return super.findList(wsProduct);
	}
	
	public Page<WsProduct> findPage(Page<WsProduct> page, WsProduct wsProduct) {
		return super.findPage(page, wsProduct);
	}
	
	public List<WsProdSku> findWsProdSkuList(WsProduct wsProduct) {
		List<WsProdSku> wsProdSkuListNew=null;//sku数据列表
		/**
		 * 如果是新增产品，则重新生成新的sku数据
		 */
		if(StringUtils.isEmpty(wsProduct.getId())){
			wsProdSkuListNew=findWsProdSkuListNew(wsProduct);
		}else{
			/**
			 * 修改产品，则判断销售属性是否修改，如果修改销售属性，则重新生成sku
			 */
			if(wsProduct.getIsSelChange().equals(WsConstant.YES)||wsProduct.getIsBaseChange().equals(WsConstant.YES)){
				wsProdSkuListNew=findWsProdSkuListNew(wsProduct);
			}else{
				WsProdSku wsProdSku=new WsProdSku();
				wsProdSku.setWsProduct(wsProduct);
				wsProdSkuListNew=wsProdSkuService.findList(wsProdSku);
			}
		}
		return wsProdSkuListNew;
	}
	
	/**
	 * 如果销售属性改变，则重新生成sku明细
	 * @param wsProduct
	 * @return
	 */
	public List<WsProdSku> findWsProdSkuListNew(WsProduct wsProduct) {
		List<WsProdSku> wsProdSkuListNew=null;//sku数据列表
		List<WsProdSku> wsProdSkuListNext=null;//sku数据指针
		List<WsProdSkuAttr> wsProdSkuAttrSelList=wsProduct.getWsProdSkuAttrSelList();
		for (WsProdSkuAttr skuAttr : wsProdSkuAttrSelList) {
			/**
			 * 初始化sku第一个属性
			 */
			if(wsProdSkuListNext==null){
				wsProdSkuListNext=new ArrayList<WsProdSku>();
				//单选，复选，下拉框选中之后AttrbuteValue不为空
				if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValue())){
					String [] attrStr=skuAttr.getAttrbuteValue().split(",");
					for (int i = 0; i < attrStr.length; i++) {
						WsProdSku sk=getProdSku(wsProduct, skuAttr,attrStr[i]);
						wsProdSkuListNext.add(sk);
						wsProdSkuListNew=wsProdSkuListNext;
					}
				}else{
					//输入框AttrbuteValue为空,AttrbuteValueName不为空
					if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValueName())){
						WsProdSku sk=getProdSku(wsProduct,skuAttr, "");
						wsProdSkuListNext.add(sk);
						wsProdSkuListNew=wsProdSkuListNext;
					}
				}
			}else{
				/**
				 * 初始化sku后续链接的属性
				 */				
				//单选，复选，下拉框选中之后AttrbuteValue不为空
				if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValue())){
					wsProdSkuListNext=new ArrayList<WsProdSku>();
					for (WsProdSku newSku : wsProdSkuListNew) {
						String [] attrStr=skuAttr.getAttrbuteValue().split(",");
						for (int i = 0; i < attrStr.length; i++) {
							WsProdSku sk=getProdSkuLink(wsProduct, skuAttr,attrStr[i],newSku);
							wsProdSkuListNext.add(sk);
						}
					}
					wsProdSkuListNew=wsProdSkuListNext;
				}else{
					wsProdSkuListNext=new ArrayList<WsProdSku>();
					//输入框AttrbuteValue为空,AttrbuteValueName不为空
					if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValueName())){
						for (WsProdSku newSku : wsProdSkuListNew) {
								WsProdSku sk=getProdSkuLink(wsProduct, skuAttr,"",newSku);
								wsProdSkuListNext.add(sk);
						}
						wsProdSkuListNew=wsProdSkuListNext;
					}
				}
			}
		}
		return wsProdSkuListNew;
	}
	
	@Transactional(readOnly = false)
	public void saveProduct(WsProduct wsProduct) {
		super.save(wsProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(WsProduct wsProduct) {
		super.save(wsProduct);
		//生成销售属性和基本属性
		if (wsProduct.getIsSelChange() != null && (wsProduct.getIsSelChange().equals(WsConstant.YES)
				|| wsProduct.getIsBaseChange().equals(WsConstant.YES))) {
			/**
			 * 删除重置sku属性
			 */
			WsProdSku wsProdSku=new WsProdSku();
			wsProdSku.setWsProduct(wsProduct);
			wsProdSkuDao.deleteByProId(wsProdSku);
			/**
			 * 删除重置skuAttr属性
			 */
			WsProdSkuAttr wsAttr=new WsProdSkuAttr();
			wsAttr.setProdId(wsProduct.getId());
			wsProdSkuAttrService.deleteByProId(wsAttr);
			wsProdSkuAttrService.saveList(wsProduct.getWsProdSkuAttrBaseList(),wsProduct.getId());
			wsProdSkuAttrService.saveList(wsProduct.getWsProdSkuAttrSelList(),wsProduct.getId());
		}
		BigDecimal minPrice=wsProduct.getWsProdSkuList().get(0).getReallyPrice();
		BigDecimal maxPrice=wsProduct.getWsProdSkuList().get(0).getReallyPrice();
		for (WsProdSku wsProdSku : wsProduct.getWsProdSkuList()){
			if(wsProdSku.getReallyPrice().compareTo(minPrice)==1){
				maxPrice=wsProdSku.getReallyPrice();
			}else{
				minPrice=wsProdSku.getReallyPrice();
			}
			wsProdSku.setState(WsConstant.VALID);
			if (wsProdSku.getId() == null){
				continue;
			}
			if (WsProdSku.DEL_FLAG_NORMAL.equals(wsProdSku.getDelFlag())){
				if (StringUtils.isBlank(wsProdSku.getId())){
					wsProdSku.setWsProduct(wsProduct);
					wsProdSku.preInsert();
					wsProdSkuDao.insert(wsProdSku);
				}else{
					wsProdSku.preUpdate();
					wsProdSkuDao.update(wsProdSku);
				}
			}else{
				wsProdSkuDao.delete(wsProdSku);
			}
		}
		/**
		 * 计算并修改价格范围
		 */
		if(minPrice.compareTo(new BigDecimal(0))==0){
			wsProduct.setRangePrice(maxPrice.toString());
			wsProduct.setMinPrice(maxPrice);
		}else{
			wsProduct.setRangePrice(minPrice.toString()+"---"+maxPrice.toString());	
			wsProduct.setMinPrice(minPrice);
		}
		super.save(wsProduct);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(WsProduct wsProduct) {
		super.delete(wsProduct);
		wsProdSkuService.deleteByProId(new WsProdSku(wsProduct));
		WsProdSkuAttr wsProdSkuAttr=new WsProdSkuAttr();
		wsProdSkuAttr.setProdId(wsProduct.getId());
		wsProdSkuAttrService.deleteByProId(wsProdSkuAttr);
	}
	
	public List<String> getProdImageList(String prodImage,String prodImages){
		List<String> prodImageList=new ArrayList<String>();
		prodImageList.add(UrlUtils.getNetUrl(prodImage));
		if(StringUtils.isNotEmpty(prodImages)){
			String [] images=prodImages.split("\\|");
			for (int i = 0; i < images.length; i++) {
				if(StringUtils.isNotEmpty(images[i])){
					prodImageList.add(UrlUtils.getNetUrl(images[i]));
				}
			}
		}
		return prodImageList;
	}
	
	/**
	 * 根据单个skuAttr属性拼接sku
	 * @param wsProduct
	 * @param skuAttr
	 * @return
	 */
	private WsProdSku getProdSku(WsProduct wsProduct,WsProdSkuAttr skuAttr,String attrValueId){
		if(StringUtils.isNotEmpty(attrValueId)){
			skuAttr.setAttrbuteValueName(wsProdAttrivalueDao.get(attrValueId).getAttrvalueValue());
		}
		WsProdSku sk=new WsProdSku();
		sk.setWsProduct(new WsProduct(wsProduct.getId()));
		sk.setAttributeValues(skuAttr.getAttrbuteId());
		sk.setAttrivalueValues(attrValueId);
		sk.setSkuName(skuAttr.getAttrbuteName()+":"+skuAttr.getAttrbuteValueName());
		sk.setPrice(wsProduct.getDefaultPrice());
		sk.setReallyPrice(wsProduct.getDefaultReallyPrice());
		sk.setRewardMoney(wsProduct.getDefaultRewardMoney());
		sk.setSurplusQuantity(wsProduct.getDefaultNum());
		sk.setState(WsConstant.VALID);
		return sk;
	}
	
	/**
	 * 根据单个skuAttr属性拼接sku链接
	 * @param wsProduct
	 * @param skuAttr
	 * @return
	 */
	private WsProdSku getProdSkuLink(WsProduct wsProduct,WsProdSkuAttr skuAttr,String attrValueId,WsProdSku wsProdSku){
		if(StringUtils.isNotEmpty(attrValueId)){
			skuAttr.setAttrbuteValueName(wsProdAttrivalueDao.get(attrValueId).getAttrvalueValue());
		}
		WsProdSku sk=new WsProdSku();
		sk.setWsProduct(new WsProduct(wsProduct.getId()));
		sk.setAttributeValues(wsProdSku.getAttributeValues()+","+skuAttr.getAttrbuteId());
		sk.setAttrivalueValues(wsProdSku.getAttrivalueValues()+","+attrValueId);
		sk.setSkuName(wsProdSku.getSkuName()+","+skuAttr.getAttrbuteName()+":"+skuAttr.getAttrbuteValueName());
		sk.setPrice(wsProduct.getDefaultPrice());
		sk.setReallyPrice(wsProduct.getDefaultReallyPrice());
		sk.setRewardMoney(wsProduct.getDefaultRewardMoney());
		sk.setSurplusQuantity(wsProduct.getDefaultNum());
		sk.setState(WsConstant.VALID);
		return sk;
	}
	
	/**
	 * 通过编号获取内容标题
	 * @return new Object[]{栏目Id,文章Id,文章标题}
	 */
	public List<Object[]> findByIds(String ids) {
		if(ids == null){
			return new ArrayList<Object[]>();
		}
		List<Object[]> list = Lists.newArrayList();
		String[] idss = StringUtils.split(ids,",");
		WsProduct e = null;
		for(int i=0;(idss.length-i)>0;i++){
			e = dao.get(idss[i]);
			list.add(new Object[]{e.getProdCategoryId(),e.getId(),StringUtils.abbr(e.getTitle(),50)});
		}
		return list;
	}
}