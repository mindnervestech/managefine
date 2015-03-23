<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<c:choose>
	<c:when test="${fieldType.ctype().name()=='INPUT'}">
		<c:choose>
			<c:when test="${!fieldType.hidden()}">
				<c:choose>
					<c:when
						test="${!fieldType.autocomplete() && !fieldType.multiselect()}">
						<div class="control-group">
							<label class="control-label" for="selectbasic">${fieldType.label()}
								<c:if
									test="${fieldType.validation() !=null && fieldType.validation().required()}">
									<sup style="color: red"> *</sup>
								</c:if>
							</label>
							<div class="controls">
								<c:choose>
									<c:when
										test="${fieldType.validation() !=null && fieldType.validation().required()}">
										<input id="${_namespace}${fieldType.name()}"
											name="${fieldType.name()}" value='${fieldType.value().o}'
											${fieldType.htmlAttrib()} class='${_fieldClass}' required
											type="text" rel="popover">
									</c:when>
									<c:otherwise>
										<input id="${_namespace}${fieldType.name()}"
											name="${fieldType.name()}" value='${fieldType.value().o}'
											${fieldType.htmlAttrib()} class='${_fieldClass}' type="text">
									</c:otherwise>
								</c:choose>
							</div>
					</c:when>

					<c:otherwise>
						<div class="control-group">
							<label class="control-label" for="selectbasic">${fieldType.label()}
								<c:if
									test="${fieldType.validation() !=null && fieldType.validation().required()}">
									<sup style="color: red"> *</sup>
								</c:if>
							</label>
							<div class="controls">
								<c:choose>
									<c:when
										test='${fieldType.validation()!=null && fieldType.validation().required()}'>
										<input id="${_namespace}${fieldType.name()}"
											name="${fieldType.name()}" value='${fieldType.value().o}'
											${fieldType.htmlAttrib()} class='${_fieldClass}' required
											type="text" rel="popover">
									</c:when>
									<c:otherwise>
										<input id="${_namespace}${fieldType.name()}"
											name="${fieldType.name()}" value='${fieldType.value().o}'
											${fieldType.htmlAttrib()} class='${_fieldClass}' type="text">
									</c:otherwise>
									
								</c:choose>
								
								<input id='${_namespace}${fieldType.name()}${"_hidden"}'
								
									value='${fieldType.value().id}'
									name='${fieldType.name()}${"_id"}' type="hidden">
							</div>

							<script>
									$('#${_namespace}${fieldType.name()}').select2({
												placeholder: "Search",
												minimumInputLength: 2,
												multiple:${fieldType.multiselect()},
											    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
											        url: "${pageContext.request.contextPath}/findDelegatedTo",
											        dataType: 'json',
											        data: function (term, page) {
											            return {
											                query: term, // search term
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
											    },
											    initSelection: function(element, callback) {
											    	var _id=$(element).val();
											    	var data = [];
											    	<c:choose>
											    	<c:when test="${fieldType.multiselect()}">
											    		<c:if test="${fieldType.value().li()!=null}">
											    			<c:forEach var="mo" items='${fieldType.value().li()}'>
											    				data.push({id: '${mo.id}', _1:'${mo.o}' });	
											    			</c:forEach>
											    		</c:if>
											    	</c:when>
											    	<c:otherwise>
											    		 data = {_1:_id,id:$('#${_namespace}${fieldType.name()}').val()};
											    	 </c:otherwise>
											    	</c:choose>
											    	callback(data);
											    }
											}).on('change',function(ev){
												$('#'+'${_namespace}${fieldType.name()}').val(ev.val);
											});
											
											</script>
											</div>
					</c:otherwise>
					
				</c:choose>
			</c:when>
			<c:otherwise>
				<input id="${_namespace}${fieldType.name()}"
					value='${fieldType.value().o}'
					name='${fieldType.name()${"_hidden"}' type="hidden">
			</c:otherwise>
		</c:choose>

	</c:when>





	<c:when test="${fieldType.ctype().name()=='CHECKBOX'}">


		<div class="control-group">
			<c:when test="${!fieldType.hidden()}">
				<label class="control-label" for="selectbasic">${fieldType.label()}
					<c:if
						test="${fieldType.validation() !=null && fieldType.validation().required()}">
						<sup style="color: red"> *</sup>
					</c:if>
				</label>
				<div class="controls">

					<c:choose>

					 <c:when
							test='${fieldType.validation() !=null && fieldType.validation().required}'>
							 <c:choose>
								<c:when test='${fieldType.value.o == true}'>
									<input id="${_namespace}${fieldType.name()}"
										name="${fieldType.name()}" value='${fieldType.value().o}'
										${fieldType.htmlAttrib()} class='${_fieldClass}' required
										type="checkbox" checked="checked" rel="popover">
								</c:when>

								<c:otherwise>
									<input id="${_namespace}${fieldType.name()}"
										name="${fieldType.name()}" value='${fieldType.value().o}'
										${fieldType.htmlAttrib()} class='${_fieldClass}' required
										type="checkbox" rel="popover">
								</c:otherwise>
							</c:choose>
							
						</c:when> 

						<c:otherwise>
							<c:choose>

								<c:when test="${fieldType.value.o == true}">
									<input id="${_namespace}${fieldType.name()}"
										name="${fieldType.name()}" value='${fieldType.value().o}'
										${fieldType.htmlAttrib()} class='${_fieldClass}'
										type="checkbox" checked="checked">
								</c:when>

								<c:otherwise>
									<input id="${_namespace}${fieldType.name()}"
										name="${fieldType.name()}" value='${fieldType.value().o}'
										${fieldType.htmlAttrib()} class='${_fieldClass}'
										type="checkbox">
								</c:otherwise>
							</c:choose>
						</c:otherwise>
 
					</c:choose>
				</div>
			</c:when>

			<c:otherwise>
				<input id="${_namespace}${fieldType.name()}"
					name='${fieldType.name()}${"_hidden"}' type="hidden">
			</c:otherwise>

		</div>
	</c:when>




	<c:when test="${fieldType.ctype().name()=='SEARCH'}">

		<div class="control-group">
			<label class="control-label" for="selectbasic">${fieldType.label()}
				<c:if
					test="${fieldType.validation() !=null && fieldType.validation().required()}">
					<sup style="color: red"> *</sup>
				</c:if> <img id='${_namespace}${fieldType.name()${"_browse"}' alt=""
				src='<c:url value="images/browse.jpg"/>'>
			</label>
			<div class="controls">
				<c:choose>
					<c:when
						test="${fieldType.validation() !=null && fieldType.validation().required()}">
						<input id="${_namespace}${fieldType.name()}"
							name="${fieldType.name()}" value='${fieldType.value().o}'
							${fieldType.htmlAttrib()} readonly class='${_fieldClass}'
							required type="text" rel="popover">
					</c:when>
					<c:otherwise>
						<input id="${_namespace}${fieldType.name()}"
							name="${fieldType.name()}" value='${fieldType.value().o()'
							${fieldType.htmlAttrib()} readonly class='${_fieldClass}'
							type="text">
					</c:otherwise>
				</c:choose>
				<input type="hidden"
					id='${_namespace}${fieldType.name()}${"_hidden"}'
					name='${fieldType.name()}${"_id"}' value='${fieldType.value().o}'>
			</div>
		</div>

		      			<script>
			     			$('#${_namespace}${fieldType.name()}${"_browse"}').click(function(){
			     				var _url = '${(fieldType.value().o.asInstanceOf[com.mnt.core.helper.SearchUI]).triggerUrl()}';
			     				_url = _url + "?id=" + "${_namespace}${fieldType.name()}" + "_hidden&name=" + "${_namespace}${fieldType.name()}";
			     				win = window.open(_url,'Search', 'width=940, height=600');
			     				
			     			});
						</script>
	</c:when>



	<c:when test="${fieldType.ctype().name()=='SELECT_OPTION'}">

		<div class="control-group">
			<label class="control-label" for="selectbasic">${fieldType.label()}</label>
			<div class="controls">
				<select id="${_namespace}${fieldType.name()}"
					name="${fieldType.name()}" class='${_fieldClass}'>
					<c:forEach var="option" items="${fieldType.options()}">
						<option value='${option}'>${option.getName()}</option>
					</c:forEach>
				</select>
			</div>
		</div>

	</c:when>


	<c:when test="${fieldType.ctype().name()=='DATE'}">

		<div class="control-group">
			<label style="margin-left: 3%;" class="control-label" for="selectbasic">${fieldType.label()}
				<c:if
					test="${fieldType.validation() !=null && fieldType.validation().required()}">
					<sup style="color: red"> *</sup>
				</c:if>
			</label>

			<div id="${fieldType.name()}" data-provide="datepicker"
				class='input-append date datepicker' data-date=""
				data-date-format="dd-mm-yyyy">
				<span style="margin-left: 5%;" class="add-on"><i class="icon-calendar"></i></span>
				<c:out value="${fieldType.value().dt}">${fieldType.value().getDt()}</c:out>
				<c:choose>
					<c:when test="${fieldType.value().dt != null}">
						<fmt:formatDate var="fmtDate" pattern="dd-MM-YYYY"  value="${fieldType.value().dt}" />
						<input style="width: 117%;" size="16" type="text" value='${fmtDate}' readonly name="${fieldType.name()}" class='${_fieldClass}'>
					</c:when>
					<c:otherwise>
						<input style="width: 117%;" size="16" type="text" value='' name='${fieldType.name()}'
							readonly class="add-on">
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:when>
</c:choose>
 