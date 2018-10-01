<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${category.name}</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="${category.description}" />
	<meta name="keywords" content="${category.keywords}" />
	  <meta name="viewport"
        content="width=device-width, initial-scale=1,maximum-scale=1.0, user-scalable=0,user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
</head>
<body>
<div class="toppic">
	<div class="am-container-1">
	 <div class="toppic-title left">
			<i class="am-icon-briefcase toppic-title-i"></i>
			<span class="toppic-title-span">客户案例</span>
			<p>Customer Case</p>
		</div>
		<div class="right toppic-progress">
			<span><a href="index.html" class="w-white">首页</a></span>
			<i class=" am-icon-arrow-circle-right w-white"></i>
			<span><a href="customer-case.html" class="w-white">客户案例</a></span>
		</div>
	</div>
</div>

<div class="am-container-1">
	 <ul data-am-widget="gallery" class="am-gallery am-avg-sm-1
  am-avg-md-3 am-avg-lg-4 am-gallery-bordered customer-case-ul" data-am-gallery="{  }" >
     
  <c:forEach items="${page.list}" var="article">   
      <li>
        <div class="am-gallery-item">
            <a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" class="">
            	<div class="customer-case-img">
              <img src="${ctxStaticTheme}/img/app1-1.png" />
             </div>
                <h3 class="am-gallery-title">啊${fns:abbr(article.title,40)}</h3>
                <div class="am-gallery-desc gallery-words">摘要：${fns:abbr(article.title,40)}</div>
            </a>
        </div>
      </li>
  </c:forEach>    
     </ul>
		<div class="pagination">${page}</div>
			<script type="text/javascript">
				function page(n,s){
					location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+n+"&pageSize="+s;
				}
			</script>
</div>
<div class="part-all gray-li">
<div class="customer  am-container-1">
		<div class="part-title">
			<i class="am-icon-users part-title-i"></i>
			<span class="part-title-span">服务客户</span>
			<p>Serve Customers</p>
		</div>
		
		<div class="am-slider am-slider-default am-slider-carousel part-all" data-am-flexslider="{itemWidth:150, itemMargin: 5, slideshow: false}" style="  background-color: #f0eeed; box-shadow:none;">
		  <ul class="am-slides">
		    <li><img src="${ctxStaticTheme}/img/ptn4.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn5.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn6.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn7.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn8.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn4.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn5.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn6.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn7.png"/></li>
		    <li><img src="${ctxStaticTheme}/img/ptn8.png"/></li>
		  </ul>
		</div>
</div>
</div>
	
	
	
	
	
</body>
</html>