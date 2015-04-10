

app.controller("TimeSheetController", function($scope,$http) {
  
	$scope.timesheetData = [];
	$scope.projectList = [];
	$scope.taskList = [];
	
	$scope.getTimesheetData = function(data) {
		$scope.getUserProjects();
		$scope.getTimesheetByWeek();
		
		var startOfWeek;
		Date.prototype.getWeek = function() {
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
			$scope.getByWeek(ev.date.getWeek()+1,ev.date.getFullYear());
			$scope.weekOfYear = ev.date.getWeek()+1;
			$scope.year = ev.date.getFullYear();
			console.log(startOfWeek);
		});
		
		var today = new Date();
		var todaysWeek = today.getWeek() + 1;
		$("#weekValue").val(today.getWeek() + 1);
		$("#yearValue").val(today.getFullYear());
		var day =   today.getDay() - 1;
		startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
		var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
		
		$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
				$.datepicker.formatDate('dd M yy', endOfWeek));
		
		
	}
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		if($scope.timesheetStatus == "Submitted") {
			$("#timeSheetTable :input").attr("readonly","readonly");
			$("#timeSheetTable select").attr("disabled","disabled");
			$("input:checkbox").attr("disabled","disabled");
			$("#copyFromLastWeek").attr("disabled","disabled");
			$("#saveTimesheetForm").attr("disabled","disabled");
			$("#submitTimesheetForm").attr("disabled","disabled");
			$("#retractTimesheetForm").removeAttr("disabled","disabled");
		} else {
			$("#timeSheetTable :input").removeAttr("readonly","readonly");
			$("#timeSheetTable select").removeAttr("disabled","disabled");
			$("input:checkbox").removeAttr("disabled","disabled");
			$("#copyFromLastWeek").removeAttr("disabled","disabled");
			$("#saveTimesheetForm").removeAttr("disabled","disabled");
			$("#submitTimesheetForm").removeAttr("disabled","disabled");
			$("#retractTimesheetForm").attr("disabled","disabled");
		}
	});
	
	$scope.getUserProjects = function() {
		$scope.userId = $('#employeeID').val();
		$http({method:'GET',url:'getProjectCodes',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.projectList = data;
			
		});
		
	}
	
	$scope.getByWeek = function(week,year) {
		
		$("#timeSheetTable :input").removeAttr("readonly","readonly");
		$("#timeSheetTable select").removeAttr("disabled","disabled");
		$("input:checkbox").removeAttr("disabled","disabled");
		$("#copyFromLastWeek").removeAttr("disabled","disabled");
		$("#saveTimesheetForm").removeAttr("disabled","disabled");
		$("#submitTimesheetForm").removeAttr("disabled","disabled");
		
		$http({method:'GET',url:'getTimesheetBySelectedWeek',params:{userId:$scope.userId,week:week,year:year}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			if(data.id != null) {
				$scope.weekOfYear = data.weekOfYear;
				$scope.year = data.year;
				$scope.timesheetStatus = data.status;
				$scope.timesheetId = data.id;
				$scope.timesheetData = data.timesheetRows;
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode);
				}
				
				if(data.status == "Submitted") {
					$("#timeSheetTable :input").attr("readonly","readonly");
					$("#timeSheetTable select").attr("disabled","disabled");
					$("input:checkbox").attr("disabled","disabled");
					$("#copyFromLastWeek").attr("disabled","disabled");
					$("#saveTimesheetForm").attr("disabled","disabled");
					$("#submitTimesheetForm").attr("disabled","disabled");
					$("#retractTimesheetForm").removeAttr("disabled","disabled");
				} else {
					$("#retractTimesheetForm").attr("disabled","disabled");
				}
				
				
			} else {
				$scope.timesheetData =[];
				$scope.addMore();
				$scope.timesheetStatus = "";
				$scope.timesheetId = null;
			}
		});	
	}
	
	$scope.getTimesheetByWeek = function() {
		$http({method:'GET',url:'getTimesheetByCurrentWeek',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			if(data.id != null) {
				$scope.weekOfYear = data.weekOfYear;
				$scope.year = data.year;
				$scope.timesheetStatus = data.status;
				$scope.timesheetId = data.id;
				$scope.timesheetData = data.timesheetRows;
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode);
				}
				
			} else {
				$scope.addMore();
			}
			
		});
	}
	
	$scope.copyFromLastWeek = function() {
		
		if (confirm("Are you sure to copy from last week?") == true) {
		
			
			$http({method:'GET',url:'getTimesheetByLastWeek',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				console.log('success');
				console.log(data);
				if(data.id != null) {
					$scope.weekOfYear = data.weekOfYear;
					$scope.year = data.year;
					$scope.timesheetStatus = data.status;
					
					$scope.timesheetData = data.timesheetRows;
					
					if(!angular.isUndefined($scope.timesheetId) || $scope.timesheetId != null) {
						$http({method:'GET',url:'deleteTimesheet',params:{timesheetId:$scope.timesheetId}})
						.success(function(data) {
							console.log('success');
						});
					}
				} else {
					alert("No data found for last week");
				}
				
				
				
			});
		
		}
		
	}
	
	
	$scope.addMore = function() {
		$scope.timesheetData.push({});
    }
	
	$scope.removeRow = function(index,rowId) {
		if (confirm("Are you sure to delete?") == true) {
			if(!angular.isUndefined(rowId) && rowId != 0) {
					
					$http({method:'GET',url:'deleteTimesheetRow',params:{rowId:rowId}})
					.success(function(data) {
						console.log('success');
					});
					
				}
			$scope.timesheetData.splice(index,1);
		} 
	}
	
	$scope.retractTimesheet = function() {
		$scope.userId = $('#employeeID').val();
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		if (confirm("Are you sure to retract?") == true) {
			$http({method:'GET',url:'timesheetRetract',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				console.log('success');
				$("#timeSheetTable :input").removeAttr("readonly","readonly");
				$("#timeSheetTable select").removeAttr("disabled","disabled");
				$("input:checkbox").removeAttr("disabled","disabled");
				$("#copyFromLastWeek").removeAttr("disabled","disabled");
				$("#saveTimesheetForm").removeAttr("disabled","disabled");
				$("#submitTimesheetForm").removeAttr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
			});
		}
	}
	
	
	$scope.setTaskOfProject = function(projectId) {
		console.log(projectId);
		for(var i=0;i<$scope.projectList.length;i++) {
			if($scope.projectList[i].id == projectId) {
				$scope.taskList = $scope.projectList[i].tasklist;
			}
		}
	}
	
	$scope.saveTimesheet = function(status) {
		
		for(var i=0;i<$scope.timesheetData.length;i++) {
			if(angular.isUndefined($scope.timesheetData[i].monFrom) || $scope.timesheetData[i].monFrom == null) {
				$scope.timesheetData[i].monFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].monTo) || $scope.timesheetData[i].monTo == null) {
				$scope.timesheetData[i].monTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].tueFrom) || $scope.timesheetData[i].tueFrom == null) {
				$scope.timesheetData[i].tueFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].tueTo) || $scope.timesheetData[i].tueTo == null) {
				$scope.timesheetData[i].tueTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].wedFrom) || $scope.timesheetData[i].wedFrom == null) {
				$scope.timesheetData[i].wedFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].wedTo) || $scope.timesheetData[i].wedTo == null) {
				$scope.timesheetData[i].wedTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].thuFrom) || $scope.timesheetData[i].thuFrom == null) {
				$scope.timesheetData[i].thuFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].thuTo) || $scope.timesheetData[i].thuTo == null) {
				$scope.timesheetData[i].thuTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].friFrom) || $scope.timesheetData[i].friFrom == null) {
				$scope.timesheetData[i].friFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].friTo) || $scope.timesheetData[i].friTo == null) {
				$scope.timesheetData[i].friTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].satFrom) || $scope.timesheetData[i].satFrom == null) {
				$scope.timesheetData[i].satFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].satTo) || $scope.timesheetData[i].satTo == null) {
				$scope.timesheetData[i].satTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].sunFrom) || $scope.timesheetData[i].sunFrom == null) {
				$scope.timesheetData[i].sunFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].sunTo) || $scope.timesheetData[i].sunTo == null) {
				$scope.timesheetData[i].sunTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].isOverTime)) {
				$scope.timesheetData[i].isOverTime = false;
			}
			
			if(angular.isUndefined($scope.timesheetData[i].rowId) || $scope.timesheetData[i].rowId == null) {
				var value = 0;
				$scope.timesheetData[i].rowId = value;
			}
			
		}
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		$scope.timesheet = {
				userId:$scope.userId,
				status:status,
				weekOfYear:$scope.weekOfYear,
				year: $scope.year,
				timesheetRows: $scope.timesheetData	
		}
		console.log($scope.timesheet);
		$http({method:'POST',url:'saveTimesheet',data:$scope.timesheet}).success(function(data) {
			console.log('success');
			if($scope.timesheet.status == "Submitted") {
				$("#timeSheetTable :input").attr("readonly","readonly");
				$("#timeSheetTable select").attr("disabled","disabled");
				$("input:checkbox").attr("disabled","disabled");
				$("#copyFromLastWeek").attr("disabled","disabled");
				$("#saveTimesheetForm").attr("disabled","disabled");
				$("#submitTimesheetForm").attr("disabled","disabled");
				$("#retractTimesheetForm").removeAttr("disabled","disabled");
			} else {
				$("#retractTimesheetForm").attr("disabled","disabled");
			}
		});
		
	}
	
});


