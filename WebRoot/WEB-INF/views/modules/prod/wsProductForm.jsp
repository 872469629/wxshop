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
					if($("#isBaseChange").val()=="1" || $("#isSelChange").val()=="1"){
						layer.confirm("检测到基本属性或者销售属性已被修改，如果下一步修改基本属性或者销售属性，则可能导致用户购物车和待付款订单失效,请慎重选择!", {
							  btn: ['不修改基本属性和销售属性','修改基本属性和销售属性'],
								area: ['500px', '320px'],
							}, function(index){
								$("#isBaseChange").val("0");
								$("#isSelChange").val("0");
		
								form.submit();
							}, function(index){

								form.submit();
							});
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
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
			$("#expressId").change(function(){
				changeExpressId();
			});
			changeExpressId();
		});
		function changeExpressId(){
			if($("#expressId").val()=='0'){
				$("#volumeDiv").css('display','none');
			}else{
				$("#volumeDiv").css('display','block');
			}
		}
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
		function baseChange(){
			$("#isBaseChange").val("1");
		}
		function selChange(){
			$("#isSelChange").val("1");
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
			<form:form id="inputForm" modelAttribute="wsProduct" action="${ctx}/prod/wsProduct/nextFormSku" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="isBaseChange" />
				<form:hidden path="isSelChange" />
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="row">
			        <div class="col-xs-12">
			          <h2 class="page-header">
			           	 产品属性
			          </h2>
			        </div>
			    </div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">分类名称：
					</label>
					<div class="col-sm-4 controls">
						<form:hidden path="prodCategoryId"  />
						<form:input path="prodCategoryName" htmlEscape="false" maxlength="200" class="form-control " readonly="true"/>
					</div>
					<label  class="col-sm-2 control-label">所属品牌：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="brandName" onclick="selectBrand()" htmlEscape="false" maxlength="64" class="form-control LayeropenWindow " readonly="true"/>
						<form:hidden path="brandId"/>
					</div>
					<script type="text/javascript">
						function selectBrand(){
							layer.open({
								  type: 2,
								  title: "添加相关",
								  shadeClose: true,
								  shade: 0.3,
								  area: ['700px','500px'],
								  content: "${ctx}/prod/wsBrand/dialogList",
								  btn: ['确定', '关闭'],
								  yes: function(index, layero){
									  var data = window[layero.find('iframe')[0]['name']].data;
									  $("#brandName").val(data.name);
									  $("#brandId").val(data.id);
									  layer.close(index);
								  },
							});
						}
					</script>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="pname" htmlEscape="false" maxlength="128" class="form-control required"/>
					</div>
					<label  class="col-sm-2 control-label">价格范围：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="rangePrice" htmlEscape="false" maxlength="11" class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">标题：
					</label>
					<div class="col-sm-10 controls">
						<form:textarea path="title" htmlEscape="false" maxlength="512" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">是否成为分销商品：
					</label>
					<div class="col-sm-4 controls">
						<form:radiobuttons path="isAgentProduct" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">搜索关键字(空格分隔)：
					</label>
					<div class="col-sm-10 controls">
						<form:input path="keyword" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">产品描述：
					</label>
					<div class="col-sm-10 controls">
						<textarea name="prodContent" id="editor" cols="100" rows="8"  style="width:700px;height:400px;visibility:hidden;">${wsProduct.prodContent}</textarea>
						<sys:kindEditor replace="editor"  />
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">产品状态：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="onGoodState" class="form-control required" >
							<form:options items="${fns:getDictList('on_good_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<label  class="col-sm-2 control-label">上架时间：
					</label>
					<div class="col-sm-4 controls">
						<input name="onGoodTime" type="text" readonly="readonly" maxlength="20" class="form-control Wdate "
							value="<fmt:formatDate value="${wsProduct.onGoodTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="disabled"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">市场价格：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="defaultPrice" htmlEscape="false" maxlength="255" class="form-control required maxNumber"/>
					</div>
					<label  class="col-sm-2 control-label">商城价格：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="defaultReallyPrice" htmlEscape="false" maxlength="11" class="form-control required number maxNumber" onblur="this.value=toDecimal(this.value);"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">分销金额：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="defaultRewardMoney" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
					<label  class="col-sm-2 control-label">库存数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="defaultNum" htmlEscape="false" maxlength="11" class="form-control required" />
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">快递模版：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="expressId" class="form-control select2">
							<form:option value="0" label="商品包邮"/>
							<form:options items="${wsExFaretemplates}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>	
					</div>
					<label  class="col-sm-2 control-label">货号：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="pnumber" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group" id="volumeDiv" style="display: none">
					<label  class="col-sm-2 control-label">体积(立方米)：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="volume" htmlEscape="false" class="form-control required number maxNumber"/>
					</div>
					<label  class="col-sm-2 control-label">重量(kg)：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="weight" htmlEscape="false" class="form-control required number maxNumber"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">是否支持退货：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="isReturn" class="form-control ">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<label  class="col-sm-2 control-label">退货有效期(天)：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="returnDate" htmlEscape="false" maxlength="11" class="form-control digits"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">仓库发货地：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="warehouse" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
					<label  class="col-sm-2 control-label">是否首页推荐：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="isHomeRecommd" class="form-control required">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-8 control-label">是否赠品(赠品只可展示,营销活动附赠)：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="isGift" class="form-control ">
							<form:options items="${fns:getDictList('no_yes')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">	
					<label  class="col-sm-2 control-label">已售数量：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="selNum" htmlEscape="false" maxlength="11" class="form-control " readonly="true"/>
					</div>
					<label  class="col-sm-2 control-label">浏览次数：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="clickNum" htmlEscape="false" class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="row">
		        	<div class="col-xs-12">
		          	<h2 class="page-header">
		           	 	商品图片
		          	</h2>
		          	<div class="form-group">
						<label  class="col-sm-2 control-label">商品主图(只选一张):</label>
						<div class="col-sm-10 controls">
						<input type="hidden" id="prodImage" name="prodImage" value="${wsProduct.prodImage}"/> 
						<sys:kindUpload input="prodImage" type="image" selectMultiple="false"></sys:kindUpload>
						</div>
					</div>
		          	<div class="form-group">
						<label  class="col-sm-2 control-label">商品副图(可以多选):</label>
						<div class="col-sm-10 controls">
						<input type="hidden" id="prodImages" name="prodImages" value="${wsProduct.prodImages}"/> 
						<sys:kindUpload input="prodImages" type="image" selectMultiple="true"></sys:kindUpload>
						</div>
					</div>
				</div>
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
						<div class="col-sm-4 controls" id="control-label-left">
							<!-- 单选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '1'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValue" onchange="baseChange()"
										style="margin: 5px;" type="radio" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 复选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '2'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValue" onchange="baseChange()"
										style="margin: 5px;" type="checkbox" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 下拉框 -->
							<c:if test="${wsProdAttribute.inputType eq '3'}">
								<select id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValue"  class="form-control "  onchange="baseChange()">
									<option value=""></option>
									<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
										<option value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}</option>
									</c:forEach>
								</select>
							</c:if>
							<!-- 文本框 -->
							<c:if test="${wsProdAttribute.inputType eq '4'}">
									<input onchange="baseChange()" id="wsProdSkuAttrBaseList${wsProdAttributeState.index}_attrbuteValueName" name="wsProdSkuAttrBaseList[${wsProdAttributeState.index}].attrbuteValueName" type="text" value="${wsProdAttribute.defauleValue}" maxlength="512" class="form-control "/>
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
						<div class="col-sm-4 controls" id="control-label-left">
							<!-- 单选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '1'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValue"  onchange="selChange()"
										style="margin: 5px;" type="radio" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 复选按钮 -->
							<c:if test="${wsProdAttribute.inputType eq '2'}">
								<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
									<input id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValue"  onchange="selChange()"
										style="margin: 5px;" type="checkbox" value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}
								</c:forEach>
							</c:if>
							<!-- 下拉框 -->
							<c:if test="${wsProdAttribute.inputType eq '3'}">
								<select id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValue" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValue"  class="form-control " onchange="selChange()">
									<option value=""></option>
									<c:forEach items="${wsProdAttribute.wsProdAttrivalueList}" var="wsProdAttrivalue" varStatus="wsProdAttrivalueState">
										<option value="${wsProdAttrivalue.id}" ${prod:compareIn(wsProdAttribute.defauleValue,wsProdAttrivalue.id)}>${wsProdAttrivalue.attrvalueValue}</option>
									</c:forEach>
								</select>
							</c:if>
							<!-- 文本框 -->
							<c:if test="${wsProdAttribute.inputType eq '4'}">
									<input onchange="selChange()" id="wsProdSkuAttrSelList${wsProdAttributeState.index}_attrbuteValueName" name="wsProdSkuAttrSelList[${wsProdAttributeState.index}].attrbuteValueName" type="text" value="${wsProdAttribute.defauleValue}" maxlength="512" class="form-control "/>
							</c:if>
						</div>
		      	</c:forEach>
		      	</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="prod:wsProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>