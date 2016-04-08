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



<body>

	<div class="idx_top">

		<div>
			<img alt="" src="<c:url value= '/resources/images/index_bg.png' />">
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
				<c:forEach step="1" begin="1" end="5" var="i">
					<li>
						<div class="slider_item">
	
							<div class="text">ADD TITLE ${i }</div>
	
							<div class="img">
								<img alt="" src="<c:url value= '/resources/images/slider_1.jpg' />">
							</div>
	
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="clear"></div>
	</div>



	<div class="idx_bot">
		<div class="img">
			<img alt="" src="<c:url value= '/resources/images/index_bot.png' />">
		</div>
	</div>



</body>

<script type="text/javascript">
	
	$(".item_circle").each(function(){
		var w = $(this).css("width");
		$(this).css("height", w);
	});

	$(".idx_slider").unslider({
		infinite : true
	});

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
	
	$(".item-parkguide").on("click",function(){
		window.location = '/disney/parkGuide/scan.html';
	});
	
	$(".item-carport").on("click",function(){
		window.location = '/disney/carport/parks.html';
	});
	
	$(".item-parkpay").on("click",function(){
		window.location = '/disney/parkpay/queryParkPay.html';
	});
	
	$(".item-parkleave").on("click",function(){
		window.location = '/disney/leave/queryPark.html';
	});
	

	if(scrollHeight() > clientHeight()){
		$(".idx_bot").css("position","relative");
	}
	
	
</script>

</html>
