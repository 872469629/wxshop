<%
response.setStatus(403);

//获取异常类
Throwable ex = Exceptions.getThrowable(request);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	if (ex!=null && StringUtils.startsWith(ex.getMessage(), "msg:")){
		out.print(StringUtils.replace(ex.getMessage(), "msg:", ""));
	}else{
		out.print("微信接口错误.");
	}
}

//输出异常信息页面
else {
%>
<%@page import="com.thinkgem.jeesite.common.web.Servlets"%>
<%@page import="com.thinkgem.jeesite.common.utils.Exceptions"%>
<%@page import="com.thinkgem.jeesite.common.utils.StringUtils"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>微信接口错误</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1>微信接口错误.</h1></div>
		<%
			if(ex.getMessage().equals("41005")){
				out.print("<div class=\"page-header\"><h1>缺少多媒体文件数据</h1></div>");
			}
			if(ex.getMessage().equals("45009")){
				out.print("<div class=\"page-header\"><h1>接口调用超过限制,上传永久素材每天不能超过10次哦</h1></div>");
			}
			if(ex.getMessage().equals("45001")){
				out.print("<div class=\"page-header\"><h1>多媒体文件大小超过限制</h1></div>");
			}
			if(ex.getMessage().equals("45010")){
				out.print("<div class=\"page-header\"><h1>创建菜单个数超过限制</h1></div>");
			}
		%>
		<div><a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></div>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
</body>
</html>
<%
} out = pageContext.pushBody();
%>