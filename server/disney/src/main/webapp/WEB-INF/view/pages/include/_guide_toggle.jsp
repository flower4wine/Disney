<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty guide.innerPic }">
	<div class="guide-toggle" >
		<div>
			<img alt="" src="<c:out value='${staticFileUrl }${guide.innerPic }' />">
		</div>
	</div>
</c:if>
