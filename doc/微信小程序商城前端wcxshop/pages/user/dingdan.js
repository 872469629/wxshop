const config = require("../../config.js");
var common = require("../../utils/common.js");
var app = getApp();
Page({  
  data: {  
    winWidth: 0,  
    winHeight: 0,  
    // tab切换  
    currentTab: 0,  
    orderList0:[],
    orderList1:[],
    orderList2:[],
    orderList3:[],
    orderList4:[],
    orderList5:[],
  },  
  onLoad: function(options) {  
    this.initSystemInfo();
    this.setData({
      currentTab: parseInt(options.currentTab),
    });
    this.loadOrderList();
  },  

//取消订单
removeOrder:function(e){
    var that = this;
    var orderId = e.currentTarget.dataset.orderid;
    console.log(orderId);
    wx.showModal({
      title: '提示',
      content: '你确定要取消订单吗？',
      success: function(res) {
        res.confirm && wx.request({
          url: config.ordercancel,
          method:'post',
          data: {
            orderId: orderId,
            user_id: app.globalData.userInfo.id,
          },
          header: {
            'Content-Type':  'application/x-www-form-urlencoded'
          },
          success: function (res) {
            var status = res.data.ret;
            if(status == 1){
              wx.showToast({
                title: '操作成功！',
                duration: 2000
              });
              that.loadOrderList();
            }else{
              wx.showToast({
                title: res.data.msg,
                duration: 2000
              });
            }
          },
          fail: function () {
            // fail
            wx.showToast({
              title: '网络异常！',
              duration: 2000
            });
          }
        });

      }
    });
  },

  //确认收货
recOrder:function(e){
    var that = this;
    var orderId = e.currentTarget.dataset.orderId;
    wx.showModal({
      title: '提示',
      content: '你确定已收到宝贝吗？',
      success: function(res) {
        res.confirm && wx.request({
          url: config.orderRecevied,
          method:'post',
          data: {
            orderId: orderId,
            user_id: app.globalData.userInfo.id,
          },
          header: {
            'Content-Type':  'application/x-www-form-urlencoded'
          },
          success: function (res) {
            //--init data
            var status = res.data.status;
            if(status == 1){
              wx.showToast({
                title: '操作成功！',
                duration: 2000
              });
              that.loadOrderList();
            }else{
              wx.showToast({
                title: res.data.err,
                duration: 2000
              });
            }
          },
          fail: function () {
            // fail
            wx.showToast({
              title: '网络异常！',
              duration: 2000
            });
          }
        });

      }
    });
  },

  loadOrderList: function(){
    var that = this;
    wx.request({
      url: config.orderlist,
      method:'post',
      data: {
        user_id: app.globalData.userInfo.id,
      },
      header: {
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.ret;
        var list = res.data.wsAllOrderItemList;
        var wsWaitPayOrderList = res.data.wsWaitPayOrderList;
        var wsWaitSendOrderList = res.data.wsWaitSendOrderList;          
        var wsWaitReceviedOrderList = res.data.wsWaitReceviedOrderList;
        var wsWaitEvaluationOrderList = res.data.wsWaitEvaluationOrderList;
        var wsFinshOrderList = res.data.wsFinshOrderList;
        var itemlist0 = [];
        var itemlist1 = [];
        var itemlist2 = [];
        var itemlist3 = [];
        var itemlist4 = [];
        var alllist = res.data.wsAllOrderItemList;
        for (var i = 0; i < alllist.length; i++) {
          var wsAllOrderItem = alllist[i];
          for (var j = 0; j < wsWaitPayOrderList.length;j++){
            if (wsAllOrderItem.wsOrder.id == wsWaitPayOrderList[j].id){
              itemlist0.push(wsAllOrderItem);
            }
          }
          for (var k = 0; k < wsWaitSendOrderList.length; k++) {
            if (wsAllOrderItem.wsOrder.id == wsWaitSendOrderList[k].id) {
              itemlist1.push(wsAllOrderItem);
            }
          }
          for (var l = 0; l < wsWaitReceviedOrderList.length; l++) {
            if (wsAllOrderItem.wsOrder.id == wsWaitReceviedOrderList[l].id) {
              itemlist2.push(wsAllOrderItem);
            }
          }
          for (var m = 0; m < wsFinshOrderList.length; m++) {
            if (wsAllOrderItem.wsOrder.id == wsWaitEvaluationOrderList[m].id) {
              itemlist3.push(wsAllOrderItem);
            }
          }
          for (var m = 0; m < wsFinshOrderList.length; m++) {
            if (wsAllOrderItem.wsOrder.id == wsFinshOrderList[m].id) {
              itemlist4.push(wsAllOrderItem);
            }
          }
        } 
        that.setData({
          orderList0: itemlist0,
          orderList1: itemlist1,
          orderList2: itemlist2,
          orderList3: itemlist3,
          orderList4: itemlist4,
        });         
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },


  initSystemInfo:function(){
    var that = this;  
  
    wx.getSystemInfo( {
      success: function( res ) {  
        that.setData( {  
          winWidth: res.windowWidth,  
          winHeight: res.windowHeight  
        });  
      }    
    });  
  },
  bindChange: function(e) {  
    var that = this;  
    that.setData( { currentTab: e.detail.current });  
  },  
  swichNav: function(e) {  
    var that = this;  
    if( that.data.currentTab === e.target.dataset.current ) {  
      return false;  
    } else { 
      var current = e.target.dataset.current;
      that.setData({
        currentTab: parseInt(current),
      });
    };
  },

  payOrderByWechat: function (e) {
    wx.navigateTo({
      url: '/pages/order/pay?orderId=' + e.target.dataset.orderid,
    })
  },

})