<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script src='<c:url value="/resources/javascripts/jquery.chainedSelects.js"/>'
	type="text/javascript"></script>
<script src='<c:url value="/resources/customScripts/timesheetRow.js"/>'
	type="text/javascript"></script>
<div id="timeSheetTable" style="float: left;width: 85%;"> 

	<div class="worksheetHeader">
		<h5>Work/Absence Hours Reporting</h5>
		<div id="statusInfo">
			<h6>With :</h6>
			<label style="margin: 4px 10px 0 0; padding: 4px 0; float: left;">
				${user.firstName} ${user.lastName} </label> <label
				style="margin: 4px 0px 0 0; padding: 4px 0; float: left;">/</label>
			<h6>Status :</h6>
			<label style="margin: 4px 0; padding: 4px 0; float: left;"
				id="outputStatus"></label>
			<p></p>
		</div>
	</div>

	<div style="width: 96%;"class="reportingTable">
		<div class="tableCss" style="margin-left: 1%;width: 904px;">
			<div class="innerLabelDiv">
				<div class="largeInputLabel largeInputLabel_First clearfix">Project
					Codes</div>
				<div class="largeInputLabel clearfix">Task Codes</div>
			</div>
			<div class="innerDaysDiv">
				<div class="smallInputLabel clearfix">
					M<br> <span id="dayLabel_0"></span>
				</div>
				<div class="smallInputLabel clearfix">
					T<br> <span id="dayLabel_1"></span>
				</div>
				<div class="smallInputLabel clearfix">
					W<br> <span id="dayLabel_2"></span>
				</div>
				<div class="smallInputLabel clearfix">
					Th<br> <span id="dayLabel_3"></span>
				</div>
				<div class="smallInputLabel clearfix">
					F<br> <span id="dayLabel_4"></span>
				</div>
				<div class="smallInputLabel clearfix">
					S<br> <span id="dayLabel_5"></span>
				</div>
				<div class="smallInputLabel clearfix">
					S<br> <span id="dayLabel_6"></span>
				</div>
				<div class="smallInputLabel totalHRSLabel clearfix">T</div>
				<div class="smallInputLabel clearfix">Overtime</div>
				<a class="btn" id="addMore" style="margin-left: 11px; float: left;"><b>+</b></a>
			</div>
		</div>

		<c:forEach var="timeSheetRow" varStatus="loopIndex"
			items="${timesheetForm.model.timesheetRows}">
			<div class="twipsies well timesheetRow" style="width: 100%;margin-left: 1.5%;">
				<div class="innerInputDiv">
					<div class="innerChainSelect">
						<div class="clearfix"
							id="timesheetRows_${loopIndex.index}__projectCode_field">
							<label for="timesheetRows_${loopIndex.index}__projectCode"></label>
							<div class="input">
								<select id="timesheetRows_${loopIndex.index}__projectCode"
									name="timesheetRows[${loopIndex.index}].projectCode"
									class="largeInput largeInputFirst required">
									<option class="blank" value="">-select-</option>
									<c:forEach var="r" items="${projectsList}">
										<c:choose>
											<c:when test="${r == timeSheetRow.projectCode}">
												<option selected="selected" value="${r}">${r}</option>
											</c:when>
											<c:otherwise>
												<option value="${r}">${r}</option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</select> <span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
						<div class="clearfix"
							id="timesheetRows_${loopIndex.index}__taskCode_field">
							<label for="timesheetRows_${loopIndex.index}__taskCode"></label>
							<div class="input">
								<c:choose>
									<c:when
										test="${timesheetForm != null && timeSheetRow.projectCode!= 'None'}">
										<select id="timesheetRows_${loopIndex.index}__taskCode"
											name="timesheetRows[${loopIndex.index}].taskCode"
											class="largeInput taskInput">
											<option class="blank" value="">-select-</option>
											<c:forEach var="r"
												items="${timesheetForm.model.timesheetRows}">
												<option value="${r.taskCode}">${r.taskCode}</option>
												<c:choose>
													<c:when test="${r.taskCode == timeSheetRow.taskCode}">
														<option selected="selected" value="${r.taskCode}">${r.taskCode}</option>
													</c:when>
													<c:otherwise>
														<option value="${r.taskCode}">${r.taskCode}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<h4>${timesheetRows.get(loopIndex.index).taskCode}</h4>
										<select id="timesheetRows_${loopIndex.index}__taskCode"
											name=timesheetRows[${loopIndex.index}].taskCode
											class="largeInput taskInput">
										</select>
									</c:otherwise>
								</c:choose>
								<span class="help-inline"></span> <span class="help-block"></span>
							</div>
						</div>
					</div>
				</div>
				<div style="innerHrsDiv"class="innerHrsDiv">
					<div class="clearfix"
						id="timesheetRows_${loopIndex.index}__mon_field">
						<label for="timesheetRows_${loopIndex.index}__mon"></label>
						<div style="margin-top: 8%;"class="input">
							<input type="number"  min="0" max="24" id="timesheetRows_${loopIndex.index}__mon"
								name="timesheetRows[${loopIndex.index}].mon"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).mon}"
								placeholder="hr" class="smallInput dayName"> <span
								class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>
					<div class="clearfix"
						id="timesheetRows_${loopIndex.index}__tue_field">
						<label for="timesheetRows_${loopIndex.index}__tue"></label>
						<div class="input">
							<input type="number"  min="0" max="24" id="timesheetRows_${loopIndex.index}__tue"
								name="timesheetRows[${loopIndex.index}].tue"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).tue}"
								placeholder="hr" class="smallInput dayName"> <span
								class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>
					<div class="clearfix"
						id="timesheetRows_${loopIndex.index}__wed_field">
						<label for="timesheetRows_${loopIndex.index}__wed"></label>

						<div class="input">
							<input type="number" min="0" max="24" id="timesheetRows_${loopIndex.index}__wed"
								name="timesheetRows[${loopIndex.index}].wed"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).wed}"
								placeholder="hr" class="smallInput dayName"> <span
								class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>
					<div class="clearfix"
						id="timesheetRows_${loopIndex.index}__thu_field">
						<label for="timesheetRows_${loopIndex.index}__thu"></label>

						<div class="input">
							<input type="number"  min="0" max="24" id="timesheetRows_${loopIndex.index}__thu"
								name="timesheetRows[${loopIndex.index}].thu"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).thu}"
								placeholder="hr" class="smallInput dayName"> <span
								class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>

					<div class="clearfix"
						id="timesheetRows_${loopIndex.index}__fri_field">
						<label for="timesheetRows_${loopIndex.index}__fri"></label>
						<div class="input">
							<input type="number"  min="0" max="24" id="timesheetRows_${loopIndex.index}__fri"
								name="timesheetRows[${loopIndex.index}].fri"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).fri}"
								placeholder="hr" class="smallInput dayName"> <span
								class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>
					<div class="clearfix"
						id="timesheetRows_${loopIndex.index}__sat_field">
						<label for="timesheetRows_${loopIndex.index}__sat"></label>

						<div class="input">
							<input type="number" min="0" max="24" id="timesheetRows_${loopIndex.index}__sat"
								name="timesheetRows[${loopIndex.index}].sat"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).sat}"
								placeholder="hr" class="smallInput dayName"> <span
								class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>
					<div class="clearfix"
						id="timesheetRows_${loopIndex.index}__sun_field">
						<label for="timesheetRows_${loopIndex.index}__sun"></label>

						<div class="input">
							<input type="number"  min="0" max="24" id="timesheetRows_${loopIndex.index}__sun"
								name="timesheetRows[${loopIndex.index}].sun"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).sun}"
								placeholder="hr" class="smallInput dayName"> <span
								class="help-inline"></span> <span class="help-block"></span>
						</div>
					</div>
				</div>

				<div class="totalHrsDiv">
					<div class="clearfix  "
						id="timesheetRows_${loopIndex.index}__totalHrs_field">
						<label for="timesheetRows_${loopIndex.index}__totalHrs"></label>
						<div class="input">

							<input type="text"
								id="timesheetRows_${loopIndex.index}__totalHrs"
								name="timesheetRows[${loopIndex.index}].totalHrs"
								value="${timesheetForm.model.timesheetRows.get(loopIndex.index).totalHrs}"
								placeholder="" class="smallInput totalHRSInput readonlycls"
								readonly="readonly"> <span class="help-inline"></span> <span
								class="help-block"></span>
						</div>
					</div>
				</div>
				<div class="clearfix"
					id="timesheetRows_${loopIndex.index}__overTime_field">
					<label for="timesheetRows_${loopIndex.index}__overTime"></label>
					<div class="input">

						<input type="checkbox"
							id="timesheetRows_${loopIndex.index}__overTime"
							name="timesheetRows[${loopIndex.index}].overTime" value="true"
							class="checkBox checkBoxInput"> <span></span> <span
							class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>
				<a class="remove btn danger pull-right" id="remove">X</a>
			</div>
		</c:forEach>

	</div>

	<div class="twipsies well timesheetRow_template" style="width: 84%;margin-left: 1.5%;">
		<div class="innerInputDiv">
			<div class="innerChainSelect">

				<div class="clearfix" id="timesheetRows_x__projectCode_field">
					<label for="timesheetRows_x__projectCode"></label>
					<div class="input">
						<select id="timesheetRows_x__projectCode"
							name="timesheetRows[x].projectCode"
							class="largeInput largeInputFirst required">
							<option class="blank" value="">-select-</option>
							<c:forEach var="r" items="${projectsList}">
								<c:choose>
									<c:when test="${r == timeSheetRow.projectCode}">
										<option selected="selected" value="${r}">${r}</option>
									</c:when>
									<c:otherwise>
										<option value="${r}">${r}</option>
									</c:otherwise>
								</c:choose>

							</c:forEach>

						</select> <span class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>
				<div class="clearfix" id="timesheetRows_x__taskCode_field">
					<label for="timesheetRows_x__taskCode"></label>
					<div class="input">

						<c:choose>
							<c:when
								test="${timesheetForm != null && timeSheetRow.projectCode!= 'None'}">
								<select id="timesheetRows_x__taskCode"
									name=timesheetRows[x].taskCode class="largeInput taskInput">
									<option class="blank" value="">-select-</option>
								</select>
							</c:when>
							<c:otherwise>
								<select id="timesheetRows_x__taskCode"
									name=timesheetRows[x].taskCode class="largeInput taskInput">
									<option class="blank" value=""></option>
								</select>
							</c:otherwise>
						</c:choose>
						<span class="help-inline"></span> <span class="help-block"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="innerHrsDiv">
			<div class="clearfix" id="timesheetRows_x__mon_field">
				<label for="timesheetRows_x__mon"></label>
				<div class="input">
					<input type="number"  min="0" max="24" id="timesheetRows_x__mon"
						name="timesheetRows[x].mon" value="" placeholder="hr"
						class="smallInput dayName"> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>
			<div class="clearfix" id="timesheetRows_x__tue_field">
				<label for="timesheetRows_x__tue"></label>
				<div class="input">
					<input type="number"  min="0" max="24" id="timesheetRows_x__tue"
						name="timesheetRows[x].tue" value="" placeholder="hr"
						class="smallInput dayName"> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>
			<div class="clearfix" id="timesheetRows_x__wed_field">
				<label for="timesheetRows_x__wed"></label>

				<div class="input">
					<input type="number"  min="0" max="24" id="timesheetRows_x__wed"
						name="timesheetRows[x].wed" value="" placeholder="hr"
						class="smallInput dayName"> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>
			<div class="clearfix" id="timesheetRows_x__thu_field">
				<label for="timesheetRows_x__thu"></label>

				<div class="input">
					<input type="number"  min="0" max="24" id="timesheetRows_x__thu"
						name="timesheetRows[x].thu" value="" placeholder="hr"
						class="smallInput dayName"> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>

			<div class="clearfix" id="timesheetRows_x__fri_field">
				<label for="timesheetRows_x__fri"></label>
				<div class="input">
					<input type="number"  min="0" max="24" id="timesheetRows_x__fri"
						name="timesheetRows[x].fri" value="" placeholder="hr"
						class="smallInput dayName"> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>
			<div class="clearfix" id="timesheetRows_x__sat_field">
				<label for="timesheetRows_x__sat"></label>

				<div class="input">
					<input type="number"  min="0" max="24" id="timesheetRows_x__sat"
						name="timesheetRows[x].sat" value="" placeholder="hr"
						class="smallInput dayName"> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>
			<div class="clearfix" id="timesheetRows_x__sun_field">
				<label for="timesheetRows_x__sun"></label>

				<div class="input">
					<input type="number"  min="0" max="24" id="timesheetRows_x__sun"
						name="timesheetRows[x].sun" value="" placeholder="hr"
						class="smallInput dayName"> <span class="help-inline"></span> <span
						class="help-block"></span>
				</div>
			</div>
		</div>

		<div class="totalHrsDiv">
			<div class="clearfix  " id="timesheetRows_x__totalHrs_field">
				<label for="timesheetRows_x__totalHrs"></label>
				<div class="input">

					<input type="text" id="timesheetRows_x__totalHrs"
						name="timesheetRows[x].totalHrs" value="" placeholder=""
						class="smallInput totalHRSInput readonlycls" readonly="readonly">
					<span class="help-inline"></span> <span class="help-block"></span>
				</div>
			</div>
		</div>
		<div class="clearfix" id="timesheetRows_x__overTime_field">
			<label for="timesheetRows_x__overTime"></label>
			<div class="input">

				<input type="checkbox" id="timesheetRows_x__overTime"
					name="timesheetRows[x].overTime" value="true"
					class="checkBox checkBoxInput"> <span></span> <span
					class="help-inline"></span> <span class="help-block"></span>
			</div>
		</div>
		<a class="remove btn danger pull-right" id="remove">X</a>
	</div>
	<div class="actions">
		<input type="button" id="copyFromLastWeek" class="btn btn-warning"
			Value="Copy from last week"> <input type="button"
			id="saveTimesheetForm" class="btn btn-warning" value="Save">
		<input type="button" id="submitTimesheetForm" class="btn btn-warning"
			value="Submit"> <input type="button"
			id="retractTimesheetForm" class="btn btn-warning" value="Retract">
		<input type="hidden" id="cancelTimesheetForm" class="btn btn-warning"
			Value="Cancel">
	</div>
