<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>
	<div class="disney-bg-full">
		
		<div class="park-info-query"  >
			<div class="park-info-title" >输入您的车牌号</div>
			<div class="park-info-remark" >以便于我们对您的车辆进行停车缴费</div>
			<div>
				<input class="park-info-input" name="carNo" >
			</div>
			
			<div class="park-info-border" >
				<div class="bg-color_red park-info-btn" >
					确认
				</div>
			</div>
		</div>
						
	</div>
	
	<%@ include file="../include/_bottom_footer.jsp" %>

	<script type="text/javascript">
		$(".bg-color_red").on("click",function(){
			var carNo = $("input[name='carNo']").val();
			window.location = '/disney/parkpay/parkpay.html?carNo='+carNo;
		});
	</script>

</body>

</html>
