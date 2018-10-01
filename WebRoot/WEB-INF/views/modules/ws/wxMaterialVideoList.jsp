<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频素材管理</title>
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
		<li class="active"><a href="${ctx}/ws/wxMaterialVideo/">视频素材列表</a></li>
		<shiro:hasPermission name="ws:wxMaterialVideo:edit"><li><a href="${ctx}/ws/wxMaterialVideo/form">视频素材添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxMaterialVideo" action="${ctx}/ws/wxMaterialVideo/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">视频标题：</label>
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
							<th class="text-center">视频标题</th>
							<th class="text-center">视频</th>
							<th class="text-center">更新时间</th>
							<shiro:hasPermission name="ws:wxMaterialVideo:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wxMaterialVideo">
						<tr>
							<td class="text-center"><a href="${ctx}/ws/wxMaterialVideo/form?id=${wxMaterialVideo.id}">
								${wxMaterialVideo.title}
							</a></td>
							<td class="text-center">
								${wxMaterialVideo.videoUrl}
							</td>
							<td class="text-center">
								<fmt:formatDate value="${wxMaterialVideo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<shiro:hasPermission name="ws:wxMaterialVideo:edit"><td>
			    				<a href="${ctx}/ws/wxMaterialVideo/form?id=${wxMaterialVideo.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/ws/wxMaterialVideo/delete?id=${wxMaterialVideo.id}" onclick="return confirmx('确认要删除该视频素材吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
								<a href="${ctx}/ws/wxMaterialVideo/synVideo?id=${wxMaterialVideo.id}" class="btn btn-info btn-sm"><i class="fa fa-plus">&nbsp;重新微信同步</i></a> 
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