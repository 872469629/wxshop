package com.thinkgem.jeesite.modules.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ws.dao.WxMaterialAudioDao;
import com.thinkgem.jeesite.modules.ws.entity.WxMaterialAudio;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;
import com.thinkgem.jeesite.modules.ws.utils.WsUtils;
import com.thinkgem.jeesite.modules.wx.core.exception.WexinReqException;
import com.thinkgem.jeesite.modules.wx.wxbase.wxmedia.JwMediaAPI;
import com.thinkgem.jeesite.modules.wx.wxbase.wxmedia.model.WxMediaForMaterialResponse;

/**
 * 音频素材Service
 * @author 大胖老师
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class WxMaterialAudioService extends CrudService<WxMaterialAudioDao, WxMaterialAudio> {

	public WxMaterialAudio get(String id) {
		return super.get(id);
	}
	
	public List<WxMaterialAudio> findList(WxMaterialAudio wxMaterialAudio) {
		return super.findList(wxMaterialAudio);
	}
	
	public Page<WxMaterialAudio> findPage(Page<WxMaterialAudio> page, WxMaterialAudio wxMaterialAudio) {
		return super.findPage(page, wxMaterialAudio);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMaterialAudio wxMaterialAudio) {
		super.save(wxMaterialAudio);
	}
	
	@Transactional(readOnly = false)
	public String synAddAudio(WxMaterialAudio wxMaterialAudio) throws WexinReqException {
		String accesstoken=WsUtils.getAccessToken();
		String fileName=com.thinkgem.jeesite.common.utils.FileUtils.getFilePathByUrl(wxMaterialAudio.getAudioUrl());	
		WxMediaForMaterialResponse reponse=JwMediaAPI.addMediaFileByMaterialNews(accesstoken , WsConstant.MSG_TYPE_VOICE , "", fileName);
		return reponse.getMedia_id();
	}
	
	@Transactional(readOnly = false)
	public void synDelAudio(WxMaterialAudio wxMaterialAudio){
		try {
			String accesstoken=WsUtils.getAccessToken();
			JwMediaAPI.deleteArticlesByMaterialNews(accesstoken, wxMaterialAudio.getMediaId());
		} catch (WexinReqException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMaterialAudio wxMaterialAudio) {
		super.delete(wxMaterialAudio);
	}
	
}