package com.thinkgem.jeesite.modules.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ws.dao.WxMenuDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMenu;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.core.req.model.menu.WeixinButton;
import com.thinkgem.jeesite.modules.wx.wxmenu.JwMenuAPI;

/**
 * 微信菜单Service
 * @author 大胖老师
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class WxMenuService extends TreeService<WxMenuDao, WxMenu> {

	public WxMenu get(String id) {
		return super.get(id);
	}
	
	public List<WxMenu> findList(WxMenu wxMenu) {
		if (StringUtils.isNotBlank(wxMenu.getParentIds())){
			wxMenu.setParentIds(","+wxMenu.getParentIds()+",");
		}
		return super.findList(wxMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMenu wxMenu) {
		super.save(wxMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMenu wxMenu) {
		super.delete(wxMenu);
	}
	
	@Transactional(readOnly = false)
	public void sysMenuButton(WxMenu wxMenu) throws Exception {
		List<WeixinButton> buttonList=new ArrayList<WeixinButton>();
		wxMenu.setParent(new WxMenu("0"));
		List<WxMenu> wxMenuList=findList(wxMenu);//获取一级菜单
		if(wxMenuList!=null && wxMenuList.size()>3){
			throw new Exception("一级菜单最多只允许创建三个");
		}
		for (WxMenu menu:wxMenuList) {
			WeixinButton button=new WeixinButton();
			button.setName(menu.getName());
			if(StringUtils.isNotEmpty(menu.getUrl())){
				button.setUrl(menu.getUrl());
			}			
			button.setType(menu.getMenuType());
			button.setKey(menu.getMenuKey());
			/**
			 * 获取二级菜单，并加载到菜单配置
			 */
			List<WeixinButton> subButtonList=new ArrayList<WeixinButton>();
			wxMenu.setParent(new WxMenu(menu.getId()));
			List<WxMenu> wxMenuSubList=findList(wxMenu);
			for (WxMenu menuSub:wxMenuSubList) {
				WeixinButton buttonSub=new WeixinButton();
				buttonSub.setName(menuSub.getName());
				if(StringUtils.isNotEmpty(menuSub.getUrl())){
					buttonSub.setUrl(menuSub.getUrl());
//					buttonSub.setUrl(JwMenuAPI.urlEncode(menuSub.getUrl(),WsUtils.getAccount().getAccountAppid()));
				}		
				buttonSub.setType(menuSub.getMenuType());
				buttonSub.setKey(menuSub.getMenuKey());
				subButtonList.add(buttonSub);
			}
			button.setSub_button(subButtonList);
			buttonList.add(button);
		}
		if(buttonList!=null && buttonList.size()>5){
			throw new Exception("二级菜单最多只允许创建五个");
		}
		String message=JwMenuAPI.createMenu(WsUtils.getAccessToken(), buttonList);
		System.out.println(message);
	}
	
}