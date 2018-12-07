package com.digital.dance.framework.infrastructure.commons;

/**
 * 
 * @author liuxiny
 *
 */
public class BeanUtils {

	/**
	 * copy properties
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object sourceObject, Object targetObject){
		org.springframework.beans.BeanUtils.copyProperties(sourceObject, targetObject);
	}
}
