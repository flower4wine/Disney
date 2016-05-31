<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="./include/_common_header.jsp"%>

<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<style>
<!--
.weui_cell .left {
	width: 40%;
	text-align: right;
	padding-right: 10px;
	font-weight:bold;
	font-size:80%;
}

.weui_cell .right {
	width: 60%;
	text-align: left;;
}
-->
</style>

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
			* 二维码号牌检验工具
		</div>
		
		<div class="scan-img" >
			<img alt="" src="<c:url value='/resources/images/scan.gif' />" >
		</div>
		
		<div  class="scan-btn" >
			<a href="#" class="weui_btn weui_btn_warn weui_btn_disabled bg-color_red" >点击扫描</a>
		</div>
			
	</div>
	
	<div id="dialog2" class="weui_dialog_alert" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd" style="color: #777;"><strong class="weui_dialog_title">二维码信息展示</strong></div>
            <div class="weui_dialog_bd code-info" >

				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary left">
							<p>名称:&nbsp;</p>
						</div>
						<div class="weui_cell_ft right"><span class="name"></span></div>
					</div>
					
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary left">
							<p>区域:&nbsp;</p>
						</div>
						<div class="weui_cell_ft right"><span class="region"></span></div>
					</div>
					
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary left">
							<p>车位范围:&nbsp;</p>
						</div>
						<div class="weui_cell_ft right"><span class="range"></span></div>
					</div>
					
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary left">
							<p>号牌编号:&nbsp;</p>
						</div>
						<div class="weui_cell_ft right"><span class="code"></span></div>
					</div>
					
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary left">
							<p>号牌尺寸:&nbsp;</p>
						</div>
						<div class="weui_cell_ft right"><span class="size"></span></div>
					</div>
					
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary left">
							<p>安装方式:&nbsp;</p>
						</div>
						<div class="weui_cell_ft right"><span class="style"></span></div>
					</div>
				</div>

			</div>
            <div class="weui_dialog_ft">
                <a class="weui_btn_dialog primary" href="javascript:;">确定</a>
            </div>
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
		
		
		function showInfo(data){
		 	var dig = $('#dialog2');
		 	
		 	 dig.find('.code-info .name').html(data.name);
		 	 dig.find('.code-info .region').html(data.region);
		 	 dig.find('.code-info .range').html(data.range);
		 	 dig.find('.code-info .code').html(data.code);
		 	 dig.find('.code-info .size').html(data.size);
		 	 dig.find('.code-info .style').html(data.style);
		 	
            dig.show();
            
            dig.find('.weui_btn_dialog').one('click', function () {
            	$(".weui_btn").removeClass("weui_btn_disabled");
                dig.hide();
            });
		}
		
		function scanResultHandler(result){
			//扫描
			tmsAsynchGet('/scanVerify.html?url='+result, function(response){
				if(response.data && response.data!=''){
					showInfo(response.data);
				}else{
					tmsError('二维码格式不正确，无法正确解析。');
				}
			});
		}
		
		
		$(".weui_btn").on("click",function(){
			if(!isWeiXin() || $(this).hasClass("weui_btn_disabled")){
				return;
			}
			wx.ready(function(){
			
				$(".weui_btn").addClass("weui_btn_disabled");
			
				//点击扫描按钮，扫描二维码并返回结果
				wx.scanQRCode({
					needResult : 1,
					desc : 'scanQRCode desc',
					success : function(res) {
						//扫描结果处理
						if(res.resultStr){
							//扫描
							scanResultHandler(res.resultStr);
						}
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
