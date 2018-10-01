const config = require("../../config.js");
var app = getApp();
Page({
  data:{
    appId: '',
    timeStamp: '',
    nonceStr: '',
    orderpackage: '',
    signType: '',
    paySign: '',
    orderId: 0,
    wsOrderItemList: [],
    wsAddress: {},
    wsOrder: 0,
    totalMoney: 0,
    expressWay: 0,
  },
  onLoad:function(options){
    var orderId = options.orderId;
    if(orderId==null || orderId == ''){
      this.loadProductDetail();
    }else{
      this.loadProductDetailOrderId(orderId);
    }
    
  },
  loadProductDetail:function(){
    var that = this;
    wx.request({
      url: config.pay,
      method:'post',
      data: wx.getStorageSync('wsPayOrder'),
      header: {
        'Content-Type':  'application/json'
      },
      success: function (res) {
        var wsOrderItemList = res.data.wsOrderItemList;
        var wsAddress = res.data.wsAddress;
        var wsOrder = res.data.wsOrder;
        var totalMoney = res.data.totalMoney;
        var expressWay = res.data.expressWay;
        var appId = res.data.appId;
        var nonceStr = res.data.nonceStr;
        var orderpackage = res.data.package;
        var signType = res.data.signType;
        var paySign = res.data.paySign;
        that.setData({
          wsOrderItemList: wsOrderItemList,
          wsAddress: wsAddress,
          wsOrder: wsOrder,
          totalMoney: totalMoney,
          expressWay: expressWay,
        });
      },
    });
  },
  loadProductDetailOrderId: function (orderId) {
    var orderId=orderId;
    var that = this;
    wx.request({
      url: config.payByOrderId,
      method: 'post',
      data: { 
        user_id: app.globalData.userInfo.id,
        orderId: orderId,
        },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var wsOrderItemList = res.data.wsOrderItemList;
        var wsAddress = res.data.wsAddress;
        var wsOrder = res.data.wsOrder;
        var totalMoney = res.data.totalMoney;
        var expressWay = res.data.expressWay;
        var appId = res.data.appId;
        var nonceStr = res.data.nonceStr;
        var orderpackage = res.data.package;
        var signType = res.data.signType;
        var paySign = res.data.paySign;
        that.setData({
          wsOrderItemList: wsOrderItemList,
          wsAddress: wsAddress,
          wsOrder: wsOrder,
          totalMoney: totalMoney,
          expressWay: expressWay,
        });
      },
      fail: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      },
    })
  },

//微信支付
  createProductOrderByWX:function(e){
    wx.requestPayment({
      timeStamp: this.data.timeStamp,
      nonceStr: this.data.nonceStr,
      package: this.data.orderpackage,
      signType: this.data.signType,
      paySign: this.data.paySign,
      success: function (res) {
        wx.showToast({
          title: "支付成功!",
          duration: 2000,
        });
        setTimeout(function () {
          wx.navigateTo({
            url: '../user/dingdan?currentTab=1&otype=deliver',
          });
        }, 2500);
      },
      fail: function (res) {
        wx.showToast({
          title: res,
          duration: 3000
        })
      }
    })
  },




});