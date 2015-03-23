var renumber = function() {
	$('.roleLevel').each(function(i) {
        $('input,select', this).each(function() {
        	$(this).attr('name', $(this).attr('name').replace(/roleLevels\[.+?\]/g, 'roleLevels[' + i + ']'));
        	$(this).attr('id', $(this).attr('id').replace(/roleLevels\_.+?\_/g, 'roleLevels_' + i + '_'));
        });
    });
}

function customValidation(){
	if($('div.timesheetRow').length == 0){
		return false;	
	}
	return true;
}

$(document).ready(function() { 
	
	/*$('#form').submit(function() {
        $('.roleLevel_template').remove()
    })*/
	
	$("a.addMore").click(function(e) {
	   	var template = $('.roleLevel_template')
	    template.before('<div class="twipsies well roleLevel">' + template.html() + '</div>')
	    renumber();
	});
	
	$("body").on("click","a.removeRole",function() {
		$(this).parents('.roleLevel').remove();
		renumber();
	});
	
	$("#cancelTimesheetForm").click(function(){
		$.post('/timesheetCancel', function(response) {
			$(".worksheetDiv").empty();
			$(".worksheetDiv").html(response);
		});		
	});
	
});