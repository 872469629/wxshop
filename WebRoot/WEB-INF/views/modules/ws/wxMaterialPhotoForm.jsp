<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片素材管理</title>
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
		<li><a href="${ctx}/ws/wxMaterialPhoto/">图片素材列表</a></li>
		<li class="active"><a href="${ctx}/ws/wxMaterialPhoto/form?id=${wxMaterialPhoto.id}">图片素材<shiro:hasPermission name="ws:wxMaterialPhoto:edit">${not empty wxMaterialPhoto.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ws:wxMaterialPhoto:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wxMaterialPhoto" action="${ctx}/ws/wxMaterialPhoto/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">图片标题：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="title" htmlEscape="false" maxlength="255" class="form-control "/>
						<span class="help-inline">图片大小不超过2M素材管理接口每天只能调用十次。</span>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">图片：
					</label>
					<div class="col-sm-4 controls">
						<input type="hidden" id="imgUrl" name="imgUrl" value="${wxMaterialPhoto.imgUrl}"/> 
						<sys:kindUpload input="imgUrl" type="image" selectMultiple="false"></sys:kindUpload>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="ws:wxMaterialPhoto:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>