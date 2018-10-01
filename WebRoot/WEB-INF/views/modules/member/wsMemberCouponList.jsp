<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户优惠券管理</title>
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
		<li class="active"><a href="${ctx}/member/wsMemberCoupon/">用户优惠券列表</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsMemberCoupon" action="${ctx}/member/wsMemberCoupon/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">用户昵称：</label>
								<div class="col-sm-8">
									<form:input path="wsMember.username" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">订单流水：</label>
								<div class="col-sm-8">
									<form:input path="wsOrder.orderSn" htmlEscape="false" maxlength="64" class="form-control"/>
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
							<th class="text-center">优惠券标题</th>
							<th class="text-center">优惠金额</th>
							<th class="text-center">满足多少金额优惠使用</th>
							<th class="text-center">状态</th>
							<th class="text-center">支付时间</th>
							<th class="text-center">订单流水</th>
							<shiro:hasPermission name="member:wsMemberCoupon:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsMemberCoupon">
						<tr>
							<td class="text-center"><a href="${ctx}/member/wsMemberCoupon/form?id=${wsMemberCoupon.id}">
								${wsMemberCoupon.wsMember.username}
							</a></td>
							<td class="text-center">
								${wsMemberCoupon.wsActivityCoupon.title}
							</td>
							<td class="text-center">
								${wsMemberCoupon.couponMoney}
							</td>
							<td class="text-center">
								${wsMemberCoupon.conditionMoney}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsMemberCoupon.state, 'coupon_state', '')}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsMemberCoupon.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsMemberCoupon.wsOrder.orderSn}
							</td>
							<shiro:hasPermission name="member:wsMemberCoupon:edit"><td>
			    				<a href="${ctx}/member/wsMemberCoupon/form?id=${wsMemberCoupon.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;查看</i></span></a>
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