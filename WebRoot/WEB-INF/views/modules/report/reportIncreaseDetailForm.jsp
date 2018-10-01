<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>每日新增数据明细管理</title>
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/report/reportIncreaseDetail/">每日新增数据明细列表</a></li>
		<li class="active"><a href="${ctx}/report/reportIncreaseDetail/form?id=${reportIncreaseDetail.id}">每日新增数据明细<shiro:hasPermission name="report:reportIncreaseDetail:edit">${not empty reportIncreaseDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="report:reportIncreaseDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="reportIncreaseDetail" action="${ctx}/report/reportIncreaseDetail/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">统计日期：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="countDate" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">新增用户数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="addUserCount" htmlEscape="false" maxlength="11" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">新增收入：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="selMoney" htmlEscape="false" class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">订单数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="orderCount" htmlEscape="false" maxlength="11" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">退款金额：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="backMoney" htmlEscape="false" class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">访问用户：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="visitMemberCount" htmlEscape="false" maxlength="255" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">备注信息：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="report:reportIncreaseDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>