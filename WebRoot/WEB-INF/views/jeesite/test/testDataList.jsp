<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>单表管理</title>
<meta name="decorator" content="adminlte" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/test/testData/">单表列表</a></li>
		<shiro:hasPermission name="test:testData:edit">
			<li><a href="${ctx}/test/testData/form">单表添加</a></li>
		</shiro:hasPermission>
	</ul>
	
	<section class="content" style="background: redl;padding: 0px">
		<div class="row">
			<div class="col-xs-12">
				<div class="box box-primary">
					<!-- /.box-header -->
					<form:form id="searchForm" modelAttribute="testData" action="${ctx}/test/testData/" method="post" class="form-horizontal">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="box-body">
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-sm-4 control-label">归属用户：</label>
									<div class="col-sm-8">
										<sys:treeselect id="user" name="user.id" value="${testData.user.id}" labelName="user.name" labelValue="${testData.user.name}"
												title="用户" url="/sys/office/treeData?type=3" cssClass="form-control"
												 allowClear="true" notAllowSelectParent="true"/>
					                </div>
								</div>
							</div>
	            			<div class="col-md-4">
								<div class="form-group">
									<label class="col-sm-4 control-label">归属部门：</label>
									<div class="col-sm-8">
										<sys:treeselect id="office" name="office.id" value="${testData.office.id}" labelName="office.name" labelValue="${testData.office.name}"
											title="部门" url="/sys/office/treeData?type=2" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
									</div>
								</div>	
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-sm-4 control-label">归属区域：</label>
									<div class="col-sm-8">
										<sys:treeselect id="area" name="area.id" value="${testData.area.id}" labelName="area.name" labelValue="${testData.area.name}"
											title="区域" url="/sys/area/treeData" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
									</div>
								</div>	
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label  class="col-sm-4 control-label">名称：</label>
									<div class="col-sm-8">
										<form:input path="name" htmlEscape="false" maxlength="100" class="form-control required" />
									</div>
								</div>	
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label  class="col-sm-4 control-label">性别:</label>
									<div class="col-sm-8">
										<form:select path="sex" class="form-control select2">
											<form:option value="" label=""/>
											<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										</form:select>	
									</div>
								</div>	
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-sm-4 control-label">加入日期：</label>
									<div class="col-sm-8">
								<input name="beginInDate" type="text" readonly="readonly" maxlength="20" class="form-control Wdate"
									value="<fmt:formatDate value="${testData.beginInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									</div>
								</div>	
							</div>

			            <div class="col-md-12 col-md-offset-5">
			            	<button id="btnSubmit" type="submit" class="btn btn-primary" data-btn-type="search">查询</button>
			            </div>
			            </div>
					 </form:form>
						
					 <div class="row">
							<div class="col-sm-12">
								<table id="user_table"
									class="table table-bordered table-striped table-hover dataTable no-footer"
									style="width: 100%;" role="grid"
									aria-describedby="user_table_info">
										<tr role="row">
											<th class="text-center sorting_disabled table-title" rowspan="1"
												colspan="1" aria-label="归属用户">归属用户</th>
											<th class="text-center sorting table-title" tabindex="0"
												aria-controls="user_table" rowspan="1" colspan="1"
												aria-label="归属部门: activate to sort column ascending">归属部门</th>
											<th class="text-center sorting table-title" tabindex="0"
												aria-controls="user_table" rowspan="1" colspan="1"
												aria-label="归属区域: activate to sort column ascending">归属区域</th>
											<th class="text-center sorting table-title" tabindex="0"
												aria-controls="user_table" rowspan="1" colspan="1"
												aria-label="名称: activate to sort column ascending">名称</th>
											<th class="text-center sorting table-title" tabindex="0"
												aria-controls="user_table" rowspan="1" colspan="1"
												aria-label="性别: activate to sort column ascending">性别</th>
											<th class="sorting_disabled text-center table-title" rowspan="1"
												colspan="1" aria-label="更新时间">更新时间</th>
											<th class="table-title text-center sorting_disabled " rowspan="1"
												colspan="1" aria-label="备注信息">备注信息</th>
											<shiro:hasPermission name="test:testData:edit">
											<th class="text-center sorting_disabled " rowspan="1"
												colspan="1" aria-label="操作">操作</th>
											</shiro:hasPermission>
										</tr>
									<c:forEach items="${page.list}" var="testData">
										<tr >
											<td class="text-center">${testData.user.name}</td>
											<td class=" text-center">${testData.office.name}</td>
											<td class=" text-center">${testData.area.name}</td>
											<td class=" text-center">${testData.name}</td>
											<td class=" text-center">${fns:getDictLabel(testData.sex, 'sex', '')}</td>
											<td class=" text-center"><fmt:formatDate value="${testData.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td class=" text-center">${testData.remarks}</td>
											<shiro:hasPermission name="test:testData:edit"><td class=" text-center">
							    				<a href="${ctx}/test/testData/form?id=${testData.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;修改</i></span></a>
												<a href="${ctx}/test/testData/delete?id=${testData.id}" onclick="return confirmx('确认要删除该单表吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a>
											</td></shiro:hasPermission>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					 	${page}					 
				</div>
			</div>
		</div>
	</section>
</body>
</html>