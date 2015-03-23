
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div  class="wizard" id='${_searchContext.entityName()}${"_some-wizard"}'>

	<c:forEach var="wizard" items="${_searchContext.getWizards()}">
		<%--J Open --%>
		<div class="wizard-card"
			data-cardname="${_searchContext.entityName()}${wizard.name()}"
			data-validate='${"form_"}${_searchContext.entityName()}${"_add_wizard"}'>
			
			<c:forEach var="fieldType" items="${wizard.card().iterator()}">
				<%--I Open --%>

				<c:choose>
					<%--H Open --%>
					<c:when test='${fieldType.ctype().name() == "INPUT"}'>
						<%-- G Open --%>
						<c:choose>
							<%-- F Open --%>
							<c:when test='${!fieldType.hidden()}'>
								<%-- E Open --%>
								<c:choose>
									<%-- C Open --%>

									<c:when
										test="${!fieldType.autocomplete() && !fieldType.multiselect()}">
										<%-- A Open--%>
										<c:if test="${fieldType.order() %2 == 1}">
											<div class="control-group"
												style="float: left; width: 46%; margin: 0px; height: 75px;">
										</c:if>
										<c:if test="${fieldType.order() % 2 != 1}">
											<div class="control-group" style="height: 75px;">
										</c:if>
										<label class="control-label" for="textinput">${fieldType.label()}
											<c:if
												test="${fieldType.validation()!=null && fieldType.validation().required()}">

												<sup style="color: red"> *</sup>
											</c:if>
										</label>
										<div class="controls">
											<c:choose>
												<c:when
													test="${fieldType.validation() !=null && fieldType.validation().required()}">
													<input
														id="${_searchContext.entityName()}${fieldType.name()}"
														name="${fieldType.name()}"
														placeholder="${fieldType.label()}"
														${fieldType.htmlAttrib()} required class="input-large"
														type="text" rel="popover">
												</c:when>
												<c:otherwise>
													<input
														id="${_searchContext.entityName()}${fieldType.name()}"
														name="${fieldType.name()}"
														placeholder="${fieldType.label()}"
														${fieldType.htmlAttrib()} class="input-large" type="text">
												</c:otherwise>
											</c:choose>
										</div>
		</div>
		</c:when>
		<%-- A close --%>
		<c:otherwise>
			<%-- B Open --%>
			<c:if test="${fieldType.order()%2==1}">
				<div class="control-group"
					style="float: left; width: 46%; margin: 0px; height: 75px;">
			</c:if>
			<c:if test="${fieldType.order()%2!=1}">
				<div class="control-group" style="height: 75px;">
			</c:if>
			<div class="fuelux controls">
				<label class="control-label" for="textinput">${fieldType.label()}
					<c:if
						test="${fieldType.validation()!=null && fieldType.validation().required()}">
						<sup style="color: red"> *</sup>
					</c:if> <img alt="" src='<c:url value="images/browse.jpg" />'>
				</label>

				<c:choose>
					<c:when
						test="${fieldType.validation()!=null && fieldType.validation().required()}">
						<!-- here two placeholder present so i removed it -->

						<input id="${_searchContext.entityName()}${fieldType.name()}"
							placeholder="${fieldType.label()}" ${fieldType.htmlAttrib()}
							required type="text">
					</c:when>
					<c:otherwise>
						<!-- here two placeholder present so i removed it -->
						<input id="${_searchContext.entityName()}${fieldType.name()}"
							placeholder="${fieldType.label()}" ${fieldType.htmlAttrib()}
							type="text">
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${fieldType.multiselect()}">
						<input
							id='${_searchContext.entityName()}${fieldType.name()}${"_hidden"}'
							placeholder="${fieldType.label()}"
							name='${fieldType.name()}${"_ids"}' class="wizardhidden" type="hidden">
					</c:when>
					<c:otherwise>
						<input
							id='${_searchContext.entityName()}${fieldType.name()}${"_hidden"}'
							placeholder="${fieldType.label()}"
							name='${fieldType.name()}${"_id"}' class="wizardhidden" type="hidden">
					</c:otherwise>
				</c:choose>
			</div>
