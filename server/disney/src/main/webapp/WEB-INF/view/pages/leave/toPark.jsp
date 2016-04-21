<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../include/_common_header.jsp"%>

<script type="text/javascript" src="<c:url value= '/resources/script/jquery.touchSwipe.min.js'/>"></script>

</head>

<body>
	
	<div  class="guide-map" >
		<div class="guide-map-container" >
			<table >
				<tr>
					<td>
						<img class="pre-view" alt=""  src="<c:url value= '${guide.outPic }' />" 	  > 
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
			
			<div class="guide-detail-info" >当前位置  -> ${guide.destName }</div>
			<div class="guide-detail-time" >${guide.time }分钟 | ${guide.distince }米</div>
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
					
					<c:if test="${item.stepType eq 0 }"><li class="step-walk"></c:if>
					<c:if test="${!(item.stepType eq 0) }"><li class="step-bus" ></c:if>
					
						<div class="guide-step-tag">
							<i class="fa fa-circle-o" ></i> 
						</div>
						
						<div class="guide-step-txt">
							${item.remark }
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
	
	<div  class="btn_handler btn_minus_pos">
		<i class="fa fa-minus"></i>
	</div>
	
	<div  class="btn_handler btn_plus_pos">
		<i class="fa fa-plus"></i>
	</div>
	
	
<style>

</style>

	<script type="text/javascript">
	window.onload = function() {
		resetGuideMap();
		
		$(".btn_minus_pos").on('click',minGuideMapImg);
		$(".btn_plus_pos").on('click',maxGuideMapImg);
		
		$("img.pre-view").on("click", function(){
			wxPreViewImg($(this));
		});
	}
	</script>

</body>

</html>
