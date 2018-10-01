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
		<li class="active"><a href="${ctx}/prod/wsProdAttr/">产品属性列表</a></li>
		<shiro:hasPermission name="prod:wsProdAttr:edit"><li><a href="${ctx}/prod/wsProdAttr/form">产品属性添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsProdAttr" action="${ctx}/prod/wsProdAttr/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">产品id</th>
							<th class="text-center">属性编号</th>
							<th class="text-center">属性名</th>
							<th class="text-center">属性描述</th>
							<th class="text-center">update_date</th>
							<th class="text-center">remarks</th>
							<shiro:hasPermission name="prod:wsProdAttr:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsProdAttr">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsProdAttr/form?id=${wsProdAttr.id}">
								${wsProdAttr.productId}
							</a></td>
							<td class="text-center">
								${wsProdAttr.attrbuteCode}
							</td>
							<td class="text-center">
								${wsProdAttr.attrbuteName}
							</td>
							<td class="text-center">
								${wsProdAttr.attrbuteDesc}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsProdAttr.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsProdAttr.remarks}
							</td>
							<shiro:hasPermission name="prod:wsProdAttr:edit"><td>
			    				<a href="${ctx}/prod/wsProdAttr/form?id=${wsProdAttr.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsProdAttr/delete?id=${wsProdAttr.id}" onclick="return confirmx('确认要删除该产品属性吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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