<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图文素材明细管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var imgageurl=$("#imageUrl").val();
					if(imgageurl==null || imgageurl==""){
						alert("封面图片不能为空");
						return;
					}
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
			$("#newType").change(function(){
				newTypeChange();
			});
			newTypeChange();
		});
		function newTypeChange(){
			if($("#newType").val()=='local'){
				$("#contentCss").css('display','block');
				$("#urlCss").css('display','none');
			}else{
				$("#contentCss").css('display','none');
				$("#urlCss").css('display','block');
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ws/wxMaterialNewsItem/">图文素材明细列表</a></li>
		<li class="active"><a href="${ctx}/ws/wxMaterialNewsItem/form?id=${wxMaterialNewsItem.id}">图文素材明细${not empty wxMaterialNewsItem.id?'修改':'添加'}</a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wxMaterialNewsItem" action="${ctx}/ws/wxMaterialNewsItem/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="newsId"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">作者：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="author" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">标题：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="title" htmlEscape="false" maxlength="255" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">消息摘要描述：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="description" rows="3" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">封面图片<br/>(图片名称不能包含中文)：
					</label>
					<div class="col-sm-10 controls">
						<input type="hidden" id="imageUrl" name="imageUrl" value="${wxMaterialNewsItem.imageUrl}"/> 
						<sys:kindUpload input="imageUrl" type="image" selectMultiple="false"></sys:kindUpload>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">图文类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="newType" class="form-control required">
							<form:options items="${fns:getDictList('new_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group" id="contentCss">
					<label  class="col-sm-2 control-label">图文内容:</label>
					<div class="col-sm-10 controls">
						<textarea name="content" id="editor" cols="100" rows="8" style="width:700px;height:400px;visibility:hidden;">${wxMaterialNewsItem.content}</textarea>
						<sys:kindEditor replace="editor"  />	
					</div>
				</div>
				<div class="form-group" id="urlCss">
					<label  class="col-sm-2 control-label">外部链接地址：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="url" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">图文顺序：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="orders" htmlEscape="false" maxlength="11" class="form-control  digits required"/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>