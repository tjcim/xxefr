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

@WebServlet(urlPatterns = "/billion-laughs.do")
public class Billion_Laughs extends HttpServlet {
	
	private ToDoService todoService = new ToDoService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/form_billion_laughs.jsp").forward(request, response);
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
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		
		String description = null;
		try {
			description = StringEscapeUtils.escapeHtml4(root.getElementsByTagName("description").item(0).getTextContent());
		} catch (NullPointerException e) {
			String xml = root.getFirstChild().getTextContent();
			request.setAttribute("xml", xml);
			request.getRequestDispatcher("/WEB-INF/views/results_billion_laughs.jsp").forward(request, response);
			return;
		}
		
		String category = null;
		try {
			category = StringEscapeUtils.escapeHtml4(root.getElementsByTagName("category").item(0).getTextContent());
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
