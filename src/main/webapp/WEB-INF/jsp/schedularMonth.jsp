<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen"
	href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/main-syle.css"/>'>	
	
	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="SchedularMonthController" ng-init="getTimesheetData(${asJson})">
<jsp:include page="menuContext.jsp" />
<input type="hidden" id="userID" ng-model="userId" value="${user.id}">
<section class="full-container inner-content" style="min-height: 786px;" data-ng-init='initMonthSchedule(${asJson})'>
	<section class="inner-content-full">
		<h2 class="left">Month</h2>
		
			<span><input style="height: 22px;width: 120px;margin-left:1%;margin-top:1%;float:left;" ng-change="getMonthAppointment()" ng-model="currentDateMonth" data-date-autoclose="true" data-provide="datepicker" data-date-min-view-mode="months" data-date-format="M yyyy">
			<div class="add-on" style="height:24px;margin-top:1%;"><i class="icon-calendar"></i></div>
			</span>
		
		<section class="w100p">
			<table class="cal-table">
				<tr>
					<th>SUNDAY</th>
					<th>MONDAY</th>
					<th>TUESDAY</th>
					<th>WEDNESDAY</th>
					<th>THURSDAY</th>
					<th>FRIDAY</th>
					<th class="last">SATURDAY</th>
				</tr>
				<tr>
					<td ng-if="!monthVM.days[0].assigned">{{monthVM.days[0].day}}</td>
					<td class="current" ng-if="monthVM.days[0].assigned"><em>{{monthVM.days[0].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[0].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[0].day,monthVM.year)"><i></i>
							{{monthVM.days[0].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[1].assigned">{{monthVM.days[1].day}}</td>
					<td class="current" ng-if="monthVM.days[1].assigned"><em>{{monthVM.days[1].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[1].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[1].day,monthVM.year)"><i></i>
							{{monthVM.days[1].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[2].assigned">{{monthVM.days[2].day}}</td>
					<td class="current" ng-class="monthVM.days[1].appoinmentCount==2?'current full':monthVM.days[1].appoinmentCount==1?'current almost':'current'" ng-if="monthVM.days[2].assigned"><em>{{monthVM.days[2].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[2].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[2].day,monthVM.year)"><i></i>
							{{monthVM.days[2].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[3].assigned">{{monthVM.days[3].day}}</td>
					<td class="current" ng-if="monthVM.days[3].assigned"><em>{{monthVM.days[3].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[3].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[3].day,monthVM.year)"><i></i>
							{{monthVM.days[3].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[4].assigned">{{monthVM.days[4].day}}</td>
					<td class="current" ng-if="monthVM.days[4].assigned"><em>{{monthVM.days[4].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[4].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[4].day,monthVM.year)"><i></i>
							{{monthVM.days[4].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[5].assigned">{{monthVM.days[5].day}}</td>
					<td class="current" ng-if="monthVM.days[5].assigned"><em>{{monthVM.days[5].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[5].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[5].day,monthVM.year)"><i></i>
							{{monthVM.days[5].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[6].assigned">{{monthVM.days[6].day}}</td>
					<td class="current" ng-if="monthVM.days[6].assigned"><em>{{monthVM.days[6].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[6].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[6].day,monthVM.year)"><i></i>
							{{monthVM.days[6].appoinmentCount}} Tasks</span></td>
				</tr>
				<tr>
					<td ng-if="!monthVM.days[7].assigned">{{monthVM.days[7].day}}</td>
					<td class="current" ng-if="monthVM.days[7].assigned"><em>{{monthVM.days[7].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[7].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[7].day,monthVM.year)"><i></i>
							{{monthVM.days[7].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[8].assigned">{{monthVM.days[8].day}}</td>
					<td class="current" ng-if="monthVM.days[8].assigned"><em>{{monthVM.days[8].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[8].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[8].day,monthVM.year)"><i></i>
							{{monthVM.days[8].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[9].assigned">{{monthVM.days[9].day}}</td>
					<td class="current" ng-if="monthVM.days[9].assigned"><em>{{monthVM.days[9].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[9].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[9].day,monthVM.year)"><i></i>
							{{monthVM.days[9].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[10].assigned">{{monthVM.days[10].day}}</td>
					<td class="current" ng-if="monthVM.days[10].assigned"><em>{{monthVM.days[10].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[10].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[10].day,monthVM.year)"><i></i>
							{{monthVM.days[10].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[11].assigned">{{monthVM.days[11].day}}</td>
					<td class="current" ng-if="monthVM.days[11].assigned"><em>{{monthVM.days[11].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[11].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[11].day,monthVM.year)"><i></i>
							{{monthVM.days[11].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[12].assigned">{{monthVM.days[12].day}}</td>
					<td class="current" ng-if="monthVM.days[12].assigned"><em>{{monthVM.days[12].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[12].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[12].day,monthVM.year)"><i></i>
							{{monthVM.days[12].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[13].assigned">{{monthVM.days[13].day}}</td>
					<td class="current" ng-if="monthVM.days[13].assigned"><em>{{monthVM.days[13].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[13].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[13].day,monthVM.year)"><i></i>
							{{monthVM.days[13].appoinmentCount}} Tasks</span></td>
				</tr>
				<tr>
					<td ng-if="!monthVM.days[14].assigned">{{monthVM.days[14].day}}</td>
					<td class="current" ng-if="monthVM.days[14].assigned"><em>{{monthVM.days[14].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[14].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[14].day,monthVM.year)"><i></i>
							{{monthVM.days[14].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[15].assigned">{{monthVM.days[15].day}}</td>
					<td class="current" ng-if="monthVM.days[15].assigned"><em>{{monthVM.days[15].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[15].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[15].day,monthVM.year)"><i></i>
							{{monthVM.days[15].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[16].assigned">{{monthVM.days[16].day}}</td>
					<td class="current" ng-if="monthVM.days[16].assigned"><em>{{monthVM.days[16].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[16].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[16].day,monthVM.year)"><i></i>
							{{monthVM.days[16].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[17].assigned">{{monthVM.days[17].day}}</td>
					<td class="current" ng-if="monthVM.days[17].assigned"><em>{{monthVM.days[17].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[17].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[17].day,monthVM.year)"><i></i>
							{{monthVM.days[17].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[18].assigned">{{monthVM.days[18].day}}</td>
					<td class="current" ng-if="monthVM.days[18].assigned"><em>{{monthVM.days[18].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[18].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[18].day,monthVM.year)"><i></i>
							{{monthVM.days[18].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[19].assigned">{{monthVM.days[19].day}}</td>
					<td class="current" ng-if="monthVM.days[19].assigned"><em>{{monthVM.days[19].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[19].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[19].day,monthVM.year)"><i></i>
							{{monthVM.days[19].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[20].assigned">{{monthVM.days[20].day}}</td>
					<td class="current" ng-if="monthVM.days[20].assigned"><em>{{monthVM.days[20].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[20].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[20].day,monthVM.year)"><i></i>
							{{monthVM.days[20].appoinmentCount}} Tasks</span></td>
				</tr>
				<tr>
					<td ng-if="!monthVM.days[21].assigned">{{monthVM.days[21].day}}</td>
					<td class="current" ng-if="monthVM.days[21].assigned"><em>{{monthVM.days[21].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[21].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[21].day,monthVM.year)"><i></i>
							{{monthVM.days[21].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[22].assigned">{{monthVM.days[22].day}}</td>
					<td class="current" ng-if="monthVM.days[22].assigned"><em>{{monthVM.days[22].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[22].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[22].day,monthVM.year)"><i></i>
							{{monthVM.days[22].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[23].assigned">{{monthVM.days[23].day}}</td>
					<td class="current" ng-if="monthVM.days[23].assigned"><em>{{monthVM.days[23].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[23].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[23].day,monthVM.year)"><i></i>
							{{monthVM.days[23].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[24].assigned">{{monthVM.days[24].day}}</td>
					<td class="current" ng-if="monthVM.days[24].assigned"><em>{{monthVM.days[24].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[24].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[24].day,monthVM.year)"><i></i>
							{{monthVM.days[24].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[25].assigned">{{monthVM.days[25].day}}</td>
					<td class="current" ng-if="monthVM.days[25].assigned"><em>{{monthVM.days[25].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[25].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[25].day,monthVM.year)"><i></i>
							{{monthVM.days[25].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[26].assigned">{{monthVM.days[26].day}}</td>
					<td class="current" ng-if="monthVM.days[26].assigned"><em>{{monthVM.days[26].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[26].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[26].day,monthVM.year)"><i></i>
							{{monthVM.days[26].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[27].assigned">{{monthVM.days[27].day}}</td>
					<td class="current" ng-if="monthVM.days[27].assigned"><em>{{monthVM.days[27].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[27].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[27].day,monthVM.year)"><i></i>
							{{monthVM.days[27].appoinmentCount}} Tasks</span></td>
				</tr>
				<tr ng-if="monthVM.days[28].assigned">
					<td ng-if="!monthVM.days[28].assigned">{{monthVM.days[28].day}}</td>
					<td class="current" ng-if="monthVM.days[28].assigned"><em>{{monthVM.days[28].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[28].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[28].day,monthVM.year)"><i></i>
							{{monthVM.days[28].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[29].assigned">{{monthVM.days[29].day}}</td>
					<td class="current" ng-if="monthVM.days[29].assigned"><em>{{monthVM.days[29].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[29].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[29].day,monthVM.year)"><i></i>
							{{monthVM.days[29].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[30].assigned">{{monthVM.days[30].day}}</td>
					<td class="current" ng-if="monthVM.days[30].assigned"><em>{{monthVM.days[30].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[30].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[30].day,monthVM.year)"><i></i>
							{{monthVM.days[30].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[31].assigned">{{monthVM.days[31].day}}</td>
					<td class="current" ng-if="monthVM.days[31].assigned"><em>{{monthVM.days[31].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[31].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[31].day,monthVM.year)"><i></i>
							{{monthVM.days[31].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[32].assigned">{{monthVM.days[32].day}}</td>
					<td class="current" ng-if="monthVM.days[32].assigned"><em>{{monthVM.days[32].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[32].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[32].day,monthVM.year)"><i></i>
							{{monthVM.days[32].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[33].assigned">{{monthVM.days[33].day}}</td>
					<td class="current" ng-if="monthVM.days[33].assigned"><em>{{monthVM.days[33].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[33].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[33].day,monthVM.year)"><i></i>
							{{monthVM.days[33].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[34].assigned">{{monthVM.days[34].day}}</td>
					<td class="current" ng-if="monthVM.days[34].assigned"><em>{{monthVM.days[34].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[34].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[34].day,monthVM.year)"><i></i>
							{{monthVM.days[34].appoinmentCount}} Tasks</span></td>
				</tr>
				<tr ng-if="monthVM.days[35].assigned">
					<td ng-if="!monthVM.days[35].assigned">{{monthVM.days[35].day}}</td>
					<td class="current" ng-if="monthVM.days[35].assigned"><em>{{monthVM.days[35].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[35].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[35].day,monthVM.year)"><i></i>
							{{monthVM.days[35].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[36].assigned">{{monthVM.days[36].day}}</td>
					<td class="current" ng-if="monthVM.days[36].assigned"><em>{{monthVM.days[36].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[36].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[36].day,monthVM.year)"><i></i>
							{{monthVM.days[36].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[37].assigned">{{monthVM.days[37].day}}</td>
					<td class="current" ng-if="monthVM.days[37].assigned"><em>{{monthVM.days[37].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[37].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[37].day,monthVM.year)"><i></i>
							{{monthVM.days[37].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[38].assigned">{{monthVM.days[38].day}}</td>
					<td class="current" ng-if="monthVM.days[38].assigned"><em>{{monthVM.days[38].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[38].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[38].day,monthVM.year)"><i></i>
							{{monthVM.days[38].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[39].assigned">{{monthVM.days[39].day}}</td>
					<td class="current" ng-if="monthVM.days[39].assigned"><em>{{monthVM.days[39].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[39].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[39].day,monthVM.year)"><i></i>
							{{monthVM.days[39].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[40].assigned">{{monthVM.days[40].day}}</td>
					<td class="current" ng-if="monthVM.days[40].assigned"><em>{{monthVM.days[40].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[40].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[40].day,monthVM.year)"><i></i>
							{{monthVM.days[40].appoinmentCount}} Tasks</span></td>
					<td ng-if="!monthVM.days[41].assigned">{{monthVM.days[41].day}}</td>
					<td class="current" ng-if="monthVM.days[41].assigned"><em>{{monthVM.days[41].day}}</em><a
						ng-href="${pageContext.request.contextPath}/{{(monthVM.monthIndex+1)}}-{{monthVM.days[41].day}}-{{monthVM.year}}/{{userId}}" class="bookapp tip"
						title="Day View"></a> <span
						ng-click="getDateAppointment(monthVM.monthIndex+1,monthVM.days[41].day,monthVM.year)"><i></i>
							{{monthVM.days[41].appoinmentCount}} Tasks</span></td>
				</tr>
			</table>
		</section>
	</section>
</section>

</body>
</html>
<style>
.container {
width:1230px;
}

.ui-pnotify-history-container.well {
top: -130.3125px;
}
</style>
