<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退货管理管理</title>
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
		<li class="active"><a href="${ctx}/returnback/wsReturn/">退货管理列表</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsReturn" action="${ctx}/returnback/wsReturn/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">订单号：</label>
								<div class="col-sm-8">
									<form:input path="orderSn" htmlEscape="false" maxlength="255" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-8">
							<div class="form-group">
								<label class="col-sm-4 control-label">退货订单状态：</label>
								<div class="col-sm-4">
									<form:select path="state" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('return_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
							<th class="text-center">订单号</th>
							<th class="text-center">退货订单状态：</th>
							<th class="text-center">退货原因</th>
							<th class="text-center">实际退货金额</th>
							<th class="text-center">退货扣除积分</th>
							<shiro:hasPermission name="returnback:wsReturn:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsReturn">
						<tr>
							<td class="text-center"><a href="${ctx}/returnback/wsReturn/form?id=${wsReturn.id}">
								${wsReturn.orderSn}
							</a></td>
							<td class="text-center">
								${fns:getDictLabel(wsReturn.state, 'return_state', '')}
							</td>
							<td class="text-center">
								${fns:abbr(wsReturn.reason,20)}
							</td>
							<td class="text-center">
								${wsReturn.returnAmount}
							</td>
							<td class="text-center">
								${wsReturn.returnScore}
							</td>
							<shiro:hasPermission name="returnback:wsReturn:edit"><td>
			    				<a href="${ctx}/returnback/wsReturn/form?id=${wsReturn.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
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