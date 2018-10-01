<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="adminlte"/>
	<script src="${ctxStatic}/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmitSend").click(function(){
 			 	$("#inputForm").submit();  
 			 	 	
 			//    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
  			  //  parent.layer.close(index); //再执行关闭   
 			 
// 				var params = {};  
// 			    //params.XX必须与Spring Mvc controller中的参数名称一致    
// 			    //否则在controller中使用@RequestParam绑定  
// 			    params.num = num;  
// 			    params.id = id;  
// 			    params.amount = amount;  
// 			    $.ajax({  
// 			        async:false,  
// 			        type: "POST",  
// 			        url: "price/update",//注意路径  
// 			        data:params,  
// 			        dataType:"json",  
// 			        success:function(data){  
// 			            if(data.result=='SUCCESS'){  
// 			                alert("修改成功");  
// 			            }else{  
// 			                alert("修改失败，失败原因【" + data + "】");  
// 			            }  
// 			        },  
// 			        error:function(data){  
// 			            alert(data.result);  
// 			        }  
// 			    });  
				
			});
						
			//$("#name").focus();
			
		});
		
	
	</script>
</head>
<body>

	<section class="content" style="background: redl;padding: 0px">
	  <div class="col-md-12">
 <div class="row">

    <div class="box box-primary">
 	<form:form id="inputForm" modelAttribute="wsOrder" action="${ctx}/order/wsOrder/saveSend" method="post" class="form-horizontal">
 		<form:hidden path="id"/>
     	<div class="form-group" style="display:none;">
					<label  class="col-sm-2 control-label">订单：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="orderSn" htmlEscape="false" maxlength="64" class="form-control " readonly="true"/>
					</div>
		</div>
     	<div class="form-group"  style="display:none;">
					<label  class="col-sm-2 control-label">物流公司：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="express" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">快递单号：
					</label>
					<div class="col-sm-4 controls">
						<form:input path="trackingno" htmlEscape="false" maxlength="64" class="form-control "/>
					</div>
				</div>
				<div class="box-footer">
					<label class="col-sm-3 control-label"></label>
					<shiro:hasPermission name="order:wsOrder:edit"><input id="btnSubmitSend" class="btn btn-primary" type="button" value="发	货"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
	</form:form>
	</div>
	</div>
	</div>
   </section>
</body>
</html>