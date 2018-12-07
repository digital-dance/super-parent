package com.digital.dance.framework.infrastructure.commons;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

/**
 * 
 * @author liuxiny
 *
 */
public class AppPropsConfigLocation extends PropertyPlaceholderConfigurer {

	public static Resource[] resources;
	
	@Override
	public void setLocations(Resource... locations) {
		super.setLocations(locations);
		resources = locations;
	}

	/**
	 * @return the resources
	 */
	public static Resource[] getResources() {
		return resources;
	}

}