app.controller("SchedularTodayController", function($scope,$http) {
	
	$scope.editStartTime ="";
	$scope.editEndTime = "";
	//$scope.myString = [{"id":null,"startTime":"12:0","endTime":"13:0","staffId":null,"customerId":null,"visitType":"Break Time!","appoitmentDate":"25-03-2015","notes":"Break Time!","color":"#ff0000","type":"Lu","workOrderId":null,"workOrderDetailId":null}];
	//$scope.data.data = JSON.stringify($scope.myString);
	
	
	$scope.currentDateObject = new Date();
	
	$scope.unitValue = 60;
	$scope.gradationBetweenPerUnit = 15;
	$scope.gradationBetweenPerUnitpx = 20;
	$scope.startTime = 15;
	$scope.endTime = 24;
	$scope.isManage = true;
	$scope.offsetVal = ($scope.unitValue/$scope.gradationBetweenPerUnit)*$scope.gradationBetweenPerUnitpx;
	$scope.currentDate = ($scope.currentDateObject.getMonth()+1)+"/"+$scope.currentDateObject.getDate()+"/"+$scope.currentDateObject.getFullYear();
	
	$scope.getSchedulerDay = function(dateString) {
		var temp = dateString.split("/");
		$scope.currentDateObject.setFullYear(parseInt(temp[2]),parseInt(temp[0])-1,parseInt(temp[1]));
		$scope.getUserProjects();
		$scope.getSchedulerDataByDate();
	}
	
	
	$scope.getSchedulerDataByDate = function(data) {
		$scope.userId = $('#userID').val();
		$scope.dateStr = $('#dateID').val();
		console.log($scope.userId);
		console.log($scope.dateStr);
		console.log($scope.currentDate);
		console.log(data);
		
		
		if(angular.isUndefined(data)) {
			$http({method:'GET',url:contextPath+'/getSchedularDay',params:{date:$scope.currentDate,userId:$scope.userId}})
			.success(function(data) {
				console.log('success');
				console.log(data);
				
				$scope.myString = JSON.stringify(data);
				$scope.data.data = $scope.myString;
			});
		} else{
			$scope.currentDate = $scope.dateStr;
			var temp = $scope.dateStr.split("/");
			$scope.currentDateObject.setFullYear(parseInt(temp[2]),parseInt(temp[0])-1,parseInt(temp[1]));
			$scope.myString = JSON.stringify(data);
			$scope.data.data = $scope.myString;
		}
	}
	
	$scope.getUserProjects = function() {
		$scope.userId = $('#userID').val();
		$http({method:'GET',url:contextPath+'/getProjectCodes',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.projectList = data;
			
		});
		
	}
	
	$scope.setTaskOfProject = function(projectId) {
		console.log(projectId);
		for(var i=0;i<$scope.projectList.length;i++) {
			if($scope.projectList[i].id == projectId) {
				$scope.taskList = $scope.projectList[i].tasklist;
			}
		}
	}
	
	
	$scope.editFunction = function(e,data) {
		
		console.log(data);
		$scope.projectCode = data.projectId;
		$scope.taskCode = data.taskId;
		$scope.setTaskOfProject($scope.projectCode);
		$scope.editStartTime = data.startTime;
		$scope.editEndTime = data.endTime;
		
		console.log($scope.editStartTime);
		
		$('#showPopup').click();
		e.stopPropagation();
	}
	
	$scope.addFunction = function(e) {
		console.log('add');
	}
	
	$scope.data = {
			unitValueInMin:  $scope.unitValue,
			gradationBetweenPerUnit: $scope.gradationBetweenPerUnit,
			gradationBetweenPerUnitpx:$scope.gradationBetweenPerUnitpx,
			width:'100%',
			startTime:0,
			endTime:24,
			data:JSON.stringify($scope.myString),
			editFunction:$scope.editFunction,
			addFunction:$scope.addFunction,
			showCurrentTime:true,
			date:$scope.currentDateObject,
			vertically:true,
			showRuller:true
		};
	
});

