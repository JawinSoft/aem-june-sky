package com.adobe.aem.ecart.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceResolverUtil {

	private static final Logger log = LoggerFactory.getLogger(ResourceResolverUtil.class);
	
	public static ResourceResolver getResourceResolver(ResourceResolverFactory factory) {
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "ECartSubService");
		
		try {
		  return	factory.getServiceResourceResolver(map);
		} catch (LoginException  e) {
			log.error("Got Exception In UserServiceImpl, Details::{} ", e);
		}
		
		throw new RuntimeException("UNable to create Resource Resolver..!"); 
	}
}
