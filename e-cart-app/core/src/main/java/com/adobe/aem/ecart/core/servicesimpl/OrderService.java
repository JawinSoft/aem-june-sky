package com.adobe.aem.ecart.core.servicesimpl;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.ecart.core.services.UserService;
import com.adobe.aem.ecart.core.util.ResourceResolverUtil;

@Component(immediate = true, name="Order Service", 
           service = OrderService.class   
		)
@Designate(ocd = UniversityConfig.class)
public class OrderService {
	
	Logger log = LoggerFactory.getLogger(OrderService.class);

	@Reference
	private UserService userService;
	
	@Reference
	private ResourceResolverFactory rrFactory;
	
	private String url;
	private String queryParamKey;
	private String queryParamValue;
	
	@Activate
	@Modified
	public void init(UniversityConfig config) {
	  String message=	userService.message();
	  url = config.url();
	  queryParamKey = config.queryParamName();
	  queryParamValue = config.queryParamValue();
	  log.info("Message :: "+ message);
	  log.info("Url :: {} , Query Param Key :: {}, Query Param value :: {}", url, queryParamKey, queryParamValue);
	}
	
	public void createChildNode() {
		ResourceResolver resolver =	ResourceResolverUtil.getResourceResolver(rrFactory);
	}
	
	
}
