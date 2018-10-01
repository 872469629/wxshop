<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品属性管理</title>
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
			
			
			var typestate = $("select[name='inputType']").val();
			if (typestate == "4") {
				$("#attributedetail").hide();
			} else {
				$("#attributedetail").show();
			}
			
			
			
			$("select[name='inputType']").click(function() {
				if ($(this).val() == "4") {
					$("#attributedetail").hide();
				} else {
					$("#attributedetail").show();
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
		<li><a href="${ctx}/prod/wsProdAttribute/">产品属性列表</a></li>
		<li class="active"><a href="${ctx}/prod/wsProdAttribute/form?id=${wsProdAttribute.id}">产品属性<shiro:hasPermission name="prod:wsProdAttribute:edit">${not empty wsProdAttribute.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prod:wsProdAttribute:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsProdAttribute" action="${ctx}/prod/wsProdAttribute/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">所属分类：
					</label>
						 <div class="col-sm-4 controls">
							<sys:treeselect id="prodCategoryId" name="prodCategoryId.id" value="${wsProdAttribute.prodCategoryId.id}" labelName="prodCategoryId.name" labelValue="${wsProdAttribute.prodCategoryId.name}"
							title="父类" url="/prod/wsProdCategory/treeData" extId="${wsProdCategory.id}" module="wsProdAttribute" cssClass="form-control" allowClear="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">属性名称：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="attrName" htmlEscape="false" maxlength="128" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">属性类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="attrType" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('att_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">输入类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="inputType" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('input_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">是否搜索：
					</label>
					<div class="col-sm-4 controls">
						<form:radiobuttons path="isSearch" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">是否必填：
					</label>
					<div class="col-sm-4 controls">
						<form:radiobuttons path="isRequire" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">状态：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="state" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('wsstate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">排序：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="sort" htmlEscape="false" maxlength="10" class="form-control required"/>
					</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label  class="col-sm-2 control-label">remarks： -->
<!-- 					</label> -->
<!-- 					<div class="col-sm-4 controls"> -->
<%-- 						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/> --%>
<!-- 					</div> -->
<!-- 				</div> -->
					<div id="attributedetail" class="form-group">
						<label  class="col-sm-2 control-label">属性值表：</label>
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
								<thead>
									<tr>
										<th class="hide"></th>
										<th>属性数值</th>
										<th>状态</th>
										<th>排序</th>
									
										<shiro:hasPermission name="prod:wsProdAttribute:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
									</tr>
								</thead>
								<tbody id="wsProdAttrivalueList">
								</tbody>
								<shiro:hasPermission name="prod:wsProdAttribute:edit"><tfoot>
									<tr><td colspan="6"><a href="javascript:" onclick="addRow('#wsProdAttrivalueList', wsProdAttrivalueRowIdx, wsProdAttrivalueTpl);wsProdAttrivalueRowIdx = wsProdAttrivalueRowIdx + 1;" class="btn">新增</a></td></tr>
								</tfoot></shiro:hasPermission>
							</table>
							<script type="text/template" id="wsProdAttrivalueTpl">//<!--
								<tr id="wsProdAttrivalueList{{idx}}">
									<td class="hide">
										<input id="wsProdAttrivalueList{{idx}}_id" name="wsProdAttrivalueList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
										<input id="wsProdAttrivalueList{{idx}}_delFlag" name="wsProdAttrivalueList[{{idx}}].delFlag" type="hidden" value="0"/>
									</td>
									<td>
										<input id="wsProdAttrivalueList{{idx}}_attrvalueValue" name="wsProdAttrivalueList[{{idx}}].attrvalueValue" type="text" value="{{row.attrvalueValue}}" maxlength="256" class="form-control "/>
									</td>
									<td>
											<c:forEach items="${fns:getDictList('wsstate')}" var="dict" varStatus="dictStatus">
											<span><input id="wsProdAttrivalueList{{idx}}_state${dictStatus.index}" name="wsProdAttrivalueList[{{idx}}].state" type="radio" value="${dict.value}" data-value="{{row.state}}"><label for="wsProdAttrivalueList{{idx}}_state${dictStatus.index}">${dict.label}</label></span>
										    </c:forEach>
									</td>
									<td>
										<input id="wsProdAttrivalueList{{idx}}_sort" name="wsProdAttrivalueList[{{idx}}].sort" type="text" value="{{row.sort}}" maxlength="64" class="form-control "/>
									</td>
									
									<shiro:hasPermission name="prod:wsProdAttribute:edit"><td class="text-center" width="10">
										{{#delBtn}}<span class="close" onclick="delRow(this, '#wsProdAttrivalueList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
									</td></shiro:hasPermission>
								</tr>//-->
							</script>
							<script type="text/javascript">
								var wsProdAttrivalueRowIdx = 0, wsProdAttrivalueTpl = $("#wsProdAttrivalueTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								$(document).ready(function() {
									var data = ${fns:toJson(wsProdAttribute.wsProdAttrivalueList)};
									for (var i=0; i<data.length; i++){
										addRow('#wsProdAttrivalueList', wsProdAttrivalueRowIdx, wsProdAttrivalueTpl, data[i]);
										wsProdAttrivalueRowIdx = wsProdAttrivalueRowIdx + 1;
									}
								});
							</script>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="prod:wsProdAttribute:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>