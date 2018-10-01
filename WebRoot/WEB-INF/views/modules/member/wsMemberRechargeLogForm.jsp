<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户充值记录管理</title>
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
		<li><a href="${ctx}/member/wsMemberRechargeLog/">用户充值记录列表</a></li>
		<li class="active"><a href="${ctx}/member/wsMemberRechargeLog/form?id=${wsMemberRechargeLog.id}">用户充值记录<shiro:hasPermission name="member:wsMemberRechargeLog:edit">${not empty wsMemberRechargeLog.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="member:wsMemberRechargeLog:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsMemberRechargeLog" action="${ctx}/member/wsMemberRechargeLog/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">
						<span class="help-inline"></span>用户昵称：
					</label>
					<div class="col-sm-4 controls">
						<form:input readonly="true" path="wsMember.nickname"  htmlEscape="false" maxlength="64" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">充值金额：
					</label>
					<div class="col-sm-4 controls">
						<form:input readonly="true" path="rechargeMoney" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">充值时间：
					</label>
					<div class="col-sm-4 controls">
						<input readonly="true" name="rechargeTime" type="text" readonly="readonly" maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${wsMemberRechargeLog.rechargeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">剩余积分：
					</label>
					<div class="col-sm-4 controls">
						<form:input readonly="true" path="score" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">剩余金额：
					</label>
					<div class="col-sm-4 controls">
						<form:input readonly="true" path="balance" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">付款方式：
					</label>
					<div class="col-sm-4 controls">
						<form:input readonly="true" path="payment" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">标记：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea readonly="true" path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
<%-- 					<shiro:hasPermission name="member:wsMemberRechargeLog:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>