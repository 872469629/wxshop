<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加备注</title>
	<meta name="decorator" content="adminlte"/>
	<script src="${ctxStatic}/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmitSend").click(function(){
 			 	$("#inputForm").submit();  
			});
		});
		
	
	</script>
</head>
<body>

	<section class="content" style="background: redl;padding: 0px">
	  <div class="col-md-12">
 <div class="row">

    <div class="box box-primary">
 	<form:form id="inputForm" modelAttribute="wsOrder" action="${ctx}/order/wsOrder/saveRemark" method="post" class="form-horizontal">
     	<div class="form-group" style="display:none;">
			<label  class="col-sm-2 control-label">订单：
			</label>
			<div class="col-sm-4 controls">
				<form:input path="id" htmlEscape="false" maxlength="64" class="form-control "/>
			</div>
		</div>
		<div class="form-group">
			<label  class="col-sm-2 control-label">备注：
			</label>
			<div class="col-sm-4 controls">
				<form:textarea path="remarks" htmlEscape="false" maxlength="64" rows="6" class="form-control "/>
			</div>
		</div>
		<div class="box-footer">
			<label class="col-sm-3 control-label"></label>
			<shiro:hasPermission name="order:wsOrder:edit"><input id="btnSubmitSend" class="btn btn-primary" type="button" value="确定"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	</div>
	</div>
	</div>
   </section>
</body>
</html>