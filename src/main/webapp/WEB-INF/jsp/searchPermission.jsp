<style type="text/css">
.fontSize{
	font-size: 10px;
}

#permission_search{
	padding: 5px 10px;
	border-radius: 5px;
}

#permission_search:hover{
	background: #e6e6e6;
	color: #fff;
}
.margin{
	margin-left:19%;
}
.space{
	margin-left:7%;
}

.permissions{
	margin-right: 20px;
	padding-top:1%;
}
</style>

<table id="permission_list"><tr><td/></tr></table> 
<div id="permission_pager" ></div> 

<form id="modal-form">
<div id="update-modal" title="Basic Modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
		<h3 id="myModalLabel">Permissions</h3>
		<h7><strong style = "color : red;">Click the Permission which should be accessed</strong></h7>
	</div>
	<div class="modal-body">
		<ul style="list-style-type: none;"></ul>
	</div>
	<div class="modal-footer">
		<button class="btn" style="color: red;" data-dismiss="modal" aria-hidden="true">Close</button>
		<button id="addPermissions" class="btn btn-primary">Save changes</button>
	</div>
	<input type="hidden" id="selectedUserId" name="id">
</div>
</form>
<!-- <p id="lastSelectedAgentID" style="display: none;">0</p> -->

<script>

$(document).ready(function(){
		permissionSearch = PermissionSearch.initialise("${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.UserPermissions.getUserList.url%>");
	 	$("#permission_search").click(function(){
	 		permissionSearch.doSearch();
		});	 
	 	
	 	
	 	$(".search-query").keypress(function(event){
			if(event.which == "13"){
				permissionSearch.doSearch();
			}
		});
	 	
		
		$('#addPermissions').click(function(){
			/* var values = $('input:checkbox:checked.permissions').map(function () {
				  return this.value;
			}); */

			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/permission/save",
				dataType : "text",
				data: $("#modal-form").serialize(),
				success: function(data){
					$('#update-modal').modal('hide');
					$.pnotify({
						history:false,
				        text: data
				    });
					permissionSearch.doSearch();
					
				},
				error: function(data){
				},
				complete: function(jqXHR,status){
					permissionSearch.doSearch();
				}
			});
			return false;
		});
	 	
});

var PermissionSearch = {
		_searchURL: "",
		initialise: function (searchUrl){
			this._searchURL = searchUrl;
			$("#permission_list").jqGrid({
				   url:this._searchURL,
				   height:231,
				   width: $('#searchPermission').width()+40,
				   datatype: 'json',
				   mtype: 'GET',
				   colNames:['Emp ID','First Name','Last Name','Designation','Permissions','Action'],
				   colModel :[ 
					 {name:'employeeId', index:'employeeId', width:10, searchoptions:{sopt:['eq']}},         
				     {name:'firstName', index:'firstName', width:20, searchoptions:{sopt:['eq']}},
				     {name:'lastName', index:'lastName', width:20, searchoptions:{sopt:['eq']}},
				     {name:'designation', index:'designation', width:20, searchoptions:{sopt:['eq']}},
				     {name:'permissionList', index:'permissionList', width:100, searchoptions:{sopt:['eq']}},
				     {name:'action', index:'action', width:8,formatter:this.actionFormatter, search:false, title:false}
				   ],
				   pagination : true,
				   pager: '#permission_pager',
				   
				   rowNum:10,
				   rowList:[10,20,30],
				   sortname: 'firstName',
				   sortorder: 'desc',
				   viewrecords: true,
				   gridview: true,
				   caption: 'Manage Permissions',
				   
				   onSelectRow: function(id){
				   },
				   ondblClickRow: function(rowid) {
				   },
				   
				   
				   loadComplete:function (data){
					   if(data.records == 0)
					   {alert("Search result not found!")}
				   
					   $('a[id^="edit-link"]').each(function() {
						   $(this).click(function(){
							   var id = $(this).attr('cell');
							   $('#selectedUserId').val(id);
								$.ajax({
									type: "POST",
									url: "${pageContext.request.contextPath}/permission/update",
									data: {id: id},
									success: function(data){
										var result = JSON.parse(data);
										console.log(result);
										//console.log(data);
										//alert("Json data loaded"+ data);
										handleData(result);
									},
									complete: function(jqXHR,status){
										$('#update-modal').modal();
									}
								});
								return false;
						   });
					   });
					   PermissionSearch.resizeGrid();
				   },	   
				});
			
			function handleData(data){
				$('#update-modal .modal-body ul').empty();
		 		for(var i=0;i<data.length;i++){
					if(data[i].state== false){
						if(data[i].parentPresent == false){
							if(data[i].urlPresent == false)
							$('#update-modal .modal-body ul').append('<label class="permissions space" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
							else
							$('#update-modal .modal-body ul').append('<input type="checkbox" class="permissions" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
						}   
						else{
							if(data[i].urlPresent == false)
							$('#update-modal .modal-body ul').append('<label  class="permissions margin space" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
							else
							$('#update-modal .modal-body ul').append('<input type="checkbox" class="permissions margin" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');	
						}
					}
					else{
						if(data[i].parentPresent == false){
							if(data[i].urlPresent == false)
							$('#update-modal .modal-body ul').append('<label class="permissions space" name="permissions" value="'+data[i].permissionKey+'" >'+data[i].permissionName+'<br/>');
							else
							$('#update-modal .modal-body ul').append('<input type="checkbox" class="permissions" name="permissions" value="'+data[i].permissionKey+'" checked>'+data[i].permissionName+'<br/>');	
						}else{
							if(data[i].urlPresent == false)
							$('#update-modal .modal-body ul').append('<label class="permissions margin space" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
							else
							$('#update-modal .modal-body ul').append('<input type="checkbox" class="permissions margin" name="permissions" value="'+data[i].permissionKey+'" checked>'+data[i].permissionName+'<br/>');	
						}
					}
				}
				$('#update-modal').modal();
				
				
			 	}
			
				jQuery("#permission_list").jqGrid('bindKeys');
				jQuery("#permission_list").jqGrid('navGrid','#permission_pager',{del:false,add:false,edit:false,search:false,refresh:false});
				return this;
			},
			
			actionFormatter: function(cellvalue, options, rowObject){
				var retVal = "";
				
				var show = "<span class='action-buttons' id='permission-edit'>" +
  								"<a href='#' cell='"+cellvalue+"' id='edit-link-"+cellvalue+"' " +
  									"class='show-link'>"+""+"</a></span>"; 
	          return show;
			},
			resizeGrid:function(){
				   $("#permission_list").setGridWidth($('#searchPermission').width()+40);
			},
			
			doSearch: function(){
				
				var firstName = $("#user_first_name").val();
				var lastName = $("#user_last_name").val();
				
				jQuery("#permission_list").setGridParam({
					mtype:'GET',
					url:this._searchURL +"?"+"firstName="+firstName+"&lastName="+lastName
				}).trigger("reloadGrid");
			}
	
}
</script>
