<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信账号配置管理</title>
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
		<li class="active"><a href="${ctx}/ws/wxAccount/form?id=${wxAccount.id}">微信账号配置<shiro:lacksPermission name="ws:wxAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wxAccount" action="${ctx}/ws/wxAccount/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-4 control-label">公众帐号名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="accountName" htmlEscape="false" maxlength="200" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">公众微信号：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="accountNumber" htmlEscape="false" maxlength="200" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">公众号类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="accountType" class="form-control ">
							<form:options items="${fns:getDictList('wx_account_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">公众帐号TOKEN：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="accountToken" htmlEscape="false" maxlength="200" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">公众帐号APPID：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="accountAppid" htmlEscape="false" maxlength="200" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">公众帐号APPSECRET：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="accountAppsecret" htmlEscape="false" maxlength="500" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">小程序APPID：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="wcxAppid" htmlEscape="false" maxlength="200" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">小程序APPSECRET：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="wcxAppsecret" htmlEscape="false" maxlength="500" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">微信支付商户号：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="mchId" htmlEscape="false" maxlength="500" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">微信支付密钥：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="payKey" htmlEscape="false" maxlength="500" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">微信支付证书地址：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="certPath" htmlEscape="false" maxlength="500" class="form-control "/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-5 control-label"></label>
					<shiro:hasPermission name="ws:wxAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>