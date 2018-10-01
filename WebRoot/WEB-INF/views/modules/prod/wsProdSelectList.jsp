<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品信息管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=id]").each(function(){
				var prodSelect = parent.prodSelect;
				for (var i=0; i<prodSelect.length; i++){
					if (prodSelect[i][0]==$(this).val()){
						this.checked = true;
					}
				}
				$(this).click(function(){
					var id = $(this).val();
					var title = $(this).attr("titleValue");
					parent.prodSelectAddOrDel(id, title);
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
				<form:form id="searchForm" modelAttribute="wsProduct" action="${ctx}/prod/wsProduct/prodSelectList" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">标题</label>
								<div class="col-sm-8">
									<form:input path="title" htmlEscape="false" maxlength="512" class="form-control"/>
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
							<th class="text-center">选择</th>
							<th class="text-center">产品分类</th>
							<th class="text-center">品牌名称</th>
							<th class="text-center">名称</th>
							<th class="text-center">标题</th>
							<th class="text-center">产品状态</th>
							<th class="text-center">是否赠品</th>
							<th class="text-center">是否支持退货</th>
							<th class="text-center">已售数量</th>
						</tr>
					<c:forEach items="${page.list}" var="wsProduct">
						<tr>
							<td class="text-center">
								<input type="checkbox" name="id" value="${wsProduct.id}" titleValue="${fns:abbr(wsProduct.title,40)}" />
							</td>
							<td class="text-center">
								${wsProduct.prodCategoryName}
							</td>
							<td class="text-center">
								${wsProduct.brandName}
							</td>
							<td class="text-center">
								${wsProduct.pname}
							</td>
							<td class="text-center">
								${wsProduct.title}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProduct.onGoodState, 'on_good_state', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProduct.isGift, 'yes_no', '')}
							</td>
							<td class="text-center">
								${fns:getDictLabel(wsProduct.isReturn, 'yes_no', '')}
							</td>
							<td class="text-center">
								${wsProduct.selNum}
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