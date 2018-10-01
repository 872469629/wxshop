<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公告管理管理</title>
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
		<li class="active"><a href="${ctx}/config/wsAnnouncement/">公告管理列表</a></li>
		<shiro:hasPermission name="config:wsAnnouncement:edit"><li><a href="${ctx}/config/wsAnnouncement/form">公告管理添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsAnnouncement" action="${ctx}/config/wsAnnouncement/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">公告类别：</label>
								<div class="col-sm-8">
									<form:select path="annoucetype" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('wx_accounce_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</div>
							</div>
						</div>
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
								<label class="col-sm-4 control-label">是否显示：</label>
								<div class="col-sm-8">
									<form:select path="isShow" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
							<th class="text-center">公告类别</th>
							<th class="text-center">标题</th>
							<th class="text-center">标记</th>
							<shiro:hasPermission name="config:wsAnnouncement:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsAnnouncement">
						<tr>
							<td class="text-center"><a href="${ctx}/config/wsAnnouncement/form?id=${wsAnnouncement.id}">
								${fns:getDictLabel(wsAnnouncement.annoucetype, 'wx_accounce_type', '')}
							</a></td>
							<td class="text-center">
								${wsAnnouncement.title}
							</td>
							<td class="text-center">
								${wsAnnouncement.remarks}
							</td>
							<shiro:hasPermission name="config:wsAnnouncement:edit"><td>
			    				<a href="${ctx}/config/wsAnnouncement/form?id=${wsAnnouncement.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/config/wsAnnouncement/delete?id=${wsAnnouncement.id}" onclick="return confirmx('确认要删除该公告管理吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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