<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style type="text/css">
.fontSize {
	font-size: 10px;
}

.ui-jqgrid tr.ui-row-ltr td {
	text-align:center;
}	

.heading {
	margin-left: 25px;
}

.permissions{
	margin-right: 20px;
	padding-top:1%;
}

.margin{
	margin-left:19%;
}
.space{
	margin-left:7%;
}

#role_permission_search {
	padding: 5px 10px;
	border-radius: 5px;
}

#role_permission_search:hover {
	background: #e6e6e6;
	color: #fff;
}

</style>

<table id="role_permission_list">
	<tr>
		<td />
	</tr>
</table>
<div id="role_permission_pager"></div>

<form id="role-modal-form">
	<div id="role-update-modal" title="Basic Modal" class="modal hide fade"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">X</button>
			<h3 id="myModalLabel">Permissions</h3>
			<h7>
			<strong style="color: red;">Click the Permission which
				should be accessed</strong></h7>
		</div>
		<div class="modal-body">
			<ul style="list-style-type: none;"></ul>
		</div>
		<div class="modal-footer">
			<button class="btn" style="color: red;" data-dismiss="modal" aria-hidden="true">Close</button>
			<button id="addRolePermissions" class="btn btn-primary">Save
				changes</button>
		</div>
		<input type="hidden" id="selectedRoleId" name="id">
	</div>
</form>
<!-- <p id="lastSelectedAgentID" style="display: none;">0</p> -->

<script>
	$(document).ready(
			function() {
				rolePermissionSearch = RolePermissionSearch
						.initialise("${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.RolePermissions.getRoleList.url%>");
				$("#role_permission_search").click(function() {
					rolePermissionSearch.doSearch();
				});

				$(".search-query").keypress(function(event) {
					if (event.which == "13") {
						rolePermissionSearch.doSearch();
					}
				});
				$('#addRolePermissions').click(function() {
					/* var values = $('input:checkbox:checked.permissions').map(function () {
						  return this.value;
					}); */
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/rolepermission/save",
						data : $("#role-modal-form").serialize(),
						success : function(data) {
							$('#role-update-modal').modal('hide');
							$.pnotify({
								history:false,
						        text: data
						    });
							rolePermissionSearch.doSearch();
						},
						error : function(data) {
							//console.log(data);
						},
						complete : function(jqXHR, status) {
							//console.log(status);
							//console.log(jqXHR);
							rolePermissionSearch.doSearch();
						}
					});
					return false;
				});

			});
	

	var RolePermissionSearch = {
		_searchURL : "",
		initialise : function(searchUrl) {
			this._searchURL = searchUrl;
			$("#role_permission_list").jqGrid(
					{
						url : this._searchURL,
						height : 231,
						width : $('#permissionSearchResult').width(),
						datatype : 'json',
						mtype : 'GET',
						colNames : ['Role','Permissions','Action'],
						colModel : [ {
							name : 'role_name',
							index : 'role_name',
							width : 35,
							searchoptions : {
								sopt : [ 'eq' ]
							}
						}, {
							name : 'permissions',
							index : 'permissions',
							width : 145,
							searchoptions : {
								sopt : [ 'eq' ]
							}
						}, {
							name : 'action',
							index : 'action',
							width : 8,
							formatter : this.actionFormatter,
							search : false,
							title : false
						}, ],
						pagination : true,
						pager : '#role_permission_pager',

						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						sortname : 'role',
						sortorder : 'desc',
						viewrecords : true,
						gridview : true,
						caption : 'Manage Permissions',

						onSelectRow : function(id) {
						},
						ondblClickRow : function(rowid) {
						},

						loadComplete : function(data) {
							/* if (data.records == 0) {
								alert("Search result not found!")
							} */

							$('a[id^="edit-link"]').each(function() {
								$(this).click(function() {

									var id = $(this).attr('cell');
									$('#selectedRoleId').val(id);

									$.ajax({
										type : "POST",
										url : "${pageContext.request.contextPath}/rolepermission/update",
										data : {
											id : id
										},
										success : function(data) {
											var result = JSON.parse(data);
											//console.log(result);
											//console.log(data);
											handleData(result);
										},
										complete : function(jqXHR, status) {

										}
									});
									return false;
								});
							});
							RolePermissionSearch.resizeGrid();
						}
					});

			function handleData(data) {
				$('#role-update-modal .modal-body ul').empty();
				for ( var i = 0; i < data.length; i++) {
					if(data[i].state== false){
						if(data[i].parentPresent == false){
							if(data[i].urlPresent == false)
							$('#role-update-modal .modal-body ul').append('<label class="permissions space" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
							else
							$('#role-update-modal .modal-body ul').append('<input type="checkbox" class="permissions" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
						}
						else{
							if(data[i].urlPresent == false)
							$('#role-update-modal .modal-body ul').append('<label  class="permissions margin space" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
							else
							$('#role-update-modal .modal-body ul').append('<input type="checkbox" class="permissions margin" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');	
						}
					}
					else{
						if(data[i].parentPresent == false){
							if(data[i].urlPresent == false)
							$('#role-update-modal .modal-body ul').append('<label class="permissions space" name="permissions" value="'+data[i].permissionKey+'" >'+data[i].permissionName+'<br/>');
							else
							$('#role-update-modal .modal-body ul').append('<input type="checkbox" class="permissions" name="permissions" value="'+data[i].permissionKey+'" checked>'+data[i].permissionName+'<br/>');	
						}else{
							if(data[i].urlPresent == false)
							$('#role-update-modal .modal-body ul').append('<label class="permissions margin space" name="permissions" value="'+data[i].permissionKey+'">'+data[i].permissionName+'<br/>');
							else
							$('#role-update-modal .modal-body ul').append('<input type="checkbox" class="permissions margin" name="permissions" value="'+data[i].permissionKey+'" checked>'+data[i].permissionName+'<br/>');	
						}
					}
				}
				$('#role-update-modal').modal();
			}

			jQuery("#role_permission_list").jqGrid('bindKeys');
			jQuery("#role_permission_list").jqGrid('navGrid',
					'#role_permission_pager', {
						del : false,
						add : false,
						edit : false,
						search : false,
						refresh : false
					});
			return this;
		},

		actionFormatter : function(cellvalue, options, rowObject) {
			var retVal = "";

			var show = "<span class='action-buttons' id='role_permission-edit'>"
					+ "<a href='#' cell='"+cellvalue+"' id='edit-link-"+cellvalue+"' " +
  									"class='show-link'>"
					+ "" + "</a></span>";
			return show;
		},
		resizeGrid : function() {
			$("#role_permission_list").setGridWidth(
					$('#permissionSearchResult').width());
		},

		doSearch : function() {

			var role = $("#role_name").val();
			var description = $("#role_description").val();
			jQuery("#role_permission_list").setGridParam(
					{
						mtype : 'GET',
						url : this._searchURL + "?" + "role=" + role
								+ "&description=" + description
					}).trigger("reloadGrid");
		}

	}
</script>
