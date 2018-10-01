<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片管理管理</title>
	<meta name="decorator" content="adminlte"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			$("#urlType").change(function(){
				changeUrlType();
			});
			changeUrlType();
		});
		function changeUrlType(){
			if($("#urlType").val()=='4'){
				$("#autoDiv").css('display','block');
				$("#notAutoDiv").css('display','none');
				$("#autoImgHref").attr("disabled",false);
				$("#notAutoImgHref").attr("disabled",true);
			}else{
				$("#autoDiv").css('display','none');
				$("#notAutoDiv").css('display','block');
				$("#autoImgHref").attr("disabled",true);
				$("#notAutoImgHref").attr("disabled",false);
			}
		}
		function showInfo(){
			var urlType=$("#urlType").val();
			layer.open({
				  type: 2,
				  skin: 'layui-layer-rim', //加上边框
				  area: ['800px', '500px'], //宽高
				  content: '${ctx}/config/wsAd/selectImgUrl?urlType='+urlType,
				  btn: ['关闭']
			});
		}
		function selectAd(imgHref,title){
			$("#imgHrefTitle").val(title);
			$("input[name='imgHref']").val(imgHref);
			layer.closeAll();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/config/wsAd/">图片管理列表</a></li>
		<li class="active"><a href="${ctx}/config/wsAd/form?id=${wsAd.id}">图片管理<shiro:hasPermission name="config:wsAd:edit">${not empty wsAd.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="config:wsAd:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">
     <div class="row">
       <div class="col-md-12">
         <div class="box box-primary">
			<form:form id="inputForm" modelAttribute="wsAd" action="${ctx}/config/wsAd/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>	
				<div class="box-body">	
				<div class="form-group">
					<label  class="col-sm-2 control-label">图片类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="imgType" class="form-control ">
							<form:options items="${fns:getDictList('cms_adimage_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">图片标题：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="imgTitle" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">图片地址：
					</label>
					<div class="col-sm-4 controls">
					<input type="hidden" id="image" name="imgUrl" value="${wsAd.imgUrl}"/> 
					<sys:kindUpload input="image" type="image" selectMultiple="false"></sys:kindUpload>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">图片跳转类型：
					</label>
					<div class="col-sm-4 controls">
						<form:select path="urlType" class="form-control ">
							<form:options items="${fns:getDictList('ad_url_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group" style="display: none" id="autoDiv">
					<label  class="col-sm-2 control-label">图片跳转地址：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="imgHref" id="autoImgHref" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<div class="form-group" style="display: none" id="notAutoDiv">
					<label  class="col-sm-2 control-label">图片跳转地址：
					</label>
					<div class="col-sm-4 controls">
						<input type="hidden" name="imgHref" id="notAutoImgHref" class="form-control required"/>
						<form:input path="imgHrefTitle" htmlEscape="false" maxlength="100" class="form-control required" readonly="true"/>
					</div>
					<div class="col-sm-4 controls">
						<a onclick="showInfo();"  class="btn btn-success btn-sm"><span class=""><i class="fa fa-pencil">&nbsp;选择模板</i></span></a>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">优先级：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="priority" htmlEscape="false" maxlength="11" class="form-control digits required"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">点击次数：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="clickNum" htmlEscape="false" maxlength="11" class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">备注：
					</label>
					<div class="col-sm-4 controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="config:wsAd:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
        </div>
	</div>
     </div>
   </section>
</body>
</html>