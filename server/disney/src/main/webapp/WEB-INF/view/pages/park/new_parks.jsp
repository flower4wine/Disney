<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../include/_common_header.jsp"%>



</head>

<body>
	<div class=" ">
	
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
			<img src="<c:url value= '/resources/images/all_parks.jpg' />" class="park-map-all-img" />
		</div>
			
	</div>
	
	<%@ include file="../include/_bottom_footer.jsp" %>

</body>

</html>
