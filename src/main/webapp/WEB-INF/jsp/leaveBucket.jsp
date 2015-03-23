<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<jsp:include page="menuContext.jsp"/> 
<h4><b style=" width: 1051px; margin-left: 20px;"><i>Leaves Request</i></b></h4>
<c:set var="_searchContext" value="${context}" scope="request" />
<c:set var="_parentSearchCtx" value="${null}" scope="request" />
<c:set var="mode" value="add" scope="request" />
<jsp:include page="searchContext.jsp"/> 
	



<script type="text/javascript">
function My_Leave_Approval_Bucketadd_gridAction_isToBeHidden(_action){
	 if(_action == 'Cross' && $("#My_Leave_Approval_Bucketaddstatus :selected").val() == 'Approved'){
		return false; 
	 }
	 
	if(_action == 'Tick' && $("#My_Leave_Approval_Bucketaddstatus :selected").val() == 'Rejected'){
		return false; 
	 }
	
	if($("#My_Leave_Approval_Bucketaddstatus :selected").val() == 'Submitted'){
		return false; 
	 }
	
	 return true;
 }
 
 $(document).ready(function(){
	 
	
	$("#My_Leave_Approval_BucketaddleaveApproveButton").hide();
	$("#My_Leave_Approval_BucketaddleaveRejectButton").show();
	
		$("#My_Leave_Approval_Bucketaddstatus").change(function(){
			if($("#My_Leave_Approval_Bucketaddstatus :selected").val() =='Approved'){
				$("#My_Leave_Approval_BucketaddleaveRejectButton").show();
				$("#My_Leave_Approval_BucketaddleaveApproveButton").hide();
			}
			if($("#My_Leave_Approval_Bucketaddstatus :selected").val() =='Submitted'){
				$("#My_Leave_Approval_BucketaddleaveRejectButton").show();
				$("#My_Leave_Approval_BucketaddleaveApproveButton").show();
			}
			if($("#My_Leave_Approval_Bucketaddstatus :selected").val() =='Rejected'){
				$("#My_Leave_Approval_BucketaddleaveApproveButton").show();
				$("#My_Leave_Approval_BucketaddleaveRejectButton").hide();
			}	
		});
 });
 
 function My_Leave_Approval_Bucket_confimationDialog(_url)
 {
	 if(_url == '${pageContext.request.contextPath}/leave/approveLeave'){
		 return true;
	 }
	 
	 if(_url == '${pageContext.request.contextPath}/leave/rejectLeave'){
		 return true;
	 }
	 return false;
	
 }
 
 </script>