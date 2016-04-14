<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

<style>

</style>


</head>

<body>
	
	<div class="otl-head">
		<div>
			<span>-&nbsp;&nbsp;奥特莱斯商场入口&nbsp;&nbsp;-</span>
		</div>
	</div>
	
	<div class="otl-content">
		
		<div class="otl-content-grid">
		
			<div class="otl-content-grid-item" >
				<div class="top-txt">
					<span>1号入口</span>
				</div>
				
				<div class="bot-txt">
					<span>路线    &nbsp;&nbsp;<i class="fa fa-level-down"></i></span>
				</div>
			</div>
			
			<div class="otl-content-grid-item" >
				<div class="top-txt">
					<span>2号入口</span>
				</div>
				
				<div class="bot-txt">
					<span>路线    &nbsp;&nbsp;<i class="fa fa-level-down"></i></span>
				</div>
			</div>
			
			<div class="clear"></div>
		</div>
		
		<div class="otl-content-grid">
		
			<div class="otl-content-grid-item" >
				<div class="top-txt">
					<span>3号入口</span>
				</div>
				
				<div class="bot-txt">
					<span>路线    &nbsp;&nbsp;<i class="fa fa-level-down"></i></span>
				</div>
			</div>
			
			<div class="otl-content-grid-item" >
				<div class="top-txt">
					<span>4号入口</span>
				</div>
				
				<div class="bot-txt">
					<span>路线    &nbsp;&nbsp;<i class="fa fa-level-down"></i></span>
				</div>
			</div>
			
			<div class="clear"></div>
		</div>
		
	</div>

	<script type="text/javascript">
		var height = clientHeight();
		var headHeight = parseInt($(".otl-head").css("height"))+20;
		
		var contentHeight = (height - headHeight) + "px";
		
		$(".otl-content").animate({height:contentHeight});
		
		$(".otl-content-grid-item .bot-txt").on('click',function(){
			var url = '/disney/pg/toLocation.html?from=a&to=b';
			window.location = url;
		});
		
	</script>

</body>

</html>
