<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="menuContext.jsp"/> 
<h4><b style=" width: 1051px; margin-left: 20px;"><i>Reports</i></b></h4>
<c:set var="_searchContext" value="${context}" scope="request" />
<c:set var="_parentSearchCtx" value="${null}" scope="request" />
<c:set var="mode" value="add" scope="request" />
<jsp:include page="searchContext.jsp"/> 

