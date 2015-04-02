
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.List"%>
<!DOCTYPE html>
<link rel="stylesheet"  href='<c:url value="/resources/stylesheets/datepicker.css"/>'>
<script src='<c:url value="/resources/javascripts/bootstrap-datepicker.js"/>' type="text/javascript"></script>
 <script type="text/javascript" src='<c:url value="/resources/javascripts/bootstrap-datetimepicker.min.js"/>'></script>

<c:set var="projectName" value='${nodeMetaData.projectTypes}'/>   

<c:set var="projectDescription" value='${nodeMetaData.projectDescription}'/>   


 <div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;"> 
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: blue;color: white;margin: 2px;padding: 1px;border-bottom: 4px gray;margin-bottom: -15px;">
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
			<div style="padding-left: 5px;padding-right: 5px;">
			<form method="POST" commandName="createProjectForm"
		action="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.CreateProject.saveCreateProjectAttributes.url%>" id="form">
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
					
					<input size="16" type="text" value='${nodeMetaData.projectTypes}'
							placeholder="Enter String" style="display: none;" name="projectT">
					<input size="16" type="text" value='${nodeMetaData.projectDescription}'
							placeholder="Enter String" style="display: none;" name="projectD">
					<input size="16" type="text" value='${nodeMetaData.projectId}'
							placeholder="Enter String" style="display: none;" name="projecttypeId">
					
					 <div class="form-group">
					 <div class="col-md-12">
					<fieldset  style="padding: .35em .625em .75em;margin: 0 2px;border: 1px solid;height: 271px;overflow: auto;margin-bottom: 7px;">
  		<legend style="width: 99px;border-style: none;margin-bottom: 1px;">Attributes </legend>
				
				<div class="modal-body" style="padding: 0px;">
				<!-- <div class="form-group">
				<button type="button" class="btn btn-primary" style="margin-left: 25px;" ng-click="newprojectValue($event)">Add </button>
				</div> -->
				
				<c:forEach var="pValue" items="${nodeMetaData.projectValue}">
				<div class="form-group">
				<div class="col-md-12" style="margin-bottom: 11px;">
				<div class="col-md-6">
				<label>${pValue.name} :</label>
				
				<input size="16" type="text" value='${pValue.projectnode}'
							placeholder="Enter String" style="display: none;" name="projectId">
							
				</div>
				<c:choose>
					<c:when
						test="${pValue.type == 'Date'}">
						<div class="col-md-6">
						<%-- <div id="${pValue.name}" data-provide="datepicker" data-date=""
							class="input-prepend date datepicker" data-date-format="dd-mm-yyyy">
							<span class="add-on" ng-click="open($event)"><i class="icon-calendar"></i></span> <input
							class="add-on" size="16" type="text" value=""
							placeholder="DD-MM-YYYY" name="${pValue.name}" is-open="opened">
						</div> --%>
					<!-- 	<input type="text" pick-a-date="date" placeholder="Select Date" /> {{ date }} -->
					   <!--  <div class="input-append date" id="dp3" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
 						   <input class="span2" size="16" type="text" value="12-02-2012">
   							 <span class="add-on"><i class="icon-th"></i></span>
   						 </div> -->
   						<!--  <input type="text" id="dp1" class="span2 datepicker" placeholder="Date..."  
           name="date"> <br> -->
           
           <input type="text"  /> 
           
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'Integer'}">
						<div class="col-md-6">
						<input size="16" type="text" value=""
							placeholder="Enter Number" name="${pValue.name}">
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'String'}">
						<div class="col-md-6">
						<input size="16" type="text" value=""
							placeholder="Enter String" name="${pValue.name}">
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'Checkbox'}">
						<div class="col-md-6">
						<c:forEach var="option" items='${pValue.valueSlice}'>
								 <input type="checkbox" name="${pValue.name}" value='${option.value}'>${option.value}
							</c:forEach>
						</div>
					
					</c:when>
					<c:when
						test="${pValue.type == 'Dropdown'}">
						<div class="col-md-6">
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
						<div class="col-md-6">
						<c:forEach var="option" items='${pValue.valueSlice}'>
									<input type="radio" name="${pValue.name}" value='${option.value}'>${option.value}
							</c:forEach>
							</div>
					</c:when>
					
					<c:otherwise>
						<label>else</label>
					</c:otherwise>
				</c:choose>
				</div>
				</div>
				
				</c:forEach>
				
				
		</fieldset>	
					</div>
					</div>
					
					
				
				
			<div class="modal-footer" style="background-color:white">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<input type="button" class="btn btn-primary" onClick="myFunction()"
					value="Submit" style="margin: 1% 2%; width: 8%;">
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


</style>
 	 