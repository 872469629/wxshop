<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商城异常数据告警管理</title>
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
		<li class="active"><a href="${ctx}/order/wsWarn/">商城异常数据告警列表</a></li>
		<shiro:hasPermission name="order:wsWarn:edit"><li><a href="${ctx}/order/wsWarn/form">商城异常数据告警添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsWarn" action="${ctx}/order/wsWarn/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">预警类型：</label>
								<div class="col-sm-8">
									<form:select path="type" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('warn_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">预警状态：</label>
								<div class="col-sm-8">
									<form:select path="state" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('warn_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
							<th class="text-center">预警类型</th>
							<th class="text-center">预警内容</th>
							<th class="text-center">预警级别</th>
							<th class="text-center">预警状态</th>
							<shiro:hasPermission name="order:wsWarn:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsWarn">
						<tr>
							<td class="text-center"><a href="${ctx}/order/wsWarn/form?id=${wsWarn.id}">
								${fns:getDictLabel(wsWarn.type, 'warn_type', '')}
							</a></td>
							<td class="text-center">
								${wsWarn.warnContent}
							</td>
							<td class="text-center">
								${wsWarn.level}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsWarn.state, 'warn_state', '')}
							</td>
							<shiro:hasPermission name="order:wsWarn:edit"><td>
			    				<a href="${ctx}/order/wsWarn/form?id=${wsWarn.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/order/wsWarn/delete?id=${wsWarn.id}" onclick="return confirmx('确认要删除该商城异常数据告警吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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