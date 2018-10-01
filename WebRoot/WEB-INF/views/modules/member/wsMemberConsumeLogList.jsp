<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户消费记录管理</title>
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
		<li class="active"><a href="${ctx}/member/wsMemberConsumeLog/">用户消费记录列表</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsMemberConsumeLog" action="${ctx}/member/wsMemberConsumeLog/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">用户昵称：</label>
								<div class="col-sm-8">
									<form:input path="wsMember.nickname" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">用户昵称</th>
							<th class="text-center">订单编号</th>
							<th class="text-center">订单消费积分</th>
							<th class="text-center">订单消费金额</th>
							<th class="text-center">消费时间</th>
							<th class="text-center">剩余积分</th>
							<th class="text-center">剩余金额</th>
							<th class="text-center">付款方式</th>
							<shiro:hasPermission name="member:wsMemberConsumeLog:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsMemberConsumeLog">
						<tr>
							<td class="text-center"><a href="${ctx}/member/wsMemberConsumeLog/form?id=${wsMemberConsumeLog.id}">
								${wsMemberConsumeLog.wsMember.nickname}
							</a></td>
							<td class="text-center">
								${wsMemberConsumeLog.wsOrder.orderSn}
							</td>
							<td class="text-center">
								${wsMemberConsumeLog.consumeScore}
							</td>
							<td class="text-center">
								${wsMemberConsumeLog.consumeMoney}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsMemberConsumeLog.consumeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsMemberConsumeLog.score}
							</td>
							<td class="text-center">
								${wsMemberConsumeLog.balance}
							</td>
							<td class="text-center">
								${wsMemberConsumeLog.payment}
							</td>
							<shiro:hasPermission name="member:wsMemberConsumeLog:edit"><td>
			    				<a href="${ctx}/member/wsMemberConsumeLog/form?id=${wsMemberConsumeLog.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;查看</i></span></a>
<%-- 								<a href="${ctx}/member/wsMemberConsumeLog/delete?id=${wsMemberConsumeLog.id}" onclick="return confirmx('确认要删除该用户消费记录吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a> --%>
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