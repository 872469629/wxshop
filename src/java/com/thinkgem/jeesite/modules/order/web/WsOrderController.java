package com.thinkgem.jeesite.modules.order.web;

import java.util.Date;

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
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.entity.WsProdSku;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 订单Controller
 * @author water
 * @version 2017-10-07
 */
@Controller
@RequestMapping(value = "${adminPath}/order/wsOrder")
public class WsOrderController extends BaseController {

	@Autowired
	private WsOrderService wsOrderService;
	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@ModelAttribute
	public WsOrder get(@RequestParam(required=false) String id) {
		WsOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsOrderService.get(id);
		}
		if (entity == null){
			entity = new WsOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("order:wsOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsOrder wsOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsOrder> page = wsOrderService.findPage(new Page<WsOrder>(request, response), wsOrder); 
		model.addAttribute("page", page);
		return "modules/order/wsOrderList";
	}

	@RequiresPermissions("order:wsOrder:view")
	@RequestMapping(value = "form")
	public String form(WsOrder wsOrder, Model model) {
		for (WsOrderItem item : wsOrder.getWsOrderItemList()) {
			WsProdSku wsProdSku = wsProdSkuService.get(item.getSkuId());
			item.setWsProdSku(wsProdSku);
		}
		model.addAttribute("wsOrder", wsOrder);
		return "modules/order/wsOrderForm";
	}
	
	@RequiresPermissions("order:wsOrder:view")
	@RequestMapping(value = "formSend")
	public String formSend(WsOrder wsOrder, Model model) {
		for (WsOrderItem item : wsOrder.getWsOrderItemList()) {
			WsProdSku wsProdSku = wsProdSkuService.get(item.getSkuId());
			item.setWsProdSku(wsProdSku);
		}
		model.addAttribute("wsOrder", wsOrder);
		return "modules/order/wsOrderFormSend";
	}
	
	
	
	@RequiresPermissions("order:wsOrder:view")
	@RequestMapping(value = "formRemark")
	public String formRemark(WsOrder wsOrder, Model model) {
		model.addAttribute("wsOrder", wsOrder);
		return "modules/order/wsOrderFormRemark";
	}
	
	
	
	

	@RequiresPermissions("order:wsOrder:edit")
	@RequestMapping(value = "save")
	public String save(WsOrder wsOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsOrder)){
			return form(wsOrder, model);
		}
		wsOrderService.save(wsOrder);
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/wsOrder/?repage";
	}
	
	@RequiresPermissions("order:wsOrder:edit")
	@RequestMapping(value = "saveSend")
	public String saveSend(WsOrder wsOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsOrder)){
			return form(wsOrder, model);
		}
		wsOrder.setSendTime(new Date());
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_RECEVIED);
		wsOrderService.saveSend(wsOrder);
		addMessage(redirectAttributes, "保存订单成功");
		return "error/405";
	}
	
	
	@RequiresPermissions("order:wsOrder:edit")
	@RequestMapping(value = "saveRemark")
	public String saveRemark(WsOrder wsOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsOrder)){
			return form(wsOrder, model);
		}
		wsOrderService.saveRemark(wsOrder);
		addMessage(redirectAttributes, "保存备注成功");
		return "error/405";
	}
	
	
	@RequiresPermissions("order:wsOrder:edit")
	@RequestMapping(value = "closeOrder")
	public String closeOrder(WsOrder wsOrder, RedirectAttributes redirectAttributes) {
		wsOrder.setOrderState(WsConstant.ORDER_STATE_WAITE_CANCEL);
		wsOrderService.closeOrder(wsOrder);
		addMessage(redirectAttributes, "关闭订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/wsOrder/?repage";
	}
	
	
	@RequiresPermissions("order:wsOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(WsOrder wsOrder, RedirectAttributes redirectAttributes) {
		wsOrderService.delete(wsOrder);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/wsOrder/?repage";
	}

}