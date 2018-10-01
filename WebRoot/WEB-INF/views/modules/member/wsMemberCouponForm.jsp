<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户优惠券管理</title>
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
		<li><a href="${ctx}/member/wsMemberCoupon/">用户优惠券列表</a></li>
		<li class="active"><a href="${ctx}/member/wsMemberCoupon/form?id=${wsMemberCoupon.id}">用户优惠券<shiro:hasPermission name="member:wsMemberCoupon:edit">${not empty wsMemberCoupon.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="member:wsMemberCoupon:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsMemberCoupon" action="" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">
						<span class="help-inline"><font color="red">*</font> </span>用户名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="wsMember.username" htmlEscape="false" maxlength="64" class="form-control required" readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">
						<span class="help-inline"><font color="red">*</font> </span>优惠券标题：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="wsActivityCoupon.title" htmlEscape="false" maxlength="64" class="form-control required" readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">优惠金额：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="couponMoney" htmlEscape="false" maxlength="11" class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">满足多少金额优惠：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="conditionMoney" htmlEscape="false" maxlength="11" class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">起始时间：
					</label>
					<div class="col-sm-4 controls">
						<input name="startTime" type="text"  readonly="readonly"  maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${wsMemberCoupon.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">结束时间：
					</label>
					<div class="col-sm-4 controls">
						<input name="endTime" type="text" readonly="readonly" maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${wsMemberCoupon.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">状态：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="state" class="form-control select2" disabled="true">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('coupon_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>	
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">支付时间：
					</label>
					<div class="col-sm-4 controls">
						<input name="useTime" type="text" readonly="readonly" maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${wsMemberCoupon.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">订单流水号：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="wsOrder.orderSn" htmlEscape="false" maxlength="200" class="form-control " readonly="true"/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>