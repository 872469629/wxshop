<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			  $('#fahuo').on('click', function(){
				 var a = $(this).attr("fahuovalue");
					   layer.open({
			            type: 2,
			            title: '发货',
			            maxmin: true,
			            shadeClose: true, //点击遮罩关闭层
			            area : ['400px' , '400px'],
			            content: '${ctx}/order/wsOrder/formSend?id='+a+'',
			            end: function () {
			                location.reload();
			            }
			        });
			    });

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
      
	</script>
	<script src="${ctxStatic}/layer/layer.js" type="text/javascript"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/wsOrder/">订单列表</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsOrder" action="${ctx}/order/wsOrder/" method="post" class="form-horizontal">
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
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">订单状态：</label>
								<div class="col-sm-8">
									<form:select path="orderState" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('order_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">快递单号：</label>
								<div class="col-sm-8">
									<form:input path="trackingno" htmlEscape="false" maxlength="255" class="form-control"/>
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
							<th class="text-center">用户</th>
							<th class="text-center">付款方式</th>
							<th class="text-center">价格</th>
							<th class="text-center">订单状态</th>
							<shiro:hasPermission name="order:wsOrder:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsOrder">
						<tr>
							<td class="text-center"><a href="${ctx}/order/wsOrder/form?id=${wsOrder.id}">
								${wsOrder.orderSn}
							</a><br>
							下单时间：<fmt:formatDate value="${wsOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							<td class="text-center">
								${wsOrder.memberId.nickname}<br>
								手机号：${wsOrder.memberId.mobile}
							</td>
							
							<td class="text-center">
								${fns:getDictLabel(wsOrder.payment, 'payment', '')}<br>
								<c:if test="${!empty wsOrder.paytime}">
									付费时间：<fmt:formatDate value="${wsOrder.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
								</c:if>
							</td>
				
							<td class="text-center">
								合计价格：${wsOrder.price} <br>
								实际支付：${wsOrder.reallyPrice}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsOrder.orderState, 'order_state', '')}
							</td>
							<shiro:hasPermission name="order:wsOrder:edit"><td>
			    				<a href="${ctx}/order/wsOrder/form?id=${wsOrder.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;详情</i></span></a><br>			    					
			    				<c:if test="${wsOrder.orderState==0}">
			    					<a href="${ctx}/order/wsOrder/finished?id=${wsOrder.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;立即完成</i></span></a><br>
			    					<a href="${ctx}/order/wsOrder/closeOrder?id=${wsOrder.id}" onclick="return confirmx('确认要关闭该订单吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;关闭</i></a>
			    				</c:if>
			    				<c:if test="${wsOrder.orderState==1}">
			    					<a id="fahuo" fahuovalue="${wsOrder.id}" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;发货</i></a>
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