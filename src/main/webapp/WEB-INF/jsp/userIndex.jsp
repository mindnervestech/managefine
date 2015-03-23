<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<jsp:include page="menuContext.jsp"/> 
    <h4><b style=" width: 1051px; margin-left: 20px;"><i>Manage Users</i></b></h4>
	<c:set var="_searchContext" value="${context}" scope="request" />
	<c:set var="_parentSearchCtx" value="${null}" scope="request" />
	<c:set var="mode" value="add" scope="request" />
	<jsp:include page="searchContext.jsp"/>
 <script type="text/javascript">
 $(document).ready(function(){
     
	   $("#UserannualIncome").focusout(function(){          
        $("#Userhourlyrate").val((parseInt($('input[id=UserannualIncome]').val(),10)/2080).toFixed(2));
     });	
   });
   
   function Usermanager_customParameterBuilder(){
  	   return $('#Userrolex :selected').val()
   }
   
   function Usereditmanager_customParameterBuilder(){
  	   return $('#Usereditrolex :selected').val()
   }
   
   function Userhrmanager_customParameterBuilder(){
  	   return $('#Userrolex :selected').val()
   }
   
   function Useredit_onCardReady(_wizard){
  	 if((_wizard).index==1){
  		 $("#UsereditannualIncome").focusout(function(){
  			 if($("#UsereditannualIncome").val() != null && $("#UsereditannualIncome").val() != ""){
  		 		$("#Useredithourlyrate").val((parseInt($('input[id=UsereditannualIncome]').val(),10)/2080).toFixed(2));
  			 }
  		 });
  		 
  		 checkEmployeeStatus();
  		 
  		 $("#Usereditstatus").change(function(){
  			 checkEmployeeStatus(); 
  		 });
  		 
  		 $("#Useraddstatus").change(function(){
  			 checkEmployeeStatus(); 
  		 });
  		 
  	 }
   
   
  	 if((_wizard).index==0){
  		 $('#Usereditemail').attr("disabled","disabled")
  	 }
   }
   
   function checkEmployeeStatus(){
  	 if($("#Usereditstatus").val() === "OffRolls" ){
			 $("#UsereditreleaseDate").addClass("addDisplayNoneClass");
		 }else if($("#Usereditstatus").val() === "OnRolls" ){
			 $("#UsereditreleaseDate").removeClass("addDisplayNoneClass");
		 }
   }
	    
 </script>