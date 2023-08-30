package com.adobe.aem.ecart.core.listeners;

import java.util.Iterator;
import java.util.UUID;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.ecart.core.util.ResourceResolverUtil;
import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;


@Component(service = EventHandler.class, immediate = true, 
          property = {EventConstants.EVENT_TOPIC+"="+PageEvent.EVENT_TOPIC}		
		)
public class PageCreateEventHandler implements EventHandler{
	
	Logger log = LoggerFactory.getLogger(PageCreateEventHandler.class);
	
	@Reference
	private ResourceResolverFactory factory;

	@Override
	public void handleEvent(Event event) {
		log.info("Page Event Happend...!");
		Iterator<PageModification> itr= PageEvent.fromEvent(event).getModifications();
		while(itr.hasNext()) {
			PageModification pm = itr.next();
			if(PageModification.ModificationType.CREATED.equals(pm.getType())) {
				String path = pm.getPath();
				ResourceResolver resolver = ResourceResolverUtil.getResourceResolver(factory);
				Resource resource = resolver.getResource(path+"/jcr:content");
				ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
				map.put("PageId", UUID.randomUUID().toString());
				log.info("Page Id is Added for {}", path);
				try {
					resolver.commit();
				} catch (PersistenceException e) {
					log.error("Exception while updating the Page id...!- ", e);
				}
			}
		}
		
		
	}

}
