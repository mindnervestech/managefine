<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'>

<div ng-app="ProjectHierarchyApp" ng-controller="CountryController">
	<div class="form-group">
	<div class="col-md-12" style="margin-top: 50px;margin-bottom: 30px;">
				<div class="col-md-3">
    			 	<select class="col-md-10" data-ng-model="country" ng-change="selectState(country)">
					        <option value="">Select Country</option>
							<option ng-repeat="country in countryList" value="{{country.id}}">{{country.name}}</option>
					</select>
					
					<a class="col-md-12" ng-click="select = 1">Add Country</a>
				</div>
				<div class="col-md-3">
				 	<select class="col-md-10" data-ng-model="state" ng-change="selectCity(state)">
					        <option value="">Select State</option>
							<option ng-repeat="state in stateList" value="{{state.id}}">{{state.name}}</option>
					</select>
					
					<a class="col-md-12" ng-click="select = 2">Add State</a>
				</div>
				<div class="col-md-3">
    				<select class="col-md-10">
					        <option value="">Select City</option>
							<option ng-repeat="city in cityList" value="{{city.id}}">{{city.name}}</option>
					</select>
					
					<a class="col-md-12" ng-click="select = 3">Add City</a>
				</div>
		<!-- <div class="col-md-6">
				<input type="file" ng-file-select="selectPartFile($files)" required>
		</div> -->
	</div>				   
		<div class="col-md-12" style="text-align: center;margin-bottom: 20px;">
		
				<div class="col-md-10" ng-if="select == 1">
				 <form ng-submit="saveCountry(data)">
					<div class="col-md-12">
						<label>Country:</label>
						<input type="text" value="" ng-model="data.name" required>
					</div>
					<div class="col-md-12">	
						<button type="submit" class="btn btn-primary" style="margin-top: 10px;" >Save </button>
					</div>
					<div class="col-md-12">	
						<label ng-if="message == 1" style="color: green;">SuccesFully Saved</label>
					</div>
					
				 </form>	
				</div>
				
				<div class="col-md-10" ng-if="select == 2" style="text-align: center;">
					<form ng-submit="saveState(data)">
						<div class="col-md-12">
							<label>State:</label>
							<select class="" ng-model="data.country" required>
							        <option value="">Select Country</option>
									<option ng-repeat="country in countryList" value="{{country.id}}">{{country.name}}</option>
							</select>
						</div>
						<div class="col-md-12" style="margin-top: 20px;">	
							<input type="text" value="" ng-model="data.name">
						</div>
						<div class="col-md-12">	
								<button type="submit" class="btn btn-primary" style="margin-top: 10px;" >Save </button>
						</div>
						<div class="col-md-12">	
							<label ng-if="message == 1" style="color: green;">SuccesFully Saved</label>
						</div>
					</form>		
				</div> 
				<div class="col-md-10" ng-if="select == 3" style="text-align: center;">
					<form ng-submit="saveCity(data)">
						<div class="col-md-12" style="text-align: center;">
							<label>City</label>
							<select class="" ng-model="country" ng-change="selectState(country)" required>
							        <option value="">Select Country</option>
									<option ng-repeat="country in countryList" value="{{country.id}}">{{country.name}}</option>
							</select>
						</div>
						<div class="col-md-12" style="margin-top: 20px;">
							<select  ng-model="data.state" required>
						        <option value="">Select State</option>
								<option ng-repeat="state in stateList" value="{{state.id}}">{{state.name}}</option>
							</select>
						</div>	
						<div class="col-md-12" style="margin-top: 20px;">	
							<input type="text" value="" ng-model="data.name">
						</div>
						<div class="col-md-12">	
								<button type="submit" class="btn btn-primary" style="margin-top: 10px;" >Save </button>
						</div>
						<div class="col-md-12">	
							<label ng-if="message == 1" style="color: green;">SuccesFully Saved</label>
						</div>
					</form>	
				</div>
			
		</div>		 
		
					</div>
					 
					<div class="form-group">
    				
    				</div>
</div>

<script type="text/javascript" src='<c:url value="/resources/javascripts/primitives/primitives.latest.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/select2/select2.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>
<script src='<c:url value="/resources/javascripts/bootstrap.min.js"/>' type="text/javascript"></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/controller.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/attributController/controller.js"/>'></script>



