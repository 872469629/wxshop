<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商分销配置管理</title>
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
		<li><a href="${ctx}/rebate/wsAgentRebateConfig/">代理商分销配置列表</a></li>
		<li class="active"><a href="${ctx}/rebate/wsAgentRebateConfig/form?id=${wsAgentRebateConfig.id}">代理商分销配置<shiro:hasPermission name="rebate:wsAgentRebateConfig:edit">${not empty wsAgentRebateConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rebate:wsAgentRebateConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsAgentRebateConfig" action="${ctx}/rebate/wsAgentRebateConfig/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">level1proportion：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level1proportion" htmlEscape="false" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">level2proportion：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level2proportion" htmlEscape="false" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">level3proportion：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level3proportion" htmlEscape="false" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">status：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="status" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">formulary1：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="formulary1" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">formulary2：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="formulary2" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">formulary3：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="formulary3" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">level1：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level1" htmlEscape="false" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">level2：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level2" htmlEscape="false" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">level3：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level3" htmlEscape="false" class="form-control "/>
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
					<shiro:hasPermission name="rebate:wsAgentRebateConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>