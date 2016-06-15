<%@ page language="java" pageEncoding="UTF-8"%>

<div  class="guide-map" >
	<div class="guide-map-container" >
		<table >
			<tr>
				<td>
					<img class="pre-view" alt="" data-out-url="<c:out value='${staticFileUrl }${guide.outPic }' />"  
						data-in-url="<c:out value='${staticFileUrl }${guide.innerPic }' />"  
						src="<c:out value='${staticFileUrl }${guide.outPic }' />" 	  > 
				</td>
			</tr>
		</table>
	</div> 
	
	<div class="guide-map-remark">
		* 点击导览图看大图
	</div>
</div>

<div class="guide-ab-bottom">
	
	<div class="clear"></div>
	
	<div class="guide-detail" >
		
		<div class="guide-detail-title" onclick="$('#detailStep').toggle();">
			<span>推荐路线</span>
		</div>
		
		<div class="guide-detail-info" >当前位置  -> ${guide.destName }</div>
		<div class="guide-detail-time" >约${guide.time }分钟 | 约${guide.distince }米</div>
	</div>
	
	
	<div id="detailStep" class="guide-step-detail" >
		
		<div class="guide-step-time" >运营时段： 8:00 ~ 21:30</div>
		
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