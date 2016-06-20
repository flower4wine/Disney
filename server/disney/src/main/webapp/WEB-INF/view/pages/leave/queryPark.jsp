<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>


</head>

<body>
	<div class="disney-bg-full">
		
		<div class="leave-page-bg" >
		
			<div  class="leave-top-message bg-color_red">* &nbsp;&nbsp;抱歉，系统未找到该车辆，请选择停车场自行定位</div>
		
			<div class="leave-tip-input">输入您的车牌号</div>
			
			<div class="leave-tip-info" >以便于我们对您的车辆进行定位</div>
			
			<div>
				<input class="leave-input" placeholder="功能测试,敬请期待!" disabled="disabled">
			</div>
			
			<div class="leave-btn-border">
				<div class="bg-color_red leave-btn" >
					确认
				</div>
			</div>
			
			
			<c:if test="${(not empty firstCarNo ) || (not empty secondCarNo ) }">
				<div class="his-cookies">
					<div class="his-cars">
						<ul>
							<c:if test="${not empty firstCarNo }">
								<li ><div class="car-no">${firstCarNo }</div></li> 
							</c:if>
							
							<c:if test="${not empty secondCarNo }">
								<li ><div class="car-no">${secondCarNo }</div></li>
							</c:if>
						</ul>
					</div>
				</div>
			</c:if>
				
			<div class="leave-select-park">
				<div class="leave-select-tip">或选择停车场</div>
				
				<ul class="leave-park">
					<li data-code="03-0001"><div class="leave-park-first">P1</div></li>
					<li data-code="03-0002"><div class="bor-left-none">P2</div></li>
					<li data-code="03-0003"><div class="bor-left-none">P3</div></li>
					<li data-code="03-0004"><div class="bor-left-none leave-park-last" >P4</div></li>
				</ul>
			</div>
			
			<div class="leave-select-remark">
				<div>
					您之前没有使用【停车导览】功能记录过停车位置，系统暂时只能将您导引到爱车停放的停车场！
				</div>
			</div>
		
		</div>
	</div>
	
	<div id="dialog" class="weui_dialog_alert" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd" style="color: #777;"><strong class="weui_dialog_title">提示信息</strong></div>
            
            <div class="weui_dialog_bd" >
				请选择停车场后，点击确认！
			</div>
			
            <div class="weui_dialog_ft">
                <a class="weui_btn_dialog primary" href="javascript:;">确定</a>
            </div>
        </div>
    </div>
    

	<script type="text/javascript">
		$(".leave-park li").on("click",function(){
			$(".leave-top-message").hide();
			$(".leave-input").val("");
			$(".leave-park li").removeClass("selected");
			$(".his-cars .car-no").removeClass("checked");
			$(this).addClass("selected");
		});
		
		function showInfo(){
		 	var dig = $('#dialog');
		 	
		    dig.show();
		    
		    dig.find('.weui_btn_dialog').one('click', function () {
		        dig.hide();
		    });
		    
		}
	
		$(".leave-btn-border").on("click",function(){
			var car = $(".leave-input").val();
			
			if(car!=''){
				//To Send request to server
				tmsAsynchGet('/le/checkCarNo.html?carNo='+car,function(response){
					if(response.data && response.data!='' && response.data.length == 7){
						window.location = '/disney/le/toParkEntrance.html?parkLocation='+response.data;
					}else{
						$(".leave-top-message").show();
					}
				});
				
			}else{
				var selected = $(".leave-park li.selected");
				
				if(selected.length>0){
					var code = $(selected).data("code");
					window.location = '/disney/le/toParkEntrance.html?parkLocation='+code;
				}else{
					//alert("请选择停车场后，点击确定！");
					showInfo();
				}
			}
		});
		
		
		$(".his-cars .car-no").on('click',function(){
		
			$(".his-cars .car-no").removeClass("checked");
			$(".leave-park li").removeClass("selected");
			
			$(this).addClass("checked");
			$(".leave-input").val($(this).html());
			
		});
		
		
	</script>

</body>

</html>
