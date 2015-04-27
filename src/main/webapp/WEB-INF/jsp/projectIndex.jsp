<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <jsp:include page="menuContext.jsp"></jsp:include>
    <h4><b style=" width: 1051px; margin-left: 20px;"><i>Manage Projects</i></b></h4>
	<c:set var="_searchContext" value="${context}" scope="request" />
	<c:set var="_parentSearchCtx" value="${null}" scope="request" />
	<c:set var="mode" value="add" scope="request" />
	<jsp:include page="searchContext.jsp"></jsp:include>


<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/select2/select2.css"/>'>
<div ng-app="ProjectHierarchyApp"
	ng-controller="createProjectController">

	<div class="modal fade" id="createProjectModal" role="dialog" style="display:none;width:60%;margin-bottom:10%;height:80%;left:20%;margin-left:0px;"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:100%;height:100%;">
			<div class="modal-content" style="width:100%;height:100%;">
				<div class="modal-header" style="height:5%;background:#005580;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<b><span aria-hidden="true" style="color: white;font-size: 25px;">&times;</span></b>
					</button>
					<h4 class="model-title" id="myModelLabel" style="line-height: 11px;color: white;">Create Project</h4>
				</div>
				<div class="modal-body">
					<form name="myForm" method="post" action="saveprojectTypeandName">
						<div class="form-group">
							<div class="col-md-6" style="padding: 0px;margin-top:15px;">
								<label class="col-md-11">Project Type</label>
								<div class="col-md-11">
									<select style="width: 100%;" class="required" ui-select2
										id="projectTypeId" name="projectTypeId"
										data-ng-model="projectinfo.projectTypeId"
										ng-change="onProjectTypeChange(projectType.projectTypeId)"
										placeholder="Select Project type." required>
										<option value="">-select-</option>
										<option ng-repeat="projectT in projectType"
											value="{{projectT.id}}">{{projectT.projectTypes}}</option>
									</select>
								</div>
							</div>
							<div class="col-md-6" style="padding: 0px;margin-top:15px;">

								<label for="org-type" class="col-md-11">Project Name</label>
								<div class="col-md-11">
									<input type="text" style="width: 95%;" name="projectName"
										class="form-control" id="pro-type"
										ng-model="projectinfo.projectName"
										placeholder="Enter Project Name." required>
								</div>
							</div>

						</div>


						<div class="form-group">
							<div class="col-md-6" style="padding: 0px;margin-top:15px;">
								<label class="col-md-11">Project Manager</label>
								<div class="col-md-11">
									<select style="width: 100%;" name="projectManager" ui-select2
										data-ng-model="projectinfo.projectManager"
										ng-change="onProjectTypeChange(projectinfo.projectManager)"
										placeholder="Select Client." required>
										<option value="">-select-</option>
										<option ng-repeat="userinfo in findUser"
											value="{{userinfo.id}}">{{userinfo.firstName}}</option>
									</select>
								</div>
								</div>
								<div class="col-md-6" style="padding: 0px;margin-top:15px;">

									<label class="col-md-11">Project Description</label>

									<div class="col-md-11">
										<textarea style="width: 95%; resize: none;"
											name="projectDescription"
											ng-model="projectinfo.projectDescriptions" rows="3"
											placeholder="Enter Project Description." required></textarea>
									</div>
								</div>
							</div>


							<div class="form-group">
								<div class="col-md-6" style="padding: 0px;margin-top:15px;">
									<label class="col-md-11">Client</label>
									<div class="col-md-11">
										<select style="width: 100%;" name="client" ui-select2
											data-ng-model="projectinfo.client"
											ng-change="onProjectTypeChange(projectinfo.client)"
											placeholder="Select Client." required>
											<option value="">-select-</option>
											<option ng-repeat="clientinfo in findCliect"
												value="{{clientinfo.id}}">{{clientinfo.clientName}}</option>
										</select>
									</div>
								</div>
								<!-- <div class="col-md-5" style="padding: 0px;">
								<label style="margin-left: 13px;">Project Type</label> 
								<select
									class="col-md-12 required" id="projectTypeId"
									name="projectTypeId" data-ng-model="projectinfo.projectTypeId"
									ng-change="onProjectTypeChange(projectType.projectTypeId)"
									placeholder="Select Project type." required>
									<option value="">-select-</option>
									<option ng-repeat="projectT in projectType"
										value="{{projectT.id}}">{{projectT.projectTypes}}</option>
								</select>
							</div> -->
								<div class="col-md-6" style="padding: 0px;margin-top:15px;">

									<label class="col-md-11">Members</label>
									<div class="col-md-11">
										<select style="width: 100%;" name="member" multiple="multiple"
											data-ng-model="projectinfo.member" ui-select2
											ng-change="onProjectTypeChange(projectinfo.member)"
											placeholder="Select member." required>
											<option ng-repeat="userinfo in findUser"
												value="{{userinfo.id}}">{{userinfo.firstName}}</option>
										</select>

									</div>
								</div>
							</div>
								<div class="form-group">
									<div class="col-md-6" style="padding: 0px;margin-top:15px;">
										<label class="col-md-11">Suppliers</label>
										<div class="col-md-11">
											<select style="width: 100%;" name="supplier" ui-select2
												multiple="multiple" data-ng-model="projectinfo.supplier"
												ng-change="onProjectTypeChange(projectinfo.supplier)"
												placeholder="Select Supplier." required>
												<option ng-repeat="supplierinfo in findSupplier"
													value="{{supplierinfo.id}}">{{supplierinfo.supplierName}}</option>
											</select>
										</div>
									</div>
								<div class="col-md-6" style="padding: 0px;margin-top:40px;">
									<div class="col-md-11"></div>
									<div class="col-md-11">
										<button type="submit" class="btn btn-primary">Add
											Project</button>
									</div>
								</div>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- <div class="form-group">
 <div class="col-md-12">
 
 
 <div id="dialog" title="Project" style="background: white;height: 311px;">
 <div class="form-group">
 	<form name="myForm" method="post" action="saveprojectTypeandName">   modelAttribute="pVm" action="/saveprojectTypeandName"
    	<div class="modal-body" >
					<div class="form-group">
					  <div class="col-md-5" style="padding: 0px;">
						<label style="margin-left: 13px;">Project Type</label>
						
							<select class="col-md-12 required" id="projectTypeId"  name="projectTypeId" data-ng-model="projectinfo.projectTypeId" ng-change="onProjectTypeChange(projectType.projectTypeId)"  placeholder="Select Project type."  required>  
								<option value="">-select-</option>
								<option ng-repeat="projectT in projectType" value="{{projectT.id}}">{{projectT.projectTypes}}</option>
					        </select>
						</div>
						<div class="col-md-5">
							
							<label for="org-type" class="col-md-12" style="padding: 0px;">Project Name</label>
							
							 <input type="text" class="col-md-12" name="projectName"
							class="form-control" id="pro-type" ng-model="projectinfo.projectName"
							placeholder="Enter Project Name."  required>
						
						</div>
						
					</div>
				
					
					<div class="form-group" style="margin-top: 13px;padding: 0px;">
					<div class="col-md-5" style="padding: 0px;margin-top: 10px;">
						<label class="col-md-12">Project Manager</label>
							
							 <select class="col-md-12" name="projectManager"  data-ng-model="projectinfo.projectManager" ng-change="onProjectTypeChange(projectinfo.projectManager)"  placeholder="Select Client."  required>    
								<option value="">-select-</option>
								<option ng-repeat="userinfo in findUser" value="{{userinfo.id}}">{{userinfo.firstName}}</option>
					        </select>
						</div>
						<div class="col-md-5" style="margin-top: 10px;">
							
							<label class="col-md-12" style="padding: 0px;">Project Description</label>
							
							<textarea  class="col-md-11" name="projectDescription" ng-model="projectinfo.projectDescriptions" rows="1"
						 placeholder="Enter Project Description." required></textarea>
						
						</div>
					</div>
					
					
					<div class="form-group" style="padding: 0px;">
					<div class="col-md-5" style="padding: 0px;margin-top: 10px;">
						<label class="col-md-12">Client</label>
						
							<select class="col-md-12" name="client"  data-ng-model="projectinfo.client" ng-change="onProjectTypeChange(projectinfo.client)"  placeholder="Select Client."  required>   
								<option value="">-select-</option>
								<option ng-repeat="clientinfo in findCliect" value="{{clientinfo.id}}">{{clientinfo.clientName}}</option>
					        </select>
						</div>
						<div class="col-md-5" style="padding: 0px;margin-top: 10px;">
							
							<label class="col-md-12">Members</label>
						
							<select class="col-md-12" name="member"  multiple="multiple" data-ng-model="projectinfo.member" ng-change="onProjectTypeChange(projectinfo.member)"  placeholder="Select member."  required>     data-ng-options="clientinfo.id as clientinfo.clientName for clientinfo in findCliect"
								<option ng-repeat="userinfo in findUser" value="{{userinfo.id}}">{{userinfo.firstName}}</option>
					        </select>
						
						</div>
					</div>
					
						<div class="form-group" style="margin-top: 13px;padding: 0px;">
					<div class="col-md-5" style="padding: 0px;margin-top: 10px;">
						<label class="col-md-12">Suppliers</label>
						
							<select class="col-md-12" name="supplier" ui-select2 multiple="multiple" data-ng-model="projectinfo.supplier" ng-change="onProjectTypeChange(projectinfo.supplier)"  placeholder="Select Supplier."  required>     
								<option ng-repeat="supplierinfo in findSupplier" value="{{supplierinfo.id}}">{{supplierinfo.supplierName}}</option>
					        </select>
						</div>
						<div class="col-md-5">
					
							{{projectinfo.supplier}}
													
						</div>
					</div>
									
					</div>
					<br>
					<br>
					<br>
					<div  class="col-md-11">
					<div  class="col-md-8">
					</div>
				<button type="submit" class="btn btn-primary col-md-3" >Add Project</button>   ng-click="addProjectName(projectinfo)"
				</div>
				</form>
				</div>	
    </div>
    </div>
    </div> -->
<!-- ui-widget ui-widget-content ui-corner-all ui-front ui-draggable ui-resizable -->


<script>
	$(document).ready(function() {
		$("#Projectinstanceadd_addButton").unbind();
		$("#Projectinstanceadd_addButton").off();
		$('#dialog').css('display', 'none');
		$("#Projectinstanceadd_addButton").click(function() {
			console.log("please open new po -up now");

			$("#createProjectModal").modal('show');
			/*  $( "#dialog" ).dialog({"width":697,"top": 130,"height":500,open: function(){
				    $('.ui-dialog-titlebar-close').addClass('ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only');
				    $('.ui-dialog-titlebar-close').append('<span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span class="ui-button-text">close</span>');
				}});
			 */

		});

	});
</script>
   
 
   
 <script type="text/javascript" src='<c:url value="/resources/javascripts/primitives/primitives.latest.js"/>'></script>
 <%-- <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular-datepicker.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/select2/select2.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>
 
 
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/createProjectController/controller.js"/>'></script> 