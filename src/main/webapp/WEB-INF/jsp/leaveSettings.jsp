<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<h4>
	<b style="width: 1051px; margin-left: 20px;"><i>Leaves Settings</i></b>
</h4>

	<%-- <div id="Leaves">
		<form:form class="well" method="POST"
			action="${pageContext.request.contextPath}/<%=com.mnt.time.controller.routes.Leaves.saveLeaves.url%>"
			id="form">
			<c:set var="count" value="0" scope="page" />
			<table class="table table-striped">
			<c:forEach var="map" items="${leave2RoleMap}">
				<tr>
					<td><label style="margin-top: 8%;"><strong>${map.key}</strong></label></td>
    				<c:forEach var="cell" items="${map.value}">
    					<td>
    						<label><small>${cell.leaveType}</small></label>
    						<input class="input-small"  type="hidden" name="roleLeaves[${count}].id" value="${cell.id}">
    						<input class="input-mini" type="number" name="roleLeaves[${count}].total_leave" value="${cell.totalLeave}">
    				 		<c:set var="count" value="${count + 1}" scope="page"/>
    					 </td>  
    				</c:forEach>
    			</tr>
			</c:forEach>
			</table>
			<div class="actions" style="margin-top: 1%;">
				<input type="button" class="btn btn-info" id="submitButton"
					value="Submit" style="margin: 1% 2%; width: 8%;">
			</div>
		</form:form>
	</div> --%>


<ul class="nav nav-tabs" id="myTab">
  <li id="li1" class="active"><a href="#home">Role Wise Leaves</a></li>
  <li id="li2"><a href="#profile">Leaves Credit Policy</a></li>
</ul>
 
<div class="tab-content">
  <div class="tab-pane active" id="home">
  
  <div id="Leaves">
		<form:form class="well" method="POST"
			action="${pageContext.request.contextPath}/<%=com.mnt.time.controller.routes.Leaves.saveLeaves.url%>"
			id="form">
			<c:set var="count" value="0" scope="page" />
			<table class="table table-striped">
			<c:forEach var="map" items="${leave2RoleMap}">
				<tr>
					<td><label style="margin-top: 3%;"><strong>${map.key}</strong></label></td>
    				<c:forEach var="cell" items="${map.value}">
    					<td>
    						<label style="  margin-left: 10px;"><small>${cell.leaveType}</small></label>
    						<input class="input-small"  type="hidden" name="roleLeaves[${count}].id" value="${cell.id}">
    						<input class="input-mini" min="0" style="height: 20px;" type="number" name="roleLeaves[${count}].total_leave" value="${cell.totalLeave}">
    				 		<c:set var="count" value="${count + 1}" scope="page"/>
    					 </td>  
    				</c:forEach>
    			</tr>
			</c:forEach>
			</table>
			<div class="actions" style="margin-top: 1%;">
				<input type="button" class="btn btn-info" id="submitButton"
					value="Submit" style="margin: 1% 2%; width: 8%;">
			</div>
		</form:form>
	</div>
  
  
  
  
  
  </div>
  <div class="tab-pane" id="profile" style="  min-height: 60px;">
  
  <form:form method="POST" commandName="leaveForm"
	action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Leaves.saveLeavesCredit.url%>"
	id="form1">
	<fieldset>
		<div id="leavex" style="  margin-top: 15px;">
			<select style=" margin-left: 10px;" name="policy" id="policy">
			  <option value="Pro rata basis">Pro rata basis</option>
			  <option value="Annual Credit Policy">Annual Credit Policy</option>
			</select>
		</div>


	</fieldset>
	<div class=" actions buttonPosition" style="margin-left: 25%;">
		 <input type="button"
			class="btn btn-warning" id="submitButton1" value="Submit"
			style="width: 9%;  margin-top: -50px;">
	</div>

</form:form>
  
  
  
  
  
  </div>
</div>
<style>
.nav-tabs > li > a:hover, .nav-tabs > li > a:focus {
  border-color: #131212 #0F0E0E #0D0C0C;
}
</style>

  <script>
	$(document).ready(function() {

		$("#submitButton").click(function() {
			$.ajax({
				type : "POST",
				data : $("#form").serialize(),
				url : "${pageContext.request.contextPath}/saveLeaveValue",
				success : function(data) {
					$.pnotify({
						history : false,
						text : data
					});
				}
			});
		});
		
		$("#submitButton1").click(function() {
			$.ajax({
				type : "POST",
				data : $("#form1").serialize(),
				url : "${pageContext.request.contextPath}/saveLeavesCredit",
				success : function(data) {
					document.getElementById("submitButton1").disabled = true;
					$.pnotify({
						history : false,
						 type:'success',
			                text: "CreditPolicy Save Successfully"
					});
				}
			});
		});
		
		
		$.ajax({
			type : "POST",
			data : $("#form1").serialize(),
			url : "${pageContext.request.contextPath}/getLeavesCredit",
			success : function(data) {
				console.log("get policy");
				console.log(data);
				if(data != "" ){
					$('#policy').val(data.policyName);
					document.getElementById("submitButton1").disabled = true;
				} 
			}
		});
		
		
	});
	$('#li2').css('background-color','#5c5c5c');
	$('#myTab a').click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		  $('#li1').css('background-color','#000000');
		  $('#li2').css('background-color','#000000');
		})
</script> 
