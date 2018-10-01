<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户搜索记录管理</title>
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
		<li class="active"><a href="${ctx}/member/wsMemberSearchLog/">用户搜索记录列表</a></li>
		<shiro:hasPermission name="member:wsMemberSearchLog:edit"><li><a href="${ctx}/member/wsMemberSearchLog/form">用户搜索记录添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsMemberSearchLog" action="${ctx}/member/wsMemberSearchLog/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">用户名：</label>
								<div class="col-sm-8">
									<form:input path="wsMember" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">搜索记录：</label>
								<div class="col-sm-8">
									<form:input path="searchLable" htmlEscape="false" maxlength="2000" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">用户名</th>
							<th class="text-center">搜索记录</th>
							<th class="text-center">最近搜索日期</th>
							<th class="text-center">搜索次数</th>
							<th class="text-center">update_date</th>
							<th class="text-center">标记</th>
							<shiro:hasPermission name="member:wsMemberSearchLog:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsMemberSearchLog">
						<tr>
							<td class="text-center"><a href="${ctx}/member/wsMemberSearchLog/form?id=${wsMemberSearchLog.id}">
								${wsMemberSearchLog.wsMember}
							</a></td>
							<td class="text-center">
								${wsMemberSearchLog.searchLable}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsMemberSearchLog.lastSearchDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsMemberSearchLog.searchNum}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsMemberSearchLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsMemberSearchLog.remarks}
							</td>
							<shiro:hasPermission name="member:wsMemberSearchLog:edit"><td>
			    				<a href="${ctx}/member/wsMemberSearchLog/form?id=${wsMemberSearchLog.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/member/wsMemberSearchLog/delete?id=${wsMemberSearchLog.id}" onclick="return confirmx('确认要删除该用户搜索记录吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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