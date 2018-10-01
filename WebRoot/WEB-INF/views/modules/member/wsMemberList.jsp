<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员资料管理</title>
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
		<li class="active"><a href="${ctx}/member/wsMember/">会员资料列表</a></li>
<%-- 		<shiro:hasPermission name="member:wsMember:edit"><li><a href="${ctx}/member/wsMember/form">会员资料添加</a></li></shiro:hasPermission> --%>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<form:form id="searchForm" modelAttribute="wsMember" action="${ctx}/member/wsMember/" method="post" class="form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="box-body">
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">昵称：</label>
								<div class="col-sm-8">
									<form:input path="nickname" htmlEscape="false" maxlength="255" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">手机：</label>
								<div class="col-sm-8">
									<form:input path="mobile" htmlEscape="false" maxlength="64" class="form-control"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="col-sm-4 control-label">会员等级：</label>
								<div class="col-sm-8">
									 <form:select path="memberRankId" class="form-control">
										<form:option value="" label="全部"/>
										<form:options items="${listWsMrank}" itemLabel="name" itemValue="id" htmlEscape="false"/>
									 </form:select> 
								</div>
							</div>
						</div>
						<div class="col-md-12 col-md-offset-5"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
						<tr>
							<th class="text-center">昵称</th>
							<th class="text-center">手机</th>
							<th class="text-center">会员级别</th>
							<th class="text-center">积分</th>
							<th class="text-center">余额</th>
							<shiro:hasPermission name="member:wsMember:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					<c:forEach items="${page.list}" var="wsMember">
						<tr>
							<td class="text-center">
							<a href="${ctx}/member/wsMember/form?id=${wsMember.id}"> 
									${wsMember.nickname}
 							</a>
							</td>
							<td class="text-center">
								${wsMember.mobile}
							</td>
							<td class="text-center">
							${wsMember.memberRankName}
							</td>
							<td class="text-center">
								${wsMember.score}
							</td>
							<td class="text-center">
								${wsMember.balance}
							</td>
							<shiro:hasPermission name="member:wsMember:edit"><td>
			    				<a href="${ctx}/member/wsMember/form?id=${wsMember.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;查看</i></span></a>
<%-- 								<a href="${ctx}/member/wsMember/delete?id=${wsMember.id}" onclick="return confirmx('确认要删除该会员资料吗？', this.href)" class="btn btn-warning btn-sm"><i class="fa fa-trash">&nbsp;删除</i></a> --%>
			    				<a href="${ctx}/member/wsMember/list1?id=${wsMember.id}" class="btn btn-primary btn-sm"><span class=""><i class="fa fa-money">&nbsp;用户优惠券</i></span></a>
			    				<a href="${ctx}/member/wsMember/list2?id=${wsMember.id}" class="btn btn-info btn-sm"><span class=""><i class="fa fa-calendar">&nbsp;消费记录</i></span></a>
			    				<a href="${ctx}/member/wsMember/list3?id=${wsMember.id}" class="btn btn-success btn-sm"><span class=""><i class="fa fa-battery-three-quarters">&nbsp;充值记录</i></span></a>
			    				<a href="${ctx}/member/wsMember/list4?id=${wsMember.id}" class="btn btn-danger btn-sm"><span class=""><i class="fa fa-diamond">&nbsp;奖励记录</i></span></a>
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