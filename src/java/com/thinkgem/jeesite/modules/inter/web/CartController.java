package com.thinkgem.jeesite.modules.inter.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.member.service.WsMessageRecordService;
import com.thinkgem.jeesite.modules.prod.entity.WsCart;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsCartService;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 购物车接口
 * @author 大胖老师
 * @version 2017-11-12
 */
@Controller
@RequestMapping(value = "${wxPath}/cart")
public class CartController extends BaseController {
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;
	
	@Autowired
	private WsCartService wsCartService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> data=new HashMap();
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
			 * 查询购物车产品
			 */
			WsCart wsCart=new WsCart();
			wsCart.setMemberId(member.getId());
			
//			wsCart.setMemberId("9a54940b4ddc4872a1eeb18a5aeb6324");
			List<WsCart> wsCartList=wsCartService.findList(wsCart);
			//图片路径转换
			for(WsCart cart:wsCartList){
				cart.setThumb(UrlUtils.getNetUrl(cart.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("wsCartList",wsCartList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("cart",e);
		}
		return data;
	}
	
	
	@RequestMapping(value = "addCart")
	@ResponseBody
	@CrossOrigin
	public Map addCart(String skuId, Integer quantity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 查询购物车是否已存在该sku，如果已存在，则数量+1，如果未存在，则新增如购物车
			 */
			WsCart wsCart=new WsCart();
			wsCart.setMemberId(member.getId());
			wsCart.setSkuId(skuId);
			List<WsCart> wsCartList=wsCartService.findList(wsCart);
			if(wsCartList!=null && wsCartList.size()>0){
				//如果已存在，则数量+1
				wsCart=wsCartList.get(0);
				//校验库存
				WsProdSku wsProdSku=wsProdSkuService.get(skuId);
				if ((wsCart.getQuantity() + quantity) > wsProdSku.getSurplusQuantity()) {
					data.put("ret",InterConstant.RET_FAILED);
					data.put("msg","库存不足");
					return data;
				}
				
				wsCart.setQuantity(wsCart.getQuantity()+quantity);
				wsCart.setPrice(wsCart.getUnitPrice().multiply(new BigDecimal(wsCart.getQuantity())));
				wsCartService.save(wsCart);
			}else{
				//如果未存在，则新增如购物车
				WsProdSku wsProdSku=wsProdSkuService.get(skuId);
				//校验库存
				if (quantity > wsProdSku.getSurplusQuantity()) {
					data.put("ret",InterConstant.RET_FAILED);
					data.put("msg","库存不足");
					return data;
				}
				WsProduct wsProduct=wsProductService.get(wsProdSku.getWsProduct().getId());
				wsCart.setProductId(wsProduct.getId());
				wsCart.setTitle(wsProduct.getTitle());
				wsCart.setSkuSpec(wsProdSku.getSkuName());
				wsCart.setThumb(wsProduct.getProdImage());
				wsCart.setQuantity(quantity);
				wsCart.setUnitDefaultPrice(wsProdSku.getPrice());
				wsCart.setUnitPrice(wsProdSku.getReallyPrice());
				wsCart.setPrice(wsProdSku.getReallyPrice());
				wsCart.setState(WsConstant.VALID);
				wsCartService.save(wsCart);
			}
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("cart/addCart",e);
		}
		return data;
	}
	
	/**
	 * 删除购物车
	 * @param wsCarts 购物车选中产品
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteCard")
	@ResponseBody
	@CrossOrigin
	public Map deleteCard(@RequestBody List<WsCart> wsCarts,String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			wsCartService.batchDelete(wsCarts);
			/**
			 * 查询购物车产品
			 */
			WsCart wsCart=new WsCart();
			wsCart.setMemberId(member.getId());
			List<WsCart> wsCartList=wsCartService.findList(wsCart);
			for(WsCart cart:wsCartList){
				cart.setThumb(UrlUtils.getNetUrl(cart.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsCartList",wsCartList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("cart/deleteCard",e);
		}
		return data;
	}
	
	
	/**
	 * 删除购物车
	 * @param wsCarts 购物车选中产品
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteCart")
	@ResponseBody
	@CrossOrigin
	public Map deleteCart(String cartId,String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			wsCartService.delete(new WsCart(cartId));
			/**
			 * 查询购物车产品
			 */
			WsCart wsCart=new WsCart();
			wsCart.setMemberId(member.getId());
			List<WsCart> wsCartList=wsCartService.findList(wsCart);
			for(WsCart cart:wsCartList){
				cart.setThumb(UrlUtils.getNetUrl(cart.getThumb()));
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsCartList",wsCartList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("cart/deleteCard",e);
		}
		return data;
	}
	
	/**
	 * 购物车总额
	 */
	@RequestMapping(value = "cartAmount")
	@ResponseBody
	@CrossOrigin
	public Map cartAmount(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			WsCart wsCart=new WsCart();
			wsCart.setMemberId(member.getId());
			List<WsCart> wsCartList=wsCartService.findList(wsCart);
			BigDecimal cartAmount = BigDecimal.ZERO;
			//图片路径转换
			for(WsCart cart:wsCartList){
				cartAmount = cartAmount.add(cart.getPrice());
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("cartAmount",cartAmount);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("cart/deleteCard",e);
		}
		return data;
	}


}