<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>

</head>

<body>
	<div>
	
		<div class="park-items" >

			 <c:forEach items="${parks }" var="park" >
				<div class="park-item" >
			
					<div class="park-item-info bor-bottom" >
						
						<div class="item">
							<div class="name" >${park.parkName }</div>
							<div class="note"> &nbsp; 剩余车位 &nbsp;</div>
							<div class="num">${park.restSpace }</div>
						</div>
					
					</div>
			
				</div>
			</c:forEach>
		</div>
		
		<div class="park-map">
			<img src="<c:out value='${staticFileUrl }/resources/images/all_parks.jpg' />" class="park-map-all-img" />
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
	
		showInfo();
	</script>

</body>

</html>
