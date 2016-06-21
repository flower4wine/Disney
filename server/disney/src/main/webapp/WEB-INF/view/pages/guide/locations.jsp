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
		<div class="lov-view-title">迪斯尼景点</div>
		<div class="loc-view-img" data-code="04-0001-0001">
			<img alt="" src="<c:out value='${staticFileUrl }/resources/images/disney-town.jpg'/>">
			<div class="txt" >迪士尼乐园入口 / 迪士尼小镇</div>
		</div>
	</div>
	
	
	<div class="loc-view-info" >
		<div class="lov-view-title">奕欧来购物村</div>
		
		<div class="loc-view-img" data-code="05-0001-0001">
			<img alt="" src="<c:out value='${staticFileUrl }/resources/images/shop1.jpg'/>">
			<div class="txt" >奕欧来购物村入口01</div>
		</div>
		
		<div class="loc-view-img" data-code="05-0001-0002">
			<img alt="" src="<c:out value='${staticFileUrl }/resources/images/shop2.jpg'/>">
			<div class="txt" >奕欧来购物村入口02</div>
		</div>
		
		<div class="loc-view-img" data-code="05-0001-0003">
			<img alt="" src="<c:out value='${staticFileUrl }/resources/images/shop3.jpg'/>">
			<div class="txt" >奕欧来购物村入口03</div>
		</div>
	
		<div class="loc-view-img" data-code="05-0001-0004">
			<img alt="" src="<c:out value='${staticFileUrl }/resources/images/shop4.jpg'/>">
			<div class="txt" >奕欧来购物村入口04</div>
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
