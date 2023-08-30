package com.adobe.aem.ecart.core.servicesimpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.ecart.core.services.UserService;
import com.adobe.aem.ecart.core.util.ResourceResolverUtil;


@Component(
		name = "User Service",
		immediate = true,
		service = UserService.class
		)
@Designate(ocd = DemoServiceConfig.class)
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Reference
	private ResourceResolverFactory rrFactory;
	
	private static final String PATH = "/content/e-cart-app/us/en/articles/jcr:content";

	static {
		log.info("Static block in UserServiceImpl");
	}
	
	{
		log.info("Instance Block");
	}
	
	public UserServiceImpl() {
		log.info("This is UserServiceImpl...Constructor...!");
	}
	
	@Activate
	public void init() {
		log.info("Bundle is Getting Activated...!");	
	}
	
	@Deactivate
	public void destroy() {
		log.info("Bundle Is Getting Stopped");
	}
	
	@Modified
	public void update() {
		log.info("Bundle is getting updated...!");
	}
	
	@Override
	public String message() {
		ResourceResolver resolver =	ResourceResolverUtil.getResourceResolver(rrFactory);
		try {
		 Resource resource  = resolver.getResource(PATH);
		 ModifiableValueMap mvm = resource.adaptTo(ModifiableValueMap.class);
		 mvm.put("newProperty1", "Value1");
		 mvm.put("newProperty2", "Value2");
		 mvm.put("newProperty3", "Value3");
		 resolver.commit();
		 
		} catch (PersistenceException e) {
			log.error("Got Exception In UserServiceImpl, Details::{} ", e);
		}
		
		return "Hello My Dear...! Welcome To Aem OSGI Services...!";
	}

}
