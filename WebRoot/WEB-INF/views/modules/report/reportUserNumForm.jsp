<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户数据统计管理</title>
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
		<li><a href="${ctx}/report/reportUserNum/">用户数据统计列表</a></li>
		<li class="active"><a href="${ctx}/report/reportUserNum/form?id=${reportUserNum.id}">用户数据统计<shiro:hasPermission name="report:reportUserNum:edit">${not empty reportUserNum.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="report:reportUserNum:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="reportUserNum" action="${ctx}/report/reportUserNum/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">统计日期：
					</label>
					<div class="col-sm-4 controls">
						<input name="countDate" type="text" readonly="readonly" maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${reportUserNum.countDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">用户数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="userCount" htmlEscape="false" maxlength="11" class="form-control  digits"/>
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
					<label  class="col-sm-2 control-label">消费用户数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="selUserCount" htmlEscape="false" maxlength="11" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">访问人数：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="visitUserCount" htmlEscape="false" maxlength="11" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">用户等级：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="mrankNanme" htmlEscape="false" maxlength="50" class="form-control "/>
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
					<shiro:hasPermission name="report:reportUserNum:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>