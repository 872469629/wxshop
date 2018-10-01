package com.thinkgem.jeesite.modules.inter.web;

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

import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.service.WsAddressService;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;

/**
 * 收货地址接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/address")
public class AddressController extends BaseController {
	
	@Autowired
	private WsAddressService wsAddressService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "list")
	@ResponseBody
	@CrossOrigin
	public Map list(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 查询用户地址列表
			 */
			WsAddress wsAddress=new WsAddress();
			wsAddress.setWsMember(member);
			List<WsAddress> wsAddressList= wsAddressService.findList(wsAddress);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsAddressList",wsAddressList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("address/list",e);
		}
		return data;
	}
	
	
	/**
	 * 查询地址详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail")
	@ResponseBody
	@CrossOrigin
	public Map detail(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 查询用户地址列表
			 */
			if(StringUtils.isNotEmpty(id)){
				WsAddress wsAddress=wsAddressService.get(id);
				data.put("wsAddress",wsAddress);
			}
			data.put("citys",WsUtils.findAreaList());
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("address/detail",e);
		}
		return data;
	}
	
	/**
	 * 删除地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@CrossOrigin
	public Map delete(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			List<WsAddress> wsAddressList = wsAddressService.userDelete(id, member);
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("wsAddressList",wsAddressList);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("address/delete",e);
		}
		return data;
	}
	
	/**
	 * 保存地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	@CrossOrigin
	public Map save(WsAddress wsAddress,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			/**
			 * 查询用户地址列表
			 */
			wsAddressService.userSave(wsAddress, member);
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("address/save",e);
		}
		return data;
	}
	
	/**
	 * 保存默认地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveDefaultAddr")
	@ResponseBody
	@CrossOrigin
	public Map saveDefaultAddr(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			/**
			 * 获取用户信息
			 */
			WsMember member=WsUtils.getMember(request, response);
			wsAddressService.userSaveDefault(id, member);
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("address/saveDefaultAddr",e);
		}
		return data;
	}
	
	
	/**
	 * 根据省份查询地市
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findCityList")
	@ResponseBody
	@CrossOrigin
	public Map findCityList(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map data=new HashMap();
		try{
			Area area=areaService.get(id);
			List<Area> cityList=areaService.findByParentId(area);
			data.put("sheng",area);
			data.put("cityList",cityList);
			data.put("ret",InterConstant.RET_SUCCESS);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("address/saveDefaultAddr",e);
		}
		return data;
	}
	

}