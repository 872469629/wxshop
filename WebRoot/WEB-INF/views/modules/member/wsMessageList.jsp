<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员消息管理</title>
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
		<li class="active"><a href="${ctx}/member/wsMessage/">会员消息列表</a></li>
		<shiro:hasPermission name="member:wsMessage:edit"><li><a href="${ctx}/member/wsMessage/form">发送消息</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsMessage" action="${ctx}/member/wsMessage/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">标题：</label>
								<div class="col-sm-8">
									<form:input path="title" htmlEscape="false" maxlength="255" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">类型：</label>
								<div class="col-sm-8">
									<form:select path="msgType" class="form-control select2">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('ws_msg_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>									
								</div>
							</div>
						</div>
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">标题</th>
							<th class="text-center">类型</th>
							<th class="text-center">消息内容</th>
							<th class="text-center">更新时间</th>
							<shiro:hasPermission name="member:wsMessage:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsMessage">
						<tr>
							<td class="text-center">
								${wsMessage.title}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsMessage.msgType, 'ws_msg_type', '')}
							</td>
							<td class="text-center">
								${fns:abbr(wsMessage.msgContent,20)}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wsMessage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<shiro:hasPermission name="member:wsMessage:edit"><td>
								<a href="${ctx}/member/wsMessage/delete?id=${wsMessage.id}" onclick="return confirmx('确认要删除该系统消息吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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