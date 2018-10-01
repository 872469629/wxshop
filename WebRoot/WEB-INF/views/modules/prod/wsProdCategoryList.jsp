<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品分类管理</title>
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
		<li class="active"><a href="${ctx}/prod/wsProdCategory/">产品分类列表</a></li>
		<shiro:hasPermission name="prod:wsProdCategory:edit"><li><a href="${ctx}/prod/wsProdCategory/form">产品分类添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
			<form:form id="searchForm" modelAttribute="wsProdCategory" action="${ctx}/prod/wsProdCategory/" method="post" class="form-horizontal">
				<div class="box-body">
					<div class="col-md-4">
						<div class="form-group">
							<label class="col-sm-4 control-label">分类名称：</label>
							<div class="col-sm-8">
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control"/>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="col-sm-4 control-label">分类描述：</label>
							<div class="col-sm-8">
						<form:input path="title" htmlEscape="false" maxlength="500" class="form-control"/>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="treeTable" class="table table-bordered table-striped table-hover dataTable no-footer">
				<thead>
					<tr>
						<th class="text-center">父类</th>
						<th class="text-center">分类名称</th>
						<th class="text-center">分类描述</th>
						<th class="text-center">图片地址</th>
						<th class="text-center">更新日期</th>
<!-- 						<th class="text-center">备注</th> -->
						<shiro:hasPermission name="prod:wsProdCategory:edit"><th class="text-center">操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody id="treeTableList"></tbody>
			</table>
			<script type="text/template" id="treeTableTpl">
				<tr id="{{row.id}}" pId="{{pid}}">
					<td class="text-center"><a href="${ctx}/prod/wsProdCategory/form?id={{row.id}}">
						{{row.parent.id}}
					</a></td>
					<td class="text-center">
						{{row.name}}
					</td>
					<td class="text-center">
						{{row.title}}
					</td>
					<td class="text-center">
						<img alt="" src="{{row.imageUrl}}" style="width: 50px;height: 50px">
					</td>
					<td class="text-center">
						{{row.updateDate}}
					</td>
				
					<shiro:hasPermission name="prod:wsProdCategory:edit"><td class="text-center">
		   				<a href="${ctx}/prod/wsProdCategory/form?id={{row.id}}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
						<a href="${ctx}/prod/wsProdCategory/delete?id={{row.id}}" onclick="return confirmx('确认要删除该产品分类及所有子产品分类吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
						{{#row.isParent}}
						<a href="${ctx}/prod/wsProdCategory/form?parent.id={{row.id}}" class="btn btn-info btn-sm"><i class="fa fa-plus">&nbsp;添加下级产品分类</i></a> 
						{{/row.isParent}}
					</td></shiro:hasPermission>
				</tr>
			</script>
				</div>
			</div>
		</div>
	</section>
</body>
</html>