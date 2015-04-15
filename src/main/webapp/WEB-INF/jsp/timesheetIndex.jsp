<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen"
	href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
	<link rel="stylesheet" media="screen"
	href='<c:url value="/resources/customCSS/jquery.timeentry.css"/>'>
<%-- <script src='<c:url value="/resources/customScripts/timesheet.js"/>'
	type="text/javascript"></script> --%>
<script src='<c:url value="/resources/customScripts/jquery.plugin.js"/>'
	type="text/javascript"></script>		
<script src='<c:url value="/resources/customScripts/jquery.timeentry.js"/>'
	type="text/javascript"></script>	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="TimeSheetController" ng-init="getTimesheetData(${asJson})">
<jsp:include page="menuContext.jsp" />

	<div class="tsMainDiv">
		<div class="timesheetInfoDiv container">
			<div id="empInfo">
				<h5>Employee Name : ${user.firstName} ${user.lastName}</h5>
				<h6>Employee ID : ${user.employeeId}</h6>

				<input type="hidden" id="employeeID" ng-model="userId" value="${user.id}">
			</div>
			<div id="weekInfo">
				<div style="margin-left: 31%;">
					<input class="week-picker" type="text" value="" readonly> <input
						type="button" style="display: none;" id="getEmployeeTimesheet"
						value="Go" class="btn">
				</div>

				<input type="button" style="display: none;" value="Go"
					class="goButton btn">
				<div class="clearfix  " id="weekValue_field">
					<label for="weekValue"></label>
					<div class="input">
						<input type="hidden" name="weekOfYear" ng-model="weekOfYear" id="weekValue" value="">
						<span class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>
				<div class="clearfix  " id="yearValue_field">
					<label for="yearValue"></label>
					<div class="input">
						<input type="hidden" name="year" ng-model="year" id="yearValue" value="">
						<span class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>
 				<div class="clearfix " id="lastUpdateDate_field">
					<label for="lastUpdateDate"></label>
					<div class="input">
						<input type="hidden" id="lastUpdateDate" name="lastUpdateDate"
							value=""> 
							<span class="help-inline"></span>
							<span class="help-block"></span>
					</div>
				</div>
 			</div>
		</div>

		<div class="worksheetDiv container">
		
			<div id="timeSheetTable" style="float: left;width: 100%;"> 

				<div class="worksheetHeader">
					<h5>Work/Absence Hours Reporting</h5>
					<div id="statusInfo">
						<%-- <h6>With :</h6>
						<label style="margin: 4px 10px 0 0; padding: 4px 0; float: left;">
							${user.firstName} ${user.lastName} </label> <label
							style="margin: 4px 0px 0 0; padding: 4px 0; float: left;">/</label> --%>
						<h6>Status : {{timesheetStatus}}</h6>
						<label style="margin: 4px 0; padding: 4px 0; float: left;"
							id="outputStatus"></label>
						<p></p>
					</div>
				</div>
				
				<div style="width: 96%;"class="reportingTable">
					<div class="tableCss" style="margin-left: 1%;width: 1054px;">
						<div class="innerLabelDiv">
							<div class="largeInputLabel largeInputLabel_First clearfix">Project
								Codes</div>
							<div class="largeInputLabel clearfix">Task Codes</div>
						</div>
					<div class="innerDaysDiv">
						<div class="smallInputLabel clearfix">
							Mon<br> <span id="dayLabel_0"></span>
						</div>
						<div class="smallInputLabel clearfix">
							Tue<br> <span id="dayLabel_1"></span>
						</div>
						<div class="smallInputLabel clearfix">
							Wed<br> <span id="dayLabel_2"></span>
						</div>
						<div class="smallInputLabel clearfix">
							Thu<br> <span id="dayLabel_3"></span>
						</div>
						<div class="smallInputLabel clearfix">
							Fri<br> <span id="dayLabel_4"></span>
						</div>
						<div class="smallInputLabel clearfix">
							Sat<br> <span id="dayLabel_5"></span>
						</div>
						<div class="smallInputLabel clearfix">
							Sun<br> <span id="dayLabel_6"></span>
						</div>
						<div class="smallInputLabel totalHRSLabel clearfix">T</div>
						<!-- <div class="smallInputLabel clearfix" style="width:0px;margin-left:-55px;display: none;">Overtime</div> -->
						<a class="btn" id="addMore" ng-show="timesheetStatus !='Submitted'" style="margin-left: 11px; float: left;" ng-click="addMore()"><b>+</b></a>
					</div>
				</div>
				
		
			<div class="twipsies well timesheetRow" style="width: 100%;margin-left: 0%;" ng-repeat="row in timesheetData track by $index" on-finish-render="ngRepeatFinished">
				 <div class="innerInputDiv" style="margin-top:20px;">
					<div class="innerChainSelect">
						<div class="clearfix"
							id="timesheetRows_1__projectCode_field">
							<label for="timesheetRows_1__projectCode"></label>
							<div class="input">
								<select class="largeInput largeInputFirst required" ng-model="row.projectCode" ng-change="setTaskOfProject(row.projectCode)">
									<option class="blank" value="">-select-</option>
									<option ng-repeat="project in projectList" ng-selected="row.projectCode == project.id" value="{{project.id}}">{{project.projectCode}}</option>
								</select> <span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
						<div class="clearfix"
							id="timesheetRows_1__taskCode_field">
							<label for="timesheetRows_1__taskCode"></label>
							<div class="input">
										<select id="timesheetRows_1__taskCode"
											name="timesheetRows[1].taskCode"
											class="largeInput taskInput" ng-model="row.taskCode">
											<option class="blank" value="">-select-</option>
											<option ng-repeat="task in taskList" ng-selected="row.taskCode == task.id" value="{{task.id}}">{{task.taskCode}}</option>
										</select>
								<span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
					</div>
				</div>
				
								<div style="margin-left:32px;"class="innerHrsDiv">
									<div class="clearfix">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.monTime">{{row.monTime}}</div>
										<label></label>
										<div style="margin-top: 8%;"class="input">
											<input type="text"
												ng-model="row.monFrom"
												placeholder="from" class="smallInput dayName" style="margin-top:20%;width:30px;"> 
											<input type="text" placeholder="to" class="smallInput dayName" style="margin-top:20%;width:30px;" ng-model="row.monTo">	
												<span class="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:1px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.tueTime">{{row.tueTime}}</div>
										<label></label>
										<div class="input">
											<input type="text"
												ng-model="row.tueFrom"
												placeholder="from" class="smallInput dayName" style="margin-top:22%;width:30px;">
												<input type="text" placeholder="to" class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.tueTo"> <span
												class="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:1px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.wedTime">{{row.wedTime}}</div>
										<label></label>
				
										<div class="input">
											<input type="text"
												ng-model="row.wedFrom"
												placeholder="from" class="smallInput dayName" style="margin-top:22%;width:30px;">
												<input type="text" placeholder="to" class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.wedTo"> <span
												class="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:1px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.thuTime">{{row.thuTime}}</div>
										<label></label>
										<div class="input">
											<input type="text"
												ng-model="row.thuFrom"
												placeholder="from" class="smallInput dayName" style="margin-top:22%;width:30px;">
												<input type="text" placeholder="to" class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.thuTo"> <span
												class="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:1px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.friTime">{{row.friTime}}</div>
										<label></label>
										<div class="input">
											<input type="text"
												ng-model="row.friFrom"
												placeholder="from" class="smallInput dayName" style="margin-top:22%;width:30px;">
												<input type="text" placeholder="to" class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.friTo"> <span
												class="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:1px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.satTime">{{row.satTime}}</div>
										<label></label>
										<div class="input">
											<input type="text"
												ng-model="row.satFrom"
												placeholder="from" class="smallInput dayName" style="margin-top:22%;width:30px;">
												<input type="text" placeholder="to" class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.satTo"> <span
												class="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:1px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.sunTime">{{row.sunTime}}</div>
										<label></label>
										<div class="input">
											<input type="text"
												ng-model="row.sunFrom"
												placeholder="from" class="smallInput dayName" style="margin-top:22%;width:30px;">
												<input type="text" placeholder="to" class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.sunTo"> <span
												class="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
								</div>
				
								<div class="totalHrsDiv">
									<div class="clearfix  ">
										<label></label>
										<div class="input">
				
											<input type="text"
												ng-model="row.totalHrs"
												placeholder="" class="smallInput totalHRSInput readonlycls"
												readonly="readonly" style="margin-top:36%;"> <span class="help-inline"></span> <span
												class="help-block"></span>
										</div>
									</div>
								</div>
								<!-- <div class="clearfix">
									<label></label>
									<div class="input" style="margin-top:-44%;">
				
										<input type="checkbox"
										 	value="true"
										 	ng-model="row.isOverTime"
											class="checkBox checkBoxInput" style="display: none;"> <span></span> <span
											class="help-inline"></span> <span class="help-block"></span>
									</div>
								</div> -->
								<a class="remove btn danger pull-right" ng-show="timesheetStatus !='Submitted'" ng-click="removeRow($index,row.rowId)" style="margin-top:22px;margin-right:3%;">X</a>
							
							</div>
				
					</div>
				
				<div class="actions">
			<input type="button" id="copyFromLastWeek" class="btn btn-warning" ng-click="copyFromLastWeek()"
				Value="Copy last week"> <input type="button"
				id="saveTimesheetForm" class="btn btn-warning" ng-click="saveTimesheet('Draft')" value="Save">
			<input type="button" id="submitTimesheetForm" class="btn btn-warning" ng-click="saveTimesheet('Submitted')"
				value="Submit"> <input type="button"
				id="retractTimesheetForm" class="btn btn-warning" ng-click="retractTimesheet()" value="Retract">
			<input type="hidden" id="cancelTimesheetForm" class="btn btn-warning"
				Value="Cancel">
		</div>
				
				
				
				</div>
				
			</div>	
		
		</div>
	</div>
	
