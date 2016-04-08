<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>


</head>

<body>


	<div  class="guide-map" >
		<div class="guide-map-container" >
			<table >
				<tr>
					<td>
						<img alt="" src="<c:url value= '/resources/images/total_1.jpg' />" 	  > 
					</td>
				</tr>
			</table>
		</div> 
	</div>
	
	<div class="guide-ab-bottom">
	
		<div class="guide-loc-info" >
			<div class="guide-loc-info-left" >
				<div class="title" >位置信息</div>
				<div class="position" ><i class="fa fa-map-marker"></i>  停车场1C区3排306</div>
				<div class="note" >记录停车位置，便于返程寻车定位</div>
			</div>
			
			<div class="guide-loc-info-right" >
				<div class="rescan-border" >
					<div class="rescan"  onclick="window.location = '/disney/parkGuide/scan.html';">
						<img alt="" src="<c:url value='/resources/images/scan.gif' />" width="35px;">
					</div>
					<div class="rescan-note" >重新定位</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="guide-loc-confirm"  >
			<span  onclick="window.location='/disney/parkGuide/outlets.html';">确认当前位置</span>
		</div>
	</div>
	
	<div  class="btn_handler btn_toggle_pos">
		<i class="fa fa-arrow-down"></i>
	</div>
	
	<div  class="btn_handler btn_minus_pos">
		<i class="fa fa-minus"></i>
	</div>
	
	<div  class="btn_handler btn_plus_pos">
		<i class="fa fa-plus"></i>
	</div>
	
	
	<script type="text/javascript">
	$(".btn_toggle_pos").on('click',function(){
	
		$(this).find('.fa-arrow-down').toggleClass("fa-arrow-up");
		$(this).find('.fa-arrow-up').toggleClass("fa-arrow-down");
		
		$(".btn_minus_pos").toggle();
		$(".btn_plus_pos").toggle();
		
		$(".guide-map .guide-map-container").animate({width:clientWidth()});
		
		$('.guide-ab-bottom').toggle();
		
	});
	
	$(".btn_minus_pos").on('click',function(){
		var w = parseInt($(".guide-map .guide-map-container").css("width"))*0.8;
		if( w > clientWidth()){
			$(".guide-map .guide-map-container").animate({width:w});
		}else{
			$(".guide-map .guide-map-container").animate({width:clientWidth()});
		}
	});
	
	
	$(".btn_plus_pos").on('click',function(){
		var w = parseInt($(".guide-map .guide-map-container").css("width"))*1.2;
		$(".guide-map .guide-map-container").animate({width:w});
	});
	
	
	function resetGuideMap(){
		var height = clientHeight();
		var headHeight = parseInt($(".guide-ab-bottom").css("height"))+20;
		
		var contentHeight = (height - headHeight) + "px";
		$(".guide-map .guide-map-container").animate({height:contentHeight});
	}
	
	resetGuideMap();
	</script>

</body>

</html>
