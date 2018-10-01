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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.WxException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.UrlUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.inter.common.InterConstant;
import com.thinkgem.jeesite.modules.member.entity.WsMember;
import com.thinkgem.jeesite.modules.member.entity.WsMessageRecord;
import com.thinkgem.jeesite.modules.member.service.WsMemberService;
import com.thinkgem.jeesite.modules.member.service.WsMessageRecordService;
import com.thinkgem.jeesite.modules.prod.entity.WsProdCategory;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.prod.service.WsProdCategoryService;
import com.thinkgem.jeesite.modules.prod.service.WsProductService;
import com.thinkgem.jeesite.modules.sys.entity.SysParam;
import com.thinkgem.jeesite.modules.sys.service.SysParamService;
import com.thinkgem.jeesite.modules.ws.utils.WsApiConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.wxaccount.JwAccountAPI;
import com.thinkgem.jeesite.modules.wx.wxaccount.model.WxQrcode;

/**
 * 邀请奖励接口
 * @author 大胖老师
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${wxPath}/reward")
public class RewardController extends BaseController {
	
	@Autowired
	private WsMessageRecordService wsMessageRecordService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private WsMemberService wsMemberService;
	
	@Autowired
	private WsProdCategoryService wsProdCategoryService;
	
	@Autowired
	private WsProductService wsProductService;
	
	@RequestMapping(value = {"index", ""})
	@ResponseBody
	@CrossOrigin
	public Map index(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			 * 获取分销二维码
			 */
			if(StringUtils.isEmpty(member.getAwardQrCode())){
				WxQrcode qrCode=JwAccountAPI.createQrcode(WsUtils.getAccessToken(), member.getId(), "QR_LIMIT_STR_SCENE", null);
				member.setAwardQrCode(WsApiConstant.qr_code_url.replace("TICKET", qrCode.getTicket()));
				wsMemberService.save(member);
			}
			/**
			 * 查询自定义分享信息
			 */
			String title=sysParamService.getByParamCode(WsConstant.SHARE_TITLE).getParamValue().replaceAll("nickname", member.getNickname());
			String desc=sysParamService.getByParamCode(WsConstant.SHARE_DESC).getParamValue().replaceAll("nickname", member.getNickname());
			String imgUrl=sysParamService.getByParamCode(WsConstant.IMGURL).getParamValue();
			String link= Global.getWxDoMain()+"/index.html?ruid="+member.getId();
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("member",member);
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
			logger.error("index",e);
		}
		return data;
	}

	@RequestMapping(value = "rule")
	@ResponseBody
	@CrossOrigin
	public Map rule(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			 * 查询关于我们
			 */
			String aboutMsg="";
			SysParam sysParam=new SysParam();
			sysParam.setParamCode(WsConstant.AWARD);
			List<SysParam> sysParamList=sysParamService.findList(sysParam);
			if(sysParamList!=null && sysParamList.size()>0 && StringUtils.isNotEmpty(sysParamList.get(0).getParamValue())){
				aboutMsg=sysParamList.get(0).getParamValue();
			}
			/**
			 * 查询客服电话
			 */
			String tel="";
			sysParam.setParamCode(WsConstant.TEL);
			sysParamList=sysParamService.findList(sysParam);
			if(sysParamList!=null && sysParamList.size()>0 && StringUtils.isNotEmpty(sysParamList.get(0).getParamValue())){
				tel=sysParamList.get(0).getParamValue();
			}
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("aboutMsg",aboutMsg);
			data.put("tel",tel);
		}catch(WxException e){
			data.put("ret",InterConstant.RET_WX);
			data.put("appid",WsUtils.getAccount().getAccountAppid());
		}catch(Exception e){
			data.put("ret",InterConstant.RET_FAILED);
			data.put("msg",e.getMessage());
			logger.error("index",e);
		}
		return data;
	}
	
	
	@RequestMapping(value = "list")
	@ResponseBody
	@CrossOrigin
	public Map list(String prodCategoryId,HttpServletRequest request, HttpServletResponse response, Model model) {
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
			 * 所有二级分类
			 */
			WsProdCategory wsProdCategory=new WsProdCategory();
			List<WsProdCategory> wsProdCategoryList=wsProdCategoryService.findChildList(wsProdCategory);
			/**
			 * 获取所有的商品
			 */
			WsProduct wsProduct=new WsProduct();
			if(StringUtils.isNotEmpty(prodCategoryId)&&!prodCategoryId.equals("null")){
				wsProduct.setProdCategoryId(prodCategoryId);
			}
			Page<WsProduct> page=new Page<WsProduct>();
			page.setOrderBy("defaultRewardMoney desc");
			wsProduct.setOnGoodState(WsConstant.VALID);
			wsProduct.setPage(page);
			List<WsProduct> wsProductList=wsProductService.findList(wsProduct);
			for (WsProduct prod:wsProductList) {
				prod.setProdImage(UrlUtils.getNetUrl(prod.getProdImage()));
			}
			/**
			 * 查询自定义分享信息
			 */
			String title=sysParamService.getByParamCode(WsConstant.SHARE_TITLE).getParamValue().replaceAll("nickname", member.getNickname());
			String desc=sysParamService.getByParamCode(WsConstant.SHARE_DESC).getParamValue().replaceAll("nickname", member.getNickname());
			String imgUrl=sysParamService.getByParamCode(WsConstant.IMGURL).getParamValue();
			String link= Global.getWxDoMain()+"/index.html?ruid="+member.getId();
			data.put("ret",InterConstant.RET_SUCCESS);
			data.put("messagenum",messagenum);
			data.put("member",member);
			data.put("wsProdCategoryList",wsProdCategoryList);
			data.put("wsProductList",wsProductList);
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
			logger.error("index",e);
		}
		return data;
	}

}