$(function(){
	$('body').on('click','select.largeInputFirst',function(){
		var projectRowId = $(this).attr("id");  
		var taskRowId = getIdOfTaskRow($(this));
		$("#"+projectRowId).chainSelect('#'+taskRowId,'timesheet/getTaskCode');
	});
});


function getIdOfTaskRow(currentRow){
	var $target = currentRow.parents(".innerInputDiv").children("div.innerChainSelect").children("div").last().children("div");
	while ($target.length) {
	    $target = $target.children("select");
	}
	return $target.end().attr('id');
}

function getValuesOfHrs(currentRow){
	var totalHrs = 0;
	var $target = currentRow.parents(".innerHrsDiv").children("div").children("div").children("input");
	$($target).each(function(index,element){
		totalHrs = +totalHrs +  +$target.eq(index).val();
	});
	var $Hrs = currentRow.parents(".well").children(".totalHrsDiv").children("div").children("div").children("input");
	$Hrs.val(totalHrs);
}

var renumber = function() {
	$('.timesheetRow').each(function(i) {
	
		$('input,select', this).each(function() {
        	if($(this).attr("class") != "clearfix totalHrs mediumInput"){
        		$(this).attr('name', $(this).attr('name').replace(/timesheetRows\[.+?\]/g, 'timesheetRows[' + i + ']'));
        		$(this).attr('id', $(this).attr('id').replace(/timesheetRows\_.+?\_/g, 'timesheetRows_' + i + '_'));
        	}
        });
    });
}

function checkStatus(){
	var $status = false;
	var $approve = false;
	if($("#status").val() == "Draft" || $("#status").val() == "Pending"){
		$status = true;
	}
	if($("#status").val() == "Approved"){
		$approve = true;
	}
	
	$("#saveTimesheetForm").prop("disabled",$status === false);
	$("#copyFromLastWeek").prop("disabled",$status === false);
	$("#cancelTimesheetForm").prop("disabled",$status === false);
	$("#submitTimesheetForm").prop("disabled",$status === false);
	$("#retractTimesheetForm").prop("disabled",$status === true);
	$("#retractTimesheetForm").prop("disabled",$approve === true);
	if($status){
		$("#timeSheetTable :input").removeAttr("readonly","readonly");
		$("#timeSheetTable select").removeAttr("disabled","disabled");
		$("input:checkbox").removeAttr("disabled","disabled");
		$("#timeSheetTable a").removeAttr("disabled","disabled");
		$(".innerInputDiv .totalHRS").attr("disabled","disabled");
		$("#retractTimesheetForm").attr("disabled","disabled");
	}else{
		$("#retractTimesheetForm").attr("enable","enable");
		$("#timeSheetTable :input").attr("readonly","readonly");
		$("#timeSheetTable select").attr("disabled","disabled");
		$("input:checkbox").attr("disabled","disabled");
		$("#timeSheetTable a").attr("disabled","disabled");
	}
	$(".readonlycls").attr("readonly","readonly");
	return $status;
}

function submitTimesheetForm(){
	$("#createTimesheet").validate({});
	
		if($("#createTimesheet").valid()){
			$(".timesheetRow_template").remove();
			$.ajax({
				type:"POST",
				url:"timesheetCreate",
				data: $('#createTimesheet').serialize(),
				success: function(response) {
					$(".worksheetDiv").empty();
					$(".worksheetDiv").html(response);
				},
				error: function(error) {
					alert("Error : "+error.responseText);
				}
			});
		}
}

function customValidation(){
	if($('div.timesheetRow').length == 0){
		return false;	
	}
	return true;
}

$(document).ready(function() { 
	
	checkStatus();
	
	$('.smallInput').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	
	$("#outputStatus").html($(".inputStatus").val());
	
	$('#addMore').click(function(e) {
	    if(checkStatus()){
	    	var template = $('.timesheetRow_template')
		    template.before('<div class="twipsies well timesheetRow">' + template.html() + '</div>')
		    renumber();
	    }
	});
	
	$("body").on("click","a#remove",function() {
		if(checkStatus()){
			$(this).parents('.timesheetRow').remove();
			renumber();
		}
	});
	
	$('body').on('change','input.smallInput',function(){
		getValuesOfHrs($(this));
	});
	
	$("#copyFromLastWeek").click(function(){
		var weekno = $("#weekValue").val();
		var yearno = $("#yearValue").val();
			
		$.ajax({
			type:"GET",
			url:"timesheet/getLastWeekTimesheet",
			data: {week: weekno,year:yearno},
			success: function(response) {
				$(".worksheetDiv").empty();
				$(".worksheetDiv").html(response);
			},
			error: function(error) {
				if(error.status = 404) {
					bootbox.confirm("No Timesheet Available", function(result) {
						if(result == true){
							return;
				        }else{
				        	return;
				        }
					});
				} else {
					bootbox.confirm(""+error.responseText, function(result) {
						if(result == true){
							return;
				        }else{
				        	return;
				        }
					});
				}
			}
		});
	});
	
	$("#saveTimesheetForm").click(function(){
		if(checkStatus() && customValidation()){
			$("#status").val("Draft");
			submitTimesheetForm();
		}else{
			bootbox.alert("Please add at least one row to save timesheet.");
		}
	});
	
	$("#submitTimesheetForm").click(function(){
		if(checkStatus() && customValidation()){
			$("#status").val("Submitted");
			submitTimesheetForm();
		}else{
			bootbox.alert("Please add at least one row to save timesheet.");
		}	
	});
	
	$("#retractTimesheetForm").click(function(){
		$("#status").val("Draft");
		var id = $(".TIMEID").val();
		$.ajax({
			type:"GET",
			url:"retractTimesheet?id="+id,
			success: function(response) {
				$(".worksheetDiv").empty();
				$(".worksheetDiv").html(response);
			},
			error: function(error) {
				alert("Error : "+error.responseText);
			}
		});
	});
	
	$("#cancelTimesheetForm").click(function(){
		var today = new Date();
		
		$("#weekValue").val(today.getWeek());
		$("#yearValue").val(today.getFullYear());
		$("#weekRange").html(getDateRangeOfWeek(today.getWeek()));
		$.post('timesheetCancel', function(response) {
			$(".worksheetDiv").empty();
			$(".worksheetDiv").html(response);
		});		
	});
	
});