const config = require("../../config.js");
//获取应用实例  
var app = getApp();
Page({
    data: {
        typeTree: {}, // 数据缓存
        currType: 0 ,// 当前类型
        wsProdCategoryList:[],
        types: [],
        typeTree: [],
    },
        
    onLoad: function (option){
      var that = this;
      app.getUserInfo(function () {
        wx.request({
          url: config.prodCatIndex,
          method: 'post',
          data: { user_id: app.globalData.userInfo.id },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            var status = res.data.ret;
            if (status == 1) {
              var list = [];
              var typeTree = [];
              var wsProdCategoryList = res.data.wsProdCategoryList;
              var currType = wsProdCategoryList[0].id;
              for (var j = 0; j < wsProdCategoryList.length; j++) {
                var wsProdCategory = wsProdCategoryList[j];
                if (wsProdCategory.parentId == '0') {
                  list.push(wsProdCategory);
                }
                if (wsProdCategory.parentId == currType) {
                  typeTree.push(wsProdCategory);
                }
              }
              that.setData({
                types: list,
                wsProdCategoryList: wsProdCategoryList,
                typeTree: typeTree,
                currType: currType,
              });
            } else {
              wx.showToast({
                title: '网络异常！'+res.data.err,
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

      });
    },
    tapType: function (e){
        var that = this;
        var typeTree = [];
        const currType = e.currentTarget.dataset.typeId;
        var wsProdCategoryList = that.data.wsProdCategoryList;
        for (var j = 0; j < wsProdCategoryList.length; j++) {
          var wsProdCategory = wsProdCategoryList[j];
          if (wsProdCategory.parentId == currType) {
            typeTree.push(wsProdCategory);
          }
        } 
        that.setData({
          currType: currType,
          typeTree: typeTree,
        });

    },
})