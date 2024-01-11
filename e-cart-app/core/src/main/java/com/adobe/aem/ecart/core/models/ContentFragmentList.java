package com.mysite.core.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.mysite.core.beans.ContentFragmentData;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentFragmentList {

	private static final Logger logger = LoggerFactory.getLogger(ContentFragmentList.class);

	@SlingObject
	private ResourceResolver resourceResolver;

	@ValueMapValue
	@Default(values = "/content/dam/mysite/content-fragment/en")
	private String path;

	@ValueMapValue
	@Default(values = "firstName")
	private String property;

	private List<ContentFragmentData> cfdList;

	public String getPath() {
		return path;
	}

	public String getProperty() {
		return property;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	@PostConstruct
	public void init() {
		logger.info("Path- {}", path);
		logger.info("Property Value- {}",property);
		Resource resource = resourceResolver.getResource("/content/dam/mysite/content-fragment/en");
		Iterator<Resource> itr = resource.listChildren();
		List<ContentFragment> cfList = new ArrayList<>();

		while (itr.hasNext()) {
			Resource child = itr.next();
			ContentFragment cf = child.adaptTo(ContentFragment.class);
			if (null != cf) {
				cfList.add(cf);
			}
		}

		Comparator<ContentFragment> sort = (cf1, cf2) -> cf1.getElement(property).getValue().getValue(String.class)
				.compareTo(cf2.getElement(property).getValue().getValue(String.class));

		List<ContentFragment> cfNewList = cfList.stream().sorted(sort).collect(Collectors.toList());

		List<ContentFragmentData> cfdList = 
				cfNewList.stream().map(cf -> {
		  ContentFragmentData cfd = new ContentFragmentData();
		  Iterator<ContentElement> itrCE	= cf.getElements();
		  Map<String, String> data = new LinkedHashMap<>();
		  while(itrCE.hasNext()) {
			  ContentElement ce =  itrCE.next();
			  data.put(ce.getTitle(), ce.getValue().getValue(String.class));
			  logger.info(ce.getName()+ "  " +ce.getValue().getValue(String.class));
		  }
		  logger.info("Data {}", data);
		  cfd.setData(data);
		  return cfd;
		}).collect(Collectors.toList());
		
		this.cfdList = cfdList;
	}

	public List<ContentFragmentData> getCfdList() {
		return cfdList;
	}
}

