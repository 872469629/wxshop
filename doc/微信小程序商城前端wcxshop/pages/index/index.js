const config = require("../../config.js");
var app = getApp();
Page({
  data: {
    imgUrls: [],
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    circular: true,
    productData: [],
    proCat:[],
    page: 2,
    index: 2,
    brand:[],
    imgUrl: [],
  },
  onLoad: function (options) {
    var that = this;
    app.getUserInfo(function () {
      wx.request({
        url:  config.index,
        method: 'post',
        data: { user_id: app.globalData.userInfo.id },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var productdata = res.data.wsProductList;
          var imgurls = res.data.adBannerList;
          that.setData({
            imgUrls: imgurls,
            productData: productdata
          });
        },
        fail: function (e) {
          wx.showToast({
            title: '网络异常！',
            duration: 2000
          });
        },
      })
    });
  },

  onShareAppMessage: function () {
    return {
      title: config.shareTitle,
      path: '/pages/index/index',
      success: function(res) {
        // 分享成功
      },
      fail: function(res) {
        // 分享失败
      }
    }
  }



});