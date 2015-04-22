<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: blue;color: white;margin: 2px;border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Add Role Description</h4>
			</div>
			
				<form method="post" data-ng-submit="saveRoleChild()" role="form" class="form-horizontal">
				<div class="modal-body">
					<div class="form-group" style="display: -webkit-inline-box;width: 100%;margin-bottom: 15px;">
						<label for="org-name" style="width: 40%;">Role name</label> <input type="text"
							class="form-control" ng-model="org.roleName"
							placeholder="Enter Role name." style="height:33px;" required>
					</div>
					<div class="form-group" style="display: -webkit-inline-box;width: 100%;margin-bottom: 15px;">
						<label for="org-type" style="width: 40%;">Role Description</label> 
							
						<textarea  class="form-control" ng-model="org.roleDescription" rows="3"
						 placeholder="Enter Role Description." required></textarea>	
							
							
					</div>
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;" ng-if="overWrite == 1">
						<label for="org-profile" style="width: 40%;color:red;">Role Name already exists</label>
					</div>
					
					</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="submit" class="btn btn-primary">Add Role</button>
			</div>
					
				</form>
			
		</div>
	</div>
</div>