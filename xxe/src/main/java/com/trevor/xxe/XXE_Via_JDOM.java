package com.trevor.xxe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.trevor.todo.ToDo;
import com.trevor.todo.ToDoService;
import com.trevor.todo.TodoXMLHandler;

@WebServlet(urlPatterns = "/xxe-jdom.do")
public class XXE_Via_JDOM extends HttpServlet {

	private ToDoService todoService = new ToDoService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/add-todo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			/* report an error */ }
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputSource inputSource = new InputSource(new StringReader(jb.toString()));
		Document doc = null;
		try {
			doc = builder.parse(inputSource);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("before");
		doc.getDocumentElement().normalize();
		System.out.println("after");
		Element root = doc.getDocumentElement();
		
		String description = null;
		try {
			description = root.getElementsByTagName("description").item(0).getTextContent();
		} catch (NullPointerException e) {
			System.out.println("null description");
		}
		
		String category = null;
		try {
			category = root.getElementsByTagName("category").item(0).getTextContent();
		} catch (NullPointerException e) {
			System.out.println("null category");
		}
		
		ToDo newTodo = null;
		try {
			newTodo = new ToDo(description, category);
			todoService.addTodo(newTodo);
		} catch (NullPointerException e) {
			System.out.println("null create todo");
		}
	}
}
