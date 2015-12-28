

app.controller("TimeSheetController", function($scope,$http) {
  
	$scope.timesheetData = [];
	$scope.projectList = [];
	$scope.newtimesheetData = [];
	$scope.n_timesheetData = [];
	var popupClose;
	var oldData = [];
	var oldday = [];
	$scope.dayScope="day";
	$scope.dayData= [];
	$scope.dayData = [];
	$scope.taskList = [];
	$scope.supplierCode=null;
	$scope.isCopyFromLastweek = false;
	
	$scope.timeRegexp = /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
	$scope.ftTimeRegexp =  /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]-([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
	
	$scope.getTimesheetData = function(data) {
		
		if(!angular.isUndefined(data)) {
			
			$scope.userId = data.userId;
			$scope.wk = data.weekOfYear;
			$scope.yr = data.year;
			$("#weekValue").val(data.weekOfYear);
			$("#yearValue").val(data.year);
			$http({method:'GET',url:contextPath+'/getProjectCodes',params:{userId:$scope.userId}})
			.success(function(data) {
				console.log('success');
				console.log(data);
				$scope.projectList = data.projectList;
				$scope.supplierList = data.supplierList;
				$scope.customerList = data.customerList;
				
				
				$scope.projectLst = data.projectList;
				$scope.supplierLst = data.supplierList;
				$scope.customerLst = data.customerList;

				
				$scope.getByWeek($scope.wk,$scope.yr);
			});
			
		} else {
			$scope.getUserProjects();
		}
		
		$scope.isCopyFromLastweek = false;
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
			if(ev.date.getDay() == 0 || ev.date.getDay() == 6) {
				$scope.getByWeek(ev.date.getWeek(),ev.date.getFullYear());
				$scope.weekOfYear = ev.date.getWeek();
			} else {
				$scope.getByWeek(ev.date.getWeek()+1,ev.date.getFullYear());
				$scope.weekOfYear = ev.date.getWeek()+1;
			}
			$scope.weekOfYear = ev.date.getWeek()+1;
			$scope.year = ev.date.getFullYear();
			console.log(startOfWeek);
		});
		if(!angular.isUndefined(data)) {
			var arr = data.date.split("/");
			var editDate = new Date(arr[2],arr[0],arr[1]);
			var sofw = new Date(editDate.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			var eofw = editDate;
			$('.week-picker').datepicker('setDate', sofw);
			$('.week-picker').val($.datepicker.formatDate('dd M yy', sofw) + " - " +
					$.datepicker.formatDate('dd M yy', eofw));
		} else {
				var today = new Date();
				if(today.getDay() == 0 || today.getDay() == 6) {
					var todaysWeek = today.getWeek();
					$("#weekValue").val(today.getWeek());
				} else {
					var todaysWeek = today.getWeek() + 1;
					$("#weekValue").val(today.getWeek() + 1);
				}
				$("#yearValue").val(today.getFullYear());
				var day =   today.getDay();
				if(day == 0) {
					startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * 6 ));
					var endOfWeek = today;
					$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
							$.datepicker.formatDate('dd M yy', endOfWeek));
				} else {
					day = day - 1;
					startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
					var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
					$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
							$.datepicker.formatDate('dd M yy', endOfWeek));
				}
		
		}
	}
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		
		if($scope.timesheetStatus == "Submitted" || $scope.timesheetStatus == "Approved") {
			$scope.isShow = false;
		} else {
			$scope.isShow = true;
		}
		
		if($scope.timesheetStatus == "Submitted") {
			$("#timeSheetTable :input").attr("readonly","readonly");
			$("#timeSheetTable select").attr("disabled","disabled");
			$("input:checkbox").attr("disabled","disabled");
			$("#copyFromLastWeek").attr("disabled","disabled");
			$("#saveTimesheetForm").attr("disabled","disabled");
			$("#submitTimesheetForm").attr("disabled","disabled");
			$("#retractTimesheetForm").removeAttr("disabled","disabled");
			$("#addMore").hide();
		} else {
			if($scope.timesheetStatus == "Approved") {
				$("#timeSheetTable :input").attr("readonly","readonly");
				$("#timeSheetTable select").attr("disabled","disabled");
				$("input:checkbox").attr("disabled","disabled");
				$("#copyFromLastWeek").attr("disabled","disabled");
				$("#saveTimesheetForm").attr("disabled","disabled");
				$("#submitTimesheetForm").attr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").hide();
			} else {
				$("#timeSheetTable :input").removeAttr("readonly","readonly");
				$("#timeSheetTable select").removeAttr("disabled","disabled");
				$("input:checkbox").removeAttr("disabled","disabled");
				$("#copyFromLastWeek").removeAttr("disabled","disabled");
				$("#saveTimesheetForm").removeAttr("disabled","disabled");
				$("#submitTimesheetForm").removeAttr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").show();
			}
		}
	});
	
	$scope.getUserProjects = function() {
		$scope.userId = $('#employeeID').val();
		$http({method:'GET',url:contextPath+'/getProjectCodes',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.projectList = data.projectList;
			$scope.supplierList = data.supplierList;
			$scope.customerList = data.customerList;
		
			$scope.projectLst = data.projectList;
			$scope.supplierLst = data.supplierList;
			$scope.customerLst = data.customerList;
			
			$scope.getTimesheetByWeek();
		});
		
	}
	
	$scope.getByWeek = function(week,year) {
		
		$("#timeSheetTable :input").removeAttr("readonly","readonly");
		$("#timeSheetTable select").removeAttr("disabled","disabled");
		$("input:checkbox").removeAttr("disabled","disabled");
		$("#copyFromLastWeek").removeAttr("disabled","disabled");
		$("#saveTimesheetForm").removeAttr("disabled","disabled");
		$("#submitTimesheetForm").removeAttr("disabled","disabled");
		
		$http({method:'GET',url:contextPath+'/getTimesheetBySelectedWeek',params:{userId:$scope.userId,week:week,year:year}})
		.success(function(data) {
			oldData=[];
			$scope.n_timesheetData=[];
			console.log('success');
			console.log(data);
			if(data.timesheetData.id != null) {
				$scope.weekOfYear = data.timesheetData.weekOfYear;
				$scope.year = data.timesheetData.year;
				$scope.timesheetStatus = data.timesheetData.status;
				$scope.timesheetId = data.timesheetData.id;
				$scope.timesheetData = data.timesheetData.timesheetRows;
				
				angular.forEach($scope.timesheetData, function(value, key){
					var o = $.extend({},value);
					oldData.push(o);
					$scope.n_timesheetData.push(o);
					});			   
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode,i);
				}
				if(data.timesheetData.status == "Submitted") {
					$scope.isShow = false;
					$("#timeSheetTable :input").attr("readonly","readonly");
					$("#timeSheetTable select").attr("disabled","disabled");
					$("input:checkbox").attr("disabled","disabled");
					$("#copyFromLastWeek").attr("disabled","disabled");
					$("#saveTimesheetForm").attr("disabled","disabled");
					$("#submitTimesheetForm").attr("disabled","disabled");
					$("#retractTimesheetForm").removeAttr("disabled","disabled");
					$("#addMore").hide();
				} else {
					if(data.timesheetData.status == "Approved") {
						$scope.isShow = false;
						$("#timeSheetTable :input").attr("readonly","readonly");
						$("#timeSheetTable select").attr("disabled","disabled");
						$("input:checkbox").attr("disabled","disabled");
						$("#copyFromLastWeek").attr("disabled","disabled");
						$("#saveTimesheetForm").attr("disabled","disabled");
						$("#submitTimesheetForm").attr("disabled","disabled");
						$("#retractTimesheetForm").attr("disabled","disabled");
						$("#addMore").hide();
					} else {
						$scope.isShow = true;
						$("#retractTimesheetForm").attr("disabled","disabled");
						$("#addMore").show();
					}
				}
				
				
			} else {
				$scope.timesheetData =[];
				//$scope.addMore();
				$scope.timesheetStatus = "";
				$scope.timesheetId = null;
				$("#addMore").show();
				$scope.isShow = true;
				
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
				
			}
		});	
	}
	
	$scope.getTimesheetByWeek = function() {
		$http({method:'GET',url:contextPath+'/getTimesheetByCurrentWeek',params:{userId:$scope.userId}})
		.success(function(data) {
			oldData=[];
			$scope.n_timesheetData=[];
			console.log('success');
			console.log(data);
			$scope.newtimesheetData=data;
			if(data.timesheetData.id != null) {
				$scope.weekOfYear = data.timesheetData.weekOfYear;
				$scope.year = data.timesheetData.year;
				$scope.timesheetStatus = data.timesheetData.status;
				$scope.timesheetId = data.timesheetData.id;
				$scope.timesheetData = data.timesheetData.timesheetRows;
				console.log($scope.timesheetData);
				angular.forEach($scope.timesheetData, function(value, key){
					var o = $.extend({},value);
					oldData.push(o);
					$scope.n_timesheetData.push(o);
					});				
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode,i);
				}
				
				for(var i=0;i<$scope.n_timesheetData.length;i++) {
					$scope.setTaskOfPrj($scope.n_timesheetData[i].projectCode,i);
				}
			} else {
				//$scope.addMore();
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
			}
			
		});
		
	}
	
	$scope.confirmCopyFromLast = function() {
		$('#popupBtn2').click();
	}
	
	$scope.copyFromLastWeek = function() {
		
		
			$scope.weekOfYear = $('#weekValue').val();
			$scope.year = $('#yearValue').val();
			$http({method:'GET',url:contextPath+'/getTimesheetByLastWeek',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				oldData=[];
				$scope.n_timesheetData=[];
				console.log('success');
				$('#lastWeekClose').click();
				console.log(data);
				if(data.id != null) {
					$scope.weekOfYear = data.weekOfYear;
					$scope.year = data.year;
					$scope.timesheetStatus = data.status;
					
					$scope.timesheetData = data.timesheetRows;
					$scope.isCopyFromLastweek = true;
					angular.forEach($scope.timesheetData, function(value, key){
						var o = $.extend({},value);
						oldData.push(o);
						$scope.n_timesheetData.push(o);
						});
					
					for(var i=0;i<$scope.timesheetData.length;i++) {
						$scope.setTaskOfProject($scope.timesheetData[i].projectCode,i);
						$scope.timesheetData[i].rowId = 0;
					}

					/*for(var i=0;i<$scope.timesheetData.length;i++) {
						$scope.setTaskOfProject($scope.timesheetData[i].projectCode,i);
					}
					*/
					for(var i=0;i<$scope.n_timesheetData.length;i++) {
						$scope.setTaskOfPrj($scope.n_timesheetData[i].projectCode,i);
						$scope.n_timesheetData[i].rowId = 0;
					}
					
				} else {
					$('#popupBtn3').click();
				}
				
				
				
			});
				
	};
	
	$scope.addCustomerAndSupplier = function(index,day) {
		console.log("addedd");
		$scope.rowIndex = index;
		$scope.dayVal = day;
		if($scope.dayVal == 'mon') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].monSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].monCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].monNotes;
		}
		if($scope.dayVal == 'tue') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].tueSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].tueCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].tueNotes;
		}
		if($scope.dayVal == 'wed') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].wedSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].wedCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].wedNotes;
		}
		if($scope.dayVal == 'thu') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].thuSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].thuCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].thuNotes;
		}
		if($scope.dayVal == 'fri') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].friSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].friCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].friNotes;
		}
		if($scope.dayVal == 'sat') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].satSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].satCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].satNotes;
		}
		if($scope.dayVal == 'sun') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].sunSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].sunCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].sunNotes;
		}
		$('#popupBtn5').click();
	}
	
	$scope.addData = function() {
		
		if($scope.dayVal == 'mon') {
			$scope.timesheetData[$scope.rowIndex].monSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].monCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].monNotes = $scope.notes;
			console.log( $scope.supplierCode);
		}
		if($scope.dayVal == 'tue') {
			$scope.timesheetData[$scope.rowIndex].tueSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].tueCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].tueNotes = $scope.notes;
		}
		if($scope.dayVal == 'wed') {
			$scope.timesheetData[$scope.rowIndex].wedSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].wedCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].wedNotes = $scope.notes;
		}
		if($scope.dayVal == 'thu') {
			$scope.timesheetData[$scope.rowIndex].thuSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].thuCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].thuNotes = $scope.notes;
		}
		if($scope.dayVal == 'fri') {
			$scope.timesheetData[$scope.rowIndex].friSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].friCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].friNotes = $scope.notes;
		}
		if($scope.dayVal == 'sat') {
			$scope.timesheetData[$scope.rowIndex].satSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].satCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].satNotes = $scope.notes;
		}
		if($scope.dayVal == 'sun') {
			$scope.timesheetData[$scope.rowIndex].sunSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].sunCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].sunNotes = $scope.notes;
		}
		
		if($scope.supplierCode==null)
			{
			$scope.supplierCode=0;
			
			}
		if( $scope.customerCode==null )
			{
			$scope.customerCode=0;
			}
		
		if($scope.timesheetData[$scope.rowIndex].rowId != 0 || !angular.isUndefined($scope.timesheetData[$scope.rowIndex].rowId)) {
			if($scope.dayVal == 'mon') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].mondayId;
			}
			if($scope.dayVal == 'tue') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].tuesdayId;
			}
			if($scope.dayVal == 'wed') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].wednesdayId;
			}
			if($scope.dayVal == 'thu') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].thursdayId;
			}
			if($scope.dayVal == 'fri') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].fridayId;
			}
			if($scope.dayVal == 'sat') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].saturdayId;
			}
			if($scope.dayVal == 'sun') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].sundayId;
			}
			$http({method:'GET',url:contextPath+'/addSupplierCustomer',params:{dayId:$scope.dayId,supplierCode:$scope.supplierCode,customerCode:$scope.customerCode,notes:$scope.notes}})
			.success(function(data) {
				console.log('success');
				console.log('after api');
				console.log($scope.timesheetData[$scope.rowIndex]);
				
			});
			
		}
		$('#modal5Close').click();
	}	
	
	$scope.addMore = function() {
		$scope.timesheetData.push({});
    }
	
	$scope.addMore1 = function() {
		$scope.dayData.push({});
    
	}
	
	$scope.addData1 = function(day1){
		$scope.headerData="";
		$scope.taskLstArray = [];
		$scope.dayData = [];
		oldday = [];
		console.log($scope.taskLstArray);
		var k= 0;
		var f=0;
		if (day1=='mon'){
			k = 0;
			 $scope.dayScope="mon";
			 $scope.headerData="Monday Data";
			for(var j=0; j<$scope.n_timesheetData.length;j++) {
				f=0;
				if(($scope.n_timesheetData[j].monTo!=null) && ($scope.n_timesheetData[j].monFrom!=null)){			
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].monCustomer;
				$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].monSupplier;
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].monCustomer;
				$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].monNotes;
				console.log($scope.n_timesheetData[j]);
				$scope.dayData.push($scope.n_timesheetData[j]);
				oldday.push($scope.n_timesheetData[j]);
				
			 for(var i=0;i<$scope.projectLst.length;i++) {
					if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {
						f++;
						if(f==1)
						{
						$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;
						k++;
						}
						
					}
				}
			  }
			};
		}
		
		if (day1=='tue')
			{
			k = 0;
			 $scope.dayScope="tue";
			 $scope.headerData="Tuesday Data";
			for(var j=0;j<$scope.n_timesheetData.length;j++) {
					f=0;
					if(($scope.n_timesheetData[j].tueTo!=null) && ($scope.n_timesheetData[j].tueFrom!=null)){
					$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].tueSupplier;
					$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].tueCustomer;
					$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].tueNotes;	
					$scope.dayData.push($scope.n_timesheetData[j]);
					oldday.push($scope.n_timesheetData[j]);
					for(var i=0;i<$scope.projectLst.length;i++) {		
							if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {
							f++
							if(f==1)
							{
							$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;
							k++;
							}
						}
					}
					}
				};
			}
		if (day1=='wed')
		{
			k=0;
		 $scope.dayScope="wed";
		 $scope.headerData="Wednesday Data";
		for(var j=0;j<$scope.n_timesheetData.length;j++) {
			f=0;
			if(($scope.n_timesheetData[j].wedTo!=null) && ($scope.n_timesheetData[j].wedFrom!=null))
			{
			$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].wedSupplier;
			$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].wedCustomer;
			$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].wedNotes;		
			$scope.dayData.push($scope.n_timesheetData[j]);
			oldday.push($scope.n_timesheetData[j]);
			for(var i=0;i<$scope.projectLst.length;i++) {
				if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {
					f++;
					if(f==1){
					$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;
					k++;
					}
				}
			  }
			}
			};
		}
		if (day1=='thu')
		{
			k=0;
		 $scope.dayScope="thu";
		 $scope.headerData="Thursday Data";
		for(var j=0;j<$scope.n_timesheetData.length;j++) {
			f=0;
			if(($scope.n_timesheetData[j].thuTo!=null) && ($scope.n_timesheetData[j].thuFrom!=null)){
			$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].thuSupplier;
			$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].thuCustomer;
			$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].thuNotes;
			$scope.dayData.push($scope.n_timesheetData[j]);
			oldday.push($scope.n_timesheetData[j]);
			for(var i=0;i<$scope.projectLst.length;i++) {
				if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {
					f++;
					if(f==1){
					$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;
					k++;
					}
				}
		   	 }
			}
			};
		}
		if (day1=='fri')
		{
		 $scope.dayScope="fri";
		 $scope.headerData="Friday Data";
		 console.log(" fri  called");
		for(var j=0;j<$scope.n_timesheetData.length;j++) {
			f=0;
			if(($scope.n_timesheetData[j].friTo!=null) && ($scope.n_timesheetData[j].friFrom!=null)){
			$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].friSupplier;
			$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].friCustomer;
			$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].friNotes;	
			$scope.dayData.push($scope.n_timesheetData[j]);
			oldday.push($scope.n_timesheetData[j]);
			for(var i=0;i<$scope.projectLst.length;i++) {
				
				if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {
					f++;
					if(f==1){
					$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;
					k++;
					}
				}
			   }
			  }
			};
		}	
			if (day1=='sat')
			{
			 $scope.dayScope="sat";
			 $scope.headerData="Saturday Data";
			 console.log(" sat  called");
			for(var j=0;j<$scope.n_timesheetData.length;j++) {
				f=0;
				if(($scope.n_timesheetData[j].satTo!=null) && ($scope.n_timesheetData[j].satFrom!=null)){
				$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].satSupplier;
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].satCustomer;
				$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].satNotes;	
				$scope.dayData.push($scope.n_timesheetData[j]);
				oldday.push($scope.n_timesheetData[j]);
				for(var i=0;i<$scope.projectLst.length;i++) {
					if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {
						f++
						if(f==1){
						$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;
						k++;
						}
					}
			      }
				}
				};
			}
			if (day1=='sun')
			{
			 $scope.dayScope="sun";
			 $scope.headerData="Sunday Data";
			 console.log(" sun  called");
			for(var j=0;j<$scope.n_timesheetData.length;j++) {
				f=0;
				if(($scope.n_timesheetData[j].sunTo!=null) && ($scope.n_timesheetData[j].sunFrom!=null)){
				$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].sunSupplier;
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].sunCustomer;
				$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].sunNotes;
				$scope.dayData.push($scope.n_timesheetData[j]);
				oldday.push($scope.n_timesheetData[j]);
				for(var i=0;i<$scope.projectLst.length;i++) {
					if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {
						f++;
						if(f==1){
						$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;
						k++;
						}
					}
				  }
				}
				};
			}
		
		$('#popupBtn6').click();
	}	
	
	$scope.confirmDelete1 = function() {
		$('#popupBtn6').click();
		$('#deleteClose').click();
		if($scope.dayScope=='mon'){
			if($scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].monTo="";
				$scope.timesheetData[$scope.deleteIndex1].monFrom="";
				$scope.timesheetData[$scope.deleteIndex1].monFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].monNotes="";
				$scope.timesheetData[$scope.deleteIndex1].monSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].monCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='tue'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].tueTo="";
				$scope.timesheetData[$scope.deleteIndex1].tueFrom="";
				$scope.timesheetData[$scope.deleteIndex1].tueFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].tueNotes="";
				$scope.timesheetData[$scope.deleteIndex1].tueSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].tueCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='wed'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].wedTo="";
				$scope.timesheetData[$scope.deleteIndex1].wedFrom="";
				$scope.timesheetData[$scope.deleteIndex1].wedFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].wedNotes="";
				$scope.timesheetData[$scope.deleteIndex1].wedSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].wedCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='thu'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].thuTo="";
				$scope.timesheetData[$scope.deleteIndex1].thuFrom="";
				$scope.timesheetData[$scope.deleteIndex1].thuFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].thuNotes="";
				$scope.timesheetData[$scope.deleteIndex1].thuSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].thuCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='fri'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].friTo="";
				$scope.timesheetData[$scope.deleteIndex1].friFrom="";
				$scope.timesheetData[$scope.deleteIndex1].friFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].friNotes="";
				$scope.timesheetData[$scope.deleteIndex1].friSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].friCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='sat'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].satTo="";
				$scope.timesheetData[$scope.deleteIndex1].satFrom="";
				$scope.timesheetData[$scope.deleteIndex1].satFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].satNotes="";
				$scope.timesheetData[$scope.deleteIndex1].satSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].satCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='sun'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null){
				$scope.removeRow();
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].sunTo="";
				$scope.timesheetData[$scope.deleteIndex1].sunFrom="";
				$scope.timesheetData[$scope.deleteIndex1].sunFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].sunNotes="";
				$scope.timesheetData[$scope.deleteIndex1].sunSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].sunCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
			}
		}
	}
	
	$scope.confirmDelete = function(index,rowId) {
		$scope.deleteIndex = index;
		$scope.deleteRowId = rowId;
		console.log($scope.deleteRowId);
		for(var i=0;i<$scope.timesheetData.length;i++)
			if($scope.timesheetData[i].rowId==$scope.deleteRowId)
			$scope.deleteIndex1 = i;
		$('#popupBtn4').click();
	}
	
	$scope.confirmDlt = function(index,rowId) {
		$scope.deleteIndex = index;
		$scope.deleteRowId = rowId;
		for(var i=0;i<$scope.timesheetData.length;i++)
			if($scope.timesheetData[i].rowId==$scope.deleteRowId)
				$scope.deleteIndex1 = i;

		$('#popupBtn4').click();
	}
	
	
	$scope.removeRow = function() {
		oldData=[];
			if(!angular.isUndefined($scope.deleteRowId) && $scope.deleteRowId != 0) {
					
					$http({method:'GET',url:contextPath+'/deleteTimesheetRow',params:{rowId:$scope.deleteRowId}})
					.success(function(data) {
						console.log('success');
						$.pnotify({
						    title: "Success",
						    type:'success',
						    text: "Calendar Row Deleted Successfully",
						});
					});
					
					console.log();
				}
			$scope.dayData.splice($scope.deleteIndex,1);
			$scope.n_timesheetData.splice($scope.deleteIndex1,1);
			$scope.timesheetData.splice($scope.deleteIndex1,1);
			
			
			console.log($scope.timesheetData);
			$scope.taskListArray.splice($scope.deleteIndex1,1);
			$scope.taskLstArray.splice($scope.deleteIndex,1);
			
			
			
			$('#deleteClose').click();
			angular.forEach($scope.timesheetData, function(value, key){
				 oldData.push(value);
				    });
		
	}
	$scope.confirmRetract = function() {
		$('#popupBtn').click();
	}
	
	$scope.retractTimesheet = function() {
		$scope.userId = $('#employeeID').val();
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		
			$http({method:'GET',url:contextPath+'/timesheetRetract',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				console.log('success');
				$("#timeSheetTable :input").removeAttr("readonly","readonly");
				$("#timeSheetTable select").removeAttr("disabled","disabled");
				$("input:checkbox").removeAttr("disabled","disabled");
				$("#copyFromLastWeek").removeAttr("disabled","disabled");
				$("#saveTimesheetForm").removeAttr("disabled","disabled");
				$("#submitTimesheetForm").removeAttr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").show();
				$scope.isShow = true;
				$scope.timesheetStatus = "Draft";
			});
			$("#addMore").show();
		$('#retractClose').click();
	}
	
	
	$scope.splitToFTTime = function (row, day) {
		if(day === 'mon'){
			if(row.monFromTo) {
				var from = row.monFromTo.split('-')[0];
				if(from) {
				   row.monFrom = from;	
				}
				
				var to = row.monFromTo.split('-')[1];
				if(to) {
					row.monTo = to;
				}
			}
		}
		if(day === 'tue'){
			if(row.tueFromTo) {
				var from = row.tueFromTo.split('-')[0];
				if(from) {
				   row.tueFrom = from;	
				}
				
				var to = row.tueFromTo.split('-')[1];
				if(to) {
					row.tueTo = to;
				}
			}
		}
		if(day === 'wed'){
			if(row.wedFromTo) {
				var from = row.wedFromTo.split('-')[0];
				if(from) {
				   row.wedFrom = from;	
				}
				
				var to = row.wedFromTo.split('-')[1];
				if(to) {
					row.wedTo = to;
				}
			}
		}
		
		if(day === 'thu'){
			if(row.thuFromTo) {
				var from = row.thuFromTo.split('-')[0];
				if(from) {
				   row.thuFrom = from;	
				}
				
				var to = row.thuFromTo.split('-')[1];
				if(to) {
					row.thuTo = to;
				}
			}
		}
		if(day === 'fri'){
			if(row.friFromTo) {
				var from = row.friFromTo.split('-')[0];
				if(from) {
				   row.friFrom = from;	
				}
				
				var to = row.friFromTo.split('-')[1];
				if(to) {
					row.friTo = to;
				}
			}
		}
		if(day === 'sat'){
			if(row.satFromTo) {
				var from = row.satFromTo.split('-')[0];
				if(from) {
				   row.satFrom = from;	
				}
				
				var to = row.satFromTo.split('-')[1];
				if(to) {
					row.satTo = to;
				}
			}
		}
		if(day === 'sun'){
			if(row.sunFromTo) {
				var from = row.sunFromTo.split('-')[0];
				if(from) {
				   row.sunFrom = from;	
				}
				
				var to = row.sunFromTo.split('-')[1];
				if(to) {
					row.sunTo = to;
				}
			}
		}
	}
	
	$scope.initFTTime = function (row, day) {
		if(day === 'mon') {
			if(row.monFrom && row.monTo) {
				row.monFromTo = row.monFrom + '-' + row.monTo;
			}
		}
		if(day === 'tue') {
			if(row.tueFrom && row.tueTo) {
				row.tueFromTo = row.tueFrom + '-' + row.tueTo;
			}
		}
		if(day === 'wed') {
			if(row.wedFrom && row.wedTo) {
				row.wedFromTo = row.wedFrom + '-' + row.wedTo;
			}
		}
		if(day === 'thu') {
			if(row.thuFrom && row.thuTo) {
				row.thuFromTo = row.thuFrom + '-' + row.thuTo;
			}
		}
		if(day === 'fri') {
			if(row.friFrom && row.friTo) {
				row.friFromTo = row.friFrom + '-' + row.friTo;
			}
		}
		if(day === 'sat') {
			if(row.satFrom && row.satTo) {
				row.satFromTo = row.satFrom + '-' + row.satTo;
			}
		}
		if(day === 'sun') {
			if(row.sunFrom && row.sunTo) {
				row.sunFromTo = row.sunFrom + '-' + row.sunTo;
			}
		}
	}
	
	$scope.taskListArray = [];
	$scope.setTaskOfProject = function(projectId,index) {
		for(var i=0;i<$scope.projectList.length;i++) {
			if($scope.projectList[i].id == projectId) {
				$scope.taskListArray[index] = $scope.projectList[i].tasklist;
			}
		}
	}
	$scope.taskListArray = [];
	
	$scope.setTaskOfProj = function(projectId,index) {
		$scope.taskListArray = [];
		for(var i=0;i<$scope.projectList.length;i++) {
			if($scope.projectList[i].id == projectId) {
				$scope.taskListArray[index] = $scope.projectList[i].tasklist;
			}
		}
	}
	
	$scope.taskLstArray = [];
	$scope.setTaskOfPrj = function(projectId,index) {
		
		for(var i=0;i<$scope.projectLst.length;i++) {
			if($scope.projectLst[i].id == projectId) {
				$scope.taskLstArray[index] = $scope.projectLst[i].tasklist;
			}
		}
	}

	/*update rows*/
	$scope.updateRows = function() {
		var flg = 0;
		var isPresent;
		var len = oldday.length;
		var newlen= $scope.dayData.length - oldday.length;	
		console.log("new day data");
		console.log($scope.dayData);
		console.log("old day data ");
		console.log(oldday);
		console.log("old db data ");
		console.log(oldData);
		
		
		for(var i=0;i<$scope.dayData.length;i++){
			if($scope.dayScope=='mon'){
				 $scope.dayData[i].monSupplier=$scope.dayData[i].supplier;
				 $scope.dayData[i].monCustomer=$scope.dayData[i].customer;
				 $scope.dayData[i].monNotes=$scope.dayData[i].note;
			}
			
			if($scope.dayScope=='tue'){
				 $scope.dayData[i].tueSupplier=$scope.dayData[i].supplier;
				 $scope.dayData[i].tueCustomer=$scope.dayData[i].customer;
				 $scope.dayData[i].tueNotes=$scope.dayData[i].note;
			}
			if($scope.dayScope=='wed'){
				 $scope.dayData[i].wedSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].wedCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].wedNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='thu'){
				 $scope.dayData[i].thuSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].thuCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].thuNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='fri'){
				 $scope.dayData[i].friSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].friCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].friNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='sat'){
				 $scope.dayData[i].satSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].satCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].satNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='sun'){
				 $scope.dayData[i].sunSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].sunCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].sunNotes=$scope.dayData[i].note ;
			}
		}
		
		for(var i=0;i<newlen;i++){
			flg=0;
			isPresent=true;
				for(var j=0;j<oldData.length;j++) {		
				if(($scope.dayData[len+i].projectCode == oldData[j].projectCode) && ($scope.dayData[len+i].taskCode==oldData[j].taskCode)){
					if($scope.dayScope=='mon'){
						if((oldData[j].monTo == null) && (oldData[j].monFrom==null) && isPresent==true){
							flg++;
							oldData[j].monTo = $scope.dayData[len+i].monTo;
							oldData[j].monFrom = $scope.dayData[len+i].monFrom;
							oldData[j].monSupplier = $scope.dayData[len+i].supplier;
							oldData[j].monCustomer = $scope.dayData[len+i].customer;
							oldData[j].monNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" mon updated....");
						}
					
					}
					if($scope.dayScope=='tue'){
						if((oldData[j].tueTo == null) && (oldData[j].tueFrom == null) && isPresent==true){
							flg++;
							oldData[j].tueTo = $scope.dayData[len+i].tueTo;
							oldData[j].tueFrom = $scope.dayData[len+i].tueFrom;
							oldData[j].tueSupplier = $scope.dayData[len+i].supplier;
							oldData[j].tueCustomer = $scope.dayData[len+i].customer;
							oldData[j].tueNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" tue updated....");
						}
					}
					if($scope.dayScope=='wed'){
						if((oldData[j].wedTo == null) && (oldData[j].wedFrom == null) && isPresent==true){
						flg++;
						oldData[j].wedTo = $scope.dayData[len+i].wedTo;
						oldData[j].wedFrom = $scope.dayData[len+i].wedFrom;
						oldData[j].wedSupplier = $scope.dayData[len+i].supplier;
						oldData[j].wedCustomer = $scope.dayData[len+i].customer;
						oldData[j].wedNotes = $scope.dayData[len+i].note;
						isPresent=false;
						console.log(" wed updated....");
						}
					}
					if($scope.dayScope=='thu'){
						if((oldData[j].thuTo == null) && (oldData[j].thuFrom == null) && isPresent==true){
						flg++;
						oldData[j].thuTo = $scope.dayData[len+i].thuTo;
						oldData[j].thuFrom = $scope.dayData[len+i].thuFrom;
						oldData[j].thuSupplier = $scope.dayData[len+i].supplier;
						oldData[j].thuCustomer = $scope.dayData[len+i].customer;
						oldData[j].thuNotes = $scope.dayData[len+i].note;
						isPresent=false;
						console.log(" thu updated....");
						}
					}
					if($scope.dayScope=='fri'){
						if((oldData[j].friTo == null) && (oldData[j].friFrom == null) && isPresent==true){
						flg++;
						oldData[j].friTo = $scope.dayData[len+i].friTo;
						oldData[j].friFrom = $scope.dayData[len+i].friFrom;
						oldData[j].friSupplier = $scope.dayData[len+i].supplier;
						oldData[j].friCustomer = $scope.dayData[len+i].customer;
						oldData[j].friNotes = $scope.dayData[len+i].note;
						isPresent=false;
						console.log(" fri updated....");
						}
					}
					if($scope.dayScope=='sat'){
						if((oldData[j].satTo == null) && (oldData[j].satFrom == null) && isPresent==true){
						flg++;
						oldData[j].satTo = $scope.dayData[len+i].satTo;
						oldData[j].satFrom = $scope.dayData[len+i].satFrom;
						oldData[j].satSupplier = $scope.dayData[len+i].supplier;
						oldData[j].satCustomer = $scope.dayData[len+i].customer;
						oldData[j].satNotes = $scope.dayData[len+i].note;
						isPresent=false;
						console.log(" sat updated....");
						}
					}
					if($scope.dayScope=='sun'){
						if((oldData[j].sunTo == null) && (oldData[j].sunFrom == null) && isPresent==true){
						flg++;
						oldData[j].sunTo = $scope.dayData[len+i].sunTo;
						oldData[j].sunFrom = $scope.dayData[len+i].sunFrom;
						oldData[j].sunSupplier = $scope.dayData[len+i].supplier;
						oldData[j].sunCustomer = $scope.dayData[len+i].customer;
						oldData[j].sunNotes = $scope.dayData[len+i].note;
						isPresent=false;
						console.log(" sun updated....");
						}
					}
				}	
			}	
				if(flg==0){
					$scope.timesheetData.push($scope.dayData[len+i]);
					console.log("not updated added");
					}			
		}
			
		if(flg>0){
			$scope.timesheetData = oldData;
			flg=0;
			}

		$('#popupBtn6').click();
		popupClose= true;
			$scope.saveTimesheet('Draft');
			
	};
	
	
	/*=============================================*/
	
	
	
	console.log($scope.timesheetData);
	$scope.saveTimesheet = function(status) {
		//popupClose=false;
	
		
		if($scope.isCopyFromLastweek == true) {
			$http({method:'GET',url:contextPath+'/deleteTimesheet',params:{timesheetId:$scope.timesheetId}})
			.success(function(data) {
				console.log('success');
				$scope.isCopyFromLastweek = false;
			});
		}
		
		console.log("in save fun ");
		console.log($scope.timesheetData);
		
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
		console.log($scope.timesheetData);
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		$scope.timesheet = {
				userId:$scope.userId,
				status:status,
				weekOfYear:$scope.weekOfYear,
				year: $scope.year,
				timesheetRows: $scope.timesheetData	
		}
		
	
		console.log("btwn save fun ");
		console.log($scope.timesheetData);
		$http({method:'POST',url:contextPath+'/saveTimesheet',data:$scope.timesheet}).success(function(data) {	
			console.log('success');
			console.log(data);
			$scope.timesheetData = data.timesheetRows;
			//$scope.dayData = data.timesheetRows;
			console.log($scope.timesheetData);
			$scope.timesheetStatus = status;
			if($scope.timesheet.status == "Submitted") {
				$("#timeSheetTable :input").attr("readonly","readonly");
				$("#timeSheetTable select").attr("disabled","disabled");
				$("input:checkbox").attr("disabled","disabled");
				$("#copyFromLastWeek").attr("disabled","disabled");
				$("#saveTimesheetForm").attr("disabled","disabled");
				$("#submitTimesheetForm").attr("disabled","disabled");
				$("#retractTimesheetForm").removeAttr("disabled","disabled");
				$("#addMore").hide();
				$scope.isShow = false;
			} else {
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").show();
				$scope.isShow = true;
			}
			if($scope.timesheet.status == "Submitted") {
				$scope.msg = "Calendar Submitted Successfully";
			} else {
				$scope.msg = "Calendar Saved Successfully";
			}
			
			$.pnotify({
			    title: "Success",
			    type:'success',
			    text: $scope.msg,
			});
			oldData=[];
			$scope.n_timesheetData=[];
			console.log("after save ");
			console.log($scope.timesheetData);
			angular.forEach($scope.timesheetData, function(value, key){
				 oldData.push(value);
				 $scope.n_timesheetData.push(value);
				    });
			if(popupClose==false)
			$('#popupBtn6').click();
		});
	}
	
	
	
	
});