app.controller("SchedularWeekController", function($scope,$http) {
	
	$scope.startTime = "";
	$scope.endTime = "";
	$scope.isEdit = false;
	
	$scope.currentDateObject = new Date();
	$scope.unitValue = 60;
	$scope.gradationBetweenPerUnit = 15;
	$scope.gradationBetweenPerUnitpx = 20;
	$scope.startTime = 15;
	$scope.endTime = 24;
	$scope.isPatientEncounter = false;
	$scope.offsetVal = ($scope.unitValue/$scope.gradationBetweenPerUnit)*$scope.gradationBetweenPerUnitpx;
	$scope.currentDate = ($scope.currentDateObject.getMonth()+1)+"/"+$scope.currentDateObject.getDate()+"/"+$scope.currentDateObject.getFullYear();
	$scope.weeks = [];
	
	Date.prototype.getWeek = function() {
		var onejan = new Date(this.getFullYear(),0,1);
		var w = Math.ceil((((this - onejan) / 86400000) + onejan.getDay())/7);
		if(w>52) {
			w -=52;
		}
		return w;
	};
	
	$scope.drawWeeklyAppointment = function(data) {
		console.log(data);
		$scope.weeks = [{
			data:data
			}];
		var yourData = JSON.stringify($scope.weeks);
		$("#scheduler1").matrixWrapper({
			day:[ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
			widthofday:(100/7)+"%",
			data: yourData,
			year:$scope.currentDateObject.getFullYear(),
			week:$scope.currentDateObject.getWeek(),
			addFunction:$scope.addFunction,
			editFunction:$scope.editFunction
		});
		var hr = new Date().getHours();
		if(hr>1) {
			hr=hr-1;
		}
		$("#doctor-week-schedule").scrollTop(80*hr);
	};
	
	$scope.init = function() {
		console.log($scope.currentDateObject.getFullYear());
		$scope.data = {"0":[],"25/03/2015":[{"id":null,"startTime":"12:0","endTime":"13:0","staffId":null,"customerId":null,"visitType":"Break Time!","appoitmentDate":null,"notes":"Break Time!","color":"#ff0000","type":"Lu","workOrderId":null,"workOrderDetailId":null}],"24/03/2015":[{"id":null,"startTime":"12:0","endTime":"13:0","staffId":null,"customerId":null,"visitType":"Break Time!","appoitmentDate":null,"notes":"Break Time!","color":"#ff0000","type":"Lu","workOrderId":null,"workOrderDetailId":null}],"26/03/2015":[{"id":null,"startTime":"12:0","endTime":"13:0","staffId":null,"customerId":null,"visitType":"Break Time!","appoitmentDate":null,"notes":"Break Time!","color":"#ff0000","type":"Lu","workOrderId":null,"workOrderDetailId":null},{"id":13,"startTime":"02:30","endTime":"03:30","staffId":123,"customerId":null,"visitType":"Pumbling","appoitmentDate":"3/26/2015","notes":"","color":"#ff80c0","type":"A","workOrderId":6,"workOrderDetailId":9}],"22/03/2015":[{"id":null,"startTime":"12:0","endTime":"13:0","staffId":null,"customerId":null,"visitType":"Break Time!","appoitmentDate":null,"notes":"Break Time!","color":"#ff0000","type":"Lu","workOrderId":null,"workOrderDetailId":null}],"28/03/2015":[{"id":null,"startTime":"00:00","endTime":"24:00","staffId":null,"customerId":null,"visitType":"Staff Leave!","appoitmentDate":null,"notes":"Staff Shift not Defined Yet!","color":"#0080c0","type":"L","workOrderId":null,"workOrderDetailId":null}],"27/03/2015":[{"id":null,"startTime":"00:00","endTime":"24:00","staffId":null,"customerId":null,"visitType":"Staff Leave!","appoitmentDate":null,"notes":"Staff Leave!","color":"#00ff40","type":"L","workOrderId":null,"workOrderDetailId":null}],"23/03/2015":[{"id":null,"startTime":"12:0","endTime":"13:0","staffId":null,"customerId":null,"visitType":"Break Time!","appoitmentDate":null,"notes":"Break Time!","color":"#ff0000","type":"Lu","workOrderId":null,"workOrderDetailId":null}]};
		console.log($scope.data);
		$scope.drawWeeklyAppointment($scope.data);
		$scope.getDataByWeek();
	}
	
	$scope.changeWeek = function(date,staff) {
		$scope.currentDateObject = date;
		$scope.selectedStaff = staff;
		$scope.currentDate= (date.getMonth()+1)+'/'+date.getDate()+'/'+date.getFullYear();
		$scope.getDataByWeek();
	};
	
	$scope.getDataByWeek = function() {
		$scope.userId = $('#userID').val();
		console.log($scope.userId);
		$http({method:'GET',url:'getSchedularWeek',params:{date:$scope.currentDate,userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.drawWeeklyAppointment(data);
			
		});
	}
	
	$scope.editFunction = function(e,data) {
		console.log('edit');
		e.stopPropagation();
	}
	
	$scope.addFunction = function(e) {
		console.log('add');
	}
	
	
});

app.controller("SchedularMonthController", function($scope,$http) {
	$scope.dateTime;
	$scope.currentDate;
	$scope.currentDateMonth;
	$scope.month =['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
	
	var date = new Date();
	$scope.currentDateMonth = $scope.month[date.getMonth()]+' '+date.getFullYear();
	
	$scope.initMonthSchedule = function(data) {
		$scope.monthVM = {"year":2015,"monthName":"Mar 2015","days":[{"day":"1","assigned":true,"appoinmentCount":0},{"day":"2","assigned":true,"appoinmentCount":0},{"day":"3","assigned":true,"appoinmentCount":0},{"day":"4","assigned":true,"appoinmentCount":0},{"day":"5","assigned":true,"appoinmentCount":0},{"day":"6","assigned":true,"appoinmentCount":0},{"day":"7","assigned":true,"appoinmentCount":0},{"day":"8","assigned":true,"appoinmentCount":0},{"day":"9","assigned":true,"appoinmentCount":0},{"day":"10","assigned":true,"appoinmentCount":0},{"day":"11","assigned":true,"appoinmentCount":0},{"day":"12","assigned":true,"appoinmentCount":0},{"day":"13","assigned":true,"appoinmentCount":0},{"day":"14","assigned":true,"appoinmentCount":0},{"day":"15","assigned":true,"appoinmentCount":0},{"day":"16","assigned":true,"appoinmentCount":0},{"day":"17","assigned":true,"appoinmentCount":0},{"day":"18","assigned":true,"appoinmentCount":0},{"day":"19","assigned":true,"appoinmentCount":0},{"day":"20","assigned":true,"appoinmentCount":0},{"day":"21","assigned":true,"appoinmentCount":0},{"day":"22","assigned":true,"appoinmentCount":0},{"day":"23","assigned":true,"appoinmentCount":0},{"day":"24","assigned":true,"appoinmentCount":0},{"day":"25","assigned":true,"appoinmentCount":1},{"day":"26","assigned":true,"appoinmentCount":0},{"day":"27","assigned":true,"appoinmentCount":0},{"day":"28","assigned":true,"appoinmentCount":0},{"day":"29","assigned":true,"appoinmentCount":0},{"day":"30","assigned":true,"appoinmentCount":0},{"day":"31","assigned":true,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0},{"day":" ","assigned":false,"appoinmentCount":0}],"monthIndex":2};
		console.log($scope.monthVM);
		$scope.userId = $('#userID').val();
		console.log($scope.userId);
		console.log($scope.currentDateMonth);
		$scope.getMonthAppointment();
	};
	
	
	$scope.getMonthAppointment = function() {
		
		$http({method:'GET', url:'getMonthSchedule',params :{date:$scope.currentDateMonth,userId:$scope.userId}}).success(function(response) {
			$scope.monthVM = response.monthVM;
			$scope.currentDateMonth = response.monthVM.monthName;
			console.log('success');
			//console.log(JSON.stringify(response.monthVM));
			console.log($scope.monthVM);
		});
		console.log($scope.currentDateMonth);
	};
	
	
});

app.controller("SetupHolidayController", function($scope,$http,ngDialog) {
	$scope.months;
	$scope.weeks;
	$scope.month =['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
	
	$scope.staffLeaveVM = {
			reason : '',
			userId : $('#userID').val(),
			selectType : '1',
			leaveType : 8,
			toDate : '',
			fromDate : ''
	};
	
	$scope.initData = function() {
		$scope.userId = $('#userID').val();
		console.log($scope.userId);
		$http({method:'GET',url:contextPath+'/getLeaveDetails',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.months = data.leaveList;
			$scope.weeks = data.weekList;
		});
	}
	
	$scope.checkBoxClick = function(index){
		if(confirm("Are you sure you want change this?")) {
			$http({method:'GET', url:contextPath+'/setWeeklyLeave', params :{leaveType:index,userId:$scope.userId}}).success(function(response) {
				console.log('success');
				console.log(response);
				$scope.months = response.leaveList;
				$scope.weeks = response.weekList;
			});
		}
	};
	
	$scope.funct1 = function(_m, _d)  {
		if(_d==0) {
			var d = new Date();
			$scope.staffLeaveVM.fromDate = (d.getMonth()+1)+'/'+d.getDate()+'/'+d.getFullYear();
			 ngDialog.open({
			    template: 'openHolidayPopup.html',
			    scope: $scope,
			    className: 'ngdialog-theme-default'
		  });
		}
		if (_d == '') return;
		var year = new Date().getFullYear();
		if(_m < $scope.months[0].monthIndex){
			year += 1;
		}
		//$scope.setSelectedDate(new Date(year,_m,_d));
		var d = new Date(year,_m,_d);
		$scope.staffLeaveVM.fromDate = (d.getMonth()+1)+'/'+d.getDate()+'/'+d.getFullYear();
		 ngDialog.open({
		    template: 'openHolidayPopup.html',
		    scope: $scope,
		    className: 'ngdialog-theme-default'
	  });
	};
	
	$scope.selectType = function(value) {
		if(value == '2') {
			$scope.staffLeaveVM.toDate = $scope.staffLeaveVM.fromDate;
		} else {
			$scope.staffLeaveVM.toDate = '';
		}
		$scope.staffLeaveVM.selectType = value;
	};
	
	$scope.setleaveType = function(isTypeChecked) {
		if($scope.staffLeaveVM.leaveType == 7) {
			$scope.staffLeaveVM.leaveType = 8;
		} else {
			$scope.staffLeaveVM.leaveType = 7;
		}
		$scope.isTypeChecked = isTypeChecked;
	};
	
	$scope.saveLeave = function() {
		$scope.staffLeaveVM.userId = $scope.userId;
		if($scope.staffLeaveVM.toDate =='') {
			$http({method:'POST', url:contextPath+'/markleaves', data:$scope.staffLeaveVM}).success(function(response) {
				$scope.weeks = response.weekList;
				$scope.months = response.leaveList;
				$scope.staffLeaveVM.fromDate ='';
				$scope.staffLeaveVM.reason = '';
				$scope.staffLeaveVM.selectType = '1';
				closePopDiv('markHoliday');
				//notificationService.success("Leaves saved!");
		    });
		} else {
			if(Date.parse($scope.staffLeaveVM.toDate) > Date.parse($scope.staffLeaveVM.fromDate)) {
				$http({method:'POST', url:contextPath+'/markleaves', data:$scope.staffLeaveVM}).success(function(response) {
					$scope.weeks = response.weekList;
					$scope.months = response.leaveList;
					closePopDiv('markHoliday');
					$scope.staffLeaveVM.fromDate ='';
					$scope.staffLeaveVM.toDate ='';
					$scope.staffLeaveVM.reason = '';
					$scope.staffLeaveVM.selectType = '1';
					//notificationService.success("Leaves saved!");
				});
			} else {
				alert("Invalid Date Range!");
			}
		}
	};
	
	
	//$scope.months = [{"year":2015,"monthName":"APRIL","monthIndex":3,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"MAY","monthIndex":4,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"31","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"JUNE","monthIndex":5,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"JULY","monthIndex":6,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"31","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"AUGUST","monthIndex":7,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"31","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"SEPTEMBER","monthIndex":8,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"OCTOBER","monthIndex":9,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"31","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"NOVEMBER","monthIndex":10,"monthDays":[{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2015,"monthName":"DECEMBER","monthIndex":11,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"31","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2016,"monthName":"JANUARY","monthIndex":0,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"31","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2016,"monthName":"FEBRUARY","monthIndex":1,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"","leaveType":9,"isLeave":false},{"day":"4","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"","leaveType":9,"isLeave":false},{"day":"11","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"","leaveType":9,"isLeave":false},{"day":"18","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"","leaveType":9,"isLeave":false},{"day":"25","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]},{"year":2016,"monthName":"MARCH","monthIndex":2,"monthDays":[{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"1","reason":"","leaveType":9,"isLeave":false},{"day":"2","reason":"","leaveType":9,"isLeave":false},{"day":"3","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"4","reason":"","leaveType":9,"isLeave":false},{"day":"5","reason":"","leaveType":9,"isLeave":false},{"day":"6","reason":"","leaveType":9,"isLeave":false},{"day":"7","reason":"","leaveType":9,"isLeave":false},{"day":"8","reason":"","leaveType":9,"isLeave":false},{"day":"9","reason":"","leaveType":9,"isLeave":false},{"day":"10","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"11","reason":"","leaveType":9,"isLeave":false},{"day":"12","reason":"","leaveType":9,"isLeave":false},{"day":"13","reason":"","leaveType":9,"isLeave":false},{"day":"14","reason":"","leaveType":9,"isLeave":false},{"day":"15","reason":"","leaveType":9,"isLeave":false},{"day":"16","reason":"","leaveType":9,"isLeave":false},{"day":"17","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"18","reason":"","leaveType":9,"isLeave":false},{"day":"19","reason":"","leaveType":9,"isLeave":false},{"day":"20","reason":"","leaveType":9,"isLeave":false},{"day":"21","reason":"","leaveType":9,"isLeave":false},{"day":"22","reason":"","leaveType":9,"isLeave":false},{"day":"23","reason":"","leaveType":9,"isLeave":false},{"day":"24","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"25","reason":"","leaveType":9,"isLeave":false},{"day":"26","reason":"","leaveType":9,"isLeave":false},{"day":"27","reason":"","leaveType":9,"isLeave":false},{"day":"28","reason":"","leaveType":9,"isLeave":false},{"day":"29","reason":"","leaveType":9,"isLeave":false},{"day":"30","reason":"","leaveType":9,"isLeave":false},{"day":"31","reason":"Weekly Leaves","leaveType":4,"isLeave":true},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false},{"day":"","reason":"","leaveType":9,"isLeave":false}]}];
	//$scope.weeks = [false,false,false,false,true,false,false];
	
	
});

app.controller("NewTimeSheetController", function($scope,$http,$compile) {
	  
	$scope.timesheetData = [];
	$scope.projectList = [];
	$scope.taskList = [];
	$scope.weekDayData;
	
	$scope.getTimesheetData = function(data) {
		$scope.getUserProjects();
		$scope.getTimesheetByWeek();
		
		var startOfWeek;
		Date.prototype.getWeek = function() {
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
			$scope.getByWeek(ev.date.getWeek()+1,ev.date.getFullYear());
			$scope.weekOfYear = ev.date.getWeek()+1;
			$scope.year = ev.date.getFullYear();
			console.log(startOfWeek);
		});
		
		var today = new Date();
		var todaysWeek = today.getWeek() + 1;
		$("#weekValue").val(today.getWeek() + 1);
		$("#yearValue").val(today.getFullYear());
		var day =   today.getDay() - 1;
		startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
		var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
		
		$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
				$.datepicker.formatDate('dd M yy', endOfWeek));
		
		
	}
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		if($scope.timesheetStatus == "Submitted") {
			$("#timeSheetTable :input").attr("readonly","readonly");
			$("#timeSheetTable select").attr("disabled","disabled");
			$("input:checkbox").attr("disabled","disabled");
			$("#copyFromLastWeek").attr("disabled","disabled");
			$("#saveTimesheetForm").attr("disabled","disabled");
			$("#submitTimesheetForm").attr("disabled","disabled");
			$("#retractTimesheetForm").removeAttr("disabled","disabled");
		} else {
			$("#timeSheetTable :input").removeAttr("readonly","readonly");
			$("#timeSheetTable select").removeAttr("disabled","disabled");
			$("input:checkbox").removeAttr("disabled","disabled");
			$("#copyFromLastWeek").removeAttr("disabled","disabled");
			$("#saveTimesheetForm").removeAttr("disabled","disabled");
			$("#submitTimesheetForm").removeAttr("disabled","disabled");
			$("#retractTimesheetForm").attr("disabled","disabled");
		}
	});
	
	$scope.getUserProjects = function() {
		$scope.userId = $('#employeeID').val();
		$http({method:'GET',url:'getProjectCodes',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.projectList = data;
			
		});
		
	}
	
	$scope.getByWeek = function(week,year) {
		
		$("#timeSheetTable :input").removeAttr("readonly","readonly");
		$("#timeSheetTable select").removeAttr("disabled","disabled");
		$("input:checkbox").removeAttr("disabled","disabled");
		$("#copyFromLastWeek").removeAttr("disabled","disabled");
		$("#saveTimesheetForm").removeAttr("disabled","disabled");
		$("#submitTimesheetForm").removeAttr("disabled","disabled");
		
		$http({method:'GET',url:'getActualTimesheetBySelectedWeek',params:{userId:$scope.userId,week:week,year:year}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			if(data.id != null) {
				$scope.weekOfYear = data.weekOfYear;
				$scope.year = data.year;
				$scope.timesheetStatus = data.status;
				$scope.timesheetId = data.id;
				$scope.timesheetData = data.timesheetRows;
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode);
				}
				
				if(data.status == "Submitted") {
					$("#timeSheetTable :input").attr("readonly","readonly");
					$("#timeSheetTable select").attr("disabled","disabled");
					$("input:checkbox").attr("disabled","disabled");
					$("#copyFromLastWeek").attr("disabled","disabled");
					$("#saveTimesheetForm").attr("disabled","disabled");
					$("#submitTimesheetForm").attr("disabled","disabled");
					$("#retractTimesheetForm").removeAttr("disabled","disabled");
				} else {
					$("#retractTimesheetForm").attr("disabled","disabled");
				}
				
				
			} else {
				$scope.timesheetData =[];
				$scope.addMore();
				$scope.timesheetStatus = "";
				$scope.timesheetId = null;
			}
		});	
	}
	
	$scope.getTimesheetByWeek = function() {
		$http({method:'GET',url:'getActualTimesheetByCurrentWeek',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			if(data.id != null) {
				$scope.weekOfYear = data.weekOfYear;
				$scope.year = data.year;
				$scope.timesheetStatus = data.status;
				$scope.timesheetId = data.id;
				$scope.timesheetData = data.timesheetRows;
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode);
				}
				
			} else {
				$scope.addMore();
			}
			
		});
	}
	
	$scope.copyFromLastWeek = function() {
		
		if (confirm("Are you sure to copy from last week?") == true) {
		
			
			$http({method:'GET',url:'getActualTimesheetByLastWeek',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				console.log('success');
				console.log(data);
				if(data.id != null) {
					$scope.weekOfYear = data.weekOfYear;
					$scope.year = data.year;
					$scope.timesheetStatus = data.status;
					
					$scope.timesheetData = data.timesheetRows;
					
					if(!angular.isUndefined($scope.timesheetId) || $scope.timesheetId != null) {
						$http({method:'GET',url:'deleteActualTimesheet',params:{timesheetId:$scope.timesheetId}})
						.success(function(data) {
							console.log('success');
						});
					}
				} else {
					alert("No data found for last week");
				}
				
				
				
			});
		
		}
		
	}
	
	
	$scope.addMore = function() {
		$scope.timesheetData.push({});
    }
	
	$scope.removeRow = function(index,rowId) {
		if (confirm("Are you sure to delete?") == true) {
			if(!angular.isUndefined(rowId) && rowId != 0) {
					
					$http({method:'GET',url:'deleteActualTimesheetRow',params:{rowId:rowId}})
					.success(function(data) {
						console.log('success');
					});
					
				}
			$scope.timesheetData.splice(index,1);
		} 
	}
	
	$scope.retractTimesheet = function() {
		$scope.userId = $('#employeeID').val();
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		if (confirm("Are you sure to retract?") == true) {
			$http({method:'GET',url:'actualTimesheetRetract',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				console.log('success');
				$("#timeSheetTable :input").removeAttr("readonly","readonly");
				$("#timeSheetTable select").removeAttr("disabled","disabled");
				$("input:checkbox").removeAttr("disabled","disabled");
				$("#copyFromLastWeek").removeAttr("disabled","disabled");
				$("#saveTimesheetForm").removeAttr("disabled","disabled");
				$("#submitTimesheetForm").removeAttr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
			});
		}
	}
	
	
	$scope.setTaskOfProject = function(projectId) {
		console.log(projectId);
		for(var i=0;i<$scope.projectList.length;i++) {
			if($scope.projectList[i].id == projectId) {
				$scope.taskList = $scope.projectList[i].tasklist;
			}
		}
	}
	
	
	$scope.getWeekDayData = function(day) {
		var htmlTemplate = "";
		$scope.userId = $('#employeeID').val();
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		console.log(day);
		$http({method:'GET',url:contextPath+'/getWeekDayData',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year,day:day}})
		.success(function(data) {
			if(day == 'monday') {
				$scope.mondayData = data;
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in mondayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'tuesday') {
				$scope.tuesdayData = data;
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in tuesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'wednesday') {
				$scope.wednesdayData = data;
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in wednesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'thursday') {
				$scope.thursdayData = data;
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in thursdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'friday') {
				$scope.fridayData = data;
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in fridayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'saturday') {
				$scope.saturdayData = data;
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in saturdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'sunday') {
				$scope.sundayData = data;
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in sundayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			console.log(data);
			$.pnotify({
			    title: day,
			    type:'info',
			    text: htmlTemplate,
			    addclass: day,
			    hide: false,
			    sticker: false
			});
			var element = $('.'+day);
			$compile(element)($scope);
		});
		
		
	};
	
	
	
	$scope.saveTimesheet = function(status) {
		
		for(var i=0;i<$scope.timesheetData.length;i++) {
			if(angular.isUndefined($scope.timesheetData[i].monFrom) || $scope.timesheetData[i].monFrom == null) {
				$scope.timesheetData[i].monFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].monTo) || $scope.timesheetData[i].monTo == null) {
				$scope.timesheetData[i].monTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].tueFrom) || $scope.timesheetData[i].tueFrom == null) {
				$scope.timesheetData[i].tueFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].tueTo) || $scope.timesheetData[i].tueTo == null) {
				$scope.timesheetData[i].tueTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].wedFrom) || $scope.timesheetData[i].wedFrom == null) {
				$scope.timesheetData[i].wedFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].wedTo) || $scope.timesheetData[i].wedTo == null) {
				$scope.timesheetData[i].wedTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].thuFrom) || $scope.timesheetData[i].thuFrom == null) {
				$scope.timesheetData[i].thuFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].thuTo) || $scope.timesheetData[i].thuTo == null) {
				$scope.timesheetData[i].thuTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].friFrom) || $scope.timesheetData[i].friFrom == null) {
				$scope.timesheetData[i].friFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].friTo) || $scope.timesheetData[i].friTo == null) {
				$scope.timesheetData[i].friTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].satFrom) || $scope.timesheetData[i].satFrom == null) {
				$scope.timesheetData[i].satFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].satTo) || $scope.timesheetData[i].satTo == null) {
				$scope.timesheetData[i].satTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].sunFrom) || $scope.timesheetData[i].sunFrom == null) {
				$scope.timesheetData[i].sunFrom = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].sunTo) || $scope.timesheetData[i].sunTo == null) {
				$scope.timesheetData[i].sunTo = "";
			}
			
			if(angular.isUndefined($scope.timesheetData[i].isOverTime)) {
				$scope.timesheetData[i].isOverTime = false;
			}
			
			if(angular.isUndefined($scope.timesheetData[i].rowId) || $scope.timesheetData[i].rowId == null) {
				var value = 0;
				$scope.timesheetData[i].rowId = value;
			}
			
		}
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		$scope.timesheet = {
				userId:$scope.userId,
				status:status,
				weekOfYear:$scope.weekOfYear,
				year: $scope.year,
				timesheetRows: $scope.timesheetData	
		}
		console.log($scope.timesheet);
		$http({method:'POST',url:'saveActualTimesheet',data:$scope.timesheet}).success(function(data) {
			console.log('success');
			if($scope.timesheet.status == "Submitted") {
				$("#timeSheetTable :input").attr("readonly","readonly");
				$("#timeSheetTable select").attr("disabled","disabled");
				$("input:checkbox").attr("disabled","disabled");
				$("#copyFromLastWeek").attr("disabled","disabled");
				$("#saveTimesheetForm").attr("disabled","disabled");
				$("#submitTimesheetForm").attr("disabled","disabled");
				$("#retractTimesheetForm").removeAttr("disabled","disabled");
			} else {
				$("#retractTimesheetForm").attr("disabled","disabled");
			}
		});
		
	}
	
});

