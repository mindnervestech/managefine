<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html ng-app="timeSheetApp">
<head>
<link rel="stylesheet" media="screen"
	href='<c:url value="/resources/customCSS/customTimesheet.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/main-syle.css"/>'>	
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/form-element-style.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/ngDialog.min.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/ngDialog-theme-default.min.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/customCSS/ngDialogNew.css"/>'>

	
	
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ngMask.min.js"/>'></script>

<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/timesheetController/controller.js"/>'></script>
</head>
<body ng-controller="SetupHolidayController" ng-init="getTimesheetData(${asJson})">
<jsp:include page="menuContext.jsp" />
<input type="hidden" id="userID" ng-model="userId" value="${user.id}">
<section class="full-container inner-content" style="min-height: 1040px;" data-ng-init='initData(${asJson})'>
	<section class="inner-content-full">
		<h2 class="left">Mark Weekly Off</h2>
		<section class="w100p">
			<table class="cal-table w-off">
				<tr>
					<th>
						<section class="field">
							<span ng-class="weeks[0]==true ?'cbox-selected' :'cbox'" ng-click="checkBoxClick(0)"> <input name=""
								type="checkbox" ng-model="weeks[0]" value=""/>
							</span>
						</section> SUNDAY
					</th>
					<th><section class="field">
							<span ng-class="weeks[1]==true ?'cbox-selected' :'cbox'" ng-click="checkBoxClick(1)"> <input name=""
								type="checkbox" value="" ng-model="weeks[1]" />
							</span>
						</section> MONDAY</th>
					<th><section class="field">
							<span ng-class="weeks[2]==true ?'cbox-selected' :'cbox'" ng-click="checkBoxClick(2)"> <input name=""
								type="checkbox" value="" ng-model="weeks[2]" />
							</span>
						</section> TUESDAY</th>
					<th><section class="field">
							<span ng-class="weeks[3]==true ?'cbox-selected' :'cbox'" ng-click="checkBoxClick(3)"> <input name=""
								type="checkbox" value="" ng-model="weeks[3]" />
							</span>
						</section> WEDNESDAY</th>
					<th><section class="field">
							<span ng-class="weeks[4]==true ?'cbox-selected' :'cbox'" ng-click="checkBoxClick(4)"> <input name="" type="checkbox"
								value="" ng-model="weeks[4]" />
							</span>
						</section> THURSDAY</th>
					<th><section class="field">
							<span ng-class="weeks[5]==true ?'cbox-selected' :'cbox'" ng-click="checkBoxClick(5)"> <input name=""
								type="checkbox" value="" ng-model="weeks[5]" />
							</span>
						</section> FRIDAY</th>
					<th class="last"><section class="field">
							<span ng-class="weeks[6]==true ?'cbox-selected' :'cbox'" ng-click="checkBoxClick(6)"> <input name=""
								type="checkbox" value="" ng-model="weeks[6]" />
							</span>
						</section> SATURDAY</th>
				</tr>
			</table>
		</section>
		<section class="holiday-cal">
		<aside ng-repeat="month in months" class="month-cal" style="min-height: 286px;">
			<span class="month-cal-head">{{month.monthName}}</span>
				<aside class="month-cal-tbl">
					<table>
						<tr>
							<th>S</th>
							<th>M</th>
							<th>T</th>
							<th>W</th>
							<th>T</th>
							<th>F</th>
							<th>S</th>
						</tr>
						<tr>
							<td ng-hide="month.monthDays[0].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[0].day)">{{month.monthDays[0].day}}</td>
							<td ng-show="month.monthDays[0].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[0].reason}}">{{month.monthDays[0].day}}</a></td>
							<td ng-hide="month.monthDays[1].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[1].day)">{{month.monthDays[1].day}}</td>
							<td ng-show="month.monthDays[1].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[1].reason}}">{{month.monthDays[1].day}}</a></td>
							<td ng-hide="month.monthDays[2].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[2].day)">{{month.monthDays[2].day}}</td>
							<td ng-show="month.monthDays[2].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[2].reason}}">{{month.monthDays[2].day}}</a></td>
							<td ng-hide="month.monthDays[3].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[3].day)">{{month.monthDays[3].day}}</td>
							<td ng-show="month.monthDays[3].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[3].reason}}">{{month.monthDays[3].day}}</a></td>
							<td ng-hide="month.monthDays[4].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[4].day)">{{month.monthDays[4].day}}</td>
							<td ng-show="month.monthDays[4].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[4].reason}}">{{month.monthDays[4].day}}</a></td>
							<td ng-hide="month.monthDays[5].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[5].day)">{{month.monthDays[5].day}}</td>
							<td ng-show="month.monthDays[5].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[5].reason}}">{{month.monthDays[5].day}}</a></td>
							<td ng-hide="month.monthDays[6].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[6].day)">{{month.monthDays[6].day}}</td>
							<td ng-show="month.monthDays[6].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[6].reason}}">{{month.monthDays[6].day}}</a></td>
						</tr>
						<tr>
							<td ng-hide="month.monthDays[7].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[7].day)">{{month.monthDays[7].day}}</td>
							<td ng-show="month.monthDays[7].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[7].reason}}">{{month.monthDays[7].day}}</a></td>
							<td ng-hide="month.monthDays[8].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[8].day)">{{month.monthDays[8].day}}</td>
							<td ng-show="month.monthDays[8].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[8].reason}}">{{month.monthDays[8].day}}</a></td>
							<td ng-hide="month.monthDays[9].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[9].day)">{{month.monthDays[9].day}}</td>
							<td ng-show="month.monthDays[9].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[9].reason}}">{{month.monthDays[9].day}}</a></td>
							<td ng-hide="month.monthDays[10].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[10].day)">{{month.monthDays[10].day}}</td>
							<td ng-show="month.monthDays[10].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[10].reason}}">{{month.monthDays[10].day}}</a></td>
							<td ng-hide="month.monthDays[11].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[11].day)">{{month.monthDays[11].day}}</td>
							<td ng-show="month.monthDays[11].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[11].reason}}">{{month.monthDays[11].day}}</a></td>
							<td ng-hide="month.monthDays[12].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[12].day)">{{month.monthDays[12].day}}</td>
							<td ng-show="month.monthDays[12].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[12].reason}}">{{month.monthDays[12].day}}</a></td>
							<td ng-hide="month.monthDays[13].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[13].day)">{{month.monthDays[13].day}}</td>
							<td ng-show="month.monthDays[13].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[13].reason}}">{{month.monthDays[13].day}}</a></td>
						</tr>
						<tr>
							<td ng-hide="month.monthDays[14].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[14].day)">{{month.monthDays[14].day}}</td>
							<td ng-show="month.monthDays[14].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[14].reason}}">{{month.monthDays[14].day}}</a></td>
							<td ng-hide="month.monthDays[15].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[15].day)">{{month.monthDays[15].day}}</td>
							<td ng-show="month.monthDays[15].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[15].reason}}">{{month.monthDays[15].day}}</a></td>
							<td ng-hide="month.monthDays[16].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[16].day)">{{month.monthDays[16].day}}</td>
							<td ng-show="month.monthDays[16].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[16].reason}}">{{month.monthDays[16].day}}</a></td>
							<td ng-hide="month.monthDays[17].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[17].day)">{{month.monthDays[17].day}}</td>
							<td ng-show="month.monthDays[17].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[17].reason}}">{{month.monthDays[17].day}}</a></td>
							<td ng-hide="month.monthDays[18].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[18].day)">{{month.monthDays[18].day}}</td>
							<td ng-show="month.monthDays[18].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[18].reason}}">{{month.monthDays[18].day}}</a></td>
							<td ng-hide="month.monthDays[19].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[19].day)">{{month.monthDays[19].day}}</td>
							<td ng-show="month.monthDays[19].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[19].reason}}">{{month.monthDays[19].day}}</a></td>
							<td ng-hide="month.monthDays[20].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[20].day)">{{month.monthDays[20].day}}</td>
							<td ng-show="month.monthDays[20].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[20].reason}}">{{month.monthDays[20].day}}</a></td>
						</tr>
						<tr>
							<td ng-hide="month.monthDays[21].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[21].day)">{{month.monthDays[21].day}}</td>
							<td ng-show="month.monthDays[21].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[21].reason}}">{{month.monthDays[21].day}}</a></td>
							<td ng-hide="month.monthDays[22].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[22].day)">{{month.monthDays[22].day}}</td>
							<td ng-show="month.monthDays[22].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[22].reason}}">{{month.monthDays[22].day}}</a></td>
							<td ng-hide="month.monthDays[23].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[23].day)">{{month.monthDays[23].day}}</td>
							<td ng-show="month.monthDays[23].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[23].reason}}">{{month.monthDays[23].day}}</a></td>
							<td ng-hide="month.monthDays[24].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[24].day)">{{month.monthDays[24].day}}</td>
							<td ng-show="month.monthDays[24].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[24].reason}}">{{month.monthDays[24].day}}</a></td>
							<td ng-hide="month.monthDays[25].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[25].day)">{{month.monthDays[25].day}}</td>
							<td ng-show="month.monthDays[25].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[25].reason}}">{{month.monthDays[25].day}}</a></td>
							<td ng-hide="month.monthDays[26].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[26].day)">{{month.monthDays[26].day}}</td>
							<td ng-show="month.monthDays[26].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[26].reason}}">{{month.monthDays[26].day}}</a></td>
							<td ng-hide="month.monthDays[27].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[27].day)">{{month.monthDays[27].day}}</td>
							<td ng-show="month.monthDays[27].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[27].reason}}">{{month.monthDays[27].day}}</a></td>
						</tr>
						<tr>
							<td ng-hide="month.monthDays[28].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[28].day)">{{month.monthDays[28].day}}</td>
							<td ng-show="month.monthDays[28].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[28].reason}}">{{month.monthDays[28].day}}</a></td>
							<td ng-hide="month.monthDays[29].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[29].day)">{{month.monthDays[29].day}}</td>
							<td ng-show="month.monthDays[29].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[29].reason}}">{{month.monthDays[29].day}}</a></td>
							<td ng-hide="month.monthDays[30].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[30].day)">{{month.monthDays[30].day}}</td>
							<td ng-show="month.monthDays[30].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[30].reason}}">{{month.monthDays[30].day}}</a></td>
							<td ng-hide="month.monthDays[31].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[31].day)">{{month.monthDays[31].day}}</td>
							<td ng-show="month.monthDays[31].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[31].reason}}">{{month.monthDays[31].day}}</a></td>
							<td ng-hide="month.monthDays[32].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[32].day)">{{month.monthDays[32].day}}</td>
							<td ng-show="month.monthDays[32].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[32].reason}}">{{month.monthDays[32].day}}</a></td>
							<td ng-hide="month.monthDays[33].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[33].day)">{{month.monthDays[33].day}}</td>
							<td ng-show="month.monthDays[33].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[33].reason}}">{{month.monthDays[33].day}}</a></td>
							<td ng-hide="month.monthDays[34].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[34].day)">{{month.monthDays[34].day}}</td>
							<td ng-show="month.monthDays[34].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[34].reason}}">{{month.monthDays[34].day}}</a></td>
						</tr>
						<tr>
							<td ng-hide="month.monthDays[35].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[35].day)">{{month.monthDays[35].day}}</td>
							<td ng-show="month.monthDays[35].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[35].reason}}">{{month.monthDays[35].day}}</a></td>
							<td ng-hide="month.monthDays[36].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[36].day)">{{month.monthDays[36].day}}</td>
							<td ng-show="month.monthDays[36].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[36].reason}}">{{month.monthDays[36].day}}</a></td>
							<td ng-hide="month.monthDays[37].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[37].day)">{{month.monthDays[37].day}}</td>
							<td ng-show="month.monthDays[37].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[37].reason}}">{{month.monthDays[37].day}}</a></td>
							<td ng-hide="month.monthDays[38].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[38].day)">{{month.monthDays[38].day}}</td>
							<td ng-show="month.monthDays[38].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[38].reason}}">{{month.monthDays[38].day}}</a></td>
							<td ng-hide="month.monthDays[39].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[39].day)">{{month.monthDays[39].day}}</td>
							<td ng-show="month.monthDays[39].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[39].reason}}">{{month.monthDays[39].day}}</a></td>
							<td ng-hide="month.monthDays[40].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[40].day)">{{month.monthDays[40].day}}</td>
							<td ng-show="month.monthDays[40].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[40].reason}}">{{month.monthDays[40].day}}</a></td>
							<td ng-hide="month.monthDays[41].isLeave" ng-click="funct1(month.monthIndex,month.monthDays[41].day)">{{month.monthDays[41].day}}</td>
							<td ng-show="month.monthDays[41].isLeave" ng-click="funct1(month.monthIndex,'')"><a class="w-off tip" title="{{month.monthDays[41].reason}}">{{month.monthDays[41].day}}</a></td>
						</tr>
					</table>
				</aside>
			</aside>
		</section>
	</section>
