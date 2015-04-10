<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: blue;color: white;margin: 2px;border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Add Project Type</h4>
			</div>
			
				<form method="post" data-ng-submit="saveProjectType()" role="form" class="form-horizontal">
				<div class="modal-body">
					<div class="form-group" style="display: -webkit-inline-box;width: 100%;margin-bottom: 15px;">
						<label for="org-name" style="width: 40%;">Project Type</label> <input type="text"
							class="form-control" id="pro-type" ng-model="pro.projectTypes"
							placeholder="Enter Project Type." style="height:33px;" required>
					</div>
					<div class="form-group" style="display: -webkit-inline-box;width: 100%;margin-bottom: 15px;">
						<label for="org-type" style="width: 40%;">Project Description</label>
							
							<textarea  class="form-control" id="pro-des" ng-model="pro.projectDescription" rows="3"
						 placeholder="Enter Project Description." required></textarea>
							
					</div>
					<!-- <div class="form-group" style="display: -webkit-inline-box;width: 100%;margin-bottom: 15px;">
						<label for="org-type" style="width: 40%;">Start Date</label>
							
							 <input type="date"  ng-model="pro.startDate"/> 
							<textarea  class="form-control" id="pro-des" ng-model="pro.projectDescription" rows="3"
						 placeholder="Enter Project Description." required></textarea>
							
					</div> -->
					 
					<div class="form-group" style="display: -webkit-inline-box;width: 100%; margin-bottom: 15px;" ng-if="overWrite == 1">
						<label for="org-profile" style="width: 40%;color:red;">Organization Name already exists</label>
					</div>
					</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="submit" class="btn btn-primary">Add Project Type</button>
			</div>
				
				</form>
			
		</div>
	</div>
</div>