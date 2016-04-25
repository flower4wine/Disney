<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>

	<div class="guide-map" >
		<div class="guide-map-container" >
			<table >
				<tr>
					<td>
						<img class="pre-view"  alt="" src="<c:url value= '${location.locationImg }' />" 	  > 
					</td>
				</tr>
			</table>
		</div> 
	</div>
	
	<div class="guide-ab-bottom">
		<div class="guide-loc-info" >
			<div class="guide-loc-info-left" >
				<div class="title" ><i class="fa fa-map-marker"></i> ${location.name }</div>
				<div class="position" >
					
					<div>
						${location.remark }
					</div>
					
				</div>
				<div class="note" >记录停车位置，便于返程寻车定位</div>
			</div>
			
			<div class="guide-loc-info-right" >
				<div class="rescan-border" >
					<div class="rescan">
						<img  alt="" src="<c:url value='/resources/images/scan.gif' />">
					</div>
					<div class="rescan-note" >重新定位</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="guide-loc-confirm"  >
			<span  onclick="window.location='/disney/pg/loConfirm.html?code=${location.code}';">确认当前位置</span>
		</div>
	</div>
	
	
	<div  class="btn_handler btn_minus_pos">
		<i class="fa fa-minus"></i>
	</div>
	
	<div  class="btn_handler btn_plus_pos">
		<i class="fa fa-plus"></i>
	</div>
	
	
	<form>
		<input type="hidden" id="timestamp" value="${timestamp}" /> 
		<input type="hidden" id="nonceStr" value="${nonceStr}" /> 
		<input type="hidden" id="signature" value="${signature}" /> 
		<input type="hidden" id="appId" value="${appId}" />
	</form>
	
	
	<script type="text/javascript">
		window.onload = function() {
			resetGuideMap();
			$(".btn_minus_pos").on('click',minGuideMapImg);
			$(".btn_plus_pos").on('click',maxGuideMapImg);
			
			var timestamp = $("#timestamp").val();//时间戳
			var nonceStr = $("#nonceStr").val();//随机串
			var signature = $("#signature").val();//签名
			var appId = $("#appId").val();

			wxConfig(timestamp, nonceStr, signature, appId);

			$(".rescan").on("click", function() {
				wxOnScan(function(code){
					window.location = '/disney/pg/lo.html?co='+code;
				});
			});

			$("img.pre-view").on("click", function() {
				wxPreViewImg($(this));
			});
		}
	</script>

</body>

</html>