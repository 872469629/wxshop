<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关注欢迎语管理</title>
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
		function showInfo(){
			var msgType=$("#msgType").val();
			layer.open({
				  type: 2,
				  skin: 'layui-layer-rim', //加上边框
				  area: ['800px', '500px'], //宽高
				  content: '${ctx}/ws/wxSubscribe/templateForm?msgType='+msgType,
				  btn: ['关闭']
			});
		}
		function selectTemplate(id,title){
			$("#materialTitle").val(title);
			$("#materialId").val(id);
			layer.closeAll();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ws/wxSubscribe/">关注欢迎语列表</a></li>
		<li class="active"><a href="${ctx}/ws/wxSubscribe/form?id=${wxSubscribe.id}">关注欢迎语<shiro:hasPermission name="ws:wxSubscribe:edit">${not empty wxSubscribe.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ws:wxSubscribe:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wxSubscribe" action="${ctx}/ws/wxSubscribe/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">消息类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="msgType" class="form-control ">
							<form:options items="${fns:getDictList('msg_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">素材名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="materialTitle" htmlEscape="false" maxlength="255" class="form-control required" readonly="true"/>
						<form:hidden path="materialId" htmlEscape="false" />
					</div>
					<div class="col-sm-4 controls">
						<a onclick="showInfo();"  class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;选择模板</i></span></a>
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
					<shiro:hasPermission name="ws:wxSubscribe:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>