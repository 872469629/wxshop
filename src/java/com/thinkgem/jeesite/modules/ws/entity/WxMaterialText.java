package com.thinkgem.jeesite.modules.ws.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 文本素材Entity
 * @author 大胖老师
 * @version 2017-09-28
 */
public class WxMaterialText extends DataEntity<WxMaterialText> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 文本标题
	private String textContent;		// 文本内容
	
	public WxMaterialText() {
		super();
	}

	public WxMaterialText(String id){
		super(id);
	}

	@Length(min=0, max=255, message="文本标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="文本内容长度必须介于 0 和 255 之间")
	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
}