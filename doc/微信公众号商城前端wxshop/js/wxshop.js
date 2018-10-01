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
	
	//获取openId
	owner.getOpenId =function getOpenId(appid){
		var url = window.location.href;
		location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+url+"&response_type=code&scope=snsapi_base&123=123#wechat_redirect";
	}
	//初始化js sdk配置
	owner.wx_config =function wx_config(callback) {
		mui.ajax(wxshop.serverUrl+"/getWxConfig", {
				type: "post",
				data:{
					url: window.location.href,
				},
				dataType: "json",
				success: function(data) {
					if(data.ret==0){
						mui.alert(data.msg);
					}
					if(data.ret==2){
						wxshop.getOpenId(data.appid);
					}					
				    var jsapi_appId = data.jsapi_appId;
			        var jsapi_timestamp = data.jsapi_timestamp;
			        var jsapi_nonceStr = data.jsapi_nonceStr;
			        var jsapi_signature = data.jsapi_signature;
			        wx.config({
			            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			            appId: jsapi_appId, // 必填，公众号的唯一标识
			            timestamp: jsapi_timestamp, // 必填，生成签名的时间戳
			            nonceStr: jsapi_nonceStr, // 必填，生成签名的随机串
			            signature: jsapi_signature,// 必填，签名
			            jsApiList: ['checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'hideMenuItems', 'showMenuItems',
			                'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem', 'translateVoice', 'startRecord', 'stopRecord', 'onRecordEnd', 'playVoice', 'pauseVoice',
			                'stopVoice', 'uploadVoice', 'downloadVoice', 'chooseImage', 'previewImage', 'uploadImage', 'downloadImage', 'getNetworkType', 'openLocation', 'getLocation',
			                'hideOptionMenu', 'showOptionMenu', 'closeWindow', 'scanQRCode', 'chooseWXPay', 'openProductSpecificView', 'addCard', 'chooseCard', 'openCard']
			        });
			        wx.ready(function () {
			            //config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			            callback();
			        });
				},
				error: function(xhr,type,errorThrown) {
					mui.alert("后台接口请求失败，请联系管理员！错误"+errorThrown);
				}
		});
	}
	//分享到微信好友
	owner.wxShareFriend =function wxShareFriend(title,desc,link,imgUrl) {
		wx.onMenuShareAppMessage({
	    	title: title, // 分享标题
	    	desc: desc, // 分享描述
			link: link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			imgUrl: imgUrl, // 分享图标
			type: '', // 分享类型,music、video或link，不填默认为link
			dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			success: function () {
			
			},
			cancel: function () {
				mui.alert("您已取消分享");
			}
		});
	}

	//前端项目地址
	owner.frontUrl="http://www.zhongdainfo.com/wxshop";
	//后端接口地址
	owner.serverUrl="http://www.zhongdainfo.com/wshop/wx";




})(mui, window.wxshop = {});