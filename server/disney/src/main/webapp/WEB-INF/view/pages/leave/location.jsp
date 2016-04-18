<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>

	<div class="guide-map" >
		<div class="guide-map-container" >
			<table >
				<tr>
					<td>
						<img alt="" src="<c:url value= '${location.locationImg }' />" 	  > 
					</td>
				</tr>
			</table>
		</div> 
	</div>
	
	<div class="guide-ab-bottom">
	
		<div class="guide-loc-info" >
			<div class="guide-loc-info-left" >
				<div class="title" >位置信息</div>
				<div class="position" ><i class="fa fa-map-marker"></i> ${location.remark }</div>
				<div class="note" >确认当前位置,便于寻车导览</div>
			</div>
			
			<div class="guide-loc-info-right" >
				<div class="rescan-border" >
					<div class="rescan">
						<img alt="" src="<c:url value='/resources/images/scan.gif' />">
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
	
	<div  class="btn_handler btn_toggle_pos">
		<i class="fa fa-arrow-down"></i>
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
		<input type="hidden" id="code" value="${code}" />
	</form>
	
	<script type="text/javascript">
	
	window.onload = function() {
		if (isWeiXin()) {
		
			$(".weui_btn").removeClass("weui_btn_disabled");
			
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
	}
	
	$(".btn_toggle_pos").on('click',function(){
	
		$(this).find('.fa-arrow-down').toggleClass("fa-arrow-up");
		$(this).find('.fa-arrow-up').toggleClass("fa-arrow-down");
		
		$(".btn_minus_pos").toggle();
		$(".btn_plus_pos").toggle();
		
		//$(".guide-map .guide-map-container").animate({width:clientWidth()});
		
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
	
	$(".rescan").on("click",function(){
		if(!isWeiXin() || $(this).hasClass("weui_btn_disabled")){
			window.location = '/disney/pg/lo.html?co=03-0002-0001';
			return;
		}
		
		onScan();
	});
	
	function onScan(){
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
