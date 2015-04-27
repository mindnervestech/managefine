<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog" style="height: 327px;">
		<div class="modal-content" style="height: 327px;">
			<div class="modal-header" style="background: #005580;color: white;margin: 2px;height: 38px;padding: 2px;border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true" style="color: white;font-size: 25px;">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Add Project Type</h4>
			</div>
			
				<form method="post" data-ng-submit="saveProjectType()" role="form" class="form-horizontal">
				<div class="modal-body">
					<div class="form-group">
						<label for="org-name">Project Type</label> <input type="text"
							class="form-control col-md-6" id="pro-type" ng-model="pro.projectTypes"
							placeholder="Enter Project Type." style="height:33px;" required>
					</div>
					<div class="form-group">
						<label for="org-type" style="margin-top: 56px;">Project Description</label>
							
							<textarea  class="form-control col-md-8" id="pro-des" ng-model="pro.projectDescription" rows="3"
						 placeholder="Enter Project Description." required></textarea>
							
					</div>
					<div class="form-group"  ng-if="overWrite == 1">
						<label for="org-profile" style="width: 40%;color:red;">Project Name already exists</label>
					</div>
					</div>
					<hr>
					<div class="col-md-12">
							<button type="button" class="btn btn-default"
								style="float: right;" ng-click="closeThisDialog()">Close</button>
							<button type="submit" class="btn btn-primary"  style="margin: 0% 2%; float: right;">Add Project Type</button>
						</div>
				
				</form>
			
		</div>
	</div>
</div>