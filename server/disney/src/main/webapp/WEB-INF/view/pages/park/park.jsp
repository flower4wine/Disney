<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>
<body>

	<div>
		<img src="<c:url value= '/resources/images/park.jpg' />" class="park-map-img" />
		
		<div class="park-info">
			<div class="bg-color-blue park-info-name">P1停车场</div>
			<div class="park-info-tip txt-color_gray"><span>----</span>剩余车位<span>----</span></div>
			<div class="park-info-num">510</div>
		</div>
		
	</div>
	
	<div class="idx_bot">
		<div class="img">
			<img alt="" src="<c:url value= '/resources/images/index_bot.png' />">
		</div>
	</div>
	
	<script type="text/javascript">
		function adjustParkInfoPos(){
			var cHeight = clientHeight()-55;
			var height = $(".park-info").css('height');
			
			var top = (parseInt(cHeight) -  parseInt(height))/2; 
			$(".park-info").animate({"top":top});
		}
		adjustParkInfoPos();
	</script>

</body>

</html>