app.controller("SchedularTodayController", function($scope,$http,ngDialog,$upload) {
	
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
	$scope.userId = $('#userID').val();
	$http({method:'GET',url:contextPath+'/getUserStaffData',params:{userId:$scope.userId}})
	.success(function(data) {
		console.log('success');
		$scope.staffs = data;
		$scope.selectedStaff = data[0].id;
	});
	
	$scope.getSchedulerDay = function(dateString) {
		var temp = dateString.split("/");
		$scope.currentDateObject.setFullYear(parseInt(temp[2]),parseInt(temp[0])-1,parseInt(temp[1]));
		
		$scope.getSchedulerDataByDate();
	}
	
	
	$scope.getSchedulerDataByDate = function(data) {
		$scope.userId = $('#userID').val();
		$scope.dateStr = $('#dateID').val();
		console.log($scope.userId);
		console.log($scope.dateStr);
		console.log($scope.currentDate);
		console.log(data);
		
		
		if(angular.isUndefined($scope.selectedStaff)) {
			$scope.selectedStaff = $scope.userId;
		}
		
		if(angular.isUndefined(data)) {
			$http({method:'GET',url:contextPath+'/getSchedularDay',params:{date:$scope.currentDate,userId:$scope.selectedStaff}})
			.success(function(data) {
				console.log('success');
				console.log(data);
				if(data.isHoliday == true) {
					$('#isHoliday').css("color","red");
				} else {
					$('#isHoliday').removeAttr("style");
				}
				
				$scope.myString = JSON.stringify(data.todayData);
				$scope.data.data = $scope.myString;
			});
		} else{
			if(data.isHoliday == true) {
				$('#isHoliday').css("color","red");
			} else {
				$('#isHoliday').removeAttr("style");
			}
			$scope.currentDate = $scope.dateStr;
			var temp = $scope.dateStr.split("/");
			$scope.currentDateObject.setFullYear(parseInt(temp[2]),parseInt(temp[0])-1,parseInt(temp[1]));
			$scope.myString = JSON.stringify(data.todayData);
			$scope.data.data = $scope.myString;
		}
	}
	
	$scope.getSchedulerDataByStaff = function(selectedStaff,currentDate) {
		console.log(selectedStaff);
		console.log(currentDate);
		$http({method:'GET',url:contextPath+'/getSchedularDay',params:{date:currentDate,userId:selectedStaff}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			if(data.isHoliday == true) {
				$('#isHoliday').css("color","red");
			} else {
				$('#isHoliday').removeAttr("style");
			}
			$scope.myString = JSON.stringify(data.todayData);
			$scope.data.data = $scope.myString;
		});
	}
	
	
	$scope.editFunction = function(e,data) {
		
		console.log(data);
		$scope.projectCode = data.visitType;
		$scope.taskCode = data.taskCode;
		
		$scope.editStartTime = data.startTime;
		$scope.editEndTime = data.endTime;
		$scope.status = data.status;
		$scope.userId = $('#userID').val();
		 $scope.showMsg = false;
		 $scope.fileErr = false;
		 $scope.supplier = data.supplier;
		 $scope.customer = data.customer;
		 $scope.todayNotes = data.todayNotes;
		$scope.taskDetail = {
			projectId:data.projectId,
			taskId:data.taskId,
			startTime:data.startTime,
			endTime:data.endTime,
			status:data.status,
			date:$scope.currentDate,
			userId:$scope.userId
		}
		
		if(angular.isUndefined($scope.selectedStaff)) {
			$scope.selectedStaff = $scope.userId;
		}
		
		$http({method:'GET',url:contextPath+'/getTaskDetails',params:{userId:$scope.selectedStaff,projectId:data.projectId,taskId:data.taskId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.documentsData = data.taskDetails;
			$scope.commentsList = data.commentDetails;
		});
		
		console.log($scope.editStartTime);
		
		ngDialog.open({
            template:contextPath+'/editSchedule',
            scope:$scope,
            closeByDocument:false
            
		});
		e.stopPropagation();
	}
	
	var file = null;
    $scope.selectFile = function(files) {
    	file = files[0];
    };
    $scope.fileErr = false;
    $scope.showMsg = false;
	$scope.saveAttachment = function() {
	
		if(file != null) {
			$scope.fileErr = false;
			$upload.upload({
	            url: contextPath+'/saveFile',
	            data: $scope.taskDetail,
	            file: file,
	            method:'post'
	        }).progress(function (evt) {
	            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
	        }).success(function (data, status, headers, config) {
	        	$scope.documentsData = data;
	        	$scope.showMsg = true;
	        });   
		} else {
			$scope.fileErr = true;
			$scope.showMsg = false;
		}
	}
	
	$scope.downloadfile = function(id) {
		$.fileDownload(contextPath+'/downloadTaskFile',
				{	   	
					   httpMethod : "POST",
					   data : {
						   attchId : id
					   }
				}).done(function(e, response)
						{
						}).fail(function(e, response)
						{
							// failure
						});
	}
	
	$scope.saveComment = function(comment) {
		$scope.userId = $('#userID').val();
		$http({method:'GET',url:contextPath+'/saveComment',params:{userId:$scope.userId,comment:comment,projectId:$scope.taskDetail.projectId,taskId:$scope.taskDetail.taskId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.commentsList = data;
			$scope.comment = "";
		});
	}
	
	$scope.setStatus = function(status) {
		
			$scope.userId = $('#userID').val();
			$http({method:'GET',url:contextPath+'/updateTaskStatus',params:{projectId:$scope.taskDetail.projectId,taskId:$scope.taskDetail.taskId,status:status}})
			.success(function(data) {
				console.log('success');
				$scope.getSchedulerDataByStaff($scope.selectedStaff,$scope.currentDate);
			});
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

app.controller("SchedularWeekController", function($scope,$http,ngDialog,$upload) {
	
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
		var d = $scope.currentDateObject.getDay();
		var weekStart;
		if(d == 0) {
			weekStart = new Date($scope.currentDateObject.getTime() - (24 * 60 * 60 * 1000 * 6 ));
		} else {
			d = d - 1;
			weekStart = new Date($scope.currentDateObject.getTime() - (24 * 60 * 60 * 1000 * d ));
		}
		var yourData = JSON.stringify($scope.weeks);
		$("#scheduler1").matrixWrapper({
			day:[ "Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"],
			widthofday:(100/7)+"%",
			data: yourData,
			year:$scope.currentDateObject.getFullYear(),
			week:$scope.currentDateObject.getWeek(),
			date:weekStart,
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
		var d = new Date();
		$scope.currentDateObject = new Date();
		$scope.currentDateObject.setHours(0);
		$scope.currentDateObject.setMinutes(0);
		$scope.currentDateObject.setSeconds(0);
		$scope.currentDateObject.setMilliseconds(0);
		console.log($scope.currentDateObject);
		$scope.currentDate = ($scope.currentDateObject.getMonth()+1)+"/"+$scope.currentDateObject.getDate()+"/"+$scope.currentDateObject.getFullYear();
		console.log($scope.currentDateObject.getFullYear());
		
		$scope.getDataByWeek();
		
		Date.prototype.getWeek = function() {
			var onejan = new Date(this.getFullYear(),0,1);
			var w = Math.ceil((((this - onejan) / 86400000) + onejan.getDay())/7);
			if(w>52) {
				w -=52;
			}
			return w;
		};
		var startOfWeek;
		Date.prototype.getStartOfWeek = function() {
			var day =   this.getDay() - 1;
			var startOfWeek;
			if (day != -1) {
				startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * day ));
			} else {
				startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			}
			return startOfWeek;
		};

		$('.week-picker').datepicker({
			chooseWeek:true,
			calendarWeeks:true,
			weekStart:1,
			format: 'dd M yy'
		}).on("changeDate",function(ev){
			console.log(ev.date);
			$scope.currentDateObject = ev.date;
			$scope.changeWeek($scope.currentDateObject);
		});
		
		var today = new Date();
		var todaysWeek = today.getWeek();
		var day =   today.getDay();
		if(day == 0) {
			startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			var endOfWeek = today;
			$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
					$.datepicker.formatDate('dd M yy', endOfWeek));
		} else {
			day = day - 1;
			startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
			var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
			$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
					$.datepicker.formatDate('dd M yy', endOfWeek));
		}
		
	}
	
	$scope.changeWeek = function(date) {
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
		console.log(data);
		$scope.projectCode = data.visitType;
		$scope.taskCode = data.taskCode;
		$scope.editStartTime = data.startTime;
		$scope.editEndTime = data.endTime;
		$scope.status = data.status;
		$scope.supplier = data.supplier;
		 $scope.customer = data.customer;
		 $scope.todayNotes = data.todayNotes;
		$scope.userId = $('#userID').val();
		 $scope.showMsg = false;
		 $scope.fileErr = false;
		
		$scope.taskDetail = {
			projectId:data.projectId,
			taskId:data.taskId,
			startTime:data.startTime,
			endTime:data.endTime,
			status:data.status,
			date:$scope.currentDate,
			userId:$scope.userId
		}
		
		$http({method:'GET',url:contextPath+'/getTaskDetails',params:{userId:$scope.userId,projectId:data.projectId,taskId:data.taskId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.documentsData = data.taskDetails;
			$scope.commentsList = data.commentDetails;
		});
		
		console.log($scope.editStartTime);
		
		ngDialog.open({
            template:contextPath+'/editSchedule',
            scope:$scope,
            closeByDocument:false
            
		});
		e.stopPropagation();
	}
	
	var file = null;
    $scope.selectFile = function(files) {
    	file = files[0];
    };
    $scope.fileErr = false;
    $scope.showMsg = false;
	$scope.saveAttachment = function() {
	
		if(file != null) {
			$scope.fileErr = false;
			$upload.upload({
	            url: contextPath+'/saveFile',
	            data: $scope.taskDetail,
	            file: file,
	            method:'post'
	        }).progress(function (evt) {
	            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
	        }).success(function (data, status, headers, config) {
	        	$scope.documentsData = data;
	        	$scope.showMsg = true;
	        });   
		} else {
			$scope.fileErr = true;
			$scope.showMsg = false;
		}
	}
	
	$scope.downloadfile = function(id) {
		$.fileDownload(contextPath+'/downloadTaskFile',
				{	   	
					   httpMethod : "POST",
					   data : {
						   attchId : id
					   }
				}).done(function(e, response)
						{
						}).fail(function(e, response)
						{
							// failure
						});
	}
	
	$scope.saveComment = function(comment) {
		$scope.userId = $('#userID').val();
		$http({method:'GET',url:contextPath+'/saveComment',params:{userId:$scope.userId,comment:comment,projectId:$scope.taskDetail.projectId,taskId:$scope.taskDetail.taskId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.commentsList = data;
			$scope.comment = "";
		});
	}
	
	$scope.setStatus = function(status) {
		
			$scope.userId = $('#userID').val();
			$http({method:'GET',url:contextPath+'/updateTaskStatus',params:{projectId:$scope.taskDetail.projectId,taskId:$scope.taskDetail.taskId,status:status}})
			.success(function(data) {
				console.log('success');
				$scope.getDataByWeek();
			});
	}
	
	$scope.addFunction = function(e) {
		console.log('add');
	}
	
	
});

app.controller("SchedularMonthController", function($scope,$http,$compile) {
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
			for(var i=0;i<response.monthVM.days.length;i++) {
				if(response.monthVM.days[i].isHoliday == true) {
					$('#day'+i).css("background-color","red");
				} else {
					$('#day'+i).removeAttr("style");
				}
			}
			$scope.currentDateMonth = response.monthVM.monthName;
			console.log('success');
			//console.log(JSON.stringify(response.monthVM));
			console.log($scope.monthVM);
		});
		console.log($scope.currentDateMonth);
	};
	
	$scope.getDateAppointment = function(month,date,year) {
		var htmlTemplate = "";
		$scope.userId = $('#userID').val();
		$scope.date = month+'/'+date+'/'+year;
		$scope.dayName = "";
		$http({method:'GET', url:contextPath+'/getDayDetails',params :{date:$scope.date,userId:$scope.userId}}).success(function(data) {
			console.log(data);
			if(data != "") {
				if(data[0].day == 'monday') {
					$scope.mondayData = data;
					$scope.dayName = "Monday";
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in mondayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(data[0].day == 'tuesday') {
					$scope.tuesdayData = data;
					$scope.dayName = "Tuesday";
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in tuesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(data[0].day == 'wednesday') {
					$scope.wednesdayData = data;
					$scope.dayName = "Wednesday";
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in wednesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(data[0].day == 'thursday') {
					$scope.thursdayData = data;
					$scope.dayName = "Thursday";
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in thursdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(data[0].day == 'friday') {
					$scope.fridayData = data;
					$scope.dayName = "Friday";
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in fridayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(data[0].day == 'saturday') {
					$scope.saturdayData = data;
					$scope.dayName = "Saturday";
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in saturdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				if(data[0].day == 'sunday') {
					$scope.sundayData = data;
					$scope.dayName = "Sunday";
					htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in sundayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
				}
				
				$.pnotify({
				    title: $scope.dayName+" "+$scope.date,
				    type:'info',
				    text: htmlTemplate,
				    addclass: data[0].day,
				    hide: false,
				    sticker: false
				});
				var element = $('.'+data[0].day);
				$compile(element)($scope);
			}
		});
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
			leaveType : 7,
			toDate : '',
			fromDate : '',
			organizationId : ''
	};
	
	$scope.initData = function() {
		$scope.userId = $('#userID').val();
		console.log($scope.userId);
		$scope.getOrganizations();
		$http({method:'GET',url:contextPath+'/getLeaveDetails',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.months = data.leaveList;
			$scope.weeks = data.weekList;
		});
	}
	
	$scope.getOrganizations = function() {
		$http({method:'GET',url:contextPath+'/getOrganizations',params:{userId:$scope.userId}})
		.success(function(data) {
			$scope.organizationList = data;
		});
	}
	
	$scope.showMessage = function(index) {
		$('#popupBtn').click();
		$scope.weekIndex = index;
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
	
	$scope.funct1 = function(_m, _d,reason,orgId)  {
		/*if(_d==0) {
			var year = new Date().getFullYear();
			if(_m < $scope.months[0].monthIndex){
				year += 1;
			}
			var d = new Date(year,_m,_d);
			$scope.staffLeaveVM.fromDate = (d.getMonth()+1)+'/'+d.getDate()+'/'+d.getFullYear();
			 ngDialog.open({
			    template: 'openHolidayPopup.html',
			    scope: $scope,
			    className: 'ngdialog-theme-default'
		  });
		}*/
		if (_d == '') return;
		var year = new Date().getFullYear();
		if(_m < $scope.months[0].monthIndex){
			year += 1;
		}
		//$scope.setSelectedDate(new Date(year,_m,_d));
		var d = new Date(year,_m,_d);
		$scope.staffLeaveVM.reason = reason;
		$scope.staffLeaveVM.organizationId = orgId;
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
		console.log($scope.staffLeaveVM.organizationId);
			$http({method:'POST', url:contextPath+'/markleaves', data:$scope.staffLeaveVM}).success(function(response) {
				$scope.weeks = response.weekList;
				$scope.months = response.leaveList;
				$scope.staffLeaveVM.fromDate ='';
				$scope.staffLeaveVM.reason = '';
				$scope.staffLeaveVM.selectType = '1';
		    });
	};
	
	
	
	
	
});





/*=========================================================================*/



app.controller("NewTimeSheetController", function($scope,$http,$compile) {
	  
	$scope.timesheetData = [];
	$scope.projectList = [];
	$scope.taskList = [];
	$scope.n_timesheetData = [];
	var popupClose;
	var oldData = [];
	var oldday = [];
	$scope.dayScope="day";
	$scope.dayData= [];
	
	$scope.weekDayData;
	$scope.isCopyFromLastweek = false;
	
	$scope.timeRegexp = /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
	$scope.ftTimeRegexp =  /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]-([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
	$scope.getTimesheetData = function(data) {
		
		
		if(!angular.isUndefined(data)) {
			
			$scope.userId = data.userId;
			$scope.wk = data.weekOfYear;
			$scope.yr = data.year;
			$("#weekValue").val(data.weekOfYear);
			$("#yearValue").val(data.year);
			$http({method:'GET',url:contextPath+'/getProjectCodes',params:{userId:$scope.userId}})
			.success(function(data) {
				console.log('success');
				console.log(data);
				$scope.projectList = data.projectList;
				$scope.supplierList = data.supplierList;
				$scope.customerList = data.customerList;
				$scope.getByWeek($scope.wk,$scope.yr);
			});
			
		} else {
			$scope.getUserProjects();
		}
		
		$scope.isCopyFromLastweek = false;
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
			if(ev.date.getDay() == 0 || ev.date.getDay() == 6) {
				$scope.getByWeek(ev.date.getWeek(),ev.date.getFullYear());
				$scope.weekOfYear = ev.date.getWeek();
			} else {
				$scope.getByWeek(ev.date.getWeek()+1,ev.date.getFullYear());
				$scope.weekOfYear = ev.date.getWeek()+1;
			}
			
			$scope.year = ev.date.getFullYear();
			console.log(startOfWeek);
		});
		
		if(!angular.isUndefined(data)) {
			var arr = data.date.split("/");
			var editDate = new Date(arr[2],arr[0],arr[1]);
			var sofw = new Date(editDate.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			var eofw = editDate;
			$('.week-picker').datepicker('setDate', sofw);
			$('.week-picker').val($.datepicker.formatDate('dd M yy', sofw) + " - " +
					$.datepicker.formatDate('dd M yy', eofw));
		} else {
				var today = new Date();
				if(today.getDay() == 0 || today.getDay() == 6) {
					var todaysWeek = today.getWeek();
					$("#weekValue").val(today.getWeek());
				} else {
					var todaysWeek = today.getWeek() + 1;
					$("#weekValue").val(today.getWeek() + 1);
				}
				$("#yearValue").val(today.getFullYear());
				var day =   today.getDay();
				if(day == 0) {
					startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * 6 ));
					var endOfWeek = today;
					$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
							$.datepicker.formatDate('dd M yy', endOfWeek));
				} else {
					day = day - 1;
					startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
					var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
					$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
							$.datepicker.formatDate('dd M yy', endOfWeek));
				}
	  }	
		
	}
	$scope.isShow = true;
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		
		if($scope.timesheetStatus == "Submitted" || $scope.timesheetStatus == "Approved") {
			$scope.isShow = false;
		} else {
			$scope.isShow = true;
		}
		
		if($scope.timesheetStatus == "Submitted") {
			$("#timeSheetTable :input").attr("readonly","readonly");
			$("#timeSheetTable select").attr("disabled","disabled");
			$("input:checkbox").attr("disabled","disabled");
			$("#copyFromLastWeek").attr("disabled","disabled");
			$("#saveTimesheetForm").attr("disabled","disabled");
			$("#submitTimesheetForm").attr("disabled","disabled");
			$("#retractTimesheetForm").removeAttr("disabled","disabled");
			$("#addMore").hide();
		} else {
			if($scope.timesheetStatus == "Approved") {
				$("#timeSheetTable :input").attr("readonly","readonly");
				$("#timeSheetTable select").attr("disabled","disabled");
				$("input:checkbox").attr("disabled","disabled");
				$("#copyFromLastWeek").attr("disabled","disabled");
				$("#saveTimesheetForm").attr("disabled","disabled");
				$("#submitTimesheetForm").attr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").hide();
			} else {
				$("#timeSheetTable :input").removeAttr("readonly","readonly");
				$("#timeSheetTable select").removeAttr("disabled","disabled");
				$("input:checkbox").removeAttr("disabled","disabled");
				$("#copyFromLastWeek").removeAttr("disabled","disabled");
				$("#saveTimesheetForm").removeAttr("disabled","disabled");
				$("#submitTimesheetForm").removeAttr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").show();
			}
		}
	});
	
	$scope.getUserProjects = function() {
		$scope.userId = $('#employeeID').val();
		$http({method:'GET',url:contextPath+'/getProjectCodes',params:{userId:$scope.userId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.projectList = data.projectList;
			$scope.supplierList = data.supplierList;
			$scope.customerList = data.customerList;
			
			$scope.projectLst = data.projectList;
			$scope.supplierLst = data.supplierList;
			$scope.customerLst = data.customerList;
			$scope.getTimesheetByWeek();
		});
		
	}
	
	$scope.getByWeek = function(week,year) {
		
		$("#timeSheetTable :input").removeAttr("readonly","readonly");
		$("#timeSheetTable select").removeAttr("disabled","disabled");
		$("input:checkbox").removeAttr("disabled","disabled");
		$("#copyFromLastWeek").removeAttr("disabled","disabled");
		$("#saveTimesheetForm").removeAttr("disabled","disabled");
		$("#submitTimesheetForm").removeAttr("disabled","disabled");
		
		
		$http({method:'GET',url:contextPath+'/getActualTimesheetBySelectedWeek',params:{userId:$scope.userId,week:week,year:year}})
		.success(function(data) {
			oldData=[];
			$scope.n_timesheetData=[];
			console.log('success');
			console.log(data);
			if(data.timesheetData.id != null) {
				$scope.weekOfYear = data.timesheetData.weekOfYear;
				$scope.year = data.timesheetData.year;
				$scope.timesheetStatus = data.timesheetData.status;
				$scope.timesheetId = data.timesheetData.id;
				$scope.timesheetData = data.timesheetData.timesheetRows;
				
				angular.forEach($scope.timesheetData, function(value, key){
					var o = $.extend({},value);
					oldData.push(o);
					$scope.n_timesheetData.push(o);
					});
				
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode,i);
				}
				
				if(data.timesheetData.status == "Submitted") {
					$scope.isShow = false;
					$("#timeSheetTable :input").attr("readonly","readonly");
					$("#timeSheetTable select").attr("disabled","disabled");
					$("input:checkbox").attr("disabled","disabled");
					$("#copyFromLastWeek").attr("disabled","disabled");
					$("#saveTimesheetForm").attr("disabled","disabled");
					$("#submitTimesheetForm").attr("disabled","disabled");
					$("#retractTimesheetForm").removeAttr("disabled","disabled");
					$("#addMore").hide();
				} else {
					if(data.timesheetData.status == "Approved") {
						$scope.isShow = false;
						$("#timeSheetTable :input").attr("readonly","readonly");
						$("#timeSheetTable select").attr("disabled","disabled");
						$("input:checkbox").attr("disabled","disabled");
						$("#copyFromLastWeek").attr("disabled","disabled");
						$("#saveTimesheetForm").attr("disabled","disabled");
						$("#submitTimesheetForm").attr("disabled","disabled");
						$("#retractTimesheetForm").attr("disabled","disabled");
						$("#addMore").hide();
					} else {
						$scope.isShow = true;
						$("#retractTimesheetForm").attr("disabled","disabled");
						$("#addMore").show();
					}
				}
				
				
			} else {
				$scope.timesheetData =[];
				//$scope.addMore();
				$scope.timesheetStatus = "";
				$scope.timesheetId = null;
				$("#addMore").show();
				$scope.isShow = true;
				
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
				
			}
		});	
	}
	
	$scope.getTimesheetByWeek = function() {
		$http({method:'GET',url:contextPath+'/getActualTimesheetByCurrentWeek',params:{userId:$scope.userId}})
		.success(function(data) {
			oldData=[];
			$scope.n_timesheetData=[];
			console.log('success');
			console.log(data);
			if(data.timesheetData.id != null) {
				$scope.weekOfYear = data.timesheetData.weekOfYear;
				$scope.year = data.timesheetData.year;
				$scope.timesheetStatus = data.timesheetData.status;
				$scope.timesheetId = data.timesheetData.id;
				$scope.timesheetData = data.timesheetData.timesheetRows;
				
				angular.forEach($scope.timesheetData, function(value, key){
					var o = $.extend({},value);
					oldData.push(o);
					$scope.n_timesheetData.push(o);
					});
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
				
				for(var i=0;i<$scope.timesheetData.length;i++) {
					$scope.setTaskOfProject($scope.timesheetData[i].projectCode,i);
				}
				
				
			} else {
				//$scope.addMore();
				
				if(data.holidayList[0] == true) {
					$('#sunLabel').css("color","red");
				} else{
					$('#sunLabel').removeAttr("style");
				}
				if(data.holidayList[1] == true) {
					$('#monLabel').css("color","red");
				} else{
					$('#monLabel').removeAttr("style");
				}
				if(data.holidayList[2] == true) {
					$('#tueLabel').css("color","red");
				} else{
					$('#tueLabel').removeAttr("style");
				}
				if(data.holidayList[3] == true) {
					$('#wedLabel').css("color","red");
				} else{
					$('#wedLabel').removeAttr("style");
				}
				if(data.holidayList[4] == true) {
					$('#thuLabel').css("color","red");
				} else{
					$('#thuLabel').removeAttr("style");
				}
				if(data.holidayList[5] == true) {
					$('#friLabel').css("color","red");
				} else{
					$('#friLabel').removeAttr("style");
				}
				if(data.holidayList[6] == true) {
					$('#satLabel').css("color","red");
				} else{
					$('#satLabel').removeAttr("style");
				}
				
			}
			
		});
	}
	
	$scope.confirmCopy = function() {
		$('#popupBtn2').click();
	}
	
	$scope.copyFromLastWeek = function() {
		
		
		
			$scope.weekOfYear = $('#weekValue').val();
			$scope.year = $('#yearValue').val();
			
			$http({method:'GET',url:contextPath+'/getActualTimesheetByLastWeek',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				olData=[];
				$scope.n_timesheetData =[];
				console.log('success');
				$('#lastWeekClose').click();
				console.log(data);
				if(data.id != null) {
					$scope.weekOfYear = data.weekOfYear;
					$scope.year = data.year;
					$scope.timesheetStatus = data.status;
					
					$scope.timesheetData = data.timesheetRows;
					$scope.isCopyFromLastweek = true;
					
					angular.forEach($scope.timesheetData, function(value, key){
						var o = $.extend({},value);
						oldData.push(o);
						$scope.n_timesheetData.push(o);
						});
					
					for(var i=0;i<$scope.timesheetData.length;i++) {
						$scope.setTaskOfProject($scope.timesheetData[i].projectCode,i);
						$scope.timesheetData[i].rowId = 0;
					}
					
				} else {
					$('#popupBtn3').click();
				}
				
				
				
			});
		
		
		
	}
	
	$scope.addCustomerAndSupplier = function(index,day) {
		$scope.rowIndex = index;
		$scope.dayVal = day;
		if($scope.dayVal == 'mon') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].monSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].monCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].monNotes;
		}
		if($scope.dayVal == 'tue') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].tueSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].tueCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].tueNotes;
		}
		if($scope.dayVal == 'wed') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].wedSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].wedCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].wedNotes;
		}
		if($scope.dayVal == 'thu') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].thuSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].thuCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].thuNotes;
		}
		if($scope.dayVal == 'fri') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].friSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].friCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].friNotes;
		}
		if($scope.dayVal == 'sat') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].satSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].satCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].satNotes;
		}
		if($scope.dayVal == 'sun') {
			$scope.supplierCode = $scope.timesheetData[$scope.rowIndex].sunSupplier;
			$scope.customerCode = $scope.timesheetData[$scope.rowIndex].sunCustomer;
			$scope.notes = $scope.timesheetData[$scope.rowIndex].sunNotes;
		}
		$('#popupBtn5').click();
	}
	
	$scope.addData = function() {
		if($scope.dayVal == 'mon') {
			$scope.timesheetData[$scope.rowIndex].monSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].monCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].monNotes = $scope.notes;
		}
		if($scope.dayVal == 'tue') {
			$scope.timesheetData[$scope.rowIndex].tueSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].tueCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].tueNotes = $scope.notes;
		}
		if($scope.dayVal == 'wed') {
			$scope.timesheetData[$scope.rowIndex].wedSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].wedCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].wedNotes = $scope.notes;
		}
		if($scope.dayVal == 'thu') {
			$scope.timesheetData[$scope.rowIndex].thuSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].thuCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].thuNotes = $scope.notes;
		}
		if($scope.dayVal == 'fri') {
			$scope.timesheetData[$scope.rowIndex].friSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].friCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].friNotes = $scope.notes;
		}
		if($scope.dayVal == 'sat') {
			$scope.timesheetData[$scope.rowIndex].satSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].satCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].satNotes = $scope.notes;
		}
		if($scope.dayVal == 'sun') {
			$scope.timesheetData[$scope.rowIndex].sunSupplier = $scope.supplierCode;
			$scope.timesheetData[$scope.rowIndex].sunCustomer = $scope.customerCode;
			$scope.timesheetData[$scope.rowIndex].sunNotes = $scope.notes;
		}
		
		if($scope.timesheetData[$scope.rowIndex].rowId != 0 || !angular.isUndefined($scope.timesheetData[$scope.rowIndex].rowId)) {
			if($scope.dayVal == 'mon') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].mondayId;
			}
			if($scope.dayVal == 'tue') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].tuesdayId;
			}
			if($scope.dayVal == 'wed') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].wednesdayId;
			}
			if($scope.dayVal == 'thu') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].thursdayId;
			}
			if($scope.dayVal == 'fri') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].fridayId;
			}
			if($scope.dayVal == 'sat') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].saturdayId;
			}
			if($scope.dayVal == 'sun') {
				$scope.dayId = $scope.timesheetData[$scope.rowIndex].sundayId;
			}
			
			if($scope.supplierCode==null)
			{
			$scope.supplierCode=0;
			
			}
			if( $scope.customerCode==null )
			{
			$scope.customerCode=0;
			}
		
			
			$http({method:'GET',url:contextPath+'/addSupplierCustomerToActual',params:{dayId:$scope.dayId,supplierCode:$scope.supplierCode,customerCode:$scope.customerCode,notes:$scope.notes}})
			.success(function(data) {
				console.log('success');
				
			});
			
		}
		$('#modal5Close').click();
	}
	
	
