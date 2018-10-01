const config = require("../../config.js");
var app = getApp();
Page({
  data:{
    page:1,
    minusStatuses: ['disabled', 'disabled', 'normal', 'normal', 'disabled'],
    total: 0,
    carts: []
  },
  onLoad: function (options) {
    this.loadProductData();
    this.sum();
  },
bindMinus: function(e) {
  var that = this;
  var index = parseInt(e.currentTarget.dataset.index);
  var num = that.data.carts[index].quantity;
  if (num > 1) {
    num--;
  }
  var cart_id = e.currentTarget.dataset.cartid;
  that.data.carts[index].quantity = num;
  that.sum();

},

bindPlus: function(e) {
  var that = this;
  var index = parseInt(e.currentTarget.dataset.index);
  var num = that.data.carts[index].quantity;
  num++;
  var cart_id = e.currentTarget.dataset.cartid;
  that.data.carts[index].quantity = num;
  that.sum();
}, 

bindCheckbox: function(e) {
  /*绑定点击事件，将checkbox样式改变为选中与非选中*/
  //拿到下标值，以在carts作遍历指示用
  var index = parseInt(e.currentTarget.dataset.index);
  //原始的icon状态
  var selected = this.data.carts[index].selected;
  var carts = this.data.carts;
  // 对勾选状态取反
  carts[index].selected = !selected;
  // 写回经点击修改后的数组
  this.setData({
    carts: carts
  });
  this.sum()
},

bindSelectAll: function() {
   // 环境中目前已选状态
   var selectedAllStatus = this.data.selectedAllStatus;
   // 取反操作
   selectedAllStatus = !selectedAllStatus;
   // 购物车数据，关键是处理selected值
   var carts = this.data.carts;
   // 遍历
   for (var i = 0; i < carts.length; i++) {
     carts[i].selected = selectedAllStatus;
   }
   this.setData({
     selectedAllStatus: selectedAllStatus,
     carts: carts
   });
   this.sum()
 },

bindCheckout: function() {
   // 初始化toastStr字符串
   var toastStr = '';
   // 遍历取出已勾选的cid
   var arrParam = [];
   for (var i = 0; i < this.data.carts.length; i++) {
     if (this.data.carts[i].selected) {
       toastStr += this.data.carts[i].id;
       toastStr += ',';
       var item = {};
       item.skuId = this.data.carts[i].skuId;
       item.quantity = this.data.carts[i].quantity;
       item.memberId=app.globalData.userInfo.id;
       arrParam.push(item)
     }
   }
   if (toastStr==''){
     wx.showToast({
       title: '请选择要结算的商品！',
       duration: 2000
     });
     return false;
   };
   wx.setStorageSync('wsCartOrder', JSON.stringify(arrParam));
   //存回data
   wx.navigateTo({
     url: '/pages/order/orderconfirm',
   })
 },

 bindToastChange: function() {
   this.setData({
     toastHidden: true
   });
 },

sum: function() {
    var carts = this.data.carts;
    // 计算总金额
    var total = 0;
    for (var i = 0; i < carts.length; i++) {
      if (carts[i].selected) {
        total += carts[i].quantity * carts[i].unitPrice;
      }
    }
    // 写回经点击修改后的数组
    this.setData({
      carts: carts,
      total: '¥ ' + total
    });
  },



onShow:function(){
  this.loadProductData();
},

removeShopCard:function(e){
    var that = this;
    var cartId = e.currentTarget.dataset.cartid;
    wx.showModal({
      title: '提示',
      content: '你确认移除吗',
      success: function(res) {
        res.confirm && wx.request({
          url: config.deleteCart,
          method:'post',
          data: {
            user_id: app.globalData.userInfo.id,
            cartId: cartId
          },
          header: {
            'Content-Type':  'application/x-www-form-urlencoded'
          },
          success: function (res) {
            //--init data
            var data = res.data;
            if(data.ret == 1){
              var wsCartList = res.data.wsCartList;
              that.setData({
                carts: wsCartList,
              });
            }else{
              wx.showToast({
                title: '操作失败！',
                duration: 2000
              });
            }
          },
        });
      },
      fail: function() {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },

// 数据案例
  loadProductData:function(){
    var that = this;
    wx.request({
      url: config.cart,
      method:'post',
      data: {
        user_id: app.globalData.userInfo.id,
      },
      header: {
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {
        //--init data
        var wsCartList = res.data.wsCartList;
        that.setData({
          carts: wsCartList,
        });
        //endInitData
      },
    });
  },

})