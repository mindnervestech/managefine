
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.List"%>
<!DOCTYPE html>
 
<%-- <link rel="stylesheet" media="screen" href='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'> --%>

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

			<div class="tabs">
				<ul class="tab-links">
					<li class="active"><a href="#tab1" data-toggle="tab">Project</a></li>
					<li><a href="#tab2" ng-click="defineParts()" ng-if="${editNodeMetaData.level == 0}" data-toggle="tab">Define Parts</a></li> 
					<li><a href="#tab3" ng-click="history()" data-toggle="tab">History</a></li>
					<li style="margin-left: 323px;font-size: 19px;color: #0044cc;margin-top: 6px;">Total Eestimated Revenue : {{totalEstimat}}</li>
				</ul>
				<div class="tab-content" style="max-height: 437px;">
					<div id="tab1" class="tab active">
					
						<div class="form-group">
						<form method="POST" commandName="createProjectForm"
							onsubmit="return myFunction()" id="form" method="post">
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
							<div class="form-group">
								<div class="col-md-6" style="margin-top: 15px;">
									<label class="col-md-12">Start Date</label>
									<div class="col-md-6">
										<div class="dropdown">
											<a class="dropdown-toggle" id="dropdownstartdate"
												role="button" data-toggle="dropdown" data-target="#"
												href="#">
												<div class="input-group">
													<input type="text" class="form-control" name="startDate"
														value="{{data.startDate | date:'dd-MM-yyyy'}}">
												</div>
											</a>
											<ul class="dropdown-menu" role="menu"
												aria-labelledby="dLabel"
												ng-init="initDate('${editNodeMetaData.startDate}','${editNodeMetaData.endDate}')">
												<datetimepicker data-ng-model="data.startDate"
													data-datetimepicker-config="{ dropdownSelector: '#dropdownstartdate',minView: 'day', minDate:'${editNodeMetaData.startDateLimit}',maxDate:'${editNodeMetaData.endDateLimit}' }" />
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
											<a class="dropdown-toggle" id="dropdownendDate" role="button"
												data-toggle="dropdown" data-target="#" href="#">
												<div class="input-group">
													<input type="text" class="form-control" name="endDate"
														value="{{data.endDate | date:'dd-MM-yyyy'}}">

												</div>
											</a>
											<ul class="dropdown-menu" role="menu"
												aria-labelledby="dLabel">
												<datetimepicker data-ng-model="data.endDate"
													data-datetimepicker-config="{ dropdownSelector: '#dropdownendDate',minView: 'day', minDate:'${editNodeMetaData.startDateLimit}',maxDate:'${editNodeMetaData.endDateLimit}' }" />
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
										<select name="projectManager" 
											 style="width: 100%;" required>
											<option ng-repeat="projectMinfo in findUser"
												value="{{projectMinfo.id}}" 
												ng-selected="${editNodeMetaData.projectManager} == projectMinfo.id">{{projectMinfo.firstName}}</option>
										</select>
									</div>
								</div>
								
								<%-- <div class="col-md-6" style="margin-top: 15px;"
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
								</div> --%>
								
								
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
								<div class="col-md-6" style="margin-top: 15px;"
									ng-if="${editNodeMetaData.level} == 0">
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
										<select name="member" multiple="multiple"
											ng-model="findSelectedUser" ui-select2 style="width: 100%;"
											required>
											<option ng-repeat="memberinfo in findUser"
												value="{{memberinfo.id}}">{{memberinfo.firstName}}</option>
										</select>
									</div>
								</div>
								<div style="display: none;" id="closeDialog"
									ng-click="closeThisDialog()"></div>
							</div>

							<input size="16" type="text"
								value='${editNodeMetaData.projectTypes}'
								placeholder="Enter String" style="display: none;"
								name="projectT"> <input size="16" type="text"
								value='${editNodeMetaData.projectDescription}'
								placeholder="Enter String" style="display: none;"
								name="projectD"> <input size="16" type="text"
								value='${editNodeMetaData.projectId}' placeholder="Enter String"
								style="display: none;" name="projecttypeId"> <input
								size="16" type="text" value='${editNodeMetaData.thisNodeId}'
								placeholder="Enter String" style="display: none;"
								name="projectId"> <input size="16" type="text"
								value='{{MainInstance}}' placeholder="Enter String"
								style="display: none;" name="projectInstance">
							<hr style="width: 100%; float: left;">

							<div class="form-group">
								<div class="col-md-12" style="padding: 0px;">
									<div class="form-group">
										<c:forEach var="pValue"
											items="${editNodeMetaData.projectValue}">
											<div class="col-md-6" style="height: 60px;">
												<div class="col-md-12">
													<label>${pValue.name} :</label>
												</div>
												<c:choose>
													<c:when test="${pValue.type == 'Date'}">
														<div class="col-md-12">
															<input type="date" value='${pValue.attriValue}'
																name="${pValue.name}" />
														</div>
													</c:when>
													<c:when test="${pValue.type == 'Integer'}">
														<div class="col-md-12">
															<input size="16" type="text" value='${pValue.attriValue}'
																placeholder="Enter Number" name="${pValue.name}">
														</div>
													</c:when>
													<c:when test="${pValue.type == 'String'}">
														<div class="col-md-12">
															<input size="16" type="text" value='${pValue.attriValue}'
																placeholder="Enter String" name="${pValue.name}">
														</div>
													</c:when>
													<c:when test="${pValue.type == 'Checkbox'}">
														<div class="col-md-12">
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
														<div class="col-md-12">
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
					<div id="tab2" class="tab">

						<div class="form-group">
							 	<div class="form-group" ng-repeat="find in defineParts.partsValue">
				  					
								  <div class="col-md-2">
				  				      <label class="col-md-12">Part No</label>
							          <select class="col-md-12" name="partNo{{$index}}" data-ng-model="find.partNo" ng-change="selectValue(find.partNo)" placeholder="Select Part No.">
										<option ng-repeat="parts in partNos"
												value="{{parts.id}}" ng-selected="parts.id == find.partNo">{{parts.partNo}}</option>
										
					      			  </select>	
				   				  </div>
				   				  <div class="col-md-2" style="width: 120px;">
									  <div class="col-md-12">
						
								         <label>AnnQty</label>
				    				  </div>
				       				  <div class="col-md-12">
				         
					   						  <input type="text" class="col-md-12" name="annualQty{{$index}}"  ng-change="calculatestimat(find.annualQty,find.suggestedResale,$index)" data-ng-model="find.annualQty">
					    			  </div>
								  </div>
								   <div class="col-md-2" style="width: 120px;">
									  <div class="col-md-12">
						
								         <label>CPrice</label>
				    				  </div>
				       				  <div class="col-md-12">
				         
					   						  <input type="text" class="col-md-12" name="costPrice{{$index}}" data-ng-model="find.costPrice">
					    			  </div>
								  </div>
								   <div class="col-md-2" style="width: 120px;">
									  <div class="col-md-12">
						
								         <label>SugResale </label>
				    				  </div>
				       				  <div class="col-md-12">
				         
					   						  <input type="text" class="col-md-12" name="suggestedResale{{$index}}" ng-change="calculatestimat(find.annualQty,find.suggestedResale,$index)" data-ng-model="find.suggestedResale">
					    			  </div>
								  </div>
								  <div class="col-md-2" style="width: 130px;">
									  <div class="col-md-12">
						
								         <label>EstRevenue </label>
				    				  </div>
				       				  <div class="col-md-12">
				         
					   						  <input type="text" class="col-md-12" name="estimatedRevenue{{$index}}" data-ng-model="find.estimatedRevenue" disabled="disabled" value="{{totalEstimat}}">
					    			  </div>
								  </div>
								  
								  <div class="col-md-2" style="width: 164px;">
									  <div class="col-md-12">
						
								         <label>Claim Status</label>
				    				  </div>
				       				  <div class="col-md-12">
				         
									 <select class="col-md-12" name="claimStatus{{$index}}" data-ng-model="find.claimStatus" placeholder="Select Claim Status." style="padding: 0px;">
										<option value="Approved" selected>Approved</option>
										<option value="Requested">Requested</option>
										<option value="Rejected">Rejected</option>
										
					      			  </select>	
					    			  </div>
								  </div>
				   				 
				   				   <div class="col-md-1" style="width: 66px;">
										<button type="button" class="btn btn-primary" style="margin-left: 8px;margin-top: 20px;width: 41px;" ng-click="newpartValue($event)"> + </button> 	
								   </div>
								  <div class="col-md-1" >
								     <span ng-show="defineParts.partsValue.length > 1"> 
									  	<button class="btn btn-default" style="margin-top: 20px;" ng-click="removeDefinepart($index)"><i class="icon-remove"></i></button>
									  </span> 
								   </div>
								  
								   
					 
							 <div class="col-md-12">
					           			
							 </div> 
				
						<br>
					
							</div> 
							<div class="col-md-12">
							<button type="button" class="btn btn-primary" ng-click="saveParts()" > Save </button>
							<label ng-show="definePartSaveMsge == 1" style="color: green;">Define Part Save successfully </label>
							</div>
						</div>

					</div>
					<div id="tab3" class="tab">

						<div class="form-group">
							<div class="col-md-12" ng-repeat="his in historyRecord">
							<label>{{his.changeDate}}</label>
								<table class="table table-striped" border="1"
									style="width: 100%; font-size: small; margin-top: 10px;">
									<thead style="background-color: #005580;">
										<tr>
											<th style="text-align: center; width: 36px; padding: 6px;"><a
												style="color: white;">Sr.No</a></th>
											<th style="text-align: center;"><a style="color: white;">Entity</a></th>
											<th style="text-align: center;"><a style="color: white;">Old Value</a></th>
											<th style="text-align: center; color: white;">New Value</th>
											<th style="text-align: center; color: white;">User</th>
										</tr>
									</thead>
									<tbody>

										<tr ng-repeat="value in his.historyAllLogVM">
											<td style="width: 22px; text-align: center; padding: 6px;">{{$index+1}}</td>
											<td style="text-align: center;">{{value.property}}</td>
											<td style="text-align: center;">{{value.oldVal}}</td>
											<td style="text-align: center;">{{value.newVal}}</td>
											<td style="text-align: center;">{{value.userName}}</td>

										</tr>

									</tbody>
								</table>
							</div>
						</div>

					</div>
					
					
				</div>
				</div>
				<div class="modal-body" style="max-height: 800px;">

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


/* --------------- */


/*----- Tabs -----*/
.tabs {
    width:100%;
    display:inline-block;
}
 
    /*----- Tab Links -----*/
    /* Clearfix */
    .tab-links:after {
        display:block;
        clear:both;
        content:'';
    }
 
    .tab-links li {
        margin:0px 5px;
        float:left;
        list-style:none;
    }
 
        .tab-links a {
            padding:9px 15px;
            display:inline-block;
            border-radius:3px 3px 0px 0px;
            background:#7FB5DA;
            font-size:16px;
            font-weight:600;
            color:#4c4c4c;
            transition:all linear 0.15s;
        }
 
        .tab-links a:hover {
            background:#a7cce5;
            text-decoration:none;
        }
 
    li.active a, li.active a:hover {
        background:#fff;
        color:#4c4c4c;
    }
 
    /*----- Content of Tabs -----*/
    .tab-content {
        padding:15px;
        border-radius:3px;
        box-shadow:-1px 1px 1px rgba(0,0,0,0.15);
        background:#fff;
    }
 
        .tab {
            display:none;
        }
 
        .tab.active {
            display:block;
        }

/* ------------------ */


</style>


 	  <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>
 	  
 <script type="text/javascript">
 
 
 

</script>






