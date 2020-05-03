package com.trevor.todo;

import java.util.ArrayList;
import java.util.List;

public class ToDoService {
	private static List<ToDo> todos = new ArrayList<ToDo>();
	static {
		todos.add(new ToDo("Define terms (entity, dtd, etc...)", "XXE"));
		todos.add(new ToDo("Denial of service (billion laughs)", "XXE"));
		todos.add(new ToDo("Learn XXE identification", "XXE"));
		todos.add(new ToDo("Learn XXE - basic", "XXE"));
		todos.add(new ToDo("Learn XXE - blind", "XXE"));
		todos.add(new ToDo("Error based XXE", "XXE"));
		todos.add(new ToDo("XXE SSRF", "XXE"));
		todos.add(new ToDo("Learn XXE and port scanning", "XXE"));
	}
	
	public List<ToDo> retrieveToDos() {
		return todos;
	}
	
	public void addTodo(ToDo todo) {
		todos.add(todo);
	}
	public void deleteTodo(ToDo todo) {
		todos.remove(todo);
	}
}
