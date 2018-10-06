<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商分销配置管理</title>
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
		<li><a href="${ctx}/rebate/wsAgentRebateConfig/">代理商分销配置列表</a></li>
		<li class="active"><a href="${ctx}/rebate/wsAgentRebateConfig/form?id=${wsAgentRebateConfig.id}">代理商分销配置<shiro:hasPermission name="rebate:wsAgentRebateConfig:edit">${not empty wsAgentRebateConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rebate:wsAgentRebateConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsAgentRebateConfig" action="${ctx}/rebate/wsAgentRebateConfig/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="type" class="form-control " id="typeButton">
						<c:if test="${not empty wsAgentRebateConfig.id }">
							<c:if test="${wsAgentRebateConfig.type=='1' }">
								<form:option value="1" label="按比例" />
								<form:option value="2" label="按固定金额"/>
							</c:if>
							<c:if test="${wsAgentRebateConfig.type=='2' }">
								<form:option value="2" label="按固定金额" />
								<form:option value="1" label="按比例" />
							</c:if>
						</c:if>
						<c:if test="${empty wsAgentRebateConfig.id }">
							<form:option value="1" label="按比例"/>
							<form:option value="2" label="按固定金额"/>
						</c:if>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">一级消费返利：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level1proportion" htmlEscape="false" class="form-control "/>
					</div>
					<div class="col-sm-1 controls">
						<span class="typeSpan"></span>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">二级消费返利：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level2proportion" htmlEscape="false" class="form-control "/>
					</div>
					<div class="col-sm-1 controls">
						<span class="typeSpan"></span>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">三级消费返利：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level3proportion" htmlEscape="false" class="form-control "/>
					</div>
					<div class="col-sm-1 controls">
						<span class="typeSpan"></span>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">状态：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="status" class="form-control ">
							<form:option value="1" label="是"/>
							<form:option value="0" label="否"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">一级推广返利：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level1promotion" htmlEscape="false" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">二级推广返利：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level2promotion" htmlEscape="false" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">三级推广返利：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="level3promotion" htmlEscape="false" class="form-control "/>
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
					<shiro:hasPermission name="rebate:wsAgentRebateConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
   <script type="text/javascript">
   	$(function(){
   		if($("#typeButton").val()=='1'){
   			$(".typeSpan").text("%")
   		}else{
			$(".typeSpan").text("")
		}
   		
   		$("#typeButton").change(function(){
   			if($(this).val()=='1'){
   				$(".typeSpan").text("%")
   			}else{
   				$(".typeSpan").text("")
   			}
   		})
   	})
   </script>
</body>
</html>