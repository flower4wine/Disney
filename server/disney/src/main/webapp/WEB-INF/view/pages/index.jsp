<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="./include/_common_header.jsp"%>

<link rel="stylesheet" type="text/css"
	href="<c:url value= '/resources/css/unslider.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value= '/resources/css/unslider-dots.css'/>">


<script type="text/javascript"
	src="<c:url value= '/resources/script/unslider-min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value= '/resources/script/jquery.touchSwipe.min.js'/>"></script>

</head>



<body class="bg-color-black">

	<div class="idx_top">

		<div>
			<%-- <img alt="" src="<c:url value= '/resources/images/bg_1_375_256.png' />"> --%>
			<img alt="" src="<c:url value= '/resources/images/index_bg_375_256.png' />">
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

	<div class="idx_bot">
		<div class="img">
			<img alt="" src="<c:url value= '/resources/images/index_bot.png' />">
		</div>
	</div>
	
	<form>
		<input type="hidden" id="timestamp" value="${timestamp}" /> 
		<input type="hidden" id="nonceStr" value="${nonceStr}" /> 
		<input type="hidden" id="signature" value="${signature}" /> 
		<input type="hidden" id="appId" value="${appId}" />
		<input type="hidden" id="code" value="${code}" />
		
	</form>



</body>

<script type="text/javascript">
window.onload = function() {
	//调整底部
	if(scrollHeight() > clientHeight()){
		$(".idx_bot").css("position","relative");
	}

	//调整位置CSS
	$(".item_circle").each(function(){
		var w = $(this).css("width");
		$(this).css("height", w);
	});

	//轮播
	$(".idx_slider").unslider({
		infinite : true
	});

	//轮播滑动
	$(".idx_slider").swipe({
		swipeLeft : function(event, direction, distance, duration,
				fingerCount) {
			$(".unslider .next").click();
		},

		swipeRight : function(event, direction, distance, duration,
				fingerCount) {
			$(".unslider .prev").click();
		},

		allowPageScroll : "auto"

	});

	//微信扫一扫配置
	if (isWeiXin()) {
		
		var timestamp = $("#timestamp").val();//时间戳
		var nonceStr = $("#nonceStr").val();//随机串
		var signature = $("#signature").val();//签名
		var appId = $("#appId").val();
		
		wx.config({
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : appId, // 必填，公众号的唯一标识
			timestamp : timestamp, // 必填，生成签名的时间戳
			nonceStr : nonceStr, // 必填，生成签名的随机串
			signature : signature,// 必填，签名，见附录1
			jsApiList : [ 'scanQRCode' ] // 需要检测的JS接口列表，所有JS接口列表见附录2,
		});
		
		wx.error(function(res){
			$(".weui_btn").addClass("weui_btn_disabled");
			console.log('微信配置出错。');
		});
	}
	
	
	$(".item-parkguide").on("click",function(){
		
		var code = $("#code").val();
		if(code!='' && code.length==12){
			window.location = '/disney/pg/lo.html?co='+code;
			return;
		}
		
		onScan();
	});
	
	
	$(".item-carport").on("click",function(){
		window.location = '/disney/carport/parks.html';
	});
	
	$(".item-parkpay").on("click",function(){
		window.location = '/disney/parkpay/queryParkPay.html';
	});
	
	$(".item-parkleave").on("click",function(){
		onScan();
	});
	
	
}

function onScan(){
	if(!isWeiXin()){
		window.location = '/disney/pg/lo.html?co=03-0001-0001';
		return;
	}
	
	wx.ready(function(){
		//点击扫描按钮，扫描二维码并返回结果
		wx.scanQRCode({
			needResult : 0,
			desc : 'scanQRCode desc',
			success : function(res) {
				//扫码后获取结果参数:htpp://xxx.com/c/?6123，截取到url中的防伪码后，赋值给Input
				//window.location = '/disney/pg/lo.html?co='+res.resultStr;
				console.log(res);
			},
			fail:function(res){
				tmsError('执行扫码出错。');
			}
		});
	});
}
	
</script>

</html>
