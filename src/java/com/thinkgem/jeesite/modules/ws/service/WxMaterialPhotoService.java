package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.dao.WxMaterialPhotoDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialPhoto;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;
import com.thinkgem.jeesite.modules.wx.wxbase.wxmedia.JwMediaAPI;
import com.thinkgem.jeesite.modules.wx.wxbase.wxmedia.model.WxMediaForMaterialResponse;

/**
 * 图片素材Service
 * @author 大胖老师
 * @version 2017-09-28
 */
@Service
@Transactional(readOnly = true)
public class WxMaterialPhotoService extends CrudService<WxMaterialPhotoDao, WxMaterialPhoto> {

	public WxMaterialPhoto get(String id) {
		return super.get(id);
	}
	
	public List<WxMaterialPhoto> findList(WxMaterialPhoto wxMaterialPhoto) {
		return super.findList(wxMaterialPhoto);
	}
	
	public Page<WxMaterialPhoto> findPage(Page<WxMaterialPhoto> page, WxMaterialPhoto wxMaterialPhoto) {
		return super.findPage(page, wxMaterialPhoto);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMaterialPhoto wxMaterialPhoto) {
		super.save(wxMaterialPhoto);
	}
	
	@Transactional(readOnly = false)
	public String synAddPhoto(WxMaterialPhoto wxMaterialPhoto) throws WexinReqException {
		String accesstoken=WsUtils.getAccessToken();
		String fileName=com.thinkgem.jeesite.common.utils.FileUtils.getFilePathByUrl(wxMaterialPhoto.getImgUrl());	
		WxMediaForMaterialResponse reponse=JwMediaAPI.addMediaFileByMaterialNews(accesstoken, WsConstant.MSG_TYPE_IMAGE , "",fileName);
		return reponse.getMedia_id();
	}
	
	@Transactional(readOnly = false)
	public void synDelPhoto(WxMaterialPhoto wxMaterialPhoto)  {
		try {
			String accesstoken=WsUtils.getAccessToken();
			JwMediaAPI.deleteArticlesByMaterialNews(accesstoken, wxMaterialPhoto.getMediaId());
		} catch (WexinReqException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMaterialPhoto wxMaterialPhoto) {
		super.delete(wxMaterialPhoto);
	}
	
}