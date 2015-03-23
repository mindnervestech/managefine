<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />

<h4>
	<b style="width: 1051px; margin-left: 20px;"><i>Define Leaves</i></b>
</h4>
<script src='<c:url value="resources/customScripts/defineLeave.js"/>' type="text/javascript"></script>
<form:form method="POST" commandName="leavexForm"
	action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Leaves.saveLeaves.url%>"
	id="form">
	<fieldset>
		<div id="leavex">
			<c:forEach var="leaves" items="${leaveLevels}" varStatus='loopIndex'>
				<div class=" twipsies well leaveLevel" style="width: 94%;margin-left: 1%;">
					<div class="clearfix" style="margin-right: 4%; float: left;">
						<label for="leaveLevels_${loopIndex.index}__leave">Leaves
							Type</label> <input type="text"
							name="leaveLevels[${loopIndex.index}].leave_type"
							value="${leaves.getLeave_type()}"
							id="leaveLevels_${loopIndex.index}__leave_type"
							class="input-small" placeholder="Leave Type">
					</div>
					<div class="clearfix" style="width: 52%;">
						<label for="leaveLevels_${loopIndex.index}__leave">Carried
							Forward</label> <select
							id="leaveLevels_${loopIndex.index}__carry_forward"
							name="leaveLevels[${loopIndex.index}].carry_forward"
							class="input-small">
							<option class="largeInput largeInputFirst required" value="">-select-</option>
							<option class="largeInput largeInputFirst required" value="YES">YES</option>
							<option class="largeInput largeInputFirst required" value="NO">NO</option>
							<option class="largeInput largeInputFirst required" selected
								value="${leaves.getCarry_forward()}">${leaves.getCarry_forward()}</option>
						</select> <a class="removeLeave btn danger pull-right btnColor">Remove</a>
					</div>
					<div class="clearfix" style="margin-right: 4%; float: left;">
						<input type="hidden" name="leaveLevels[${loopIndex.index}].id"
							value="${leaves.getId()}"
							id="leaveLevels_${loopIndex.index}__leave_type"
							class="input-small">
					</div>

				</div>
			</c:forEach>



			<div class="twipsies well leaveLevel_template hidden">
				<div class="clearfix" style="margin-right: 4%; float: left;">
					<label for="leaveLevels_x__leave">Leaves Type</label> <input
						type="text" name="leaveLevels[x].leave_type"
						id="leaveLevels_x__leave_type" class="input-small"
						placeholder="Leave Type">
				</div>
				<div style="width: 52%;">
					<label for="leaveLevels_x__leave">Carried Forward</label> <select
						id="leaveLevels_x__carry_forward"
						name="leaveLevels[x].carry_forward" class="input-small">
						<option class="largeInput largeInputFirst required" value="">-select-</option>
						<option class="largeInput largeInputFirst required" value="YES">YES</option>
						<option class="largeInput largeInputFirst required" value="NO">NO</option>
					</select> <a class="removeLeave btn danger pull-right btnColor">Remove</a>
				</div>
				<div class="clearfix" style="margin-right: 4%; float: left;">
					<input type="hidden" name="leaveLevels[x].id" value=""
						id="leaveLevels_x__leave_type" class="input-small">
				</div>

			</div>
		</div>


	</fieldset>
	<div class=" actions buttonPosition" style="margin-left: 2%;">
		<a class="addMore btn btn-warning">Add More</a> <input type="button"
			class="btn btn-warning" id="submitButton" value="Submit"
			style="width: 9%;">
	</div>

</form:form>

<script>
	$(document).ready(function() {

		$("#submitButton").click(function() {
			$('.leaveLevel_template').remove()
			$.ajax({
				type : "POST",
				data : $("#form").serialize(),
				url : "${pageContext.request.contextPath}/saveLeaves",
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

<style>
/* .well{
 	background-color: #ffffff; 
 	border: 0px solid #e3e3e3;
} */
.btnColor{
	color: gray;
}
.input-small {
	width: 159px !important;
}
</style>
