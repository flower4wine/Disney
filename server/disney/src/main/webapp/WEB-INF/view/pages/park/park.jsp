<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>
<body>

	<div>
		<img src="<c:url value= '${bgImg }' />" class="park-map-img" />
		
		<div class="park-info">
			<div class="bg-color-blue park-info-name">P1停车场</div>
			<div class="park-info-tip txt-color_gray"><span>----</span>剩余车位<span>----</span></div>
			<div class="park-info-num">510</div>
		</div>
		
	</div>
	
	<div class="idx_bot">
		<div class="img">
			<div style="font-size: 90%;letter-spacing0.5px; text-align: center;">Shanghai International Tourism & Resorts Zone</div>
			<div style="font-size: 110%;text-align: center;">-&nbsp;上海国际旅游度假区&nbsp;-</div>
		</div>
	</div>
	
	<script type="text/javascript">
	
		function adjustParkInfoPos(){
			var bottom = clientHeight()*parseInt('${bottom}')/100;
			var left = clientWidth()*parseInt('${left}')/100;
			
			$(".park-info").animate({
				"bottom" : bottom,
				"left" : left
			});
		}
		
		adjustParkInfoPos();
	</script>

</body>

</html>
