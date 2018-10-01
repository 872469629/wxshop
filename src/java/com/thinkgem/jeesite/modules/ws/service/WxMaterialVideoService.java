package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.dao.WxMaterialVideoDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialVideo;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;
import com.thinkgem.jeesite.modules.wx.wxbase.wxmedia.JwMediaAPI;
import com.thinkgem.jeesite.modules.wx.wxbase.wxmedia.model.WxMediaForMaterialResponse;

/**
 * 视频素材Service
 * @author 大胖老师
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class WxMaterialVideoService extends CrudService<WxMaterialVideoDao, WxMaterialVideo> {

	public WxMaterialVideo get(String id) {
		return super.get(id);
	}
	
	public List<WxMaterialVideo> findList(WxMaterialVideo wxMaterialVideo) {
		return super.findList(wxMaterialVideo);
	}
	
	public Page<WxMaterialVideo> findPage(Page<WxMaterialVideo> page, WxMaterialVideo wxMaterialVideo) {
		return super.findPage(page, wxMaterialVideo);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMaterialVideo wxMaterialVideo) {
		super.save(wxMaterialVideo);
	}
	
	@Transactional(readOnly = false)
	public String synAddVideo(WxMaterialVideo wxMaterialVideo) throws WexinReqException {
		String accesstoken=WsUtils.getAccessToken();
		String fileName=com.thinkgem.jeesite.common.utils.FileUtils.getFilePathByUrl(wxMaterialVideo.getVideoUrl());	
		WxMediaForMaterialResponse reponse=JwMediaAPI.addMediaFileVideo(accesstoken, WsConstant.MSG_TYPE_VIDEO ,fileName,wxMaterialVideo.getTitle(),wxMaterialVideo.getDescription()	);
		return reponse.getMedia_id();
	}
	
	@Transactional(readOnly = false)
	public void synDelAudio(WxMaterialVideo wxMaterialVideo) throws WexinReqException {
		try{
			String accesstoken=WsUtils.getAccessToken();
			JwMediaAPI.deleteArticlesByMaterialNews(accesstoken, wxMaterialVideo.getMediaId());
		} catch (WexinReqException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMaterialVideo wxMaterialVideo) {
		super.delete(wxMaterialVideo);
	}
	
}