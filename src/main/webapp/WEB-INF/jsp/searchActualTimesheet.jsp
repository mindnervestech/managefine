<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<jsp:include page="menuContext.jsp"/> 
	<c:set var="_searchContext" value="${context}" scope="request" />
	<c:set var="_parentSearchCtx" value="${null}" scope="request" />
	<c:set var="mode" value="add" scope="request" />
	<jsp:include page="searchContext.jsp"/>

<h5 id="My_TimeSheet_Approval_Bucket_caution" style="display:none;"> WILL YOU LIKE TO CONTINUE AND DISAPPROVE ??</h5>
<script type="text/javascript">
 $(document).ready(function(){
	 $("#Timesheetadd_editButton").show();
	 $("#Timesheetaddstatus").change(function(){
			if($("#Timesheetaddstatus :selected").val() =='Approved'){
				$("#Timesheetadd_editButton").hide();
			}
			else
			if($("#Timesheetaddstatus :selected").val() =='Submitted'){
				$("#Timesheetadd_editButton").hide();
			}
			else
				$("#Timesheetadd_editButton").show();
		});
  });
  </script>