</section>

</body>
</html>

<script  type="text/ng-template" id="openHolidayPopup.html"> 
<section class="jpopup small" id="markHoliday" style="position:absolute;"> <a class="close-jpopup" data-ng-click="closeThisDialog()">X</a>
  <aside class="jpopup-head"> <span class="phead">Mark Holiday(s)</span>
  </aside>
  <aside class="jpopup-content-out">
    <aside class="jpopup-content" style="width: 450px;margin-top:10%;">
      <aside class="pad5">
        <aside class="markh-top">
          <table>
            <tr ng-hide="true">
              <td>Select Type</td>
              <td><aside class="tab-option"> <a ng-click="selectType('1')" href="javascript:void(0);" ng-class="staffLeaveVM.toDate=='' ? 'active' : ''">Single</a> <a href="javascript:void(0);" ng-click="selectType('2')" ng-class="staffLeaveVM.toDate=='' ? 'callmultiFld' : 'callmultiFld active'">Multiple</a> </aside><section class="error-msg"><span>Error message will come here.</span></section></td>
            </tr>
            <tr>
              <td>Select Date</td>
              <td><aside class="field w150">
                  <input type="text" class="textfield clndr" data-date-autoclose="true" data-provide="datepicker" ng-model="staffLeaveVM.fromDate" style="height:30px;">
                </aside>
                <aside class="field w150 right multiFld" ng-show="staffLeaveVM.toDate!=''">
                  <input type="text" class="textfield clndr" data-date-autoclose="true" data-provide="datepicker" ng-model="staffLeaveVM.toDate" style="height:30px;">
                </aside>
                <section class="error-msg"><span>Error message will come here.</span></section></td>
            </tr>
            <tr>
              <td>Reasons</td>
              <td><aside class="field">
                  <input type="text" class="textfield" ng-model="staffLeaveVM.reason" value="" style="height:30px;">
                </aside>
                <section class="error-msg"><span>Error message will come here.</span></section></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
             <!-- <td><section class="field nomargin-cbox"> <span ng-class="staffLeaveVM.leaveType==8 ? 'cbox-selected' : 'cbox'" ng-click="setleaveType()">
                  <input name="" type="checkbox" checked id="repeatE" />
                  </span>
                  <label for="repeatE" class="checklabel">Repeat Every Year</label>
                </section></td> -->
            </tr>
          </table>
        </aside>
        <aside class="markh-btm">
          <input type="button" class="bluebtn" value="Save" ng-click="saveLeave();closeThisDialog()">
        </aside>
        <aside class="clear"></aside>
      </aside>
    </aside>
  </aside>
</section>
</script>
<script>
$("#datepicker1").datepicker();
$("#datepicker2").datepicker();
</script>
<style>
.jpopup {
	height: 25em;
}
.jpopup .jpopup-content-out {
	height: 20em;
}
</style>
<style type="text/css">
.user-pillbox-div {
	padding-left: 0px;
	padding-right: 0px;
	margin-top: 10px;
}

.jpopup .close-jpopup  {
	right: -13px;
	top: -13px;
}

.ngdialog.ngdialog-theme-default .ngdialog-content {
	min-height: 0px;
	padding: 0px;
}

.ngdialog.ngdialog-theme-default .ngdialog-close {
	display: none;
}

.jpopup {
	position: absolute;
	width: 90%;
	height: 28em;
	left: 25%;
}

.jpopup .jpopup-content {
	width: 100%;
	height: 22em;
}

.jpopup .jpopup-content-out {
	padding: 0px !important;
	height: 22em;
	width: 100%;
}

.container {
width:1230px;
}
</style>

