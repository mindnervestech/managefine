<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<style>
#custom-modal-container {
	width: 580px !important;
	left: 50% !important;
	top: 20%;
}

.formClass {
	padding: 0 30px;
}

.modal-footer {
	padding: 15px 15px 5px !important;
	text-align: left;
	background-color: none !important;
	margin-top: 15px;
}
</style>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">&times;</button>
	<h4>Feedback For ${user.firstName} ${user.lastName}</h4>
</div>

<form action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Feedbacks.create.url%>"
	method="GET" id="createFeedbackForm" class="formClass"
	enctype="multipart/form-data">
	<input type="hidden" value="${user.id}" name="employeeID"> <br>

	<div class="clearfix  " id="feedback_field">
		<label for="feedback">Remark</label>
		<div class="input">


			<textarea id="feedback" name="feedback"
				placeholder="Provide your valuable feedback here."
				class="customTextArea"></textarea>

			<span class="help-inline"></span> <span class="help-block"></span>
		</div>
	</div>

	<div class="clearfix  " id="rating_field">
		<label for="rating">Rating</label>
		<div class="input">

			<select id="rating" name="rating" class="largeInput">


				<c:forEach var="Rating1" items="${ratings}">
					<c:choose>
						<c:when test="${Rating1==feedbackform.model.feedback.rating}">
							<option selected value="${Rating1}">${Rating1}</option>
						</c:when>
						<c:otherwise>
							<option value="${Rating1}">${Rating1}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> <span class="help-inline"></span> <span class="help-block"></span>
		</div>
	</div>


	<div class="modal-footer">
		<a href="#" data-dismiss="modal" class="btn">Cancel</a> <a href="#"
			class="btn btn-primary" id="feedbackSubmitBtn">Submit</a>
	</div>

</form>

<script>
	$("#feedbackSubmitBtn").click(function(){
		$.post("${pageContext.request.contextPath}/submitFeedback",$('#createFeedbackForm').serialize(), function(response) {
			$('#custom-modal-container').modal('hide');
			$.pnotify({
				history:false,
		        text: response
		    });
		});
	});
</script>