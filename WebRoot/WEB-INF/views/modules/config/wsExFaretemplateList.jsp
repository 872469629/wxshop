<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>快递模版管理</title>
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
		<li class="active"><a href="${ctx}/config/wsExFaretemplate/">快递模版列表</a></li>
		<shiro:hasPermission name="config:wsExFaretemplate:edit"><li><a href="${ctx}/config/wsExFaretemplate/form">快递模版添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsExFaretemplate" action="${ctx}/config/wsExFaretemplate/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">模版名称：</label>
								<div class="col-sm-8">
									<form:input path="name" htmlEscape="false" maxlength="100" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">模版名称</th>
							<th class="text-center">发货时间</th>
							<th class="text-center">发货地址</th>
							<th class="text-center">运送方式</th>
							<th class="text-center">更新时间</th>
							<th class="text-center">备注</th>
							<shiro:hasPermission name="config:wsExFaretemplate:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsExFaretemplate">
						<tr>
							<td class="text-center"><a href="${ctx}/config/wsExFaretemplate/form?id=${wsExFaretemplate.id}">
								${wsExFaretemplate.name}
							</a></td>
							<td class="text-center">
								${fns:getDictLabel(wsExFaretemplate.dispatchTime, 'config_dispatchtime', '')}
							</td>
							<td class="text-center">
								${wsExFaretemplate.shopAddr}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsExFaretemplate.carryWay, 'config_carryway', '')}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsExFaretemplate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsExFaretemplate.remarks}
							</td>
							<shiro:hasPermission name="config:wsExFaretemplate:edit"><td>
			    				<a href="${ctx}/config/wsExFaretemplate/form?id=${wsExFaretemplate.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/config/wsExFaretemplate/delete?id=${wsExFaretemplate.id}" onclick="return confirmx('确认要删除该快递模版吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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