app.controller("SchedularTodayAllController", function($scope,$http) {
	
	var d = new Date();
	$scope.currentDate = (d.getMonth()+1)+"/"+d.getDate()+"/"+d.getFullYear();
	$scope.unitValueInMin = 60;
	$scope.gradationBetweenPerUnit = 15;
	$scope.gradationBetweenPerUnitpx = 50;
	var d1 = new Date($scope.currentDate);
	
	$scope.addFunction = function() {
		
	};
	
	$scope.editFunction = function() {
		
	};
	
	$scope.init = function(data) {
		$scope.dataList = data;
		var myString = JSON.stringify($scope.dataList);
		$("#horizontal-scheduler").weekHorizontal({
			unitValueInMin:  $scope.unitValueInMin,
			gradationBetweenPerUnit: $scope.gradationBetweenPerUnit,
			gradationBetweenPerUnitpx:$scope.gradationBetweenPerUnitpx,
			width:"400",
			height:"100px",
			data: myString,
			startTime:'0',
			endTime:'24',
			showCurrentTime:true,
			date:d1,
			addFunction:$scope.addFunction,
			editFunction:$scope.editFunction,
			showRuller:true,
			vertically:false
		});
	};
	
	$scope.getAllStaffAppointments = function(currentDate) {
		console.log(currentDate);
		$scope.userId = $('#userID').val();
		var d1 = new Date(currentDate);
		$http({method:'GET',url:contextPath+'/getTodayAllByDate',params:{userId:$scope.userId,date:currentDate}})
		.success(function(data) {
			console.log('success');
			var myString = JSON.stringify(data);
			$("#horizontal-scheduler").weekHorizontal({
				unitValueInMin:  $scope.unitValueInMin,
				gradationBetweenPerUnit: $scope.gradationBetweenPerUnit,
				gradationBetweenPerUnitpx:$scope.gradationBetweenPerUnitpx,
				width:"400",
				height:"100px",
				data: myString,
				startTime:'0',
				endTime:'24',
				showCurrentTime:true,
				date:d1,
				addFunction:$scope.addFunction,
				editFunction:$scope.editFunction,
				showRuller:true,
				vertically:false
			});
		});	
	}
	
});

