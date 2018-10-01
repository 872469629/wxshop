const config = require("../../config.js");
var app = getApp();
Page({
  data:{
    toUseCouponList:[],
    wsActivityCouponList: [],
  },
   like:function(e){
    wx.navigateTo({
      url: config.coupon,
      success: function(res){
        // success
      },
      fail: function() {
        // fail
      },
      complete: function() {
        // complete
      }
    })
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    var that = this;
    wx.request({
      url: config.coupon,
      method:'post',
      data: { user_id: app.globalData.userInfo.id,},
      header: {
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {  
        var ret = res.data.ret;
        var toUseCouponList = res.data.toUseCouponList;
        var wsActivityCouponList = res.data.wsActivityCouponList;
        console.log(wsActivityCouponList);
        if(ret==1){
          that.setData({
            toUseCouponList: toUseCouponList,
            wsActivityCouponList: wsActivityCouponList,
          });
        }else{
          wx.showToast({
            title: res.data.msg,
            duration: 2000
          });
        }
        //endInitData
      },
      error:function(e){
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      },
    });
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  }
})