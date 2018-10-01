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
		<li class="active"><a href="${ctx}/prod/wsAttribute/">产品属性列表</a></li>
		<shiro:hasPermission name="prod:wsAttribute:edit"><li><a href="${ctx}/prod/wsAttribute/form">产品属性添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsAttribute" action="${ctx}/prod/wsAttribute/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">所属分类：</label>
								
								
						 <div class="col-sm-8 controls">
						<sys:treeselect id="prodCategoryId" name="prodCategoryId.id" value="${wsAttribute.prodCategoryId.id}" labelName="prodCategoryId.name" labelValue="${wsAttribute.prodCategoryId.name}"
							title="父类" url="/prod/wsProdCategory/treeData" extId="${wsProdCategory.id}" module="wsAttribute" cssClass="form-control" allowClear="true"/>
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
					<thead>
						<tr>
							<th class="text-center">所属分类</th>
							<th class="text-center">属性名称</th>
							<th class="text-center">属性类型</th>
							<th class="text-center">是否搜索</th>
							<th class="text-center">是否必填</th>
							<th class="text-center">状态</th>
							<th class="text-center">排序</th>
							<th class="text-center">update_date</th>
							<th class="text-center">remarks</th>
							<shiro:hasPermission name="prod:wsAttribute:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="wsAttribute">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsAttribute/form?id=${wsAttribute.id}">
								${wsAttribute.prodCategoryId.name}
							</a></td>
							<td class="text-center">
								${wsAttribute.attrName}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsAttribute.attrType, 'att_type', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsAttribute.isSearch, 'yes_no', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsAttribute.isRequire, 'yes_no', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsAttribute.state, 'yes_no', '')}
							</td>
							<td class="text-center">
								${wsAttribute.sort}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsAttribute.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsAttribute.remarks}
							</td>
							<shiro:hasPermission name="prod:wsAttribute:edit"><td>
			    				<a href="${ctx}/prod/wsAttribute/form?id=${wsAttribute.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsAttribute/delete?id=${wsAttribute.id}" onclick="return confirmx('确认要删除该产品属性吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
							</td></shiro:hasPermission>
						</tr>
					</c:forEach>
					</tbody>
				</table>
					${page}					 
				</div>
			</div>
		</div>
	</section>
</body>
</html>