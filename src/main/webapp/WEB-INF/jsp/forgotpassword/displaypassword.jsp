<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
		<div class="heading">
		
<form:form action="<%=com.mnt.time.controller.routes.Application.login.url%>" method="get">
				
					<br>
					<br>
					<br>
					<fieldset id="login_field" class="right-align" style="width:35%;margin-top:70px;">
					
				
								<div class="well" style="width:100%;">
									<h3>Please Check your Inbox for your User Name and Password</h3>
									
			        			    <p>
			           				 	<button type="submit" class="btn btn-success" style="margin-left:185px;">Go To Login</button>
			           			   </p>
		       			  	 </div>
			</fieldset>
			</form:form>
		</div>
	</div>	
	