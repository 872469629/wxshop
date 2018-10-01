package com.thinkgem.jeesite.modules.prod.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.entity.WsExFaretemplate;
import com.thinkgem.jeesite.modules.config.service.WsExFaretemplateService;
import com.thinkgem.jeesite.modules.prod.entity.WsProdAttribute;
import com.thinkgem.jeesite.modules.prod.entity.WsProdCategory;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsProdAttributeService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 产品信息Controller
 * @author 大胖老师
 * @version 2017-10-01
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/wsProduct")
public class WsProductController extends BaseController {

	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsProdAttributeService wsProdAttributeService;
	
	@Autowired
	private WsExFaretemplateService wsExFaretemplateService;
	
	
	@ModelAttribute
	public WsProduct get(@RequestParam(required=false) String id) {
		WsProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsProductService.get(id);
		}
		if (entity == null){
			entity = new WsProduct();
		}
		return entity;
	}
	
	@RequiresPermissions("prod:wsProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsProduct wsProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsProduct> page = wsProductService.findPage(new Page<WsProduct>(request, response), wsProduct); 
		model.addAttribute("page", page);
		return "modules/prod/wsProductList";
	}

	@RequiresPermissions("prod:wsProduct:view")
	@RequestMapping(value = "form")
	public String form(WsProduct wsProduct, Model model) {
		model.addAttribute("wsProduct", wsProduct);
		return "modules/prod/wsProductCatForm";
	}
	
	@RequiresPermissions("prod:wsProduct:view")
	@RequestMapping(value = "nextForm")
	public String nextForm(WsProduct wsProduct, Model model) {
		/**
		 * 查询分类对应的属性数据
		 */
		WsProdCategory wsProdCategory=new WsProdCategory();
		wsProdCategory.setId(wsProduct.getProdCategoryId());
		WsProdAttribute wsProdAttribute=new WsProdAttribute();
		wsProdAttribute.setProdCategoryId(wsProdCategory);
		//查询分类对应的基本属性和属性值
		wsProdAttribute.setAttrType(WsConstant.ATTR_TYPE_BASE);
		List<WsProdAttribute> wsProdAttributeBase = wsProdAttributeService.findVauleList(wsProdAttribute,wsProduct.getId());
		//查询分类对应的销售属性和属性值
		wsProdAttribute.setAttrType(WsConstant.ATTR_TYPE_SEL);
		List<WsProdAttribute> wsProdAttributeSel = wsProdAttributeService.findVauleList(wsProdAttribute,wsProduct.getId());
		model.addAttribute("wsProduct", wsProduct);
		model.addAttribute("wsProdAttributeBase", wsProdAttributeBase);
		model.addAttribute("wsProdAttributeSel", wsProdAttributeSel);
		if(wsProdAttributeSel==null || wsProdAttributeSel.size()==0){
			model.addAttribute("message", "该分类下没有销售属性，请先增加销售属性再添加商品！");
			return form(wsProduct, model);
		}
		//查询快递模板
		List<WsExFaretemplate> wsExFaretemplates=wsExFaretemplateService.findList(new WsExFaretemplate());
		model.addAttribute("wsExFaretemplates", wsExFaretemplates);
		return "modules/prod/wsProductForm";
	}
	
	
	@RequiresPermissions("prod:wsProduct:view")
	@RequestMapping(value = "nextFormSku")
	public String nextFormSku(WsProduct wsProduct, Model model) {
		/**
		 * 生成sku,并设置到产品信息里面
		 */
		wsProduct.setWsProdSkuList(wsProductService.findWsProdSkuList(wsProduct));
		/**
		 * 查询分类对应的属性数据
		 */
		WsProdCategory wsProdCategory=new WsProdCategory();
		wsProdCategory.setId(wsProduct.getProdCategoryId());
		WsProdAttribute wsProdAttribute=new WsProdAttribute();
		wsProdAttribute.setProdCategoryId(wsProdCategory);
		//查询分类对应的基本属性
		wsProdAttribute.setAttrType(WsConstant.ATTR_TYPE_BASE);
		//查询分类对应的销售属性
		List<WsProdAttribute> wsProdAttributeBase = wsProdAttributeService.findSkuVauleList(wsProdAttribute,wsProduct.getId(),wsProduct.getWsProdSkuAttrBaseList());
		wsProdAttribute.setAttrType(WsConstant.ATTR_TYPE_SEL);
		List<WsProdAttribute> wsProdAttributeSel = wsProdAttributeService.findSkuVauleList(wsProdAttribute,wsProduct.getId(),wsProduct.getWsProdSkuAttrSelList());
		model.addAttribute("wsProduct", wsProduct);
		model.addAttribute("wsProdAttributeBase", wsProdAttributeBase);
		model.addAttribute("wsProdAttributeSel", wsProdAttributeSel);
		return "modules/prod/wsProductFormSku";
	}

	
	
	@RequiresPermissions("prod:wsProduct:edit")
	@RequestMapping(value = "save")
	public String save(WsProduct wsProduct, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, wsProduct)){
			return form(wsProduct, model);
		}
		wsProduct.setProdContent(StringEscapeUtils.unescapeHtml4(wsProduct.getProdContent()));
		wsProductService.save(wsProduct);
		addMessage(redirectAttributes, "填写宝贝价格和库存");
		return list(wsProduct, request, response, model);
	}
	
	@RequiresPermissions("prod:wsProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(WsProduct wsProduct, RedirectAttributes redirectAttributes) {
		wsProductService.delete(wsProduct);
		addMessage(redirectAttributes, "删除产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProduct/?repage";
	}
	
	/**
	 * 通过编号获取活动商品信息
	 */
	@RequiresPermissions("cms:article:view")
	@ResponseBody
	@RequestMapping(value = "findByIds")
	public String findByIds(String ids) {
		List<Object[]> list = wsProductService.findByIds(ids);
		return JsonMapper.nonDefaultMapper().toJson(list);
	}
	
	/**
	 * 获取产品弹出框列表
	 */
	@RequestMapping(value = "prodSelectList")
	public String prodSelectList(WsProduct wsProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsProduct> page = wsProductService.findPage(new Page<WsProduct>(request, response), wsProduct); 
		model.addAttribute("page", page);
        return "modules/prod/wsProdSelectList";
	}
	
	@RequestMapping(value = "prodAdSelectList")
	public String prodAdSelectList(WsProduct wsProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsProduct> page = wsProductService.findPage(new Page<WsProduct>(request, response), wsProduct); 
		model.addAttribute("page", page);
        return "modules/prod/wsProdAdList";
	}
	
	/**
	 *上架商品
	 */
	@RequiresPermissions("prod:wsProduct:edit")
	@RequestMapping(value = "upProduct")
	public String upProduct(WsProduct wsProduct, RedirectAttributes redirectAttributes) {
		wsProduct=wsProductService.get(wsProduct.getId());
		wsProduct.setOnGoodState(WsConstant.VALID);
		wsProduct.setOnGoodTime(new Date());
		wsProductService.saveProduct(wsProduct);
		addMessage(redirectAttributes, "上架产品成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProduct/?repage";
	}
	
	/**
	 * 下架商品
	 */
	@RequiresPermissions("prod:wsProduct:edit")
	@RequestMapping(value = "downProduct")
	public String downProduct(WsProduct wsProduct, RedirectAttributes redirectAttributes) {
		wsProduct=wsProductService.get(wsProduct.getId());
		wsProduct.setOnGoodState(WsConstant.NOT_VALID);
		wsProduct.setOnGoodTime(null);
		wsProductService.saveProduct(wsProduct);
		addMessage(redirectAttributes, "下架产品成功");
		return "redirect:"+Global.getAdminPath()+"/prod/wsProduct/?repage";
	}
}