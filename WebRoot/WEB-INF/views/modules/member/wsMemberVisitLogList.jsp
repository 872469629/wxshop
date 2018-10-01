<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户访问记录管理</title>
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
		<li class="active"><a href="${ctx}/member/wsMemberVisitLog/">用户访问记录列表</a></li>
		<shiro:hasPermission name="member:wsMemberVisitLog:edit"><li><a href="${ctx}/member/wsMemberVisitLog/form">用户访问记录添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsMemberVisitLog" action="${ctx}/member/wsMemberVisitLog/" method="post" class="form-horizontal">
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
								<label class="col-sm-4 control-label">产品：</label>
								<div class="col-sm-8">
									<form:input path="wsProduct" htmlEscape="false" maxlength="64" class="form-control"/>
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
							<th class="text-center">产品</th>
							<th class="text-center">最近搜索日期</th>
							<th class="text-center">访问次数</th>
							<th class="text-center">update_date</th>
							<th class="text-center">标记</th>
							<shiro:hasPermission name="member:wsMemberVisitLog:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsMemberVisitLog">
						<tr>
							<td class="text-center"><a href="${ctx}/member/wsMemberVisitLog/form?id=${wsMemberVisitLog.id}">
								${wsMemberVisitLog.wsMember}
							</a></td>
							<td class="text-center">
								${wsMemberVisitLog.wsProduct}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsMemberVisitLog.lastVisitDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsMemberVisitLog.visitNum}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsMemberVisitLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsMemberVisitLog.remarks}
							</td>
							<shiro:hasPermission name="member:wsMemberVisitLog:edit"><td>
			    				<a href="${ctx}/member/wsMemberVisitLog/form?id=${wsMemberVisitLog.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/member/wsMemberVisitLog/delete?id=${wsMemberVisitLog.id}" onclick="return confirmx('确认要删除该用户访问记录吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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