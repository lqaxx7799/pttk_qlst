<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Bạn đã đặt hàng thành công</h2>
		</div>
		<div>
			<c:forEach items="${invoiceDetails}" var="invoiceDetail">
				<div class="box">
					<article class="media">
						<div class="media-left">
							<figure class="image is-64x64">
								<img src="./images/128x128.png" alt="Image">
							</figure>
						</div>
						<div class="media-content">
							<div class="content tile is-ancestor level">
								<div class="tile is-parent is-8 level-item">
									<div class="tile is-child">
										<a href="./ItemDetail?id=${invoiceDetail.item.id}"><strong>${invoiceDetail.item.itemName}</strong></a>
										<br/>
										<p class="has-text-danger txtUnitPrice" data-itemid="${invoiceDetail.item.id}">
											<fmt:formatNumber type="number" maxFractionDigits="3" value="${invoiceDetail.item.unitPrice}" />đ
										</p>
									</div>
								</div>
								<div class="tile is-parent is-2 level-item">
									<div class="tile is-child">
										<div class="field">
											<label class="label">Số lượng:</label>
											${invoiceDetail.quantity}
										</div>
									</div>
								</div>
							</div>
						</div>
				  </article>
				</div>
			</c:forEach>
		</div>
		<div style="margin-top: 20px">
			<strong>Tổng tiền: </strong> 
			<span id="totalMoney" class="has-text-danger">
				<fmt:formatNumber type="number" maxFractionDigits="3" value="${totalMoney}" />đ
			</span>
		</div>
		<div style="margin-top: 20px">
			<div class="field">
				<label class="label">Phương thức thanh toán</label>
			 	<div class="control">
			 		<c:if test="${invoice.paymentMethod eq 'direct'}">
			 			Thanh toán khi nhận hàng
			 		</c:if>
			    	<c:if test="${invoice.paymentMethod eq 'online'}">
			 			Thanh toán online
			 		</c:if>
			  	</div>
			</div>
		</div>
		<c:if test="${invoice.paymentMethod eq 'online'}">
			<div style="margin-top: 20px">
				<div class="field">
					<label class="label">Thẻ thanh toán</label>
				 	<div class="control">
				    	${invoice.creditCard}
				  	</div>
				</div>
			</div>
		</c:if>
		<div style="margin-top: 20px">
			<div class="field">
				<label class="label">Địa chỉ nhận hàng</label>
				<div class="control">
					${invoice.deliveryAddress}
				</div>
			</div>
		</div>
	</jsp:body>
</t:layout>