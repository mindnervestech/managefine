<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <jsp:include page="menuContext.jsp"></jsp:include> 
 


<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>

<label style="font-size: large;margin-left: 74px;"><b>${createProject.projectName}</b></label>
<div ng-app="ProjectHierarchyApp" ng-controller="createProjectController" ng-init='viewHierarchy(${createProject.projectid},${createProject.id})'><!--  viewHierarchy -->
	<div class="form-group">
				 <!--  <label class="col-md-12" style="margin-left: 4px;">Choose Project type</label>  -->
						 	
							<!--  <select class="col-md-3" data-ng-model="projectType.id" ng-change="onProjectTypeChange(projectType.id)" data-ng-options="projectT.id as projectT.projectTypes for projectT in projectType" placeholder="Select Project type." style="margin-left: 15px;" required>
								<option>Select Project Type..</option>
					        </select>  -->
							
							
							<!--  <button type="button" class="btn btn-default" ng-click="viewHierarchy(projectType.id)">View</button> --> 
				       <!-- <button type="submit" class="btn btn-primary" onClick="myFunction()">Add</button> -->
					</div>
					<!-- <div class="form-group">
						<label class="col-md-12" style="margin-left: 15px;">Choose Client</label>
						 	
							<select class="col-md-3" data-ng-model="projectType.id" ng-change="onProjectTypeChange(projectType.id)" data-ng-options="projectT.id as projectT.projectTypes for projectT in projectType" placeholder="Select Project type." style="margin-left: 15px;" required>
								<option>Select </option>
					        </select>
					</div> -->
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
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular-datepicker.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/primitives/primitives.latest.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script src='<c:url value="/resources/javascripts/bootstrap.min.js"/>' type="text/javascript"></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/createProjectController/controller.js"/>'></script>


<script>
   

function myFunction() {
	$.ajax({
		type : "POST",
		data : $("#form").serialize(),
		url : "${pageContext.request.contextPath}/saveCreateProjectAttributes",
		success : function(data) {
			$.pnotify({
				history : false,
				text : data
			});
		}
	});
}


</script> 

 
 
 
 
 
 
 
 