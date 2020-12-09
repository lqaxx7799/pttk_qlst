<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Tìm kiếm mặt hàng</h2>
			<form action="./SearchItem">
				<div style="margin-bottom: 20px; display: flex; width: 500px">
					<input type="text" class="input" name="q" value="${param.q}">
					<button type="submit" class="button is-info">Tìm</button>
				</div>
			</form>
			<c:if test="${empty items}">
				<h3 class="title is-3">Không tìm thấy mặt hàng</h3>
			</c:if>
			<c:if test="${not empty items}">
				<c:forEach items="${items}" var="item">
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
											<a href="./ItemDetail?id=${item.id}"><strong>${item.itemName}</strong></a>
											<br/>
											<p class="has-text-danger txtUnitPrice" data-itemid="${item.id}">
												<fmt:formatNumber type="number" maxFractionDigits="3" value="${item.unitPrice}" />đ
											</p>
										</div>
									</div>
									<div class="tile is-parent is-2 level-item">
										<div class="tile is-child">
											<div class="field">
												<label class="label">Số lượng:</label>
												<div class="control">
											    	<input type="text" class="input" class="txtQuantity" data-id="${item.id}" style="width: 70px" />
											  	</div>
											</div>
										</div>
									</div>
									<div class="tile is-parent is-2 level-item">
										<div class="tile is-child">
											<button class="button is-primary btnAddToCart" data-id="${item.id}">Thêm</button>
										</div>
									</div>
								</div>
							</div>
					  </article>
					</div>
				</c:forEach>
			</c:if>
		</div>
		
		<script>
			$('.btnAddToCart').click(function(e) {
				const itemId = $(this).data('id');
				const quantityStr = $(`input[data-id="\${itemId}"]`).val();
				if (isNaN(quantityStr)) {
					alert("Error");
					return;
				}
				const quantity = parseInt(quantityStr);
				if (quantity <= 0) {
					alert("Error");
					return;
				}
				const formData = new FormData();
				formData.append('itemId', itemId);
				formData.append('quantity', quantity);
				$.ajax({
					type: 'POST',
					url: './AddToCart',
					data: formData,
	                contentType: false,
	                processData: false,
	                success: function(response) {
	                	console.log(response)
	                	toastr['info']('Thêm mặt hàng vào giỏ thành công');
	                	$(`input[data-id="\${itemId}"]`).val('');
	                	const numberElement = $('#btnCartDetail > span');
	                	if (numberElement.length) {
		                		numberElement.text(response.number);
	                	} else {
	                		$('#btnCartDetail').append('&nbsp;<span class="tag is-primary">1</span>')
	                	}
	                }
				});
			});
		</script>
	</jsp:body>
</t:layout>