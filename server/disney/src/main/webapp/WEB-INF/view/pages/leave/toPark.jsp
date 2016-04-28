<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../include/_common_header.jsp"%>

<script type="text/javascript" src="<c:url value= '/resources/script/jquery.touchSwipe.min.js'/>"></script>

</head>

<body>
	
	<%@ include file="../include/_guide_tip.jsp" %>
			
	<%@ include file="../include/_guide_map.jsp" %>

	<script type="text/javascript">
	window.onload = function() {
		resetGuideMap();
		
		$("img.pre-view").on("click", function(){
			wxPreViewImg($(this));
		});
	}
	</script>

</body>

</html>
