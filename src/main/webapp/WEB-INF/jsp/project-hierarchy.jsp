<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'>

<div ng-app="ProjectHierarchyApp" ng-controller="ProjectHierarchyController">
	<div class="form-group">
						<label class="col-md-12" style="margin-left: 3px;">Project type</label>
						 	
							<!-- <select class="col-md-3" data-ng-model="projectType.id" ng-change="viewHierarchy(projectType.id)" data-ng-options="projectT.id as projectT.projectTypes for projectT in projectType" placeholder="Select Project type." style="margin-left: 15px;" required>
								<option>Select Project Type..</option>
					        </select> -->
					        <select class="col-md-3" data-ng-model="projectType.id" ng-change="viewHierarchy(projectType.id)" placeholder="Select Project type." style="margin-left: 15px;" required>
					          <option value="">Select Project Type..</option>
								 <option ng-repeat="projectT in projectType" value="{{projectT.id}}">{{projectT.projectTypes}}</option>
							</select>
							
							<!-- <button type="button" class="btn btn-default" ng-click="viewHierarchy(projectType.id)">View</button> -->
				       <button type="submit" class="btn btn-primary" style="margin-left: 20px;" ng-click="addHierarchyProjectType()">Add Project Type</button>
					</div>
					<div class="form-group">
    				
    			<div id="centerpanel"  style="overflow: hidden; padding: 0px; margin: 0px; border: 0px;">
					<div bp-org-diagram 
						data-options="myOptions" 
						data-on-highlight-changed="onMyHighlightChanged()"  
						data-on-cursor-changed="onMyCursorChanged()" 
						style="width: 100%; height: 600px; border-style: dotted; border-width: 1px;">
					</div>
    			</div>
    				
    				
    				
    				</div>
</div>

<%-- <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular-datepicker.js"/>'></script> --%>
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



