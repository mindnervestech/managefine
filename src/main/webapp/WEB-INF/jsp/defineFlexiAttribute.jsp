<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<html ng-app="FlexiAttributeApp">
<head>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<!-- <div id="basicdiagram2" style="width: 640px; height: 480px; border-style: dotted; border-width: 1px;">
</div> -->

<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/flexiController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/flexiController/controller.js"/>'></script>

</head>
<body ng-controller="FlexiAttributeController" ng-init='loadData()'>

<div style="height: 300px;">

<h3 style="margin-left: 2%;">
	<b><i>Flexi Attribute</i></b>
</h3>

<label style="  margin-left: 22px;">Entities :</label>
    <select style="  margin-left: 22px;" ng-change="selectEntityFirst(selecteditem)"							
							ng-model="selecteditem">
	  <option ng-selected="selectedItem == User">User</option>
	  <option ng-selected="selectedItem == Project">Project</option>
	  <option ng-selected="selectedItem == Client">Client</option>
	   <option ng-selected="selectedItem == Supplier">Supplier</option>
	  <option ng-selected="selectedItem == Task">Task</option>
</select>


      <div ng-repeat="w in item" style="  width: 100%; float: left;">
      	<div style="  float: left;margin-left: 23px;margin-top: 10px;">
      		<label>Name</label>
      		<input class="input-medium" ng-model="w.name" type="text">
      	</div>
      	<div style="float: left;margin-left: 23px;margin-top: 10px;">
      		<label>Type</label>
      		<select ng-model="w.type">
			  <option>string</option>
			  <option>textarea</option>
			  <option>date</option>
			  <option>FILE</option>
			</select>
		</div>	
		<div>
				<button type="button" style="float: left;margin-left: 35px;margin-top: 35px;" ng-click="romove($index)" class="btn btn-danger">Remove</button>
			</div>
      </div>
      	
      	<div style=" float: right;margin-bottom: 10px;">
      		<button type="button" style="float: left;margin-left: 35px;margin-top: 18px;margin-right: 10px;" ng-click="addMore()" class="btn btn-primary">Add More</button>
      		<button type="button" style="float: right;margin-top: 7%;margin-right: 77px;" ng-click="submitFlexiAttribute()" class="btn btn-success">Save</button>
      	</div> 








</div>

</body>

</html>
