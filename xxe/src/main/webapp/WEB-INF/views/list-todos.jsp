<%@ include file="../common/header.jspf" %>
<%@ include file="../common/nav.jspf" %>
	<div class="container pt-5 pb-5">
		<h1>Your ToDos:</h1>
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">Description</th>
					<th scope="col">Category</th>
					<th scope="col">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.description}</td>
					<td>${todo.category}</td>
					<td>
						<form class="form-inline" action="./delete-todo.do" method="post" accept-charset="utf-8">
							<input type="hidden" name="description" value="${todo.description}"/>
							<input type="hidden" name="category" value="${todo.category}"/>
							<button type="submit" class="btn btn-danger">Delete</button>
						</form>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<a href="./protected.do" class="btn btn-primary">Add ToDo</a>
	</div>
<%@ include file="../common/footer.jspf" %>