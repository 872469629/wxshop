<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动配置管理</title>
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
		<li><a href="${ctx}/config/wsActivity/">活动配置列表</a></li>
		<li class="active"><a href="${ctx}/config/wsActivity/form?id=${wsActivity.id}">活动配置<shiro:hasPermission name="config:wsActivity:edit">${not empty wsActivity.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="config:wsActivity:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsActivity" action="${ctx}/config/wsActivity/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动编号：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="code" htmlEscape="false" maxlength="11" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动标题：
					</label>
					<div class="col-sm-7 controls">
						<form:textarea rows="5" path="title" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="activityType" class="form-control ">
							<form:options items="${fns:getDictList('activity_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动内容：
					</label>
					<div class="col-sm-10 controls">
						<textarea name="activityContent" id="editor" cols="100" rows="8" style="width:700px;height:400px;visibility:hidden;">${wsActivity.activityContent}</textarea>
						<sys:kindEditor replace="editor"  />
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动实现类：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="implClass" htmlEscape="false" maxlength="200" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动界面：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="frontPage" htmlEscape="false" maxlength="200" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">优先级：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="priority" htmlEscape="false" maxlength="10" class="form-control  digits required"/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="config:wsActivity:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>