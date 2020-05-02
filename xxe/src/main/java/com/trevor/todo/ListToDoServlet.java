package com.trevor.todo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trevor.todo.ToDoService;

@WebServlet(urlPatterns = "/list-todos.do")
public class ListToDoServlet extends HttpServlet {
	
	private ToDoService todoService = new ToDoService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("todos", todoService.retrieveToDos());
		request.getRequestDispatcher("/WEB-INF/views/list-todos.jsp").forward(request, response);
	}
}