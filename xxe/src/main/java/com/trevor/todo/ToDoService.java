package com.trevor.todo;

import java.util.ArrayList;
import java.util.List;

public class ToDoService {
	private static List<ToDo> todos = new ArrayList<ToDo>();
	static {
		todos.add(new ToDo("Define terms (entity, dtd, etc...)", "XXE"));
		todos.add(new ToDo("Denial of service (billion laughs)", "XXE"));
		todos.add(new ToDo("Learn XXE identification (include forms)", "XXE"));
		todos.add(new ToDo("Learn XXE in-band", "XXE"));
		todos.add(new ToDo("Learn XXE out of band", "XXE"));
		todos.add(new ToDo("Error based XXE", "XXE"));
		todos.add(new ToDo("XXE SSRF (AWS, ETC, etc...)", "XXE"));
		todos.add(new ToDo("Learn XXE and port scanning", "XXE"));
		todos.add(new ToDo("XXE via image upload", "XXE"));
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
