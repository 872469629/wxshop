<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>底部链接管理</title>
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
		<li><a href="${ctx}/cms/wsLink/">底部链接列表</a></li>
		<li class="active"><a href="${ctx}/cms/wsLink/form?id=${wsLink.id}">底部链接<shiro:hasPermission name="cms:wsLink:edit">${not empty wsLink.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:wsLink:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsLink" action="${ctx}/cms/wsLink/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="name" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">分类：
					</label>
					<!-- 
					<div class="col-sm-4 controls">
						<form:input path="linkCategoryId" htmlEscape="false" maxlength="100" class="form-control "/>
					</div>
					 -->
					<div class="col-sm-4 controls">
<!-- 				<select  name="linkCategoryId.id" id="linkCategoryId" style="vertical-align:top;"> -->
<!-- 					<option value="">请选择</option> -->
<%-- 					<c:forEach items="${linkcategorylist}" var="b"> --%>
<%-- 						<option value="${b.id}" <c:if test="${b.id==wsLink.linkCategoryId.id}"> selected="selected" </c:if> >${b.name}</option> --%>
<%-- 					</c:forEach> --%>
<!-- 				</select> -->
     	
			            <form:select path="linkCategoryId.id" class="form-control ">
							<form:option value="" label="请选择"/>
							<form:options items="${linkcategorylist}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>

			</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">链接地址：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="linkHref" htmlEscape="false" maxlength="100" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">状态：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="state" class="form-control ">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('wsstate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
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
					<shiro:hasPermission name="cms:wsLink:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>