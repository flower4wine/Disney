<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>
	<div class="disney-bg-full">
		
		<div class="park-info-query"  >
			<div class="park-info-title" >输入您的车牌号</div>
			<div class="park-info-remark" >以便于我们对您的车辆进行停车缴费</div>
			<div>
				<input class="park-info-input" name="carNo" disabled="disabled" placeholder="功能测试中，敬请期待" >
			</div>
			
			<div class="park-info-border" >
				<div class="bg-color_red park-info-btn" >
					确认
				</div>
			</div>
		</div>
						
	</div>
	
	<div id="dialog" class="weui_dialog_alert" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd" style="color: #777;"><strong class="weui_dialog_title">提示信息</strong></div>
            
            <div class="weui_dialog_bd" >
				功能测试中，敬请期待！
			</div>
			
            <div class="weui_dialog_ft">
                <a class="weui_btn_dialog primary" href="javascript:;">确定</a>
            </div>
        </div>
    </div>
	
	<%@ include file="../include/_bottom_footer.jsp" %>

	<script type="text/javascript">
		function showInfo(){
		 	var dig = $('#dialog');
		 	
		    dig.show();
		    
		    dig.find('.weui_btn_dialog').one('click', function () {
		        //dig.hide();
		         window.location = '/disney/index.html';
		    });
		    
		}
	
		$(".bg-color_red").on("click",function(){
			//var carNo = $("input[name='carNo']").val();
			//window.location = '/disney/parkpay/parkpay.html?carNo='+carNo;
			showInfo();
		});
	</script>

</body>

</html>
