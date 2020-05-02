package com.trevor.todo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import com.trevor.todo.ToDoService;

@WebServlet(urlPatterns = "/delete-todo.do")
public class DeleteToDoServlet extends HttpServlet {
	
	private ToDoService todoService = new ToDoService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newTodoDescription = StringEscapeUtils.escapeHtml4(request.getParameter("description"));
		String newTodoCategory = StringEscapeUtils.escapeHtml4(request.getParameter("category"));
		todoService.deleteTodo(new ToDo(newTodoDescription, newTodoCategory));
		response.sendRedirect("./list-todos.do");
	}
}