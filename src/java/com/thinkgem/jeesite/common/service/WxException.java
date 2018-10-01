/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.service;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * @author ThinkGem
 */
public class WxException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WxException() {
		super();
	}

	public WxException(String message) {
		super(message);
	}

	public WxException(Throwable cause) {
		super(cause);
	}

	public WxException(String message, Throwable cause) {
		super(message, cause);
	}
}
