package com.thinkgem.jeesite.modules.config.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.entity.WsActivity;
import com.thinkgem.jeesite.modules.config.entity.WsAd;
import com.thinkgem.jeesite.modules.config.service.WsActivityService;
import com.thinkgem.jeesite.modules.config.service.WsAdService;
import com.thinkgem.jeesite.modules.prod.entity.WsBrand;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsBrandService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 图片管理Controller
 * @author water
 * @version 2017-10-01
 */
@Controller
@RequestMapping(value = "${adminPath}/config/wsAd")
public class WsAdController extends BaseController {

	@Autowired
	private WsAdService wsAdService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsActivityService wsActivityService;
		
	@Autowired
	private WsBrandService wsBrandService;
	

	
	@ModelAttribute
	public WsAd get(@RequestParam(required=false) String id) {
		WsAd entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsAdService.get(id);
		}
		if (entity == null){
			entity = new WsAd();
		}
		return entity;
	}
	
	@RequiresPermissions("config:wsAd:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsAd wsAd, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsAd> pageContion = new Page<WsAd>(request, response);
		pageContion.setOrderBy("img_type,update_date");
		Page<WsAd> page = wsAdService.findPage(pageContion, wsAd); 
		model.addAttribute("page", page);
		return "modules/config/wsAdList";
	}

	@RequiresPermissions("config:wsAd:view")
	@RequestMapping(value = "form")
	public String form(WsAd wsAd, Model model) {
		model.addAttribute("wsAd", wsAd);
		return "modules/config/wsAdForm";
	}

	@RequiresPermissions("config:wsAd:edit")
	@RequestMapping(value = "save")
	public String save(WsAd wsAd, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsAd)){
			return form(wsAd, model);
		}
		wsAdService.save(wsAd);
		addMessage(redirectAttributes, "保存图片管理成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsAd/?repage";
	}
	
	@RequiresPermissions("config:wsAd:edit")
	@RequestMapping(value = "delete")
	public String delete(WsAd wsAd, RedirectAttributes redirectAttributes) {
		wsAdService.delete(wsAd);
		addMessage(redirectAttributes, "删除图片管理成功");
		return "redirect:"+Global.getAdminPath()+"/config/wsAd/?repage";
	}
	
	
	@RequiresPermissions("ws:wxSubscribe:edit")
	@RequestMapping(value = "selectImgUrl")
	public String selectImgUrl(String urlType ,String title,String cnname, HttpServletRequest request, HttpServletResponse response, Model model) {
		//选择产品url
		if(urlType.equals("1")){
			WsProduct wsProduct=new WsProduct();
			wsProduct.setOnGoodState(WsConstant.YES);
			if(StringUtils.isNotEmpty(title)){
				wsProduct.setTitle(title);
			}
			Page<WsProduct> page = wsProductService.findPage(new Page<WsProduct>(request, response), wsProduct); 
			model.addAttribute("page", page);
			model.addAttribute("wsProduct", wsProduct);
			return "modules/prod/wsProdAdList";
		}
		//选择活动url
		if(urlType.equals("2")){
			WsActivity wsActivity=new WsActivity();
			if(StringUtils.isNotEmpty(title)){
				wsActivity.setTitle(title);
			}
			Page<WsActivity> page = wsActivityService.findPage(new Page<WsActivity>(request, response), wsActivity); 
			model.addAttribute("page", page);
			model.addAttribute("wsActivity", wsActivity);
			return "modules/config/wsActivitySelectList";
		}
		//选择品牌url
		if(urlType.equals("3")){
			WsBrand wsBrand=new WsBrand();
			if(StringUtils.isNotEmpty(cnname)){
				wsBrand.setCnname(cnname);
			}
			Page<WsBrand> page = wsBrandService.findPage(new Page<WsBrand>(request, response), wsBrand); 
			model.addAttribute("page", page);
			model.addAttribute("wsBrand", wsBrand);
			return "modules/prod/wsBrandSelectList";
		}
		return "modules/prod/wsProdSelectList";
	}

}