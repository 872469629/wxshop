package com.thinkgem.jeesite.modules.ws.resp;

/**
 * 图片消息
 * @author 大胖老师
 *
 */
public class ImageMessageResp extends BaseMessageResp{
	//图片
    private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image Image) {
		this.Image = Image;
	}

    
}
