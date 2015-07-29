<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div style="width: 94%;margin-left:1%; float: left;"id='${_searchContext.entityName()}${mode}${"_search"}' class="well form-inline" >  
		
		<c:if test="${_searchContext.getFilter() != null}">		
		 <c:forEach var="fieldType" items="${_searchContext.getFilter().iterator()}">
			<c:choose>
				 <c:when test='${fieldType.ctype().name() == "INPUT"}'>
				    <input id="${_searchContext.entityName()}${mode}${fieldType.name()}" name="${fieldType.name()}" placeholder="${fieldType.label()}" class="input-medium search-query" type="text">
				 </c:when>
				 <c:when test='${fieldType.ctype().name() == "SELECT_OPTION"}'>
				 	<label style="margin-left: 1%;"class="control-label" for="selectbasic">${fieldType.label()}</label>
				    <select style="width: 20%;"id="${_searchContext.entityName()}${mode}${fieldType.name()}" name="${fieldType.name()}" class="input-xlarge search-on-select">
					    
					    <c:forEach var="option" items="${fieldType.options()}">
					    	<option value='${option}'>${option.getName()}</option>
					    </c:forEach>	
					</select>
				 </c:when>
				 <c:when test='${fieldType.ctype().name() == "DATE"}'>
				 	<label style="margin-left: 2%;"class="control-label" for="selectbasic">${fieldType.label()}</label>
					<div data-provide="datepicker" class="input-append date datepicker" data-date="" data-date-format="dd-mm-yyyy">
	    				<input style="width: 80%;  height: 20px;"id='${_searchContext.entityName()}${mode}${fieldType.name()}' size="16" type="text" value="" readonly name='${fieldType.name()}'><span class="add-on"><i class="icon-calendar"></i></span>
					</div>
				 </c:when>
			</c:choose>
		 </c:forEach>
			<label class="control-label" for="singlebutton">&nbsp;</label>
			<button style="margin-left: 5%; color: gray;"id='${_searchContext.entityName()}${mode}${"_searchButton"}' onclick="return false;" name="searchButton" class="btn">Search</button>
		</c:if>		
				
</div>


<fieldset>

		<div class="row-fluid" style="margin-left: 1%">
			<div class="span12">
				<div class="row-fluid">
					<div class="control-group">
					  <table id='${_searchContext.entityName()}${mode}${"_jqGrid_list"}'><tr><td/></tr></table> 
						<div id='${_searchContext.entityName()}${mode}${"_jqGrid_pager"}'></div> 

					</div>
					<div class="control-group">
					  <label class="control-label" for="button1id">&nbsp;</label>
					  <div class="controls">
						<c:if test='${_parentSearchCtx == null && _searchContext.showAddButton().visibility()}'>
					  	 	<button type="button" id='${_searchContext.entityName()}${mode}${"_addButton"}' data-toggle="modal" class="btn btn-warning" data-target='#${_searchContext.entityName()}${"_addModal"}'>Create New</button>
					  	</c:if>
					  <c:if test='${_parentSearchCtx == null && _searchContext.showEditButton().visibility()}'>
					  		
					  		<c:choose>
					  		 	<c:when test='${_searchContext.showEditButton().target().name()=="MODAL"}'>
					  		 		<button type="button" id='${_searchContext.entityName()}${mode}${"_editButton"}' data-toggle="modal" class="btn btn-warning" data-target='#${_searchContext.entityName()}${"_editModal"}'>${_searchContext.showEditButton().label()}</button>
					  		 	</c:when>
					  			<c:when test='${_searchContext.showEditButton().target().name()=="NEW"}'>
					  				<button type="button" id='${_searchContext.entityName()}${mode}${"_editButton"}' class="btn btn-warning" >${_searchContext.showEditButton().label()}</button>
					  			</c:when>
					  		</c:choose>
					  	</c:if>
					  	<c:if test='${_parentSearchCtx != null}'>
					  		<button type="button" id='${_searchContext.entityName()}${mode}${"_addToButton"}'  class="btn" >Add To Board...</button>
					  	</c:if>
					  	
					  	<c:forEach var="button" items="${_searchContext.getButtonActions()}">
					 
						  	<c:if test='${_parentSearchCtx == null && button.visibility()}'>
						  		
						  		<c:choose>
							  		<c:when test='${button.target().name()=="MODAL"}'>
							  			<button type="button" id='${_searchContext.entityName()}${mode}${button.id()}' class="btn btn-warning" >${button.label()}</button>
							  		</c:when>
							  		<c:when test='${button.target().name()=="NEW"}'>
							  			<button type="button" id='${_searchContext.entityName()}${mode}${button.id()}' class="btn btn-warning" >${button.label()}</button>
							  		</c:when>
							  		<c:when test='${button.target().name()=="ACTION"}'>
							  			<button type="button" id='${_searchContext.entityName()}${mode}${button.id()}' class="btn btn-warning" >${button.label()}</button>
							  		</c:when>
						  		</c:choose>
						  	</c:if>
					  	</c:forEach>
					 
					  </div>
					</div>
					
				</div>
			</div>
		</div>
	
