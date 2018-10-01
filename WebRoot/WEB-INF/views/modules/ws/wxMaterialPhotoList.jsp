<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片素材管理</title>
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
		<li class="active"><a href="${ctx}/ws/wxMaterialPhoto/">图片素材列表</a></li>
		<shiro:hasPermission name="ws:wxMaterialPhoto:edit"><li><a href="${ctx}/ws/wxMaterialPhoto/form">图片素材添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxMaterialPhoto" action="${ctx}/ws/wxMaterialPhoto/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">图片标题：</label>
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
							<th class="text-center">图片标题</th>
							<th class="text-center">图片</th>
							<th class="text-center">是否微信同步</th>
							<shiro:hasPermission name="ws:wxMaterialPhoto:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wxMaterialPhoto">
						<tr>
							<td class="text-center"><a href="${ctx}/ws/wxMaterialPhoto/form?id=${wxMaterialPhoto.id}">
								${wxMaterialPhoto.title}
							</a></td>
							<td class="text-center">
								<img src="${wxMaterialPhoto.imgUrl}" style="width: 200px;height: 100px;">
							</td>
							<td class="text-center">
								<c:if test="${empty wxMaterialPhoto.mediaId}">否</c:if>
								<c:if test="${not empty wxMaterialPhoto.mediaId}">是</c:if>
							</td>
							<shiro:hasPermission name="ws:wxMaterialPhoto:edit"><td>
			    				<a href="${ctx}/ws/wxMaterialPhoto/form?id=${wxMaterialPhoto.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/ws/wxMaterialPhoto/delete?id=${wxMaterialPhoto.id}" onclick="return confirmx('确认要删除该图片素材吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
								<a href="${ctx}/ws/wxMaterialPhoto/synPhoto?id=${wxMaterialPhoto.id}" class="btn btn-info btn-sm"><i class="fa fa-plus">&nbsp;重新微信同步</i></a> 
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