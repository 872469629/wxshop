const config = require("../../config.js");
var app = getApp();
Page({
  data: {
    shengArr: [],//省级数组
    shengId: [],//省级id数组
    shiArr: [],//城市数组
    shiId: [],//城市id数组
    quArr: [],//区数组
    shengIndex: 0,
    shiIndex: 0,
    quIndex: 0,
    mid: 0,
    sheng:0,
    city:0,
    area:0,
    code:0,
    id:0
  },
  onLoad: function (options) {
    // 生命周期函数--监听页面加载
    var that = this;
    if (options.addressId != null && options.addressId!=''){
      that.setData({
        id: options.addressId
      })
    }
    //获取省级城市
    wx.request({
      url: config.addressdetail,
      data: {
        user_id: app.globalData.userInfo.id,
        id: that.data.id,
      },
      method: 'POST',
      success: function (res) {
        var ret = res.data.ret;
        var province = res.data.citys;
        var sArr = [];
        var sId = [];
        sArr.push('请选择');
        sId.push('0');
        for (var i = 0; i < province.length; i++) {
          sArr.push(province[i].n);
          sId.push(province[i].id);
        }
        that.setData({
          shengArr: sArr,
          shengId: sId
        })
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      },
    })

  },
  formSubmit: function (e) {
    var adds = e.detail.value;
    if (this.data.sheng == null || this.data.sheng == '' || this.data.city == null || this.data.city == '' 
       || this.data.area == null || this.data.area == ''){
      wx.showToast({
        title: '省市区不能为空！',
        duration: 2000
      });
      return;
    }
    wx.request({
      url: config.addresssave,
      data: {
        user_id: app.globalData.userInfo.id,
        consignee: adds.name,
        tel: adds.phone,
        city: this.data.sheng + " " + this.data.city + " " + this.data.area,
        address: adds.address,
        zipCode: this.data.code,
      },
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {// 设置请求的 header
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {
        // success
        var status = res.data.status;
        if(status==1){
          wx.showToast({
            title: '保存成功！',
            duration: 2000
          });
        }else{
          wx.showToast({
            title: res.data.err,
            duration: 2000
          });
        }
        wx.redirectTo({
          url: 'user-address/user-address'
        });
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })


  },

  bindPickerChangeshengArr: function (e) {
    this.setData({
      shengIndex: e.detail.value,
      shiArr: [],
      shiId: [],
      quArr: [],
      quiId: []
    });
    var that = this;
    wx.request({
      url: config.findCityList,
      data: { id: this.data.shengId[this.data.shengIndex]},
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {// 设置请求的 header
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {
        // success
        var ret = res.data.ret;
        var city = res.data.cityList;

        var hArr = [];
        var hId = [];
        hArr.push('请选择');
        hId.push('0');
        for (var i = 0; i < city.length; i++) {
          hArr.push(city[i].name);
          hId.push(city[i].id);
        }
        that.setData({
          sheng:res.data.sheng.name,
          shiArr: hArr,
          shiId: hId
        })
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      },

    })
  },
  bindPickerChangeshiArr: function (e) {
    this.setData({
      shiIndex: e.detail.value,
      quArr:[],
      quiId: []
    })
    var that = this;
    wx.request({
      url: config.findCityList,
      data: { id: this.data.shiId[this.data.shiIndex] },
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {// 设置请求的 header
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var ret = res.data.ret;
        var areas = res.data.cityList;

        var qArr = [];
        var qId = [];
        qArr.push('请选择');
        qId.push('0');
        for (var i = 0; i < areas.length; i++) {
          qArr.push(areas[i].name)
          qId.push(areas[i].id)
        }
        that.setData({
          city:res.data.sheng.name,
          quArr: qArr,
          quiId: qId
        })
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },
  bindPickerChangequArr: function (e) {
    var index = e.detail.value;
    this.setData({
      quIndex: e.detail.value,
      area: this.data.quArr[index],
    })
  }

})