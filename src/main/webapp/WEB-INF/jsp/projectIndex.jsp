<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <jsp:include page="menuContext.jsp"></jsp:include>
    <h4><b style=" width: 1051px; margin-left: 20px;"><i>Manage Projects</i></b></h4>
	<c:set var="_searchContext" value="${context}" scope="request" />
	<c:set var="_parentSearchCtx" value="${null}" scope="request" />
	<c:set var="mode" value="add" scope="request" />
	<jsp:include page="searchContext.jsp"></jsp:include>


<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<div ng-app="ProjectHierarchyApp" ng-controller="createProjectController">

<div class="form-group">
 <div class="col-md-12">
 <div id="dialog" title="Project" style="background: white;height: 311px;">
 <div class="form-group">
 	<form method="post" action="saveprojectTypeandName" >   <!--modelAttribute="pVm" action="/saveprojectTypeandName" -->
    	<div class="modal-body" >
					<div class="form-group">
						<label for="org-name">Project Type</label>
						
							<select class="col-md-6" name="projectTypeId" data-ng-model="projectinfo.projectTypeId" ng-change="onProjectTypeChange(projectType.projectTypeId)"  placeholder="Select Project type."  required>   <!-- data-ng-options="projectT.id as projectT.projectTypes for projectT in projectType" -->
								<option ng-repeat="projectT in projectType" value="{{projectT.id}}">{{projectT.projectTypes}}</option>
					        </select>
						
					</div>
					<div class="form-group">
					 <div class="col-md-12" style="padding-right: 0px;padding-left: 0px;">
					 <div class="col-md-6" style="padding-right: 0px;padding-left: 0px;">
						<label for="org-type" class="col-md-11">Project Name</label>
							
							 <input type="text" class="col-md-11" name="projectName"
							class="form-control" id="pro-type" ng-model="projectinfo.projectName"
							placeholder="Enter Project Name."  required>
							</div>
							
							<div class="col-md-6" style="padding-right: 0px;padding-left: 0px;">
						<label for="org-type" class="col-md-11">Project Description</label>
							
							<textarea  class="col-md-11" id="pro-des" name="projectDescription" ng-model="projectinfo.projectDescriptions" rows="1"
						 placeholder="Enter Project Description."></textarea>
						 							
						</div>
					
							
							
						
						
						</div>	
					</div>
					
					<div class="form-group">
						<label class="col-md-8">Client</label>
						
							<select class="col-md-6" name="client" data-ng-model="projectinfo.client" ng-change="onProjectTypeChange(projectinfo.client)"  placeholder="Select Client."  required>     <!-- data-ng-options="clientinfo.id as clientinfo.clientName for clientinfo in findCliect" -->
								<option ng-repeat="clientinfo in findCliect" value="{{clientinfo.id}}">{{clientinfo.clientName}}</option>
					        </select>
						
					</div>
					
					</div>
					<br>
					<br>
					<br>
					<div  class="col-md-11">
					<div  class="col-md-8">
					</div>
				<button type="submit" class="btn btn-primary col-md-3" >Add Project</button>   <!-- ng-click="addProjectName(projectinfo)" -->
				</div>
				</form>
				</div>	
    </div>
    </div>
    </div>
    </div>
    <!-- ui-widget ui-widget-content ui-corner-all ui-front ui-draggable ui-resizable -->
    <style>
   
    
    </style>
    
<script>

$(document).ready(function(){
	$("#Projectinstanceadd_addButton").unbind();
	$("#Projectinstanceadd_addButton").off();
	$('#dialog').css('display','none');
	$("#Projectinstanceadd_addButton").click(function() {
		console.log("please open new po -up now");
		
		
		 $( "#dialog" ).dialog({"width":656,"top": 130,"height":400,open: function(){
			    $('.ui-dialog-titlebar-close').addClass('ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only');
			    $('.ui-dialog-titlebar-close').append('<span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span class="ui-button-text">close</span>');
			}});
				
		
	});
	
	
	
});
</script>
   
    
   
 <script type="text/javascript" src='<c:url value="/resources/javascripts/primitives/primitives.latest.js"/>'></script>
 <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular-datepicker.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
 
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/createProjectController/controller.js"/>'></script> 