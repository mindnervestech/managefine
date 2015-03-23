<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="menuContext.jsp"/> 
    <h4 class="container"><b><i>Company Status</i></b></h4>
	<c:set var="_searchContext" value="${context}" scope="request" />
	<c:set var="_parentSearchCtx" value="${null}" scope="request" />
	<c:set var="mode" value="add" scope="request" />
<jsp:include page="searchContext.jsp"/> 

<script type="text/javascript">
 $(document).ready(function(){
	 
	
	$("#CompanyaddcompanyApproveButton").show();
	$("#CompanyaddcompanyDisapproveButton").show();
	
		$("#Companyaddstatus").change(function(){
			if($("#Companyaddstatus :selected").val() =='Approved'){
				$("#CompanyaddcompanyDisapproveButton").show();
				$("#CompanyaddcompanyApproveButton").hide();
			}
			if($("#Companyaddstatus :selected").val() =='Disapproved'){
				$("#CompanyaddcompanyApproveButton").show();
				$("#CompanyaddcompanyDisapproveButton").hide();
			}	
			if($("#Companyaddstatus :selected").val() =='PendingApproval'){
				$("#CompanyaddcompanyApproveButton").show();
				$("#CompanyaddcompanyDisapproveButton").show();
			}	
		});
 });
 
 function Companyadd_gridAction_isToBeHidden(_action){
	 if(_action == 'Cross' && $("#Companyaddstatus :selected").val() == 'Approved'){
		return false; 
	 }
	 
	if(_action == 'Tick' && $("#Companyaddstatus :selected").val() == 'Disapproved'){
		return false; 
	 }
	if($("#Companyaddstatus :selected").val() == 'PendingApproval'){
		return false; 
	 }
	 return true;
 }
 
 function Company_confimationDialog(_url)
 {
	 if(_url == '${pageContext.request.contextPath}/companyStatusApprove'){
		 return true;
	 }
	 
	 if(_url == '${pageContext.request.contextPath}/companyStatusDisapprove'){
		 return true;
	 }
	 return false;
	
 }
 </script>
 
 