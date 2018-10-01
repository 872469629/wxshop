<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评论回复管理管理</title>
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
		<li class="active"><a href="${ctx}/prod/wsConsulation/">评论回复管理列表</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsConsulation" action="${ctx}/prod/wsConsulation/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">产品名称：</label>
								<div class="col-sm-8">
									<form:input path="productTitle" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">评论会员：</label>
								<div class="col-sm-8">
									<form:input path="nickname" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">评论产品</th>
							<th class="text-center">评论会员</th>
							<th class="text-center">内容</th>
							<th class="text-center">评分等级</th>
							<shiro:hasPermission name="prod:wsConsulation:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsConsulation">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsConsulation/form?id=${wsConsulation.id}">
								${wsConsulation.productTitle}
							</a></td>
							<td class="text-center">
								${wsConsulation.nickname}
							</td>
							<td class="text-center">
								${fns:abbr(wsConsulation.consulationContent,90)}
							</td>
							<td class="text-center">
								${wsConsulation.prodConsulationLevel}星
							</td>
							<shiro:hasPermission name="prod:wsConsulation:edit"><td>
			    				<a href="${ctx}/prod/wsConsulation/form?id=${wsConsulation.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsConsulation/delete?id=${wsConsulation.id}" onclick="return confirmx('确认要删除该评论回复管理吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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