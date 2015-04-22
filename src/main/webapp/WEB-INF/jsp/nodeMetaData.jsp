
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.List"%>
<!DOCTYPE html>
<link rel="stylesheet"  href='<c:url value="/resources/stylesheets/datepicker.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'>
<script src='<c:url value="/resources/javascripts/bootstrap-datepicker.js"/>' type="text/javascript"></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>


<c:set var="projectName" value='${nodeMetaData.projectTypes}'/>   

<c:set var="projectDescription" value='${nodeMetaData.projectDescription}'/>   


 <div id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;"> 
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: #005580;color: white;padding: 1px;border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
					<c:choose>
					
					<c:when
						test="${nodeMetaData.level == 0}">
						<h4 class="modal-title" id="myModalLabel" style="margin-left: 18px;">Project Type</h4>
					</c:when>
					
					<c:when
						test="${nodeMetaData.level == 1}">
						<h4 class="modal-title" id="myModalLabel" style="margin-left: 18px;">Project Stage</h4>
					</c:when>
					
					<c:when
						test="${nodeMetaData.level == 2}">
						<h4 class="modal-title" id="myModalLabel" style="margin-left: 18px;">Stage-Task</h4>
					</c:when>
					
					<c:otherwise>
						<h4 class="modal-title" id="myModalLabel" style="margin-left: 18px;">Sub Task</h4>
						
					</c:otherwise>
				</c:choose>
								
			</div>
			
			<%-- <h4>'${nodeMetaData.projectnode}'</h4> --%>
			<div style="padding-left: 5px;max-height: 500px;overflow: auto;">
			<form method="POST" commandName="createProjectForm"
		action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.CreateProject.saveCreateProjectAttributes.url%>" id="form"  name="myform" onsubmit="return validateform()" >
				<div class="examples">

 				 <div id="testDiv3"> 
				 <div class="form-group">
				 <div class="col-md-4">
				
						<label class="col-md-12" style="margin-top: 16px;">Type</label>
						  <input type="text" name="projectName" value='${nodeMetaData.projectTypes}' disabled="disabled" class="col-md-11">
						  
						  <label class="col-md-12" style="color:red" ng-if="projectT == 1">type is required</label>
						  
					</div>
					 <div class="col-md-2" style="margin-top: 42px;">
					<!-- <input type="color" ng-model="pro.projectColor" style="width: 64%;height: 26px;" class="ng-pristine ng-valid"> -->
					</div>
				
					 <div class="col-md-6">
						<label class="col-md-11" style="margin-top: 16px;" for="pro-name">Description</label>
						  <textarea  class="col-md-11" name="projectDescription" rows="1"
						 placeholder="Enter Project Description."disabled="disabled" value='${nodeMetaData.projectDescription}'>${nodeMetaData.projectDescription}</textarea>
						 <label class="col-md-12" style="color:red" ng-if="projectD == 1">description is required</label>
						 
					</div>
					</div>
				<div class="form-group" style="float:left;">
				
				 <div class="col-md-4">
						<label class="col-md-12" style="margin-top: 15px;">Start Date</label>
						  <input type="date" name="startDate" min='${nodeMetaData.startDateLimit}' max='${nodeMetaData.endDateLimit}'required/> 
						
					
						  
					</div>
					 <div class="col-md-2" style="margin-top: 40px;">
					<!-- <input type="color" ng-model="pro.projectColor" style="width: 64%;height: 26px;" class="ng-pristine ng-valid"> -->
					</div>
				
					 <div class="col-md-5" style="margin-left: 17px;">
						<label class="col-md-11" style="margin-top: 15px;"  for="pro-name">End Date</label>
						   <input type="date" name="endDate" min='${nodeMetaData.startDateLimit}' max='${nodeMetaData.endDateLimit}' required/> 
						
						 
					</div>
					</div>
					
					<div class="form-group">
					 
					 <div class="col-md-6" style="padding: 0px;">
						<label class="col-md-11" style="margin-top: 16px;margin-left: 14px;">Weightage *</label>
						<select class="col-md-5" name="weightage"  style="margin-left: 15px;" required>
								<option value="">Select</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
					        </select> 
						  
						  
					</div>
					
					<div class="col-md-6" style="padding: 0px;">
						<label class="col-md-11" ng-if="${nodeMetaData.level} == 0" style="margin-top: 16px;margin-left: 14px;">Project Manager</label>
						<input type="text" ng-if="${nodeMetaData.level} == 0" name="projectManager" value='${nodeMetaData.projectManager}' disabled="disabled" class="col-md-11">
						  
						  
					</div>
					</div>
					
					<div class="col-md-12" ng-if="${nodeMetaData.level} == 0">
					 
					 <div class="col-md-6">
						<label class="col-md-12" style="margin-top: 16px;margin-left: 14px;">Supplier</label>
						<select class="col-md-12" name="supplier" ui-select2 multiple="multiple"  style="margin-left: 15px;" required>
								<option ng-repeat="supplierinfo in findSelectedSupplier" value="{{supplierinfo.id}}">{{supplierinfo.supplierName}}</option>
					        </select> 
						  
						  
					</div>
					
					<div class="col-md-6">
						<label class="col-md-12" style="margin-top: 16px;margin-left: 14px;">Member</label>
						<select class="col-md-12" name="member" ui-select2 multiple="multiple"  style="margin-left: 15px;" required>
								<option ng-repeat="memberinfo in findSelectedUser" value="{{memberinfo.id}}">{{memberinfo.firstName}}</option>
					        </select> 
					</div>
					</div>
					
					<input size="16" type="text" value='${nodeMetaData.projectTypes}'
							placeholder="Enter String" style="display: none;" name="projectT">
					<input size="16" type="text" value='${nodeMetaData.projectDescription}'
							placeholder="Enter String" style="display: none;" name="projectD">
					<input size="16" type="text" value='${nodeMetaData.projectId}'
							placeholder="Enter String" style="display: none;" name="projecttypeId">
					
					<input size="16" type="text" value='${nodeMetaData.thisNodeId}'
							placeholder="Enter String" style="display: none;" name="projectId">
							
					 <input size="16" type="text" value='{{MainInstance}}'
							placeholder="Enter String" style="display: none;" name="projectInstance"> 
					
					<hr style="width: 100%;float: left;">
					<c:choose>
					
					<c:when
						test="${nodeMetaData.projectValue.size() != 0}">
											
					 <div class="form-group">
					 <div class="col-md-12" style="padding: 0px;margin-top: 17px;">
				
				<div class="modal-body" style="padding: 0px;">
				
				 <div class="form-group"> 
				
				<c:forEach var="pValue" items="${nodeMetaData.projectValue}" varStatus="index">
								
				<div class="col-md-6" style="height:60px;">
				<div class="col-md-12">
				<label>${pValue.name}:</label>
				
										
				</div>
				<c:choose>
					<c:when
						test="${pValue.type == 'Date'}">
						<div class="col-md-12" style="padding: 0px;">
				
        					   <input type="date" name="${pValue.name}" /> 
           
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'Integer'}">
						<div class="col-md-12" style="padding: 0px;">
						<input size="16" type="text" 
							placeholder="Enter Number" name="${pValue.name}">
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'String'}">
						<div class="col-md-12" style="padding: 0px;">
						<input size="16" type="text" 
							placeholder="Enter String" name="${pValue.name}">
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'Checkbox'}">
						<div class="col-md-12" style="padding: 0px;">
						<c:forEach var="option" items='${pValue.valueSlice}'>
								 <input class="col-md-1" type="checkbox" name="${pValue.name}" value='${option.value}'><span class="col-md-3" style="padding: 0px;margin-left: 3px;">${option.value}</span>
							</c:forEach>
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'Dropdown'}">
						<div class="col-md-12" style="padding: 0px;">
						<select id="Dropdown"
							name="${pValue.name}" class="input-large">

								<c:forEach var="option" items='${pValue.valueSlice}'>
									<option value='${option.value}'>${option.value}</option>
							</c:forEach>
						</select>
					  </div>
					</c:when>
					<c:when
						test="${pValue.type == 'Radio'}">
						<div class="col-md-12" style="padding: 0px;">
						<c:forEach var="option" items='${pValue.valueSlice}'>
									<input class="col-md-1" type="radio" name="${pValue.name}" value='${option.value}'><span class="col-md-3" style="padding: 0px;margin-left: 3px;">${option.value}</span>
							</c:forEach>
							</div>
					</c:when>
					
					<c:otherwise>
						<label>else</label>
					</c:otherwise>
				</c:choose>
				</div>
				
			 
				</c:forEach>
					
				 </div> 
		<!-- </fieldset>	 -->
					</div>
					</div>
						</c:when>
					</c:choose>
					
				
				
			<div class="modal-footer" style="background-color:white;border-top: 0px">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
			
			<input type="button" class="btn btn-primary" onClick="myFunction()"
					ng-click="closeThisDialog()" value="Submit" style="margin: 1% 2%; width: 8%;">
			</div>
				</div>
				</div>
				</form>
			</div>
		</div>
	</div>
</div>



<style>
 .modal-dialog{
	width: 747px; 
 }

	#loading{
		display:none;
	}

</style>
 	  <script type="text/javascript">
    $(function(){
     
      $('#testDiv3').slimScroll({
          color: '#00f',
          height: 480,
          alwaysVisible: true
      });
    
    });
</script>  
 