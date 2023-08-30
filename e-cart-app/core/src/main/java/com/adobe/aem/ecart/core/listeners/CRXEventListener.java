package com.adobe.aem.ecart.core.listeners;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service= EventListener.class, immediate = true)
public class CRXEventListener implements EventListener {
	
	Logger log = LoggerFactory.getLogger(CRXEventListener.class);
	
	
	@Reference
	SlingRepository repository;
	
	@Activate
	public void init() {
		String[] nodeType = {"cq:PageContent"};
		try {
			Session session = repository.loginService("ECartSubService", null);
			session.getWorkspace().getObservationManager()
			.addEventListener(
					this, //Event Listener
					Event.PROPERTY_ADDED| Event.NODE_ADDED, // event type
					"/content/e-cart-app/us/en/articles", //path
					true, //isDeep
					null,  //UUID FIlter
					nodeType,
					true  //noLocal
					);
			log.info("CRXEventListener Activated Successfully...!");
			
		} catch (LoginException e) {
			log.error("Exception in CRXEventListener ", e);
		} catch (RepositoryException e) {
			log.error("Exception in CRXEventListener ", e);
		}
		
		
	}

	@Override
	public void onEvent(EventIterator events) {
		while(events.hasNext()) {
			try {
				log.info("EventListener... Path {}", events.nextEvent().getPath());
			} catch (RepositoryException e) {
				log.error("Exception in CRXEventListener ", e);
			}
		}
	}

}
