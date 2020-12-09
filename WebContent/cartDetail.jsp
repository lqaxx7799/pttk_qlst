<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Giỏ hàng của bạn</h2>
			<c:if test="${empty cart}">
				<h3 class="title is-3">Bạn chưa có sản phẩm nào trong giỏ hàng!</h3>
				<a class="button is-link" href="./SearchItem">Tìm kiếm sản phẩm</a>
			</c:if>
			<c:if test="${not empty cart}">
				<form action="./CreateInvoice" method="post" id="formCart">
					<div>
						<c:forEach items="${cart}" var="cartItem">
							<input type="hidden" name="txtItemId" value="${cartItem.item.id}"/>
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
													<a href="./ItemDetail?id=${cartItem.item.id}"><strong>${cartItem.item.itemName}</strong></a>
													<br/>
													<p class="has-text-danger txtUnitPrice" data-itemid="${cartItem.item.id}" data-unitprice="${cartItem.item.unitPrice}">
														<fmt:formatNumber type="number" maxFractionDigits="3" value="${cartItem.item.unitPrice}" />đ
													</p>
												</div>
											</div>
											<div class="tile is-parent is-2 level-item">
												<div class="tile is-child">
													<div class="field">
														<label class="label">Số lượng:</label>
														<div class="control">
													    	<input class="input" value="${cartItem.quantity}" id="txtQuantity${cartItem.item.id}" name="txtQuantity" type="number" style="width: 70px" />
													  	</div>
													</div>
												</div>
											</div>
											<div class="tile is-parent is-2 level-item">
												<div class="tile is-child">
													<a class="button is-warning" href="./RemoveFromCart?id=${cartItem.item.id}">Gỡ</a>
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
					<div style="margin-top: 20px" class="columns">
						<div class="field column">
							<label class="label">Phương thức thanh toán</label>
						 	<div class="control">
						    	<div class="select">
						      		<select name="txtPaymentMethod" id="txtPaymentMethod">
						        		<option value="direct">Thanh toán khi nhận hàng</option>
						        		<option value="online">Thanh toán online</option>
						      		</select>
						    	</div>
						  	</div>
						</div>
						<div id="creditCard" class="field column" style="display: none">
  							<label class="label">Thẻ thanh toán</label>
  							<div class="control">
    							<input class="input" type="text" name="txtCreditCard" placeholder="Nhập thẻ thanh toán">
  							</div>
						</div>
					</div>
					<div style="margin-top: 20px">
						<div class="field">
  							<label class="label">Địa chỉ nhận hàng</label>
  							<div class="control">
    							<input class="input" type="text" name="txtDeliveryAddress" placeholder="Nhập địa chỉ nhận hàng">
  							</div>
						</div>
					</div>
					<div style="margin-top: 20px">
						<button type="submit" class="button is-primary">Thanh toán</button>
					</div>
				</form>
			</c:if>
		</div>
		<script>
			document.querySelectorAll('[id*=txtQuantity]').forEach(element => {
				element.addEventListener('input', event => {
					let totalMoney = 0;
					document.querySelectorAll('.txtUnitPrice').forEach(e => {
						const price = parseFloat(e.dataset.unitprice);
						const itemId = e.dataset.itemid;
						const quantityElement = document.querySelector('#txtQuantity'+itemId);
						const quantity = quantityElement.value ? parseInt(quantityElement.value) : 0;
						totalMoney += price * quantity;
					});
					const formatted = totalMoney.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					document.querySelector('#totalMoney').innerHTML = formatted + 'đ';
				});
			});
			
			$('#txtPaymentMethod').on('change', function(e) {
				console.log(this.value);
				if (this.value === 'online') {
					$('#creditCard').css('display', 'block');
				} else {
					$('#creditCard').css('display', 'none');
				}
			})
			
			$('#formCart').submit(function(e) {
				e.preventDefault();
				const formData = new FormData(this);
				if (formData.get('txtPaymentMethod') === 'online' && !formData.get('txtCreditCard')) {
					toastr['error']('Không thể kết nối đến thẻ');
					return;
				}
				if (!formData.get('txtDeliveryAddres')) {
					toastr['error']('Không được để trống địa chỉ giao hàng');
					return;
				}
				$.ajax({
					type: $(this).attr('method'),
					url: $(this).attr('action'),
					data: formData,
	                contentType: false,
	                processData: false,
	                success: function(response) {
	                	console.log(response)
	                	window.location = '/pttk_qlst/YourInvoice?id=' + response.id;
	                }
				});
			});
		</script>
	</jsp:body>
</t:layout>