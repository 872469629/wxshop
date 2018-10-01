<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>热销产品管理</title>
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
		<li class="active"><a href="${ctx}/report/reportProductSel/">热销产品列表</a></li>
		<shiro:hasPermission name="report:reportProductSel:edit"><li><a href="${ctx}/report/reportProductSel/form">热销产品添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="reportProductSel" action="${ctx}/report/reportProductSel/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">统计日期：</label>
								<div class="col-sm-8">
									<input name="countDate" type="text" readonly="readonly" maxlength="20" class="form-control Wdate"
										value="<fmt:formatDate value="${reportProductSel.countDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">产品名称：</label>
								<div class="col-sm-8">
									<form:input path="productName" htmlEscape="false" maxlength="64" class="form-control"/>
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
							<th class="text-center">产品id</th>
							<th class="text-center">产品名称</th>
							<th class="text-center">销售数量</th>
							<th class="text-center">销售金额</th>
							<th class="text-center">当日销量百分比</th>
							<th class="text-center">创建者</th>
							<th class="text-center">更新时间</th>
							<th class="text-center">备注信息</th>
							<shiro:hasPermission name="report:reportProductSel:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="reportProductSel">
						<tr>
							<td class="text-center"><a href="${ctx}/report/reportProductSel/form?id=${reportProductSel.id}">
								${reportProductSel.countDate}
							</a></td>
							<td class="text-center">
								${reportProductSel.productId}
							</td>
							<td class="text-center">
								${reportProductSel.productName}
							</td>
							<td class="text-center">
								${reportProductSel.selNum}
							</td>
							<td class="text-center">
								${reportProductSel.selMoney}
							</td>
							<td class="text-center">
								${reportProductSel.percent}
							</td>
							<td class="text-center">
								${reportProductSel.createBy.id}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${reportProductSel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${reportProductSel.remarks}
							</td>
							<shiro:hasPermission name="report:reportProductSel:edit"><td>
			    				<a href="${ctx}/report/reportProductSel/form?id=${reportProductSel.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/report/reportProductSel/delete?id=${reportProductSel.id}" onclick="return confirmx('确认要删除该热销产品吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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