</div>
<script>
											$('#${_searchContext.entityName()}${fieldType.name()}').select2({
												placeholder: "Search",
												minimumInputLength: 2,
												multiple:${fieldType.multiselect()},
											    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
											        url: "${pageContext.request.contextPath}${_searchContext.autoCompleteUrls().get(fieldType.label())}",
											        dataType: 'json',
											        data: function (term, page) {
											            return {
											                query: term, // search term
											                param: function(){
											                	
											                	if(typeof(${_searchContext.entityName()}${fieldType.name()}_customParameterBuilder) == "function"){
											                		return ${_searchContext.entityName()}${fieldType.name()}_customParameterBuilder();
											                	}else{
											                		return "";
											                	}
											                },
											                page_limit: 10
											            };
											        },
											        results: function (data, page) { // parse the results into the format expected by Select2.
											            // since we are using custom formatting functions we do not need to alter remote JSON data
											            return {results: data.results};
											        }
											    },
											    formatResult: function(exercise) { 
											        return "<div class='select2-user-result'>" + exercise._1 + "</div>"; 
											    },
											    formatSelection: function(result) { 
											    	return result._1; 
											    }
											}).on('change',function(ev){
												$('#'+'${_searchContext.entityName()}${fieldType.name()}${"_hidden"}').val(ev.val);
											});
											
											</script>
</c:otherwise>
<%-- B close --%>
</c:choose>
<%-- C close --%>
</c:when>
<%-- E Close --%>
<c:otherwise>
	<input id="${_searchContext.entityName()}${fieldType.name()}"
		name='${fieldType.name()}${"_hidden"}' class="wizardhidden" type="hidden">
</c:otherwise>
</c:choose>
<%-- F Close --%>
</c:when>
<%-- G close  --%>

<c:when test='${fieldType.ctype().name() == "FLEXI"}'>
	
	<fieldset ng-controller="flexiAttributeController" 
		 >
    <legend>Flexi Attributes</legend>
    
    <c:forEach var="flexiType" items="${fieldType.value().getO()}" varStatus="index">
    		<c:if test="${index.index %2 != 1}">
				<div class="control-group"
					style="float: left; width: 46%; margin: 0px; height: 75px;">
			</c:if>
			<c:if test="${index.index %2 == 1}">
				<div class="control-group" style="height: 75px;">
			</c:if>
    		
    			<label class="control-label" for="textinput">${flexiType.getName()}
				</label>
				<c:if test="${flexiType.getType() == 'FILE'}">
					<input type="file" placeholder="value" name="flexiAttributes[${index.index}].value" 
    				class="input-large"  >
    			
    			</c:if>
    			
    			<c:if test="${flexiType.getType() != 'FILE'}">
    				<input placeholder="value" name="flexiAttributes[${index.index}].value" 
    				class="input-large"  >
    			</c:if>
    			
    			<input  type="hidden" id="flexiAttributes" placeholder="value" 
    				name="flexiAttributes[${index.index}].flexiId" 
    			class="input-large"  value='${flexiType.getId()}' >
    	</div>
    </c:forEach> 
    	
    </fieldset>
	
	
</c:when>

<c:when test='${fieldType.ctype().name()== "SELECT_OPTION"}'>

	<c:if test="${fieldType.order() %2 ==1}">
		<div class="control-group"
			style="float: left; width: 46%; margin: 0px; height: 75px;">
	</c:if>
	<c:if test='${fieldType.order() %2 !=1}'>

		<div class="control-group" style="height: 75px;">
	</c:if>

	<label class="control-label" for="selectbasic">${fieldType.label()}</label>

	<div class="controls">

		<select id="${_searchContext.entityName()}${fieldType.name()}"
			name="${fieldType.name()}" class="input-large">

			<c:forEach var="option" items='${fieldType.options()}'>
				<c:if test="${option.uiHidden()==false}">
					<option value='${option}'>${option.getName()}</option>
				</c:if>
			</c:forEach>
		</select>
	</div>
	</div>
</c:when>

