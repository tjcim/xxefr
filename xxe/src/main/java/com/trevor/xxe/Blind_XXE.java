package com.trevor.xxe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;

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
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.commons.text.StringEscapeUtils;

import com.trevor.todo.ToDo;
import com.trevor.todo.ToDoService;
import com.trevor.todo.TodoXMLHandler;

@WebServlet(urlPatterns = "/blind-xxe.do")
public class Blind_XXE extends HttpServlet {
	
	private ToDoService todoService = new ToDoService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/form_blind_xxe.jsp").forward(request, response);
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
			/* report an error */ 
		}
		//String xmlString = jb.toString().replace(System.getProperty("line.separator"),  " ");
		String xmlString = jb.toString();
		System.out.println(xmlString);
		InputSource inputSource = new InputSource(new StringReader(xmlString));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			//doc = builder.parse(xmlString);
			doc = builder.parse(inputSource);
		} catch (SAXException e) {
			String message = e.getMessage();
			e.printStackTrace();
			request.setAttribute("xmlError", message);
			request.getRequestDispatcher("/WEB-INF/views/results_blind.jsp").forward(request, response);
			return;
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			String message = e.getMessage();
			if(message.contains("http://")) {
				message = "Error sending data";
			}
			e.printStackTrace();
			request.setAttribute("xmlError", message);
			request.getRequestDispatcher("/WEB-INF/views/results_blind.jsp").forward(request, response);
			return;
		}
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		
		boolean errorFound = false;
		String description = null;
		try {
			description = StringEscapeUtils.escapeHtml4(root.getElementsByTagName("description").item(0).getTextContent());
		} catch (NullPointerException e) {
			request.setAttribute("xmlDescriptionError", "No description element was found.");
			errorFound = true;
		}
		
		String category = null;
		try {
			category = StringEscapeUtils.escapeHtml4(root.getElementsByTagName("category").item(0).getTextContent());
		} catch (NullPointerException e) {
			request.setAttribute("xmlCategoryError", "No category element was found.");
			errorFound = true;
		}
		
		if(!errorFound) {
			ToDo newTodo = null;
			newTodo = new ToDo(description, category);
			todoService.addTodo(newTodo);
		} else {
			request.getRequestDispatcher("/WEB-INF/views/results_blind.jsp").forward(request, response);
		}
	}
}
