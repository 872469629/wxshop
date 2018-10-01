const config = require("../../config.js");
var app = getApp();
// pages/user/shoucang.js
Page({
  data:{
    page:1,
    productData:[],
  },
  onLoad:function(options){
    this.loadProductData();
  },
  onShow:function(){
    // 页面显示
    this.loadProductData();
  },
  removeFavorites:function(e){
    var that = this;
    var ccId = e.currentTarget.dataset.favId;

    wx.showModal({
      title: '提示',
      content: '你确认移除吗',
      success: function(res) {

        res.confirm && wx.request({
          url: config.cancelCollect,
          method:'post',
          data: {
            user_id: app.globalData.userInfo.id,
            collectId: ccId,
          },
          header: {
            'Content-Type':  'application/x-www-form-urlencoded'
          },
          success: function (res) {
            var data = res.data;
            if(data.ret == '1'){
              that.loadProductData();
            }
          },
        });

      }
    });
  },
  loadProductData:function(){
    var that = this;
    wx.request({
      url: config.userCollect,
      method:'post',
      data: {
        user_id: app.globalData.userInfo.id,
      },
      header: {
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var wsMemberCollectLogList = res.data.wsMemberCollectLogList;
        that.setData({
          productData: wsMemberCollectLogList,
        });
      },
    });
  },
});