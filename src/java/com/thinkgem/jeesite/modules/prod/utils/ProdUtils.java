package com.thinkgem.jeesite.modules.prod.utils;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.SeqUtils;

public class ProdUtils {
	
	/**
	 * 对比参数，判断是否选中
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String compareIn(String str1,String str2){
		if(StringUtils.isNotEmpty(str1)){
			String [] striArray=str1.split(",");
			for (int i = 0; i < striArray.length; i++) {
				if(str2.equals(striArray[i])){
					return "checked=\"checked\"";
				}
			}
		}
		return "";
	}
	
	/**
	 * 获取日期+序列类型的序列
	 * @return
	 */
	public static String getDateSeq(String seqName){
		int seq=SeqUtils.getNextSeq(seqName);
		return DateUtils.getDate("yyyyMMddHHmmss")+seq;
	}
}
