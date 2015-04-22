<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: #0000FF;color: white;margin: 2px;height: 38px;padding: 2px; border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Role Description</h4>
			</div>
			
				<form method="post" data-ng-submit="editRoleChild()" role="form" class="form-horizontal">
				 <div class="modal-body">
					<div class="form-group">
						<label for="org-name" >Role Name</label> <input type="text"
							class="form-control col-md-5" id="org-name" ng-model="org.roleName"
							placeholder="Enter Role name." style="height:33px;" required>
					</div>


					<div class="form-group">
						<label for="org-name" style="margin-top: 55px;">Department</label>
						
						<select class="col-md-5 required" id="projectTypeId" name="department" data-ng-model="org.department" ng-change="onProjectTypeChange(projectType.projectTypeId)"  placeholder="Select Project type."  required>  
							
								<option ng-repeat="departmentFind in findDepartment" value="{{departmentFind.id}}" ng-selected="departmentFind.id == org.department">{{departmentFind.name}}</option>
					        </select>
						
					</div>
					
					<div class="form-group">
						<label for="org-type" style="margin-top: 55px;">Role Description</label> 
							<textarea  class="form-control col-md-5" id="org-type" ng-model="org.roleDescription" rows="3"
						 placeholder="Enter Role Description." value="org.roleDescription" required></textarea>	
							
					</div>
					
					<div class="form-group"  ng-if="overWrite == 1">
						<label for="org-profile" style="width: 40%;color:red;">Role Name already exists</label>
					</div>
					
					</div>
			<div class="modal-footer" style="height: 42px;padding: 7px 15px 15px;">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="submit" class="btn btn-primary">Edit Role</button>
			</div>
					
				</form>
			
		</div>
	</div>
</div>