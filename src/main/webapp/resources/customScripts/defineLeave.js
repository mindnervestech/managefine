var renumber = function() {
	$('.leaveLevel').each(function(i) {
        $('input,select', this).each(function() {
        	$(this).attr('name', $(this).attr('name').replace(/leaveLevels\[.+?\]/g, 'leaveLevels[' + i + ']'));
        	$(this).attr('id', $(this).attr('id').replace(/leaveLevels\_.+?\_/g, 'leaveLevels_' + i + '_'));
        });
    });
}

$(document).ready(function() { 
		
	$("a.addMore").click(function(e) {
	   	var template = $('.leaveLevel_template')
	    template.before('<div class="twipsies well leaveLevel">' + template.html() + '</div>')
	    renumber();
	});
	
	$("body").on("click",".removeLeave",function() {
		$(this).parents('.leaveLevel').remove();
		renumber();
	});
	
});