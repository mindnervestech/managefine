<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<h4>
	<b style="width: 1051px; margin-left: 20px;"><i>Leaves Settings</i></b>
</h4>

	<div id="Leaves">
		<form:form class="well" method="POST"
			action="${pageContext.request.contextPath}/<%=com.mnt.time.controller.routes.Leaves.saveLeaves.url%>"
			id="form">
			<c:set var="count" value="0" scope="page" />
			<table class="table table-striped">
			<c:forEach var="map" items="${leave2RoleMap}">
				<tr>
					<td><label style="margin-top: 8%;"><strong>${map.key}</strong></label></td>
    				<c:forEach var="cell" items="${map.value}">
    					<td>
    						<label><small>${cell.leaveType}</small></label>
    						<input class="input-small"  type="hidden" name="roleLeaves[${count}].id" value="${cell.id}">
    						<input class="input-mini" type="number" name="roleLeaves[${count}].total_leave" value="${cell.totalLeave}">
    				 		<c:set var="count" value="${count + 1}" scope="page"/>
    					 </td>  
    				</c:forEach>
    			</tr>
			</c:forEach>
			</table>
			<div class="actions" style="margin-top: 1%;">
				<input type="button" class="btn btn-info" id="submitButton"
					value="Submit" style="margin: 1% 2%; width: 8%;">
			</div>
		</form:form>
	</div>


  <script>
	$(document).ready(function() {

		$("#submitButton").click(function() {
			$.ajax({
				type : "POST",
				data : $("#form").serialize(),
				url : "${pageContext.request.contextPath}/saveLeaveValue",
				success : function(data) {
					$.pnotify({
						history : false,
						text : data
					});
				}
			});
		});
	});
</script> 
