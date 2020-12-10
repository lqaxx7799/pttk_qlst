<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Duyệt đơn</h2>
			<div class="buttons">
				<a class="button is-primary" href="./BrowseInvoice">Xem tất cả</a>
				<a class="button is-primary" href="./BrowseInvoice?status=WAITING">Xem đơn chưa xuất</a>
			</div>
		</div>
		<div>
			<table class="table is-striped">
				<thead>
					<tr>
						<th>STT</th>
						<th>Mã đơn hàng</th>
						<th>Thông tin đơn hàng</th>
						<th>Tổng tiền</th>
						<th>Trạng thái</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${invoices}" var="invoice" varStatus="loop">
						<tr>
							<td>${loop.index}</td>
							<td>${invoice.id}</td>
							<td>
								<div><strong>Người đặt: </strong>${invoice.customer.userName}</div>
								<div><strong>Ngày đặt: </strong>${invoice.createdTime}</div>
							</td>
							<td>
								<fmt:formatNumber type="number" maxFractionDigits="3" value="${totalMoneys[loop.index]}" />đ
							</td>
							<td>
								<c:if test="${invoice.status eq 'WAITING'}">								
									Chưa xuất
								</c:if>
								<c:if test="${invoice.status eq 'DELIVERING'}">								
									Đang vận chuyển
								</c:if>
							</td>
							<td>
								<c:if test="${invoice.status eq 'WAITING'}">								
									<a class="button is-primary" href="./WaitingInvoiceDetail?id=${invoice.id}">Xuất kho</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</jsp:body>
</t:layout>