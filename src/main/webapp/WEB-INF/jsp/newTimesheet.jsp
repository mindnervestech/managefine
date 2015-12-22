<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen"
	href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
	<link rel="stylesheet" media="screen"
	href='<c:url value="/resources/customCSS/pnotify.custom.min.css"/>'>
<%-- <script src='<c:url value="/resources/customScripts/timesheet.js"/>'
	type="text/javascript"></script> --%>
<script src='<c:url value="/resources/customScripts/jquery.plugin.js"/>'
	type="text/javascript"></script>		
<script src='<c:url value="/resources/customScripts/jquery.timeentry.js"/>'
	type="text/javascript"></script>	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/pnotify/pnotify.custom.min.js"/>'></script>	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ngMask.min.js"/>'></script>

<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="NewTimeSheetController" ng-init='getTimesheetData(${asJson})'>
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
						<div class="smallInputLabel clearfix" style="cursor:pointer;" id="monLabel">
							<a href="" style="text-decoration: none; color:inherit" ng-click="getWeekDayData('monday')" >Mon  <span id="dayLabel_0"></span></a>
							<img src="/time/resources/images/plus.png" ng-click="addDataOnPlus('mon')" style="height:10px;margin-left: 8px; cursor: pointer; cursor: hand; ">
						</div>
					
						<div class="smallInputLabel clearfix" style="cursor:pointer;" id="tueLabel">
							<a href="" style="text-decoration: none; color:inherit" ng-click="getWeekDayData('tuesday')">Tue <span id="dayLabel_1"></span></a>
							<img src="/time/resources/images/plus.png" ng-click="addDataOnPlus('tue')" style="height:10px;margin-left: 8px; cursor: pointer; cursor: hand; ">
						</div>
						
						<div class="smallInputLabel clearfix" style="cursor:pointer;" id="wedLabel">
							<a href="" style="text-decoration: none; color:inherit"  ng-click="getWeekDayData('wednesday')">Wed <span id="dayLabel_2"></span></a>
							<img src="/time/resources/images/plus.png" ng-click="addDataOnPlus('wed')" style="height:10px;margin-left: 8px; cursor: pointer; cursor: hand; ">
						</div>
		
						<div class="smallInputLabel clearfix" style="cursor:pointer;" id="thuLabel">
							<a href="" style="text-decoration: none; color:inherit" ng-click="getWeekDayData('thursday')">Thu <span id="dayLabel_3"></span></a>
							<img src="/time/resources/images/plus.png" ng-click="addDataOnPlus('thu')" style="height:10px;margin-left: 8px; cursor: pointer; cursor: hand; ">
						</div>
						<div class="smallInputLabel clearfix" style="cursor:pointer;" id="friLabel">
							<a href="" style="text-decoration: none; color:inherit" ng-click="getWeekDayData('friday')">Fri <span id="dayLabel_4"></span></a>
							<img src="/time/resources/images/plus.png" ng-click="addDataOnPlus('fri')" style="height:10px;margin-left: 8px; cursor: pointer; cursor: hand; ">
						</div>
						<div class="smallInputLabel clearfix" style="cursor:pointer;" id="satLabel">
							<a href="" style="text-decoration: none; color:inherit"  ng-click="getWeekDayData('saturday')">Sat <span id="dayLabel_5"></span></a>
							<img src="/time/resources/images/plus.png" ng-click="addDataOnPlus('sat')" style="height:10px;margin-left: 8px; cursor: pointer; cursor: hand; ">
						</div>
						<div class="smallInputLabel clearfix" style="cursor:pointer;" id="sunLabel">
							<a href="" style="text-decoration: none; color:inherit"  ng-click="getWeekDayData('sunday')">Sun <span id="dayLabel_6"></span></a>
							<img src="/time/resources/images/plus.png" ng-click="addDataOnPlus('sun')" style="height:10px;margin-left: 8px; cursor: pointer; cursor: hand; ">
						</div>
						<div class="smallInputLabel totalHRSLabel clearfix">T</div>
						<!-- <div class="smallInputLabel clearfix" style="width:0px;margin-left:-55px;display: none;">Overtime</div> -->
						<a type="button" disabled class="btn" id="addMore" style="margin-left: 11px; float: left;" ng-click=""><b>+</b></a>
					</div>
					
						
				</div>
				
			<div style="margin-left:50%;"><b>Enter time in 24 hrs format e.g. (09:30-14:30)</b></div>
			<div class="twipsies well timesheetRow" style="width: 100%;margin-left: 0%;" ng-repeat="row in timesheetData track by $index" on-finish-render="ngRepeatFinished">
				 <div class="innerInputDiv" style="margin-top:47px;">
					<div class="innerChainSelect">
						<div class="clearfix"
							id="timesheetRows_1__projectCode_field">
							<label for="timesheetRows_1__projectCode"></label>
							<div class="input">
								<select class="largeInput largeInputFirst required" ng-model="row.projectCode" ng-change="setTaskOfProject(row.projectCode,$index)">
									<option class="blank" value="">-select-</option>
									<option ng-repeat="project in projectList" ng-selected="row.projectCode == project.id" value="{{project.id}}">{{project.projectCode}}</option>
								</select> <span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
						<div class="clearfix"
							id="timesheetRows_1__taskCode_field">
							<label for="timesheetRows_1__taskCode"></label>
							<div class="input" ng-init="setTaskOfProject(row.projectCode,$index)">
										<select id="timesheetRows_1__taskCode"
											name="timesheetRows[1].taskCode"
											class="largeInput taskInput" ng-model="row.taskCode">
											<option class="blank" value="">-select-</option>
											<option ng-repeat="task in taskListArray[$index] | orderBy: 'taskCode'" ng-selected="row.taskCode == task.id" value="{{task.id}}">{{task.taskCode}}</option>
										</select>
								<span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
					</div>
				</div>
				
								<div style="margin-left:40px;"class="innerHrsDiv">
									<div class="clearfix">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.monTime">{{row.monTime}}</div>
										<label></label>
										<img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'mon')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/>
										<div style="margin-top: 8%;" class="input" ng-init="initFTTime(row,'mon')">
										    <input type="text"
										    ng-model="row.monFromTo"
										    ng-blur="splitToFTTime(row,'mon')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.monFrom"
												ng-blur="checkTime(row.monFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.monTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.monTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:10px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.tueTime">{{row.tueTime}}</div>
										<label></label>
										<img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'tue')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/>
										<div class="input" ng-init="initFTTime(row,'tue')">
										    <input type="text"
										    ng-model="row.tueFromTo"
										    ng-blur="splitToFTTime(row,'tue')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.tueFrom"
												ng-blur="checkTime(row.tueFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.tueTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.tueTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:10px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.wedTime">{{row.wedTime}}</div>
										<label></label>
										<img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'wed')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/>
										<div class="input" ng-init="initFTTime(row,'wed')">
										    <input type="text"
										    ng-model="row.wedFromTo"
										    ng-blur="splitToFTTime(row,'wed')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.wedFrom"
												ng-blur="checkTime(row.wedFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.wedTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.wedTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:10px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.thuTime">{{row.thuTime}}</div>
										<label></label>
										<img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'thu')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/>
										<div class="input" ng-init="initFTTime(row,'thu')">
										    <input type="text"
										    ng-model="row.thuFromTo"
										    ng-blur="splitToFTTime(row,'thu')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.thuFrom"
												ng-blur="checkTime(row.thuFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.thuTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.thuTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:10px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.friTime">{{row.friTime}}</div>
										<label></label>
										<img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'fri')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/>
										<div class="input" ng-init="initFTTime(row,'fri')">
										    <input type="text"
										    ng-model="row.friFromTo"
										    ng-blur="splitToFTTime(row,'fri')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.friFrom"
												ng-blur="checkTime(row.friFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.friTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.friTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:10px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.satTime">{{row.satTime}}</div>
										<label></label>
										<img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'sat')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/>
										<div class="input" ng-init="initFTTime(row,'sat')">
										    <input type="text"
										    ng-model="row.satFromTo"
										    ng-blur="splitToFTTime(row,'sat')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.satFrom"
												ng-blur="checkTime(row.satFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.satTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.satTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" style="margin-left:10px;">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.sunTime">{{row.sunTime}}</div>
										<label></label>
										<img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'sun')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/>
										<div class="input" ng-init="initFTTime(row,'sun')">
										    <input type="text"
										    ng-model="row.sunFromTo"
										    ng-blur="splitToFTTime(row,'sun')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.sunFrom"
												ng-blur="checkTime(row.sunFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.sunTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.sunTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
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
												readonly="readonly" style="margin-top:36%;display: none;"> <span class="help-inline"></span> <span
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
								<a class="remove btn danger pull-right" ng-show="isShow" ng-click="confirmDelete($index,row.rowId)" style="margin-top:22px;margin-right:3%;">X</a>
							
							</div>
				
					</div>
				
				<div class="actions">
			<input type="button" id="copyFromLastWeek" class="btn btn-warning" ng-click="confirmCopy()"
				Value="Copy last week"> <input type="button"
				id="saveTimesheetForm" class="btn btn-warning" ng-click="saveTimesheet('Draft')" value="Save">
			<input type="button" id="submitTimesheetForm" class="btn btn-warning" ng-click="saveTimesheet('Submitted')"
				value="Submit"> <input type="button"
				id="retractTimesheetForm" style="display:none"class="btn btn-warning" ng-click="confirmRetract()" value="Retract">
			<input type="hidden" id="cancelTimesheetForm" class="btn btn-warning"
				Value="Cancel">
		</div>
				
				
				
				</div>
				
			</div>	
		
		</div>
	</div>
	<button id="popupBtn" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" style="display: none;">
