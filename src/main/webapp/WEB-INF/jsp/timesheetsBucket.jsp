
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<jsp:include page="menuContext.jsp"/> 
		<h4><b style=" width: 1051px; margin-left: 20px;"><i>Timesheets Bucket</i></b></h4>
		<c:set var="_searchContext" value="${context}" scope="request" />
		<c:set var="_parentSearchCtx" value="${null}" scope="request" />
		<c:set var="mode" value="add" scope="request" />
	<jsp:include page="searchContext.jsp"/>
<h5 id="My_TimeSheet_Approval_Bucket_caution" style="display:none;"> WILL YOU LIKE TO CONTINUE AND DISAPPROVE ??</h5>

<script type="text/javascript">
 	     
	function My_TimeSheet_Approval_Bucket_confimationDialog(_url)
	 {
		 if(_url == '${pageContext.request.contextPath}/timesheet/approveTimesheets'){
			 return false;
		 }
		 
		 if(_url == '${pageContext.request.contextPath}/timesheet/rejectTimesheets'){
			 return true;
		 }
		 return false;
		
	 }
	
	$(document).ready(function(){

		$("#My_TimeSheet_Approval_BucketaddtimesheetApproveButton").show();
		$("#My_TimeSheet_Approval_BucketaddtimesheetRejectButton").show();
		
		$("#My_TimeSheet_Approval_Bucketaddstatus").change(function(){
			if($("#My_TimeSheet_Approval_Bucketaddstatus :selected").val() =='Approved'){
				$("#My_TimeSheet_Approval_BucketaddtimesheetRejectButton").show();
				$("#My_TimeSheet_Approval_BucketaddtimesheetApproveButton").hide();
			}
			if($("#My_TimeSheet_Approval_Bucketaddstatus :selected").val() =='Submitted'){
				$("#My_TimeSheet_Approval_BucketaddtimesheetApproveButton").show();
				$("#My_TimeSheet_Approval_BucketaddtimesheetRejectButton").show();
			}
			if($("#My_TimeSheet_Approval_Bucketaddstatus :selected").val() =='Rejected'){
				$("#My_TimeSheet_Approval_BucketaddtimesheetApproveButton").show();
				$("#My_TimeSheet_Approval_BucketaddtimesheetRejectButton").hide();
			}	
		});
		
	});
	
	function My_TimeSheet_Approval_Bucketadd_gridAction_isToBeHidden(_action){
		 if(_action == 'Cross' && $("#My_TimeSheet_Approval_Bucketaddstatus :selected").val() == 'Approved'){
			return false; 
		 }
		 
		if(_action == 'Tick' && $("#My_TimeSheet_Approval_Bucketaddstatus :selected").val() == 'Rejected'){
			return false; 
		 }
		
		if($("#My_TimeSheet_Approval_Bucketaddstatus :selected").val() == 'Submitted'){
			return false; 
		 }
		
		 return true;
	 }
 
 </script>