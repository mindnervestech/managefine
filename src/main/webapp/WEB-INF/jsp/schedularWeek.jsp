<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen"
	href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/stylesheets/ruler.css"/>'>	
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/ngDialog.min.css"/>'>	
	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/plugin.js"/>'></script>	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/week.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/matrixwrapper.js"/>'></script>	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/moment.min.js"/>'></script>

<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/jquery.fileDownload.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ngMask.min.js"/>'></script>

<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="SchedularWeekController" ng-init="init()">
<jsp:include page="menuContext.jsp" />
<input type="hidden" id="userID" ng-model="userId" value="${user.id}">
<aside class="date-arrow" style="width: 11%;margin-left: 2%;margin-bottom: 10px;float: none;display: inline-block;">
		<span><!-- <input style="height: 26px;border: 1;width: 170px;" type="week" ng-change="changeWeek(currentDateObject)" name="input" ng-model="currentDateObject"
		 /> --><input class="week-picker" type="text" value="" readonly ng-change="changeWeek(currentDateObject)"> <!-- <input
						type="button" style="display: none;" id="getEmployeeTimesheet"
						value="Go" class="btn"> --></span> <!-- <a href="javascript:void(0);"
								class="cal13 tip" title="Change Date"></a> -->
	</aside>

	<div id="scheduler1" style="width: 100%;"></div>
</body>
</html>