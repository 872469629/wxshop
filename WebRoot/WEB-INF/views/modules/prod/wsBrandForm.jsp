<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌管理</title>
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
		<li><a href="${ctx}/prod/wsBrand/">品牌列表</a></li>
		<li class="active"><a href="${ctx}/prod/wsBrand/form?id=${wsBrand.id}">品牌<shiro:hasPermission name="prod:wsBrand:edit">${not empty wsBrand.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prod:wsBrand:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsBrand" action="${ctx}/prod/wsBrand/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">中文名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="cnname" htmlEscape="false" maxlength="64" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">英文名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="enname" htmlEscape="false" maxlength="64" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">描述：
					</label>
					<div class="col-sm-4 controls">
					<form:textarea path="description" htmlEscape="false" rows="4" maxlength="255" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">logo：
					</label>
				<!--  	
					<div class="col-sm-4 controls">
						<form:input path="logo" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
					-->
					<div class="col-sm-4 controls">
<%-- 		                <input type="hidden" id="image" name="logo" value="${wsBrand.logo}" /> --%>
<%-- 						<sys:ckfinder input="image" type="thumb" uploadPath="/cms/article" selectMultiple="false"/> --%>
					
					<input type="hidden" id="image" name="logo" value="${wsBrand.logo}"/> 
					<sys:kindUpload input="image" type="image" selectMultiple="false"></sys:kindUpload>
					
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">状态：
					</label>
					<div class="col-sm-4 controls">
<%-- 						<form:input path="state" htmlEscape="false" maxlength="10" class="form-control "/> --%>
						<form:radiobuttons path="state" items="${fns:getDictList('wsstate')}" itemLabel="label" itemValue="label" htmlEscape="false" class=""/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">官网地址：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="websiteurl" htmlEscape="false" maxlength="255" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">品牌故事：
					</label>
					<div class="col-sm-8 controls">
<%-- 						<form:textarea path="brandstory" htmlEscape="false" rows="4" maxlength="255" class="form-control "/> --%>
					<textarea name="brandstory" id="editor" cols="100" rows="8"  style="width:700px;height:400px;visibility:hidden;">${wsBrand.brandstory}</textarea>
						<sys:kindEditor replace="editor"  />
				
					</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label  class="col-sm-2 control-label">标记： -->
<!-- 					</label> -->
<!-- 					<div class="col-sm-4 controls"> -->
<%-- 						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="prod:wsBrand:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>