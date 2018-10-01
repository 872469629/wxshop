<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频素材管理</title>
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
		<li><a href="${ctx}/ws/wxMaterialVideo/">视频素材列表</a></li>
		<li class="active"><a href="${ctx}/ws/wxMaterialVideo/form?id=${wxMaterialVideo.id}">视频素材<shiro:hasPermission name="ws:wxMaterialVideo:edit">${not empty wxMaterialVideo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ws:wxMaterialVideo:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wxMaterialVideo" action="${ctx}/ws/wxMaterialVideo/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">视频标题：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="title" htmlEscape="false" maxlength="255" class="form-control "/>
						<span class="help-inline">视频大小不超过20M素材管理接口每天只能调用十次。</span>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">视频上传：
					</label>
					<div class="col-sm-4 controls">
						<input type="hidden" id="videoUrl" name="videoUrl" value="${wxMaterialVideo.videoUrl}"/> 
						<sys:kindUpload input="videoUrl" type="media" selectMultiple="false"></sys:kindUpload>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">视频描述：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="description" htmlEscape="false" rows="5" maxlength="255" class="form-control "/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="ws:wxMaterialVideo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>