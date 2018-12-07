package com.digital.dance.framework.codis;

import org.apache.commons.logging.LogFactory;

public class Log {

	org.apache.commons.logging.Log log = null;

	@SuppressWarnings("rawtypes")
	public Log(Class clazz) {
		log = LogFactory.getLog(clazz);
	}
	
	public Log(String name) {
		log = LogFactory.getLog(name);
	}

	/**
	 * debug
	 * @param arg0
	 */
	public void debug(Object arg0) {
		log.debug(arg0);
	}

	/**
	 * debug
	 * @param arg0
	 * @param arg1
	 */
	public void debug(Object arg0, Throwable arg1) {
		log.debug(arg0, arg1);
	}

	/**
	 * info
	 * @param arg0
	 */
	public void info(Object arg0) {
		log.info(arg0);
	}

	/**
	 * info
	 * @param arg0
	 * @param arg1
	 */
	public void info(Object arg0, Throwable arg1) {
		log.info(arg0, arg1);
	}

	/**
	 * warn
	 * @param arg0
	 */
	public void warn(Object arg0) {
		log.warn(arg0);
	}

	/**
	 * warn
	 * @param arg0
	 * @param arg1
	 */
	public void warn(Object arg0, Throwable arg1) {
		log.warn(arg0, arg1);
	}

	/**
	 * error
	 * @param arg0
	 */
	public void error(Object arg0) {
		log.error(arg0);
	}

	/**
	 * error
	 * @param arg0
	 * @param arg1
	 */
	public void error(Object arg0, Throwable arg1) {
		log.error(arg0, arg1);
	}

	/**
	 * fatal
	 * @param arg0
	 */
	public void fatal(Object arg0) {
		log.fatal(arg0);
	}

	/**
	 * fatal
	 * @param arg0
	 * @param arg1
	 */
	public void fatal(Object arg0, Throwable arg1) {
		log.fatal(arg0, arg1);
	}
}
