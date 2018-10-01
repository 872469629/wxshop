<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		var data=new Object();
		$(document).ready(function() {
			$("input[name='indexcheck']").click(function (event){
				$("input[name='indexcheck']:checked").each(function(){
					data.id=$(this).val();
					data.name=$(this).attr("value2");
				});
			});
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
				<form:form id="searchForm" modelAttribute="wsBrand" action="${ctx}/prod/wsBrand/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">中文名称：</label>
								<div class="col-sm-8">
									<form:input path="cnname" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
					<thead>
						<tr>
							<th class="text-center">序列</th>
							<th class="text-center">中文名称</th>
							<th class="text-center">英文名称</th>
							<th class="text-center">logo</th>
							<th class="text-center">状态</th>
							<th class="text-center">官网地址</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="wsBrand">
						<tr>
							<td><input id="indexcheck" type="radio" name="indexcheck" value="${wsBrand.id}" value2="${wsBrand.cnname}"/></td>
							<td class="text-center">
								${wsBrand.cnname}
							</td>
							<td class="text-center">
								${wsBrand.enname}
							</td>
							<td class="text-center">
							
					<img alt="" src="${wsBrand.logo}" style="width: 100px;height: 50px">
							</td>
							<td class="text-center">
								${wsBrand.state}
							</td>
							<td class="text-center">
								${wsBrand.websiteurl}
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
					${page}					 
				</div>
			</div>
		</div>
	</section>
</body>
</html>