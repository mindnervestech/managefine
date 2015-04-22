<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<link rel="stylesheet" media="screen" href='<c:url value="resources/stylesheets/customRoleX.css"/>'>
<form ng-app="dept-app" ng-controller="TimeController">
<fieldset>
	<div id="rolex">
		<div class="twipsies well roleLevel" ng-repeat="department in departments">
			<div class="clearfix">
				<label>Department</label>
				<div class="input">
					<input type="text" ng-model="department.name" /> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>
			<a class="removeRole btn danger pull-right btnColor" ng-click="romove($index,department.id)">Remove</a>
		</div>
	</div>
</fieldset>
<div class="actions buttonPosition" style="margin-left: 2%;">
	<a class="addMore btn btn-warning" ng-click="addMoreDepartment()">Add More</a> <input type="button"
		class="btn btn-warning" id="submitButton" value="Submit" ng-click="saveDepartments()"
		style="width: 9%;">
</div>
</form>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/deparmentController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/deparmentController/controller.js"/>'></script>
<style>
.btnColor{
	color: gray;
}
.buttonPosition {
	margin-left: 18%;
	margin-top: 1%;
}
</style>