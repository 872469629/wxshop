<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商分销配置管理</title>
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
		<li class="active"><a href="${ctx}/rebate/wsAgentRebateConfig/">代理商分销配置列表</a></li>
		<shiro:hasPermission name="rebate:wsAgentRebateConfig:edit"><li><a href="${ctx}/rebate/wsAgentRebateConfig/form">代理商分销配置添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsAgentRebateConfig" action="${ctx}/rebate/wsAgentRebateConfig/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">类型：</label>
								<div class="col-sm-8">
									<form:select path="status" class="form-control">
										<form:option value="" label="全部"/>
										<form:option value="1" label="按比例"/>
										<form:option value="2" label="按固定金额"/>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">是否启用：</label>
								<div class="col-sm-8">
									<form:select path="status" class="form-control">
										<form:option value="" label="全部"/>
										<form:option value="1" label="是"/>
										<form:option value="0" label="否"/>
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
							<th class="text-center">类型</th>
							<th class="text-center">一级消费返利</th>
							<th class="text-center">二级消费返利</th>
							<th class="text-center">三级消费返利</th>
							<th class="text-center">是否启用</th>
							<th class="text-center">一级推广返利</th>
							<th class="text-center">二级推广返利</th>
							<th class="text-center">三级推广返利</th>
							<shiro:hasPermission name="rebate:wsAgentRebateConfig:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsAgentRebateConfig">
						<tr>
							<td class="text-center">
								<c:if test="${wsAgentRebateConfig.type=='1' }">按比例</c:if>
								<c:if test="${wsAgentRebateConfig.type=='2' }">按固定金额</c:if>
							</td>
							<td class="text-center">
								${wsAgentRebateConfig.level1proportion}
							</td>
							<td class="text-center">
								${wsAgentRebateConfig.level2proportion}
							</td>
							<td class="text-center">
								${wsAgentRebateConfig.level3proportion}
							</td>
							<td class="text-center">
								<c:if test="${wsAgentRebateConfig.status=='1' }">是</c:if>
								<c:if test="${wsAgentRebateConfig.status!='1' }">否</c:if>
							</td>
							<td class="text-center">
								${wsAgentRebateConfig.level1promotion}
							</td>
							<td class="text-center">
								${wsAgentRebateConfig.level2promotion}
							</td>
							<td class="text-center">
								${wsAgentRebateConfig.level3promotion}
							</td>
							<shiro:hasPermission name="rebate:wsAgentRebateConfig:edit"><td>
			    				<a href="${ctx}/rebate/wsAgentRebateConfig/form?id=${wsAgentRebateConfig.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/rebate/wsAgentRebateConfig/delete?id=${wsAgentRebateConfig.id}" onclick="return confirmx('确认要删除该代理商分销配置吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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