app.controller("SchedularWeekReportController", function($scope,$http,$compile) {
	console.log('week report........');
	$scope.currentDate = new Date(); 
	$scope.staffs = [];
	
	$scope.init = function(data) {
		$scope.userId = $('#userID').val();
		for(var i=0;i<data.length;i++) {
			if(data[i].weekReport == null || angular.isUndefined(data[i].weekReport)) {
				data[i].flag = false;
			} else {
				data[i].flag = true;
			}
		}
		$scope.staffs = data;
		console.log(data);
	}
	
	$scope.changeWeek = function(date) {
		$scope.userId = $('#userID').val();
		var d = (date.getMonth()+1)+'/'+date.getDate()+'/'+date.getFullYear();
		$http({method:'GET',url:contextPath+'/getStaffWeekReport',params:{userId:$scope.userId,date:d}}).success(function(data) {
			console.log(data);
			for(var i=0;i<data.length;i++) {
				if(data[i].weekReport == null || angular.isUndefined(data[i].weekReport)) {
					data[i].flag = false;
				} else {
					data[i].flag = true;
				}
			}
			$scope.staffs = data;
		});
	};
	
	$scope.getWeekDayData = function(day,week,year,userId) {
		var htmlTemplate = "";
		
			$http({method:'GET',url:contextPath+'/getWeekDayData',params:{userId:userId,week:week,year:year,day:day}})
			.success(function(data) {
				if(day == 'monday') {
					$scope.mondayData = data;
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in mondayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(day == 'tuesday') {
					$scope.tuesdayData = data;
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in tuesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(day == 'wednesday') {
					$scope.wednesdayData = data;
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in wednesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(day == 'thursday') {
					$scope.thursdayData = data;
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in thursdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(day == 'friday') {
					$scope.fridayData = data;
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in fridayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(day == 'saturday') {
					$scope.saturdayData = data;
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in saturdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(day == 'sunday') {
					$scope.sundayData = data;
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in sundayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
			$.pnotify({
			    title: day,
			    type:'info',
			    text: htmlTemplate,
			    addclass: day,
			    hide: false,
			    sticker: false
			});
			var element = $('.'+day);
			$compile(element)($scope);
		});
	}
	
	$scope.config = {
			tooltips: true,
			labels: true,
			mouseover: function() {},
			mouseout: function() {},
			click: function() {},
			innerRadius: '40%',
			colors:['blue','green','black','red'],
			legend: {
				display: false,
				//could be 'left, right'
				position: 'right'
			}
		};

		$scope.data = {
			data: [{
				y: [100, 500, 0],
				tooltip: "this is tooltip"
			}, {
				y: [300, 100, 100]
			}, {
				y: [351]
			}, {
				y: [54, 0, 879]
			}]
		};
});

