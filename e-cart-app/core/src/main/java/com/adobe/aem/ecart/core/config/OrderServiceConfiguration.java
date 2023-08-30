package com.adobe.aem.ecart.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


//Fully Qualified Name -> com.adobe.aem.ecart.core.config.OrderServiceConfiguration
@ObjectClassDefinition(name = "OrderServiceConfiguration", pid = "$")
public @interface OrderServiceConfiguration {

	@AttributeDefinition(
			defaultValue = "http://orderservice.com/orders",
			type =  AttributeType.STRING,
			required = true,
			name = "Order Service URL"
			)
	public String url() default "http://orderservice.com/orders";
}
