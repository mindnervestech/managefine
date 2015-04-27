
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.List"%>
<!DOCTYPE html>
 
 <link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'>

<c:set var="projectName" value='${editNodeMetaData.projectTypes}'/>   

<c:set var="projectDescription" value='${editNodeMetaData.projectDescription}'/>


<div id="myModal" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true"
	style="width: 80%; margin-left: 10%; display: block;">
	<div class="modal-dialog" style="width: 100%; height: 100%;">
		<div class="modal-content" style="width: 100%; height: 100%;">
			<div class="modal-header" style="background: #005580; color: white;height: 43px;padding: 3px;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true" style="color: white;font-size: 25px;">&times;</span>
				</button>
				<c:choose>
					<c:when test="${editNodeMetaData.level == 0}">
						<h4 class="modal-title" id="myModalLabel"
							style="margin-left: 18px;">Project Type</h4>
					</c:when>

					<c:when test="${editNodeMetaData.level == 1}">
						<h4 class="modal-title" id="myModalLabel"
							style="margin-left: 18px;">Project Stage</h4>
					</c:when>

					<c:when test="${editNodeMetaData.level == 2}">
						<h4 class="modal-title" id="myModalLabel"
							style="margin-left: 18px;">Stage-Task</h4>
					</c:when>
					<c:otherwise>
						<h4 class="modal-title" id="myModalLabel"
							style="margin-left: 18px;">Sub Task</h4>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="modal-body" style="max-height:800px;">
				<div class="form-group">
					<form method="POST" commandName="createProjectForm" onsubmit="return myFunction()"
						id="form" method="post">
						<div class="form-group">
							<div class="col-md-6">
								<label class="col-md-12">Project Type</label>
								<div class="col-md-12">
									<input type="text" name="projectName"
										value='${editNodeMetaData.projectTypes}' disabled="disabled"
										style="width: 100%;"> <label style="color: red"
										ng-if="projectT == 1">type is required</label>
								</div>
							</div>
							<div class="col-md-6">
								<label for="org-type" class="col-md-12">Project
									Description</label>
								<div class="col-md-12">
									<textarea name="projectDescription" rows="3"
										style="resize: none; width: 100%;"
										placeholder="Enter Project Description." disabled="disabled"
										value='${editNodeMetaData.projectDescription}'>${editNodeMetaData.projectDescription}</textarea>
									<label style="color: red" ng-if="projectD == 1">description
										is required</label>
								</div>
							</div>
						</div>
						
						${editNodeMetaData.startDateLimit}
						<div class="form-group">
							<div class="col-md-6" style="margin-top: 15px;">
								<label class="col-md-12">Start Date</label>
								<div class="col-md-6">
								   <div class="dropdown">
								   	  <a class="dropdown-toggle" id="dropdownstartdate" role="button" data-toggle="dropdown" data-target="#" href="#">
									    <div class="input-group">
									    <input type="text" class="form-control" 
									    	name= "startDate"
									    value="{{data.startDate | date:'dd-MM-yyyy'}}">
									    
									    </div>
									  </a>
									  <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" ng-init="initDate('${editNodeMetaData.startDate}','${editNodeMetaData.endDate}')">
									    <datetimepicker data-ng-model="data.startDate" 
									    data-datetimepicker-config="{ dropdownSelector: '#dropdownstartdate',minView: 'day' }"/>
									  </ul>
								   </div>
									<%-- <input type="date" name="startDate" style="width: 100%;"
										value='${editNodeMetaData.startDate}' required /> <label
										style="color: red" ng-if="projectT == 1">type is
										required</label> --%>
								</div>
							</div>
							<div class="col-md-6" style="margin-top: 15px;">
								<label for="org-type" class="col-md-12">End Date</label>
								<div class="col-md-6">
									<div class="dropdown">
								   	  <a class="dropdown-toggle" id="dropdownendDate" role="button" data-toggle="dropdown" data-target="#" href="#">
									    <div class="input-group">
									    <input type="text" class="form-control" 
									    	name= "endDate"
									    value="{{data.endDate | date:'dd-MM-yyyy'}}">
									    
									    </div>
									  </a>
									  <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" >
									    <datetimepicker data-ng-model="data.endDate" 
									    data-datetimepicker-config="{ dropdownSelector: '#dropdownendDate',minView: 'day' }"/>
									  </ul>
								   </div>
									<%-- <input type="date" name="endDate" style="width: 100%;"
										value='${editNodeMetaData.endDate}' required  /> --%>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6" style="margin-top: 15px;"
								ng-if="${editNodeMetaData.level} == 0">
								<label class="col-md-12">Project Manager</label>
								<div class="col-md-12">
									<select name="projectManager" ui-select2
										ng-model="projectManagerData" style="width: 100%;" required>
										<option ng-repeat="projectMinfo in findUser"
											value="{{projectMinfo.id}}" required 
											ng-selected="${editNodeMetaData.projectManager} == projectMinfo.id">{{projectMinfo.firstName}}</option>
									</select>
								</div>
							</div>
							<div class="col-md-6" style="margin-top: 15px;">
								<label class="col-md-12">Weightage*</label>
								<div class="col-md-12">
									<select style="width: 100%;" name="weightage" required>
										<option value="1"
											ng-selected="${editNodeMetaData.weightage} == '1'">1</option>
										<option value="2"
											ng-selected="${editNodeMetaData.weightage} == '2'">2</option>
										<option value="3"
											ng-selected="${editNodeMetaData.weightage} == '3'">3</option>
										<option value="4"
											ng-selected="${editNodeMetaData.weightage} == '4'">4</option>
										<option value="5"
											ng-selected="${editNodeMetaData.weightage} == '5'">5</option>
									</select>

								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6" style="margin-top: 15px;"  ng-if="${editNodeMetaData.level} == 0">
								<label class="col-md-12">Suppliers</label>
								<div class="col-md-12">
									<select name="supplier" multiple="multiple" ui-select2
										ng-model="findSelectedSupplier" style="width: 100%;" required>
										<option ng-repeat="supplierinfo in findSupplier" 
											value="{{supplierinfo.id}}">{{supplierinfo.supplierName}}</option>
									</select>
								</div>
							</div>
							<div class="col-md-6" style="margin-top: 15px;">
								<label class="col-md-12">Members</label>
								<div class="col-md-12">
									<select name="member" multiple="multiple" ng-model="findSelectedUser" ui-select2
										style="width: 100%;" required>
										<option ng-repeat="memberinfo in findUser"
											value="{{memberinfo.id}}">{{memberinfo.firstName}}</option>
									</select>
								</div>
							</div>
							<div style="display:none;" id="closeDialog" ng-click="closeThisDialog()"></div>
						</div>

						<%-- <div class="form-group">
						<div class="col-md-4">
							<label class="col-md-12" style="margin-top: 16px;">Type</label> <input
								type="text" name="projectName"
								value='${editNodeMetaData.projectTypes}' disabled="disabled"
								class="col-md-11"> <label class="col-md-12"
								style="color: red" ng-if="projectT == 1">type is
								required</label>

						</div>
						<div class="col-md-2" style="margin-top: 42px;">
							<!-- <input type="color" ng-model="pro.projectColor" style="width: 64%;height: 26px;" class="ng-pristine ng-valid"> -->
						</div>

						<div class="col-md-6">
							<label class="col-md-11" style="margin-top: 16px;" for="pro-name">Description</label>
							<textarea class="col-md-11" name="projectDescription" rows="3"
								style="resize: none;" placeholder="Enter Project Description."
								disabled="disabled"
								value='${editNodeMetaData.projectDescription}'>${editNodeMetaData.projectDescription}</textarea>
							<label class="col-md-12" style="color: red" ng-if="projectD == 1">description
								is required</label>
						</div>
					</div>

					<div class="form-group" style="float: left;">
						<div class="col-md-4">
							<label class="col-md-12" style="margin-top: 16px;">Start
								Date</label> <input type="date" name="startDate"
								value='${editNodeMetaData.startDate}' /> <label
								class="col-md-12" style="color: red" ng-if="projectT == 1">type
								is required</label>
						</div>
						<div class="col-md-2" style="margin-top: 42px;">
							<!-- <input type="color" ng-model="pro.projectColor" style="width: 64%;height: 26px;" class="ng-pristine ng-valid"> -->
						</div>
						<div class="col-md-5" style="margin-left: 17px;">
							<label class="col-md-11" style="margin-top: 16px;" for="pro-name">End
								Date</label> <input type="date" name="endDate"
								value='${editNodeMetaData.endDate}' />
						</div>
					</div> --%>

						<%-- <div class="form-group">

						<div class="col-md-6" style="padding: 0px;">
							<label class="col-md-11"
								style="margin-top: 16px; margin-left: 14px;">Weightage *</label>
							<select class="col-md-4" name="weightage"
								style="margin-left: 15px;" required>
								<option value="">Select</option>
								<option value="1"
									ng-selected="${editNodeMetaData.weightage} == '1'">1</option>
								<option value="2"
									ng-selected="${editNodeMetaData.weightage} == '2'">2</option>
								<option value="3"
									ng-selected="${editNodeMetaData.weightage} == '3'">3</option>
								<option value="4"
									ng-selected="${editNodeMetaData.weightage} == '4'">4</option>
								<option value="5"
									ng-selected="${editNodeMetaData.weightage} == '5'">5</option>
							</select>


						</div>

						<div class="col-md-6" style="padding: 0px;">

							<label class="col-md-11" ng-if="${editNodeMetaData.level} == 0"
								style="margin-top: 16px; margin-left: 14px;">Project
								Manager</label>
							<input type="text" name="projectManager" ng-if="${editNodeMetaData.level} == 0" value='${editNodeMetaData.projectManager}' disabled="disabled" class="col-md-11">
							<select class="col-md-12" name="projectManager" ui-select2
								multiple="multiple" ng-model="projectManagerData"
								style="margin-left: 15px;" required>
								<option ng-repeat="projectMinfo in findUser"
									value="{{projectMinfo.id}}"
									ng-selected="${editNodeMetaData.projectManager} == projectMinfo.id">{{projectMinfo.firstName}}</option>
							</select>

						</div>
					</div> --%>

						<%-- <div class="col-md-12" ng-if="${editNodeMetaData.level} == 0">

						<div class="col-md-6">
							<label class="col-md-12"
								style="margin-top: 16px; margin-left: 14px;">Supplier</label> <select
								class="col-md-12" name="supplier" multiple="multiple"
								ng-model="supplierData" style="margin-left: 15px;" required>
								<option ng-repeat="supplierinfo in selectSupplier"
									value="{{supplierinfo.id}}"
									ng-selected="supplierinfo.selected == 'selected'">{{supplierinfo.supplierName}}</option>
							</select>


						</div>

						<div class="col-md-6">
							<label class="col-md-12"
								style="margin-top: 16px; margin-left: 14px;">Member</label> <select
								class="col-md-12" name="member" multiple="multiple"
								ng-model="memberData" style="margin-left: 15px;" required>
								<option ng-repeat="memberinfo in selectUser"
									value="{{memberinfo.id}}"
									ng-selected="memberinfo.selected == 'selected'">{{memberinfo.firstName}}</option>
							</select>
						</div>
					</div> --%>


						<input size="16" type="text"
							value='${editNodeMetaData.projectTypes}'
							placeholder="Enter String" style="display: none;" name="projectT">
						<input size="16" type="text"
							value='${editNodeMetaData.projectDescription}'
							placeholder="Enter String" style="display: none;" name="projectD">
						<input size="16" type="text" value='${editNodeMetaData.projectId}'
							placeholder="Enter String" style="display: none;"
							name="projecttypeId"> <input size="16" type="text"
							value='${editNodeMetaData.thisNodeId}' placeholder="Enter String"
							style="display: none;" name="projectId"> <input size="16"
							type="text" value='{{MainInstance}}' placeholder="Enter String"
							style="display: none;" name="projectInstance">
						<hr style="width: 100%; float: left;">

						<div class="form-group">
							<div class="col-md-12">
								<div class="form-group">
									<c:forEach var="pValue"
										items="${editNodeMetaData.projectValue}">
										<div class="col-md-6" style="height: 60px;">
											<div class="col-md-12">
												<label>${pValue.name} :</label>
											</div>
											<c:choose>
												<c:when test="${pValue.type == 'Date'}">
													<div class="col-md-12" style="padding: 0px;">
														<input type="date" value='${pValue.attriValue}'
															name="${pValue.name}" />
													</div>
												</c:when>
												<c:when test="${pValue.type == 'Integer'}">
													<div class="col-md-12" style="padding: 0px;">
														<input size="16" type="text" value='${pValue.attriValue}'
															placeholder="Enter Number" name="${pValue.name}">
													</div>
												</c:when>
												<c:when test="${pValue.type == 'String'}">
													<div class="col-md-12" style="padding: 0px;">
														<input size="16" type="text" value='${pValue.attriValue}'
															placeholder="Enter String" name="${pValue.name}">
													</div>
												</c:when>
												<c:when test="${pValue.type == 'Checkbox'}">
													<div class="col-md-12" style="padding: 0px;">
														<c:forEach var="option" items='${pValue.valueSlice}'>
															<input class="col-md-1" type="checkbox"
																name="${pValue.name}" value='${option.value}'
																${option.select}>
															<span class="col-md-3"
																style="padding: 0px; margin-left: 3px;">${option.value}</span>
														</c:forEach>
													</div>
												</c:when>
												<c:when test="${pValue.type == 'Dropdown'}">
													<div class="col-md-12" style="padding: 0px;">
														<select id="Dropdown" name="${pValue.name}"
															class="input-large" value='${pValue.attriValue}'>
															<c:forEach var="option" items='${pValue.valueSlice}'>
																<option value='${option.value}'
																	<c:if test="${option.value eq pValue.attriValue}">Selected</c:if>>${option.value}</option>
															</c:forEach>
														</select>
													</div>
												</c:when>
												<c:when test="${pValue.type == 'Radio'}">
													<div class="col-md-12" style="padding: 0px;">
														<c:forEach var="option" items='${pValue.valueSlice}'>
															<input class="col-md-1" type="radio"
																name="${pValue.name}" value='${option.value}'
																<c:if test="${option.value eq pValue.attriValue}">checked</c:if>>
															<span class="col-md-3"
																style="padding: 0px; margin-left: 3px;">${option.value}</span>
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
							</div>
						</div>
						<div class="col-md-12">
							<button type="button" class="btn btn-default"
								style="float: right;" ng-click="closeThisDialog()">Close</button>
							<input type="submit" value="Submit" class="btn btn-primary"
								value="Submit" style="margin: 0% 2%; float: right;">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<style>
.modal-dialog {
	width: 747px;
}

#loading {
	display: none;
}
.select2-drop {
z-index:10000;
}
</style>


 	  <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>
 	  
 <script type="text/javascript">
 
 
 

</script>