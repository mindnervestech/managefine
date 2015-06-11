<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>	
         
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/d3.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/angular-charts.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/jquery-ui.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ngMask.min.js"/>'></script>
 
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="UsageReportController">
<jsp:include page="menuContext.jsp" />
<input type="hidden" id="userID" ng-model="userId" value="${user.id}">
<section class="full-container inner-content" ng-init='init(${plannedTask},${actualTask})' style="min-height: 500px;overflow: auto;">
	<section class="inner-content-full">
		<div class="row" style="margin: 0px 0px 0px 0px; position: relative;">
		<div style="float:left;margin-left:17%;margin-top:2%;">
			<aside style="margin-bottom: 10px;">
				<span>
				<input class="week-picker" type="text" value="" readonly></span>
			</aside>
		</div>
		<div style="float:left;margin-left:3%;margin-top:2%;">
			<select ng-model="selectedUser">
				<option ng-repeat="usr in userList" value="{{usr.id}}">{{usr.name}}</option>		
			</select>
		</div>
		<div style="float:left;margin-left:3%;margin-top:2%;">	
			<select ng-model="reportLevel">
				<option value="task">Task Level</option>
				<option value="stage">Stage Level</option>
			</select>
		</div>	
		<div style="margin-right:15%;margin-top:2%;float:right;">
			<input type="button" value="Submit" ng-click="getReportData()" class="btn" style="color:blue;"> 
		</div>
		
		<div style="margin-left:40%;margin-top:7%;">
		<label ng-hide="taskWeekTotal.weekReport == null"><b>Planned &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Actual</b></label>
		</div>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="taskWeekTotal.weekReport[0]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;float:left;margin-left:37%;">
	  								</div>
	  								
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="taskWeekTotal.weekReport[1]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;float:left;">
	  								</div>
			<div class="col-md-12 col-sm-12" id="week_appoinment" style="margin-left:9%;margin-top:3%;">        
                     <label style="width: 155px;margin-left: -1%;float:left;">Mon {{plannedTask.weekReport[0].date}}</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Tue {{plannedTask.weekReport[1].date}}</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Wed {{plannedTask.weekReport[2].date}}</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Thu {{plannedTask.weekReport[3].date}}</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Fri {{plannedTask.weekReport[4].date}}</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Sat {{plannedTask.weekReport[5].date}}</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Sun {{plannedTask.weekReport[6].date}}</label>
			</div>
			<div class="col-md-9 col-sm-12" style="padding: 0px;margin-left:9%;">
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<label style="margin-left:30%;" ng-hide="plannedTask.weekReport == null"><b>Planned</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="plannedTask.weekReport[0]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								<label style="margin-left:30%;" ng-hide="actualTask.weekReport == null"><b>Actual</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="actualTask.weekReport[0]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
							</div>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<label style="margin-left:30%;" ng-hide="plannedTask.weekReport == null"><b>Planned</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="plannedTask.weekReport[1]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								<label style="margin-left:30%;" ng-hide="actualTask.weekReport == null"><b>Actual</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="actualTask.weekReport[1]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
							</div>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<label style="margin-left:30%;" ng-hide="plannedTask.weekReport == null"><b>Planned</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="plannedTask.weekReport[2]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								<label style="margin-left:30%;" ng-hide="actualTask.weekReport == null"><b>Actual</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="actualTask.weekReport[2]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
							</div>
						</div>
						
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
								<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<label style="margin-left:30%;" ng-hide="plannedTask.weekReport == null"><b>Planned</b></label>
								<p ng-show="plannedTask.weekReport == null" style="margin-top:10%;"><b>No Calendar defined</b></p>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="plannedTask.weekReport[3]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
	  								<label style="margin-left:30%;" ng-hide="actualTask.weekReport == null"><b>Actual</b></label>
	  								<p ng-show="actualTask.weekReport == null" style="margin-top:5%;"><b>No Timesheet defined</b></p>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="actualTask.weekReport[3]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
							</div>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<label style="margin-left:30%;" ng-hide="plannedTask.weekReport == null"><b>Planned</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="plannedTask.weekReport[4]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								<label style="margin-left:30%;" ng-hide="actualTask.weekReport == null"><b>Actual</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="actualTask.weekReport[4]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
							</div>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<label style="margin-left:30%;" ng-hide="plannedTask.weekReport == null"><b>Planned</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="plannedTask.weekReport[5]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								<label style="margin-left:30%;" ng-hide="actualTask.weekReport == null"><b>Actual</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="actualTask.weekReport[5]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
							</div>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<label style="margin-left:30%;" ng-hide="plannedTask.weekReport == null"><b>Planned</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="plannedTask.weekReport[6]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								<label style="margin-left:30%;" ng-hide="actualTask.weekReport == null"><b>Actual</b></label>
									<div 
	    								data-ac-chart="'pie'" 
	    								data-ac-data="actualTask.weekReport[6]" 
	    								data-ac-config="config" 
	    								class="chart"
	    								style="height:150px;width:125px;">
	  								</div>
	  								
							</div>
						</div>

					</div>

				</div>
			
	</div>

	</section>
</section>

</body>
</html>

<style>
.col-md-2 {
	width: 14.285%;
}
#week_appoinment label {
	background-color: #0067b0;
	width: 100%;
	text-align: center;
	color: #fff;
	font-size: 13px;
	font-weight: normal;
	text-transform: uppercase;
	padding: 5px 0px 5px 0px;
}
#do_name {
	background-color: #dedede;
	width: 100%;
	text-align: center;
	color: #303030;
	font-size: 13px;
	font-weight: normal;
	text-transform: uppercase;
	padding: 5px 0px 5px 0px;
	float: left;
	margin: 10px 0px 10px 0px;
}
#free_hours b {
	float: right;
	font-weight: lighter;
	color: #fff;
	background-color: #8cc042;
	padding: 0px 3px 0px 3px;
	margin-right: 5px;
}
#weekly_doc_view h1 {
	font-size: 20px;
	color: #7b8398;
	font-weight: normal;
	margin: 0px;
	padding: 0px 0px 5px 5px;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	width: 98%;
}
#weekly_doc_view h3 {
	font-size: 16px;
	color: #0167b1;
	font-weight: normal;
	margin: 0px;
	padding: 0px 0px 5px 5px;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	width: 98%;
}
#weekly_doc_view p {
	font-size: 11px;
	color: #acb2be;
	font-weight: normal;
	margin: 0px;
	padding: 0px 0px 5px 5px;
}
.col-sm-12, .col-md-12 {
padding-left: 0px;
padding-right: 0px;
}

.container {
width:1230px;
}

.ui-pnotify-history-container.well {
top: -130.3125px;
}
.chart {
 width: 500px;
 height: 500px;
}
</style>
