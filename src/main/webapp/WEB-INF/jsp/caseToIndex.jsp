<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div ng-app="ProjectHierarchyApp" ng-controller="casesController">
	<jsp:include page="menuContext.jsp" />
	<h4>
		<b style="width: 1051px; margin-left: 20px;"><i>Assigned To Me</i></b>
	</h4>

	<c:set var="_searchContext" value="${context}" scope="request" />
	<c:set var="_parentSearchCtx" value="${null}" scope="request" />
	<c:set var="mode" value="add" scope="request" />
	<jsp:include page="searchContext.jsp" />

	<link rel="stylesheet" media="screen"
		href='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
	<link rel="stylesheet" media="screen"
		href='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.css"/>'>



	<div class="form-group">
		<div class="col-md-12">
			<div id="dialog" title="Assigned To Me"
				style="background: white; height: 311px;">
				<div class="form-group" ng-init='getCaseId()'>
					<form name="myForm" method="post">
						<!-- action="${pageContext.request.contextPath}/caseToCreate"  // modelAttribute="pVm" action="/saveprojectTypeandName" -->
						<div>

							<div class="form-group">
								<label class="col-md-11">Status</label> <select
									class="col-md-5 required" style="margin-left: 14px;"
									id="caseStatus" name="caseStatus"
									ng-model="caseData.caseStatus"
									placeholder="Select Project type." required>
									<option value="">-select-</option>
									<option value="Assigned">Assigned</option>
									<option value="Inprogress">Inprogress</option>
									<option value="Completed">Completed</option>

								</select>

							</div>
							<hr style="width: 100%; float: left;">

							<!-- 	 <div class="form-group">
					 <div class="col-md-12" style="padding: 0px;">
						 <div class="col-md-2">
							 <span><b>Attachment:</b></span>
							  
						 </div>
						 <div class="col-md-5">
						 
						 	<input type="file" ng-file-select="selectFile($files)" id="org-profile" required>
						 
						 </div>
						
						
						<div class="col-md-2">
						</div>
						  
					</div>
					
					</div> -->

							<div class="form-group">
								<div class="col-md-11">
									<table class="table table-striped" border="1"
										style="width: 100%; font-size: small; margin-top: 10px;">
										<thead style="background-color: #005580;">
											<tr>
												<th style="text-align: center; width: 36px; padding: 6px;"><a
													style="color: white;">Sr.No</a></th>

												<th style="text-align: center;"><a
													style="color: white;">Documents</a></th>
												<th style="width: 81px; text-align: center; color: white;">Download</th>
											</tr>
										</thead>
										<tbody>

											<tr ng-repeat="doc in fileAttachData">
												<td style="width: 22px; text-align: center; padding: 6px;">{{$index+1}}</td>

												<td style="text-align: center;">{{doc.value}}</td>
												<td style="text-align: center;"><a
													ng-click="downloadfile(doc.id)">download</a></td>

											</tr>

										</tbody>
									</table>
								</div>
							</div>

							<hr style="width: 100%; float: left;">

							<div class="form-group">
								<div class="col-md-11">

									<span class="col-md-4" style="padding: 0px; margin-left: 3px;"><b>Activity
											:</b></span>
									<textarea class="form-control col-md-11" name="caseNote"
										ng-model="caseData.comment" rows="1" placeholder="Comment."
										style="margin-bottom: 7px;" required></textarea>



								</div>
								<div class="col-md-11" style="padding: 0px;">
									<div class="col-md-2">
										<span><b>Attachment:</b></span>

									</div>
									<div class="col-md-5">

										<input type="file" ng-file-select="selectFile($files)"
											id="org-profile" required>

									</div>

									<div class="col-md-3">
										<button type="submit" ng-click="saveCaseInfo()"
											class="btn btn-primary col-md-6">Save</button>
									</div>

								</div>
							</div>

							<div class="form-group" ng-repeat="note in comment">
								<div class="col-md-11" style="margin-bottom: 17px;">
									<span style="font-style: italic; font-size: 13px;">
										{{note.userName}} <span style="font-size: 10px;">{{note.commetDate}}</span>
									</span>

									<div class="comment_box  col-md-11">
										<p class="comment_paragraph" contenteditable="true">
											{{note.caseComment}}</p>
										<!-- <span class="date"><b>{{note.commetDate}}</b></span> </div> -->
									</div>

									<div class="col-md-11" ng-repeat="doc in note.projectAtt">
										<a ng-click="downloadNotesfile(doc.id)" style="color: blue;"><u><span>{{doc.docName}}</span></u></a>

									</div>

								</div>


							</div>


							<input class="offtheScreen" type="text" id="myitem"
								name="caseform" ng-change="showEditButton()"
								ng-model="selectedValue">
						</div>
						<br> <br> <br>
						<div class="col-md-11">
							<div class="col-md-8"></div>
							<!-- <button type="submit" ng-click="saveCaseInfo()" class="btn btn-primary col-md-3" >Save</button>  -->
							<!-- ng-click="addProjectName(projectinfo)" -->
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<style>
.comment_box {
	padding-bottom: 8px;
	height: auto;
	background: none repeat scroll 0% 0% ghostwhite;
	border: 1px solid #D8D8D8;
	position: relative;
}

.fr {
	float: right;
}

.comment_paragraph {
	color: #454545;
	line-height: 14px;
	margin: 4px 10px 0px 15px;
}

.ui-widget-header {
	background-color: #005580 !important;
	border: 1px solid #005580;
}

.offtheScreen {
	width: 0px;
	height: 0px;
	position: relative;
	top: -87px;
}
</style>

<script>
    $('#dialog').css('display','none');
    function CaseEditButtonFn(selItem) {
    	
    	$('#myitem').val(selItem);
    	
    	if(selItem) { 
			$('#myitem').trigger('change');
    	}
		
    }
</script>

<script type="text/javascript"
	src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/select2.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/javascripts/app/bower_components/angular-ui-select2/src/select2.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/customScripts/projectController/app.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/customScripts/casesController/controller.js"/>'></script>


