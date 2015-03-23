
<style>
	#custom-modal-container{
		width: 500px !important;
		left: 55% !important;
		top: 30%;
	}

	.formClass{
		padding: 0 30px;
	}
	
	.modal-footer{
		padding: 15px 15px 5px !important;
		text-align: left;
		background-color: none !important;
		margin-top: 15px;
	}
	
	.modal-header .close{
		margin-right: -25px;
	}
</style>

<div class="formClass">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h4>Feedback For ${user.firstName} ${user.lastName}</h4>
	</div>

	<br>
	
	<p><b>Feedback :</b> ${Feedback.feedback}</p>

	<p><b>Rating :</b> ${Feedback.rating}</p>
	
	<p><b>Feedback From :</b> ${Feedback.manager.firstName} ${Feedback.manager.lastName} on ${Feedback.feedbackDateDisplay}</p>
	
	
</div>