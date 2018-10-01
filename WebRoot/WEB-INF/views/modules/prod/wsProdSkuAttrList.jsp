<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品sku属性管理</title>
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
		<li class="active"><a href="${ctx}/prod/wsProdSkuAttr/">产品sku属性列表</a></li>
		<shiro:hasPermission name="prod:wsProdSkuAttr:edit"><li><a href="${ctx}/prod/wsProdSkuAttr/form">产品sku属性添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsProdSkuAttr" action="${ctx}/prod/wsProdSkuAttr/" method="post" class="form-horizontal">
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
							<th class="text-center">属性id</th>
							<th class="text-center">属性名=比如内存</th>
							<th class="text-center">属性值ID</th>
							<th class="text-center">属性值名称=比如16</th>
							<th class="text-center">update_date</th>
							<th class="text-center">remarks</th>
							<shiro:hasPermission name="prod:wsProdSkuAttr:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsProdSkuAttr">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsProdSkuAttr/form?id=${wsProdSkuAttr.id}">
								${wsProdSkuAttr.skuId}
							</a></td>
							<td class="text-center">
								${wsProdSkuAttr.attrbuteId}
							</td>
							<td class="text-center">
								${wsProdSkuAttr.attrbuteName}
							</td>
							<td class="text-center">
								${wsProdSkuAttr.attrbuteValue}
							</td>
							<td class="text-center">
								${wsProdSkuAttr.attrbuteValueName}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsProdSkuAttr.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsProdSkuAttr.remarks}
							</td>
							<shiro:hasPermission name="prod:wsProdSkuAttr:edit"><td>
			    				<a href="${ctx}/prod/wsProdSkuAttr/form?id=${wsProdSkuAttr.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsProdSkuAttr/delete?id=${wsProdSkuAttr.id}" onclick="return confirmx('确认要删除该产品sku属性吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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