<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>音频素材管理</title>
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
		<li class="active"><a href="${ctx}/ws/wxMaterialAudio/">音频素材列表</a></li>
		<shiro:hasPermission name="ws:wxMaterialAudio:edit"><li><a href="${ctx}/ws/wxMaterialAudio/form">音频素材添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxMaterialAudio" action="${ctx}/ws/wxMaterialAudio/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">音频名称：</label>
								<div class="col-sm-8">
									<form:input path="title" htmlEscape="false" maxlength="255" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
						</div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">音频名称</th>
							<th class="text-center">音频</th>
							<th class="text-center">更新时间</th>
							<shiro:hasPermission name="ws:wxMaterialAudio:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wxMaterialAudio">
						<tr>
							<td class="text-center"><a href="${ctx}/ws/wxMaterialAudio/form?id=${wxMaterialAudio.id}">
								${wxMaterialAudio.title}
							</a></td>
							<td class="text-center">
								${wxMaterialAudio.audioUrl}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wxMaterialAudio.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<shiro:hasPermission name="ws:wxMaterialAudio:edit"><td>
			    				<a href="${ctx}/ws/wxMaterialAudio/form?id=${wxMaterialAudio.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/ws/wxMaterialAudio/delete?id=${wxMaterialAudio.id}" onclick="return confirmx('确认要删除该音频素材吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
								<a href="${ctx}/ws/wxMaterialAudio/synAudio?id=${wxMaterialAudio.id}" class="btn btn-info btn-sm"><i class="fa fa-plus">&nbsp;重新微信同步</i></a> 
								
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