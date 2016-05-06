<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

<style>

</style>

</head>

<body>
	<div class="disney-bg-full">
		
		<div style="text-align: center;background-color: rgba(7,7,7,0.7);height:100%;">
			<div style="font-size: 180%;padding: 30px 0 10px 0;letter-spacing: 2PX;">输入您的车牌号</div>
			<div style="color: #CCC;padding-bottom:20px;">以便于我们对您的车辆进行停车缴费</div>
			<div>
				<input name="carNo" style="height: 60px;width:80%;padding: 15px;font-size: 180%;text-align: center;border-radius:4px;border: none;letter-spacing: 2px;">
			</div>
			<div style="padding: 20px 0;">
				<div class="bg-color_red" style="width:80%;margin: 0 auto;padding: 12px 0px;border-radius:4px;font-size: 180%;letter-spacing: 4px;">
					确认
				</div>
			</div>
		</div>
						
	</div>
	
	<div style="position: absolute;padding: 15px 0;text-align: center;bottom: 0px;width: 100%;font-size: 130%;letter-spacing: 3px;background-color: black;">
		缴费记录   &nbsp;<i class="fa fa-chevron-right"></i>
	</div>
	

	<script type="text/javascript">
		$(".bg-color_red").on("click",function(){
			var carNo = $("input[name='carNo']").val();
			window.location = '/disney/parkpay/parkpay.html?carNo='+carNo;
			// onclick="window.location='/disney/parkpay/parkpay.html';"
		});
	
	</script>

</body>

</html>
