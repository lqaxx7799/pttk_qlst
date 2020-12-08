<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Chi tiết mặt hàng</h2>		
		</div>
		<div class="columns" style="margin-top: 20px;">
			<div class="column is-one-fifth">
				<img src="./images/128x128.png" />
			</div>
			<div class="column">
				<strong>Mã đơn hàng: </strong><span>${item.id}</span><br/>
				<strong>Tên đơn hàng: </strong><span>${item.itemName}</span><br/>
				<strong>Đơn giá: </strong><span><fmt:formatNumber type="number" maxFractionDigits="3" value="${item.unitPrice}" />đ</span><br/>
				<strong>Mô tả: </strong><span>${item.description}</span><br/>
			</div>
		</div>
		
		<div>
			<div>Lịch sử giao dịch</div>
			<table class="table">
				<thead>
					<tr>
						<th>STT</th>
						<th>Mã đơn hàng</th>
						<th>Người đặt</th>
						<th>Ngày đặt</th>
						<th>Số lượng</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${invoices}" var="invoice" varStatus="loop">
						<tr>
							<td>${loop.index}</td>
							<td>${invoice.id}</td>
							<td>${invoice.customer.userName}</td>
							<td>${invoice.createdTime}</td>
							<td>${quantities[loop.index]}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</jsp:body>
</t:layout>