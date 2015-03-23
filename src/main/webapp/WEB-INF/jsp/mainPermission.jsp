
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



	<div id="permission_search_accordion">
	<jsp:include page="menuContext.jsp"/> 
	
	<h4 class="heading"><i>Manage User Permissions :</i></h4>
	<div class="searchContainer" style="height: 389px;">
		<div id="searchPermission" class="well form-inline" style="margin-left: 1%;margin-right: 1%;">
	
			<table>
				<tr>
					<td><input type="text" id="user_first_name" placeholder="First Name" class="input-medium search-query" /></td>
					<td><input type="text" id="user_last_name" placeholder="Last Name" class="input-medium search-query"/></td>
					
					<td><button name="Search" class="btn" id="permission_search" style="margin-left: 55%;color: gray;">Search</button></td>
				</tr>
			</table>
		</div>
		
		 <div id="permissionSearchResult" style="margin-left: 1%;">
		<jsp:include page="searchPermission.jsp"/> 
		</div> 
		
		
	</div>
	</div>
<style>
.btnColor{
color: gray;
}
</style>