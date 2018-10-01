<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品属性管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/prod/wsProdAttribute/">产品属性列表</a></li>
		<shiro:hasPermission name="prod:wsProdAttribute:edit"><li><a href="${ctx}/prod/wsProdAttribute/form">产品属性添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsProdAttribute" action="${ctx}/prod/wsProdAttribute/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">所属分类：</label>
										
						 <div class="col-sm-8 controls">
						<sys:treeselect id="prodCategoryId" name="prodCategoryId.id" value="${wsProdAttribute.prodCategoryId.id}" labelName="prodCategoryId.name" labelValue="${wsProdAttribute.prodCategoryId.name}"
							title="父类" url="/prod/wsProdCategory/treeData" extId="${wsProdCategory.id}" module="wsProdAttribute" cssClass="form-control" allowClear="true"/>
					        </div>
								
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">属性名称：</label>
								<div class="col-sm-8">
									<form:input path="attrName" htmlEscape="false" maxlength="128" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">属性类型：</label>
								<div class="col-sm-8">
									<form:select path="attrType" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('att_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">所属分类</th>
							<th class="text-center">属性名称</th>
							<th class="text-center">属性类型</th>
							<th class="text-center">是否搜索</th>
							<th class="text-center">更新时间</th>
							<shiro:hasPermission name="prod:wsProdAttribute:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsProdAttribute">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsProdAttribute/form?id=${wsProdAttribute.id}">
								${wsProdAttribute.prodCategoryId.name}
							</a></td>
							<td class="text-center">
								${wsProdAttribute.attrName}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProdAttribute.attrType, 'att_type', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProdAttribute.isSearch, 'yes_no', '')}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsProdAttribute.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<shiro:hasPermission name="prod:wsProdAttribute:edit"><td>
			    				<a href="${ctx}/prod/wsProdAttribute/form?id=${wsProdAttribute.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsProdAttribute/delete?id=${wsProdAttribute.id}" onclick="return confirmx('确认要删除该产品属性吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
							</td></shiro:hasPermission>
						</tr>
					</c:forEach>
				</table>
					${page}					 
				</div>
			</div>
		</div>
	</section>
</body>
</html>