</fieldset>

<c:if test='${ _parentSearchCtx == null && mode == "add" && _searchContext.showAddButton().visibility()}'>
 <jsp:include page="wizard.jsp"/>
</c:if> 

 
<div id="wizard-core-container"></div>
<div id="custom-modal-container" class="modal hide fade">
	
</div>
 
 <style>
 option:hover{
	background-color: gray;
}

.btn-default{
	color: black !important;
}
</style>
 	<script type="text/javascript">
 	
 	var  ${_searchContext.entityName()}${mode}${"_SearchGrid"} = {
 			
 	
			_searchURL: "${pageContext.request.contextPath}${_searchContext.searchUrl()}",
			initialise: function (){
				$('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid({
			   	url:'${pageContext.request.contextPath}${_searchContext.searchUrl()}',
			   	height:231,
			   	width: $('#${_searchContext.entityName()}${mode}${"_search"}').width() + 40,
			   	datatype: 'json',
			   	mtype: 'GET',
			   	colNames:[
			   	          
			             <c:forEach var="col" items="${_searchContext.getColumn()}" varStatus="loopIndex">
			             	 <c:choose>
			             	 	<c:when test="${loopIndex.index == 0}">
			             	 	'${col.colName()}'
			             	 	</c:when>
			             	 
				             	 <c:otherwise>
				             	,'${col.colName()}'
				             	 </c:otherwise>
			             	</c:choose>
			             </c:forEach> 
			              <c:if test='${_searchContext.getGridActions() !=null && _searchContext.getGridActions().size()!=0}'>
			            	 ,'Action'
			              </c:if>
			             ],
			   	colModel :[
						
							<c:forEach var="col" items="${_searchContext.getColumn()}" varStatus="loopIndex">
							<c:choose>
			             	 	<c:when test="${loopIndex.index == 0}">
			             	 	{name:'${col.name()}', index:'${loopIndex.index}', width:'${col.width()}' ,align:'center',sortable: false}
			             	 	</c:when>
				             	 <c:otherwise>
			    	         	,{name:'${col.name()}', index:'${loopIndex.index}', width:'${col.width()}',align:'center',sortable: false}
			        	     	 </c:otherwise>
			             	</c:choose> 	
							
						</c:forEach>
						
						<c:if test="${_searchContext.getGridActions() !=null && _searchContext.getGridActions().size()!=0}">
							,{name:'action', index:'action', width:40,align:'center',formatter:this.actionFormatter, search:false, title:false}
						</c:if>
			   	],
			   	pagination : true,
			   	pager: '#${_searchContext.entityName()}${mode}${"_jqGrid_pager"}',
			   	rowNum:20,
			   	rowList:[10,20,30],
			   	viewrecords: true,
			   	gridview: true,
			   	multiselect: ${_searchContext.isMultiSelectSearch()},
			   	caption:  '${_searchContext.entityDisplayName()}',
			   
			   	onSelectRow: function(id){
			   	
			   	},
			   	
			   	ondblClickRow: function(rowid) {
			   		
			   		<c:if test='${_searchContext.showEditButton().visibility()}'>
			   			${_searchContext.entityName()}${mode}${"_SearchGrid"}.doEdit();
			   		</c:if>
			   		
			   		<c:if test='${ _searchContext.onDoubleRowClickCallID() != null}'>
			   			$('#${_searchContext.entityName()}${mode}${_searchContext.onDoubleRowClickCallID()}').click();
			   		</c:if>
			   		
			   	},
			   	loadComplete:function (data){
			   		$('a[id^=${_searchContext.entityName()}${mode}${"_gridAction"}]').each(function() {
			   			if(typeof(${_searchContext.entityName()}${mode}_gridAction_isToBeHidden) == "function"){
			   				${_searchContext.entityName()}${mode}_gridAction_isToBeHidden($(this).attr('type')) ? $(this).hide(): $(this).show();
			   			}
			   			
			   			_this = this;
				   		$(this).click(function(e){
				   			console.log("hihihih");
				   			var id = $(this).attr('cell');
				   			var _url = $(this).attr('url');
				   			var _action = $(this).attr('action');
				   			var s = id;
				   			if(typeof(${_searchContext.entityName()}_confimationDialog)=='function'){
				   				if(${_searchContext.entityName()}_confimationDialog(_url)){
				   					bootbox.confirm("Do You Want to Continue?", function(result) {
				   						 if(result == true){
								          ${_searchContext.entityName()}${mode}${"_SearchGrid"}.ajaxCaller(_url,s);
								        }else{
								         	    return true;
								        } 
								 	});
								
								}else{
									
									if(_action == 'EDIT_TYPE'){
										${_searchContext.entityName()}${mode}${"_SearchGrid"}.doEdit(s);					
									}else{
										${_searchContext.entityName()}${mode}${"_SearchGrid"}.ajaxCaller(_url,s);
									}
								}
							
							}		
				   		});
				   	 });
			   		${_searchContext.entityName()}${mode}${"_SearchGrid"}.resizeGrid();
			   	}	   
			});
			jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid('bindKeys');
			jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid('navGrid','#${_searchContext.entityName()}${mode}${"_jqGrid_pager"}',{del:false,add:false,edit:false,search:false,refresh:false});
			jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid('navButtonAdd','#${_searchContext.entityName()}${mode}${"_jqGrid_pager"}',
					{
				onClickButton:function(){
					${_searchContext.entityName()}${"_squrl"}='';
					<c:if test="${_searchContext.getFilter() != null}">
						<c:forEach var="fieldType" items="${_searchContext.getFilter()}">
							${_searchContext.entityName()}${"_squrl"}  = ${_searchContext.entityName()}${"_squrl"} + '${fieldType.name()}' + '=' + $('#${_searchContext.entityName()}${mode}${fieldType.name()}').val() + '&'
						</c:forEach>
					</c:if>
					${_searchContext.entityName()}${"_squrl"}=${_searchContext.entityName()}${"_squrl"} + '1=1';
					window.open('${pageContext.request.contextPath}${_searchContext.generateExcel()}?'+${_searchContext.entityName()}${"_squrl"},'','');
				},
				caption:"",
				position: "last", 
				title:"Excel", 
				cursor: "pointer"});
			
			jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').on('reloadGrid',function (){
				//TODO
			});	
			this.bindButtons();
						
		},
		actionFormatter:function(cellvalue, options, rowObject){
			
			var show ="";
			
			_actionSize  = ${_searchContext.getGridActions().size()};
			<c:forEach var="gridAction" items='${_searchContext.getGridActions().iterator()}'>
				_image ='<c:url value="/resources/images/${gridAction.icon.name()}.png"/>';
				
				show = show + "<a id='${_searchContext.entityName()}${mode}_gridAction' action='${gridAction.target()}' class='tooltipShow' title='${gridAction.tooltip()}' href='#' type='${gridAction.icon.name()}' url='${pageContext.request.contextPath}${gridAction.url()}' cell="+cellvalue+" ><img style='max-width:22px; margin-left:5px;'src='"+_image+"'></a>"
			</c:forEach>
			return show;
		},
		
		doSearch: function(){
			$('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').setGridWidth($('#${_searchContext.entityName()}${mode}${"_search"}').width()+40);
			${_searchContext.entityName()}${"_squrl"}='';
			
				<c:if test="${_searchContext.getFilter() != null}">	
				<c:forEach var="fieldType" items="${_searchContext.getFilter()}">
					${_searchContext.entityName()}${"_squrl"} = ${_searchContext.entityName()}${"_squrl"} + '${fieldType.name()}' + '=' + $('#${_searchContext.entityName()}${mode}${fieldType.name()}').val() +'&'
				</c:forEach>
				</c:if>
			
			${_searchContext.entityName()}${"_squrl"} = ${_searchContext.entityName()}${"_squrl"} + '1=1';
			
			jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').setGridParam({
				mtype:'GET',
				url:'${pageContext.request.contextPath}${_searchContext.searchUrl()}' + "?" + ${_searchContext.entityName()}${"_squrl"}
			}).trigger('reloadGrid');
			
			return false;
			
		},
		doCustomAction:function(_url){
			s = this.selectedItem();
			_this = this;
		
			if(s.length == 0 ){
				bootbox.confirm("Please Mark Selection From Grid", function(result) {
					if(result == true){
						return;
			        }else{
			        	return;
			        }
				});
				return;					
					
			}
			if(typeof(${_searchContext.entityName()}_confimationDialog)=='function'){
				if(${_searchContext.entityName()}_confimationDialog(_url)){
					bootbox.confirm("Do You Want to Continue?", function(result) {
  						 if(result == true){
  							_this.ajaxCaller(_url,s);
				        }else{
				         	    return true;
				        } 
				 });
				
				}else{
					this.ajaxCaller(_url,s);
					
				}
			
			}else{
				this.ajaxCaller(_url,s);
			}
			
		},
		ajaxCaller: function(_url,s){
			$.ajax({
				url:_url,
				data: {query: s.toString()},
				success: function(data) {
					_dataType = JSON.parse(data);
					$.pnotify({
						history:false,
				        text: _dataType.message
				    });
					if(_dataType.count !=0)
						$(".noti_bubble").html(_dataType.count);
					else
						$(".noti_bubble").remove();
					${_searchContext.entityName()}${"_this"}.doSearch();	
				},
				error: function(data) {
					$.pnotify({
						history:false,
				        text: "We are unable to process your Request Please contact Admin "
				    });		
				}
			})
		},
		selectedItem: function(){
			var s=[];
			<c:choose>
			 <c:when test='${_searchContext.isMultiSelectSearch()}'>
				s = jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid('getGridParam','selarrrow');		
			 </c:when>
			
			 <c:otherwise>
				_s = jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid('getGridParam','selrow');
				if(_s!=null)
					s[0] = _s;
			 </c:otherwise>
			</c:choose>
			return s;
		},
		doEdit:function(_s){
			s = [];
			if (typeof(_s) != "undefined"){
				s[0] = _s;	
			}else{
				s = this.selectedItem();
			}
			if('${_searchContext.showEditButton().target()}' == 'NEW'){
				if(s.length != 0 ){
					var rowData = jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').getRowData(s[0]);
					window.location.href = "${pageContext.request.contextPath}${_searchContext.showEditButton().url()}"+"/"+s;
				}	
				
			}else{
				if(s.length == 0 ){
					bootbox.confirm("Please Mark Selection From Grid", function(result) {
						if(result == true){
							return;
				        }else{
				        	return;
				        }
					});
					return;
				}
			
			
				if(typeof(${_searchContext.entityName()}${"EditButtonFn"})=='function') {
				
					${_searchContext.entityName()}${"EditButtonFn"}(s);
					return;
				} else {
				  $.ajax({
					url:'${pageContext.request.contextPath}${_searchContext.showEditUrl()}',
					data: {query: s[0]},
					success: function(data) {
								
						if(jQuery('#${_searchContext.entityName()}${"_edit-wizard"}')){
							jQuery('#${_searchContext.entityName()}${"_edit-wizard"}').remove();
						}
						
			        	$('#wizard-core-container').html(data);
			        	var options = {
			        			width:window.innerWidth * 0.8,
			        			increaseHeight:100,
			        			leftPanelWidthPercent:20,
			        			submitUrl:'${pageContext.request.contextPath}${_searchContext.editUrl()}',
			        			id:'${_searchContext.entityName()}${"_edit-wizard"}',
			        			onCardReady: function(_currentCard){
			        					
			        					if(typeof(${_searchContext.entityName()}${"edit"}_onCardReady)=='function'){
			        						${_searchContext.entityName()}${"edit"}_onCardReady(_currentCard);
			        					}
			        					
			        				},
			        				onSuccess: function(){
			        					${_searchContext.entityName()}${mode}${"_SearchGrid"}.doSearch();
			        				}
			        			};
			        	var  ${_searchContext.entityName()}${"_wizard"} = $('#${_searchContext.entityName()}${"_edit-wizard"}').wizard(options);
			        	$('.wizard-title').html('Edit ${_searchContext.entityName()}');
			        	${_searchContext.entityName()}${"_wizard"}.show();
			        	$('#wizard-core-container').empty();
					},
					error : function(response) {
						if(response.responseText != "")
						{	
							$.pnotify({
								history:false,
						        text: response.responseText
						    });
						}
					}
				}); // end of ajax
			   } // end of else
			}
			
		},
		doCreate: function(){
			var _qurl='';
			
			<c:if test="${_searchContext.getFilter() != null}">	
			<c:forEach var="fieldType" items="${_searchContext.getFilter()}">
				_qurl = _qurl + '${fieldType.name()}' +'='+ $('#${fieldType.name()}').val() +'&'
			</c:forEach>
			</c:if>
			_qurl = _qurl + '1=1';
			jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').setGridParam({
				mtype:'GET',
				url:this._searchURL +"?"+_qurl
			}).trigger("reloadGrid");
			
		},
		resizeGrid:function(){
			$('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').setGridWidth($('#${_searchContext.entityName()}${mode}${"_search"}').width()+40);
		},
		addToBucket: function(){
			s = this.selectedItem();
			//$('#@(_searchContext.entityName + "_Pillbox ul")').empty();
			jQuery.each(s,function(i,e){
				val_1 = jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid ('getCell', e, 1);
				val_2 = jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid ('getCell', e, 2);
				val_3 = jQuery('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').jqGrid ('getCell', e, 3);
				$('#${_searchContext.entityName()}${mode}${"_Pillbox ul"}').append("<li data-id='"+e+"' class='status-info'>"+val_1+"-"+val_2+"("+val_3+")</li>");
			});
			$('.OneToMany').each(function(){
				if($(this).attr('entity') == '${_parentSearchCtx}'){
					_this = this;
					ids = [];
					jQuery.each($('#${_searchContext.entityName()}${mode}${"_Pillbox ul"}').pillbox('items'),function(i,e){
						ids.push(e.id);	
					});
					$(_this).val(ids)
					
					
				};
			});
			
		},
		
		
		
		bindButtons: function(){
			${_searchContext.entityName()}${"_this"} = ${_searchContext.entityName()}${mode}${"_SearchGrid"};
			$('#${_searchContext.entityName()}${mode}${"_searchButton"}').click(function(){
				${_searchContext.entityName()}${"_this"}.doSearch();
			});
			$('#${_searchContext.entityName()}${mode}${"_addToButton"}').click(function(){
				${_searchContext.entityName()}${"_this"}.addToBucket();
			});
			$('#${_searchContext.entityName()}${mode}${"_addButton"}').click(function(){
				$('.wizard-title').html('Create ${_searchContext.entityName()}');
				$('#${_searchContext.entityName()}${"_add-wizard"}').find('input[type=text], textarea').val('');
				$('#${_searchContext.entityName()}${"_add-wizard"}').find('.wizardhidden').val('');
				${"form_"}${_searchContext.entityName()}${"_add_wizard_reset"}();
				
				${_searchContext.entityName()}${"_wizard"}.show();
				${_searchContext.entityName()}${"_wizard"}.reset();
			    
			});
			$('#${_searchContext.entityName()}${mode}${"_editButton"}').click(function(){
				${_searchContext.entityName()}${"_this"}.doEdit();
				
			});
			
			<c:forEach var="button" items="${_searchContext.getButtonActions()}">
			<c:if test="${_parentSearchCtx == null && button.visibility()}">
			
					<c:choose>
					
		  		 	<c:when test='${button.target().name()=="ACTION"}'>
		  		 	$('#${_searchContext.entityName()}${mode}${button.id()}').click(function() {
						${_searchContext.entityName()}${"_this"}.doCustomAction("${pageContext.request.contextPath}${button.url()}");
						
		  		 	});
					</c:when>
					<c:when test='${button.target().name()=="MODAL"}'>
					
			  		 $('#${_searchContext.entityName()}${mode}${button.id()}').click(function() {
									
								s =  ${_searchContext.entityName()}${"_this"}.selectedItem();
								if(s.length == 0 ){
									bootbox.confirm("Please Mark Selection From Grid", function(result) {
										if(result == true){
											return;
								        }else{
								        	return;
								        }
									});
									return;
								}
								$.ajax({
									url:'${pageContext.request.contextPath}${button.url()}',
									data: {query: s[0]},
									success: function(data) {
										$('#custom-modal-container').empty();
										$('#custom-modal-container').html(data);
										$('#custom-modal-container').modal('show').css({
											width: window.innerWidth * 0.9,
											position: 'absolute',
											left:'25%'});
										if(typeof(${_searchContext.entityName()}${mode}${button.id()}_onReady)=='function'){
											${_searchContext.entityName()}${mode}${button.id()}_onReady();
										}
							        }
							    });
			  		 	});
						</c:when>
					<c:when test='${button.target().name()=="NEW"}'>
							 $('#${_searchContext.entityName()}${mode}${button.id()}').click(function() {
									s =  ${_searchContext.entityName()}${"_this"}.selectedItem();
									if(s.length == 0 ){
										bootbox.confirm("Please Mark Selection From Grid", function(result) {
											if(result == true){
												return;
									        }else{
									        	return;
									        }
										});
										return;
									}
									window.location.href = "${pageContext.request.contextPath}${button.url()}"+"/"+s[0];
									
								
			  		 		}); 
							
					</c:when>
					</c:choose>  		
		  	</c:if>
		</c:forEach>
			
			$(".search-query").keypress(function(event){
				if(event.which == "13"){
					${_searchContext.entityName()}${"_this"}.doSearch();
				}
			});
			$(".search-on-select").change(function(event){
				${_searchContext.entityName()}${"_this"}.doSearch();
			});
		}
	}
	
	${_searchContext.entityName()}${mode}${"_SearchGrid"}.initialise();
	<c:if test="${_parentSearchCtx == null}">
		var options = {width:window.innerWidth * 0.8 ,
				increaseHeight:100,leftPanelWidthPercent:20,
				submitUrl:'${pageContext.request.contextPath}${_searchContext.createUrl()}',
				id:'${_searchContext.entityName()}${"_add-wizard"}',
				onCardReady: function(_currentCard){
					
					if(typeof(${_searchContext.entityName()}${mode}_onCardReady)=='function'){
						${_searchContext.entityName()}${mode}_onCardReady(_currentCard);
					}
					},
				onSuccess: function(){
					${_searchContext.entityName()}${mode}${"_SearchGrid"}.doSearch();
				}
				};
		var  ${_searchContext.entityName()}${"_wizard"} = $('#${_searchContext.entityName()}${"_some-wizard"}').wizard(options);
	
	</c:if>	
	
	$(window).bind('resize', function() {
 		$('#${_searchContext.entityName()}${mode}${"_jqGrid_list"}').setGridWidth($('#${_searchContext.entityName()}${mode}${"_search"}').width()+40);
 	}).trigger('resize');
	$('.ui-icon-newwin').css(
			{
    			"background-image":"url('http://localhost:8080/time/resources/images/excel.png')",
    			"background-position":"0"
    		});
	
</script>
 