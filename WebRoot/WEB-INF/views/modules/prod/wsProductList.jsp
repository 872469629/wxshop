<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品信息管理</title>
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
		<li class="active"><a href="${ctx}/prod/wsProduct/">产品信息列表</a></li>
		<shiro:hasPermission name="prod:wsProduct:edit"><li><a href="${ctx}/prod/wsProduct/form">产品信息添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsProduct" action="${ctx}/prod/wsProduct/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">产品分类：</label>
								<div class="col-sm-8">
								<sys:treeselect id="prodCategoryId" name="prodCategoryId" value="${wsProduct.prodCategoryId}" labelName="prodCategoryName" labelValue="${wsProduct.prodCategoryName}"
									notAllowSelectParent="true" title="产品分类" url="/prod/wsProdCategory/treeData" extId="" cssClass="form-control" allowClear="true"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">标题</label>
								<div class="col-sm-8">
									<form:input path="title" htmlEscape="false" maxlength="512" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">产品状态：</label>
								<div class="col-sm-8">
									<form:select path="onGoodState" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('on_good_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
							<th class="text-center">产品分类</th>
							<th class="text-center">品牌名称</th>
							<th class="text-center">名称</th>
							<th class="text-center">标题</th>
							<th class="text-center">产品状态</th>
							<th class="text-center">是否赠品</th>
							<th class="text-center">是否支持退货</th>
							<th class="text-center">已售数量</th>
							<shiro:hasPermission name="prod:wsProduct:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsProduct">
						<tr>
							<td class="text-center"><a href="${ctx}/prod/wsProduct/form?id=${wsProduct.id}">
								${wsProduct.prodCategoryName}
								</a>
							</td>
							<td class="text-center">
								${wsProduct.brandName}
							</td>
							<td class="text-center">
								${fns:abbr(wsProduct.pname,30)}
							</td>
							<td class="text-center">
								${fns:abbr(wsProduct.title,30)}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProduct.onGoodState, 'on_good_state', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProduct.isGift, 'yes_no', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProduct.isReturn, 'yes_no', '')}
							</td>
							<td class="text-center">
								${wsProduct.selNum}
							</td>
							<shiro:hasPermission name="prod:wsProduct:edit"><td>
			    				<a href="${ctx}/prod/wsProduct/nextForm?id=${wsProduct.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/prod/wsProduct/delete?id=${wsProduct.id}" onclick="return confirmx('确认要删除该产品信息吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
								<c:if test="${wsProduct.onGoodState==1}">
								<a href="${ctx}/prod/wsProduct/downProduct?id=${wsProduct.id}" onclick="return confirmx('确认要下架该产品信息吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;下架</i></a>
								</c:if>
								<c:if test="${wsProduct.onGoodState==0}">
								<a href="${ctx}/prod/wsProduct/upProduct?id=${wsProduct.id}" onclick="return confirmx('确认要上架该产品信息吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;上架</i></a>
								</c:if>
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