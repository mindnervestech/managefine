<div  id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" style="display: block;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background: blue;color: white;margin: 2px;border-bottom: 4px gray;">
				<button type="button" class="close" ng-click="closeThisDialog()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Project Progress</h4>
			</div>
			
				<form method="post" data-ng-submit="saveProjectType()" role="form" class="form-horizontal">
				<div class="modal-body">
					<form method="post" role="form" class="form-horizontal">
				 <div class="form-group">
					 <div class="col-md-12" style="padding: 0px;">
						 <div class="col-md-6" style="padding: 0px;">
							 <label class="col-md-9">Task % compilation *</label>
							 <input type="text" name="projectName" ng-blur="task(taskCompilation)" data-ng-model="taskCompilation" class="col-md-3" required>
						 </div>
						<div class="col-md-6">
						</div>	
						  
						  
					</div>
					
					<hr>
					
					 <div class="form-group">
					 <div class="col-md-12" style="margin-top: 25px;">
						 <div class="col-md-3">
							 <label>Attachment </label>
							  
						 </div>
						 <div class="col-md-3">
						 
						 	<input type="file" ng-file-select="selectFile($files)" id="org-profile" required>
						 
						 </div>
						
						<div class="col-md-4">
						 <button type="button" class="btn btn-primary" ng-click="saveAttachment()">Save on server</button>
						 
						</div>	
						<div class="col-md-2">
						</div>
						  
					</div>
					
					</div>
					<div class="form-group">
					 <div class="col-md-12">
						<table border="1" style="width: 100%;font-size: small;">
                	<thead style="background-color: #00F;">
                    	<tr>
                           <th><a style="color: white;">Sr.No</a></th>
                            <th><a style="color: white;">DateTime</a></th>
                            <th><a style="color: white;">Documents</a></th>
                            <th>Download</th>
                        </tr>
                    </thead>
                	<tbody>
                	
                    	<tr ng-repeat="doc in fileAttachData.projectAttachment">
          					<td>{{$index+1}}</td>
          					 <td>{{doc.docDate}}</td>
                           <td>{{doc.docName}}</td>
                             <td><a ng-click="downloadfile(doc.id)">download</a></td>
                           
                            </tr>
                           
                    	</tbody>
                </table>	
					</div>
					</div>
					<hr>
				<div class="form-group">
					 <div class="col-md-12">
						
							 <label class="col-md-4">Note</label>
							 <textarea  class="form-control col-md-12" ng-model="comment" rows="1"
						 placeholder="Comment." required></textarea>
						 
						 <div class="col-md-3" style="margin-top: 6px;">
						 <button type="button" class="btn btn-primary" ng-click="saveComment(comment)">Post Comment</button>
						 
						</div>	
						
					</div>
		    	</div>
		    	
		    	<div class="form-group">
					 <div class="col-md-12" ng-repeat="note in fileAttachData.projectcomments">
						 <label>" {{note.userName}} "</label>	
							
						<div class="comment_box fr col-md-12">
     							 <p class="comment_paragraph" contenteditable="true">" {{note.projectComment}} "</p>
         						  <span class="date">{{note.commetDate}}</span> </div>  
         						  
         						  <div class="container">
     
      <!-- <div class="panel-group" id="accordion"  ng-repeat="note in fileAttachData.projectcomments">
      
        <div class="panel panel-default">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" data-parent="#accordion" href='#collapsex{{$inde}}'>{{note.userName}}</a>
            </h4>
          </div>
          <div id="collapse{{$index}}" class="panel-collapse collapse">
            <div class="panel-body">{{note.projectComment}}</div>
          </div>
        </div>
       
      </div>  -->
    </div>
         						  
								
					</div>
		    	</div>
		    	
		    	<!-- ------------- -->
					
					 <!-- <div class="container">
      <h2>Collapse</h2>
      <div class="panel-group">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h4 class="panel-title">
              <a data-toggle="collapse" class="collapsed" data-target="#collapse1" aria-expanded="true" aria-controls="collapse1" style="cursor: pointer;">Collapsible Group 1</a>
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
    </div>  -->
					
				<!-- -------------- -->	
					</div>
			<!-- <div class="modal-footer">
				<button type="button" class="btn btn-default" ng-click="closeThisDialog()">Close</button>
				<button type="submit" class="btn btn-primary">Add Project Type</button>
			</div> -->
				
				</form>
			
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
</style>

