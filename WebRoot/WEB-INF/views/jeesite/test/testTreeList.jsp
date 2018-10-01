<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>树结构管理</title>
	<meta name="decorator" content="adminlte"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/test/testTree/">树结构列表</a></li>
		<shiro:hasPermission name="test:testTree:edit"><li><a href="${ctx}/test/testTree/form">树结构添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
		<div class="row">
			<div class="col-xs-12">
				<div class="box box-primary">
					<form:form id="searchForm" modelAttribute="testTree" action="${ctx}/test/testTree/" method="post" class="form-horizontal">
						<div class="box-body">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label">名称：</label>
									<div class="col-sm-4">
										<form:input path="name" htmlEscape="false" maxlength="100" class="form-control required" />
									</div>
						            <div class="col-sm-2">
						            	<button id="btnSubmit" type="submit" class="btn btn-primary" data-btn-type="search">查询</button>
						            </div>
								</div>	
							</div>
						</div>
					</form:form>
					<sys:message content="${message}"/>
					<table id="treeTable" class="table table-bordered table-striped table-hover dataTable no-footer">
							<tr>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;名称</th>
								<th class="text-center">排序</th>
								<th class="text-center">更新时间</th>
								<th class="text-center">备注信息</th>
								<shiro:hasPermission name="test:testTree:edit"><th class="text-center">操作</th></shiro:hasPermission>
							</tr>
						<tbody id="treeTableList"></tbody>
					</table>
					<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/test/testTree/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td class="text-center">
				{{row.sort}}
			</td>
			<td class="text-center">
				{{row.updateDate}}
			</td>
			<td class="text-center">
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="test:testTree:edit"><td class="text-center">
   				<a href="${ctx}/test/testTree/form?id={{row.id}}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
				<a href="${ctx}/test/testTree/delete?id={{row.id}}" onclick="return confirmx('确认要删除该树结构及所有子树结构吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
				<a href="${ctx}/test/testTree/form?parent.id={{row.id}}" class="btn btn-info btn-sm"><i class="fa fa-plus">&nbsp;添加下级树结构</i></a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
				</div>
			</div>
		</div>
	</section>
</body>
</html>