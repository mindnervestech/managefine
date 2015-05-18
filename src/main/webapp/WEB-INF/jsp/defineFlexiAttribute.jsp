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

<div style="height: auto;">

<h3 style="margin-left: 2%;">
	<b><i>Flexi Attribute</i></b>
</h3>

<label style="  margin-left: 22px;">Entities :</label>
    <select style="  margin-left: 22px;width: 166px;" ng-change="selectEntityFirst(selecteditem)"							
							ng-model="selecteditem">
	 
	  <option ng-selected="selectedItem == Customer">Customer</option>
	   <option ng-selected="selectedItem == Supplier">Supplier</option>
	    <option ng-selected="selectedItem == Case">Case</option>
	    <option ng-selected="selectedItem == User">User</option>
</select>


      <div ng-repeat="w in item" style="  width: 100%; float: left;">
      	<div style="  float: left;margin-left: 23px;margin-top: 10px;">
      		<label>Name</label>
      		<input class="input-medium" ng-model="w.name" type="text">
      	</div>
      	<input class="input-medium" ng-model="w.nid" type="hidden">
      	<div style="float: left;margin-left: 23px;margin-top: 10px;">
      		<label>Type</label>
      		<select ng-model="w.type">
			  <option value="string">String</option>
			  <option value="textarea">Text Area</option>
			  <option value="date">Date</option>
			  <option value="FILE">File Type</option>
			</select>
		</div>	
		<div>
				<button ng-if="w.id == undefined" type="button" style="float: left;margin-left: 35px;margin-top: 35px;color: gray;" ng-click="romove($index)" class="btn ">Remove</button>
			</div>
      </div>
      	
      	<div>
      		<button type="button" style="float: left;margin-left: 35px;margin-top: 13px;margin-right: 10px;" ng-click="addMore()" class="btn btn-warning">Add More</button>
      		<button type="button" style="  margin-top: 13px;margin-bottom: 25px;" ng-click="submitFlexiAttribute()" class="btn btn-warning">Save</button>
      	</div> 








</div>

</body>

</html>
