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
	
	<%@ include file="../include/_bottom_footer.jsp" %>
	
	<script type="text/javascript">
	
		function adjustParkInfoPos(){
			var bottom = clientHeight()*parseInt('${bottom}')/100;
			var left = clientWidth()*parseInt('${left}')/100;
			
			$(".park-info").animate({
				"bottom" : bottom,
				"left" : left
			},function(){
				$(".park-info").show();
			});
		}
		
		adjustParkInfoPos();
	</script>

</body>

</html>
