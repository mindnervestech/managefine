<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.mailDiv {
	width: 25%;
	float: left;
	padding: 20px 40px;
	margin-top: 5%;
}

.mailDiv input {
	padding: 5% !important;
	margin-bottom: 10% !important;
}
</style>


<jsp:include page="menuContext.jsp" />
<h3 style="margin-left: 2%;">
	<b><i>Mail Settings</i></b>
</h3>

<form action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Mail.settingSave.url%>"
	id="emailSettings" method="post">

	<div class="mailDiv">

		<label for="hostName">Host Name</label> <input type="text"
			id="hostName" name="hostName" value="${mailForm.model.hostName}" placeholder="Host Name"
			required="true"> <label for="portNumber">Port Number</label>

		<input type="text" id="portNumber" name="portNumber" value="${mailForm.model.portNumber}"
			placeholder="Port Number" required="true">

	</div>

	<div class="mailDiv">
		<label for="userName">UserName</label>
		 <input type="email"
			id="userName" name="userName" value="${mailForm.model.userName}"
			placeholder="User Name" required="true"> 
			<label for="password">Password</label> 
			<input type="password" id="password"
			name="password" value="${mailForm.model.password}" placeholder="Password" required="true">


	</div>


	<div class="mailDiv">
		<label for="ssl">SSL</label> 
		<c:if test="${mailForm.model.sslPort==true}">
			<input type="checkbox" id="ssl"
					name="sslPort" checked="checked" value="true" placeholder="SSL">
		</c:if>
		<c:if test="${mailForm.model.sslPort==false}">
			<input type="checkbox" id="ssl"
					name="sslPort"  value="false" placeholder="SSL">
		</c:if>
		
		<label for="tls">TLS</label> 
		<c:if test="${mailForm.model.tlsPort==true}">
			<input type="checkbox" id="tls"
					name="tlsPort" checked="checked" value="true" placeholder="TLS">
		</c:if>
		<c:if test="${mailForm.model.tlsPort==false}">
			<input type="checkbox" id="tls"
					name="tlsPort"  value="false" placeholder="TLS">
		</c:if>

	</div>
	<div style="margin-right: 27%;">
		<button type="submit" id="updateButton" style="margin-left: 5%;"
			class="btn btn-warning">Submit</button>
		<a href="/time/index"
			style="margin-left: 1.5%;" class="btn btn-warning">Back</a>

	</div>

</form>
