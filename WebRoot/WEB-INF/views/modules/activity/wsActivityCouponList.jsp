<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券活动管理</title>
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
		<li class="active"><a href="${ctx}/activity/wsActivityCoupon/">优惠券活动列表</a></li>
		<shiro:hasPermission name="activity:wsActivityCoupon:edit"><li><a href="${ctx}/activity/wsActivityCoupon/form">优惠券活动添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsActivityCoupon" action="${ctx}/activity/wsActivityCoupon/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">优惠券名称：</label>
								<div class="col-sm-8">
									<form:input path="title" htmlEscape="false" maxlength="100" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">优惠券名称</th>
							<th class="text-center">面值</th>
							<th class="text-center">优惠券总量</th>
							<th class="text-center">已发放数量</th>
							<th class="text-center">订单金额满足多少才能使用</th>
							<th class="text-center">产品类型</th>
							<shiro:hasPermission name="activity:wsActivityCoupon:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsActivityCoupon">
						<tr>
							<td class="text-center"><a href="${ctx}/activity/wsActivityCoupon/form?id=${wsActivityCoupon.id}">
								${wsActivityCoupon.title}
							</a></td>
							<td class="text-center">
								${wsActivityCoupon.faceValue}
							</td>
							<td class="text-center">
								${wsActivityCoupon.countNum}
							</td>
							<td class="text-center">
								${wsActivityCoupon.surplusNum}
							</td>
							<td class="text-center">
								${wsActivityCoupon.orderMoney}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsActivityCoupon.prodType, 'prod_type', '')}
							</td>
							<shiro:hasPermission name="activity:wsActivityCoupon:edit"><td>
			    				<a href="${ctx}/activity/wsActivityCoupon/form?id=${wsActivityCoupon.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/activity/wsActivityCoupon/delete?id=${wsActivityCoupon.id}" onclick="return confirmx('确认要删除该优惠券活动吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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