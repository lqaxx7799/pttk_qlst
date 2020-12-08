<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Đăng nhập</h2>
			<form action="./LogIn" method="post">
				<div class="field">
					<label class="label">Email:</label>
					<div class="control">
				    	<input class="input ${empty errEmail ? '' : 'is-danger'}"
				    		value="${logInViewModel.email}"
				    		name="txtEmail" type="text" placeholder="Nhập email của bạn">
				  	</div>
				  	<c:if test="${not empty errEmail}">
				  		<p class="help is-danger">${errEmail}</p>
				  	</c:if>
				</div>
				<div class="field">
					<label class="label">Mật khẩu:</label>
					<div class="control">
				    	<input class="input ${empty errPassword ? '' : 'is-danger'}"
				    		value="${logInViewModel.password}"
				    		name="txtPassword" type="password" placeholder="Nhập mật khẩu của bạn">
				  	</div>
				  	<c:if test="${not empty errPassword}">
				  		<p class="help is-danger">${errPassword}</p>
				  	</c:if>
				</div>
				<div class="field" style="margin-top: 20px">
					<button class="button is-primary">Đăng nhập</button>
				</div>
			</form>
		</div>
	</jsp:body>
</t:layout>