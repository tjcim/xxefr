
<%@ include file="../common/header.jspf" %>
<%@ include file="../common/nav.jspf" %>
	<div class="bg-light">
		<div class="container bg-light pt-5 pb-5">
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">${errorMessage}</div>
			</c:if>
			<form action="./login.do" method="post">
				<div class="form-group">
					<label for="email">Email address</label>
					<input type="email" class="form-control" name="email" id="email" aria-describedby="emailHelp" placeholder="Enter email">
				</div>
				<div class="form-group">
					<label for="password">Password</label>
					<input type="password" class="form-control" name="password" id="password" placeholder="Password">
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</div>
	</div>
<%@ include file="../common/footer.jspf" %>