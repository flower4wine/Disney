<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>
	
	<div class="park-pay-top" >
		<table >
			<tr accesskey="right">
				<td>
					<span class="title" >共支付金额</span>
				</td>
			</tr>
			<tr>
				<td>
					<div class="amt" ><i class="fa fa-jpy"></i> ${order.serviceFee }</div>
				</td>
			</tr>
		</table>
	</div>	
	
	<div class="park-pay-content" >
		
		<div class="title" >停车信息</div>
		
		<div class="carno" >
			${order.carNo }
		</div>
		
		<div class="park-pay-info">
			<div class="park-pay-info-left" >入场时间</div>
			<div class="park-pay-info-right" >${order.startTime }</div>
			<div class="clear"></div>
		</div>
		
		<div class="park-pay-info">
			<div class="park-pay-info-left" >停车时长</div>
			<div class="park-pay-info-right" >${order.serviceTime }</div>
			<div class="clear"></div>
		</div>
		
		<div class="park-pay-info">
			<div class="park-pay-info-left" >支付费用</div>
			<div class="park-pay-info-right" >${order.serviceFee }元</div>
			<div class="clear"></div>
		</div>
		
		<div class="paytitle" >支付方式</div>
		
		<div>
			<table class="paylist" >
				<tr>
					<td><img alt="" src="<c:out value='${staticFileUrl }/resources/images/pay-wx.jpg' />" ></td>
					<td class="paystyle" style="padding: 0 10px;">微信支付</td>
					<td><img alt="" src="<c:out value='${staticFileUrl }/resources/images/pay-ali.jpg' />" ></td>
					<td  class="paystyle" >支付宝支付</td>
				</tr>
				
			</table>
			<div class="clear"></div>
		</div>
		
		<div class="payborder"  >
			<div class="bg-color_red paybtn" >
				支付
			</div>
		</div>
		
	</div>

</body>

</html>
