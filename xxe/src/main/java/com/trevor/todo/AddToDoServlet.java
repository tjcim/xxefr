package com.trevor.todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.trevor.todo.ToDoService;

@WebServlet(urlPatterns = "/protected.do")
public class AddToDoServlet extends HttpServlet {
	
	private ToDoService todoService = new ToDoService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/form_protected.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			/* report an error */ }
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		InputSource inputSource = new InputSource(new StringReader(jb.toString()));
		Document doc = null;
		try {
			doc = builder.parse(inputSource);
		} catch (SAXException e) {
			request.getRequestDispatcher("/WEB-INF/views/results_protected.jsp").forward(request, response);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		
		String description = null;
		try {
			description = StringEscapeUtils.escapeHtml4(root.getElementsByTagName("description").item(0).getTextContent());
		} catch (NullPointerException e) {
			System.out.println("null description");
		}
		
		String category = null;
		try {
			category = StringEscapeUtils.escapeHtml4(root.getElementsByTagName("category").item(0).getTextContent());
		} catch (NullPointerException e) {
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