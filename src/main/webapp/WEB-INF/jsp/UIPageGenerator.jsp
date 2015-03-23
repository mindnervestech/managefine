<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${isModal}">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	</div>
	<div class="modal-body">
	</c:if>
<form id='${_pageContext.pageName()}${"_form"}' action="">
 	<div class="container">	   
		<c:forEach var="rlist" items="${_pageContext.getFieldLayout().iterator()}">
	     <div class="row-fluid">
	     	<c:forEach var="fieldType" items="${rlist.iterator()}">
	     	
	     		<c:choose>
	     		
					<c:when test='${rlist.size()==1}'>
					<div class="span12">
					<c:set var="fieldType" value="${fieldType}" scope="request" />
       				<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
					<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
					<c:when test='${rlist.size()==2}'>
					<div class="span6">
					<c:set var="fieldType" value="${fieldType}" scope="request" />
					<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
					<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
					<c:when test='${rlist.size()==3}'>
						<div class="span4">
					<c:set var="fieldType" value="${fieldType}" scope="request" />
       				<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
						<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
					<c:when test='${rlist.size()==4}'>
					<div class="span3">
				    <c:set var="fieldType" value="${fieldType}" scope="request" />
       				<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
					<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
					<c:when test='${rlist.size()==6}'>
						<div class="span2">
						<c:set var="fieldType" value="${fieldType}" scope="request" />
       				<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
						<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
					<c:when test='${rlist.size()==7}'>
					<div class="span1">
					<c:set var="fieldType" value="${fieldType}" scope="request" />
       				<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
					<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
					<c:when test='${rlist.size()==8}'>
					<div class="span1">
					<c:set var="fieldType" value="${fieldType}" scope="request" />
       				<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
					<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
					<c:when test='${rlist.size()==9}'>
					<div class="span1">
					<c:set var="fieldType" value="${fieldType}" scope="request" />
       				<c:set var="_namespace" value="${_pageContext.pageName()}" scope="request" />
      				 <c:set var="_fieldClass" value="span12" scope="request" />
					<jsp:include page="UIField.jsp"></jsp:include>
					
					</c:when>
						
	     		</c:choose>
	     		             </div>
	     		</c:forEach>
		     </div>
		     
	 </c:forEach>
	     
	     
 </div>
	     
	      
 	
</form>
<c:if test="${isModal}">
	</div><!-- /modal-body -->
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
</c:if>
	<c:forEach var="button" items="${_pageContext.getButtonActions()}">
  		<c:if test="${button.visibility()}">
		  			<button style="float:right;margin-top:5%;width: 12%;margin-right: 2%;"type="button" id='${_pageContext.pageName()}${button.id()}' class="btn btn-warning" >${button.label()}</button>
  		</c:if>
  	</c:forEach>
<c:if test='${isModal}'>	
  	</div>
 </c:if>
 
<script type="text/javascript">
$(document).ready(function(){
	

	var ${_pageContext.pageName()}${"_jsObject"} = {
		initialise: function (){
			this.bindButtons();
		},
		bindButtons: function(){
			${_pageContext.pageName()}${"_this"} = ${_pageContext.pageName()}${"_jsObject"};
			 <c:forEach var="button" items="${_pageContext.getButtonActions()}">
					<c:if test="${button.visibility()}">
					
			  			<c:choose>
			  			
			  			<c:when test='${button.target().name() == "SUBMIT"}'>
							$('#${_pageContext.pageName()}${button.id()}').click(function(){
								${_pageContext.pageName()}${"_this"}.doSubmitAction("${pageContext.request.contextPath}${button.url()}");	
				  			});
						</c:when>
						
						<c:when test='${button.target().name() == "MODAL"}'>
							$('#${_pageContext.pageName()}${button.id()}').click(function(){
								
				  			});
						</c:when>
						
						<c:when test='${button.target().name() == "ACTION"}'>
							$('#${_pageContext.pageName()${button.id()}').click(function(){
								${_pageContext.pageName()}${"_this"}.doCustomAction("${button.url()}");
				  			});
						</c:when>
						
						</c:choose>
		  		</c:if>
		  		
		</c:forEach>
			
		},
		doCustomAction: function(_url){
			
			$.ajax({
				url:_url,
				data: {
					query: function(){
					}
				},
				success: function(data) {
				},
				error: function(data) {
				}
			})
		},
		doSubmitAction: function(_url){
			
			$('#${_pageContext.pageName()}${"_form"}').attr('action' ,_url);
			$('#${_pageContext.pageName()}${"_form"}').submit();
		}
	}
	${_pageContext.pageName()}${"_jsObject"}.initialise();
	$('#${_pageContext.pageName()}${"_modal"}').modal('show');
	$('#${_pageContext.pageName()}${"_form"}').validate({
	//errorElement:"", 
	rules: {
	    		 <c:forEach var="fieldType" items="${_pageContext.getFields().iterator()}">
	    			 <c:if test="${fieldType.validation() !=null}">
		  						 ${fieldType.name()} :{
			    				 minlength: ${fieldType.validation().minlength()},
			    				 required : ${fieldType.validation().required()},
			    				 email: ${fieldType.validation().email()},
			    				 maxlength: ${fieldType.validation().maxlength()},
			    				 date: ${fieldType.validation().date()},
			    				 digits: ${fieldType.validation().digits()},
								 number: ${fieldType.validation().number()}			    				 
			    			 },
	    		 	</c:if>
	    	 </c:forEach>
    	 },
    	 messages: {
    			 <c:forEach var="fieldType" items="${_pageContext.getFields().iterator()}">
    			   <c:if test='${fieldType.validation() !=null}'>
    				   <c:if test='${!fieldType.validation.messages.equals("")}'>
	    				 ${fieldType.name()} :  ${fieldType.validation().messages()},
    				   </c:if>
	    		  </c:if>
    		   </c:forEach>
    	  },
    highlight: function (element) {
        $(element).closest('.control-group').removeClass('success').addClass('error');
    },
    success: function(element) {
        element
        .addClass('valid')
        .closest('.control-group').removeClass('error').addClass('success');
      }
});
});
</script>
 