<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动配置管理</title>
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
				<form:form id="searchForm" modelAttribute="wsActivity" action="${ctx}/config/wsActivity/activityAdSelectList" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">活动标题：</label>
								<div class="col-sm-8">
									<form:input path="title" htmlEscape="false" maxlength="100" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="box-footer">
							<label class="col-sm-4 control-label"></label>
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
						</div>					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">活动标题</th>
							<th class="text-center">活动类型</th>
							<th class="text-center">活动内容</th>
							<th class="text-center">优先级</th>
							<shiro:hasPermission name="config:wsActivity:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsActivity">
						<tr>
							<td class="text-center"><a href="${ctx}/config/wsActivity/form?id=${wsActivity.id}">
								${wsActivity.title}
							</a></td>
							<td class="text-center">
								${fns:getDictLabel(wsActivity.activityType, 'activity_type', '')}
							</td>
							<td class="text-center">
								${wsActivity.activityContent}
							</td>
							<td class="text-center">
								${wsActivity.priority}
							</td>
							<shiro:hasPermission name="config:wsActivity:edit"><td>
			    				<a onclick="parent.selectAd('${wsActivity.frontPage}','${wsActivity.title}')" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;选择</i></span></a>
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