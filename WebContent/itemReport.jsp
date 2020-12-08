<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Xem báo báo mặt hàng theo doanh thu</h2>
			<div class="buttons">
				<a href="./ItemReport" class="button is-primary">Mặt hàng theo doanh thu</a>
				<a href="#" class="button is-primary">Nhà phân phối theo số lượng</a>
			</div>
		</div>
		<div>
			<form action="./ItemReport" method="GET">
				<div class="columns">
					<div class="column is-one-third">
						<div class="field">
	 						<label class="label">Ngày bắt đầu</label>
	  						<div class="control">
	    						<input class="input" type="date" name="txtFrom" placeholder="Nhập ngày bắt đầu" value="${param.txtFrom}">
	  						</div>
						</div>
					</div>
					<div class="column is-one-third">
						<div class="field">
	 						<label class="label">Ngày kết thúc</label>
	  						<div class="control">
	    						<input class="input" type="date" name="txtTo" placeholder="Nhập kết thúc" value="${param.txtTo}">
	  						</div>
						</div>
					</div>
					<div class="column is-one-third">
						<button class="button is-primary" type="submit">Xem báo cáo</button>
					</div>
				</div>
				<span class="has-text-danger">${errorMessage}</span>
			</form>
		</div>
		<c:if test="${not empty itemReports}">
			<div>
				<table class="table">
					<thead>
						<tr>
							<th>STT</th>
							<th>Mã mặt hàng</th>
							<th>Thông tin mặt hàng</th>
							<th>Đơn giá</th>
							<th>Số lượng bán</th>
							<th>Thành tiền</th>
							<th>Hành động</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${itemReports}" var="itemReport" varStatus="loop">
							<tr>
								<td>${loop.index}</td>
								<td>${itemReport.item.id}</td>
								<td>${itemReport.item.itemName}</td>
								<td>
									<fmt:formatNumber type="number" maxFractionDigits="3" value="${itemReport.item.unitPrice}" />đ
								</td>
								<td>${itemReport.quantity}</td>
								<td>
									<fmt:formatNumber type="number" maxFractionDigits="3" value="${itemReport.item.unitPrice * itemReport.quantity}" />đ
								</td>
								<td>
									<a class="button is-primary" href="./ItemReportDetail?id=${itemReport.item.id}">Chi tiết</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		
	</jsp:body>
</t:layout>