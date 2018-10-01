<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${category.name}</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="${category.description}" />
	<meta name="keywords" content="${category.keywords}" />
	  <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
			<i class="am-icon-dropbox toppic-title-i"></i>
			<span class="toppic-title-span">产品展示</span>
			<p>Product Show</p>
		</div>
		<div class="right toppic-progress">
			<span><a href="index.html" class="w-white">首页</a></span>
			<i class=" am-icon-arrow-circle-right w-white"></i>
			<span><a href="product-show.html" class="w-white">产品展示</a></span>
		</div>
	</div>
</div>



<div class="am-container-1 news-content-all">

<div class="left am-u-sm-12 am-u-md-8 am-u-lg-9 ">

	<ul class=" product-show-ul">
	
	<c:forEach items="${page.list}" var="article">
	    <li >
	    	<div class="product-content">
			    	<div class="left am-u-sm-12 am-u-md-6 am-u-lg-6 product-content-left">
			    		<div class="product-show-title">
			    			<h3>${fns:abbr(article.title,40)}</h3>
			    			<span>${article.category.name}</span>
			    		</div>
			    	
			    	<div class="product-show-content">
			    		<div class="product-add">
			    			<span>查看详情：</span>
			    			<div><a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}">点我查看详细介绍</a></div>
			    			<i class="am-icon-dribbble"></i>
			    		</div>
			    		<div class="product-intro">
			    			<span>摘要：</span>
			    			<div><p>${fns:abbr(article.description,100)}</p></div>
			    		  <i class="am-icon-tasks"></i>
			    		</div>
			    	</div>
			    	</div>
			    	<div class="right am-u-sm-12 am-u-md-6 am-u-lg-6 product-content-right">
			    		<img class="product-img" src="${article.image}" />
			    	</div>	
			    	<div class="clear"></div>
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
	
	<div class="left am-u-sm-12 am-u-md-4 am-u-lg-3">
	<section data-am-widget="accordion" class="am-accordion am-accordion-gapped" data-am-accordion='{  }'>
		<div class="hot-title"><i class="am-icon-thumbs-o-up"></i>产品分类 / Hot 	News</div>
      <dl class="am-accordion-item am-active">
      <c:forEach items="${categoryList}" var="tpl">
        <dt class="am-accordion-title">
          <a href="${ctx}/list-${tpl.id}${urlSuffix}">${tpl.name}</a> 
        </dt>
      </c:forEach>
      </dl>
  </section>
	</div>
	
	
</div>

 <div class="clear"></div>


</body>
</html>