<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购物车管理管理</title>
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
		<li class="active"><a href="${ctx}/prod/wsCart/">购物车管理列表</a></li>
		<shiro:hasPermission name="prod:wsCart:edit"><li><a href="${ctx}/prod/wsCart/form">购物车管理添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsCart" action="${ctx}/prod/wsCart/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">用户：</label>
								<div class="col-sm-8">
									<form:input path="memberId" htmlEscape="false" maxlength="255" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">购物车产品：</label>
								<div class="col-sm-8">
									<form:input path="productId" htmlEscape="false" maxlength="11" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">用户</th>
							<th class="text-center">购物车产品</th>
							<th class="text-center">产品sku</th>
							<th class="text-center">数量</th>
							<th class="text-center">总价格=数量和单价的乘积</th>
							<th class="text-center">购物车商品状态0失效1生效</th>
							<th class="text-center">update_date</th>
							<th class="text-center">标记</th>
							<shiro:hasPermission name="prod:wsCart:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsCart">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsCart/form?id=${wsCart.id}">
								${wsCart.memberId}
							</a></td>
							<td class="text-center">
								${wsCart.productId}
							</td>
							<td class="text-center">
								${wsCart.skuId}
							</td>
							<td class="text-center">
								${wsCart.quantity}
							</td>
							<td class="text-center">
								${wsCart.price}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsCart.state, 'state', '')}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsCart.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsCart.remarks}
							</td>
							<shiro:hasPermission name="prod:wsCart:edit"><td>
			    				<a href="${ctx}/prod/wsCart/form?id=${wsCart.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsCart/delete?id=${wsCart.id}" onclick="return confirmx('确认要删除该购物车管理吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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