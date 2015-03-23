<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="menuContext.jsp"/> 
	<div class="permissionRadio">
		<input id="user" type="radio" name="somevalue" value="user" checked>User
		<input id="role" type="radio" name="somevalue" value="role">Role<br>
	</div>
	<div id="userPermission">
		<jsp:include page="mainPermission.jsp"/> 
	</div>

	<div id="rolePermission" style="display:none;">
		<jsp:include page="roleMainPermission.jsp"/> 
	</div>

<script type="text/javascript">

$(document).ready(function() { 
	$('input[id=user]').change(function(){
		$("#rolePermission").css("display","none");
		$("#userPermission").css("display","block");
	});
	
	$('input[id=role]').change(function(){
		$("#userPermission").css("display","none");
		$("#rolePermission").css("display","block");
	});
});
</script>
