<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群发微信管理</title>
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
		<li class="active"><a href="${ctx}/ws/wxMassMsg/">群发微信列表</a></li>
		<shiro:hasPermission name="ws:wxMassMsg:edit"><li><a href="${ctx}/ws/wxMassMsg/form">群发微信添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxMassMsg" action="${ctx}/ws/wxMassMsg/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">消息类型</label>
								<div class="col-sm-8">
									<form:select path="msgType" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('msg_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
							<th class="text-center">消息类型</th>
							<th class="text-center">素材标题</th>
							<th class="text-center">更新时间</th>
							<th class="text-center">备注信息</th>
							<shiro:hasPermission name="ws:wxMassMsg:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wxMassMsg">
						<tr>
							<td class="text-center"><a href="${ctx}/ws/wxMassMsg/form?id=${wxMassMsg.id}">
								${fns:getDictLabel(wxMassMsg.msgType, 'msg_type', '')}
							</a></td>
							<td class="text-center">
								${wxMassMsg.materialTitle}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wxMassMsg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								${wxMassMsg.remarks}
							</td>
							<shiro:hasPermission name="ws:wxMassMsg:edit"><td>
			    				<a href="${ctx}/ws/wxMassMsg/form?id=${wxMassMsg.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/ws/wxMassMsg/delete?id=${wxMassMsg.id}" onclick="return confirmx('确认要删除该群发微信吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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