const config = require("../../config.js");
var app = getApp();
Page({
  data:{
    wsOrderItemList:[],
    wsAddress:'',
    total:0,
  },
  onLoad:function(options){
    var that = this;
    wx.request({
      url: config.order,
      method: 'post',
      data: wx.getStorageSync('wsCartOrder'),
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        var ret=res.data.ret;
        if(ret!=1){
          wx.showToast({
            title: res.data.msg,
            duration: 2000
          });
          return;
        }
        var wsOrderItemList = res.data.wsOrderItemList;
        var wsAddress = res.data.wsAddress;
        that.setData({
          wsOrderItemList: wsOrderItemList,
          wsAddress: wsAddress,
        });
        that.sum();
      },
    });
  },
  bindMinus: function (e) {
    var that = this;
    var index = parseInt(e.currentTarget.dataset.index);
    var num = that.data.wsOrderItemList[index].quantity;
    if (num > 1) {
      num--;
    }
    var wsOrderItemList = that.data.wsOrderItemList;
    wsOrderItemList[index].quantity = num;
    this.setData({
      wsOrderItemList: wsOrderItemList
    });

  },

  bindPlus: function (e) {
    var that = this;
    var index = parseInt(e.currentTarget.dataset.index);
    var num = that.data.wsOrderItemList[index].quantity;
    num++;
    var wsOrderItemList = that.data.wsOrderItemList;
    wsOrderItemList[index].quantity = num;
    this.setData({
      wsOrderItemList: wsOrderItemList
    });
    
    that.sum();
  }, 
  sum: function () {
    var wsOrderItemList = this.data.wsOrderItemList;
    // 计算总金额
    var total = 0;
    for (var i = 0; i < wsOrderItemList.length; i++) {
      total += wsOrderItemList[i].quantity * wsOrderItemList[i].reallyUnitPrice;
    }
    // 写回经点击修改后的数组
    this.setData({
      total: total
    });
  },

//去支付
  createProductOrderByWX:function(e){
    if (this.data.wsAddress==null){
      wx.showToast({
        title: '请选择收货地址！',
        duration: 2000
      });
      return;
    }
    var arrParam = [];
    for (var i = 0; i < this.data.wsOrderItemList.length; i++) {
        var item = {};
        item.skuId = this.data.wsOrderItemList[i].skuId;
        item.quantity = this.data.wsOrderItemList[i].quantity;
        item.memberId = app.globalData.userInfo.id;
        arrParam.push(item);
    }
    if (arrParam.length == 0) {
      wx.showToast({
        title: '请选择要结算的商品！',
        duration: 2000
      });
      return;
    }
    console.log(JSON.stringify(arrParam));
    wx.setStorageSync('wsPayOrder', JSON.stringify(arrParam));
    //存回data
    wx.navigateTo({
      url: '/pages/order/pay',
    })
  },
});