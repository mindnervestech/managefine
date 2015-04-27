<div  id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;position: absolute;left: 343px;width: 621px;">
	<div class="modal-dialog">
		<div class="modal-content" style="height: 551px;">
			<div class="modal-header" style="background: #005580;color: white;margin: 2px;border-bottom: 4px gray;height: 37px;padding: 1px;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true" style="color: white;font-size: 25px;">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Project Progress</h4>
			</div>
			<div class="examples">

 				 <div id="testDiv3"> 
				<div class="modal-body" style="max-height: 915px;overflow-y:hidden;">
				
					
				 <div class="form-group">
					 <div class="col-md-12" style="padding: 0px;">
						 <div class="col-md-6" style="padding: 0px;">
							 <span class="col-md-8"><b>Task % completed <span style="color:red;">*</span> :</b></span>
							 <div class="col-md-4">
							 <input class="col-md-9" type="text" name="projectName" ng-blur="task(taskCompilation)" data-ng-model="taskCompilation" required>%
							 </div>
						 </div>
						<div class="col-md-6">
						 <span ng-if="completeStatus == 'Inprogress'"><span  class="icon-forward"></span> {{completeStatus}}</span>
						  <span ng-if="completeStatus == 'Completed'" style="color:green"><span class="icon-ok"></span> {{completeStatus}}</span>
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
						 
						 </div>
						
						<div class="col-md-4">
						 <button type="button" class="btn btn-primary" ng-click="saveAttachment()">Upload</button>
						 
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
                	
                    	<tr ng-repeat="doc in fileAttachData.projectAttachment">
          					<td style="width: 22px;text-align: center;padding: 6px;">{{$index+1}}</td>
          					 <td style="text-align: center;">{{doc.docDate}}</td>
                           <td style="text-align: center;">{{doc.docName}}</td>
                             <td style="text-align: center;"><a ng-click="downloadfile(doc.id)">download</a></td>
                           
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
					 <div class="col-md-12" style="margin-bottom: 17px;" ng-repeat="note in fileAttachData.projectcomments">
						 <span style="font-style: italic;">" {{note.userName}} "</span>	
							
						<div class="comment_box fr col-md-12">
     							 <p class="comment_paragraph" contenteditable="true">" {{note.projectComment}} "</p>
         						  <span class="date"><b>{{note.commetDate}}</b></span> </div>  
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
		
		.ngdialog-content{
		 width: 660px;
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



			
