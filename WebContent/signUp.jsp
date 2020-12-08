<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:body>
		<div>
			<h2 class="title is-2">Sign Up</h2>
			<form action="./SignUp" method="post">
				<div class="field">
					<label class="label">User name:</label>
					<div class="control">
				    	<input class="input ${empty errUserName ? '' : 'is-danger'}"
				    		value="${signUpViewModel.userName}"
				    		name="txtUserName" type="text" placeholder="Insert your user name">
				  	</div>
				  	<c:if test="${not empty errUserName}">
				  		<p class="help is-danger">${errUserName}</p>
				  	</c:if>
				</div>
				<div class="field">
					<label class="label">Email:</label>
					<div class="control">
				    	<input class="input ${empty errEmail ? '' : 'is-danger'}"
				    		value="${signUpViewModel.email}"
				    		name="txtEmail" type="text" placeholder="Insert your email">
				  	</div>
				  	<c:if test="${not empty errEmail}">
				  		<p class="help is-danger">${errEmail}</p>
				  	</c:if>
				</div>
				<div class="field">
					<label class="label">Password:</label>
					<div class="control">
				    	<input class="input ${empty errPassword ? '' : 'is-danger'}"
				    		value="${signUpViewModel.password}"
				    		name="txtPassword" type="password" placeholder="Insert your password">
				  	</div>
				  	<c:if test="${not empty errPassword}">
				  		<p class="help is-danger">${errPassword}</p>
				  	</c:if>
				</div>
				<div class="field">
					<label class="label">Reenter password:</label>
					<div class="control">
				    	<input class="input ${empty errReenterPassword ? '' : 'is-danger'}"
				    		value="${signUpViewModel.reenterPassword}"
				    		name="txtReenterPassword" type="password" placeholder="Insert your password">
				  	</div>
				  	<c:if test="${not empty errReenterPassword}">
				  		<p class="help is-danger">${errReenterPassword}</p>
				  	</c:if>
				</div>
				<div class="field" style="margin-top: 20px">
					<button class="button is-primary">Sign up</button>
				</div>
			</form>
		</div>
	</jsp:body>
</t:layout>