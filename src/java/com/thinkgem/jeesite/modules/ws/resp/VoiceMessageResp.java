package com.thinkgem.jeesite.modules.ws.resp;

/**
 * 音频消息
 * @author 大胖老师
 *
 */
public class VoiceMessageResp extends BaseMessageResp{
	
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

}
