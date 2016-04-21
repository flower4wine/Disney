<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body >
	
	<div class="loc-search">
		<div class="loc-search-bg">
			<i class="fa fa-search" ></i> 
			<input placeholder="搜索景点" >
		</div>
	</div>
	
	<div class="loc-view-info" >
		<div class="lov-view-title">奕欧来购物村</div>
		<div class="loc-view-img" data-code="05-0001-0001">
			<img alt="" src="<c:url value = '/resources/images/shop1.jpg'/>">
			<div class="txt" >奕欧来购物村入口</div>
		</div>
	</div>
	
	<div class="loc-view-info" >
		<div class="lov-view-title">其他景点</div>
		
		<div class="loc-view-img" data-code="01-0001-0002">
			<img alt="" src="<c:url value = '/resources/images/shop3.jpg'/>">
			<div class="txt" >迪斯尼酒店入口</div>
		</div>
		
		<div class="loc-view-img" data-code="01-0002-0001">
			<img alt="" src="<c:url value = '/resources/images/shop4.jpg'/>">
			<div class="txt" >玩具总动员酒店入口</div>
		</div>
	</div>
	

	<script type="text/javascript">
		 $(".loc-view-img").on('click',function(){
		 	var code = $(this).data("code");
			var url = '/disney/pg/toLocation.html?toLocation='+code;
			window.location = url;
		});
	</script>

</body>

</html>
