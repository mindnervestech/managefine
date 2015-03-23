<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="menuContext.jsp" />

<link rel="stylesheet" media="screen" href='<c:url value="resources/stylesheets/customRoleX.css"/>'>
<script src='<c:url value="resources/customScripts/roleScript.js"/>' type="text/javascript"></script>

<fieldset>
	<div id="rolex">
		<form:form method="POST" commandName="rolexForm"
			action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Roles.saveRole.url%>"
			id="form">
			<c:if test="${rolexForm==null}">
				<h4>List defined Role First</h4>
			</c:if>
			<c:if test="${rolexForm!=null}">
				<h4>
					<b style="width: 1051px; margin-left: 20px;"><i>Define
							Roles</i></b>
				</h4>
				<c:forEach var="role_level" items="${rolexForm.model.roleLevels}"
					varStatus='loopIndex'>
					<div class="twipsies well roleLevel">
						<div class="clearfix"
							id="roleLevels_${loopIndex.index}__role_name_field">
							<label for="roleLevels_${loopIndex.index}__role_name">Role
								Name</label>
							<div class="input">

								<input type="text"
									name="roleLevels[${loopIndex.index}].role_name"
									value="${role_level.role_name}"
									id="roleLevels_${loopIndex.index}__role_name"
									class=" roleDivs roleDivsInput readonlycls" readonly="readonly">
								<span class="help-inline"></span> <span class="help-block"></span>

							</div>
						</div>
						<div class="clearfix"
							id="roleLevels_${loopIndex.index}__reporting_to_field">
							<label for="roleLevels_${loopIndex.index}__reporting_to">Reporting
								To </label>
							<div class="input">
								<select id="roleLevels_${loopIndex.index}__reporting_to"
									name='roleLevels[${loopIndex.index}].reporting_to'
									class="roleDivs">
									
									<c:forEach var="rl" items="${roleLevels}">
										<c:choose>
											<c:when test="${rl == role_level.reporting_to}">
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
							id="roleLevels_${loopIndex.index}__final_approval_field">
							<label for="roleLevels_${loopIndex.index}__final_approval">Final
								Approval To </label>
							<div class="input">

								<select id="roleLevels_${loopIndex.index}__final_approval"
									name='roleLevels[${loopIndex.index}].final_approval'
									class="roleDivs">
									<c:forEach var="rl" items="${roleLevels}">

										<c:choose>
											<c:when test="${rl == role_level.final_approval}">
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
						<div class="clearfix">
							<label for="roleLevels_${loopIndex.index}__role_level"
								class="roleDivs hidden">Role Level</label>
							<div class="input">
								<select id="roleLevels_${loopIndex.index}__role_level"
									name='roleLevels[${loopIndex.index}].role_level'
									class="roleDivs hidden">
									<c:forEach var="rl" items="${levels}">
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
							id="roleLevels_${loopIndex.index}__permission_field">
							<div class="input">
								<input type="hidden"
									name='roleLevels[${loopIndex.index}].permissions'
									id="roleLevels_${loopIndex.index}__role_name class="
									roleDivsroleDivsInputhidden"> <span
									class="help-inline"></span> <span class="help-block"></span>

							</div>
						</div>
						<div class="clearfix" id="roleLevels_${loopIndex.index}__id_field">
							<label for="roleLevels_${loopIndex.index}__id_field"></label>
							<div class="input">
								<input type="hidden" name='roleLevels[${loopIndex.index}].id'
									value="${rolexForm.model.roleLevels[loopIndex.index].id}"
									id="roleLevels_${loopIndex.index}__role_name class="
									roleDivsroleDivsInputhidden"> <span
									class="help-inline"></span> <span class="help-block"></span>

							</div>

						</div>
					</div>
				</c:forEach>

				<div class="actions" style="margin-top: 1%;">
					<input type="button" class="btn btn-warning" id="submitButton"
						value="Submit" style="margin: 1% 2%; width: 8%;">
				</div>
			</c:if>
		</form:form>
	</div>
</fieldset>

<script>
	$(document).ready(function() {

		$("#submitButton").click(function() {
			if (customValidation()) {
				$('.roleLevel_template').remove()
				$.ajax({
					type : "POST",
					data : $("#form").serialize(),
					url : "${pageContext.request.contextPath}/saveOrg",
					success : function(data) {
						$.pnotify({
							history : false,
							text : data
						});
					}
				});
			} else {
				bootbox.alert("Please define roles first.");
			}
		});
	});

	function customValidation() {
		if ($('div.roleLevel').length == 0) {
			return false;
		} else if ($('div.roleLevel').length == 1) {
			if ($(".readonlycls").val() == "") {
				return false;
			}
		}
		return true;
	}
</script>

