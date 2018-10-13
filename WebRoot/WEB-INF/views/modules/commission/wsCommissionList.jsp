<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分销明细管理</title>
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
		<li class="active"><a href="${ctx}/commission/wsCommission/">分销明细列表</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsCommission" action="${ctx}/commission/wsCommission/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">订单号：</label>
								<div class="col-sm-8">
									<form:input path="orderId.orderSn" htmlEscape="false" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">用户：</label>
								<div class="col-sm-8">
									<form:input path="memberParent.nickname" htmlEscape="false" maxlength="255" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">一级推广起始：</label>
								<div class="col-sm-8">
									<form:input path="agent1PromotionStart" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">一级推广结束：</label>
								<div class="col-sm-8">
									<form:input path="agent1PromotionEnd" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">一级消费起始：</label>
								<div class="col-sm-8">
									<form:input path="agent1ConsumeStart" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">一级消费结束：</label>
								<div class="col-sm-8">
									<form:input path="agent1ConsumeEnd" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">二级推广起始：</label>
								<div class="col-sm-8">
									<form:input path="agent2PromotionStart" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">二级推广结束：</label>
								<div class="col-sm-8">
									<form:input path="agent2PromotionEnd" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">二级消费起始：</label>
								<div class="col-sm-8">
									<form:input path="agent2ConsumeStart" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">二级消费结束：</label>
								<div class="col-sm-8">
									<form:input path="agent2ConsumeEnd" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">三级推广起始：</label>
								<div class="col-sm-8">
									<form:input path="agent3PromotionStart" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">三级推广结束：</label>
								<div class="col-sm-8">
									<form:input path="agent3PromotionEnd" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">三级消费起始：</label>
								<div class="col-sm-8">
									<form:input path="agent3ConsumeStart" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="col-sm-4 control-label">三级消费结束：</label>
								<div class="col-sm-8">
									<form:input path="agent3ConsumeEnd" htmlEscape="false" maxlength="255" class="form-control" type="number"/>
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
							<th class="text-center">变化金额</th>
							<th class="text-center">金额类型</th>
							<th class="text-center">是否结算</th>
						</tr>
					<c:forEach items="${page.list}" var="wsCommission">
						<tr>
							<td class="text-center">
								${wsCommission.orderId.orderSn}
								<br/>
								支付时间：<fmt:formatDate value="${wsCommission.orderId.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								<c:if test="${not empty wsCommission.memberParent}">
									一级用户：${wsCommission.memberParent.nickname }
								</c:if>
								<c:if test="${empty wsCommission.memberParent}">
									一级用户：-
								</c:if>
								<br/>
								<c:if test="${not empty wsCommission.memberParentParent}">
									二级用户：${wsCommission.memberParentParent.nickname }
								</c:if>
								<c:if test="${empty wsCommission.memberParentParent}">
									二级用户：-
								</c:if>
								<br/>
								<c:if test="${not empty wsCommission.memberParentParentParent}">
									三级用户：${wsCommission.memberParentParentParent.nickname }
								</c:if>
								<c:if test="${empty wsCommission.memberParentParentParent}">
									三级用户：-
								</c:if>
							</td>
							<td>
								<c:if test="${not empty wsCommission.mpBalanceBefore}">
									变化前的余额：${wsCommission.mpBalanceBefore }
								</c:if>
								<c:if test="${empty wsCommission.mpBalanceBefore}">
									变化前的余额：-
								</c:if>
								,
								<c:if test="${not empty wsCommission.mpBalanceAfter}">
									变化后的余额：${wsCommission.mpBalanceAfter }
								</c:if>
								<c:if test="${empty wsCommission.mpBalanceAfter}">
									变化后的余额：-
								</c:if>
								<br/>
								<c:if test="${not empty wsCommission.mppBalanceBefore}">
									变化前的余额：${wsCommission.mppBalanceBefore }
								</c:if>
								<c:if test="${empty wsCommission.mppBalanceBefore}">
									变化前的余额：-
								</c:if>
								,
								<c:if test="${not empty wsCommission.mppBalanceAfter}">
									变化后的余额：${wsCommission.mppBalanceAfter }
								</c:if>
								<c:if test="${empty wsCommission.mppBalanceAfter}">
									变化后的余额：-
								</c:if>
								<br/>
								<c:if test="${not empty wsCommission.mpppBalanceBefore}">
									变化前的余额：${wsCommission.mpppBalanceBefore }
								</c:if>
								<c:if test="${empty wsCommission.mpppBalanceBefore}">
									变化前的余额：-
								</c:if>
								,
								<c:if test="${not empty wsCommission.mpppBalanceAfter}">
									变化后的余额：${wsCommission.mpppBalanceAfter }
								</c:if>
								<c:if test="${empty wsCommission.mpppBalanceAfter}">
									变化后的余额：-
								</c:if>
							</td>
							<td>
								一级推广返利：<c:if test="${not empty wsCommission.agent1Promotion }">${wsCommission.agent1Promotion }</c:if>
								<c:if test="${empty wsCommission.agent1Promotion }">-</c:if>
								,
								一级消费返利：<c:if test="${not empty wsCommission.agent1Consume }">${wsCommission.agent1Consume }</c:if>
								<c:if test="${empty wsCommission.agent1Consume }">-</c:if>
								<br/>
								二级推广返利：<c:if test="${not empty wsCommission.agent2Promotion }">${wsCommission.agent2Promotion }</c:if>
								<c:if test="${empty wsCommission.agent2Promotion }">-</c:if>
								,
								二级消费返利：<c:if test="${not empty wsCommission.agent2Consume }">${wsCommission.agent2Consume }</c:if>
								<c:if test="${empty wsCommission.agent2Consume }">-</c:if>
								<br/>
								三级推广返利：<c:if test="${not empty wsCommission.agent3Promotion }">${wsCommission.agent3Promotion }</c:if>
								<c:if test="${empty wsCommission.agent3Promotion }">-</c:if>
								,
								三级消费返利：<c:if test="${not empty wsCommission.agent3Consume }">${wsCommission.agent3Consume }</c:if>
								<c:if test="${empty wsCommission.agent3Consume }">-</c:if>
							</td>
							<td class="text-center">
								<c:if test="${wsCommission.status=='0' }">
									<div style="color:red;"><i class="fa fa-remove"></i></div>
								</c:if>
								<c:if test="${wsCommission.status=='1' }">
									<div style="color:green;"><i class="fa fa-check"></i></div>
								</c:if>
							</td>
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