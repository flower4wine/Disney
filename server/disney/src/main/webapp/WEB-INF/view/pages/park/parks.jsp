<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>
	<div class="disney-bg-full">
	
		<div class="park-p-items" >
			 <c:forEach items="${parks }" var="park" >
			 	<div class="park-p-item" >
					<div class="park-p-item-info bor-top bor-right" >
						<div class="item">
							<div class="name" >${park.parkName }</div>
							<div class="price">单价：${park.price }元/小时</div>
							<div class="note">- 剩余车位 -</div>
							<div class="num">${park.restSpace }</div>
							<div class="location" data-code="${park.qrCode }">车库位置  &nbsp;<i class="fa fa-chevron-right"></i></div>
						</div>
					</div>
				</div>
			 </c:forEach>
		</div>
	</div>
	
	<%@ include file="../include/_bottom_footer.jsp" %>

	<script type="text/javascript">
		$(".park-p-item-info").each(function(){
			var height = $(this).css('height');
			var heightItem = $(this).find(".item").css('height');
			
			var top = (parseInt(height) -  parseInt(heightItem))/2; 
			$(this).find(".item").animate({"top":top});
		});
		
		$(".location").on("click",function(){
			var code = $(this).data("code");
			window.location = '/disney/carport/park.html?code='+code;
		});
	
	</script>

</body>

</html>
