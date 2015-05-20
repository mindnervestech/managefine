<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <jsp:include page="menuContext.jsp"></jsp:include> 

<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/slimScroll/prettify/prettify.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'>

<div ng-app="ProjectHierarchyApp" ng-controller="createProjectController">
<%-- <span style="font-size: large;margin-left: 19px;margin-top: 12px;"><u><b>${createProject.projectName} </b></u>  (Client Name :${createProject.clientName}, Start Date : ${createProject.startDate} / End Date : ${createProject.endDate})</span> --%>

<div class="container" ng-init='viewHierarchy(${createProject.projectid},${createProject.id},"${createProject.projectName}","${createProject.clientName}","${createProject.startDate}","${createProject.endDate}")'><!--  viewHierarchy -->
	<div class="form-group">
						</div>
			
					<div>
					 
				
					</div>
					<div class="form-group">
    				
    			<div id="centerpanel"  style="overflow: hidden; padding: 0px; margin: 0px; border: 0px;">
					<div bp-org-diagram 
						data-options="myOptions" 
						data-on-highlight-changed="onMyHighlightChanged()"  
						data-on-cursor-changed="onMyCursorChanged()" 
						style="width: 98%; height: 600px; border-style: none; border-width: 1px;">
						
					</div>
					
    			</div>
    				
    				</div> 
</div>
</div>
<%-- <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular-datepicker.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/resources/javascripts/primitives/primitives.latest.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/slimScroll/prettify/prettify.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/slimScroll/jquery.slimscroll.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/jquery.fileDownload.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>
 
<script src='<c:url value="/resources/javascripts/bootstrap.min.js"/>' type="text/javascript"></script>
<%-- <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ui-bootstrap-tpls-0.12.1.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/resources/customScripts/projectController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/createProjectController/controller.js"/>'></script>


<style>

	.progress { 
  height: 3em;
  i { line-height: 3.5em; }
}

.progress-bar { 
  transition: width 1s ease-in-out; 
}

.progress-bar-success {
  background-color: #5cb85c;
}

.progress-striped .progress-bar-success {
  background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}


.progress-bar-warning {
  background-color: #f0ad4e;
}

.progress-striped .progress-bar-warning {
  background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}


.progress-bar-danger {
  background-color: #d9534f;
}

.progress-striped .progress-bar-danger {
  background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}

	.ui-pnotify{
			top: 99px;
			right: 47px;
	}
	.ui-pnotify-icon{
		display:none;
	}

</style>

<script>
   

function myFunction() {
	 var startD = document.forms["form"]["startDate"].value;
	 var endD = document.forms["form"]["endDate"].value;
	 var eDate = new Date(endD);
	  var sDate = new Date(startD);
	if (sDate < eDate) {
	$.ajax({
		type : "POST",
		data : $("#form").serialize(),
		url : "${pageContext.request.contextPath}/saveCreateProjectAttributes",
		success : function(data) {
			$("#closeDialog").trigger("click");
			/* $.pnotify({
                title: "Error",
                type:'error',
                text: "First Add Data",
            }); */
		}
	});
	}else{
		alert("Please ensure that the End Date is greater than Start Date.");
	}
}


</script> 
 <script type="text/javascript">
    $(function(){
     
      $('#testDiv3').slimScroll({
          color: '#00f',
          height: 480,
          alwaysVisible: true
      });
    
    });
</script>  
 
 
 