package com.thinkgem.jeesite.modules.inter.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCoupon;
import com.thinkgem.jeesite.modules.activity.entity.WsActivityCouponProd;
import com.thinkgem.jeesite.modules.activity.service.WsActivityCouponProdService;
import com.thinkgem.jeesite.modules.activity.service.WsActivityCouponService;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMemberCollectLog;
import com.thinkgem.jeesite.modules.member.entity.WsMemberSearchLog;
import com.thinkgem.jeesite.modules.member.entity.WsMemberVisitLog;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.member.service.WsMemberCollectLogService;
import com.thinkgem.jeesite.modules.member.service.WsMemberSearchLogService;
import com.thinkgem.jeesite.modules.member.service.WsMemberVisitLogService;
import com.thinkgem.jeesite.modules.member.service.WsMessageRecordService;
import com.thinkgem.jeesite.modules.prod.entity.WsBrand;
import com.thinkgem.jeesite.modules.prod.entity.WsConsulation;
import com.thinkgem.jeesite.modules.prod.entity.WsProdCategory;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSkuAttr;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsBrandService;
import com.thinkgem.jeesite.modules.prod.service.WsConsulationService;
import com.thinkgem.jeesite.modules.prod.service.WsProdCategoryService;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuAttrService;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.sys.service.SysParamService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 产品接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/prod")
public class ProductController extends BaseController {
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;
	
	@Autowired
	private WsBrandService wsBrandService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private WsProdCategoryService wsProdCategoryService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsProdSkuAttrService wsProdSkuAttrService;
	
	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@Autowired
	private WsConsulationService wsConsulationService;
	
	@Autowired
	private WsActivityCouponService wsActivityCouponService;
	
	@Autowired
	private WsActivityCouponProdService wsActivityCouponProdService;
	
	@Autowired
	private WsMemberVisitLogService wsMemberVisitLogService;  
	
	@Autowired
	private WsMemberSearchLogService wsMemberSearchLogService;    
	
	@Autowired
	private WsMemberCollectLogService wsMemberCollectLogService;  
	
