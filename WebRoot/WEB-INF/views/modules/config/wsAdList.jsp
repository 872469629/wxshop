<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片管理管理</title>
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
		<li class="active"><a href="${ctx}/config/wsAd/">图片管理列表</a></li>
		<shiro:hasPermission name="config:wsAd:edit"><li><a href="${ctx}/config/wsAd/form">图片管理添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsAd" action="${ctx}/config/wsAd/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">图片类型：</label>
								<div class="col-sm-8">
									<form:select path="imgType" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('cms_adimage_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">图片标题：</label>
								<div class="col-sm-8">
									<form:input path="imgTitle" htmlEscape="false" maxlength="100" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">图片类型</th>
							<th class="text-center">图片标题</th>
							<th class="text-center">图片地址</th>
							<th class="text-center">备注</th>
							<shiro:hasPermission name="config:wsAd:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsAd">
						<tr>
							<td class="text-center"><a href="${ctx}/config/wsAd/form?id=${wsAd.id}">
								${fns:getDictLabel(wsAd.imgType, 'cms_adimage_type', '')}
							</a></td>
							<td class="text-center">
								${wsAd.imgTitle}
							</td>
							<td class="text-center">
								
							<img alt="" src="${wsAd.imgUrl}" style="width: 100px;height: 50px">
							</td>
							<td class="text-center">
								${wsAd.remarks}
							</td>
							<shiro:hasPermission name="config:wsAd:edit"><td>
			    				<a href="${ctx}/config/wsAd/form?id=${wsAd.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
								<a href="${ctx}/config/wsAd/delete?id=${wsAd.id}" onclick="return confirmx('确认要删除该图片管理吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
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