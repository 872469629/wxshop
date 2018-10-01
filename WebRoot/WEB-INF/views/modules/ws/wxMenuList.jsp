<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信菜单管理</title>
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
							menuType: getDictLabel(${fns:toJson(fns:getDictList('menu_type'))}, row.menuType),
							msgType: getDictLabel(${fns:toJson(fns:getDictList('msg_type'))}, row.msgType),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		function sysMenuButton(){
			$.get("${ctx}/ws/wxMenu/sysMenuButton", function(data){
				if(data.ret=="1"){
					alert("微信菜单同步成功！");
					return;
				}else{
					alert("微信菜单同步失败,"+data.message);
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ws/wxMenu/">微信菜单列表</a></li>
		<shiro:hasPermission name="ws:wxMenu:edit"><li><a href="${ctx}/ws/wxMenu/form">微信菜单添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
			<form:form id="searchForm" modelAttribute="wxMenu" action="${ctx}/ws/wxMenu/" method="post" class="form-horizontal">
				<div class="box-body">
					<div class="col-md-4">
						<div class="form-group">
							<label class="col-sm-4 control-label">菜单名称：</label>
							<div class="col-sm-8">
						<form:input path="name" htmlEscape="false" maxlength="255" class="form-control"/>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-md-offset-5">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
					<input onclick="sysMenuButton()" class="btn btn-primary" type="button" value="同步微信菜单"/>
					</div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="treeTable" class="table table-bordered table-striped table-hover dataTable no-footer">
				<thead>
					<tr>
						<th class="text-center">菜单名称</th>
						<th class="text-center">菜单类型</th>
						<th class="text-center">消息类型</th>
						<th class="text-center">模板标题</th>
						<th class="text-center">网页链接类</th>
						<shiro:hasPermission name="ws:wxMenu:edit"><th class="text-center">操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody id="treeTableList"></tbody>
			</table>
			<script type="text/template" id="treeTableTpl">
				<tr id="{{row.id}}" pId="{{pid}}">
					<td class="text-center"><a href="${ctx}/ws/wxMenu/form?id={{row.id}}">
						{{row.name}}
					</a></td>
					<td class="text-center">
						{{dict.menuType}}
					</td>
					<td class="text-center">
						{{dict.msgType}}
					</td>
					<td class="text-center">
						{{row.materialTitle}}
					</td>
					<td class="text-center">
						{{row.url}}
					</td>
					<shiro:hasPermission name="ws:wxMenu:edit">
					<td class="text-center">
		   				<a href="${ctx}/ws/wxMenu/form?id={{row.id}}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
						<a href="${ctx}/ws/wxMenu/delete?id={{row.id}}" onclick="return confirmx('确认要删除该微信菜单及所有子微信菜单吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
						{{#row.isParent}}
						<a href="${ctx}/ws/wxMenu/form?parent.id={{row.id}}" class="btn btn-info btn-sm"><i class="fa fa-plus">&nbsp;添加下级微信菜单</i></a> 
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