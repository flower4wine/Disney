<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="./include/_common_header.jsp"%>
</head>

<body>

	<div class="container js_container">

		<div class="page">
			<div class="weui_msg">
				<div class="weui_icon_area">
					<i class="weui_icon_msg weui_icon_warn"></i>
				</div>
				<div class="weui_text_area">
					<h2 class="weui_msg_title" style="color: #777;">操作失败</h2>
					<p class="weui_msg_desc">${errorMessage }</p>
				</div>
				<div class="weui_opr_area">
					<p class="weui_btn_area">
						<a href="<c:url value = '/' />" class="weui_btn weui_btn_warn">确定</a> 
					</p>
				</div>
			</div>
		</div>
		
	</div>

</body>
</html>
