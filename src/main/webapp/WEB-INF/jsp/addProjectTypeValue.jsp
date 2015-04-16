<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">  <!-- ng-app="ProjectHierarchyApp" ng-controller="ProjectHierarchyController" -->
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: #005580;color: white;margin: 2px;padding: 1px;border-bottom: 4px gray;margin-bottom: -15px;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
								<h4 ng-if="level == '0'" class="modal-title" id="myModalLabel" style="margin-left: 18px;">Project Type</h4>
				<h4 ng-if="level == '1'" class="modal-title" id="myModalLabel" style="margin-left: 18px;">Project Stage</h4>
				<h4 ng-if="level == '2'" class="modal-title" id="myModalLabel" style="margin-left: 18px;">Stage-Task</h4>
				<h4 ng-if="level == '3'" class="modal-title" id="myModalLabel" style="margin-left: 18px;">Sub Task</h4>
				<h4 ng-if="level == '4'" class="modal-title" id="myModalLabel" style="margin-left: 18px;">Describe Node Type</h4>
			</div>
			<div style="padding-left: 5px;padding-right: 5px;">
			<!-- <div class="col-md-11"> -->
				<form method="post" role="form" class="form-horizontal">
				 <div class="form-group">
				 <div class="col-md-4">
						<label class="col-md-12" style="margin-top: 16px;">Type</label>
						  <input type="text" name="projectName" data-ng-model="pro.projectTypes" class="col-md-11">
						  <label class="col-md-12" style="color:red" ng-if="projectT == 1">type is required</label>
						  
					</div>
					 <div class="col-md-2" style="margin-top: 42px;">
					<input type="color" ng-model="pro.projectColor" style="width: 64%;height: 26px;" class="ng-pristine ng-valid">
					</div>
					 <div class="col-md-6">
						<label class="col-md-11" style="margin-top: 16px;" for="pro-name">Description</label>
						  <textarea  class="col-md-11" id="pro-des" ng-model="pro.projectDescription" rows="1"
						 placeholder="Enter Project Description."></textarea>
						 <label class="col-md-12" style="color:red" ng-if="projectD == 1">description is required</label>
						 
					</div>
					</div>
				
					<div class="form-group">
						<%--   <jsp:include page="attribut.jsp" /> --%>
						<!-- <div class="col-md-12" ng-include="'attribut.jsp'"> -->
						<div class="col-md-12">
						<%@ include file="attribut.jsp" %>
						</div>
						
					</div>
				
			<div class="modal-footer" style="background-color:white">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="button" class="btn btn-primary" ng-click="saveProjectInfo()">Add</button>
			</div>
				
				</form>
				<!-- </div> -->
			</div>
		</div>
	</div>
</div>



<style>
 .modal-dialog{
	width: 747px; 
 }


</style>
		
     				      <!--  <div ng-include="addRoleOrganization.jsp"></div> -->
     				        <!-- <div data-ng-controller="attributesController" class="" data-ng-include data-src="'attribut.jsp'"> -->
     				<!--  <div ng-controller="attributesController"> -->
     				
				<!-- 	</div> -->
		<!-- 		 <div class="form-group" ng-repeat="find in projectsearch.projectValue">
			
					<input type="text" name="Name" data-ng-model="find.name" class="form-control">
				
					<input type="text" name="locationAddr" data-ng-model="find.type" class="form-control">
					<select class="form-control" data-ng-model="find.type" placeholder="Select Project type." required>
								<option value="String">String</option>
								<option value="Integer">Integer</option>
					        </select>	
								
				<div>
				   <button class="btn btn-default" ng-click="newprojectValue($event)">+</button>
           			<span ng-show="projectsearch.projectValue.length > 1">
                        <button class="btn btn-default" ng-click="projectsearch.projectValue.splice($index, 1)">-</button>
                        </span>
				</div>
				
				<br>
					
					</div> -->