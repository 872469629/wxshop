<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员等级管理</title>
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
		<li><a href="${ctx}/config/wsMrank/">会员等级列表</a></li>
		<li class="active"><a href="${ctx}/config/wsMrank/form?id=${wsMrank.id}">会员等级<shiro:hasPermission name="config:wsMrank:edit">${not empty wsMrank.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="config:wsMrank:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsMrank" action="${ctx}/config/wsMrank/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-4 control-label">级别名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">折扣(价格*折扣为实际付费价格)：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="scale" htmlEscape="false" class="form-control maxNumber required" min="0" max="1"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">是否默认级别：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="isDefault" class="form-control select2">
							<form:options items="${fns:getDictList('no_yes')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>	
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">需要积分：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="amount" htmlEscape="false" maxlength="11" class="form-control digits required " />
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">备注：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="config:wsMrank:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>