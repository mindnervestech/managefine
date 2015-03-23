<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="menuContext.jsp"/> 
    <h4 class="container"><b><i>User Status</i></b></h4>
   	
   	<c:set var="_searchContext" value="${context}" scope="request" />
	<c:set var="_parentSearchCtx" value="${null}" scope="request" />
	<c:set var="mode" value="add" scope="request" />
<jsp:include page="searchContext.jsp"/> 


<script type="text/javascript">
 $(document).ready(function(){
 
	
	 $("#UseradduserApproveButton").show();
	$("#UseradduserdisapproveButton").show();
	
		$("#Useraddstatus").change(function(){
			if($("#Useraddstatus :selected").val() =='Approved'){
				$("#UseradduserdisapproveButton").show();
				$("#UseradduserApproveButton").hide();
			}
			if($("#Useraddstatus :selected").val() =='Disapproved'){
				$("#UseradduserdisapproveButton").hide();
				$("#UseradduserApproveButton").show();
			}
			if($("#Useraddstatus :selected").val() =='PendingApproval'){
				$("#UseradduserdisapproveButton").show();
				$("#UseradduserApproveButton").show();
			}	
		});
 });
 
 function Usereditmanager_customParameterBuilder(){
	   return $('#Usereditrolex :selected').val()
}
 
 function Useredit_onCardReady(_wizard){
	 if((_wizard).index==1){
		 $("#UsereditannualIncome").focusout(function(){          
		        $("#Useredithourlyrate").val((parseInt($('input[id=UsereditannualIncome]').val(),10)/2080).toFixed(2));
		     });
	 }
 }
 
 
function Useradd_gridAction_isToBeHidden(_action){
	 if(_action == 'Cross' && $("#Useraddstatus :selected").val() == 'Approved'){
		return false; 
	 }
	 
	if(_action == 'Tick' && $("#Useraddstatus :selected").val() == 'Disapproved'){
		return false; 
	 }
	if($("#Useraddstatus :selected").val() == 'PendingApproval'){
		return false; 
	 }
	 return true;
 }
 
$( ".tooltipShow" ).tooltip({ 
	content: "Awesome title!",
	position : "right"
});


$( ".tooltipShow" ).tooltip('show');
 
 function User_confimationDialog(_url)
 {
	 if(_url == '${pageContext.request.contextPath}/userStatusApprove'){
		 return true;
	 }
	 
	 if(_url == '${pageContext.request.contextPath}/userStatusdisapprove'){
		 return true;
	 }
	 return false;
	
 }
 </script> 
 