$scope.confirmDelete1 = function() {
		$('#popupBtn6').click();
		$('#deleteClose').click();
		if($scope.dayScope=='mon'){
			if($scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].monTo="";
				$scope.timesheetData[$scope.deleteIndex1].monFrom="";
				$scope.timesheetData[$scope.deleteIndex1].monFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].monNotes="";
				$scope.timesheetData[$scope.deleteIndex1].monSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].monCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='tue'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].tueTo="";
				$scope.timesheetData[$scope.deleteIndex1].tueFrom="";
				$scope.timesheetData[$scope.deleteIndex1].tueFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].tueNotes="";
				$scope.timesheetData[$scope.deleteIndex1].tueSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].tueCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='wed'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].wedTo="";
				$scope.timesheetData[$scope.deleteIndex1].wedFrom="";
				$scope.timesheetData[$scope.deleteIndex1].wedFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].wedNotes="";
				$scope.timesheetData[$scope.deleteIndex1].wedSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].wedCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='thu'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].thuTo="";
				$scope.timesheetData[$scope.deleteIndex1].thuFrom="";
				$scope.timesheetData[$scope.deleteIndex1].thuFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].thuNotes="";
				$scope.timesheetData[$scope.deleteIndex1].thuSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].thuCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='fri'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].friTo="";
				$scope.timesheetData[$scope.deleteIndex1].friFrom="";
				$scope.timesheetData[$scope.deleteIndex1].friFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].friNotes="";
				$scope.timesheetData[$scope.deleteIndex1].friSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].friCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='sat'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].sunFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].satTo="";
				$scope.timesheetData[$scope.deleteIndex1].satFrom="";
				$scope.timesheetData[$scope.deleteIndex1].satFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].satNotes="";
				$scope.timesheetData[$scope.deleteIndex1].satSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].satCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}
		if($scope.dayScope=='sun'){
			if($scope.timesheetData[$scope.deleteIndex1].monFromTo==null && $scope.timesheetData[$scope.deleteIndex1].tueFromTo==null && $scope.timesheetData[$scope.deleteIndex1].thuFromTo==null && $scope.timesheetData[$scope.deleteIndex1].friFromTo==null && $scope.timesheetData[$scope.deleteIndex1].satFromTo==null  && $scope.timesheetData[$scope.deleteIndex1].wedFromTo==null){
				$scope.removeRow();
				console.log("delete row...");
			}
			else{
				$scope.timesheetData[$scope.deleteIndex1].sunTo="";
				$scope.timesheetData[$scope.deleteIndex1].sunFrom="";
				$scope.timesheetData[$scope.deleteIndex1].sunFromTo="";
				$scope.timesheetData[$scope.deleteIndex1].sunNotes="";
				$scope.timesheetData[$scope.deleteIndex1].sunSupplier="";
				$scope.timesheetData[$scope.deleteIndex1].sunCustomer="";
				$scope.dayData.splice($scope.deleteIndex,1);
				$scope.taskLstArray.splice($scope.deleteIndex,1);
				$scope.saveTimesheet('Draft');
				console.log("delte update...");
			}
		}

		
	}
	
	
	
	$scope.addMore = function() {
		$scope.timesheetData.push({});
    }
	
	$scope.addMore1 = function() {
		$scope.dayData.push({});
    
	}
	
	$scope.confirmDelete = function(index,rowId) {
		/*$scope.deleteIndex = index;
		$scope.deleteRowId = rowId;
		$('#popupBtn4').click();*/

		$scope.deleteIndex = index;
		$scope.deleteRowId = rowId;
		console.log($scope.deleteRowId);
		for(var i=0;i<$scope.timesheetData.length;i++)
			if($scope.timesheetData[i].rowId==$scope.deleteRowId)
			$scope.deleteIndex1 = i;
		$('#popupBtn4').click();
	}
	$scope.addDataOnPlus = function(day1)
	{
		$scope.headerData="";		
		$scope.taskLstArray = [];		
		$scope.dayData = [];		
		oldday = [];		
		console.log($scope.taskLstArray);		
		var k= 0;		
		var f=0;		
		if (day1=='mon'){		
			k = 0;		
			 $scope.dayScope="mon";		
			 $scope.headerData="Monday Data";
			 console.log("mon call");
			for(var j=0; j<$scope.n_timesheetData.length;j++) {		
				f=0;		
				if(($scope.n_timesheetData[j].monTo!=null) && ($scope.n_timesheetData[j].monFrom!=null)){					
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].monCustomer;		
				$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].monSupplier;		
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].monCustomer;		
				$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].monNotes;		
				console.log($scope.n_timesheetData[j]);		
				$scope.dayData.push($scope.n_timesheetData[j]);		
				oldday.push($scope.n_timesheetData[j]);		
						
			 for(var i=0;i<$scope.projectLst.length;i++) {		
					if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {		
						f++;		
						if(f==1)		
						{		
						$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;		
						k++;		
						}		
								
					}		
				}		
			  }		
			};		
		}		
				
		if (day1=='tue')		
			{		
			k = 0;		
			 $scope.dayScope="tue";		
			 $scope.headerData="Tuesday Data";		
			for(var j=0;j<$scope.n_timesheetData.length;j++) {		
					f=0;		
					if(($scope.n_timesheetData[j].tueTo!=null) && ($scope.n_timesheetData[j].tueFrom!=null)){		
					$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].tueSupplier;		
					$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].tueCustomer;		
					$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].tueNotes;			
					$scope.dayData.push($scope.n_timesheetData[j]);		
					oldday.push($scope.n_timesheetData[j]);		
					for(var i=0;i<$scope.projectLst.length;i++) {				
							if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {		
							f++		
							if(f==1)		
							{		
							$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;		
							k++;		
							}		
						}		
					}		
					}		
				};		
			}		
		if (day1=='wed')		
		{		
			k=0;		
		 $scope.dayScope="wed";		
		 $scope.headerData="Wednesday Data";		
		for(var j=0;j<$scope.n_timesheetData.length;j++) {		
			f=0;		
			if(($scope.n_timesheetData[j].wedTo!=null) && ($scope.n_timesheetData[j].wedFrom!=null))		
			{		
			$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].wedSupplier;		
			$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].wedCustomer;		
			$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].wedNotes;				
			$scope.dayData.push($scope.n_timesheetData[j]);		
			oldday.push($scope.n_timesheetData[j]);		
			for(var i=0;i<$scope.projectLst.length;i++) {		
				if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {		
					f++;		
					if(f==1){		
					$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;		
					k++;		
					}		
				}		
			  }		
			}		
			};		
		}		
		if (day1=='thu')		
		{		
			k=0;		
		 $scope.dayScope="thu";		
		 $scope.headerData="Thursday Data";		
		for(var j=0;j<$scope.n_timesheetData.length;j++) {		
			f=0;		
			if(($scope.n_timesheetData[j].thuTo!=null) && ($scope.n_timesheetData[j].thuFrom!=null)){		
			$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].thuSupplier;		
			$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].thuCustomer;		
			$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].thuNotes;		
			$scope.dayData.push($scope.n_timesheetData[j]);		
			oldday.push($scope.n_timesheetData[j]);		
			for(var i=0;i<$scope.projectLst.length;i++) {		
				if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {		
					f++;		
					if(f==1){		
					$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;		
					k++;		
					}		
				}		
		   	 }		
			}		
			};		
		}		
		if (day1=='fri')		
		{		
		 $scope.dayScope="fri";		
		 $scope.headerData="Friday Data";		
		 console.log(" fri  called");		
		for(var j=0;j<$scope.n_timesheetData.length;j++) {		
			f=0;		
			if(($scope.n_timesheetData[j].friTo!=null) && ($scope.n_timesheetData[j].friFrom!=null)){		
			$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].friSupplier;		
			$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].friCustomer;		
			$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].friNotes;			
			$scope.dayData.push($scope.n_timesheetData[j]);		
			oldday.push($scope.n_timesheetData[j]);		
			for(var i=0;i<$scope.projectLst.length;i++) {		
						
				if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {		
					f++;		
					if(f==1){		
					$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;		
					k++;		
					}		
				}		
			   }		
			  }		
			};		
		}			
			if (day1=='sat')		
			{		
			 $scope.dayScope="sat";		
			 $scope.headerData="Saturday Data";		
			 console.log(" sat  called");		
			for(var j=0;j<$scope.n_timesheetData.length;j++) {		
				f=0;		
				if(($scope.n_timesheetData[j].satTo!=null) && ($scope.n_timesheetData[j].satFrom!=null)){		
				$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].satSupplier;		
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].satCustomer;		
				$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].satNotes;			
				$scope.dayData.push($scope.n_timesheetData[j]);		
				oldday.push($scope.n_timesheetData[j]);		
				for(var i=0;i<$scope.projectLst.length;i++) {		
					if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {		
						f++		
						if(f==1){		
						$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;		
						k++;		
						}		
					}		
			      }		
				}		
				};		
			}		
			if (day1=='sun')		
			{		
			 $scope.dayScope="sun";		
			 $scope.headerData="Sunday Data";		
			 console.log(" sun  called");		
			for(var j=0;j<$scope.n_timesheetData.length;j++) {		
				f=0;		
				if(($scope.n_timesheetData[j].sunTo!=null) && ($scope.n_timesheetData[j].sunFrom!=null)){		
				$scope.n_timesheetData[j].supplier=$scope.n_timesheetData[j].sunSupplier;		
				$scope.n_timesheetData[j].customer = $scope.n_timesheetData[j].sunCustomer;		
				$scope.n_timesheetData[j].note = $scope.n_timesheetData[j].sunNotes;		
				$scope.dayData.push($scope.n_timesheetData[j]);		
				oldday.push($scope.n_timesheetData[j]);		
				for(var i=0;i<$scope.projectLst.length;i++) {		
					if($scope.projectLst[i].id == $scope.n_timesheetData[j].projectCode) {		
						f++;		
						if(f==1){		
						$scope.taskLstArray[k] = $scope.projectLst[i].tasklist;		
						k++;		
						}		
					}		
				  }		
				}		
				};		
			}		
	
		$('#popupBtn6').click();
	}
	
	
	
	$scope.removeRow = function() {
		
			if(!angular.isUndefined($scope.deleteRowId) && $scope.deleteRowId != 0) {
					
					$http({method:'GET',url:contextPath+'/deleteActualTimesheetRow',params:{rowId:$scope.deleteRowId}})
					.success(function(data) {
						console.log('success');
						$.pnotify({
						    title: "Success",
						    type:'success',
						    text: "Timesheet Row Deleted Successfully",
						});
					});
					
				}
			$scope.dayData.splice($scope.deleteIndex,1);		
			$scope.n_timesheetData.splice($scope.deleteIndex1,1);
			$scope.timesheetData.splice($scope.deleteIndex1,1);
			
			console.log($scope.timesheetData);
			$scope.taskListArray.splice($scope.deleteIndex1,1);
			$scope.taskLstArray.splice($scope.deleteIndex,1);
			
			$('#deleteClose').click();
			angular.forEach($scope.timesheetData, function(value, key){
				 oldData.push(value);
				    })
		
	}
	
	$scope.confirmRetract = function() {
		$('#popupBtn').click();
	}
	
	$scope.retractTimesheet = function() {
		$scope.userId = $('#employeeID').val();
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		
			$http({method:'GET',url:contextPath+'/actualTimesheetRetract',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year}})
			.success(function(data) {
				console.log('success');
				$("#timeSheetTable :input").removeAttr("readonly","readonly");
				$("#timeSheetTable select").removeAttr("disabled","disabled");
				$("input:checkbox").removeAttr("disabled","disabled");
				$("#copyFromLastWeek").removeAttr("disabled","disabled");
				$("#saveTimesheetForm").removeAttr("disabled","disabled");
				$("#submitTimesheetForm").removeAttr("disabled","disabled");
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").show();
				$scope.isShow = true;
				$scope.timesheetStatus = "Draft";
			});
		$('#retractClose').click();
	}
	
	$scope.taskListArray = [];
	$scope.setTaskOfProject = function(projectId,index) {
		console.log(projectId);
		for(var i=0;i<$scope.projectList.length;i++) {
			if($scope.projectList[i].id == projectId) {
				$scope.taskListArray[index] = $scope.projectList[i].tasklist;
			}
		}
	}
	
	$scope.checkTime = function(time) {
		
	}
	
	$scope.splitToFTTime = function (row, day) {
		if(day === 'mon'){
			if(row.monFromTo) {
				var from = row.monFromTo.split('-')[0];
				if(from) {
				   row.monFrom = from;	
				}
				
				var to = row.monFromTo.split('-')[1];
				if(to) {
					row.monTo = to;
				}
			}
		}
		if(day === 'tue'){
			if(row.tueFromTo) {
				var from = row.tueFromTo.split('-')[0];
				if(from) {
				   row.tueFrom = from;	
				}
				
				var to = row.tueFromTo.split('-')[1];
				if(to) {
					row.tueTo = to;
				}
			}
		}
		if(day === 'wed'){
			if(row.wedFromTo) {
				var from = row.wedFromTo.split('-')[0];
				if(from) {
				   row.wedFrom = from;	
				}
				
				var to = row.wedFromTo.split('-')[1];
				if(to) {
					row.wedTo = to;
				}
			}
		}
		
		if(day === 'thu'){
			if(row.thuFromTo) {
				var from = row.thuFromTo.split('-')[0];
				if(from) {
				   row.thuFrom = from;	
				}
				
				var to = row.thuFromTo.split('-')[1];
				if(to) {
					row.thuTo = to;
				}
			}
		}
		if(day === 'fri'){
			if(row.friFromTo) {
				var from = row.friFromTo.split('-')[0];
				if(from) {
				   row.friFrom = from;	
				}
				
				var to = row.friFromTo.split('-')[1];
				if(to) {
					row.friTo = to;
				}
			}
		}
		if(day === 'sat'){
			if(row.satFromTo) {
				var from = row.satFromTo.split('-')[0];
				if(from) {
				   row.satFrom = from;	
				}
				
				var to = row.satFromTo.split('-')[1];
				if(to) {
					row.satTo = to;
				}
			}
		}
		if(day === 'sun'){
			if(row.sunFromTo) {
				var from = row.sunFromTo.split('-')[0];
				if(from) {
				   row.sunFrom = from;	
				}
				
				var to = row.sunFromTo.split('-')[1];
				if(to) {
					row.sunTo = to;
				}
			}
		}
	}
	
	$scope.initFTTime = function (row, day) {
		if(day === 'mon') {
			if(row.monFrom && row.monTo) {
				row.monFromTo = row.monFrom + '-' + row.monTo;
			}
		}
		if(day === 'tue') {
			if(row.tueFrom && row.tueTo) {
				row.tueFromTo = row.tueFrom + '-' + row.tueTo;
			}
		}
		if(day === 'wed') {
			if(row.wedFrom && row.wedTo) {
				row.wedFromTo = row.wedFrom + '-' + row.wedTo;
			}
		}
		if(day === 'thu') {
			if(row.thuFrom && row.thuTo) {
				row.thuFromTo = row.thuFrom + '-' + row.thuTo;
			}
		}
		if(day === 'fri') {
			if(row.friFrom && row.friTo) {
				row.friFromTo = row.friFrom + '-' + row.friTo;
			}
		}
		if(day === 'sat') {
			if(row.satFrom && row.satTo) {
				row.satFromTo = row.satFrom + '-' + row.satTo;
			}
		}
		if(day === 'sun') {
			if(row.sunFrom && row.sunTo) {
				row.sunFromTo = row.sunFrom + '-' + row.sunTo;
			}
		}
	}
	
	$scope.getWeekDayData = function(day) {
		var htmlTemplate = "";
		$scope.userId = $('#employeeID').val();
		$scope.weekOfYear = $('#weekValue').val();
		$scope.year = $('#yearValue').val();
		console.log(day);
		$scope.dayName = "";
		$http({method:'GET',url:contextPath+'/getWeekDayData',params:{userId:$scope.userId,week:$scope.weekOfYear,year:$scope.year,day:day}})
		.success(function(data) {
			console.log("-----dta---------");
			console.log(data);
			if(day == 'monday') {
				$scope.mondayData = data;
				$scope.dayName = "Monday";
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in mondayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'tuesday') {
				$scope.tuesdayData = data;
				$scope.dayName = "Tuesday";
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in tuesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'wednesday') {
				$scope.wednesdayData = data;
				$scope.dayName = "Wednesday";
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in wednesdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'thursday') {
				$scope.thursdayData = data;
				$scope.dayName = "Thursday";
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in thursdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'friday') {
				$scope.fridayData = data;
				$scope.dayName = "Friday";
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in fridayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'saturday') {
				$scope.saturdayData = data;
				$scope.dayName = "Saturday";
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in saturdayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			if(day == 'sunday') {
				$scope.sundayData = data;
				$scope.dayName = "Sunday";
				htmlTemplate = '<table class="table"><tr><td>Project Code</td><td>Task Code</td><td>From</td><td>To</td></tr><tr ng-repeat="dayData in sundayData"><td>{{dayData.projectCode}}</td><td>{{dayData.taskCode}}</td><td>{{dayData.from}}</td><td>{{dayData.to}}</td></tr></table>';
			}
			console.log(data);
			$.pnotify({
			    title: $scope.dayName,
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
	$scope.taskLstArray = [];
	$scope.setTaskOfPrj = function(projectId,index) {
		
		for(var i=0;i<$scope.projectLst.length;i++) {
			if($scope.projectLst[i].id == projectId) {
				$scope.taskLstArray[index] = $scope.projectLst[i].tasklist;
			}
		}
	}
	
	/*update rows*/

	$scope.updateRows = function() {
		var flg = 0;
		var isPresent;
		var len = oldday.length;
		var newlen= $scope.dayData.length - oldday.length;	
		console.log("new day data ");
		console.log($scope.dayData);
		console.log("old day data ");
		console.log(oldday);
		console.log("old db data ");
		console.log(oldData);
		
		for(var i=0;i<$scope.dayData.length;i++){
			if($scope.dayScope=='mon'){
				 $scope.dayData[i].monSupplier=$scope.dayData[i].supplier;
				 $scope.dayData[i].monCustomer=$scope.dayData[i].customer;
				 $scope.dayData[i].monNotes=$scope.dayData[i].note;
			}
			
			if($scope.dayScope=='tue'){
				 $scope.dayData[i].tueSupplier=$scope.dayData[i].supplier;
				 $scope.dayData[i].tueCustomer=$scope.dayData[i].customer;
				 $scope.dayData[i].tueNotes=$scope.dayData[i].note;
			}
			if($scope.dayScope=='wed'){
				 $scope.dayData[i].wedSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].wedCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].wedNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='thu'){
				 $scope.dayData[i].thuSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].thuCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].thuNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='fri'){
				 $scope.dayData[i].friSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].friCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].friNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='sat'){
				 $scope.dayData[i].satSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].satCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].satNotes=$scope.dayData[i].note ;
			}
			if($scope.dayScope=='sun'){
				 $scope.dayData[i].sunSupplier=$scope.dayData[i].supplier ;
				 $scope.dayData[i].sunCustomer=$scope.dayData[i].customer ;
				 $scope.dayData[i].sunNotes=$scope.dayData[i].note ;
			}
		}
		
		
		for(var i=0;i<newlen;i++){
			flg=0;
			isPresent=true;
			for(var j=0;j<oldData.length;j++) {		
				if(($scope.dayData[len+i].projectCode==oldData[j].projectCode) && ($scope.dayData[len+i].taskCode==oldData[j].taskCode)){
					if($scope.dayScope=='mon'){
						if((oldData[j].monTo == null) && (oldData[j].monFrom==null) && isPresent==true){
							flg++;
							oldData[j].monTo = $scope.dayData[len+i].monTo;
							oldData[j].monFrom = $scope.dayData[len+i].monFrom;
							oldData[j].monSupplier = $scope.dayData[len+i].supplier;
							oldData[j].monCustomer = $scope.dayData[len+i].customer;
							oldData[j].monNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" mon updated....");
						}
					}
					if($scope.dayScope=='tue'){
						if((oldData[j].tueTo == null) && (oldData[j].tueFrom==null) && isPresent==true){
							flg++;
							oldData[j].tueTo = $scope.dayData[len+i].tueTo;
							oldData[j].tueFrom = $scope.dayData[len+i].tueFrom;
							oldData[j].tueSupplier = $scope.dayData[len+i].supplier;
							oldData[j].tueCustomer = $scope.dayData[len+i].customer;
							oldData[j].tueNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" tue updated....");
						}
					}
					if($scope.dayScope=='wed'){
						if((oldData[j].wedTo == null) && (oldData[j].wedFrom==null) && isPresent==true){
							flg++;	
							oldData[j].wedTo = $scope.dayData[len+i].wedTo;
							oldData[j].wedFrom = $scope.dayData[len+i].wedFrom;
							oldData[j].wedSupplier = $scope.dayData[len+i].supplier;
							oldData[j].wedCustomer = $scope.dayData[len+i].customer;
							oldData[j].wedNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" wed updated....");
						}
					}
					if($scope.dayScope=='thu'){
						if((oldData[j].thuTo == null) && (oldData[j].thuFrom==null) && isPresent==true){
							flg++;	
							oldData[j].thuTo = $scope.dayData[len+i].thuTo;
							oldData[j].thuFrom = $scope.dayData[len+i].thuFrom;
							oldData[j].thuSupplier = $scope.dayData[len+i].supplier;
							oldData[j].thuCustomer = $scope.dayData[len+i].customer;
							oldData[j].thuNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" thu updated....");
						}
					}
					if($scope.dayScope=='fri'){
						if((oldData[j].friTo == null) && (oldData[j].friFrom==null) && isPresent==true){
							flg++;
							oldData[j].friTo = $scope.dayData[len+i].friTo;
							oldData[j].friFrom = $scope.dayData[len+i].friFrom;
							oldData[j].friSupplier = $scope.dayData[len+i].supplier;
							oldData[j].friCustomer = $scope.dayData[len+i].customer;
							oldData[j].friNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" fri updated....");
						}
					}
					if($scope.dayScope=='sat'){
						if((oldData[j].satTo == null) && (oldData[j].satFrom == null) && isPresent==true){
							flg++;
							oldData[j].satTo = $scope.dayData[len+i].satTo;
							oldData[j].satFrom = $scope.dayData[len+i].satFrom;
							oldData[j].satSupplier = $scope.dayData[len+i].supplier;
							oldData[j].satCustomer = $scope.dayData[len+i].customer;
							oldData[j].satNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" sat updated....");
						}
					}
					if($scope.dayScope=='sun'){
						if((oldData[j].sunTo == null) && (oldData[j].sunFrom==null) && isPresent==true){
							flg++;
							oldData[j].sunTo = $scope.dayData[len+i].sunTo;
							oldData[j].sunFrom = $scope.dayData[len+i].sunFrom;
							oldData[j].sunSupplier = $scope.dayData[len+i].supplier;
							oldData[j].sunCustomer = $scope.dayData[len+i].customer;
							oldData[j].sunNotes = $scope.dayData[len+i].note;
							isPresent=false;
							console.log(" sun updated....");
						}
					}
				}	
			}	
			if(flg==0){
				$scope.timesheetData.push($scope.dayData[len+i]);
				console.log("not updated added");
			}				
		}

		if(flg>0){
			$scope.timesheetData = oldData;
			flg=0;
		}

		$('#popupBtn6').click();
		popupClose= true;
			$scope.saveTimesheet('Draft');
			
	};
	
	
	/*=============================================*/
	
	
	$scope.saveTimesheet = function(status) {
		oldData=[];
		if($scope.isCopyFromLastweek == true) {
			$http({method:'GET',url:contextPath+'/deleteActualTimesheet',params:{timesheetId:$scope.timesheetId}})
			.success(function(data) {
				console.log('success');
				$scope.isCopyFromLastweek = false;
			});
		}
		
		
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
		$http({method:'POST',url:contextPath+'/saveActualTimesheet',data:$scope.timesheet}).success(function(data) {
			console.log('success');
			$scope.timesheetData = data.timesheetRows;
			$scope.timesheetStatus = status;
			if($scope.timesheet.status == "Submitted") {
				$("#timeSheetTable :input").attr("readonly","readonly");
				$("#timeSheetTable select").attr("disabled","disabled");
				$("input:checkbox").attr("disabled","disabled");
				$("#copyFromLastWeek").attr("disabled","disabled");
				$("#saveTimesheetForm").attr("disabled","disabled");
				$("#submitTimesheetForm").attr("disabled","disabled");
				$("#retractTimesheetForm").removeAttr("disabled","disabled");
				$("#addMore").hide();
				$scope.isShow = false;
			} else {
				$("#retractTimesheetForm").attr("disabled","disabled");
				$("#addMore").show();
				$scope.isShow = true;
			}
			if($scope.timesheet.status == "Submitted") {
				$scope.msg = "Timesheet Submitted Successfully";
			} else {
				$scope.msg = "Timesheet Saved Successfully";
			}
			$.pnotify({
			    title: "Success",
			    type:'success',
			    text: $scope.msg,
			});
			
			
			$scope.n_timesheetData=[];
			console.log("after save ");
			console.log($scope.timesheetData);
			angular.forEach($scope.timesheetData, function(value, key){
				 oldData.push(value);
				 $scope.n_timesheetData.push(value);
				    });
			if(popupClose==false)
			$('#popupBtn6').click();
		});
		
	}
	
});