</body>
</html>
<style>
.largeInputLabel_First {
	margin-right: -3px;
	margin-left: 12px ;
}
.innerLabelDiv {
	width: 310px;
	float: left;
}
.smallInputLabel {
	width: 75px;
}
.innerDaysDiv {
	float: right;
	width: 743px;
}

.largeInputLabel {
	width: 150px;
}

.largeInput {
	width: 145px;
}
.innerInputDiv {
	width: 300px;
}

#timesheetRows_0__overTime_field{
	width: 5%;
}
.well {
	width: 96%;
	margin-left: 1%;
	padding-right: 0% !important;
}
</style>
<script>

		$(document).ready(function() {
			 var startOfWeek;
		/*	Date.prototype.getWeek = function() {
			    var onejan = new Date(this.getFullYear(),0,1);
			    return Math.floor((((this - onejan) / 86400000) + onejan.getDay()+1)/7);
			}
			
			Date.prototype.getStartOfWeek = function() {
				var day =   this.getDay() - 1;
				var startOfWeek;
				if (day != -1) {
					startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * day ));
				} else {
					startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * 6 ));
				}
				return startOfWeek;
			}

			$('.week-picker').datepicker({
				chooseWeek:true,
				calendarWeeks:true,
				weekStart:1,
				format: 'dd M yy'
			}).on('changeDate',function(ev){
				$("#weekValue").val(ev.date.getWeek()+1);
				$("#yearValue").val(ev.date.getFullYear());
				$("#selectedWeekRange").html($(".week-picker").val());
				startOfWeek = ev.date.getStartOfWeek();
				console.log(ev.date.getWeek()+1);
			});
			
			var today = new Date();
			var todaysWeek = today.getWeek() + 1;
			$("#weekValue").val(today.getWeek() + 1);
			$("#yearValue").val(today.getFullYear());
			var day =   today.getDay() - 1;
			startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
			var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
			
			$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
					$.datepicker.formatDate('dd M yy', endOfWeek)); */
			
		});
		
		$('.timeRange').timeEntry({beforeShow: customRange}); 
		 
		function customRange(input) { 
		    return {minTime: (input.id === 'timeTo' ? 
		        $('#timeFrom').timeEntry('getTime') : null),  
		        maxTime: (input.id === 'timeFrom' ? 
		        $('#timeTo').timeEntry('getTime') : null)}; 
		}
		
</script>