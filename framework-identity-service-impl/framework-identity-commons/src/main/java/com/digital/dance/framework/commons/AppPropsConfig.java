package com.digital.dance.framework.commons;

import java.util.Properties;

import org.springframework.core.io.Resource;
import java.util.Map.Entry;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 
 * @author liuxiny
 *
 */
public class AppPropsConfig {

	private static Log logger = new Log(AppPropsConfig.class);
	
	public static String APP_PROPERTIES_CFG = "APP_PROPERTIES_CFG";
	public static String cfgFileName = "system";
	public static String cfgFileSuffix = ".properties";

	private static Properties cfgProperties = new Properties();

	static {
		loadCfgProperties();
	}

	private static synchronized void loadCfgProperties() {
		boolean propertiesFileNotFound = true;

		try {
			if (cfgProperties == null || cfgProperties.isEmpty()) {
				String envF = System.getenv(APP_PROPERTIES_CFG);
				InputStream iStream = AppPropsConfig.class.getClassLoader().getResourceAsStream(
						envF != null ? (cfgFileName + envF + cfgFileSuffix) : (cfgFileName + cfgFileSuffix));
				if (iStream != null) {
					propertiesFileNotFound = false;

					cfgProperties.load(iStream);

				} /* */
				Resource[] propertyResources = AppPropsConfigLocation.getResources();
				if (propertyResources != null && propertyResources.length > 0) {
					propertiesFileNotFound = false;

					for (Resource resItem : propertyResources) {

						Properties propertiesItem = new Properties();
						if (resItem.getInputStream() == null)
							continue;
						propertiesItem.load(resItem.getInputStream());

						for (Entry e : propertiesItem.entrySet()) {
							if (cfgProperties.containsKey(e.getKey()))
								continue;
							cfgProperties.put(e.getKey(), e.getValue());
						}
					}

				}

				if (propertiesFileNotFound) {
					logger.error("AppPropsConfig.loadCfgProperties FileNotFoundException");
				}
			}

		} catch (FileNotFoundException e) {
			logger.error("AppPropsConfig.loadCfgProperties FileNotFoundException", e);
			// e.printStackTrace();
		} catch (IOException e) {
			logger.error("AppPropsConfig.loadCfgProperties IOException", e);
		}

	}

	public static synchronized void initCfgProperties() {
		if (cfgProperties == null || cfgProperties.isEmpty()) {
			loadCfgProperties();
		}
	}

	public static Properties getProperties() {
		initCfgProperties();
		return cfgProperties;
	}

	public static Boolean containsPropertyKey(String propertyKey) {

		if (StringTools.isEmpty(propertyKey)) {
			return false;
		}
		if (cfgProperties == null || cfgProperties.isEmpty()) {
			loadCfgProperties();
		}
		if (cfgProperties == null || cfgProperties.isEmpty()) {
			return false;
		}
		return cfgProperties.containsKey(propertyKey);
	}

	public static String getPropertyValue(String propertyKey) {
		if (cfgProperties == null || cfgProperties.isEmpty()) {
			loadCfgProperties();
		}
		if (cfgProperties == null || cfgProperties.isEmpty()) {
			return null;
		}
		return (String) cfgProperties.get(propertyKey);
	}

	public static int getPropertyValue(String propertyKey, int defaultV) {
		return getPropertyValue(propertyKey) != null ? Integer.parseInt(getPropertyValue(propertyKey)) : defaultV;
	}

	public static String getPropertyValue(String propertyKey, String defaultV) {
		return getPropertyValue(propertyKey) != null ? getPropertyValue(propertyKey) : defaultV;
	}

}
