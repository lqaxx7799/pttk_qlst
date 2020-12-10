<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Xuất kho</h2>
			<div>
				<strong>Mã đơn hàng: </strong><span>${invoice.id}</span><br/>
				<strong>Tổng tiền: </strong><span><fmt:formatNumber type="number" maxFractionDigits="3" value="${totalMoney}" />đ</span><br/>
				<strong>Người đặt hàng: </strong><span>${invoice.customer.userName}</span><br/>
				<strong>Thời gian đặt hàng: </strong><span>${invoice.createdTime}</span><br/>
				<strong>Địa chỉ nhận hàng: </strong><span>${invoice.deliveryAddress}</span><br/>
				<strong>Phương thức thanh toán: </strong>
				<span>
					<c:if test="${invoice.paymentMethod eq 'direct'}">
						Thanh toán khi nhận hàng
					</c:if>
					<c:if test="${invoice.paymentMethod eq 'online'}">
						Thanh toán online
					</c:if>
				</span>
				<br/>
			</div>
		</div>
		<div>
			<div class="title" style="margin-bottom: 20px; margin-top: 20px;">Chi tiết đơn hàng: </div>
			<table class="table is-striped">
				<thead>
					<tr>
						<th>STT</th>
						<th>Mã mặt hàng</th>
						<th>Thông tin mặt hàng</th>
						<th>Đơn giá</th>
						<th>Số lượng</th>
						<th>Thành tiền</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${invoiceDetails}" var="invoiceDetail" varStatus="loop">
						<tr>
							<td>${loop.index}</td>
							<td>${invoiceDetail.item.id}</td>
							<td>${invoiceDetail.item.itemName}</td>
							<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${invoiceDetail.unitPrice}" />đ</td>
							<td>${invoiceDetail.quantity}</td>
							<td>
								<fmt:formatNumber type="number" maxFractionDigits="3" value="${invoiceDetail.unitPrice * invoiceDetail.quantity}" />đ
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div>
			<form action="./ExportInvoice" method="POST" id="formExport">
				<input type="hidden" name="txtInvoiceId" value="${invoice.id}" />
				<div style="display: flex; margin-top: 20px;">
					<div class="field" style="margin-right: 20px;">
						<label class="label">Cập nhật trạng thái</label>
						<div class="control">
							<div class="select">
					      		<select name="txtStatus">
					        		<option value="WAITING">Đợi xuất</option>
					        		<option value="DELIVERING">Giao hàng</option>
					      		</select>
					    	</div>
						</div>
					</div>
					<div class="field">
						<label class="label">Chọn nhân viên vận chuyển</label>
						<div class="control">
							<div class="select">
					      		<select name="txtExportEmployee">
					      			<c:forEach items="${employees}" var="employee">
					      				<option value="${employee.id}">${employee.userName}</option>
					      			</c:forEach>					        		
					      		</select>
					    	</div>
						</div>
					</div>
					<div style="flex-grow: 1"></div>
					<button type="button" class="button is-primary" id="btnPrint">In hóa đơn</button>
				</div>
				<button type="submit" class="button is-primary">Xuất kho</button>
			</form>
		</div>
		<script>
			$('#formExport').submit(function(e) {
				e.preventDefault();
				$.ajax({
					type: $(this).attr('method'),
					url: $(this).attr('action'),
					data: new FormData(this),
	                contentType: false,
	                processData: false,
	                success: function(response) {
	                	console.log(response)
	                	window.location = '/pttk_qlst/BrowseInvoice';
	                }
				});
			});
			
			$('#btnPrint').click(function(e) {
				toastr['warning']('Thiết bị in không hoạt động. Kiểm tra lại thiết bị và thử lại');
			})
		</script>
	</jsp:body>
</t:layout>