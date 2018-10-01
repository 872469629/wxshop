(function(mui, owner) {
	
	//获取url参数
	owner.getUrlParam =function GetQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
	//url参数中文解码
	owner.getDecodeUrlParam =function GetDecodeQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = decodeURI(window.location.search).substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}

	//前端项目地址
	owner.frontUrl="http://www.zhongdainfo.com/appshop";
	//后端接口地址
	owner.serverUrl="http://www.zhongdainfo.com/wshop/wx";




})(mui, window.wxshop = {});