<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="./include/_common_header.jsp"%>



</head>



<body class="bg-color-black">

	<div class="idx_top">

		<div>
			<img alt="" src="<c:url value= '/resources/images/index_bg.jpg' />">
		</div>

		<div class="items">

			<div class="item item-parkguide">
				<div class="item_circle item_col1">
					<img alt="" class="item_img"
						src="<c:url value= '/resources/images/item_1.png' />">
				</div>
				<div class="item_text item_bot_col1">
					<span>停车导览</span>
				</div>
			</div>

			<div class="item item-parkleave">
				<div class="item_circle item_col2">
					<img alt="" class="item_img"
						src="<c:url value= '/resources/images/item_2.png' />">
				</div>
				<div class="item_text item_bot_col2">
					<span>离园寻车</span>
				</div>
			</div>

			<div class="item item-parkpay">
				<div class="item_circle item_col3">
					<img alt="" class="item_img"
						src="<c:url value= '/resources/images/item_3.png' />">
				</div>
				<div class="item_text item_bot_col3">
					<span>停车缴费</span>
				</div>
			</div>

			<div class="item item-carport">

				<div class="item_circle item_col4">
					<img alt="" class="item_img"
						src="<c:url value= '/resources/images/item_4.png' />">
				</div>

				<div class="item_text item_bot_col4">
					<span>车库余位</span>
				</div>

			</div>

			<div class="clear"></div>

		</div>

	</div>

	<div class="idx_center">
		<div class="idx_slider">
			<ul>
				<%@ include file="./_solid_bot.jsp" %>
			</ul>
		</div>
		<div class="clear"></div>
	</div>

	<%@ include file="./include/_bottom_footer.jsp" %>
	
	<form>
		<input type="hidden" id="timestamp" value="${timestamp}" /> 
		<input type="hidden" id="nonceStr" value="${nonceStr}" /> 
		<input type="hidden" id="signature" value="${signature}" /> 
		<input type="hidden" id="appId" value="${appId}" />
		
		<input type="hidden" id="parkCode" value="${parkCode}" />
		<input type="hidden" id="leaveCode" value="${leaveCode}" />
	</form>



</body>

<script type="text/javascript">

window.onload = function() {
	//首页页面自动调整
	idxUIFit();

	//微信扫一扫配置
	var timestamp = $("#timestamp").val();//时间戳
	var nonceStr = $("#nonceStr").val();//随机串
	var signature = $("#signature").val();//签名
	var appId = $("#appId").val();

	wxConfig(timestamp, nonceStr, signature, appId);
	
	
	$(".item-parkguide").on("click",function(){
		var code = $("#parkCode").val();
		if(code!='' && code.length==12){
			window.location = '/disney/pg/lo.html?co='+code;
			return;
		}
		wxOnScan(function(code){
					window.location = '/disney/pg/lo.html?co='+code;
				});
	});
	
	
	$(".item-parkleave").on("click",function(){
		var code = $("#leaveCode").val();
		
		if(code!='' && code.length==12){
			window.location = '/disney/le/lo.html?co='+code;
			return;
		}
		wxOnScan(function(code){
					window.location = '/disney/le/lo.html?co='+code;
				});
	});
	
	
	$(".item-carport").on("click",function(){
		window.location = '/disney/carport/parks.html';
	});
	
	$(".item-parkpay").on("click",function(){
		window.location = '/disney/parkpay/queryParkPay.html';
	});
	
	
}
</script>

</html>
