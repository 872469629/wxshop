<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购物车管理管理</title>
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
		<li><a href="${ctx}/prod/wsCart/">购物车管理列表</a></li>
		<li class="active"><a href="${ctx}/prod/wsCart/form?id=${wsCart.id}">购物车管理<shiro:hasPermission name="prod:wsCart:edit">${not empty wsCart.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prod:wsCart:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsCart" action="${ctx}/prod/wsCart/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">用户：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="memberId" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">购物车产品：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="productId" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">产品sku：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="skuId" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">主图：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="thumb" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="quantity" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">活动id：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="activityId" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">优惠金额=通过活动后优惠的金额：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="discountAmount" htmlEscape="false" maxlength="11" class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">单价：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="unitPrice" htmlEscape="false" maxlength="11" class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">总价格=数量和单价的乘积：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="price" htmlEscape="false" maxlength="11" class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">购物车商品状态0失效1生效：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="state" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
					<shiro:hasPermission name="prod:wsCart:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>