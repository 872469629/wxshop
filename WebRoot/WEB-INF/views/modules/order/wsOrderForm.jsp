 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="adminlte"/>
	<script src="${ctxStatic}/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {	
			 $('#fahuo').on('click', function(){
					   layer.open({
			            type: 2,
			            title: '发货',
			            maxmin: true,
			            shadeClose: true, //点击遮罩关闭层
			            area : ['400px' , '400px'],
			            content: '${ctx}/order/wsOrder/formSend?id=${wsOrder.id}',
			            end: function () {
			                location.reload();
			            }
			        });
			    });
			 $('#remark').on('click', function(){
					   layer.open({
			            type: 2,
			            title: '备注',
			            maxmin: true,
			            shadeClose: true, //点击遮罩关闭层
			            area : ['400px' , '400px'],
			            content: '${ctx}/order/wsOrder/formRemark?id=${wsOrder.id}',
			            end: function () {
			                location.reload();
			            }
			        });
			    });

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
		function closeDialog(){
			layer.closeAll();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/wsOrder/">订单列表</a></li>
		<li class="active"><a href="${ctx}/order/wsOrder/form?id=${wsOrder.id}">订单<shiro:hasPermission name="order:wsOrder:edit">${not empty wsOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:wsOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<section class="content" style="background: redl;padding: 0px">

		<div class="row">
	        <div class="col-md-12">
	          <div class="box">
	            <div class="box-header with-border">
	              <h3 class="box-title">订单处理</h3>
	            </div>
	            <div class="box-body bg-light-blue">
    				<c:if test="${wsOrder.orderState==1}">
    				<a id="fahuo" class="btn btn-primary btn-sm"><i class="fa fa-folder">&nbsp;发货</i></a>
    				</c:if>
    				<a href="${ctx}/order/wsOrder/closeOrder?id=${wsOrder.id}" onclick="return confirmx('确认要关闭该订单吗？', this.href)" class="btn btn-primary btn-sm"><i class="fa fa-remove">&nbsp;关闭</i></a>
    				<a id="remark" class="btn btn-primary btn-sm"><i class="fa fa-folder">&nbsp;备注</i></a>
	            </div>
	          </div>
	        </div>
	     </div>
	    <div class="row">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">买家信息</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            
          <div class="col-md-12">
            <div class="form-group">
				<label  class="col-sm-2 control-label">会员名：</label>
				<div class="col-sm-4 controls">
				${wsOrder.memberId.nickname}
				</div>	
				<label  class="col-sm-2 control-label">快递单号：
				</label>
				<div class="col-sm-4 controls">
				${wsOrder.trackingno}
				</div>
			</div>
		  </div>
		
		  <div class="col-md-12">
			 <div class="form-group">
					<label  class="col-sm-2 control-label">地址：
					</label>
					<div class="col-sm-10 controls">
				  ${wsOrder.address.city}${wsOrder.address.address}
					</div>
		   	</div>
		
		</div>
			
		  <div class="col-md-12">
		   <div class="form-group">
					<label  class="col-sm-2 control-label">收货人电话：
					</label>
					<div class="col-sm-4 controls">
				 ${wsOrder.address.tel}
					</div>
					<label  class="col-sm-2 control-label">收货人姓名：
					</label>
					<div class="col-sm-4 controls">
					${wsOrder.address.consignee}
					</div>
		   	</div>
	 	</div>
		<div class="col-md-12">
	 		 <div class="form-group">
					<label  class="col-sm-2 control-label">买家备注：
					</label>
					<div class="col-sm-10 controls">
				 ${wsOrder.buysWords}
					</div>
		   	</div>
		</div>
		<div class="col-md-12">
	 		 <div class="form-group">
					<label  class="col-sm-2 control-label">发货备注：
					</label>
					<div class="col-sm-10 controls">
				 ${wsOrder.remarks}
					</div>
		   	</div>
		</div>
          </div>
        </div>
      </div>
      
      
    
    
    
    
    
      
        <div class="row">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">&nbsp;&nbsp;产品信息</h3>
					
            </div>
            <!-- /.box-header -->
            <div class="box-body">
		 		<div class="form-group">
					<div class="col-sm-12 controls">
						<table id="contentTable" class="table table-bordered table-striped table-hover dataTable no-footer">
							<thead>
								<tr>
									<th colspan="2" style="padding-left: 10px;">产品名称</th>
									<th style="display:none;">成本价</th>
									<th>商品单价(元)</th>
									<th>购买数量</th>
									<th>小计(元)</th>
								</tr>
							</thead>
							<c:forEach items="${wsOrder.wsOrderItemList}" var="wsOrderItem">
							<tr>
								<td width="7%">
									<a href="${ctx}/prod/wsProduct/nextForm?id=${wsOrderItem.wsProduct.id}" style="float: left; margin-left: 10px;">
		                    			<img id="" src="${wsOrderItem.thumb}" style="width:40px;border-width:0px;">
		                    		</a>
								</td>
								<td width="32%">
									<span class="Name">
										<a href="${ctx}/prod/wsProduct/nextForm?id=${wsOrderItem.wsProduct.id}" class="text-ellipsis" style="padding-right: 10px; width: 300px;">${wsOrderItem.wsProduct.title}</a>
									</span>
									<br>
									<span class="colorC">${wsOrderItem.wsProdSku.skuName } </span>
								</td>
								<td style="display:none">
									<span id="">${wsOrderItem.reallyUnitPrice }</span>
								</td>
								<td>
		                			<span id="">${wsOrderItem.unitPrice }</span>
		                		</td>
		                		<td>
		                			${wsOrderItem.quantity }
		                		</td>
		                		<td>
					                <div class="color_36c"></div>
					                <strong class="colorG">
					                    <span id="">${wsOrderItem.reallyPrice }</span>
					                </strong>
		                		</td>
							</tr>
							</c:forEach>
						</table>
					</div>
				</div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="row">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">&nbsp;&nbsp;订单信息</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            	<div class="col-md-12">
            	<table id="" width="300" border="0" cellspacing="0"	style="float: left;">
            		<tr>
            			<td>订单编号:</td>
            			<td>${wsOrder.orderSn}</td>
            		</tr>
            		<tr>
            			<td>下单时间:</td>
            			<td><fmt:formatDate value="${wsOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            		</tr>
            		<tr>
            			<td>付款时间:</td>
            			<td><fmt:formatDate value="${wsOrder.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            		</tr>
            	</table>
            
    			<table id="" width="300" border="0" cellspacing="0"	style="float: right;">
            		<tr>
            			<td>合计价格:</td>
            			<td>${wsOrder.price}</td>
            		</tr>
            		<tr>
            			<td>快递费用:</td>
            			<td>${wsOrder.postage}</td>
            		</tr>
            		<tr>
            			<td>订单可得积分:</td>
            			<td>${wsOrder.score}</td>
            		</tr>
            		<tr class="bg" style="border-top:1px solid #ddd;">
            			<td class="colorG">订单实付款：</td>
            			<td>
            				<strong style="color:#333;font-weight:700;">${wsOrder.reallyPrice}元</strong>
            			</td>
            		</tr>
            	</table>
            	</div>
            </div>
          </div>
        </div>
   </div>
      </div>
	
	
	
	
	


   </section>
</body>
</html>