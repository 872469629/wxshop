const config = require("../../config.js");
var app = getApp();
//引入这个插件，使html内容自动转换成wxml内容
var WxParse = require('../../wxParse/wxParse.js');
Page({
  firstIndex: -1,
  data: {
    bannerApp: true,
    winWidth: 0,
    winHeight: 0,
    currentTab: 0, //tab切换  
    currentSkuId:'',
    productId: 0,
    wsProduct: {},
    bannerItem: [],
    buynum: 1,
    buystatus:0,
    // 产品图片轮播
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    // 属性选择
    firstIndex: -1,
    //准备数据
    wsProduct:{},
    WsProdSkuBaseAttrList: [],
    wsProdSkuList: [],
    wsConsulationNum:0,
    wsMemberCollectLogNum:0,
  },
  // 传值
  onLoad: function (option) {
    var that = this;
    that.setData({
      productId: option.productId,
    });
    that.loadProductDetail();

  },
  // 商品详情数据获取
  loadProductDetail: function () {
    var that = this;
    wx.request({
      url: config.prodDetail,
      method: 'post',
      data: {
        id: that.data.productId,
        user_id: app.globalData.userInfo.id,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        //--init data 
        var status = res.data.ret;
        if (status == 1) {
          var wsProduct = res.data.wsProduct;
          var content = wsProduct.prodContent;
          WxParse.wxParse('content', 'html', content, that, 3);
          that.setData({
            wsProduct: wsProduct,
            bannerItem: wsProduct.prodImageList,
            wsProdSkuList: res.data.wsProdSkuList,
            wsMemberCollectLogNum: res.data.wsMemberCollectLogNum,
            wsConsulationNum: res.data.wsConsulationNum,
          });
        } else {
          wx.showToast({
            title: res.data.err,
            duration: 2000,
          });
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000,
        });
      },
    });
  },
  // 加减
  changeNum: function (e) {
    var that = this;
    if (e.target.dataset.alphaBeta == 0) {
      if (this.data.buynum <= 1) {
        buynum: 1
      } else {
        this.setData({
          buynum: this.data.buynum - 1
        })
      };
    } else {
      this.setData({
        buynum: this.data.buynum + 1
      })
    };
  },
  selectSkuBox: function (e) {
    this.setData({
      currentSkuId: e.currentTarget.dataset.id,
    })
  },
  //添加到收藏
  addFavorites: function (e) {
    var that = this;
    wx.request({
      url: config.addCollect,
      method: 'post',
      data: {
        user_id: app.globalData.userInfo.id,
        prodId: that.data.productId,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        // //--init data        
        var data = res.data;
        if (data.ret == 1) {
          that.setData({
            wsMemberCollectLogNum: res.data.wsMemberCollectLogNum,
          });
          wx.showToast({
            title: '操作成功！',
            duration: 2000
          });
        } else {
          wx.showToast({
            title: data.msg,
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
  },
  addShopCart: function (e) { //添加到购物车
    var that = this;
    if (that.data.currentSkuId == null || that.data.currentSkuId == '' ){
      wx.showToast({
        title: "请选中商品规格",
        duration: 3000
      });
      return;
    }
    wx.request({
      url: config.addCart,
      method: 'post',
      data: {
        user_id: app.globalData.userInfo.id,
        skuId: that.data.currentSkuId,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        // //--init data        
        var data = res.data;
        if (data.ret == 1) {
            wx.showToast({
              title: '加入购物车成功',
              icon: 'success',
              duration: 2000
            });
        } else {
          wx.showToast({
            title: data.msg,
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
  },
  buyProd: function (e) { //添加到购物车
    var that = this;
    if (that.data.currentSkuId == null || that.data.currentSkuId == '') {
      wx.showToast({
        title: "请选中商品规格",
        duration: 3000
      });
      return;
    }
    var arrParam = [];
    var item = {};
    item.skuId = that.data.currentSkuId;
    item.quantity = 1;
    item.memberId = app.globalData.userInfo.id;
    arrParam.push(item);
    wx.setStorageSync('wsCartOrder', JSON.stringify(arrParam));
    wx.redirectTo({
      url: '../order/orderconfirm'
    });
  },
  // 弹窗
  setModalStatus: function (e) {
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })

    this.animation = animation
    animation.translateY(300).step();

    this.setData({
      animationData: animation.export()
    })

    if (e.currentTarget.dataset.status == 1 || e.currentTarget.dataset.status == 2) {

      this.setData(
        {
          buystatus:e.currentTarget.dataset.status,
          showModalStatus: true,
        }
      );
    }
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation
      })
      if (e.currentTarget.dataset.status == 0) {
        this.setData(
          {
            showModalStatus: false
          }
        );
      }
    }.bind(this), 200)
  },
  bindChange: function (e) {//滑动切换tab 
    var that = this;
    that.setData({ currentTab: e.detail.current });
  },
  initNavHeight: function () {////获取系统信息
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        });
      }
    });
  },
  bannerClosed: function () {
    this.setData({
      bannerApp: false,
    })
  },
  swichNav: function (e) {//点击tab切换
    var that = this;
    if (that.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  }
});
