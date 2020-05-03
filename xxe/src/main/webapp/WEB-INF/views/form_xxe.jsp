<%@ include file="../common/header.jspf" %>
<%@ include file="../common/nav.jspf" %>
	<div>
		<div class="container pt-5 pb-5" id="content">
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">${errorMessage}</div>
			</c:if>
			<h1>Basic XXE</h1>
			<hr/>
			<form>
				<div class="form-group">
					<label for="description">Description</label>
					<input type="text" class="form-control" name="description" id="description" value="Description">
				</div>
				<div class="form-group">
					<label for="category">Category</label>
					<input type="text" class="form-control" name="category" id="category" value="Category">
				</div>
				<button type="submit" class="btn btn-primary" onclick="submitXML()">Add ToDo</button>
				<hr/>
				<div class="form-group">
					<label for="data">Data</label>
					<textarea class="form-control" name="data" id="data" placeholder="XML to be submitted" rows="10"></textarea>
				</div>
			</form>
		</div>
	</div>
<%@ include file="../common/footer.jspf" %>