package com.combined.core.servlets;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

@Component(service = Servlet.class, property = {
		Constants.SERVICE_DESCRIPTION + "=Static form servlet for email service.",
		SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/combined/agent-page-migrate" })
public class AgentPageMigrationServlet extends SlingSafeMethodsServlet {

	private static final String BG_IMAGE_PATH = "bgImagePath";

	private static final String PHONE = "phone";

	private static final String TITLE = "title";

	private static final String NAME = "name";

	private static final String DESCRIPTION = "description";

	private static final String FILE_REFERENCE = "fileReference";

	private static final Logger logger = LoggerFactory.getLogger(AgentPageMigrationServlet.class);

	private static final String JCR_CONTENT = "/jcr:content";

	private static final String CHILD_PAGE_NAME = "/Agent-Page";

	private static final String ROOT_PATH = "/content/chubb-sites/combined-insurance/us/en/microsite/combined/us/en/agents/";

	private static final String JCR_TITLE = "jcr:title";
	
	private static final String TEMPLATE_PAGE_NAME = "kurt-preddie";
	
	private static final String OPPORTUNITY_PAGE_NAME = "/be-part-of-the-opportunity";
	
	private static final String NEW_PAGE_PATH  = "NEW_PAGE_PATH";
	
	private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
	
	private static final String GIVE_US_A_CALL = "Give us a call:";
	
	private static final String EMAIL = "Email";
	
	
	private static final String  NEW_PAGE = "newPageName";

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		String source = request.getParameter("source");
		ResourceResolver resourceResolver = request.getResourceResolver();
		Map<String, String> dataMap = new HashMap<>();

		try {
			createAgentPage(ROOT_PATH + TEMPLATE_PAGE_NAME, request.getResourceResolver(), source,dataMap);
			//opportunity PAGE
			createOppertunityPage(ROOT_PATH + TEMPLATE_PAGE_NAME, resourceResolver, OPPORTUNITY_PAGE_NAME, dataMap);
			
			updateAgentDetails(source,resourceResolver,dataMap);
			updateOpportunityDetails(resourceResolver, dataMap);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		response.getWriter().write("Done.");
	}
	
	private void updateOpportunityDetails(ResourceResolver resourceResolver,Map<String, String> dataMap) throws PathNotFoundException, RepositoryException { 
		String path  = dataMap.get(NEW_PAGE_PATH) + OPPORTUNITY_PAGE_NAME+ JCR_CONTENT+ "/root/container/container";
		Resource resource = resourceResolver.getResource(path);
		Resource sectionChild = resource.listChildren().next();
		String peopleCardCopyPath = sectionChild.getPath()+ "/container/people_container/people_card_copy";
		Session session = resourceResolver.adaptTo(Session.class);
		Node contentNode = session.getNode(peopleCardCopyPath);
		if (Objects.nonNull(contentNode)) {
			contentNode.setProperty(NAME, dataMap.get(NAME));
			contentNode.setProperty(TITLE, dataMap.get(TITLE));
			contentNode.setProperty(BG_IMAGE_PATH, dataMap.get(FILE_REFERENCE));
			//contentNode.setProperty(DESCRIPTION, dataMap.get(DESCRIPTION));
			session.save();
		}
	}
	
	private void updateAgentDetails(String source,ResourceResolver resourceResolver,Map<String, String> dataMap) throws PathNotFoundException, RepositoryException {
		String path = ROOT_PATH+source+CHILD_PAGE_NAME+JCR_CONTENT+"/root";
		Resource resource = resourceResolver.getResource(path);
		Iterator<Resource> children = resource.listChildren();
		
		while (children.hasNext()) {
		    Resource child = children.next();
		    String resourceType = child.getResourceType();
             if(resourceType.endsWith("page-authorable/c-hero")) {
            	 getNameAndTitle(child, dataMap);
             }
             
             if(resourceType.endsWith("c-multi-column-control")) {
            	 String descriptionPath = child.getPath()+"/par_0/c_intro_text";
            	 Resource introTextResource = resourceResolver.getResource(descriptionPath);
            	 ValueMap map1 = introTextResource.adaptTo(ValueMap.class);
         		 String descritpion = map1.get("text", String.class);
         		processDescription(descritpion, dataMap);
         		 
         		String imageParsysPath = child.getPath()+"/par_1/c_custom_container/par";
         		Resource imageParResource = resourceResolver.getResource(imageParsysPath);
         		Resource imageResource = imageParResource.listChildren().next();
         		ValueMap map2 = imageResource.adaptTo(ValueMap.class);
        		String imagePath = map2.get(FILE_REFERENCE, String.class);
        		dataMap.put(FILE_REFERENCE, imagePath);
             }
		   }
		   
			// setting name and title of agent
			String newPagePath = dataMap.get(NEW_PAGE_PATH);
			newPagePath = newPagePath + JCR_CONTENT + "/root/container/container";
			resource = resourceResolver.getResource(newPagePath);
			Resource sectionChild = resource.listChildren().next();

			String sectionPath = sectionChild.getPath();
			String peopleCardCopyPath = sectionPath + "/container/people_container/people_card_copy";
			Session session = resourceResolver.adaptTo(Session.class);
			Node contentNode = session.getNode(peopleCardCopyPath);
			if (Objects.nonNull(contentNode)) {
				contentNode.setProperty(NAME, dataMap.get(NAME));
				contentNode.setProperty(TITLE, dataMap.get(TITLE));
				contentNode.setProperty(BG_IMAGE_PATH, dataMap.get(FILE_REFERENCE));
				contentNode.setProperty(DESCRIPTION, dataMap.get(DESCRIPTION));
				session.save();
			}
			
			String getInTouchCardPath = sectionPath + "/container/people_container/get_in_touch_card";
			 session = resourceResolver.adaptTo(Session.class);
			 contentNode = session.getNode(getInTouchCardPath);
			if (Objects.nonNull(contentNode)) {
				contentNode.setProperty(EMAIL.toLowerCase(), dataMap.get(EMAIL));
				contentNode.setProperty(PHONE, dataMap.get(PHONE));
				contentNode.setProperty("ctaLink",dataMap.get("oppertunityPagePath")+".html");
				session.save();
			}
	}
	
	private void processDescription(String description, Map<String, String> dataMap) {
		String output = removeTags(description);
		String onlyDesc = output.substring(0,output.indexOf(GIVE_US_A_CALL));
		String mobileNumber = output.substring(output.indexOf(GIVE_US_A_CALL), output.indexOf(EMAIL));
		mobileNumber =mobileNumber.replaceAll(GIVE_US_A_CALL, "").replaceAll(" ","");
		String emailId = output.substring(output.indexOf(mobileNumber+EMAIL));
		emailId = emailId.replaceAll(mobileNumber+EMAIL+" : ", "");
		 dataMap.put(DESCRIPTION, "<p>"+onlyDesc+"</p>");
		 dataMap.put(PHONE, mobileNumber);
		 dataMap.put(EMAIL, emailId);
	}
	
	private  String removeTags(String string) {
	    if (string == null || string.length() == 0) {
	        return string;
	    }

	    Matcher m = REMOVE_TAGS.matcher(string);
	    return m.replaceAll("");
	}
	
	private void getNameAndTitle(Resource heroResource,Map<String, String> dataMap) {
		ValueMap map = heroResource.adaptTo(ValueMap.class);
		String title = map.get("eyebrowText", String.class);
		String name= map.get("heroTitle", String.class);
		dataMap.put(NAME, name);
		dataMap.put(TITLE, title);
	}
	
	private void createOppertunityPage(String sourcePagePath, ResourceResolver resourceResolver, String newPageName,Map<String, String> dataMap) {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		Page page = pageManager.getPage(sourcePagePath+newPageName);
		try {
			String pagePath = dataMap.get(NEW_PAGE_PATH) + newPageName;
			dataMap.put("oppertunityPagePath", pagePath);
			pageManager.copy(page, pagePath, null, true, false);
			
		} catch (WCMException e) {
			logger.error("Error in Copying Oppertunity Page ", e.getMessage(), e);
		}
	}

	private String createAgentPage(String sourcePagePath, ResourceResolver resourceResolver, String source,Map<String, String> dataMap ) throws PathNotFoundException, RepositoryException {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		Page page = pageManager.getPage(sourcePagePath);
		try {
			String pageTitle = getPageName(source, resourceResolver);
			String newPageName = pageTitle.replaceAll(" ", "-").toLowerCase();
			String pagePath = ROOT_PATH + newPageName;
			dataMap.put(NEW_PAGE_PATH, pagePath);
			dataMap.put(NEW_PAGE, newPageName);
			
			pageManager.copy(page, pagePath, null, true, false);
			updatePageProperties(pagePath, resourceResolver, pageTitle);
			
			return newPageName + "Page Created Successfully..";
		} catch (WCMException e) {
			logger.error("Error in Copying Page ", e.getMessage(), e);
		}
		return "Page Creation Failed.";
	}

	private String getPageName(String source, ResourceResolver resourceResolver) {
		Resource resource = resourceResolver.getResource(ROOT_PATH + source + CHILD_PAGE_NAME + JCR_CONTENT);
		ValueMap properties = resource.adaptTo(ValueMap.class);
		return properties.get(JCR_TITLE, String.class);
	}

	private void updatePageProperties(String path, ResourceResolver resourceResolver, String name) throws PathNotFoundException, RepositoryException {
		Session session = resourceResolver.adaptTo(Session.class);
		Node contentNode = session.getNode(path+JCR_CONTENT);
	    if (Objects.nonNull(contentNode)) {
	        contentNode.setProperty(JCR_TITLE, name);
	        contentNode.setProperty("navTitle", name);
	        contentNode.setProperty("pageTitle", name);
	        session.save();
	    }
	}
}
