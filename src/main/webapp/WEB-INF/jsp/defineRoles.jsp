<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<h4>
	<b style="width: 1051px; margin-left: 20px;"><i>Define Roles</i></b>
</h4>

<link rel="stylesheet" media="screen" href='<c:url value="resources/stylesheets/customRoleX.css"/>'>
<script src='<c:url value="resources/customScripts/roleScript.js"/>' type="text/javascript"></script>
<form:form method="POST" commandName="rolexForm"
	action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Roles.saveRole.url%>" id="form">
	<fieldset>
		<div id="rolex">
			<c:forEach var="role_level" items="${rolexForm.model.roleLevels}"
				varStatus='loopIndex'>
				
				<div class="twipsies well roleLevel">
					<div class="clearfix">
						<label for="roleLevels_${loopIndex.index}__role_level">Role
							Level</label>
						<div class="input">
							<select id="roleLevels_${loopIndex.index}__role_level"
								name='roleLevels[${loopIndex.index}].role_level'
								class="roleDivs">
								<c:forEach var="rl" items="${roleLevels}">
									<c:choose>
										<c:when test="${rl == role_level.role_level.name()}">
											<option selected value="${rl}">${rl}</option>
										</c:when>
										<c:otherwise>
											<option value="${rl}">${rl}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>

							</select> <span class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>
					<div class="clearfix"
						id="roleLevels_${loopIndex.index}__role_name_field">
						<label for="roleLevels_${loopIndex.index}__role_name">Role
							Name</label>
						<div class="input">
							<input type="text" name='roleLevels[${loopIndex.index}].role_name'
								value='${role_level.role_name}'
								id="roleLevels_${loopIndex.index}__role_name"
								class=" roleDivs roleDivsInput"> 
								<span class="help-inline"></span> 
								<span class="help-block"></span>
						</div>
					</div>
					<a class="removeRole btn danger pull-right btnColor">Remove</a>

					<div class="clearfix"
						id="roleLevels_${loopIndex.index}__reporting_to_field">
						<label for="roleLevels_${loopIndex.index}__reporting_to_field"></label>
						<div class="input">
							<input type="hidden"
								name='roleLevels[${loopIndex.index}].reporting_to'
								id="roleLevels_${loopIndex.index}__reporting_to"
								value="${role_level.reporting_to}">

						</div>
					</div>

					<div class="clearfix"
						id="roleLevels_${loopIndex.index}__final_approval_field">
						<label for="roleLevels_${loopIndex.index}__final_approval_field"></label>
						<div class="input">
							<input type="hidden"
								name='roleLevels[${loopIndex.index}].final_approval'
								value="${role_level.final_approval}"
								id="roleLevels_${loopIndex.index}__final_approval">

						</div>
					</div>

					<div class="clearfix"
						id="roleLevels_${loopIndex.index}__permission_field">
						<label for="roleLevels_${loopIndex.index}__permission_field"></label>
						<div class="input">
							<input type="hidden"
								name='roleLevels[${loopIndex.index}].permissions'
								id="roleLevels_${loopIndex.index}__role_name class=" roleDivsroleDivsInput">

							<span class="help-inline"></span> <span class="help-block"></span>

						</div>
					</div>
					<div class="clearfix" id="roleLevels_${loopIndex.index}__id_field">
						<label for="roleLevels_${loopIndex.index}__id_field"></label>
						<div class="input">
							<input type="hidden" name='roleLevels[${loopIndex.index}].id'
								value="${role_level.id}"
								id="roleLevels_${loopIndex.index}__role_name class=" roleDivsroleDivsInput">

							<span class="help-inline"></span> <span class="help-block"></span>

						</div>
					</div>
				</div>

			</c:forEach>
			<div class="twipsies well roleLevel_template">
				<div class="clearfix  " id="roleLevels_x__role_level_field">
					<label for="roleLevels_x__role_level">Role Level</label>
					<div class="input">
						<select  name="roleLevels[x].role_level" id="roleLevels_x__role_level"
										class="roleDivs">
												<c:forEach var="rl" items="${roleLevels}">
								<option value="${rl}">${rl}</option>
							</c:forEach>
						</select>
						<span class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>

				<div class="clearfix  " id="roleLevels_x__role_name_field">
					<label for="roleLevels_x__role_name">Role Name</label>
					<div class="input">

						<input type="text" id="roleLevels_x__role_name"
							name="roleLevels[x].role_name" value="" placeholder="Role Name"
							class="roleDivs roleDivsInput"> <span class="help-inline"></span>
						<span class="help-block"></span>
					</div>
				</div>

				<a class="removeRole btn danger pull-right btnColor">Remove</a>
				<div class="clearfix  " id="roleLevels_x__reporting_to_field">
					<label for="roleLevels_x__reporting_to"></label>
					<div class="input">

						<select id="roleLevels_x__reporting_to"
							name="roleLevels[x].reporting_to" class="roleDivs hidden">
							<c:forEach var="rl" items="${levels}">
								<option value="${rl}">${rl}</option>
							</c:forEach>

						</select> <span class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>
				<div class="clearfix  " id="roleLevels_x__final_approval_field">
					<label for="roleLevels_x__final_approval"></label>
					<div class="input">

						<select id="roleLevels_x__final_approval"
							name="roleLevels[x].final_approval" class="roleDivs hidden">

							<c:forEach var="rl" items="${levels}">
								<option value="${rl}">${rl}</option>
							</c:forEach>
						</select> <span class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>

				<div class="clearfix  " id="roleLevels_x__permissions_field">
					<label for="roleLevels_x__permissions"></label>
					<div class="input">

						<input type="hidden" id="roleLevels_x__permissions"
							name="roleLevels[x].permissions" value=""> <span
							class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>

				<div class="clearfix  " id="roleLevels_x__id_field">
					<label for="roleLevels_x__id"></label>
					<div class="input">
						<input type="hidden" id="roleLevels_x__id" name="roleLevels[x].id"
							value=""> <span class="help-inline"></span> <span
							class="help-block"></span>
					</div>
				</div>
			</div> 
		</div>
	</fieldset>
	<div class="actions buttonPosition">
		<a class="addMore btn btn-warning">Add More</a> <input type="button"
			class="btn btn-warning" id="submitButton" value="Submit"style="width: 9%;">
	</div>
</form:form>


<script>
	$(document).ready(function() {

		$("#submitButton").click(function() {
			$('.roleLevel_template').remove()
			$.ajax({
				type : "POST",
				data : $("#form").serialize(),
				url : "${pageContext.request.contextPath}/saveRole",
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
.btnColor{
	color: gray;
}
.buttonPosition {
	margin-left: 18%;
	margin-top: 1%;
}
</style>