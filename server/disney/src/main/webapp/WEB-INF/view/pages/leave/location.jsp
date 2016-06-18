<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>

	
	<div class="guide-map" >
		<div class="guide-map-container" >
			<table >
				<tr>
					<td>
						<img class="pre-view"  alt="" src="<c:out value='${staticFileUrl }${location.locationImg }' />" 	  > 
					</td>
				</tr>
			</table>
		</div> 
	</div>
	
	<div class="guide-ab-bottom">
		<div class="guide-loc-info" >
		
			<div class="guide-loc-info-left" >
				
				<table>
					<tr class="title" >
						<td class="left"><i class="fa fa-map-marker"></i></td>
						<td>${location.name }</td>
					</tr>
					<tr>
						<td class="left">
							<img  alt="" src="<c:out value='${staticFileUrl }/resources/images/mark-xing.png' />" >
						</td>
						<td class="loc-mark-single-txt">
							${location.remark }
						</td>
					</tr>
					<tr class="note">
						<td class="left"><i class="fa fa-asterisk" aria-hidden="true"></i></td>
						<td>确认当前位置,寻车导览</td>
					</tr>
				</table>
				
			</div>
			
			
			<div class="guide-loc-info-right" >
				<div class="rescan-border" >
					<div class="rescan">
						<img alt="" src="<c:out value='${staticFileUrl }/resources/images/scan.gif' />">
					</div>
					<div class="rescan-note" >重新定位</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="guide-loc-confirm"  >
			<span  onclick="window.location='/disney/le/loConfirm.html?code=${location.code}';">确认当前位置</span>
		</div>
	</div>
	
	
	<form>
		<input type="hidden" id="timestamp" value="${timestamp}" /> 
		<input type="hidden" id="nonceStr" value="${nonceStr}" /> 
		<input type="hidden" id="signature" value="${signature}" /> 
		<input type="hidden" id="appId" value="${appId}" />
		<input type="hidden" id="code" value="${code}" />
	</form>
	
	<script type="text/javascript">

		window.onload = function() {
		
			resetGuideMap();
			
			var timestamp = $("#timestamp").val();//时间戳
			var nonceStr = $("#nonceStr").val();//随机串
			var signature = $("#signature").val();//签名
			var appId = $("#appId").val();

			wxConfig(timestamp, nonceStr, signature, appId);

			$(".rescan").on("click", function() {
				wxOnScan(function(code){
					window.location = '/disney/le/lo.html?co='+code;
				});
			});

			$("img.pre-view").on("click", function() {
				wxPreViewImg($(this));
			});

		}
	</script>

</body>

</html>
