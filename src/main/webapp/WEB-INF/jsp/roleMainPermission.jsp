<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div id="role_permission_search_accordion">
	<jsp:include page="menuContext.jsp"/> 
	
	<h4 class="heading"><i>Manage Role Permissions :</i></h4>
	<div class="searchContainer" style="height: 389px;">
		<div id="searchRolePermission" class="well form-inline" style="margin-left: 1%;margin-right: 1%;" >
	
			<table>
				<tr>
					<td><input type="text" id="role_name" placeholder="Role Name" class="input-medium search-query" /></td>
					<td><input type="text" id="role_description" placeholder="Role Description" class="input-medium search-query"/></td>
					<td><button name="Search" class="btn" id="role_permission_search" style="margin-left: 55%;color: gray;">Search</button></td>
				</tr>
			</table>
		</div>
		
		<div id="permissionSearchResult">
		 <jsp:include page="roleSearchPermission.jsp"/> 
		</div>
	</div>
	</div>

<style>
.btnColor{
color: gray;
}
</style>