package com.adobe.aem.ecart.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.ecart.core.services.UserService;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class }, adapters = ArticleDetails.class)
public class ArticleDetails {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleDetails.class);

	@ValueMapValue
    private String articledesc;
	
	@OSGiService
	private UserService  userService;
	
	
	@ValueMapValue(name = "sling:resourceType", injectionStrategy = InjectionStrategy.OPTIONAL)
	@Default(values = "e-cart-app/components/article-details")
	private String slingResourceType;
	
	@ChildResource
	@Optional
	private List<RelatedArticles> relatedarticles;
	
	@PostConstruct
	public void init() {
		if(articledesc.length() > 50) {
			articledesc = articledesc.substring(0, 49);
		}
	  String message =	userService.message();
	  log.info("Message from User Service ::{}", message);
	}

	public String getArticledesc() {
		return articledesc;
	}

	public void setArticledesc(String articledesc) {
		this.articledesc = articledesc;
	}
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getSlingResourceType() {
		return slingResourceType;
	}

	public void setSlingResourceType(String slingResourceType) {
		this.slingResourceType = slingResourceType;
	}

	public List<RelatedArticles> getRelatedarticles() {
		return relatedarticles;
	}

	public void setRelatedarticles(List<RelatedArticles> relatedarticles) {
		this.relatedarticles = relatedarticles;
	}

	
	
}
