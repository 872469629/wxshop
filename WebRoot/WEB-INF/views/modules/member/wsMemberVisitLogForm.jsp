<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户访问记录管理</title>
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
		<li><a href="${ctx}/member/wsMemberVisitLog/">用户访问记录列表</a></li>
		<li class="active"><a href="${ctx}/member/wsMemberVisitLog/form?id=${wsMemberVisitLog.id}">用户访问记录<shiro:hasPermission name="member:wsMemberVisitLog:edit">${not empty wsMemberVisitLog.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="member:wsMemberVisitLog:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsMemberVisitLog" action="${ctx}/member/wsMemberVisitLog/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">
						<span class="help-inline"><font color="red">*</font> </span>用户名：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="wsMember" htmlEscape="false" maxlength="64" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">产品：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="wsProduct" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">最近搜索日期：
					</label>
					<div class="col-sm-4 controls">
						<input name="lastVisitDate" type="text" readonly="readonly" maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${wsMemberVisitLog.lastVisitDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">访问次数：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="visitNum" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">标记：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="member:wsMemberVisitLog:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>