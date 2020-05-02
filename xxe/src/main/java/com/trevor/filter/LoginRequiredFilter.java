package com.trevor.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "*.do" })
public class LoginRequiredFilter implements Filter{
	
	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("", "/login.do")));

	@Override
	public void destroy() {	
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);
		String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

		boolean allowedPath = ALLOWED_PATHS.contains(path);
		
		// If there is not a valid session
		if(session == null) {
			// If we are going to /login.do - allow it to continue
			if(allowedPath) {
				chain.doFilter(servletRequest, servletResponse);
				return;
			// else we redirect to the login page
			} else {
				System.out.println("here");
				response.sendRedirect("./login.do");
				return;
			}
		}
		
		boolean loggedIn = (session.getAttribute("email") != null);
		
		if(loggedIn || allowedPath) {
			chain.doFilter(servletRequest, servletResponse);
		} else {
			System.out.println("here1");
			System.out.println(request.getRequestURI());
			response.sendRedirect("./login.do");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
