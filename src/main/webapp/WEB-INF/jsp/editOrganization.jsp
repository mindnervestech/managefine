<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: blue;color: white;margin: 2px; border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Organization</h4>
			</div>
			
				<form method="post" data-ng-submit="editOrgChild()" role="form" class="form-horizontal">
				 <div class="modal-body">
					<div class="form-group" style="display: -webkit-inline-box;width: 100%;margin-bottom: 15px;">
						<label for="org-name" style="width: 40%;">Organization name</label> <input type="text"
							class="form-control" id="org-name" ng-model="org.organizationName"
							placeholder="Enter organization name." style="height:33px;" required>
					</div>
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;">
						<label for="org-type" style="width: 40%;">Organization type</label> <input type="text"
							class="form-control" id="org-type" ng-model="org.organizationType"
							placeholder="Enter organization type." style="height:33px;" required>
					</div>
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;">
						<label for="org-type" style="width: 40%;">Organization Address</label> <!-- <input
							type="text" class="form -control" id="org-type" ng-model="org.organizationLocation"
							placeholder="Enter organization type." style="height:33px;" value="org.organizationLocation" required> -->
							
							<textarea  class="form-control" id="org-type" ng-model="org.organizationLocation" rows="3"
						 placeholder="Enter organization type." value="org.organizationLocation" required></textarea>
							
					</div>
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;">
						<label for="org-profile" style="width: 40%;">Organization profile picture</label> <input
							type="file" ng-file-select="selectFile($files)" id="org-profile">
					</div>
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;" ng-if="overWrite == 1">
						<label for="org-profile" style="width: 40%;color:red;">Organization Name already exists</label>
					</div>
					
					</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="submit" class="btn btn-primary">Edit Organization</button>
			</div>
				</form>
			
		</div>
	</div>
</div>