/*========================================End Of NewTimesheet Conrtoller=====================================*/



app.controller("SchedularTodayAllController", function($scope,$http,ngDialog,$upload) {
	
	var d = new Date();
	$scope.currentDate = (d.getMonth()+1)+"/"+d.getDate()+"/"+d.getFullYear();
	$scope.unitValueInMin = 60;
	$scope.gradationBetweenPerUnit = 15;
	$scope.gradationBetweenPerUnitpx = 50;
	var d1 = new Date($scope.currentDate);
	$scope.currentDateObject = new Date();
	
	$scope.addFunction = function() {
		
	};
	
	$scope.editFunction = function(e,data) {
		$scope.projectCode = data.visitType;
		$scope.taskCode = data.taskCode;
		$scope.editStartTime = data.startTime;
		$scope.editEndTime = data.endTime;
		$scope.status = data.status;
		$scope.userId = data.staffId;
		$scope.supplier = data.supplier;
		 $scope.customer = data.customer;
		 $scope.todayNotes = data.todayNotes;
		 $scope.showMsg = false;
		 $scope.fileErr = false;
		
		$scope.taskDetail = {
			projectId:data.projectId,
			taskId:data.taskId,
			startTime:data.startTime,
			endTime:data.endTime,
			status:data.status,
			date:$scope.currentDate,
			userId:data.staffId
		}
		
		$http({method:'GET',url:contextPath+'/getTaskDetails',params:{userId:data.staffId,projectId:data.projectId,taskId:data.taskId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.documentsData = data.taskDetails;
			$scope.commentsList = data.commentDetails;
		});
		
		console.log($scope.editStartTime);
		
		ngDialog.open({
            template:contextPath+'/editSchedule',
            scope:$scope,
            closeByDocument:false
            
		});
	};
	
	var file = null;
    $scope.selectFile = function(files) {
    	file = files[0];
    };
    $scope.fileErr = false;
    $scope.showMsg = false;
	$scope.saveAttachment = function() {
	
		if(file != null) {
			$scope.fileErr = false;
			$upload.upload({
	            url: contextPath+'/saveFile',
	            data: $scope.taskDetail,
	            file: file,
	            method:'post'
	        }).progress(function (evt) {
	            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
	        }).success(function (data, status, headers, config) {
	        	$scope.documentsData = data;
	        	$scope.showMsg = true;
	        });   
		} else {
			$scope.fileErr = true;
			$scope.showMsg = false;
		}
	}
	
	$scope.downloadfile = function(id) {
		$.fileDownload(contextPath+'/downloadTaskFile',
				{	   	
					   httpMethod : "POST",
					   data : {
						   attchId : id
					   }
				}).done(function(e, response)
						{
						}).fail(function(e, response)
						{
							// failure
						});
	}
	
	$scope.saveComment = function(comment) {
		$http({method:'GET',url:contextPath+'/saveComment',params:{userId:$scope.userId,comment:comment,projectId:$scope.taskDetail.projectId,taskId:$scope.taskDetail.taskId}})
		.success(function(data) {
			console.log('success');
			console.log(data);
			$scope.commentsList = data;
			$scope.comment = "";
		});
	}
	
	$scope.setStatus = function(status) {
		
			$scope.userId = $('#userID').val();
			$http({method:'GET',url:contextPath+'/updateTaskStatus',params:{projectId:$scope.taskDetail.projectId,taskId:$scope.taskDetail.taskId,status:status}})
			.success(function(data) {
				console.log('success');
				$scope.getAllStaffAppointments($scope.currentDate);
			});
	}
	
	$scope.init = function(data) {
		$scope.dataList = data.todayAllData;
		if(data.isHoliday == true) {
			$('#isHoliday').css("color","red");
		} else {
			$('#isHoliday').removeAttr("style");
		}
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
		var temp = currentDate.split("/");
		$scope.currentDateObject.setFullYear(parseInt(temp[2]),parseInt(temp[0])-1,parseInt(temp[1]));
		
		$scope.userId = $('#userID').val();
		var d1 = new Date(currentDate);
		$http({method:'GET',url:contextPath+'/getTodayAllByDate',params:{userId:$scope.userId,date:currentDate}})
		.success(function(data) {
			console.log(data);
			if(data.isHoliday == true) {
				$('#isHoliday').css("color","red");
			} else {
				$('#isHoliday').removeAttr("style");
			}
			var myString = JSON.stringify(data.todayAllData);
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
	$scope.currentDate = new Date(); 
	$scope.staffs = [];
	$scope.flag = 0;
	$scope.init = function(data) {
		$scope.flag = 0;
		$scope.reportLevel = "task";
		$scope.userId = $('#userID').val();
		for(var i=0;i<data.length;i++) {
			if(data[i].weekReport == null || angular.isUndefined(data[i].weekReport)) {
				data[i].flag = false;
			} else {
				data[i].flag = true;
			}
		}
		$scope.staffs = data;
		angular.forEach($scope.staffs, function(obj, index){
			if(obj.weekReport != null){
				$scope.flag = 1;
			}
		});
		Date.prototype.getWeek = function() {
			var onejan = new Date(this.getFullYear(),0,1);
			var w = Math.ceil((((this - onejan) / 86400000) + onejan.getDay())/7);
			if(w>52) {
				w -=52;
			}
			return w;
		};
		var startOfWeek;
		Date.prototype.getStartOfWeek = function() {
			var day =   this.getDay() - 1;
			var startOfWeek;
			if (day != -1) {
				startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * day ));
			} else {
				startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			}
			return startOfWeek;
		};

		$('.week-picker').datepicker({
			chooseWeek:true,
			calendarWeeks:true,
			weekStart:1,
			format: 'dd M yy'
		}).on("changeDate",function(ev){
			console.log(ev.date);
			$scope.currentDateObject = ev.date;
			$scope.changeWeek($scope.currentDateObject);
		});
		
		var today = new Date();
		$scope.currentDateObject = today;
		var todaysWeek = today.getWeek();
		var day =   today.getDay();
		if(day == 0) {
			startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			var endOfWeek = today;
			$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
					$.datepicker.formatDate('dd M yy', endOfWeek));
		} else {
			day = day - 1;
			startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
			var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
			$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
					$.datepicker.formatDate('dd M yy', endOfWeek));
		}
		console.log(data);
	}
	
	$scope.changeWeek = function(date) {
		$scope.flag = 0;
		$scope.userId = $('#userID').val();
		var d = (date.getMonth()+1)+'/'+date.getDate()+'/'+date.getFullYear();
		if($scope.reportLevel == "task") {
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
				
				angular.forEach($scope.staffs, function(obj, index){
					if(obj.weekReport != null){
						$scope.flag = 1;
					}
				});
			});
		}
		
		if($scope.reportLevel == "stage") {
			$http({method:'GET',url:contextPath+'/getStaffStageReport',params:{userId:$scope.userId,date:d}}).success(function(data) {
				console.log(data);
				for(var i=0;i<data.length;i++) {
					if(data[i].weekReport == null || angular.isUndefined(data[i].weekReport)) {
						data[i].flag = false;
					} else {
						data[i].flag = true;
					}
				}
				$scope.staffs = data;
				
				angular.forEach($scope.staffs, function(obj, index){
					if(obj.weekReport != null){
						$scope.flag = 1;
					}
				});
			});
		}
		
	};
	
	$scope.getReportByLevel = function(date) {
		$scope.changeWeek(date);
	}
	
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

