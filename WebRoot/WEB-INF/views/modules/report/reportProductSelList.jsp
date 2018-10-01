<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>热销产品管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/reportProductSelList.js"></script>
	<meta name="decorator" content="adminlte"/>
	
	<script type="text/javascript">
	$(document).ready(function(){
		$("#chaxun").click(function(){
		var prdname = $("#productName").val();
		$("#baobiaoiframe").attr("src","${ctx}/report/reportProductSel/report?prdname="+prdname).ready();
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/reportProductSel/">热销产品列表</a></li>
		<shiro:hasPermission name="report:reportProductSel:edit"><li><a href="${ctx}/report/reportProductSel/form">热销产品添加</a></li></shiro:hasPermission>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="reportProductSel" action="${ctx}/report/reportProductSel/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">统计日期：</label>
								<div class="col-sm-8">
									<input name="countDate" type="text" readonly="readonly" maxlength="20" class="form-control Wdate"
										value="<fmt:formatDate value="${reportProductSel.countDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">产品名称：</label>
								<div class="col-sm-8">
									<form:input path="productName" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<input id="chaxun" class="btn btn-primary" type="button" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<iframe id="baobiaoiframe" frameborder=0 width="100%" height="600" marginheight=0 marginwidth=0 scrolling=yes src="${ctx}/report/reportProductSel/report"></iframe>
						 
				</div>
			</div>
		</div>
	</section>
</body>
</html>