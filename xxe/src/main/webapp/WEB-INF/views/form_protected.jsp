<%@ include file="../common/header.jspf" %>
<%@ include file="../common/nav.jspf" %>
<div class="">
  <div class="container pt-5 pb-5" id="content">
    <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
    </c:if>
    <h1>Not Vulnerable</h1>
    <p>This form's endpoint is "/add-todo.do" and it has been configured so that it is protected from XXE attacks.</p>
    <p>It uses the guidance from OWASP found here: <a href="https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html#jaxp-documentbuilderfactory-saxparserfactory-and-dom4j">https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html</a>
    <hr/>
    <div>
      <div class="form-group">
        <label for="description">Description</label>
        <input type="text" class="form-control" name="description" id="description" placeholder="Enter Description">
      </div>
      <div class="form-group">
        <label for="category">Category</label>
        <input type="text" class="form-control" name="category" id="category" placeholder="Enter Category">
      </div>
      <button type="submit" class="btn btn-primary" onclick="submitXML()">Add ToDo</button>
      <hr/>
      <div class="form-group">
        <label for="data">Data</label>
        <textarea class="form-control" name="data" id="data" placeholder="XML to be submitted" rows="10"></textarea>
      </div>
    </div>
  </div>
</div>
<%@ include file="../common/footer.jspf" %>