app.controller("UsageReportController", function($scope,$http,$compile) {
	$scope.currentDateObject = new Date(); 
	$scope.showDaywise = true;
	$scope.init = function(plannedTask,actualTask) {
		console.log(plannedTask);
		console.log(actualTask);
		$scope.plannedTask = plannedTask;
		$scope.actualTask = actualTask;
		$scope.reportLevel = "task";
		$scope.reportView = "daywise";
		$scope.userId = $('#userID').val();
		$http({method:'GET',url:contextPath+'/getUsersForReport',params:{userId:$scope.userId}})
		.success(function(data) {
			$scope.userList = data;
			$scope.selectedUser = data[0].id;
		});
		
		var d = ($scope.currentDateObject.getMonth()+1)+'/'+$scope.currentDateObject.getDate()+'/'+$scope.currentDateObject.getFullYear();
		$http({method:'GET',url:contextPath+'/getTaskWeekTotal',params:{userId:$scope.userId,date:d}}).success(function(data) {
			$scope.taskWeekTotal = data;
			$scope.showDaywise = false;
		});
		
		Date.prototype.getWeek = function() {
			var onejan = new Date(this.getFullYear(),0,1);
			var w = Math.ceil((((this - onejan) / 86400000) + onejan.getDay())/7);
			if(w>52) {
				w -=52;
			}
			return w;
		};
		var startOfWeek;
		Date.prototype.getStartOfWeek = function() {
			var day =   this.getDay() - 1;
			var startOfWeek;
			if (day != -1) {
				startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * day ));
			} else {
				startOfWeek = new Date(this.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			}
			return startOfWeek;
		};

		$('.week-picker').datepicker({
			chooseWeek:true,
			calendarWeeks:true,
			weekStart:1,
			format: 'dd M yy'
		}).on("changeDate",function(ev){
			
			$scope.currentDateObject = ev.date;
			
		});
		
		var today = new Date();
		$scope.currentDateObject = today;
		var todaysWeek = today.getWeek();
		var day =   today.getDay();
		if(day == 0) {
			startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * 6 ));
			var endOfWeek = today;
			$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
					$.datepicker.formatDate('dd M yy', endOfWeek));
		} else {
			day = day - 1;
			startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
			var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
			$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
					$.datepicker.formatDate('dd M yy', endOfWeek));
		}
	};
	
	$scope.getReportData = function() {
		$scope.userId = $('#userID').val();
		var d = ($scope.currentDateObject.getMonth()+1)+'/'+$scope.currentDateObject.getDate()+'/'+$scope.currentDateObject.getFullYear();
		if($scope.reportLevel == "task") {
			$http({method:'GET',url:contextPath+'/getTaskLevelReport',params:{userId:$scope.selectedUser,date:d}}).success(function(data) {
				console.log(data);
				$scope.plannedTask = data.plannedTasks;
				$scope.actualTask = data.actualTasks;
				$scope.showDaywise = true;
			});
			
			$http({method:'GET',url:contextPath+'/getTaskWeekTotal',params:{userId:$scope.selectedUser,date:d}}).success(function(data) {
				$scope.taskWeekTotal = data;
				$scope.showDaywise = false;
			});
		}
		
		
		if($scope.reportLevel == "stage") {
			$http({method:'GET',url:contextPath+'/getStageLevelReport',params:{userId:$scope.selectedUser,date:d}}).success(function(data) {
				console.log(data);
				$scope.plannedTask = data.plannedStages;
				$scope.actualTask = data.actualStages;
				$scope.showDaywise = true;
			});
			
			$http({method:'GET',url:contextPath+'/getStageWeekTotal',params:{userId:$scope.selectedUser,date:d}}).success(function(data) {
				$scope.taskWeekTotal = data;
				$scope.showDaywise = false;
			});
		}
		
	};
	
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

		
	
	
});	
