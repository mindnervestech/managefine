<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
		<div class="heading">
	
	<form action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Application.finduser.url%>" method="GET">
		
					<br>
					<br>
					<br>
					
					<c:if test="${not empty success}">
				       <p class="success" style="color:red;">${success}</p>
			        </c:if>
					
	                <c:if test="${not empty fail}">
						<p class="error" style="color: red;">${fail}</p>
					</c:if>
	            	
	            	
								<fieldset id="registerHere">
									<div class="well" style="width: 60%;">
										<p>
						                <input type="email" id="inputemail" name="inputemail" placeholder="Email"  >
						            	</p>
						            	<p>
						            	<button  type="submit" class="btn btn-info" >Submit</button>
						            	<a href="/" class="btn btn-warning">Back</a>
						            	</p>
						            </div>
						       </fieldset>
						</form>
			</div>
		</div>



		<script src='<c:url value="http://code.jquery.com/jquery.js"/>'></script>
		<script src='<c:url value="resources/javascripts/bootstrap.min.js"/>' ></script>

</html>