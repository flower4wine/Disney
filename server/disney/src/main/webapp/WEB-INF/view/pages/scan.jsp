<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="./include/_common_header.jsp"%>

<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

</head>

<body>

	<form>
		<input type="hidden" id="timestamp" value="${timestamp}" /> 
		<input type="hidden" id="nonceStr" value="${nonceStr}" /> 
		<input type="hidden" id="signature" value="${signature}" /> 
		<input type="hidden" id="appId" value="${appId}" />
	</form>
	
	
	<div class="scan-wechat" >
	
		<div class="bg-color_red scan-remark" >
			* 微信公众号打开微信扫一扫进行扫码
		</div>
		
		<div class="scan-img" >
			<img alt="" src="<c:url value='/resources/images/scan.gif' />" >
		</div>
		
		<div  class="scan-btn" >
			<a href="#" class="weui_btn weui_btn_warn weui_btn_disabled bg-color_red" >点击扫描</a>
		</div>
			
	</div>
	


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
		
		
		$(".weui_btn").on("click",function(){
			if(!isWeiXin() || $(this).hasClass("weui_btn_disabled")){
				window.location = '/disney/pg/lo.html?co=03-0001-0001';
				return;
			}
			
			wx.ready(function(){
				//点击扫描按钮，扫描二维码并返回结果
				wx.scanQRCode({
					needResult : 1,
					desc : 'scanQRCode desc',
					success : function(res) {
						//扫码后获取结果参数:htpp://xxx.com/c/?6123，截取到url中的防伪码后，赋值给Input
						window.location = '/disney/pg/lo.html?co='+res.resultStr;
					},
					fail:function(res){
						tmsError('执行扫码出错。');
					}
				});
			});
		});
		
		
	</script>


</body>

</html>
