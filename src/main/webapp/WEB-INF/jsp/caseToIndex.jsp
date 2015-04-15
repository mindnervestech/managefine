<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 
<jsp:include page="menuContext.jsp"/> 
<h4><b style=" width: 1051px; margin-left: 20px;"><i>Assigned To Me</i></b></h4>

<c:set var="_searchContext" value="${context}" scope="request" />
<c:set var="_parentSearchCtx" value="${null}" scope="request" />
<c:set var="mode" value="add" scope="request" />
<jsp:include page="searchContext.jsp"/> 

<div>
<div class="form-group">
 <div class="col-md-12">
 <div id="dialog" title="Assigned To Me" style="background: white;height: 311px;">
 <div class="form-group">
     <form name="myForm" method="post" action="${pageContext.request.contextPath}/caseToCreate">   <!--modelAttribute="pVm" action="/saveprojectTypeandName" -->
        <div class="modal-body" >
                    <div class="form-group">
                        <label style="margin-left: 15px;">Type</label>
                        
                            <select class="col-md-6 required" id="caseStatus" name="caseStatus"  placeholder="Select Project type."  required> 
                                <option value="">-select-</option>
                                <option value="Assigned">Assigned</option>
                                <option value="Unassigned">Unassigned</option>
                                
                            </select>
                        
                    </div>
                    <div class="form-group">
                     <div class="col-md-12" style="padding-right: 0px;padding-left: 0px;margin-top: 15px;">
                     <div class="col-md-6" style="padding-right: 0px;padding-left: 0px;">
                        <label for="org-type" class="col-md-11">Note</label>
                            
                            <!--  <input type="text" class="col-md-11" name="caseNote"
                            class="form-control" id="pro-type" 
                            placeholder="Enter Project Name."  required> -->
                            
                            <textarea rows="4" cols="50" style="width: 285px;" name="caseNote"
                            class="form-control" >
 
							</textarea>
                            </div>
 
                            
                        </div>    
                    </div>
                    <input type="hidden" id="myitem" name="caseform">
                    </div>
                    <br>
                    <br>
                    <br>
                    <div  class="col-md-11">
                    <div  class="col-md-8">
                    </div>
                <button type="submit" class="btn btn-primary col-md-3" >Save</button>   <!-- ng-click="addProjectName(projectinfo)" -->
                </div>
                </form>
                </div>    
    </div>
    </div>
    </div>
    </div>
    
    <script>
    $('#dialog').css('display','none');
    function CaseEditButtonFn(selItem) {
		console.log("please open new po -up now");
		console.log(selItem);
		  
		$('#myitem').val(selItem);    
		         $( "#dialog" ).dialog({"width":656,"top": 130,"height":400,open: function(){
		                $('.ui-dialog-titlebar-close').addClass('ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only');
		                $('.ui-dialog-titlebar-close').append('<span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span class="ui-button-text">close</span>');
		            }});
    }
</script>