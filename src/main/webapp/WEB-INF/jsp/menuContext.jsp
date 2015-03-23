<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="tabbable container">
<ul class="nav nav-tabs" style="background:#5c5c5c;">
	<c:forEach var="items" items="${_menuContext.items}">	
	
		 <c:if test="${items.isSubMenu()}"> 	
		  		<c:if test='${items.name == "Logout"}'>
		  		<li style="float:right;">

		 			 <a href="${pageContext.request.contextPath}<%=com.mnt.time.controller.routes.Application.index.url%>" style="padding-top: 8px; float:left;"> ${user.firstName} |</a>
		 			 <a style="float:left;" href="${pageContext.request.contextPath}${items.url}">${items.name}</a>
		 		</li>
		 		</c:if>
		 		<c:if test='${items.name != "Logout"}'>
		 		<li>
		 			<a href="${pageContext.request.contextPath}${items.url}">${items.name}</a>
		 		</li>
		 		</c:if>
		 	
		 </c:if>
		 <c:if test="${!items.isSubMenu()}">
		 	<li class="dropdown">
		 		<c:if test='${items.name == "Actions"}'>
 		 			<% int count =com.mnt.time.controller.Application.count(((models.User)request.getAttribute("user")).getEmail()); if(count != 0) { %>
		 			<div class="noti_bubble"><%=count %></div>
		 			<%} %>
		 		</c:if>	
		 			<a class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" href="#">${items.name} <b class="caret"></b></a>
		 			<ul class="dropdown-menu">
		 			
                  	<c:forEach var="subMenu" items="${items.getSubMenu()}">
                 	 <li><a href='${pageContext.request.contextPath}${subMenu.url}' >${subMenu.name}</a></li>
                 	</c:forEach>
                	</ul>
		 	</li>
		 </c:if>
		 
	</c:forEach>
</ul>
<div class="tab-content">
	<div class="tab-pane active" id="tab-panel-holder-contents">
	</div>
</div>
</div>

<script>
	function loadTabContent(_url){
		$.post(_url,'',function(data){
			$('#tab-panel-holder-contents').empty();
			$('#tab-panel-holder-contents').html(data);			
		});
	}
	
	$('ul.nav li.dropdown').hover(function() {
		  $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(5);
		}, function() {
		  $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(5);
		});

</script>

<style>
.noti_bubble {
    position:absolute;
    top: -18px;
    right:14px;
    padding:4px 8px 2px 8px;
    background-color:#35bac4;
    color:white;
    font-weight:bold;
    font-size:14px;
    
    border-radius:30px;
    box-shadow:1px 1px 1px gray;
}
</style>
