<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'>

<div ng-app="ProjectHierarchyApp" ng-controller="ProjectHierarchyController">
	<div class="form-group">
	<div class="col-md-12">
		<div class="col-md-6">
				<input type="file" ng-file-select="selectPartFile($files)" required>
		</div>
		<!-- <div class="col-md-6">
				<input type="file" ng-file-select="selectPartFile($files)" required>
		</div> -->
	</div>
				    <button type="submit" class="btn btn-primary" style="margin-left: 20px;margin-top: 26px;" ng-click="savePartFile()">Save File</button> 
					</div>
					<div class="form-group">
    				
    				</div>
</div>

<script type="text/javascript" src='<c:url value="/resources/javascripts/primitives/primitives.latest.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/select2/select2.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>
<script src='<c:url value="/resources/javascripts/bootstrap.min.js"/>' type="text/javascript"></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/controller.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/attributController/controller.js"/>'></script>



