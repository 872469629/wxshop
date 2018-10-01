<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信账号配置管理</title>
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
		<li class="active"><a href="${ctx}/ws/wxAccount/">微信账号配置列表</a></li>
		<shiro:hasPermission name="ws:wxAccount:edit"><li><a href="${ctx}/ws/wxAccount/form">微信账号配置添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxAccount" action="${ctx}/ws/wxAccount/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">公众帐号名称：</label>
								<div class="col-sm-8">
									<form:input path="accountName" htmlEscape="false" maxlength="200" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">公众微信号：</label>
								<div class="col-sm-8">
									<form:input path="accountNumber" htmlEscape="false" maxlength="200" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">公众帐号名称</th>
							<th class="text-center">公众微信号</th>
							<th class="text-center">公众号原始id</th>
							<th class="text-center">公众帐号TOKEN</th>
							<th class="text-center">公众号类型</th>
							<th class="text-center">公众帐号描述</th>
							<th class="text-center">公众帐号APPID</th>
							<th class="text-center">公众帐号APPSECRET</th>
							<th class="text-center">ACCESS_TOKEN</th>
							<th class="text-center">加密字符串</th>
							<th class="text-center">api_ticket</th>
							<th class="text-center">api_tickett_time</th>
							<th class="text-center">jsapi_ticket</th>
							<th class="text-center">jsapi_ticket_time</th>
							<th class="text-center">更新时间</th>
							<th class="text-center">备注信息</th>
							<shiro:hasPermission name="ws:wxAccount:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wxAccount">
						<tr>
							<td class="text-center"><a href="${ctx}/ws/wxAccount/form?id=${wxAccount.id}">
								${wxAccount.accountName}
							</a></td>
							<td class="text-center">
								${wxAccount.accountNumber}
							</td>
							<td class="text-center">
								${wxAccount.accountToken}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wxAccount.accountType, 'wx_account_type', '')}
							</td>
							<td class="text-center">
								${wxAccount.accountAppid}
							</td>
							<td class="text-center">
								${wxAccount.accountAppsecret}
							</td>
							<td class="text-center">
								${wxAccount.accountAccesstoken}
							</td>
							<td class="text-center">
								${wxAccount.encodingAesKey}
							</td>
							<td class="text-center">
								${wxAccount.apiTicket}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wxAccount.apiTickettTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wxAccount.jsapiTicket}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wxAccount.jsapiTicketTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wxAccount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wxAccount.remarks}
							</td>
							<shiro:hasPermission name="ws:wxAccount:edit"><td>
			    				<a href="${ctx}/ws/wxAccount/form?id=${wxAccount.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/ws/wxAccount/delete?id=${wxAccount.id}" onclick="return confirmx('确认要删除该微信账号配置吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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