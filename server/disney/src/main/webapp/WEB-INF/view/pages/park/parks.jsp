<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>



</head>

<body>
	<div class="disney-bg-full">
	
		<div class="park-p-items" >

				<div class="park-p-item" >
					
					<div class="park-p-item-info bor-right" >
						
						<div class="item">
							<div class="name" >P1停车场</div>
							<div class="price">单价：15元/小时</div>
							<div class="note">- 剩余车位 -</div>
							<div class="num">510</div>
							<div class="location">车库位置  &nbsp;<i class="fa fa-chevron-right"></i></div>
						</div>
					
					</div>
					
				</div>
				
				<div class="park-p-item" >
					
					<div class="park-p-item-info " >
						<div class="item">
							<div class="name" >P1停车场</div>
							<div class="price">单价：15元/小时</div>
							<div class="note">- 剩余车位 -</div>
							<div class="num">510</div>
							<div class="location">车库位置  &nbsp;<i class="fa fa-chevron-right"></i></div>
						</div>
					</div>
					
				</div>
				
				<div class="park-p-item" >
					
					<div class="park-p-item-info bor-top bor-right" >
					
						<div class="item">
							<div class="name" >P1停车场</div>
							<div class="price">单价：15元/小时</div>
							<div class="note">- 剩余车位 -</div>
							<div class="num">510</div>
							<div class="location">车库位置  &nbsp;<i class="fa fa-chevron-right"></i></div>
						</div>
					
					</div>
					
				</div>
				
				<div class="park-p-item" >
					
					<div class="park-p-item-info bor-top" >
					
						<div class="item">
							<div class="name" >P1停车场</div>
							<div class="price">单价：15元/小时</div>
							<div class="note">- 剩余车位 -</div>
							<div class="num">510</div>
							<div class="location">车库位置  &nbsp;<i class="fa fa-chevron-right"></i></div>
						</div>
					
					</div>
					
				</div>
				
						
		</div>
	</div>
	
	<div class="idx_bot">
		<div class="img">
			<img alt="" src="<c:url value= '/resources/images/index_bot.png' />">
		</div>
	</div>

	<script type="text/javascript">
		$(".park-p-item-info").each(function(){
			var height = $(this).css('height');
			var heightItem = $(this).find(".item").css('height');
			
			var top = (parseInt(height) -  parseInt(heightItem))/2; 
			$(this).find(".item").animate({"top":top});
		});
		
		$(".location").on("click",function(){
			window.location = '/disney/carport/park.html';
		});
	
	
	</script>

</body>

</html>
