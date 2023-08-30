package com.adobe.aem.ecart.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd=DemoSchduler.Config.class)
public class DemoSchduler implements Runnable{
	
	Logger log = LoggerFactory.getLogger(DemoSchduler.class);
	
	@Reference
	private Scheduler scheduler;

	@Override
	public void run() {
		log.info("This statement will prove that, We are running the schduler...! {}", Thread.currentThread().getName());
	}
	
	@Activate
	public void active(Config config) {
		configurareScheduler(config);
	}
	
	@Modified
	public void modify(Config config) {
		configurareScheduler(config);
	}
	
	
	@Deactivate
	public void deactive(Config config) {
		scheduler.unschedule(config.scheduler_name());
	}
	
	
	private void configurareScheduler(Config config) {
		String name = config.scheduler_name();
		log.info("Schduler Name {}, Enabled:{}", name, config.enable());
		if(config.enable()) {
			ScheduleOptions options = scheduler.EXPR(config.scheduler_expression());		
			options.name(name);
			options.canRunConcurrently(config.concurrent_scheduler());
			scheduler.schedule(this, options);
		}{
			scheduler.unschedule(name);
		}
		
	}
	
	
	@ObjectClassDefinition(name ="Demo Scheduler", description = "A Demo Scheduler to present How schduler works in AEM")
	public @interface Config{
		
		@AttributeDefinition(name ="SchedulerName",type = AttributeType.STRING)
		public String scheduler_name() default "DemoScheduler";
		
		@AttributeDefinition(name ="SchedulerExpression", type = AttributeType.STRING)
		public String scheduler_expression() default "*/60 * * * * ?";
		
		@AttributeDefinition(name ="EnableScheduler", type= AttributeType.BOOLEAN)
		public boolean enable() default true;
		
		@AttributeDefinition(name ="RunParally", type = AttributeType.BOOLEAN)
		public boolean concurrent_scheduler() default false;
	}
}
