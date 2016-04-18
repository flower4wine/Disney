<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../include/_common_header.jsp"%>

<style>

input{outline:none} 

.loc-view{
	padding: 5px 0px;height: 140px;
}

.loc-view img{
	height: 140px;
}

.loc-view .txt{
	text-align: right;position: relative;color: white;bottom: 35px;font-size:130%;background-color: rgba(7,7,7,0.3);padding: 0 10px;
}

</style>


</head>

<body class="">
	
	<div style="border-bottom:solid 1px #ccc;padding:20px;">
		<div style="color:white;background-color: #777;border-radius:4px;font-size: 130%;padding: 5px;">
		
			<i class="fa fa-search" style="padding: 0 12px;"></i> 
			
			<input placeholder="搜索景点"  
				style="padding:5px 0px;background-color: rgba(7,7,7,0);border: none;width: 80%;color: white;">
		</div>
	</div>
	
	<div style="padding:0px 10px;">
		
		<div style="color: #777;font-size: 130%;text-align:center;padding: 10px;">奕欧来购物村</div>
		
		<div class="loc-view" data-code="05-0001-0001">
			<img alt="" src="<c:url value = '/resources/images/shop1.jpg'/>">
			<div class="txt" style="">奕欧来购物村入口</div>
		</div>
		
		<%-- <div class="loc-view">
			<img alt="" src="<c:url value = '/resources/images/shop2.jpg'/>">
			<div class="txt" style="">奕欧来购物村北门入口</div>
		</div> --%>
		
	</div>
	
	<div style="padding:0px 10px;">
		<div style="color: #777;font-size: 130%;text-align:center;padding: 10px;">其他景点</div>
		
		<div class="loc-view" data-code="01-0001-0002">
			<img alt="" src="<c:url value = '/resources/images/shop3.jpg'/>">
			<div class="txt" style="">迪斯尼酒店入口</div>
		</div>
		
		<div class="loc-view" data-code="01-0002-0001">
			<img alt="" src="<c:url value = '/resources/images/shop4.jpg'/>">
			<div class="txt" style="">玩具总动员酒店入口</div>
		</div>
	</div>
	
	
	

	<script type="text/javascript">
		 $(".loc-view").on('click',function(){
		 	var code = $(this).data("code");
			var url = '/disney/pg/toLocation.html?toLocation='+code;
			window.location = url;
		});
	</script>

</body>

</html>
