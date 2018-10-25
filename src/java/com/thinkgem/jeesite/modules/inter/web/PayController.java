
package com.thinkgem.jeesite.modules.inter.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.service.WsExFaretemplateService;
import com.thinkgem.jeesite.modules.config.service.WsMrankService;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.inter.service.PayService;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsAddressService;
import com.thinkgem.jeesite.modules.member.service.WsMemberCouponService;
import com.thinkgem.jeesite.modules.member.service.WsMemberRewardLogService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.order.service.WsOrderItemService;
import com.thinkgem.jeesite.modules.order.service.WsOrderService;
import com.thinkgem.jeesite.modules.prod.service.WsProdSkuService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.pay.WXPayUtil;

/**
 * 支付功能接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/pay")
public class PayController extends BaseController {
	
	@Autowired
	private WsProductService wsProductService;
	
	@Autowired
	private WsProdSkuService wsProdSkuService;
	
	@Autowired
	private WsAddressService wsAddressService;
	
	@Autowired
	private WsOrderItemService wsOrderItemService;
	
	@Autowired
	private WsOrderService wsOrderService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsMemberCouponService wsMemberCouponService;
	
	@Autowired
	private WsMemberRewardLogService wsMemberRewardLogService;
	
	@Autowired
	private WsExFaretemplateService wsExFaretemplateService;
	
	@Autowired
	private WsMrankService wsMrankService;
	
	@Autowired
	private PayService payService;
	
	/**
	 * 付款接口，通过商品和购物车下单付款调用此接口
	 */
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(@RequestBody List<WsOrderItem> wsOrderItems,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
	        String ip=request.getLocalAddr();
	        String notify=request.getRequestURL()+"/payNotify";
	        data=payService.orderPay(member, wsOrderItems,ip,notify);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("pay",e);
		}
		return data;
	}
	
	/**
	 * 付款接口，通过待付款按钮进入付款页面，调用此接口
	 */
	@RequestMapping(value = "payByOrderId")
	@ResponseBody
	@CrossOrigin
	public Map payByOrderId(String orderId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
	        String ip=request.getLocalAddr();
	        String notify=request.getRequestURL()+"/payNotify";
			data=payService.orderPayById(orderId, member, ip, notify);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("pay/payByOrderId",e);
		}
		return data;
	}
	
	@RequestMapping(value = "payNotify")
	@ResponseBody
	@CrossOrigin
	public String payNotify(HttpServletRequest request, HttpServletResponse response, Model model) {
		String result;//返回给微信的处理结果  
		try{
	        String inputLine;  
	        String notityXml = "";  
	        request.setCharacterEncoding("UTF-8");  
	        response.setCharacterEncoding("UTF-8");  
	        response.setContentType("text/html;charset=UTF-8");  
	        response.setHeader("Access-Control-Allow-Origin", "*");  
	        //微信给返回的东西  
            while ((inputLine = request.getReader().readLine()) != null) {  
                notityXml += inputLine;  
            }  
            request.getReader().close();  
	        if (StringUtils.isEmpty(notityXml)) {  
	            result = setXml("fail","xml为空");  
	        }  
	        Map map = WXPayUtil.xmlToMap(notityXml); 
	        // 验证签名  
	        if (WXPayUtil.isSignatureValid(map, WsUtils.getAccount().getPayKey())){  
	        	String outTradeNo = (String) map.get("out_trade_no");// 获取商户订单号 
	        	String totalFee = (String) map.get("total_fee");// 获取订单金额
	        	payService.payNotify(outTradeNo,totalFee);
	            result = setXml("SUCCESS", "OK");  
	        } else {  
	            logger.error("签名不一致！");  
	            result = setXml("fail", "签名不一致！");  
	        }		
        }catch(Exception e){
        	logger.error(e.getMessage()); 
	       result = setXml("fail", e.getMessage());  
		}
        return result;    
	}

	 //通过xml 发给微信消息  
    public static String setXml(String return_code, String return_msg) {  
        SortedMap<String, String> parameters = new TreeMap<String, String>();  
        parameters.put("return_code", return_code);  
        parameters.put("return_msg", return_msg);  
        return "<xml><return_code><![CDATA[" + return_code + "]]>" +   
                "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";  
    }
    
    
	/**
	 * 保存用户留言和js前端支付状态
	 */
	@RequestMapping(value = "savePayJs")
	@ResponseBody
	@CrossOrigin
	public Map savePayJs(WsOrder wsOrder,String ruid,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			WsOrder order=wsOrderService.get(wsOrder.getId());
			order.setJsPayState(WsConstant.YES);
			order.setBuysWords(wsOrder.getBuysWords());
			if(StringUtils.isNotEmpty(ruid)){
				order.setRuid(new WsMember(ruid));
			}
			wsOrderService.save(order);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("pay/payByOrderId",e);
		}
		return data;
	}
	
	
	/**
	 * 小程序付款接口，通过商品和购物车下单付款调用此接口
	 */
	@RequestMapping(value = "wcxindex")
	@ResponseBody
	@CrossOrigin
	public Map wcxindex(@RequestBody List<WsOrderItem> wsOrderItems,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
	        String ip=request.getLocalAddr();
	        String notify=request.getRequestURL()+"/payNotify";
			String memberId=wsOrderItems.get(0).getMemberId();
			WsMember member=wsMemberService.get(memberId);
			data=payService.orderPay(member, wsOrderItems, ip, notify);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("pay",e);
		}
		return data;
	}
	
}