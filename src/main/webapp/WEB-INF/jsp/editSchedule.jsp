<div  id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content" style="height: 460px;overflow-x:auto;">
			<div class="modal-header" style="background: #005580;color: white;margin: 2px;border-bottom: 4px gray;height: 37px;padding: 6px;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true" style="color: white;font-size: 25px;">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel" style="margin-top:3px;">Task Details</h4>
			</div>
			<div class="examples">

 				 <div id="testDiv3"> 
				<div class="modal-body" style="max-height: 412px;">
				
					
				 <div class="form-group">
				 <div class="col-md-12" style="padding: 0px;">
						 <div class="col-md-6" style="padding: 0px;">
							 <span class="col-md-4"><b>Project Name :</b></span>
							 <div class="col-md-4">
							 <label>{{projectCode}}</label>
							 </div>
						 </div>
						<div class="col-md-6">
							 <span class="col-md-4"><b>Task Name :</b></span>
							 <div class="col-md-4">
							 <label>{{taskCode}}</label>
							 </div>
						</div>	
					</div>
					<div class="col-md-12" style="padding: 0px;">
						 <div class="col-md-6" style="padding: 0px;">
							 <span class="col-md-4"><b>Start Time :</b></span>
							 <div class="col-md-4">
							 <label>{{editStartTime}}</label>
							 </div>
						 </div>
						<div class="col-md-6">
							 <span class="col-md-4"><b>End Time :</b></span>
							 <div class="col-md-4">
							 <label>{{editEndTime}}</label>
							 </div>
						</div>	
					</div>
					<div class="col-md-12" style="padding: 0px;">
						 <div class="col-md-6" style="padding: 0px;">
							 <span class="col-md-4"><b>Status :</b></span>
							 <div class="col-md-4">
							 <select ng-model="status" style="width:140px;" ng-change="setStatus(status)">
							 	<option value="Not Started">Not Started</option>
							 	<option value="Inprogress">InProgress</option>
							 	<option value="Paused">Paused</option>
							 	<option value="Completed">Completed</option>
							 	<option value="Cancelled">Cancelled</option>
							 </select>
							 </div>
						 </div>
						<div class="col-md-6">
							 <span class="col-md-4"><b>Date :</b></span>
							 <div class="col-md-4">
							 <label>{{currentDate}}</label>
							 </div>
						</div>	
					</div>
					<div class="col-md-12" style="padding: 0px;margin-top:2%;">
						 <div class="col-md-6" style="padding: 0px;">
						 	<span class="col-md-4"><b>Customer :</b></span>
							 <div class="col-md-6">
							 <label>{{customer}}</label>
							 </div>
						 </div>
						<div class="col-md-6">
							 <span class="col-md-4"><b>Supplier :</b></span>
							 <div class="col-md-6">
							 <label>{{supplier}}</label>
							 </div>
						</div>	
					</div> 
					<div class="col-md-12" style="padding: 0px;">
							 <span class="col-md-2"><b>Notes :</b></span>
							 <div class="col-md-8">
							 <label>{{todayNotes}}</label>
							 </div>
					</div> 
					<hr style="width: 100%;float: left;">
					
					 <div class="form-group">
					 <div class="col-md-12" style="padding: 0px;">
						 <div class="col-md-2">
							 <span><b>Attachment:</b></span>
							  
						 </div>
						 <div class="col-md-5">
						 
						 	<input type="file" ng-file-select="selectFile($files)" id="org-profile" required>
						 	 <p style="color:red;" ng-show="fileErr"><b>*Please select file</b></p>
						 </div>
						
						<div class="col-md-4">
						 <button type="button" class="btn btn-primary" ng-click="saveAttachment()">Upload</button>
						 <p style="color:green;" ng-show="showMsg"><b>File Uploaded Successfully</b></p>
						</div>	
						<div class="col-md-2">
						</div>
						  
					</div>
					
					</div>
					<div class="form-group">
					 <div class="col-md-12">
						<table class="table table-striped" border="1" style="width: 100%;font-size: small;margin-top: 10px;">
                	<thead style="background-color: #005580;">
                    	<tr>
                           <th style="text-align: center;width: 36px;padding: 6px;"><a style="color: white;">Sr.No</a></th>
                            <th style="text-align: center;"><a style="color: white;">DateTime</a></th>
                            <th style="text-align: center;"><a style="color: white;">Documents</a></th>
                            <th style="width:81px;text-align: center;color: white;">Download</th>
                        </tr>
                    </thead>
                	<tbody>
                	
                    	<tr ng-repeat="doc in documentsData">
          					<td style="width: 22px;text-align: center;padding: 6px;">{{$index+1}}</td>
          					 <td style="text-align: center;">{{doc.date}}</td>
                           <td style="text-align: center;">{{doc.fileName}}</td>
                             <td style="text-align: center;"><a ng-click="downloadfile(doc.id)" style="cursor: pointer;">download</a></td>
                           
                            </tr>
                           
                    	</tbody>
                </table>	
					</div>
					</div>
					<hr style="width: 100%;float: left;">
				<div class="form-group">
					 <div class="col-md-12">
						
							 <span class="col-md-4" style="padding: 0px;margin-left: 3px;"><b>Activity :</b></span>
							 <textarea  class="form-control col-md-12" ng-model="comment" rows="1"
						 placeholder="Comment." required></textarea>
						 
						 <div class="col-md-3" style="margin-top: 6px;margin-bottom: 15px;padding-left:0px;">
						 <button type="button" class="btn btn-primary" ng-click="saveComment(comment)">Post Comment</button>
						 
						</div>	
						
					</div>
		    	</div>
		    	
		    	<div class="form-group">
					 <div class="col-md-12" style="margin-bottom: 17px;" ng-repeat="note in commentsList">
						 <span style="font-style: italic;">" {{note.userName}} "</span>	
							
						<div class="comment_box fr col-md-12">
     							 <p class="comment_paragraph" contenteditable="true">" {{note.comment}} "</p>
         						  <span class="date"><b>{{note.date}}</b></span> </div>  
					</div>
		    	</div>
		    	
		    
					</div>
			
				
				
			
		</div>
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
	 .comment_paragraph{
		color: #454545;
		line-height: 14px;
		margin: 4px 10px 0px 15px;
	}
	
	.date {
    float: right;
    font-size: 0.85em;
    color: #454545;
    margin-top: 0px;
    margin-right: 15px;
	}
	
	.slimScrollDiv{
	
		height: 486px !important;;
		}
		
	.ngdialog-content {
	  	width: 60%;
  		margin-left: 19%;
  		margin-top: 4%;
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



	<!-- ------------- -->
					
					<!--   <div class="container">
      <h2>Collapse</h2>
      <div class="panel-group">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" id="coll1" class="collapsed" data-target="#collapse1" ng-click="opencoll()" aria-expanded="true" aria-controls="collapse1" style="cursor: pointer;">Collapsible Group 1</a>
            </h4>
          </div>
          <div id="collapse1" class="collapse in">
            <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</div>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Collapsible Group 2</a>
            </h4>
          </div>
          <div id="collapse2" class="panel-collapse collapse">
            <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</div>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Collapsible Group 3</a>
            </h4>
          </div>
          <div id="collapse3" class="collapse in">
            <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</div>
          </div>
        </div>
      </div> 
    </div>   -->
					
				<!-- -------------- -->	
				
			
