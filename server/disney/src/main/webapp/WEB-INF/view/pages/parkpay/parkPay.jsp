<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

<style>

.park-pay-info{
	font-size: 130%;
	padding: 5px 0;
	
}

.park-pay-info-left{
	float: left;
	color:#AAA;
}

.park-pay-info-right{
	float: right;
}

</style>

</head>

<body style="background-color: white;">
	
	<div class="park-pay-top" >
		<table style="text-align: right;width: 100%;">
			<tr accesskey="right">
				<td>
					<span style="padding:8px 20px;border:solid 1px white;border-radius:4px;">共支付金额</span>
				</td>
			</tr>
			<tr>
				<td>
					<div style="margin-top: 15px;font-size: 300%;"><i class="fa fa-jpy"></i> ${order.serviceFee }</div>
				</td>
			</tr>
		</table>
	</div>	
	
	<div style="color: #777;padding: 15px;">
		
		<div style="font-size: 150%;padding-bottom: 8px;font-weight: bold;">停车信息</div>
		
		<div style="text-align: center;padding: 8px;
			border-radius:4px;
			border: solid 1px #ccc;font-size: 150%;background-color: #eee;letter-spacing: 1px;margin-bottom:5px;">
			${order.carNo }
		</div>
		
		<div class="park-pay-info">
			<div class="park-pay-info-left" >入场时间</div>
			<div class="park-pay-info-right" >${order.startTime }</div>
			<div class="clear"></div>
		</div>
		
		<div class="park-pay-info">
			<div class="park-pay-info-left" >停车时长</div>
			<div class="park-pay-info-right" >${order.serviceTimed }</div>
			<div class="clear"></div>
		</div>
		
		<div class="park-pay-info">
			<div class="park-pay-info-left" >支付费用</div>
			<div class="park-pay-info-right" >${order.serviceFee }元</div>
			<div class="clear"></div>
		</div>
		
		
		<div style="font-size: 150%;border-bottom: solid 1px #ccc;padding-bottom: 5px;padding-top: 30px;font-weight: bold;">支付方式</div>
		
		<div>
			<table style="color: #777; padding:15px 0px;">
				<tr>
					<td><img alt="" src="<c:url value= '/resources/images/pay-wx.jpg' />" style="width:30px;"></td>
					<td style="padding: 0 10px;">微信支付</td>
					<td><img alt="" src="<c:url value= '/resources/images/pay-ali.jpg' />" style="width:30px;"></td>
					<td  style="padding: 0 10px;">支付宝支付</td>
				</tr>
				
			</table>
			<div class="clear"></div>
		</div>
		
		
		
		<div style="padding: 10px 0;" >
			<div class="bg-color_red" style="text-align:center;color:white; margin: 0 auto;padding: 12px 0px;border-radius:4px;font-size: 180%;letter-spacing: 4px;">
				支付
			</div>
		</div>
		
		<div style="padding: 10px 0;">
			<div class="park-pay-query" style="">
				缴费记录   &nbsp;<i class="fa fa-chevron-right"></i>
			</div>
		</div>
		
	</div>
	
	

	<script type="text/javascript">
	
	
	</script>

</body>

</html>
