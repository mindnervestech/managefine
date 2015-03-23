<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="menuContext.jsp"/> 
  <h3 style="margin-left: 2%;"><b><i>TimeSheet Notification</i></b></h3>
  		
  		<form style="margin-left: 2%;" action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Notifications.save.url%>" id="notificationSettings" method="POST">
			
			<c:forEach var="_node" items="${notifyForm.get().iterator()}">
			<div class="container">
				<div class="row-fluid">
				<div class="span2">
					<input type="checkbox" name="d[]" "${_node.findValue("f").getTextValue()}" class="input-medium"> ${_node.findValue("d").getTextValue()}
				</div>
				<c:choose>
				<c:when test='${!_node.findValue("f").getTextValue().equals("checked")}'>
					<div class="span1 ">
						<input type="number" name="h[]" min="0" max="23" style="height:23px;width:50px" placeholder="hr" maxlength="2">
					</div>
					<div class="span2 ">
						<input type="number" name="m[]" min="0" max="59" style="height:23px;width:50px" placeholder="min" maxlength="2">
					</div>
					</c:when>
				<c:otherwise>
					<div class="span1">
						<input type="number" name="h[]" min="0" max="23" value='${_node.findValue("h").getIntValue()}' placeholder="hr" maxlength="2" style="height:23px;width:50px">
					</div>
					<div class="span2">
						<input type="number" name="m[]" min="0" max="59" value='${_node.findValue("m").getIntValue()}' placeholder="min" maxlength="2" style="height:23px;;width:50px">
					</div>
				</c:otherwise>
				</c:choose>
				<div class="span7"></div>
			</div>	
			</div>	
			</c:forEach>
			
			<input type="submit" name="Submit" class="btn btn-warning"style="margin-top: 2%;">
		 	
					
 </form>
  
  <style>
.btnColor{
	color: gray;
}
</style>