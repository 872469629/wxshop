<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退货管理管理</title>
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
		<li><a href="${ctx}/returnback/wsReturn/">退货管理列表</a></li>
		<li class="active"><a href="${ctx}/returnback/wsReturn/form?id=${wsReturn.id}">退货管理<shiro:hasPermission name="returnback:wsReturn:edit">${not empty wsReturn.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="returnback:wsReturn:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsReturn" action="${ctx}/returnback/wsReturn/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	退货单信息
		          	</h2>
		        	</div>
		      	</div>	
				<div class="form-group">
					<label  class="col-sm-2 control-label">订单号：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="orderSn" htmlEscape="false" maxlength="255" class="form-control " readonly="true"/>
					</div>
					<label  class="col-sm-2 control-label">退货单状态：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="state" class="form-control " disabled="true">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('return_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">实际退货金额：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="returnAmount" htmlEscape="false" maxlength="11" class="form-control  number maxNumber"/>
					</div>
					<label  class="col-sm-2 control-label">退货扣除积分：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="returnScore" htmlEscape="false" maxlength="11" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">退货原因：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="255" class="form-control " readonly="true"/>
					</div>
					<label  class="col-sm-2 control-label">备注：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				
								<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	退货产品明细
		          	</h2>
		        	</div>
		      	</div>
					<div class="form-group">
						<label  class="col-sm-2 control-label"></label>
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
								<thead>
									<tr>
										<th class="hide"></th>
										<th>产品标题</th>
、										<th>规格名称</th>
										<th>退货数量</th>
										<th>购买单价</th>
										<th>实际退货单价</th>
										<th>退货总价</th>
									</tr>
								</thead>
								<tbody id="wsReturnItemList">
								</tbody>
							</table>
							<script type="text/template" id="wsReturnItemTpl">//<!--
								<tr id="wsReturnItemList{{idx}}">
									<td class="hide">
										<input id="wsReturnItemList{{idx}}_id" name="wsReturnItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
										<input id="wsReturnItemList{{idx}}_delFlag" name="wsReturnItemList[{{idx}}].delFlag" type="hidden" value="0"/>
									</td>
									<td>
										<input id="wsReturnItemList{{idx}}_wsProduct.title" name="wsReturnItemList[{{idx}}].wsProduct.title" type="text" value="{{row.wsProduct.title}}"  class="form-control "/>
									</td>
									<td>
										<input id="wsReturnItemList{{idx}}_skuName" name="wsReturnItemList[{{idx}}].skuName" type="text" value="{{row.skuName}}" maxlength="500" class="form-control "/>
									</td>
									<td>
										<input id="wsReturnItemList{{idx}}_quantity" name="wsReturnItemList[{{idx}}].quantity" type="text" value="{{row.quantity}}" maxlength="255" class="form-control "/>
									</td>
									<td>
										<input id="wsReturnItemList{{idx}}_unitPrice" name="wsReturnItemList[{{idx}}].unitPrice" type="text" value="{{row.unitPrice}}" maxlength="11" class="form-control "/>
									</td>
									<td>
										<input id="wsReturnItemList{{idx}}_reallyUnitPrice" name="wsReturnItemList[{{idx}}].reallyUnitPrice" type="text" value="{{row.reallyUnitPrice}}" maxlength="11" class="form-control "/>
									</td>
									<td>
										<input id="wsReturnItemList{{idx}}_reallyPrice" name="wsReturnItemList[{{idx}}].reallyPrice" type="text" value="{{row.reallyPrice}}" maxlength="11" class="form-control "/>
									</td>
								</tr>//-->
							</script>
							<script type="text/javascript">
								var wsReturnItemRowIdx = 0, wsReturnItemTpl = $("#wsReturnItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								$(document).ready(function() {
									var data = ${fns:toJson(wsReturn.wsReturnItemList)};
									for (var i=0; i<data.length; i++){
										addRow('#wsReturnItemList', wsReturnItemRowIdx, wsReturnItemTpl, data[i]);
										wsReturnItemRowIdx = wsReturnItemRowIdx + 1;
									}
								});
							</script>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<c:if test="${wsReturn.state=='2'||wsReturn.state=='1'}">
						<input type="hidden" id="returnState" name="returnState" />
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="微信自动退款" onclick="$('#returnState').val('2')"/>
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="人工手工退款" onclick="$('#returnState').val('3')"/>
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="拒绝退款" onclick="$('#returnState').val('5')"/>
					</c:if>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>