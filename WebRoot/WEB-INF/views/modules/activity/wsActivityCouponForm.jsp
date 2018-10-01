<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券活动管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("#prodType").change(function(){
				changeProdType();
			});
			changeProdType();
		});
		function changeProdType(){
			if($("#prodType").val()=='2'){
				$("#prodRelDiv").css('display','block');
			}else{
				$("#prodRelDiv").css('display','none');
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/activity/wsActivityCoupon/">优惠券活动列表</a></li>
		<li class="active"><a href="${ctx}/activity/wsActivityCoupon/form?id=${wsActivityCoupon.id}">优惠券活动<shiro:hasPermission name="activity:wsActivityCoupon:edit">${not empty wsActivityCoupon.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="activity:wsActivityCoupon:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsActivityCoupon" action="${ctx}/activity/wsActivityCoupon/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	优惠券基本信息
		          	</h2>
		        	</div>
		      	</div>	
				<div class="form-group">
					<label  class="col-sm-2 control-label">优惠券名称：
					</label>
					<div class="col-sm-10 controls">
						<form:input path="title" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">面值：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="faceValue" htmlEscape="false" maxlength="11" class="form-control  required number maxNumber"/>
					</div>
					<label  class="col-sm-2 control-label">订单金额满足多少才能使用：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="orderMoney" htmlEscape="false" maxlength="11" class="form-control  required number maxNumber"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">优惠券总量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="countNum" htmlEscape="false" maxlength="11" class="form-control  digits required"/>
					</div>
					<label  class="col-sm-2 control-label">已发放数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="surplusNum" htmlEscape="false" maxlength="10" class="form-control  digits "  readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">产品类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="prodType" class="form-control ">
							<form:options items="${fns:getDictList('prod_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<label  class="col-sm-2 control-label">兑换积分：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="score" htmlEscape="false" maxlength="10" class="form-control  digits "  />
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动结束时间：
					</label>
					<div class="col-sm-4 controls">
						<input name="endtime" type="text" readonly="readonly" maxlength="20" class="form-control Wdate required"
							value="<fmt:formatDate value="${wsActivityCoupon.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</div>
					<label  class="col-sm-2 control-label">活动开始时间：
					</label>
					<div class="col-sm-4 controls">
						<input name="starttime" type="text" readonly="readonly" maxlength="20" class="form-control Wdate required"
							value="<fmt:formatDate value="${wsActivityCoupon.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">优惠券详细描述：
					</label>
					<div class="col-sm-8 controls">
						<textarea name="couponContent" id="editor" cols="100" rows="8" style="width:700px;height:400px;visibility:hidden;">${wsActivityCoupon.couponContent}</textarea>
						<sys:kindEditor replace="editor"  />	
					</div>
				</div>
					<div class="form-group" style="display: none" id="prodRelDiv">
						<div class="row">
				        	<div class="col-xs-12">
				          	<h2 class="page-header">
				           	 	优惠券指定产品
				          	</h2>
				        	</div>
				      	</div>
						<label  class="col-sm-2 control-label"></label>
						<div class="col-sm-10 controls">
							
		 <div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<form:hidden path="prodRelation" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<ol id="prodSelectList"></ol>
				<a id="relationButton" href="javascript:" class="btn btn-success btn-sm">添加活动商品</a>
				<script type="text/javascript">
					var prodSelect = [];
					function prodSelectAddOrDel(id,title){
						var isExtents = false, index = 0;
						for (var i=0; i<prodSelect.length; i++){
							if (prodSelect[i][0]==id){
								isExtents = true;
								index = i;
							}
						}
						if(isExtents){
							prodSelect.splice(index,1);
						}else{
							prodSelect.push([id,title]);
						}
						prodSelectRefresh();
					}
					function prodSelectRefresh(){
						$("#prodRelation").val("");
						$("#prodSelectList").children().remove();
						for (var i=0; i<prodSelect.length; i++){
							$("#prodSelectList").append("<li>"+prodSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"prodSelectAddOrDel('"+prodSelect[i][0]+"','"+prodSelect[i][1]+"');\">×</a></li>");
							$("#prodRelation").val($("#prodRelation").val()+prodSelect[i][0]+",");
						}
					}
					$.getJSON("${ctx}/prod/wsProduct/findByIds",{ids:$("#prodRelation").val()},function(data){
						for (var i=0; i<data.length; i++){
							prodSelect.push([data[i][1],data[i][2]]);
						}
						prodSelectRefresh();
					});
					$("#relationButton").click(function(){
						layer.open({
							  type: 2,
							  title: "选择活动商品",
							  shadeClose: true,
							  shade: 0.3,
							  area: [($(top.document).width()-320)+"px",($(top.document).height()-180)+"px"],
							  content: "${ctx}/prod/wsProduct/prodSelectList?pageSize=8",
							  btn: ['确定'],
							  yes: function(index, layero){
								  layer.close(index);
							  },
						});
					});
				</script>
			</div>
		</div>
							
							

						</div>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-5 control-label"></label>
					<shiro:hasPermission name="activity:wsActivityCoupon:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>