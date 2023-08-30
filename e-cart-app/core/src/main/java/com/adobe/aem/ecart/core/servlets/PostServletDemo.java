package com.adobe.aem.ecart.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class)
//@SlingServletPaths(value ="/jawinsoft/post")
@SlingServletResourceTypes(
		resourceTypes = "e-cart-app/components/article-details", 
		 methods =  HttpConstants.METHOD_POST ,
		 selectors = "test",
		 extensions = "html")
public class PostServletDemo extends SlingAllMethodsServlet{

	
	@Override
	protected void doPost( SlingHttpServletRequest request,  SlingHttpServletResponse response)
			throws ServletException, IOException {
	  String userName=	request.getParameter("userName");
	  String password = request.getParameter("password");
	  
	  Map<String, String> map = new HashMap<>();
	  map.put("UserName", userName);
	  map.put("Password", password);
	  
	  String json = new ObjectMapper().writeValueAsString(map);
	  
	  response.getWriter().write(json);
	}
}
