<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌管理</title>
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
		<li class="active"><a href="${ctx}/prod/wsBrand/">品牌列表</a></li>
		<shiro:hasPermission name="prod:wsBrand:edit"><li><a href="${ctx}/prod/wsBrand/form">品牌添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsBrand" action="${ctx}/prod/wsBrand/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">中文名称：</label>
								<div class="col-sm-8">
									<form:input path="cnname" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">英文名称：</label>
								<div class="col-sm-8">
									<form:input path="enname" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">官网地址：</label>
								<div class="col-sm-8">
									<form:input path="websiteurl" htmlEscape="false" maxlength="255" class="form-control"/>
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
							<th class="text-center">中文名称</th>
							<th class="text-center">英文名称</th>
							<th class="text-center">logo</th>
							<th class="text-center">状态</th>
							<th class="text-center">官网地址</th>
							<th class="text-center">更新日期</th>
<!-- 							<th class="text-center">标记</th> -->
							<shiro:hasPermission name="prod:wsBrand:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="wsBrand">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsBrand/form?id=${wsBrand.id}">
								${wsBrand.cnname}
							</a></td>
							<td class="text-center">
								${wsBrand.enname}
							</td>
							<td class="text-center">
							
					<img alt="" src="${wsBrand.logo}" style="width: 100px;height: 50px">
							</td>
							<td class="text-center">
								${wsBrand.state}
							</td>
							<td class="text-center">
							<a href="${wsBrand.websiteurl}" target="_blank">
									${wsBrand.websiteurl}
							</a>
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsBrand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
<!-- 							<td class="text-center"> -->
<%-- 								${wsBrand.remarks} --%>
<!-- 							</td> -->
							<shiro:hasPermission name="prod:wsBrand:edit"><td>
			    				<a href="${ctx}/prod/wsBrand/form?id=${wsBrand.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsBrand/delete?id=${wsBrand.id}" onclick="return confirmx('确认要删除该品牌吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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