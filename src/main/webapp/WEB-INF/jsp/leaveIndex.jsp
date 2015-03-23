<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


 <jsp:include page="menuContext.jsp"/> 
 <h4><b style=" width: 1051px; margin-left: 20px;"><i>My Leaves</i></b></h4>

<div class=" twipsies well leaveLevel" style="width: 94%;margin-left: 1%">
	<div class="clearfix"
		style="margin-right: 4%; float: left; width: 100%;">
		<c:forEach var="leave" items="${leaves}" varStatus='loopIndex'>
			<label style="float: left; margin-right: 2%;"
				for="leave_${loopIndex.index}_leave_name">
				${leaves.get(loopIndex.index).getLeaveLevel().getLeave_type()}: ${leaves.get(loopIndex.index).getBalance()}
			</label>
		</c:forEach>

	</div>
</div>


<c:set var="_searchContext" value="${context}" scope="request" />
<c:set var="_parentSearchCtx" value="${null}" scope="request" />
<c:set var="mode" value="add" scope="request" />
<jsp:include page="searchContext.jsp"/> 



<h5 id="Leave_caution" style="display:none;"> WILL YOU LIKE TO CONTINUE AND RETRACT ??</h5>

<script>

 $(document).ready(function(){
	 var leaveBalanceDS = {};
	 <c:forEach var="leave" items="${leaves}" varStatus='loopIndex'>
	 leaveBalanceDS["${leaves.get(loopIndex.index).getLeaveLevel().getId()}"] = ${leaves.get(loopIndex.index).getBalance()};
	</c:forEach>
	
	 $('#Leaveleave_domain').change (function() {
		var v1 = $(this).val();
		if(parseInt($('#LeavenoOfDays').val()) > parseInt(leaveBalanceDS[v1])){
			alert("You don't have sufficient leaves");
			$('#LeavenoOfDays').val(0);
			return false;
		}
		
	});	 

	 $('#LeavenoOfDays').change (function() {
		 var v1 = $('#Leaveleave_domain').val();
		 if(parseInt($(this).val()) > parseInt(leaveBalanceDS[v1])){
				alert("You don't have sufficient leaves");
				$('#LeavenoOfDays').val(0);
				return false;
			}
		 
	 });
	
	
	function Leave_confimationDialog(_url)
	{
		 if(_url == '${pageContext.request.contextPath}/leave/retractLeave'){
			 return true;
		 }
		 return false;
		
	}
 });	
</script>

