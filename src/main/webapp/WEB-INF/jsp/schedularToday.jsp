<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/stylesheets/ruler.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/ngDialog.min.css"/>'>	
	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/schedular/plugin.js"/>'></script>	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/jquery.fileDownload.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="SchedularTodayController" ng-init='getSchedulerDataByDate(${userJson})'>
<jsp:include page="menuContext.jsp" />
		<div class="input-append date datepicker" style="margin-left:2%;">
			<input data-date-autoclose="true" data-provide="datepicker"  style="width: 70%;" type="text" ng-change="getSchedulerDay(currentDate)" ng-model="currentDate"/>
			<div class="add-on" style="height:24px;"><i class="icon-calendar"></i></div> 
		</div>
		
		<input type="hidden" id="userID" ng-model="userId" value="${user.id}">
		<input type="hidden" id="dateID" ng-model="dateStr" value="${dateStr}">
		<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" id="showPopup" style="display: none;">
		  Launch demo modal
		</button>
		<div id="int_doctor_view">
		<h4>{{currentDateObject|date:'fullDate'}}</h4>
			<div class="col-md-12 col-sm-12" id="int_doctor_view">
				<div id="scheduler-wrapper" style="height: 454px;">
					<div id="scheduler-container" ng-today-scheduler data="data"></div>
				</div>
			</div>
		</div>
		
</body>

<!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" id="modalClose" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Add Task</h4>
		      </div>
		      <div class="modal-body">
		        
					<div class="control-group" style="float: left; width: 46%; margin: 0px; height: 75px;">
						<label class="control-label" for="textinput">Start time
							<sup style="color: red"> *</sup>
						</label>
							<div class="controls">
								<input type="text" name="startTime" ng-model="editStartTime" class="input-large" readonly="readonly" style="width:60%;">
							</div>
					</div>
					<div class="control-group" style="float: left; width: 46%; margin: 0px; height: 75px;">
						<label class="control-label" for="textinput">End time
							<sup style="color: red"> *</sup>
						</label>
							<div class="controls">
								<input type="text" name="endTime" ng-model="editEndTime" class="input-large" readonly="readonly" style="width:60%;">
							</div>
					</div>
					
					<div class="control-group" style="float: left; width: 46%; margin: 0px; height: 75px;">
						<label class="control-label" for="textinput">Project code
							<sup style="color: red"> *</sup>
						</label>
							<div class="controls">
								<select class="largeInput largeInputFirst required" ng-model="projectCode" ng-change="setTaskOfProject(projectCode)">
									<option ng-repeat="project in projectList" ng-selected="projectCode == project.id" value="{{project.id}}">{{project.projectCode}}</option>
								</select> <span class="help-inline"></span> <span class="help-block"></span>
							</div>
					</div>
					
					<div class="control-group" style="float: left; width: 46%; margin: 0px; height: 75px;">
						<label class="control-label" for="textinput">Task code
							<sup style="color: red"> *</sup>
						</label>
							<div class="controls">
										<select id="timesheetRows_1__taskCode"
											class="largeInput taskInput" ng-model="taskCode">
											<option ng-repeat="task in taskList" ng-selected="taskCode == task.id" value="{{task.id}}">{{task.taskCode}}</option>
										</select>
							</div>
					</div>
      </div>
    </div>
  </div>
</div> -->
</html>

<style>
.date-arrow .cal13 {
  margin: 6px 0 0 10px;
}

.cal13 {
  background: url(../images/sprite.png) no-repeat 0 0;
  float: left;
  width: 13px;
  height: 13px;
}

</style>