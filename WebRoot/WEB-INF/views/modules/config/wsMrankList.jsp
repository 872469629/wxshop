<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员等级管理</title>
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
		<li class="active"><a href="${ctx}/config/wsMrank/">会员等级列表</a></li>
		<shiro:hasPermission name="config:wsMrank:edit"><li><a href="${ctx}/config/wsMrank/form">会员等级添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsMrank" action="${ctx}/config/wsMrank/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">级别名称：</label>
								<div class="col-sm-8">
									<form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">级别名称</th>
							<th class="text-center">折扣</th>
							<th class="text-center">是否默认级别</th>
<!-- 							<th class="text-center">update_date</th> -->
							<shiro:hasPermission name="config:wsMrank:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsMrank">
						<tr>
							<td class="text-center"><a href="${ctx}/config/wsMrank/form?id=${wsMrank.id}">
								${wsMrank.name}
							</a></td>
							<td class="text-center">
								${wsMrank.scale}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsMrank.isDefault, 'yes_no', '')}
							</td>
<!-- 							<td class="text-center"> -->
<%-- 								<fmt:formatDate value="${wsMrank.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/> --%>
<!-- 							</td> -->
							<shiro:hasPermission name="config:wsMrank:edit"><td>
			    				<a href="${ctx}/config/wsMrank/form?id=${wsMrank.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/config/wsMrank/delete?id=${wsMrank.id}" onclick="return confirmx('确认要删除该会员等级吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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