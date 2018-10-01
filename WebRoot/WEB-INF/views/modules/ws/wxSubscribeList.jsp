<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关注欢迎语管理</title>
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
		<li class="active"><a href="${ctx}/ws/wxSubscribe/">关注欢迎语列表</a></li>
		<shiro:hasPermission name="ws:wxSubscribe:edit"><li><a href="${ctx}/ws/wxSubscribe/form">关注欢迎语添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxSubscribe" action="${ctx}/ws/wxSubscribe/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">素材名称：</label>
								<div class="col-sm-8">
									<form:input path="materialTitle" htmlEscape="false" maxlength="255" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">消息类型</th>
							<th class="text-center">素材名称</th>
							<th class="text-center">更新时间</th>
							<shiro:hasPermission name="ws:wxSubscribe:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wxSubscribe">
						<tr>
							<td class="text-center"><a href="${ctx}/ws/wxSubscribe/form?id=${wxSubscribe.id}">
								${fns:getDictLabel(wxSubscribe.msgType, 'msg_type', '')}
							</a></td>
							<td class="text-center">
								${wxSubscribe.materialTitle}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wxSubscribe.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<shiro:hasPermission name="ws:wxSubscribe:edit"><td>
			    				<a href="${ctx}/ws/wxSubscribe/form?id=${wxSubscribe.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/ws/wxSubscribe/delete?id=${wxSubscribe.id}" onclick="return confirmx('确认要删除该关注欢迎语吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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