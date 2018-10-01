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
		<li><a href="${ctx}/prod/wsAttribute/">产品属性列表</a></li>
		<li class="active"><a href="${ctx}/prod/wsAttribute/form?id=${wsAttribute.id}">产品属性<shiro:hasPermission name="prod:wsAttribute:edit">${not empty wsAttribute.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prod:wsAttribute:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsAttribute" action="${ctx}/prod/wsAttribute/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">所属分类：
					</label>
					<!-- 
					<div class="col-sm-4 controls">
						<form:input path="prodCategoryId" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
					 -->
					 <div class="col-sm-4 controls">
						<sys:treeselect id="prodCategoryId" name="prodCategoryId.id" value="${wsAttribute.prodCategoryId.id}" labelName="prodCategoryId.name" labelValue="${wsAttribute.prodCategoryId.name}"
							title="父类" url="/prod/wsProdCategory/treeData" extId="${wsProdCategory.id}" module="wsAttribute" cssClass="form-control" allowClear="true"/>
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
							<form:option value="" label="请选择"/>
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
						<form:radiobuttons path="state" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">排序：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="sort" htmlEscape="false" maxlength="10" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">remarks：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
					<div class="form-group">
						<label  class="col-sm-2 control-label">属性值表：</label>
						<div class="col-sm-10 controls">
							<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
								<thead>
									<tr>
										<th class="hide"></th>
										<th>属性值名称</th>
										<th>状态</th>
										<th>排序</th>
										<th>remarks</th>
										<shiro:hasPermission name="prod:wsAttribute:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
									</tr>
								</thead>
								<tbody id="wsAttrivalueList">
								</tbody>
								<shiro:hasPermission name="prod:wsAttribute:edit"><tfoot>
									<tr><td colspan="6"><a href="javascript:" onclick="addRow('#wsAttrivalueList', wsAttrivalueRowIdx, wsAttrivalueTpl);wsAttrivalueRowIdx = wsAttrivalueRowIdx + 1;" class="btn">新增</a></td></tr>
								</tfoot></shiro:hasPermission>
							</table>
							<script type="text/template" id="wsAttrivalueTpl">//<!--
								<tr id="wsAttrivalueList{{idx}}">
									<td class="hide">
										<input id="wsAttrivalueList{{idx}}_id" name="wsAttrivalueList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
										<input id="wsAttrivalueList{{idx}}_delFlag" name="wsAttrivalueList[{{idx}}].delFlag" type="hidden" value="0"/>
									</td>
									<td>
										<input id="wsAttrivalueList{{idx}}_attrvalueValue" name="wsAttrivalueList[{{idx}}].attrvalueValue" type="text" value="{{row.attrvalueValue}}" maxlength="256" class="form-control "/>
									</td>
									<td>
										<input id="wsAttrivalueList{{idx}}_state" name="wsAttrivalueList[{{idx}}].state" type="text" value="{{row.state}}" maxlength="255" class="form-control "/>
									</td>
									<td>
										<input id="wsAttrivalueList{{idx}}_sort" name="wsAttrivalueList[{{idx}}].sort" type="text" value="{{row.sort}}" maxlength="64" class="form-control "/>
									</td>
									<td>
										<textarea id="wsAttrivalueList{{idx}}_remarks" name="wsAttrivalueList[{{idx}}].remarks" rows="4" maxlength="255" class="form-control ">{{row.remarks}}</textarea>
									</td>
									<shiro:hasPermission name="prod:wsAttribute:edit"><td class="text-center" width="10">
										{{#delBtn}}<span class="close" onclick="delRow(this, '#wsAttrivalueList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
									</td></shiro:hasPermission>
								</tr>//-->
							</script>
							<script type="text/javascript">
								var wsAttrivalueRowIdx = 0, wsAttrivalueTpl = $("#wsAttrivalueTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								$(document).ready(function() {
									var data = ${fns:toJson(wsAttribute.wsAttrivalueList)};
									for (var i=0; i<data.length; i++){
										addRow('#wsAttrivalueList', wsAttrivalueRowIdx, wsAttrivalueTpl, data[i]);
										wsAttrivalueRowIdx = wsAttrivalueRowIdx + 1;
									}
								});
							</script>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="prod:wsAttribute:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>