<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: #0000FF;color: white;margin: 2px; border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Role Description</h4>
			</div>
			
				<form method="post" data-ng-submit="editRoleChild()" role="form" class="form-horizontal">
				 <div class="modal-body">
					<div class="form-group" style="display: -webkit-inline-box;width: 100%;margin-bottom: 15px;">
						<label for="org-name" style="width: 40%;">Role Name</label> <input type="text"
							class="form-control" id="org-name" ng-model="org.roleName"
							placeholder="Enter Role name." style="height:33px;" required>
					</div>

					
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;">
						<label for="org-type" style="width: 40%;">Role Description</label> <!-- <input
							type="text" class="form -control" id="org-type" ng-model="org.roleDescription"
							placeholder="Enter Role Description." style="height:33px;" value="org.roleDescription" required> -->
							
							<textarea  class="form-control" id="org-type" ng-model="org.roleDescription" rows="3"
						 placeholder="Enter Role Description." value="org.roleDescription" required></textarea>	
							
					</div>
					
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;" ng-if="overWrite == 1">
						<label for="org-profile" style="width: 40%;color:red;">Role Name already exists</label>
					</div>
					
					</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="submit" class="btn btn-primary">Edit Role</button>
			</div>
					
				</form>
			
		</div>
	</div>
</div>