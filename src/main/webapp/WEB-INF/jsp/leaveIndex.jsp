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

<button id="popupBtn" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" style="display: none;">
</button>

<div class="modal fade" style="z-index: 999999 !important;" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
<div class="modal-content">
<div class="modal-header">
<button type="button" class="close" id="retractClose" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
</div>
<div class="modal-body">
<b>Yor are not able to take this Type Of Leave Because you are not completed 6 months .</b>
</div>
<div class="modal-footer">
<button type="button" class="btn btn-default" data-dismiss="modal">Ok</button>
</div>
</div>
</div>
</div>


<script>

 $(document).ready(function(){
	 var leaveBalanceDS = {};
	 <c:forEach var="leave" items="${leaves}" varStatus='loopIndex'>
	 leaveBalanceDS["${leaves.get(loopIndex.index).getLeaveLevel().getId()}"] = ${leaves.get(loopIndex.index).getBalance()};
	</c:forEach>
	
	 $('#Leaveleave_domain').change (function() {
		 console.log("leaves domain");
		var v1 = $(this).val();
		 console.log(this);
		 var selects1 = this.options[ this.selectedIndex ].text;
		 console.log(selects1);
		  if(${usercheckleaves} == true && selects1 == "Unpaid Leave"){
			alert("success");
			//$('#LeavenoOfDays').val(0);
			return false;
		}else if(${usercheckleaves} == false){
			alert("success");
		}else{
			$('#popupBtn').click();
			//alert("Yor are not able to take this Type Of Leave Because you are not completed 6 months ");
			$('#Leaveleave_domain').val("Unpaid Leave");
		} 
		
	});	 

	 $('#LeavenoOfDays').change (function() {
		 console.log("leaves no.");
		 var v1 = $('#Leaveleave_domain').val();
		 console.log(v1);
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

