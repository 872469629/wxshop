<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员资料管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/member/wsMember/">会员资料列表</a></li>
		<li class="active"><a href="${ctx}/member/wsMember/form?id=${wsMember.id}">会员资料<shiro:hasPermission name="member:wsMember:edit">${not empty wsMember.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="member:wsMember:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsMember" action="${ctx}/member/wsMember/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
			      	<div class="row">
			        	<div class="col-xs-12">
			          	<h2 class="page-header">
			           	 	会员资料
			          	</h2>
			        	</div>
			      	</div>
					<div class="form-group">
					<label  class="col-sm-2 control-label">昵称：
					</label>
					<div class="col-sm-4 controls">
						<form:input readonly="true" path="nickname" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
					<label  class="col-sm-2 control-label">余额：
					</label>
					<div class="col-sm-4 controls">
						<form:input disabled="true" path="balance" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">会员级别：
					</label>
					<div class="col-sm-4 controls">
						 <form:select disabled="true" path="memberRankId" class="form-control">
										<form:option value="" label="全部"/>
										<form:options items="${listWsMrank}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						 </form:select> 
					</div>
					<label  class="col-sm-2 control-label">手机：
					</label>
					<div class="col-sm-4 controls">
						<form:input disabled="true" path="mobile" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
				</div>
			
				<div class="form-group">
					<label  class="col-sm-2 control-label">积分：
					</label>
					<div class="col-sm-4 controls">
						<form:input disabled="true" path="score" htmlEscape="false" maxlength="11" class="form-control "/>
					</div>
					
				</div>				
			 	<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	用户属性
		          	</h2>
		        	</div>
		      	</div>
			      	<div class="form-group">
				      	<c:forEach items="${wsMemberAttrList}" var="wsMemberAttr" varStatus="wsMemberAttrState">		
								<label  class="col-sm-2 control-label">${wsMemberAttr.attrName}：</label>
								<%-- <input id="wsMemberAttr.id" name="wsMemberAttr.id" type="hidden" value="${wsMemberAttr.id}"/>
								<input id="wsMemberAttr.attrName" name="wsMemberAttr.attrName" type="hidden" value="${wsMemberAttr.attrName}"/> --%>
								<div class="col-sm-4 controls" id="control-label-left">
									${wsMemberAttr.attrValue}
									<%-- <input id="wsMemberAttr.attrValue" name="wsMemberAttr.attrValue" type="text" value="${wsMemberAttr.attrValue}" maxlength="512" class="form-control "/> --%>
								</div>
				      	</c:forEach>
			      	</div>
		      	
			      	<div class="row">
			        	<div class="col-xs-12">
			          	<h2 class="page-header">
			           	 	会员地址
			          	</h2>
			        	</div>
			      	</div>
					<div class="form-group">
						<label  class="col-sm-1 control-label"></label>
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
					<div class="form-group">
						<label  class="col-sm-1 control-label"></label>
						<!-- <label  class="col-sm-2 control-label">会员地址信息：</label> -->
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
								<thead>
									<tr>
										<th class="hide"></th>
										<th>收货人名称</th>
										<th>联系电话</th>
										<th>邮编</th>
										<th>收货地址</th>
										<th>是否默认地址</th>
									</tr>
								</thead>
								<tbody id="wsAddressList">
								</tbody>
								
							</table>
							<script type="text/template" id="wsAddressTpl">//<!--
								<tr id="wsAddressList{{idx}}">
									<td class="hide">
										<input id="wsAddressList{{idx}}_id" name="wsAddressList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
										<input id="wsAddressList{{idx}}_delFlag" name="wsAddressList[{{idx}}].delFlag" type="hidden" value="0"/>
									</td>
									<td>
										<input disabled="true" id="wsAddressList{{idx}}_consignee" name="wsAddressList[{{idx}}].consignee" type="text" value="{{row.consignee}}" maxlength="50" class="form-control "/>
									</td>
									<td>
										<input disabled="true" id="wsAddressList{{idx}}_tel" name="wsAddressList[{{idx}}].tel" type="text" value="{{row.tel}}" maxlength="30" class="form-control "/>
									</td>
									<td>
										<input disabled="true" id="wsAddressList{{idx}}_zipCode" name="wsAddressList[{{idx}}].zipCode" type="text" value="{{row.zipCode}}" maxlength="20" class="form-control "/>
									</td>
								
									<td>
										<input disabled="true" id="wsAddressList{{idx}}_address" name="wsAddressList[{{idx}}].address" type="text" value="{{row.city}}{{row.address}}" maxlength="1000" class="form-control "/>
									</td>
									<td>
										<c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
											<span><input id="wsAddressList{{idx}}_isDefault${dictStatus.index}" name="wsAddressList[{{idx}}].isDefault" type="radio" value="${dict.value}" data-value="{{row.isDefault}}" disabled><label for="wsAddressList{{idx}}_isDefault${dictStatus.index}">${dict.label}</label></span>
										</c:forEach>
									</td>
									
								</tr>//-->
							</script>
							<script type="text/javascript">
								var wsAddressRowIdx = 0, wsAddressTpl = $("#wsAddressTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								$(document).ready(function() {
									var data = ${fns:toJson(wsMember.wsAddressList)};
									for (var i=0; i<data.length; i++){
										addRow('#wsAddressList', wsAddressRowIdx, wsAddressTpl, data[i]);
										wsAddressRowIdx = wsAddressRowIdx + 1;
									}
								});
							</script>
						</div>
					</div>

				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
<%-- 					<shiro:hasPermission name="member:wsMember:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>