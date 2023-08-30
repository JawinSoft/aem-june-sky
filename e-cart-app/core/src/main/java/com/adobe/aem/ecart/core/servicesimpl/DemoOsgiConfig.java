package com.adobe.aem.ecart.core.servicesimpl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;



@ObjectClassDefinition(name = "DemoOsgiConfig",pid="$", description = "Demo For OSGI Config")
public @interface DemoOsgiConfig {
	
	@AttributeDefinition(
			name = "url",
			defaultValue = "https://universities.hipolabs.com/search?name=middle",
			type = AttributeType.STRING,
			required = true
			)
	String url() default "https://universities.hipolabs.com/search?name=middle";
	
	
	@AttributeDefinition(
			name = "queryParamKey",
			defaultValue = "name",
			type = AttributeType.STRING,
			required = true
			)
	String queryParamName() default "name";
	
	
	@AttributeDefinition(
			name = "queryParamValue",
			defaultValue = "middle",
			type = AttributeType.STRING,
			required = true
			)
	String queryParamValue() default "middle";

}
