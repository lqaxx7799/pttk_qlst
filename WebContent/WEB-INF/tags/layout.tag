<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="footer" fragment="true"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Language" content="vi" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${titleName} - QLST by Qanh</title>
<link href="./css/bulma.min.css"
	rel="stylesheet" type="text/css" />
<link href="./css/toastr.min.css"
	rel="stylesheet" type="text/css" />
<style>
	.main-body {
		padding-top: 10px;
		padding-bottom: 30px;
	}
	.bookstore-wrapper {
		display: flex;
		flex-direction: row;
		width: 100%;
	}
	.bookstore-item {
		width: calc(100% / 5);
	}
	.bookstore-item:not(:last-of-type) {
		margin-right: 20px;
	}
</style>
<script src="./js/jquery-3.5.1.min.js"></script>
<script src="./js/toastr.min.js"></script>
</head>
<body>
	<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<a class="navbar-item" href="./Index">
				<img src="./images/bulma-logo.png" width="112" height="28" />
			</a>
			
			<a role="button" class="navbar-burger burger" aria-label="menu"
				aria-expanded="false" data-target="navbarBasicExample">
				<span aria-hidden="true"></span>
				<span aria-hidden="true"></span>
				<span aria-hidden="true"></span>
			</a>
		</div>

		<div id="navbarBasicExample" class="navbar-menu">
			<div class="navbar-start">
				<a class="navbar-item" href="./Index">Trang chủ</a>
				<c:if test='${not empty sessionScope.email and sessionScope.role eq "manager"}'>
					<a class="navbar-item" href="./ReportMenu">Báo cáo</a>
				</c:if>
				<c:if test='${not empty sessionScope.email and sessionScope.role eq "customer"}'>
					<a class="navbar-item" href="./SearchItem">Tìm kiếm mặt hàng</a>
					<a class="navbar-item" href="./CartDetail" id="btnCartDetail">
						Giỏ hàng
						<c:if test="${not empty sessionScope.cart}">&nbsp;
							<span class="tag is-primary">${sessionScope.cart.size()}</span>
						</c:if>
					</a>
				</c:if>
				<c:if test='${not empty sessionScope.email and sessionScope.role eq "storageEmployee"}'>
					<a class="navbar-item" href="./BrowseInvoice">Duyệt đơn</a>
				</c:if>
			</div>

			<div class="navbar-end">
				<div class="navbar-item">
					<c:if test='${empty sessionScope.email}'>
						<div class="buttons">
							<a class="button is-primary" href="./SignUp"><strong>Đăng kí</strong></a> 
							<a class="button is-light" href="./LogIn">Đăng nhập</a>
						</div>
					</c:if>
					<c:if test='${not empty sessionScope.email}'>
						<div style="margin-right: 10px;">Xin chào, ${sessionScope.userName}</div>
						<div class="buttons">
							<a class="button is-light" href="./LogOut">Đăng xuất</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</nav>
	<div class="container main-body">
		<jsp:doBody/>
	</div>
	<footer class="footer">
  		<div class="content has-text-centered">
    		By Qanh.
  		</div>
	</footer>
</body>
</html>
