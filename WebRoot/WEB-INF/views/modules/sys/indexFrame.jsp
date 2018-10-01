<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<meta name="decorator" content="adminlte" />
<body>
<style type="text/css">
.info-box-text{
	text-align:center;
	font-size: 14px;
	display: block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.info-box-number{
	text-align:center;
	font-size: 18px;
	display: block;
    font-weight: bold;
}

.users-list>li {
    width: 50%;
    float: left;
    padding: 10px;
    text-align: center;
}
</style>
<script src="${ctxStatic}/echarts/echarts.min.js" type="text/javascript"></script>
    <!-- Main content -->
    <section class="content">
      <!-- Info boxes -->
      <div class="row">
        <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-aqua"><i class="ion ion-ios-gear-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">全部订单</span>
              <span class="info-box-number">${totalNum}<small>单</small></span>
            </div>
          </div>
        </div>
        <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-red"><i class="fa fa-google-plus"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">待付款订单</span>
              <span class="info-box-number">${waitPayNum}<small>单</span>
            </div>
          </div>
        </div>

        <div class="clearfix visible-sm-block"></div>
        <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-green"><i class="ion ion-ios-cart-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">待发货订单</span>
              <span class="info-box-number">${waitSendNum}<small>单</span>
            </div>
          </div>
        </div>
        <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-yellow"><i class="ion ion-ios-people-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">待收货订单</span>
              <span class="info-box-number">${waitReceviedNum}<small>单</span>
            </div>
          </div>
        </div>
                <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-aqua"><i class="ion ion-ios-gear-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">待评价订单</span>
              <span class="info-box-number">${waitEvaluationNum}<small>单</span>
            </div>
          </div>
        </div>
                <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-red"><i class="fa fa-google-plus"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">待处理退款定单</span>
              <span class="info-box-number">${wxWaitNum}<small>单</span>
            </div>
          </div>
        </div>
                <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-green"><i class="ion ion-ios-cart-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">已完成订单</span>
              <span class="info-box-number">${waitFinshNum}<small>单</span>
            </div>
          </div>
        </div>
                <div class="col-md-3 col-sm-6 col-xs-12">
          <div class="info-box">
            <span class="info-box-icon bg-yellow"><i class="ion ion-ios-people-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">已取消订单</span>
              <span class="info-box-number">${waitCancelNum}<small>单</span>
            </div>
          </div>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

 <%--      <!-- Main row -->
      <div class="row">
        <div class="col-md-4">
          <div class="info-box bg-yellow">
            <span class="info-box-icon"><i class="ion ion-ios-pricetag-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">昨日新增收入</span>
              <span class="info-box-number">￥${memberIncreased.selMoney}</span>
            </div>
            <!-- /.info-box-content -->
          </div>
          <!-- /.info-box -->
          <div class="info-box bg-green">
            <span class="info-box-icon"><i class="ion ion-ios-heart-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">昨日新增订单</span>
              <span class="info-box-number">${memberIncreased.orderCount}</span>
            </div>
            <!-- /.info-box-content -->
          </div>
          <!-- /.info-box -->
          <div class="info-box bg-red">
            <span class="info-box-icon"><i class="ion ion-ios-cloud-download-outline"></i></span>

            <div class="info-box-content">
              <span class="info-box-text">昨日新增用户</span>
              <span class="info-box-number">${memberIncreased.addUserCount}</span>
            </div>
          </div>
          <div class="info-box bg-aqua">
            <span class="info-box-icon"><i class="ion-ios-chatbubble-outline"></i></span>
            <div class="info-box-content">
              <span class="info-box-text">昨日用户访问量</span>
              <span class="info-box-number">${memberIncreased.visitMemberCount}</span>
            </div>
          </div>
        </div>
        <!-- /.col --> --%>
    <div class="row">
        <div class="col-md-4">
           <div class="box box-primary">
             <div class="box-header with-border">
               <h3 class="box-title">系统日报</h3>

               <div class="box-tools pull-right">
                 <span class="label label-danger">报表明细</span>
                 <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                 </button>
                 <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i>
                 </button>
               </div>
             </div>
             <!-- /.box-header -->
             <div class="box-body no-padding">
               <ul class="users-list clearfix">
                 <li>
                   <img src="${ctxStatic}/adminlte/dist/img/newshouru.jpg" alt="User Image">
                   <a class="users-list-name" href="#">昨日新增收入</a>
                   <span class="users-list-date">${empty memberIncreased.selMoney?0:memberIncreased.selMoney}</span>
                 </li>
                 <li>
                   <img src="${ctxStatic}/adminlte/dist/img/neworder.jpg" alt="User Image">
                   <a class="users-list-name" href="#">昨日新增订单</a>
                   <span class="users-list-date">${empty memberIncreased.orderCount?0:memberIncreased.orderCount}</span>
                 </li>
                 <li>
                   <img src="${ctxStatic}/adminlte/dist/img/newadduser.jpg" alt="User Image">
                   <a class="users-list-name" href="#">昨日新增用户</a>
                   <span class="users-list-date">${empty memberIncreased.addUserCount?0:memberIncreased.addUserCount}</span>
                 </li>
                 <li>
                   <img src="${ctxStatic}/adminlte/dist/img/newliuliang.jpg" alt="User Image">
                   <a class="users-list-name" href="#">昨日访问量</a>
                   <span class="users-list-date">${empty memberIncreased.visitMemberCount?0:memberIncreased.visitMemberCount}</span>
                 </li>
               
               </ul>
               <!-- /.users-list -->
             </div>
             <!-- /.box-body 
             <div class="box-footer text-center">
               <a href="javascript:void(0)" class="uppercase">查询所有数据</a>
             </div>-->
             <!-- /.box-footer -->
           </div>
           <!--/.box -->
         </div>
        <div id="increaseChart" style="width: 600px;height:400px;" class="col-md-8"></div>
		<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('increaseChart'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '昨日热销产品统计'
            },
            tooltip: {},
            legend: {
                data:['昨日热销产品统计']
            },
            xAxis: {
                data: ["纯棉床单..","白色绣..","多色梅..","外贸纯..","浅色纯棉..","纯棉绣.."]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [10, 20, 36, 10, 10, 20]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
       </div> 
          
          </div>
      <!-- /.row -->
    </section>
</body>
</html>