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
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wxMaterialVideo" action="${ctx}/ws/wxMaterialVideo/templateList" method="post" class="form-horizontal">
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
						<div class="box-footer">
							<label class="col-sm-4 control-label"></label>
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
			    				<a onclick="parent.selectTemplate('${wxMaterialVideo.id}','${wxMaterialVideo.title}')" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;选择</i></span></a>
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