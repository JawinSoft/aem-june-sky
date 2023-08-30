package com.adobe.aem.ecart.core.workflow;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;



@Component(
		 service = WorkflowProcess.class,
		 immediate = true,
		 property = {
				 "process.label=Custom WorkFlow Process",
				 Constants.SERVICE_VENDOR + "=Adobe",
				 Constants.SERVICE_DESCRIPTION+ "=Custom Workflow Process"
		 }
		)
public class CustomWorkFlowProcess implements WorkflowProcess{
	
	Logger log = LoggerFactory.getLogger(CustomWorkFlowProcess.class);

	@Override
	public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap mdmap) throws WorkflowException {
		log.info("#################################################################################");
		String payload = item.getWorkflowData().getPayload().toString();
		log.info("CustomWorkFlowProcess:: PayLoad {}", payload);///content/e-cart-app/us/en/articles/spy-movie
		
		Session session = wfsession.adaptTo(Session.class);
		try {
			Node node = (Node)session.getItem(payload+"/jcr:content");
			
			
		 String[] arguments=	mdmap.get("PROCESS_ARGS", "string").toString().split(",");
			
		 for(String argument : arguments) {
			 String[] argArr = argument.split(":");
			 node.setProperty(argArr[0], argArr[1]);
			 
		 }
		 log.info("SuccessFully Completed .... CustomWorkFlowProcess:: ");	
		 
		} catch (Exception e) {
			log.error("Exception in CustomWorkFlowProcess ",e);
		}
		
		
		
	}

}