</button>
<button id="popupBtn2" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal2" style="display: none;">
</button>
<button id="popupBtn3" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal3" style="display: none;">
</button>
<button id="popupBtn4" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal4" style="display: none;">
</button>
<button id="popupBtn5" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal5" style="display: none;">
</button>
<button id="popupBtn6" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal6" style="display: none;">
</button>

</body>
</html>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" id="retractClose" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
        <b>Are you sure to retract?</b>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
        <button type="button" class="btn btn-primary" ng-click="retractTimesheet()">Yes</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" id="lastWeekClose" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
        <b>Are you sure to copy from last week?</b>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
        <button type="button" class="btn btn-primary" ng-click="copyFromLastWeek()">Yes</button>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" id="nodataClose" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
        <b>No data found for last week</b>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:10000">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" id="deleteClose" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
        <b>Are you sure to delete?</b>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
        <button type="button" class="btn btn-primary" ng-click="removeRow($index,row.rowId)">Yes</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="myModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" id="modal5Close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      	<h4 class="modal-title">Add Supplier and Customer</h4>
      </div>
      <div class="modal-body">
        <div class="form-inline">
		  <div class="form-group"> 
		    <label>Supplier</label>
		    <select class="form-control" ng-model="supplierCode" style="width:182px;">
		    	<option value="">--Select Supplier--</option>
		    	<option ng-repeat="supplier in supplierList | orderBy:'name'" value="{{supplier.id}}">{{supplier.name}}</option>
		    </select>
		  </div>
		  <div class="form-group">
		    <label>Customer</label>
		     <select class="form-control" ng-model="customerCode" style="width:182px;">
		    	<option value="">--Select Customer--</option>
		    	<option ng-repeat="cust in customerList | orderBy:'name'" value="{{cust.id}}">{{cust.name}}</option>
		    </select>
		  </div>
		  
		</div>
		<div class="form-inline" Style="margin-top:6%;margin-left:13px;">
			<div class="form-group">
			    <label>Notes</label>
			     <textarea ng-model="notes" class="form-group"></textarea>
			  </div>
		  </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" ng-click="addData()">Save</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="myModal6" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none; width: 1162px; left: 26%; ">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" id="modal6Close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      	<h4 class="modal-title">{{headerData}}</h4>
      	
      	 <a class="btn" id="addMore1" style="margin-left: 20px; float: left;" ng-click="addMore1()"><b>+</b></a>
     <input type="button"
				id="saveTimesheetForm" class="btn btn-warning" style="margin-left: 10px;" ng-click="updateRows()" value="Save">
      	</div>
      	
      	    <div class="modal-body" style="padding: 20px">
      	  
        <div class="form-inline">
       
         <div class="twipsies well timesheetRow" style="width: 100%;margin-left: 0%;" ng-repeat="row in dayData track by $index" on-finish-render="ngRepeatFinished"> 
		  <div class="form-group" >
		   
		   	    <div class="innerInputDiv" style="margin-top:30px;">
		   
		   		     <div class="clearfix"
							id="timesheetRows_1__projectCode_field">
							<label for="timesheetRows_1__projectCode"></label>
							<div class="input">
								<select class="largeInput largeInputFirst required" ng-model="row.projectCode" ng-change="setTaskOfPrj(row.projectCode,$index)">
									<option class="blank" value="">-select-</option>
									<option  ng-repeat="project in projectLst" ng-selected="row.projectCode == project.id" value="{{project.id}}">{{project.projectCode}}</option>
								</select> <span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
						<div class="clearfix"
							id="timesheetRows_1__taskCode_field">
							<label for="timesheetRows_1__taskCode"></label>
							<div class="input" ng-init="setTaskOfPrj(row.projectCode,$index)">
										<select id="timesheetRows_1__taskCode"
											name="timesheetRows[1].taskCode"
											class="largeInput taskInput" ng-model="row.taskCode">
											<option class="blank" value="">-select-</option>
											<option ng-repeat="task in taskLstArray[$index] | orderBy: 'taskCode'" ng-selected="row.taskCode == task.id" value="{{task.id}}">{{task.taskCode}}</option>
										</select>
								<span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
				</div>		
								  <div style="margin-left:20px;"class="innerHrsDiv">
									<div class="clearfix" ng-if="dayScope=='mon'">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.monTime">{{row.monTime}}</div>
										<label></label> 
										<%-- <img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'mon')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/> --%> 
										 <div style="margin-top: 8%;" class="input" ng-init="initFTTime(row,'mon')"> 
										    <input type="text"
										    ng-model="row.monFromTo"
										    ng-blur="splitToFTTime(row,'mon')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.monFrom"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;margin-bottom:-18px;margin-top:10px;">
											<input type="hidden" 
											    placeholder="To" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.monTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" ng-if="dayScope=='tue'">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.tueTime">{{row.tueTime}}</div>
										<label></label>
										<%-- <img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'tue')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/> --%>
										<div class="input" ng-init="initFTTime(row,'tue')">
										    <input type="text"
										    ng-model="row.tueFromTo"
										    ng-blur="splitToFTTime(row,'tue')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.tueFrom"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.tueTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" ng-if="dayScope=='wed'">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.wedTime">{{row.wedTime}}</div>
										<label></label>
										<%-- <img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'wed')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/> --%>
										<div class="input" ng-init="initFTTime(row,'wed')">
										    <input type="text"
										    ng-model="row.wedFromTo"
										    ng-blur="splitToFTTime(row,'wed')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.wedFrom"
												ng-blur="checkTime(row.wedFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.wedTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.wedTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" ng-if="dayScope=='thu'">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.thuTime">{{row.thuTime}}</div>
										<label></label>
										<%-- <img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'thu')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/> --%>
										<div class="input" ng-init="initFTTime(row,'thu')">
										    <input type="text"
										    ng-model="row.thuFromTo"
										    ng-blur="splitToFTTime(row,'thu')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.thuFrom"
												ng-blur="checkTime(row.thuFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.thuTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.thuTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" ng-if="dayScope=='fri'">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.friTime">{{row.friTime}}</div>
										<label></label>
									<%-- <img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'fri')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/> --%>
										<div class="input" ng-init="initFTTime(row,'fri')">
										    <input type="text"
										    ng-model="row.friFromTo"
										    ng-blur="splitToFTTime(row,'fri')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.friFrom"
												ng-blur="checkTime(row.friFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.friTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.friTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" ng-if="dayScope=='sat'">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.satTime">{{row.satTime}}</div>
										<label></label>
										<%-- <img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'sat')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/> --%>
										<div class="input" ng-init="initFTTime(row,'sat')">
										    <input type="text"
										    ng-model="row.satFromTo"
										    ng-blur="splitToFTTime(row,'sat')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.satFrom"
												ng-blur="checkTime(row.satFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.satTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.satTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
									
									<div class="clearfix" ng-if="dayScope=='sun'">
										<div style="font-size:9px;margin-bottom:-20px;display:none;" ng-model="row.sunTime">{{row.sunTime}}</div>
										<label></label>
										<%-- <img src='<c:url value="/resources/images/plus.png"/>' ng-click="addCustomerAndSupplier($index,'sun')" style="height:12px;margin-left:35px;margin-bottom:-18px;margin-top:10px;"/> --%>
										<div class="input" ng-init="initFTTime(row,'sun')">
										    <input type="text"
										    ng-model="row.sunFromTo"
										    ng-blur="splitToFTTime(row,'sun')"
										    mask="29:59-29:59" restrict="reject" clean="false"
										    ng-pattern="ftTimeRegexp"
										    class="smallInput dayName" style="margin-top:20%;width:65px;"
										    placeholder="From-To"
										    >
											
											<input type="hidden"
												ng-model="row.sunFrom"
												ng-blur="checkTime(row.sunFrom)"
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												placeholder="From" class="smallInput dayName" style="margin-top:22%;width:30px;">
											<input type="hidden" 
											    placeholder="To" ng-blur="checkTime(row.sunTo)" 
												mask="29:59" restrict="reject"" clean="false" ng-pattern="timeRegexp"
												class="smallInput dayName" style="margin-top:22%;width:30px;" ng-model="row.sunTo"> 
												<spanclass="help-inline"></span> <span class="help-block"></span>
										</div>
									</div>
				
		
										
																			
						</div>
					</div>			
								 <div class="form-group">
								 <div style="margin-top:48px;">
									<div class="clearfix" style="margin-left:7px;">
									 <select id="scode" class="form-control" ng-model="row.supplier" style="width:182px;">
		    							<option value="">--Select Supplier--</option>
		    							<option ng-repeat="supplier in supplierList | orderBy: 'name'" ng-selected="row.supplier == supplier.id" value="{{supplier.id}}">{{supplier.name}}</option>
		   							 </select>
									</div>	
								</div>	
								</div>
								 <div class="form-group">
								<div style="margin-top:48px;">	
									<div class="clearfix" style="margin-left:7px;">
									<select id="cust" class="form-control" ng-model="row.customer" ng-init="cus" style="width:182px;">
		    							<option value="">--Select Customer--</option>
		    							<option ng-repeat="cust in customerList | orderBy:'name'" ng-selected="row.customer == cust.id" value="{{cust.id}}">{{cust.name}}</option>
		  						    </select>
									</div>	
								</div>
							</div>
							
						<div class="form-group">
							<div style="margin-top:28px;">
				     		<div class="clearfix" style="margin-left:7px;">
				     			<div class="form-inline" Style="margin-top:6%;">
			    			 	<textarea ng-model="row.note" class="form-group"></textarea>
			 				 	</div>
			 				 </div>
		 					 </div>
				  		</div>
				  	 <!--ng if form group close  -->	
						<div class="form-group">
							<div style="margin-top:28px;">
				     		<div class="clearfix" style="margin-left:7px;">
				     		<!-- <a class="btn" id="addMore" style="margin-left: 11px; float: left;" ng-click="addMore()"><b>+</b></a> -->
				     		<a class="remove btn danger pull-right" ng-show="isShow" ng-click="confirmDelete($index,row.rowId)" style="margin-top:20px;margin-right:3%;">X</a>
				         </div>
				         </div>
				         </div>         
		   	</div>
		   	
		</div>
		</div>
      </div>
      </div>
    
</div>



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

.ui-pnotify-history-container.well {
top: -130.3125px;
}
.modal-header {
 border-bottom: 0px;
}

.well {
 margin-top:-20px;
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