const config = require("../../config.js");
var app = getApp()
Page( {
  data: {
    member: {},
    waitPayOrderNum:'',
    waitSendOrderNum: '',
    waitReceviedOrderNum: '',
    waitEvakyatuibOrderNum: '',
    waitBackOrderNum: '',
    tel:'',
    flag:false,
    userListInfo: [ {
        icon: '../../images/iconfont-dingdan.png',
        text: '我的订单',
        isunread: true,
        unreadNum: 2
      }, {
        icon: '../../images/iconfont-card.png',
        text: '我的代金券',
        isunread: false,
        unreadNum: 2
      }, {
        icon: '../../images/iconfont-icontuan.png',
        text: '我的拼团',
        isunread: true,
        unreadNum: 1
      }, {
        icon: '../../images/iconfont-shouhuodizhi.png',
        text: '收货地址管理'
      }, {
        icon: '../../images/iconfont-kefu.png',
        text: '联系客服'
      }, {
        icon: '../../images/iconfont-help.png',
        text: '常见问题'
      }],
       loadingText: '加载中...',
       loadingHidden: false,
  },
  onLoad: function () {
      var that = this;
      // 查看是否授权
      wx.getSetting({
        success: function (res) {
          if (res.authSetting['scope.userInfo']) {
            // 已经授权，可以直接调用 getUserInfo 获取头像昵称
            wx.getUserInfo({
              success: function (res) {
                  that.setData({
                    flag:true,
                  });
              }
            })
          }
        }
      })
      this.loadOrderStatus();
  },
  onShow:function(){
    this.loadOrderStatus();
  },
  //用户授权之后，将用户信息传递给后台
  bindGetUserInfo: function (e) {
    var that = this;
    that.setData({
      flag: true,
    });
    wx.request({
      url: config.saveWcxUser,
      method: 'post',
      data: {
        user_id: app.globalData.userInfo.id,
        nickName: e.detail.userInfo.nickName,
        avatarUrl: e.detail.userInfo.avatarUrl,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.ret;
        if (status == 1) {
          that.loadOrderStatus();
        } else {
          wx.showToast({
            title: '非法操作.',
            duration: 2000
          });
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  loadOrderStatus: function () {
    //获取用户订单数据
    var that = this;
    wx.request({
      url: config.userindex,
      method: 'post',
      data: {
        user_id: app.globalData.userInfo.id,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.ret;
        if (status == 1) {
          var member = res.data.member;
          var waitPayOrderNum = res.data.waitPayOrderNum;
          var waitSendOrderNum = res.data.waitSendOrderNum;
          var waitReceviedOrderNum = res.data.waitReceviedOrderNum;
          var waitEvakyatuibOrderNum = res.data.waitEvakyatuibOrderNum;
          var waitBackOrderNum = res.data.waitBackOrderNum;
          var tel=res.data.tel;
          that.setData({
            member: member,
            waitPayOrderNum: waitPayOrderNum,
            waitSendOrderNum: waitSendOrderNum,
            waitReceviedOrderNum: waitReceviedOrderNum,
            waitEvakyatuibOrderNum: waitEvakyatuibOrderNum,
            waitBackOrderNum: waitBackOrderNum,
            tel:tel,
          });
        } else {
          wx.showToast({
            title: '非法操作.',
            duration: 2000
          });
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  contact:function(){
    wx.makePhoneCall({
      phoneNumber: this.data.tel //仅为示例，并非真实的电话号码
    })
  },
  onShareAppMessage: function () {
    return {
      title: config.shareTitle,
      path: '/pages/index/index',
      success: function (res) {
        // 分享成功
      },
      fail: function (res) {
        // 分享失败
      }
    }
  }
})