<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Add Organization</h4>
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group">
						<label for="org-name">Organization name</label> <input type="text"
							class="form-control" id="org-name" ng-model="org.organizationName"
							placeholder="Enter organization name.">
					</div>
					<div class="form-group">
						<label for="org-type">Organization type</label> <input type="text"
							class="form-control" id="org-type" ng-model="org.organizationType"
							placeholder="Enter organization type.">
					</div>
					<div class="form-group">
						<label for="org-type">Organization location</label> <input
							type="text" class="form-control" id="org-type" ng-model="org.organizationLocation"
							placeholder="Enter organization type.">
					</div>
					<div class="form-group">
						<label for="org-profile">Organization profile picture</label> <input
							type="file" ng-file-select="selectFile($files)" id="org-profile">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="button" class="btn btn-primary" ng-click="saveOrgChild()">Add Organization</button>
			</div>
		</div>
	</div>
</div>