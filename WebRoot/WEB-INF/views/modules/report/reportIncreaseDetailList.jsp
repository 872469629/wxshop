<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>每日新增数据明细管理</title>
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
		<li class="active"><a href="${ctx}/report/reportIncreaseDetail/">每日新增数据明细列表</a></li>
		<shiro:hasPermission name="report:reportIncreaseDetail:edit"><li><a href="${ctx}/report/reportIncreaseDetail/form">每日新增数据明细添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="reportIncreaseDetail" action="${ctx}/report/reportIncreaseDetail/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">统计日期：</label>
								<div class="col-sm-8">
									<form:input path="countDate" htmlEscape="false" maxlength="20" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">统计日期</th>
							<th class="text-center">新增用户数量</th>
							<th class="text-center">新增收入</th>
							<th class="text-center">订单数量</th>
							<th class="text-center">退款金额</th>
							<th class="text-center">访问用户</th>
							<th class="text-center">更新时间</th>
							<th class="text-center">备注信息</th>
							<shiro:hasPermission name="report:reportIncreaseDetail:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="reportIncreaseDetail">
						<tr>
							<td class="text-center"><a href="${ctx}/report/reportIncreaseDetail/form?id=${reportIncreaseDetail.id}">
								${reportIncreaseDetail.countDate}
							</a></td>
							<td class="text-center">
								${reportIncreaseDetail.addUserCount}
							</td>
							<td class="text-center">
								${reportIncreaseDetail.selMoney}
							</td>
							<td class="text-center">
								${reportIncreaseDetail.orderCount}
							</td>
							<td class="text-center">
								${reportIncreaseDetail.backMoney}
							</td>
							<td class="text-center">
								${reportIncreaseDetail.visitMemberCount}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${reportIncreaseDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${reportIncreaseDetail.remarks}
							</td>
							<shiro:hasPermission name="report:reportIncreaseDetail:edit"><td>
			    				<a href="${ctx}/report/reportIncreaseDetail/form?id=${reportIncreaseDetail.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/report/reportIncreaseDetail/delete?id=${reportIncreaseDetail.id}" onclick="return confirmx('确认要删除该每日新增数据明细吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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