<c:when test='${fieldType.ctype().name()=="SEARCH"}'>
	<div class="control-group">
		<label class="control-label" for="selectbasic">${fieldType.label()}</label>
		<div class="controls">
			<%-- TODO --%>
			searchContext('${_searchContext.getSearchContexts().get(fieldType.name())}','${_searchContext.entityName()}',"add"
		</div>
		<input class="OneToMany" entity='${_searchContext.entityName()}'
			id='${_searchContext.entityName()}${fieldType.name()}${"_hidden"}'
			name='${fieldType.name()}${"_ids"}' class="wizardhidden" type="hidden">
	</div>
</c:when>

<c:when test='${fieldType.ctype().name()=="DATE"}'>
	<c:if test='${fieldType.order() %2==1}'>
		<div class="control-group"
			style="float: left; width: 46%; margin: 0px; height: 75px;">
	</c:if>
	<c:if test='${fieldType.order() %2!=1}'>
		<div class="control-group" style="height: 75px;">
	</c:if>
	<label class="control-label" for="selectbasic">${fieldType.label()}
		<c:if
			test='${fieldType.validation() !=null && fieldType.validation().required()}'>
			<sup style="color: red"> *</sup>
		</c:if>
	</label>
	<div id="${fieldType.name()}" data-provide="datepicker" data-date=""
		class="input-prepend date datepicker" data-date-format="dd-mm-yyyy">
		<span class="add-on"><i class="icon-calendar"></i></span> <input
			class="add-on" size="16" type="text" value=""
			placeholder="DD-MM-YYYY" name="${fieldType.name()}">
	</div>
	</div>
</c:when>

</c:choose>
<%-- H Close--%>
</c:forEach>
<%-- I Close--%>
</div>
</c:forEach>
<%-- J Close--%>

<div class="wizard-success">${_searchContext.entityName()}${" Created Successfully"}
</div>

<div class="wizard-error">submission had an error</div>

<div class="wizard-failure">submission failed</div>

</div>

<script>
var ${"form_"}${_searchContext.entityName()}${"_add_wizard_validate"};
function ${"form_"}${_searchContext.entityName()}${"_add_wizard"}(){
	return $('#${"form_"}${_searchContext.entityName()}${"_add-wizard"}').valid();
}

function ${"form_"}${_searchContext.entityName()}${"_add_wizard_reset"}(){
	
	${"form_"}${_searchContext.entityName()}${"_add_wizard_validate"}.resetForm();
	$(".error").removeClass("error");
	document.getElementById('${"form_"}${_searchContext.entityName()}${"_add-wizard"}').reset();
}
$(document).ready(function(){
	

 ${"form_"}${_searchContext.entityName()}${"_add_wizard_validate"} = $('#${"form_"}${_searchContext.entityName()}${"_add-wizard"}').validate({
	 
	//errorElement:"", 
	rules: {
			 <c:forEach var="wizard" items='${_searchContext.getWizards()}'>
	    		 <c:forEach var="fieldType" items='${wizard.card().iterator()}'>
	    		 	 <c:if test='${fieldType.validation()!=null}'>
	    			 		 ${fieldType.name()} :{
			    				 minlength: ${fieldType.validation().minlength()},
			    				 required : ${fieldType.validation().required()},
			    				 email: ${fieldType.validation().email()},
			    				 maxlength: ${fieldType.validation().maxlength()},
			    				 date: ${fieldType.validation().date()},
			    				 digits: ${fieldType.validation().digits()},
								 <c:if test='${!"".equals(fieldType.validation().remote())}'>
									 remote: {
										 url: '${pageContext.request.contextPath}/${fieldType.validation().remote()}',
										 type: "post",
										 data:{
											 q: function(){
												return $('#${_searchContext.entityName()}${fieldType.name()}').val(); 
											 }
												 
										 }
									 },
								 </c:if>
		    			 		 number: ${fieldType.validation().number()}
							 
			    			 },
	    		 	</c:if>
    	    	 </c:forEach>	 
	    	 </c:forEach>	 
    	 },
    	 messages: {
    		<c:forEach var="wizard" items='${_searchContext.getWizards()}'>
    			<c:forEach var="fieldType" items='${wizard.card().iterator()}'>
    				<c:if test='${fieldType.validation() !=null}'>
    				 	<c:if test='${!fieldType.validation().messages().equals("")}'>
    				 			 ${fieldType.name()} : '${fieldType.validation().messages()}',
	    				</c:if>
	    			</c:if> 
    		  	</c:forEach>
    		</c:forEach>
    	 },
    			 
        
    
    highlight: function (element) {
        $(element).closest('.control-group').removeClass('success').addClass('error');
    },
    success: function(element) {
        element
        .addClass('valid')
        .closest('.control-group').removeClass('error').addClass('success');
      }
});

})
</script>

