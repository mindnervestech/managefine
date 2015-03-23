<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
	#custom-modal-container{
		width: 700px !important;
		left: 45% !important;
	}

	.formClass{
		padding: 0 30px 40px;
	}
	
	.modal-footer{
		padding: 15px 15px 5px !important;
		text-align: left;
		background-color: none !important;
		margin-top: 15px;
	}
	
	table{
		width: 100%;
		text-align: center;
		margin-top: 15px;
	}
	
	table tr th{
		padding: 5px 0 5px;
		border-bottom: 1px solid #333;
	}
	
	table tr td{
		padding: 5px 0;
		border-bottom: 1px solid #d5d5d5;
		border-left: 1px solid #d5d5d5;
		border-right: 1px solid #d5d5d5;
	}
	
	.modal-header .close{
		margin-right: -25px;
	}
	
</style>

<div class="formClass">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h4>Project Details</h4>
	</div>

	<div class="modal-body">
	<table>
		<tr>
			<th>Employee</th>
			<th>HRS Spent</th>
			<th>Money Billed</th>
		</tr>
		<c:forEach var="user" items="${userDetails}">
			<tr>
				<td>${user.empName}</td>
				<td>${user.hrsSpent}</td>
				<td>${user.moneyBilled}</td>
			
			</tr>
		</c:forEach>
	</table>
	</div>
</div>	