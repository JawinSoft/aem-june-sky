package com.adobe.aem.ecart.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(
		service = Servlet.class
		//,property = "sling.servlet.paths=/jawinsoft/hello-world"
		)
@SlingServletPaths(value = "/jawinsoft/message")
public class HelloWorldServlet extends SlingSafeMethodsServlet {

	@Override
	protected void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response)
			throws ServletException, IOException {
		String message = "Hello My Dear...! Welcome to AEM Servlet...";
		
		ResourceResolver rr = request.getResourceResolver();
		response.getWriter().write(message);
     }
	
}
