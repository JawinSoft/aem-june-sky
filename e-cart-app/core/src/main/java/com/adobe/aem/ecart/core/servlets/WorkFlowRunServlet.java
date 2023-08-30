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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;




@Component(service = Servlet.class, immediate = true)
@SlingServletPaths(value = "/bin/run-workflow")
public class WorkFlowRunServlet  extends SlingSafeMethodsServlet{

	Logger log = LoggerFactory.getLogger(WorkFlowRunServlet.class);
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		
		String payload = request.getParameter("path");
		ResourceResolver resolver =request.getResourceResolver();
		
		
		try {
			WorkflowSession session = resolver.adaptTo(WorkflowSession.class);
			WorkflowModel model = session.getModel("/var/workflow/models/page-version-wf");
			WorkflowData data = session.newWorkflowData("JCR_PATH", payload);
			
			session.startWorkflow(model, data);
 			
			response.getWriter().write("Running page-version- Work Flow...!");
					
		} catch (WorkflowException e) {
			log.error("Exception while Running page-version-wf ",e);
		}
		
	}
}
