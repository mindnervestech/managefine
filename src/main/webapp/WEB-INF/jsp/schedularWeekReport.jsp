<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/main-syle.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/form-element-style.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/styles.css"/>'>	

<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
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
<body ng-controller="SchedularWeekReportController">
<jsp:include page="menuContext.jsp" />
<input type="hidden" id="userID" ng-model="userId" value="${user.id}">

<section class="full-container inner-content" ng-init='init(${weekReport})' style="min-height: 500px;overflow: auto;">
	<section class="inner-content-full">
		<div class="row" style="margin: 0px 0px 0px 0px; position: relative;">
			<aside style="margin-bottom: 10px;">
				<span><!-- <input style="height: 26px;background: #037CA9;border: none;width: 140px;color: white;" type="week" ng-change="changeWeek(currentDate)" name="input" ng-model="currentDate"
			      	placeholder="YYYY-W##"  /> --><input class="week-picker" type="text" value="" readonly ng-change="changeWeek(currentDateObject)"></span> <!-- <a href="javascript:void(0);"
					class="cal13 tip" title="Change Date"></a> -->
			</aside>
			
			<div ng-if="flag == 1">
			<div class="col-md-12 col-sm-12" id="week_appoinment" ng-repeat="s in staffs">
			<div ng-show="s.weekReport != null">
				
                     <label style="width: 305px;float:left;" >Staffs</label>        
                     <label style="width: 155px;margin-left: -1%;float:left;">Mon ({{s.weekReport[0].date}})</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Tue ({{s.weekReport[1].date}})</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Wed ({{s.weekReport[2].date}})</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Thu ({{s.weekReport[3].date}})</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Fri ({{s.weekReport[4].date}})</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Sat ({{s.weekReport[5].date}})</label>
                     <label style="width: 139px;margin-left: -1%;float:left;">Sun ({{s.weekReport[6].date}})</label>
                </div>
			</div>
			</div>
				<div ng-if="flag == 0">
			<div class="col-md-12 col-sm-12" id="week_appoinment" ng-repeat="s in staffs">
			<div>
                     <label style="width: 305px;float:left;" ng-show="$first">Staffs</label>        
                     <label style="width: 155px;margin-left: -1%;float:left;" ng-show="$first">Mon </label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Tue </label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Wed </label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Thu </label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Fri </label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Sat </label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Sun </label>
                
                </div>
			</div>
			</div>
			
			<div class="col-md-12 col-sm-12" id="week_appoinment" ng-repeat="s in staffs">
				<!-- <div ng-show="s.weekReport != null">
			                  <label style="width: 305px;float:left;" ng-show="$first">Staffs</label>        
                     <label style="width: 155px;margin-left: -1%;float:left;">Mon ({{s.weekReport[0].date}})</label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Tue  <span ng-if="{{s.weekReport[1].date}} != ''">({{s.weekReport[1].date}})</span></label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Wed  <span ng-if="{{s.weekReport[2].date}} != ''">({{s.weekReport[2].date}})</span></label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Thu  <span ng-if="{{s.weekReport[3].date}} != ''">({{s.weekReport[3].date}})</span></label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Fri  <span ng-if="{{s.weekReport[4].date}} != ''">({{s.weekReport[4].date}})</span></label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Sat  <span ng-if="{{s.weekReport[5].date}} != ''">({{s.weekReport[5].date}})</span></label>
                     <label style="width: 139px;margin-left: -1%;float:left;" ng-show="$first">Sun  <span ng-if="{{s.weekReport[6].date}} != ''">({{s.weekReport[6].date}})</span></label>
                </div> -->
			<div id="do_name">{{s.name}}</div>
				<div class="col-md-3 col-sm-12"
					style="padding: 0px; position: relative;">
					<div class="col-md-3 col-sm-12">
						<!-- <img src="get-staff-profile/{{s.id}}"
							style="width: 75px; border: 1px solid #ccc; margin: 0px 10px 0px 0px;" /> -->
					</div>
						
					<div class="col-md-8 col-sm-12" id="weekly_doc_view">
						<h1> {{s.name}}</h1>
						<h3>{{s.speciality}}</h3>
						<p>{{s.bio}}</p>
						<!-- <p>
							Monday, <span>27 Aug, 2014, 10:00 am</span> Follow Up visit, <span>0123
								456 780</span>
						</p> -->
					</div>
					
					<hr	style="background-color: #0167b1; width: 1px; height: 112%; position: absolute; right: 10px; top: 9px;" ng-show="s.flag">
				</div>
				<div class="col-md-9 col-sm-12" style="padding: 0px;">
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<div 
    								data-ac-chart="'pie'" 
    								data-ac-data="s.weekReport[0]" 
    								data-ac-config="config" 
    								class="chart"
    								style="height:150px;width:125px;" ng-click="getWeekDayData('monday',s.week,s.year,s.staffId)">
  								</div>
								<!-- <p id="free_hours" style="margin-bottom: 1px;">
									Total Hrs<b style="border-bottom: 1px solid white;">{{s.weekReport[0].totalHours}}</b>
								</p>
								<p id="free_hours">
									Free Hrs<b>{{s.weekReport[0].freeHours}}</b>
								</p> -->
								<a href="${pageContext.request.contextPath}/getUserTodayData/{{s.weekReport[0].date}}/{{s.staffId}}/{{userId}}" style="margin-left:34%;" ng-show="s.flag">Details</a>
							</div>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<div 
    								data-ac-chart="'pie'" 
    								data-ac-data="s.weekReport[1]" 
    								data-ac-config="config" 
    								class="chart"
    								style="height:150px;width:125px;" ng-click="getWeekDayData('tuesday',s.week,s.year,s.staffId)">
  								</div>
								<!-- <p id="free_hours" style="margin-bottom: 1px;">
									Total Hrs<b style="border-bottom: 1px solid white;">{{s.weekReport[1].totalHours}}</b>
								</p>
								<p id="free_hours">
									Free Hrs<b>{{s.weekReport[1].freeHours}}</b>
								</p> -->
								<a href="${pageContext.request.contextPath}/getUserTodayData/{{s.weekReport[1].date}}/{{s.staffId}}/{{userId}}" style="margin-left:34%;" ng-show="s.flag">Details</a>	
							</div>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<p ng-show="s.flag == false" style="margin-top:38%;"><b>No Calendar defined</b></p>
								<div 
    								data-ac-chart="'pie'" 
    								data-ac-data="s.weekReport[2]" 
    								data-ac-config="config" 
    								class="chart"
    								style="height:150px;width:125px;" ng-click="getWeekDayData('wednesday',s.week,s.year,s.staffId)" ng-show="s.flag">
  								</div>
								<!-- <p id="free_hours" style="margin-bottom: 1px;">
									Total Hrs<b style="border-bottom: 1px solid white;">{{s.weekReport[2].totalHours}}</b>
								</p>
								<p id="free_hours">
									Free Hrs<b>{{s.weekReport[2].freeHours}}</b>
								</p> -->
								<a href="${pageContext.request.contextPath}/getUserTodayData/{{s.weekReport[2].date}}/{{s.staffId}}/{{userId}}" style="margin-left:34%;" ng-show="s.flag">Details</a>
						</div>
						
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
								<div 
    								data-ac-chart="'pie'" 
    								data-ac-data="s.weekReport[3]" 
    								data-ac-config="config" 
    								class="chart"
    								style="height:150px;width:125px;" ng-click="getWeekDayData('thursday',s.week,s.year,s.staffId)">
  								</div>
  								<!-- <p id="free_hours" style="margin-bottom: 1px;">
									Total Hrs<b style="border-bottom: 1px solid white;">{{s.weekReport[3].totalHours}}</b>
								</p>
								<p id="free_hours">
									Free Hrs<b>{{s.weekReport[3].freeHours}}</b>
								</p> -->
								<a href="${pageContext.request.contextPath}/getUserTodayData/{{s.weekReport[3].date}}/{{s.staffId}}/{{userId}}" style="margin-left:34%;" ng-show="s.flag">Details</a>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div 
    							data-ac-chart="'pie'" 
    							data-ac-data="s.weekReport[4]" 
    							data-ac-config="config" 
    							class="chart"
    							style="height:150px;width:125px;" ng-click="getWeekDayData('friday',s.week,s.year,s.staffId)">
  							</div>
  							<!-- <p id="free_hours" style="margin-bottom: 1px;">
								Total Hrs<b style="border-bottom: 1px solid white;">{{s.weekReport[4].totalHours}}</b>
							</p>
							<p id="free_hours">
								Free Hrs<b>{{s.weekReport[4].freeHours}}</b>
							</p> -->
							<a href="${pageContext.request.contextPath}/getUserTodayData/{{s.weekReport[4].date}}/{{s.staffId}}/{{userId}}" style="margin-left:34%;" ng-show="s.flag">Details</a>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div 
    							data-ac-chart="'pie'" 
    							data-ac-data="s.weekReport[5]" 
    							data-ac-config="config" 
    							class="chart"
    							style="height:150px;width:125px;" ng-click="getWeekDayData('saturday',s.week,s.year,s.staffId)">
  							</div>
							<!-- <p id="free_hours" style="margin-bottom: 1px;">
								Total Hrs<b style="border-bottom: 1px solid white;">{{s.weekReport[5].totalHours}}</b>
							</p>
							<p id="free_hours">
								Free Hrs<b>{{s.weekReport[5].freeHours}}</b>
							</p> -->
							<a href="${pageContext.request.contextPath}/getUserTodayData/{{s.weekReport[5].date}}/{{s.staffId}}/{{userId}}" style="margin-left:34%;" ng-show="s.flag">Details</a>
						</div>
						<hr
							style="background-color: #0167b1; width: 1px; height: 89%; position: absolute; right: 3px; top: 9px;" ng-show="s.flag">
					</div>
					<div class="col-md-2 col-sm-12" style="padding: 0px;">
						<div class="col-md-12 col-sm-12" style="padding: 0px;"
							id="doctor_schedule">
							<div 
    							data-ac-chart="'pie'" 
    							data-ac-data="s.weekReport[6]" 
    							data-ac-config="config" 
    							class="chart"
    							style="height:150px;width:125px;" ng-click="getWeekDayData('sunday',s.week,s.year,s.staffId)">
  							</div>
  							<!-- <p id="free_hours" style="margin-bottom: 1px;">
								Total Hrs<b style="border-bottom: 1px solid white;">{{s.weekReport[6].totalHours}}</b>
							</p>
							<p id="free_hours">
								Free Hrs<b>{{s.weekReport[6].freeHours}}</b>
							</p> -->
							<a href="${pageContext.request.contextPath}/getUserTodayData/{{s.weekReport[6].date}}/{{s.staffId}}/{{userId}}" style="margin-left:34%;" ng-show="s.flag">Details</a>
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
</style>
