package com.trevor.todo;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TodoXMLHandler extends DefaultHandler {
	private static final String DESCRIPTION = "description";
	private static final String CATEGORY = "category";
	
	private ToDo newTodo;
	private String elementValue;
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		elementValue = new String(ch, start, length);
	}
	
	@Override
	public void startDocument() throws SAXException {
		newTodo = new ToDo();
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case DESCRIPTION:
			newTodo.setDescription(elementValue);
			break;
		case CATEGORY:
			newTodo.setCategory(elementValue);
			break;
		}
	}
	
	public ToDo getTodo() {
		return newTodo;
	}
}