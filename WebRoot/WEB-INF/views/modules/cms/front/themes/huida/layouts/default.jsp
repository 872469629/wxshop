<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title default="欢迎光临" /> - ${site.title} -
	Powered By JeeSite</title>
<%@include file="/WEB-INF/views/modules/cms/front/include/head.jsp"%>
<sitemesh:head />
</head>
<body>

	<header class="am-topbar header">
		<div class="am-container-1">
			<div class="left hw-logo">
				<img class=" logo" src="${ctxStaticTheme}/img/HENGWANG.png"></img> <img
					class="word" src="${ctxStaticTheme}/img/hw-word.png"></img>
			</div>
			<button
				class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
				data-am-collapse="{target: '#doc-topbar-collapse'}">
				<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
			</button>

			<div class="am-collapse am-topbar-collapse right"
				id="doc-topbar-collapse">


				<div class=" am-topbar-left am-form-inline am-topbar-right"
					role="search">
					<ul class="am-nav am-nav-pills am-topbar-nav hw-menu">
						<li class="hw-menu-active"><a href="${ctx}">首页</a></li>
						<!--       <li ><a href="solutions.html">解决方案</a></li> -->
						<!--       <li><a href="product-show.html">产品展示 </a></li> -->
						<!--       <li><a href="customer-case.html">客户案例</a></li> -->
						<!--       <li><a href="service-center.html">服务中心 </a></li> -->
						<!--       <li><a href="news.html">新闻动态 </a></li> -->
						<!--       <li><a href="about-us.html">关于我们</a></li> -->
						<!--       <li><a href="recruit.html">招贤纳士 </a></li> -->

						<c:forEach items="${fnc:getMainNavList(site.id)}" var="category"
							varStatus="status">
							<c:if test="${status.index lt 10}">
								<c:set var="menuCategoryId" value=",${category.id}," />
								<li
									class="${requestScope.category.id eq category.id||fn:indexOf(requestScope.category.parentIds,menuCategoryId) ge 1?'active':''}"><a
									href="${category.url}" target="${category.target}"><span>${category.name}</span></a></li>
							</c:if>
						</c:forEach>



					</ul>
				</div>

			</div>
		</div>
	</header>


	<div class="container">
		<div class="content">
			<sitemesh:body />
		</div>
	</div>

	<footer class="footer ">

		<ul>

			<li class="am-u-lg-4 am-u-md-4 am-u-sm-12 part-5-li2">
				<div class="part-5-title">联系我们</div>
				<div class="part-5-words2">
					<span>地址:山东省威海市高新技术开发区</span> <span>电话:18238765101</span> <span>传真:(123)
						456-7890</span> <span>邮箱:water@zhongdainfo.com</span> 
				</div>
			</li>
			<li class="am-u-lg-4 am-u-md-4 am-u-sm-12 ">
				<div class="part-5-title">相关链接</div>
				<div class="part-5-words2">
					<ul class="part-5-words2-ul">
						<li class="am-u-lg-4 am-u-md-6 am-u-sm-4"><a
							href="solutions.html">解决方案</a></li>
						<li class="am-u-lg-4 am-u-md-6 am-u-sm-4"><a
							href="product-show.html">产品展示</a></li>
						<li class="am-u-lg-4 am-u-md-6 am-u-sm-4"><a
							href="customer-case.html">客户案例</a></li>
						<li class="am-u-lg-4 am-u-md-6 am-u-sm-4"><a
							href="service-center.html">服务中心</a></li>
						<li class="am-u-lg-4 am-u-md-6 am-u-sm-4"><a
							href="about-us.html">关于我们</a></li>
						<li class="am-u-lg-4 am-u-md-6 am-u-sm-4"><a
							href="recruit.html">招贤纳士</a></li>
						<div class="clear"></div>
					</ul>
				</div>
			</li>
			<div class="clear"></div>
		</ul>

	</footer>

</body>
</html>