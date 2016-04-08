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
				<input class="leave-input">
			</div>
			
			<div class="leave-btn-border">
				<div class="bg-color_red leave-btn" >
					确认
				</div>
			</div>
			
			<div class="leave-select-park">
				
				<div class="leave-select-tip">或选择停车场</div>
				
				<ul class="leave-park">
					<li>
						<div class="leave-park-first">P1</div>
					</li>
					<li><div class="bor-left-none">P2</div></li>
					<li><div class="bor-left-none">P3</div></li>
					<li><div class="bor-left-none leave-park-last" >P4</div></li>
				</ul>
			</div>
		
		</div>
	</div>

	<script type="text/javascript">
		$(".leave-park li").on("click",function(){
			$(".leave-top-message").hide();
			$(".leave-input").val("");
			$(".leave-park li").removeClass("selected");
			$(this).addClass("selected");
		});
		
		$(".leave-btn-border").on("click",function(){
			var car = $(".leave-input").val();
			
			if(car!=''){
				
				$(".leave-top-message").show();
				
			}else{
				var selected = $(".leave-park li.selected");
				
				if(selected.length>0){
					var park = $(selected).find("div").html();
					alert(park);
				}
				
			}
		});
	</script>

</body>

</html>
