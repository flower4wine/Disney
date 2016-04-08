<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../include/_common_header.jsp"%>

<script type="text/javascript"
	src="<c:url value= '/resources/script/jquery.touchSwipe.min.js'/>"></script>
	

<style>
<!--

-->
</style>


</head>

<body>
	
	<div  class="guide-map" >
		<div class="guide-map-container" >
			<table >
				<tr>
					<td>
						<img alt="" data-out-url="<c:url value= '${guide.outPic }' />"  
							data-in-url="<c:url value= '${guide.innerPic }' />"
						  src="<c:url value= '${guide.innerPic }' />" 	  > 
					</td>
				</tr>
			</table>
		</div> 
	</div>
	
	<div class="guide-ab-bottom">
		
		<div class="guide-top" onclick="$('#detailStep').toggle();">详情</div>
		
		<div class="clear"></div>
		
		<div class="guide-detail" >
			
			<div class="guide-detail-title" >
				<span>推荐路线</span>
			</div>
			
			<div class="guide-toggle" >内</div>
			
			<div class="guide-detail-info" >当前位置  -> ${guide.destName }</div>
			<div class="guide-detail-time" >${guide.time } | ${guide.distince }</div>
		</div>
		
		
		<div id="detailStep" class="guide-step-detail" >
			
			<div class="guide-step-time" >运营时段： 6:00 ~ 22:30</div>
			
			<ul class="txt-color-gray">
				
				<li class="txt-color-blue">
					<div class="guide-step-tag">
						<i class="fa fa-circle-o" ></i> 
					</div>
					
					<div class="guide-step-txt">
						当前位置
					</div>
					
					<div class="clear"></div>
				</li>
				
				<c:forEach items="${guide.steps }" var="item">
					<li>
						<div class="guide-step-tag">
							<i class="fa fa-circle-o" ></i> 
						</div>
						
						<div class="guide-step-txt">
							${item }
						</div>
						
						<div class="clear"></div>
					</li>
				</c:forEach>
				
				<li class="txt-color-red">
					<div class="guide-step-tag">
						<i class="fa fa-circle-o" ></i> 
					</div>
					
					<div class="guide-step-txt">
						${guide.destName }
					</div>
					
					<div class="clear"></div>
				</li>
			</ul>
			
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
	
	
<style>

</style>

	<script type="text/javascript">
		$(".btn_toggle_pos").on('click',function(){
			
			$('.guide-ab-bottom').toggle();
			
			$(this).find('.fa-arrow-down').toggleClass("fa-arrow-up");
			$(this).find('.fa-arrow-up').toggleClass("fa-arrow-down");
			
			$(".btn_minus_pos").toggle();
			$(".btn_plus_pos").toggle();
			
			$(".guide-map .guide-map-container").animate({width:clientWidth()});
			
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
		
		$(".guide-toggle").on("click",function(){
			var o = $(".guide-map img").data("out-url");
			var i = $(".guide-map img").data("in-url");
			var src = $(".guide-map img").attr("src");
			
			if(src == o){
				 $(".guide-map img").attr("src",i);
				 $(this).html("内");
			}else{
				 $(".guide-map img").attr("src",o);
				 $(this).html("外");
			}
		});
		
		
	</script>

</body>

</html>
