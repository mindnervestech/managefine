<fieldset ng-controller="attributesController" style="padding: .35em .625em .75em;margin: 0 2px;border: 1px solid lightgray;height: 271px;overflow: auto;margin-bottom: 7px;">
  <legend style="width: 153px;border-style: none;margin-bottom: 1px;font-size: 16px;">Add Attributes<button type="button" class="btn btn-primary" style="margin-left: 8px;width: 41px;" ng-click="newprojectValue($event)"> + </button></legend>
				<form method="post" data-ng-submit="saveRoleChild()" role="form" class="form-horizontal">
				<div class="modal-body" style="padding: 0px;">
				<!-- <div class="form-group">
				<button type="button" class="btn btn-primary" style="margin-left: 25px;" ng-click="newprojectValue($event)">Add </button>
				</div> -->
				<div class="form-group" ng-repeat="find in projectsearch.projectValue">
				  <div class="col-md-3">
				  <div class="col-md-12">
						
				         <label>Name</label>
				         </div>
				         <div class="col-md-12">
				         
					     <input type="text" class="col-md-12" name="name{{$index}}" data-ng-model="find.name">
					     </div>
				  </div>
				  <div class="col-md-3">
				        <label class="col-md-12">Type</label>
					        <select class="col-md-12" name="type{{$index}}" data-ng-model="find.type" ng-change="selectValue(find.type)" placeholder="Select Project type.">
								<option value="String" selected>String</option>
								<option value="Integer">Integer</option>
								<option value="Date">Date</option>
								<option value="Radio">Radio</option>
								<option value="Checkbox">Checkbox</option>
								<option value="Dropdown">Dropdown</option>
					        </select>	
				   </div>
				   <div>
				   <div class="col-md-5" ng-if="find.type == 'Radio' || find.type == 'Checkbox' || find.type == 'Dropdown'">  
				        <label class="col-md-12">Value</label>
					    <textarea  class="col-md-12" name="value{{$index}}" ng-model="find.value" style="font-size: 10px;" rows="1"
						 placeholder="Enter Value."></textarea>
						 <label class="col-md-10" style="font-size: 8px;">Enter Value in Separat Line</label>
				   </div>
				   </div>
				  <div class="col-md-1">
				  <span ng-show="projectsearch.projectValue.length > 1">
				  	<button class="btn btn-default" style="margin-top: 25px;" ng-click="projectsearch.projectValue.splice($index, 1)"><i class="icon-remove"></i></button>
				  	</span>
				  </div>
					 
				 <div class="col-md-12">
					           			
				</div> 
				
				<br>
					
					</div> 
					 <!-- <button class="btn btn-default" ng-click="newprojectValue($event)">+</button> -->
					</div>
					<!-- <button type="button" class="btn btn-primary" ng-click="newprojectValue($event)">Add </button> -->
				</form>
		</fieldset>	
	

	<style>
		select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input {
		    height: 31px;
		}
	
	</style>