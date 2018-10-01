<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图文素材明细管理</title>
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
		function goBackNews(){
			location="${ctx}/ws/wxMaterialNews/";
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ws/wxMaterialNewsItem/">图文素材明细列表</a></li>
		<li><a href="${ctx}/ws/wxMaterialNewsItem/form?newsId=${wxMaterialNewsItem.newsId}">图文素材明细添加</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxMaterialNewsItem" action="${ctx}/ws/wxMaterialNewsItem/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">作者：</label>
								<div class="col-sm-8">
									<form:input path="author" htmlEscape="false" maxlength="255" class="form-control"/>
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
						<div class="col-md-12 col-md-offset-5">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;
							<input id="btnSubmit" class="btn btn-primary" type="button" value="返回上层图文信息" onclick="goBackNews()"/>&nbsp;
						</div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">图文标题</th>
							<th class="text-center">图文类型</th>
							<th class="text-center">作者</th>
							<th class="text-center">素材标题</th>
							<th class="text-center">消息摘要描述</th>
							<th class="text-center">图文顺序</th>
							<th>操作</th>
						</tr>
					<c:forEach items="${page.list}" var="wxMaterialNewsItem">
						<tr>
							<td class="text-center">
								${wxMaterialNewsItem.newsTitle}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wxMaterialNewsItem.newType, 'new_type', '')}
							</td>
							<td class="text-center">
								${wxMaterialNewsItem.author}
							</td>
							<td class="text-center">
								${wxMaterialNewsItem.title}
							</td>
							<td class="text-center">
								${wxMaterialNewsItem.description}
							</td>
							<td class="text-center">
								${wxMaterialNewsItem.orders}
							</td>
							<td>
			    				<a href="${ctx}/ws/wxMaterialNewsItem/form?id=${wxMaterialNewsItem.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/ws/wxMaterialNewsItem/delete?id=${wxMaterialNewsItem.id}" onclick="return confirmx('确认要删除该图文素材明细吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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