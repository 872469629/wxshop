package com.thinkgem.jeesite.modules.returnback.web;

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
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.returnback.entity.WsReturn;
import com.thinkgem.jeesite.modules.returnback.service.WsReturnService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 退货管理Controller
 * @author 大胖老师
 * @version 2017-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/returnback/wsReturn")
public class WsReturnController extends BaseController {

	@Autowired
	private WsReturnService wsReturnService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@ModelAttribute
	public WsReturn get(@RequestParam(required=false) String id) {
		WsReturn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsReturnService.get(id);
		}
		if (entity == null){
			entity = new WsReturn();
		}
		return entity;
	}
	
	@RequiresPermissions("returnback:wsReturn:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsReturn wsReturn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsReturn> page = wsReturnService.findPage(new Page<WsReturn>(request, response), wsReturn); 
		model.addAttribute("page", page);
		return "modules/returnback/wsReturnList";
	}

	@RequiresPermissions("returnback:wsReturn:view")
	@RequestMapping(value = "form")
	public String form(WsReturn wsReturn, Model model) {
		model.addAttribute("wsReturn", wsReturn);
		return "modules/returnback/wsReturnForm";
	}

	@RequiresPermissions("returnback:wsReturn:edit")
	@RequestMapping(value = "save")
	public String save(WsReturn wsReturn,String returnState,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsReturn)){
			return form(wsReturn, model);
		}
		try{
			/**
			 * 拒绝退款
			 */
			if(returnState.equals(WsConstant.RETURN_ORDER_STATE_REFUND)){
				wsReturn.setState(WsConstant.RETURN_ORDER_STATE_REFUND);
				wsReturnService.saveWsReturn(wsReturn);
				//将购物定单中的退款信息清空
				WsOrder wsOrder=wsOrderService.get(wsReturn.getOrderSn());
				wsOrder.setWsReturn(new WsReturn());
				wsOrderService.save(wsOrder);
			}
			/**
			 * 微信退款成功
			 */
			if(returnState.equals(WsConstant.RETURN_ORDER_STATE_WX_FINISH)){
				wsReturn.setState(WsConstant.RETURN_ORDER_STATE_WX_FINISH);
				wsReturnService.saveWsReturnOrder(wsReturn);
			}
			/**
			 * 人工退款成功
			 */
			if(returnState.equals(WsConstant.RETURN_ORDER_STATE_MAN_FINISH)){
				wsReturn.setState(WsConstant.RETURN_ORDER_STATE_MAN_FINISH);
				wsReturnService.saveWsReturn(wsReturn);
			}
		}catch(Exception e){
			addMessage(model, "操作失败，请联系系统管理员或者切换人工退款");
			return form(wsReturn, model);
		}
		addMessage(redirectAttributes, "保存退货管理成功");
		return "redirect:"+Global.getAdminPath()+"/returnback/wsReturn/?repage";
	}
	
	@RequiresPermissions("returnback:wsReturn:edit")
	@RequestMapping(value = "delete")
	public String delete(WsReturn wsReturn, RedirectAttributes redirectAttributes) {
		wsReturnService.delete(wsReturn);
		addMessage(redirectAttributes, "删除退货管理成功");
		return "redirect:"+Global.getAdminPath()+"/returnback/wsReturn/?repage";
	}

}