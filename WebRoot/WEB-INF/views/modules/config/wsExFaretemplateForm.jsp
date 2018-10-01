<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>快递模版管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
	$(document).ready(function() {
		/*
		 * 根据选择的包邮条件动态的显示隐藏表格
		 */
		var radioisInclPostAgeByif = $("input[name='isInclPostAgeByif']:checked").val();
		if (radioisInclPostAgeByif == "1") {
			$("#divwsExInclpostageprovisoList").show();
		} else {
			$("#divwsExInclpostageprovisoList").hide();
		}

		$("input[name='isInclPostAgeByif']").click(function() {
			if ($(this).val() == "1") {
				$("#divwsExInclpostageprovisoList").show();
			} else {
				$("#divwsExInclpostageprovisoList").hide();
			}
		});
		//根据计价方式选择快递费用模板
		var radiovaluationModel = $("#valuationModel").val();
		checkValuationModel(radiovaluationModel);
		$("#valuationModel").click(function() {
			checkValuationModel($(this).val());
		});

		$("#inputForm").validate({
			submitHandler : function(form) {
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox") || element.is(":radio")
						|| element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});
	function addRow(list, idx, tpl, row) {
		$(list).append(Mustache.render(tpl, {
			idx : idx,
			delBtn : true,
			row : row
		}));
		$(list + idx).find("select").each(function() {
			$(this).val($(this).attr("data-value"));
		});
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function() {
			var ss = $(this).attr("data-value").split(',');
			for (var i = 0; i < ss.length; i++) {
				if ($(this).val() == ss[i]) {
					$(this).attr("checked", "checked");
				}
			}
		});
		var radiovaluationModel = $("#valuationModel").val();
		checkValuationModel(radiovaluationModel);
		if(idx==0 && list=="#wsExCarrymodeList"){
			$("#wsExCarrymodeList0_isDefault0").attr("checked",true);//打勾
			$("#wsExCarrymodeList0_isDefault0").attr("data-value",1);//打勾
			$("#wsExCarrymodeList0_isDefault1").attr("data-value",0);//打勾
			$("#wsExCarrymodeList0_regionName").val("全国");
			$("#wsExCarrymodeList0_regionId").val("0");
			$("#wsExCarrymodeList0_regionButton").attr("class","input-group disabled");
		}
		if(idx!=0 && list=="#wsExCarrymodeList"){
			$("#wsExCarrymodeList"+idx+"_isDefault1").attr("checked",true);//打勾
			$("#wsExCarrymodeList"+idx+"_isDefault1").attr("data-value",1);//打勾
			$("#wsExCarrymodeList"+idx+"_isDefault0").attr("data-value",0);//打勾
		}
		if(idx==0 && list=="#wsExInclpostageprovisoList"){
			$("#wsExInclpostageprovisoList0_regionIdName").val("全国");
			$("#wsExInclpostageprovisoList0_regionIdId").val("0");
			$("#wsExInclpostageprovisoList0_regionIdButton").attr("class","input-group disabled");
		}
	}
	function delRow(obj, prefix) {
		var id = $(prefix + "_id");
		var delFlag = $(prefix + "_delFlag");
		if (id.val() == "") {
			$(obj).parent().parent().remove();
		} else if (delFlag.val() == "0") {
			delFlag.val("1");
			$(obj).html("&divide;").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		} else if (delFlag.val() == "1") {
			delFlag.val("0");
			$(obj).html("&times;").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}
	
	
	function checkValuationModel(modelValue){
		if (modelValue == "0") {
			$("#thfirstPiece").show();
			$("#thsecondPiece").show();
			$("#thfirstAmount").show();
			$("#thsecondAmount").show();
			$("#thfirstWeight").hide();
			$("#thfirstBulk").hide();
			$("#thsecondWeight").hide();
			$("#thsecondBulk").hide();
			$("#thfirstPieceProviso").show();
			$("#thfirstWeightProviso").hide();
			$("#thfirstBulkProviso").hide();

			$("td[name='tdfirstPiece']").show();
			$("td[name='tdsecondPiece']").show();
			$("td[name='tdfirstAmount']").show();
			$("td[name='tdsecondAmount']").show();
			$("td[name='tdfirstWeight']").hide();
			$("td[name='tdfirstBulk']").hide();
			$("td[name='tdsecondWeight']").hide();
			$("td[name='tdsecondBulk']").hide();

		} else if (modelValue == "2") {
			$("#thfirstPiece").hide();
			$("#thsecondPiece").hide();
			$("#thfirstAmount").show();
			$("#thsecondAmount").show();
			$("#thfirstWeight").hide();
			$("#thfirstBulk").show();
			$("#thsecondWeight").hide();
			$("#thsecondBulk").show();
			$("#thfirstPieceProviso").hide();
			$("#thfirstWeightProviso").hide();
			$("#thfirstBulkProviso").show();
			
			$("td[name='tdfirstPiece']").hide();
			$("td[name='tdsecondPiece']").hide();
			$("td[name='tdfirstAmount']").show();
			$("td[name='tdsecondAmount']").show();
			$("td[name='tdfirstWeight']").hide();
			$("td[name='tdfirstBulk']").show();
			$("td[name='tdsecondWeight']").hide();
			$("td[name='tdsecondBulk']").show();
		} else {
			$("#thfirstPiece").hide();
			$("#thsecondPiece").hide();
			$("#thfirstAmount").show();
			$("#thsecondAmount").show();
			$("#thfirstWeight").show();
			$("#thfirstBulk").hide();
			$("#thsecondWeight").show();
			$("#thsecondBulk").hide();
			$("#thfirstPieceProviso").hide();
			$("#thfirstWeightProviso").show();
			$("#thfirstBulkProviso").hide();
			
			$("td[name='tdfirstPiece']").hide();
			$("td[name='tdsecondPiece']").hide();
			$("td[name='tdfirstAmount']").show();
			$("td[name='tdsecondAmount']").show();
			$("td[name='tdfirstWeight']").show();
			$("td[name='tdfirstBulk']").hide();
			$("td[name='tdsecondWeight']").show();
			$("td[name='tdsecondBulk']").hide();
		}
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/config/wsExFaretemplate/">快递模版列表</a></li>
		<li class="active"><a href="${ctx}/config/wsExFaretemplate/form?id=${wsExFaretemplate.id}">快递模版<shiro:hasPermission name="config:wsExFaretemplate:edit">${not empty wsExFaretemplate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="config:wsExFaretemplate:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsExFaretemplate" action="${ctx}/config/wsExFaretemplate/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">
				<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	快递模板基本信息
		          	</h2>
		        	</div>
		      	</div>	
				<div class="form-group">
					<label  class="col-sm-2 control-label">模版名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">发货时间：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="dispatchTime" class="form-control ">
							<form:options items="${fns:getDictList('config_dispatchtime')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">宝贝地址：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="shopAddr" htmlEscape="false" maxlength="2500" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">计价方式：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="valuationModel" class="form-control ">
							<form:options items="${fns:getDictList('config_valmodel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<label  class="col-sm-2 control-label">运送方式：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="carryWay" class="form-control ">
							<form:options items="${fns:getDictList('config_carryway')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">备注：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	快递费用模板
		          	</h2>
		          	<h6>除指定地区外，其余地区均采用第一条默认模板：</h6>
		        	</div>
		      	</div>
					<div id="divwsExCarrymodeList" class="form-group">
						<label  class="col-sm-2 control-label"></label>
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
								<thead>
									<tr>
										<th class="hide"></th>
										<th>地区</th>
										<th id="thfirstPiece">首件数量(个)</th>
										<th id="thfirstWeight">首件重量(kg)</th>
										<th id="thfirstBulk">首体积(立方米)</th>
										<th id="thfirstAmount">首费用(元)</th>
										<th id="thsecondPiece">续件(个)</th>
										<th id="thsecondWeight">续重量(kg)</th>
										<th id="thsecondBulk">续体积(立方米)</th>
										<th id="thsecondAmount">续费(元)</th>
										<th>是否默认</th>
										<shiro:hasPermission name="config:wsExFaretemplate:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
									</tr>
								</thead>
								<tbody id="wsExCarrymodeList">
								</tbody>
								<shiro:hasPermission name="config:wsExFaretemplate:edit"><tfoot>
									<tr><td colspan="13"><a href="javascript:" onclick="addRow('#wsExCarrymodeList', wsExCarrymodeRowIdx, wsExCarrymodeTpl);wsExCarrymodeRowIdx = wsExCarrymodeRowIdx + 1;" class="btn">新增</a></td></tr>
								</tfoot></shiro:hasPermission>
							</table>
							<script type="text/template" id="wsExCarrymodeTpl">//<!--
								<tr id="wsExCarrymodeList{{idx}}">
									<td class="hide">
										<input id="wsExCarrymodeList{{idx}}_id" name="wsExCarrymodeList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
										<input id="wsExCarrymodeList{{idx}}_delFlag" name="wsExCarrymodeList[{{idx}}].delFlag" type="hidden" value="0"/>
									</td>
									<td>
										<sys:treeselect id="wsExCarrymodeList{{idx}}_region" name="wsExCarrymodeList[{{idx}}].region" value="{{row.region}}" labelName="wsExCarrymodeList[{{idx}}].regionName" labelValue="{{row.regionName}}"
											title="区域" url="/sys/area/treeFirstData" cssClass="form-control required" allowClear="true" notAllowSelectParent="false"/>
									</td>
									<td name="tdfirstPiece">
										<input id="wsExCarrymodeList{{idx}}_firstPiece" name="wsExCarrymodeList[{{idx}}].firstPiece" type="text" value="{{row.firstPiece}}" maxlength="2500" class="form-control required digits"/>
									</td>
									<td name="tdfirstWeight">
										<input id="wsExCarrymodeList{{idx}}_firstWeight" name="wsExCarrymodeList[{{idx}}].firstWeight" type="text" value="{{row.firstWeight}}" maxlength="255" class="form-control required number maxNumber"/>
									</td>
									<td name="tdfirstBulk">
										<input id="wsExCarrymodeList{{idx}}_firstBulk" name="wsExCarrymodeList[{{idx}}].firstBulk" type="text" value="{{row.firstBulk}}" maxlength="10" class="form-control required number maxNumber"/>
									</td>
									<td name="tdfirstAmount">
										<input id="wsExCarrymodeList{{idx}}_firstAmount" name="wsExCarrymodeList[{{idx}}].firstAmount" type="text" value="{{row.firstAmount}}" maxlength="255" class="form-control required number maxNumber"/>
									</td>
									<td name="tdsecondPiece">
										<input id="wsExCarrymodeList{{idx}}_secondPiece" name="wsExCarrymodeList[{{idx}}].secondPiece" type="text" value="{{row.secondPiece}}" maxlength="2500" class="form-control required digits"/>
									</td>
									<td name="tdsecondWeight">
										<input id="wsExCarrymodeList{{idx}}_secondWeight" name="wsExCarrymodeList[{{idx}}].secondWeight" type="text" value="{{row.secondWeight}}" maxlength="255" class="form-control required number maxNumber"/>
									</td>
									<td name="tdsecondBulk">
										<input id="wsExCarrymodeList{{idx}}_secondBulk" name="wsExCarrymodeList[{{idx}}].secondBulk" type="text" value="{{row.secondBulk}}" maxlength="255" class="form-control required number maxNumber"/>
									</td>
									<td name="tdsecondAmount">
										<input id="wsExCarrymodeList{{idx}}_secondAmount" name="wsExCarrymodeList[{{idx}}].secondAmount" type="text" value="{{row.secondAmount}}" maxlength="255" class="form-control required number maxNumber"/>
									</td>
									<td>
										<c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
											<span><input id="wsExCarrymodeList{{idx}}_isDefault${dictStatus.index}" name="wsExCarrymodeList[{{idx}}].isDefault" type="radio" value="${dict.value}" data-value="{{row.isDefault}}" disabled><label for="wsExCarrymodeList{{idx}}_isDefault${dictStatus.index}">${dict.label}</label></span>
										</c:forEach>
									</td>
								
									<shiro:hasPermission name="config:wsExFaretemplate:edit"><td class="text-center" width="10">
										{{#delBtn}}<span class="close" onclick="delRow(this, '#wsExCarrymodeList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
									</td></shiro:hasPermission>
								</tr>//-->
							</script>
							<script type="text/javascript">
								var wsExCarrymodeRowIdx = 0, wsExCarrymodeTpl = $("#wsExCarrymodeTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								$(document).ready(function() {
									var data = ${fns:toJson(wsExFaretemplate.wsExCarrymodeList)};
									for (var i=0; i<data.length; i++){
										addRow('#wsExCarrymodeList', wsExCarrymodeRowIdx, wsExCarrymodeTpl, data[i]);
										wsExCarrymodeRowIdx = wsExCarrymodeRowIdx + 1;
									}
								});
							</script>
						</div>
					</div>
					<div class="row">
			        	<div class="col-xs-12">
			          	<h2 class="page-header">
			           	 	指定包邮条件
			          	</h2>
			        	</div>
			      	</div>
					<div class="form-group">
						<label  class="col-sm-2 control-label">指定条件包邮：
						</label>
						<div class="col-sm-4 controls" id="control-label-left">
							<form:radiobuttons path="isInclPostAgeByif" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
						</div>
					</div>
					<div id="divwsExInclpostageprovisoList" class="form-group">
						<label  class="col-sm-2 control-label">快递包邮条件表：</label>
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
								<thead>
									<tr>
										<th class="hide"></th>
										<th>包邮地区</th>
										<th>满多少金额包邮(元)</th>
										<shiro:hasPermission name="config:wsExFaretemplate:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
									</tr>
								</thead>
								<tbody id="wsExInclpostageprovisoList">
								</tbody>
								<shiro:hasPermission name="config:wsExFaretemplate:edit"><tfoot>
									<tr><td colspan="8"><a href="javascript:" onclick="addRow('#wsExInclpostageprovisoList', wsExInclpostageprovisoRowIdx, wsExInclpostageprovisoTpl);wsExInclpostageprovisoRowIdx = wsExInclpostageprovisoRowIdx + 1;" class="btn">新增</a></td></tr>
								</tfoot></shiro:hasPermission>
							</table>
							<script type="text/template" id="wsExInclpostageprovisoTpl">//<!--
								<tr id="wsExInclpostageprovisoList{{idx}}">
									<td class="hide">
										<input id="wsExInclpostageprovisoList{{idx}}_id" name="wsExInclpostageprovisoList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
										<input id="wsExInclpostageprovisoList{{idx}}_delFlag" name="wsExInclpostageprovisoList[{{idx}}].delFlag" type="hidden" value="0"/>
									</td>
									<td>
										<sys:treeselect id="wsExInclpostageprovisoList{{idx}}_regionId" name="wsExInclpostageprovisoList[{{idx}}].regionId" value="{{row.regionId}}" labelName="wsExInclpostageprovisoList[{{idx}}].regionName" labelValue="{{row.regionName}}"
											title="区域" url="/sys/area/treeFirstData" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
									</td>
									<td>
										<input id="wsExInclpostageprovisoList{{idx}}_amount" name="wsExInclpostageprovisoList[{{idx}}].amount" type="text" value="{{row.amount}}" maxlength="255" class="form-control required number maxNumber" min="0"/>
									</td>
									
									<shiro:hasPermission name="config:wsExFaretemplate:edit"><td class="text-center" width="10">
										{{#delBtn}}<span class="close" onclick="delRow(this, '#wsExInclpostageprovisoList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
									</td></shiro:hasPermission>
								</tr>//-->
							</script>
							<script type="text/javascript">
								var wsExInclpostageprovisoRowIdx = 0, wsExInclpostageprovisoTpl = $("#wsExInclpostageprovisoTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								$(document).ready(function() {
									var data = ${fns:toJson(wsExFaretemplate.wsExInclpostageprovisoList)};
									for (var i=0; i<data.length; i++){
										addRow('#wsExInclpostageprovisoList', wsExInclpostageprovisoRowIdx, wsExInclpostageprovisoTpl, data[i]);
										wsExInclpostageprovisoRowIdx = wsExInclpostageprovisoRowIdx + 1;

									}
								});
							</script>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="config:wsExFaretemplate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>