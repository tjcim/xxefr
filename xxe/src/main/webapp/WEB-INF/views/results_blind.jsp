<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		
		<div class="container pt-5 pb-5" id="content">
			<h1>Malformed XML</h1>
			<h2>Errors processing the XML</h2>
			<ul>
				<c:if test="${xmlDescriptionError != null}">
					<li>${xmlDescriptionError}</li>
				</c:if>
				<c:if test="${xmlCategoryError != null}">
					<li>${xmlCategoryError}</li>
				</c:if>
				<c:if test="${xmlError != null}">
					<li>${xmlError}</li>
				</c:if>
			</ul>
		</div>