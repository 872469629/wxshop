<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>链接类型表管理</title>
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
		<li class="active"><a href="${ctx}/cms/wsLinkCategory/">链接类型表列表</a></li>
		<shiro:hasPermission name="cms:wsLinkCategory:edit"><li><a href="${ctx}/cms/wsLinkCategory/form">链接类型表添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsLinkCategory" action="${ctx}/cms/wsLinkCategory/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">分类名称：</label>
								<div class="col-sm-8">
									<form:input path="name" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">状态：</label>
								<div class="col-sm-8">
									<form:select id="type" path="state" class="form-control">
										<form:option value="" label="所有"/>
										<form:options items="${fns:getDictList('wsstate')}" itemValue="value" itemLabel="label" htmlEscape="false"/>
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
							<th class="text-center">类型名称</th>
							<th class="text-center">状态</th>
							<th class="text-center">标记</th>
							<shiro:hasPermission name="cms:wsLinkCategory:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="wsLinkCategory">
						<tr>
							<td class="text-center"><a href="${ctx}/cms/wsLinkCategory/form?id=${wsLinkCategory.id}">
								${wsLinkCategory.name}
							</a></td>
							<td class="text-center">
								${fns:getDictLabel(wsLinkCategory.state, 'wsstate', '')}
							</td>
							<td class="text-center">
								${wsLinkCategory.remarks}
							</td>
							<shiro:hasPermission name="cms:wsLinkCategory:edit"><td>
			    				<a href="${ctx}/cms/wsLinkCategory/form?id=${wsLinkCategory.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/cms/wsLinkCategory/delete?id=${wsLinkCategory.id}" onclick="return confirmx('确认要删除该链接类型表吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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