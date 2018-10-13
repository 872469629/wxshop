<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现管理</title>
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
		<li class="active"><a href="${ctx}/withdrawal/wsWithdrawal/">提现列表</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsWithdrawal" action="${ctx}/withdrawal/wsWithdrawal/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">状态：</label>
								<div class="col-sm-8">
									<form:select path="status" class="form-control">
										<form:option value="" label="全部"/>
										<form:option value="0" label="提现中"/>
										<form:option value="1" label="成功"/>
										<form:option value="2" label="无效"/>
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
							<th class="text-center">提取金额</th>
<!-- 							<th class="text-center">当前金额</th> -->
							<th class="text-center">处理时间</th>
							<th class="text-center">状态</th>
							<th class="text-center">用户</th>
							<th class="text-center">创建时间</th>
							<th class="text-center">备注</th>
							<shiro:hasPermission name="withdrawal:wsWithdrawal:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsWithdrawal">
						<tr>
							<td class="text-center">
								${wsWithdrawal.amount}
							</td>
<!-- 							<td class="text-center"> -->
<%-- 								${wsWithdrawal.availableAmt} --%>
<!-- 							</td> -->
							<td class="text-center">
								<fmt:formatDate value="${wsWithdrawal.processDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${wsWithdrawal.status=='1' }">成功</c:when>
									<c:when test="${wsWithdrawal.status=='2' }">无效</c:when>
								</c:choose>
							</td>
							<td class="text-center">
								${wsWithdrawal.memberId.nickname}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsWithdrawal.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wsWithdrawal.remarks}
							</td>
							<shiro:hasPermission name="withdrawal:wsWithdrawal:edit"><td>
								<c:if test="${wsWithdrawal.status=='0' }">
				    				<a href="${ctx}/withdrawal/wsWithdrawal/form?id=${wsWithdrawal.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
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