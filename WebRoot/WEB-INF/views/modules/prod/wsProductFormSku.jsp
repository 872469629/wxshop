<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品信息管理</title>
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
		<li><a href="${ctx}/prod/wsProduct/">产品信息列表</a></li>
		<li class="active"><a href="${ctx}/prod/wsProduct/form?id=${wsProduct.id}">产品信息<shiro:hasPermission name="prod:wsProduct:edit">${not empty wsProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prod:wsProduct:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsProduct" action="${ctx}/prod/wsProduct/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="prodCategoryId"  />
				<form:hidden path="prodCategoryName" htmlEscape="false" maxlength="200" class="form-control " readonly="true"/>
				<form:hidden path="brandName"  htmlEscape="false" maxlength="64" class="form-control LayeropenWindow " readonly="true"/>
				<form:hidden path="brandId"/>		
				<form:hidden path="pname" htmlEscape="false" maxlength="128" class="form-control "/>
				<form:hidden path="rangePrice" htmlEscape="false" maxlength="11" class="form-control " readonly="true"/>
				<form:hidden path="title" htmlEscape="false" maxlength="512" class="form-control "/>
				<form:hidden path="keyword" htmlEscape="false" maxlength="255" class="form-control "/>
				<form:hidden path="prodContent" htmlEscape="false" maxlength="255" class="form-control "/>
				<form:hidden path="onGoodState" htmlEscape="false" maxlength="255" class="form-control "/>
				<input name="onGoodTime" type="hidden" readonly="readonly" maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${wsProduct.onGoodTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="disabled"/>
				<form:hidden path="defaultPrice" htmlEscape="false" maxlength="255" class="form-control "/>
				<form:hidden path="defaultReallyPrice" htmlEscape="false" maxlength="11" class="form-control " />
				<form:hidden path="defaultRewardMoney" htmlEscape="false" maxlength="255" class="form-control "/>
				<form:hidden path="defaultNum" htmlEscape="false" maxlength="11" class="form-control " />
				<form:hidden path="expressId" htmlEscape="false" maxlength="255" class="form-control "/>
				<form:hidden path="pnumber" htmlEscape="false" maxlength="255" class="form-control "/>
				<form:hidden path="volume" htmlEscape="false" class="form-control "/>
				<form:hidden path="weight" htmlEscape="false" class="form-control "/>
				<form:hidden path="isReturn" htmlEscape="false" class="form-control "/>
				<form:hidden path="returnDate" htmlEscape="false" maxlength="11" class="form-control "/>
				<form:hidden path="warehouse" htmlEscape="false" maxlength="64" class="form-control "/>
				<form:hidden path="isHomeRecommd" htmlEscape="false" maxlength="64" class="form-control "/>
				<form:hidden path="isGift" htmlEscape="false" maxlength="64" class="form-control "/>
				<form:hidden path="selNum" htmlEscape="false" maxlength="11" class="form-control "/>
				<form:hidden path="clickNum" htmlEscape="false" class="form-control "/>
				<form:hidden path="prodImage" htmlEscape="false" class="form-control "/>
				<form:hidden path="prodImages" htmlEscape="false" class="form-control "/>
				<form:hidden path="isBaseChange" htmlEscape="false" class="form-control "/>
				<form:hidden path="isSelChange" htmlEscape="false" class="form-control "/>
				<form:hidden path="isAgentProduct" htmlEscape="false" class="form-control "/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="row">
			 	<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	基本属性
		          	</h2>
		        	</div>
		      	</div>
		      	<div class="form-group">
		      	<c:forEach items="${wsProdAttributeBase}" var="wsProdAttribute" varStatus="wsProdAttributeState">		
						<label  class="col-sm-2 control-label">${wsProdAttribute.attrName}</label>
						<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_id" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteId" type="hidden" value="${wsProdAttribute.id}"/>
						<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteName" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteName" type="hidden" value="${wsProdAttribute.attrName}"/>
						<c:if test="${wsProdAttribute.inputType ne '4'}">
						<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValueName" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValueName" type="hidden" value="${wsProdAttribute.defauleValue}" maxlength="512" class="form-control " readonly="readonly"/>
						<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValue" type="hidden" value="${wsProdAttribute.defauleValue}" maxlength="512" class="form-control " readonly="readonly"/>
						</c:if>
						<div class="col-sm-4 controls" id="control-label-left">
							<!-- 单选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '1'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValue"   disabled="disabled"
										style="margin: 5px;" type="radio" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 复选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '2'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValue"  disabled="disabled"
										style="margin: 5px;" type="checkbox" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 下拉框 -->
							<c:if test="${wsProdAttribute.inputType eq '3'}">
								<select id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValue"  class="form-control " disabled="disabled">
									<option value=""></option>
									<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
										<option value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}</option>
									</c:forEach>
								</select>
							</c:if>
							<!-- 文本框 -->
							<c:if test="${wsProdAttribute.inputType eq '4'}">
									<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValueName" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValueName" type="text" value="${wsProdAttribute.defauleValue}" maxlength="512" class="form-control " readonly="readonly"/>
							</c:if>
						</div>
		      	</c:forEach>
		      	</div>
		      	</div>
		      	<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	销售属性
		          	</h2>
		        	</div>
		      	</div>
		      	<div class="form-group">
		      	<c:forEach items="${wsProdAttributeSel}" var="wsProdAttribute" varStatus="wsProdAttributeState">		
						<label  class="col-sm-2 control-label">${wsProdAttribute.attrName}</label>
						<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_id" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteId" type="hidden" value="${wsProdAttribute.id}"/>
						<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteName" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteName" type="hidden" value="${wsProdAttribute.attrName}"/>
						<c:if test="${wsProdAttribute.inputType ne '4'}">
						<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValueName" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValueName" type="hidden" value="${wsProdAttribute.defauleValue}" />
						<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValue" type="hidden" value="${wsProdAttribute.defauleValue}" />
						</c:if>
						<div class="col-sm-4 controls" id="control-label-left">
							<!-- 单选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '1'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValue"  disabled="disabled"
										style="margin: 5px;" type="radio" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 复选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '2'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValue" disabled="disabled"
										style="margin: 5px;" type="checkbox" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 下拉框 -->
							<c:if test="${wsProdAttribute.inputType eq '3'}">
								<select id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValue"  class="form-control " disabled="disabled">
									<option value=""></option>
									<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
										<option value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}</option>
									</c:forEach>
								</select>
							</c:if>
							<!-- 文本框 -->
							<c:if test="${wsProdAttribute.inputType eq '4'}">
									<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValueName" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValueName" type="text" value="${wsProdAttribute.defauleValue}" maxlength="512" class="form-control " readonly="readonly"/>
							</c:if>
						</div>
		      	</c:forEach>
		      	</div>
		      	
			     <div class="row">
			        <div class="col-xs-12">
			          <h2 class="page-header">
			           	 产品SKU明细
			          </h2>
			        </div>
			      </div>
					<div class="form-group">
						<label  class="col-sm-1 control-label"></label>
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
								<thead>
									<tr>
										<th class="hide"></th>
										<th style="width: 50%">sku名称</th>
										<th style="width: 10%">剩余数量</th>
										<th style="width: 10%">价格</th>
										<th style="width: 10%">实际价格</th>
										<th style="width: 10%">分销金额</th>
									</tr>
								</thead>
								<tbody id="wsProdSkuList">
								</tbody>
							</table>
							<script type="text/template" id="wsProdSkuTpl">//<!--
								<tr id="wsProdSkuList{{idx}}">
									<td class="hide">
										<input id="wsProdSkuList{{idx}}_id" name="wsProdSkuList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
										<input id="wsProdSkuList{{idx}}_delFlag" name="wsProdSkuList[{{idx}}].delFlag" type="hidden" value="0"/>
										<input id="wsProdSkuList{{idx}}_attributeValues" name="wsProdSkuList[{{idx}}].attributeValues" type="hidde" value="{{row.attributeValues}}" />
										<input id="wsProdSkuList{{idx}}_attrivalueValues" name="wsProdSkuList[{{idx}}].attrivalueValues" type="hidde" value="{{row.attrivalueValues}}" />
									</td>
									<td>
										<input id="wsProdSkuList{{idx}}_skuName" name="wsProdSkuList[{{idx}}].skuName" type="text" value="{{row.skuName}}" maxlength="512" class="form-control " readonly="readonly" required/>
									</td>
									<td>
										<input id="wsProdSkuList{{idx}}_surplusQuantity" name="wsProdSkuList[{{idx}}].surplusQuantity" type="text" value="{{row.surplusQuantity}}" maxlength="11" class="form-control "/>
									</td>
									<td>
										<input id="wsProdSkuList{{idx}}_price" name="wsProdSkuList[{{idx}}].price" type="text" value="{{row.price}}" maxlength="11" class="form-control "/>
									</td>
									<td>
										<input id="wsProdSkuList{{idx}}_reallyPrice" name="wsProdSkuList[{{idx}}].reallyPrice" type="text" value="{{row.reallyPrice}}" maxlength="11" class="form-control required"/>
									</td>
									<td>
										<input id="wsProdSkuList{{idx}}_rewardMoney" name="wsProdSkuList[{{idx}}].rewardMoney" type="text" value="{{row.rewardMoney}}" maxlength="11" class="form-control "/>
									</td>
								</tr>//-->
							</script>
							<script type="text/javascript">
								var wsProdSkuRowIdx = 0, wsProdSkuTpl = $("#wsProdSkuTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								$(document).ready(function() {
									var data = ${fns:toJson(wsProduct.wsProdSkuList)};
									for (var i=0; i<data.length; i++){
										addRow('#wsProdSkuList', wsProdSkuRowIdx, wsProdSkuTpl, data[i]);
										wsProdSkuRowIdx = wsProdSkuRowIdx + 1;
									}
								});
							</script>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="prod:wsProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保存产品"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>