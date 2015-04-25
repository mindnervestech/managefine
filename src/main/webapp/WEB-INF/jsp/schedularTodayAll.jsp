<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/stylesheets/ruler.css"/>'>	
	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/plugin.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/week.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/week-horizontal.js"/>'></script>	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ngMask.min.js"/>'></script>

<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="SchedularTodayAllController">
<jsp:include page="menuContext.jsp" />
<input type="hidden" id="userID" ng-model="userId" value="${user.id}">
<div style="width: 100%;display: inline-block;">
	<aside class="date-arrow" style="float: right;margin: 5px 20px 5px 0px;">
		<span>
			<input style="height: 26px;width: 100px;" ng-change="getAllStaffAppointments(currentDate)" data-date-autoclose="true" ng-model="currentDate" data-provide="datepicker" data-date-format="mm/dd/yyyy">
		</span>
	</aside>
</div>
<div id="horizontal-scheduler" style="border: 1px solid black;height: 484px;" ng-init='init(${list})' ></div>
</body>
</html>

<style>
#webui-popover-title {
	padding: 8px 14px !important;
	margin: 0 !important;
	font-size: 14px !important;
	font-weight: normal !important;
	line-height: 18px !important;
	background-color: #f7f7f7 !important;
	border-bottom: 1px solid #ebebeb !important;
	border-radius: 5px 5px 0 0 !important;
	float: none;
}
.webui-popover {
	width: 400px;
}
.webui-popover-inner ,.webui-popover-inner table{
	width: 100%;
}
.webui-popover table tbody tr td { 
	text-align: center;
}
</style>