</div>

<div class="clearfix  " id="id_field">
	<label for="id"></label>
	<div class="input">

		<c:if test="${timesheetForm!=null}">
			<input type="hidden" id="id" name="id"
				value="${timesheetForm.model.id}" class="TIMEID">
		</c:if>

		<span class="help-inline"></span> <span class="help-block"></span>
	</div>
</div>
<div class="clearfix  " id="status_field">
	<label for="status"></label>
	<div class="input">
		<div class="input">
			<select id="status" name="status"
				class="mediumInput hidden inputStatus" type="hidden">
				<c:forEach var="r" items="${TimesheetStatus}">
					<c:choose>
						<c:when test="${r.toString() == timesheetForm.model.status}">
							<option selected value="${r.toString()}">${r.toString()}</option>
						</c:when>
						<c:otherwise>
							<option value="${r}">${r}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> <span class="help-inline"></span> <span class="help-block"></span>
		</div>
	</div>
</div>

<style>
.checkBox {
	width: 15px !important;
	margin: 8px !important;
}

</style>


<script>
	$(".dayName").focusout (function() {
		var d = this.value;
		console.log(d);
		
		  if(parseInt(d) > 24) {
			  $(this).val(0);
		  }
	});
	
	$( "body" ).on( "focusout", ".dayName", function() {
		var d = this.value;
		  if(parseInt(d) > 24) {
			  $(this).val(0);

		  }
	});

</script>
