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

var startOfWeek;

$(document).ready(function() {
	
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
		getEmployeeTimesheet();
	});
	
	if($("#weekValue").val() == "" && $("#yearValue").val() == ""){
		var today = new Date();
		var todaysWeek = today.getWeek() + 1;
		
		$("#weekValue").val(today.getWeek() + 1);
		$("#yearValue").val(today.getFullYear());
		var day =   today.getDay() - 1;
		startOfWeek = new Date(today.getTime() - (24 * 60 * 60 * 1000 * day ));
		var endOfWeek = new Date(today.getTime() + (24 * 60 * 60 * 1000 * (6  - day) ));
		
		$('.week-picker').val($.datepicker.formatDate('dd M yy', startOfWeek) + " - " +
				$.datepicker.formatDate('dd M yy', endOfWeek));
		$("#selectedWeekRange").html($(".week-picker").val());
		getEmployeeTimesheet();
	}
	
	
	$("#thisWeek").click(function(){
		$("#weekValue").val(todaysWeek);
		$("#yearValue").val(todaysWeek);
	});
	
	$("#previousWeek").click(function(){
		$("#weekValue").val($("#weekValue").val() - 1 );
		getEmployeeTimesheet();
	});
	
	$("#weekInfo .goButton").click(getEmployeeTimesheet);
	$("#getEmployeeTimesheet").click(getEmployeeTimesheet);
	
});

function populateDateRange(startDate){
	for(var i = 0 ;i <= 6 ; i++) {
		dateToBeShown = new Date(startDate.getTime() + (24 * 60 * 60 * 1000 * i ));
		var dateObj = new Date(dateToBeShown.getMonth()+1 + "/" + dateToBeShown.getDate() + "/" +dateToBeShown.getFullYear());
		$('#dayLabel_' + i ).html(moment(dateObj).format("DD MMM"));
	}
}

function getEmployeeTimesheet(){
	$.get(contextPath + '/timesheet/getTimesheetTable', {id: $('#employeeID').val(),week:$('#weekValue').val(),year:$('#yearValue').val()}, 
			function(response) {
				$(".worksheetDiv").empty();
				$(".worksheetDiv").html(response);
				populateDateRange(startOfWeek);
	});
}
/*
$('body').on("change", "select[id$='__projectCode']", function(){

	$.ajax
	({
		type: "GET",
		url: "/getTaskByProject?userId="+$('#employeeID').val()+"&projectCode="+$(this).val(),
		cache: false,
		success: function(responce)
		{
			var id = $("#"+this.id).next();
			var task_select = $(this);
			console.log(id);
		} 
	});

});*/
