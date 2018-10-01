<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品分类管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
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
		<li><a href="${ctx}/prod/wsProdCategory/">产品分类列表</a></li>
		<li class="active"><a href="${ctx}/prod/wsProdCategory/form?id=${wsProdCategory.id}&parent.id=${wsProdCategoryparent.id}">产品分类<shiro:hasPermission name="prod:wsProdCategory:edit">${not empty wsProdCategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prod:wsProdCategory:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
      <div class="row">
        <div class="col-md-12">
          <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsProdCategory" action="${ctx}/prod/wsProdCategory/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>		
				<div class="box-body">
				<div class="form-group">
					<label  class="col-sm-2 control-label">上级父类:</label>
					<div class="col-sm-4 controls">
						<sys:treeselect id="parent" name="parent.id" value="${wsProdCategory.parent.id}" labelName="" labelValue="${wsProdCategory.parent.name}"
							title="父类" url="/prod/wsProdCategory/treeData" extId="${wsProdCategory.id}" cssClass="form-control" allowClear="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">分类名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">分类描述：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="title" htmlEscape="false" maxlength="500" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">图片地址：
					</label>
					<!-- 
					<div class="col-sm-4 controls">
						<form:input path="imageUrl" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
					 -->
					 
					<div class="col-sm-4 controls">
<%-- 		                <input type="hidden" id="image" name="imageUrl" value="${wsProdCategory.imageUrl}" /> --%>
<%-- 						<sys:ckfinder input="image" type="thumb" uploadPath="/cms/article" selectMultiple="false"/> --%>
					
						<input type="hidden" id="image" name="imageUrl" value="${wsProdCategory.imageUrl}"/> 
					<sys:kindUpload input="image" type="image" selectMultiple="false"></sys:kindUpload>
					
					
					</div>
					
					
					
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">排序：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="sort" htmlEscape="false" maxlength="100" class="form-control "/>
					</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label  class="col-sm-2 control-label">备注： -->
<!-- 					</label> -->
<!-- 					<div class="col-sm-4 controls"> -->
<%-- 						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="prod:wsProdCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>