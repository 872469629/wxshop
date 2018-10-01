<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员消息管理</title>
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
		function selectAllRank(){
			if($("#qb").attr('checked')){
				$("[name=wsMrankList]:checkbox").attr("checked", true);
			}else{
				$("[name=wsMrankList]:checkbox").attr("checked", false);
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/member/wsMessage/">会员消息列表</a></li>
		<li class="active"><a href="${ctx}/member/wsMessage/form?id=${wsMessage.id}">会员消息<shiro:hasPermission name="member:wsMessage:edit">${not empty wsMessage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="member:wsMessage:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsMessage" action="${ctx}/member/wsMessage/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">
				<div class="form-group">
					<label  class="col-sm-2 control-label">消息类型：
					</label>
					<div class="col-sm-8 controls">
						<form:select path="msgType" class="form-control select2">
							<form:options items="${fns:getDictList('ws_msg_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>	
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">标题：
					</label>
					<div class="col-sm-8 controls">
						<form:input path="title" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">消息内容：
					</label>
					<div class="col-sm-10 controls">
						<textarea name="msgContent" id="editor" cols="100" rows="8"  style="width:700px;height:400px;visibility:hidden;">${wsMessage.msgContent}</textarea>
						<sys:kindEditor replace="editor"  />
					</div>
				</div>
				<div class="form-group hide">
					<label  class="col-sm-2 control-label">备注：
					</label>
					<div class="col-sm-10 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
					<div class="form-group">
						<label  class="col-sm-2 control-label"><span class="help-inline"><font color="red">*</font> </span>接收会员：</label>
						<div class="col-sm-8 controls" id="control-label-left">
							<form:checkboxes path="wsMrankList" items="${wsMrankList}"
							itemLabel="name" itemValue="id" htmlEscape="false" class="required" /><br/>
							<span><input id="qb" onclick="selectAllRank()" name="qb" type="checkbox" value="全选"/><label for="wsMrankList">全选</label></span>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="member:wsMessage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>