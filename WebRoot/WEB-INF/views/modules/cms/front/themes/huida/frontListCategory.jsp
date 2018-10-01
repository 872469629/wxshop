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
			<i class="am-icon-newspaper-o toppic-title-i"></i>
			<span class="toppic-title-span">新闻动态</span>
			<p>Hengwang News</p>
		</div>
		<div class="right toppic-progress">
			<span><a href="index.html" class="w-white">首页</a></span>
			<i class=" am-icon-arrow-circle-right w-white"></i>
			<span><a href="news.html" class="w-white">新闻动态</a></span>
		</div>
	</div>
</div>


	
<div class="am-container-1 news-content-all">
<div class="left am-u-sm-12 am-u-md-8 am-u-lg-9 ">
	  <ul class="news-ul">
	  

	  
<c:forEach items="${page.list}" var="article">
		<li class="am-u-sm-12 am-u-md-6 am-u-lg-4 ">
	  		<a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}">
	  		<div class="news-ul-liall">
	  		  	<img class="news-ul-liimg" src="img/news2.png"/>		  	  		  	 
	  		  <div class="inform-list">
		  		  	<div class="inform-list-date"><i class="am-icon-arrow-circle-right"></i><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></div>
		  		  	<div class="inform-list-label"><i class="am-icon-arrow-circle-right"></i>互联网 开发</div>
		  		  	<div class="inform-list-numb"><i class="am-icon-arrow-circle-right"></i>点击次数：273</div>
	  		  </div>		  
	  		  <span>${fns:abbr(article.title,40)}</span>
	  		  <p>摘要：${fns:abbr(article.title,40)}</p>
	  	    <span class="see-more3">查看更多<i class="am-icon-angle-double-right"></i></span>
	  	  </div>
	  	  
	  	 </a>
	  	 </li>
</c:forEach>
	  	
	  	
	  	<div class="clear"></div>
	  	
		</ul>

			<div class="pagination">${page}</div>
			<script type="text/javascript">
				function page(n,s){
					location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+n+"&pageSize="+s;
				}
			</script>
	
		
</div>	  

<div class="left am-u-sm-12 am-u-md-4 am-u-lg-3">
  
	<section data-am-widget="accordion" class="am-accordion am-accordion-gapped" data-am-accordion='{  }'>
		<div class="hot-title"><i class="am-icon-thumbs-o-up"></i>新闻分类 / Hot 	News</div>
     
     
      <dl class="am-accordion-item am-active">
      <c:forEach items="${categoryList}" var="tpl">
        <dt class="am-accordion-title">
          <a href="${ctx}/list-${tpl.id}${urlSuffix}">${tpl.name}</a> 
        </dt>
      </c:forEach>
      </dl>
     
      
  </section>
	
</div>







<div class="clear"></div>
</div>



</body>
</html>