	/**
	 * 商品详情接口
	 */
	@RequestMapping(value = "getProdDetail")
	@ResponseBody
	@CrossOrigin
	public Map getProdDetail(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 查询产品信息
			 */
			WsProduct wsProduct = wsProductService.get(id);
			wsProduct.setClickNum(wsProduct.getClickNum()+1);
			wsProductService.saveProduct(wsProduct);
			/**
			 * 记录用户统计
			 */
			WsMemberVisitLog wsMemberVisitLog=new WsMemberVisitLog();
			wsMemberVisitLog.setWsMember(member);
			wsMemberVisitLog.setWsProduct(wsProduct);
			List<WsMemberVisitLog> wsMemberVisitLogList=wsMemberVisitLogService.findList(wsMemberVisitLog);
			if(wsMemberVisitLogList!=null && wsMemberVisitLogList.size()>0){
				wsMemberVisitLog=wsMemberVisitLogList.get(0);
				wsMemberVisitLog.setVisitNum(wsMemberVisitLog.getVisitNum()+1);
				wsMemberVisitLog.setLastVisitDate(new Date());
				wsMemberVisitLogService.save(wsMemberVisitLog);
			}else{
				wsMemberVisitLog.setVisitNum(1);
				wsMemberVisitLog.setLastVisitDate(new Date());
				wsMemberVisitLogService.save(wsMemberVisitLog);
			}
			//查询产品副图（轮播图）
			wsProduct.setProdImageList(wsProductService.getProdImageList(wsProduct.getProdImage(), wsProduct.getProdImages()));
			/**
			 * 查询产品非销售属性,多选的要进行拼接，方面页面展示
			 */
			WsProdSkuAttr wsProdSkuAttr=new WsProdSkuAttr();
			wsProdSkuAttr.setProdId(wsProduct.getId());
			//所有的非销售属性值
			List<WsProdSkuAttr> WsProdSkuAttrAllList=wsProdSkuAttrService.findList(wsProdSkuAttr);
			//所有的产品属性
			List<WsProdSkuAttr> WsProdSkuAttrubteIdList=wsProdSkuAttrService.findAttrbuteIdList(wsProdSkuAttr);
			List<WsProdSkuAttr> WsProdSkuBaseAttrList=new ArrayList<WsProdSkuAttr>();
			//每条属性对应一条属性值，拼接多选的属性
			for (WsProdSkuAttr skuAttrAttrubte:WsProdSkuAttrubteIdList) {
				WsProdSkuAttr skuAttr=new WsProdSkuAttr();
				for (WsProdSkuAttr skuAttrAll:WsProdSkuAttrAllList) {
					if(skuAttrAttrubte.getAttrbuteId().equals(skuAttrAll.getAttrbuteId())){
						skuAttr.setAttrbuteName(skuAttrAll.getAttrbuteName());
						if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValueName())){
							skuAttr.setAttrbuteValueName(skuAttr.getAttrbuteValueName()+skuAttrAll.getAttrbuteValueName()+",");
						}else{
							skuAttr.setAttrbuteValueName(skuAttrAll.getAttrbuteValueName()+",");
						}
					}
				}
				if(StringUtils.isNotEmpty(skuAttr.getAttrbuteValueName())){
					skuAttr.setAttrbuteValueName(skuAttr.getAttrbuteValueName().substring(0, skuAttr.getAttrbuteValueName().length()-1));
				}
				WsProdSkuBaseAttrList.add(skuAttr);
			}
			//查询产品sku规格方便用户进行选择
			WsProdSku wsProdSku=new WsProdSku();
			wsProdSku.setWsProduct(wsProduct);
			List<WsProdSku> wsProdSkuList=wsProdSkuService.findList(wsProdSku);
			/**
			 * 查询产品的评价
			 */
			WsConsulation wsConsulation=new WsConsulation();
			wsConsulation.setProductId(wsProduct.getId());
			List<WsConsulation> wsConsulationList = wsConsulationService.findList(wsConsulation);
			/**
			 * 查询用户收藏
			 */
			WsMemberCollectLog wsMemberCollectLog=new WsMemberCollectLog();
			wsMemberCollectLog.setWsProduct(wsProduct);
			List<WsMemberCollectLog> wsMemberCollectLogList=wsMemberCollectLogService.findList(wsMemberCollectLog);
			/**
			 * 查询自定义分享信息
			 */
			String title="";
			String desc="";
			if(StringUtils.isNotEmpty(member.getNickname())){
				title=sysParamService.getByParamCode(WsConstant.SHARE_TITLE).getParamValue().replaceAll("nickname", member.getNickname());
				desc=sysParamService.getByParamCode(WsConstant.SHARE_DESC).getParamValue().replaceAll("nickname", member.getNickname());
			}else{
				title=sysParamService.getByParamCode(WsConstant.SHARE_TITLE).getParamValue().replaceAll("nickname", "");
				desc=sysParamService.getByParamCode(WsConstant.SHARE_DESC).getParamValue().replaceAll("nickname", "");
			}
			String imgUrl=sysParamService.getByParamCode(WsConstant.IMGURL).getParamValue();
			String link= Global.getWxDoMain()+"/productDetails.html?ruid="+member.getId()+"&id="+wsProduct.getId();
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("member",member);
			data.put("wsProduct",wsProduct);
			data.put("WsProdSkuBaseAttrList",WsProdSkuBaseAttrList);
			data.put("wsProdSkuList",wsProdSkuList);
			data.put("wsConsulationList",wsConsulationList);
			data.put("wsConsulationNum",wsConsulationList.size());
			data.put("wsMemberCollectLogNum",wsMemberCollectLogList.size());
			data.put("skuId",wsProdSkuList.get(0).getId());
			data.put("title",title);
			data.put("desc",desc);
			data.put("imgUrl",imgUrl);
			data.put("link",link);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("prod/getProdDetail",e);
		}
		return data;
	}

	/**
	 * 根据分类查询商品列表
	 */
	@RequestMapping(value = "getProdListByCat")
	@ResponseBody
	@CrossOrigin
	public Map getProdListByCat(String prodCategoryId,String prodOrderBy,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 查询分类信息
			 */
			WsProdCategory wsProdCategory=wsProdCategoryService.get(prodCategoryId);
			/**
			 * 获取产品列表，并根据前端选择进行排序
			 */
			WsProduct wsProduct = new WsProduct();
			wsProduct.setOnGoodState(WsConstant.YES);
			Page page=new Page();
			page.setOrderBy(prodOrderBy);
			wsProduct.setPage(page);
			wsProduct.setProdCategoryId(prodCategoryId);
			List<WsProduct> wsProductList=wsProductService.findList(wsProduct);
			for(WsProduct product:wsProductList){
				product.setProdImage(UrlUtils.getNetUrl(product.getProdImage()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsProductList",wsProductList);
			data.put("wsProdCategory",wsProdCategory);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("prod/getProdListByCat",e);
		}
		return data;
	}
	
	/**
	 * 根据品牌查询商品列表
	 */
	@RequestMapping(value = "getProdListByBrand")
	@ResponseBody
	@CrossOrigin
	public Map getProdListByBrand(String brandId,String prodOrderBy,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 查询品牌信息
			 */
			WsBrand wsBrand=wsBrandService.get(brandId);
			/**
			 * 获取产品列表，并根据前端选择进行排序
			 */
			WsProduct wsProduct = new WsProduct();
			wsProduct.setOnGoodState(WsConstant.YES);
			Page page=new Page();
			page.setOrderBy(prodOrderBy);
			wsProduct.setPage(page);
			wsProduct.setBrandId(brandId);
			List<WsProduct> wsProductList=wsProductService.findList(wsProduct);
			for(WsProduct product:wsProductList){
				product.setProdImage(UrlUtils.getNetUrl(product.getProdImage()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsProductList",wsProductList);
			data.put("wsBrand",wsBrand);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("prod/getProdListByBrand",e);
		}
		return data;
	}
	
	/**
	 * 根据优惠券查询可使用的商品列表
	 */
	@RequestMapping(value = "getProdListByCoupon")
	@ResponseBody
	@CrossOrigin
	public Map getProdListByCoupon(String couponId,String prodOrderBy,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			if(StringUtils.isEmpty(couponId)){
				data.put("ret",InterConstant.RET_SUCCESS);
			}
			//查询优惠券信息
			WsActivityCoupon coupon=wsActivityCouponService.get(couponId);
			/**
			 * 如果优惠券是指定的商品，则查询指定的商品
			 */
			List<WsProduct> wsProductList=new ArrayList<WsProduct>();
			if(coupon.getProdType().equals(WsConstant.COUPON_PROD_APPOINT)){
				WsActivityCouponProd wsActivityCouponProd=new WsActivityCouponProd();
				wsActivityCouponProd.setWsActivityCoupon(coupon);
				Page page=new Page();
				page.setOrderBy(prodOrderBy);
				wsActivityCouponProd.setPage(page);
				List<WsActivityCouponProd> wsActivityCouponProdList=wsActivityCouponProdService.findList(wsActivityCouponProd);
				for (WsActivityCouponProd couponProd:wsActivityCouponProdList) {
					WsProduct wsProduct=wsProductService.get(couponProd.getProdId());
					wsProductList.add(wsProduct);
				}
			}else{
				/**
				 * 如果优惠券是全场商品，则查询全场商品
				 */
				WsProduct wsProduct=new WsProduct();
				wsProduct.setOnGoodState(WsConstant.VALID);
				Page page=new Page();
				page.setOrderBy(prodOrderBy);
				wsProduct.setPage(page);
				wsProductList=wsProductService.findList(wsProduct);
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsProductList",wsProductList);
			data.put("coupon",coupon);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("prod/getProdListByCoupon",e);
		}
		return data;
	}
	
	/**
	 * 根据品牌查询商品列表
	 */
	@RequestMapping(value = "getProdListBySearch")
	@ResponseBody
	@CrossOrigin
	public Map getProdListBySearch(String lable,String prodOrderBy,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 获取未读消息数量
			 */
			WsMessageRecord wsMessageRecord=new WsMessageRecord();
			wsMessageRecord.setMemberId(member.getId());
			wsMessageRecord.setReadFlag(InterConstant.NO);
			int messagenum=wsMessageRecordService.findCount(wsMessageRecord);
			/**
			 * 新增用户搜索记录
			 */
			if(StringUtils.isNotEmpty(lable)){
				WsMemberSearchLog wsMemberSearchLog=new WsMemberSearchLog();
				wsMemberSearchLog.setWsMember(member);
				wsMemberSearchLog.setSearchLable(lable);
				List<WsMemberSearchLog> wsMemberSearchLogList=wsMemberSearchLogService.findList(wsMemberSearchLog);
				if(wsMemberSearchLogList!=null &&wsMemberSearchLogList.size()>0){
					wsMemberSearchLog=wsMemberSearchLogList.get(0);
					wsMemberSearchLog.setSearchNum(wsMemberSearchLog.getSearchNum()+1);
					wsMemberSearchLogService.save(wsMemberSearchLog);
				}else{
					wsMemberSearchLog=new WsMemberSearchLog();
					wsMemberSearchLog.setSearchLable(lable);
					wsMemberSearchLog.setSearchNum(1);
					wsMemberSearchLog.setLastSearchDate(new Date());
					wsMemberSearchLog.setWsMember(member);
					wsMemberSearchLogService.save(wsMemberSearchLog);
				}
			}
			/**
			 * 获取产品列表，并根据前端选择进行排序
			 */
			WsProduct wsProduct = new WsProduct();
			wsProduct.setOnGoodState(WsConstant.VALID);
			Page page=new Page();
			page.setOrderBy(prodOrderBy);
			wsProduct.setPage(page);
			wsProduct.setTitle(lable);
			List<WsProduct> wsProductList=wsProductService.findList(wsProduct);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsProductList",wsProductList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("prod/getProdListBySearch",e);
		}
		return data;
	}

}