package com.digital.dance.framework.infrastructure.commons;

//import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author liuxiny
 *
 */
public class Log {

	Logger log = null;
//	org.apache.commons.logging.Log log = null;

	@SuppressWarnings("rawtypes")
	public Log(Class clazz) {
		log = LoggerFactory.getLogger(clazz);
	}
	
	public Log(String name) {
		log = LoggerFactory.getLogger(name);
	}

	/**
	 * debug
	 * @param arg0
	 */
	public void debug(Object arg0) {
		log.debug( GsonUtils.toJsonStr(arg0) );
	}

	/**
	 * debug
	 * @param arg0
	 * @param arg1
	 */
	public void debug(Object arg0, Throwable arg1) {
		log.debug(GsonUtils.toJsonStr(arg0), arg1);
	}

	/**
	 * info
	 * @param arg0
	 */
	public void info(Object arg0) {
		log.info(GsonUtils.toJsonStr(arg0));
	}

	/**
	 * info
	 * @param arg0
	 * @param arg1
	 */
	public void info(Object arg0, Throwable arg1) {
		log.info(GsonUtils.toJsonStr(arg0), arg1);
	}

	/**
	 * warn
	 * @param arg0
	 */
	public void warn(Object arg0) {
		log.warn(GsonUtils.toJsonStr(arg0));
	}

	/**
	 * warn
	 * @param arg0
	 * @param arg1
	 */
	public void warn(Object arg0, Throwable arg1) {
		log.warn(GsonUtils.toJsonStr(arg0), arg1);
	}

	/**
	 * error
	 * @param arg0
	 */
	public void error(Object arg0) {
		log.error(GsonUtils.toJsonStr(arg0));
	}

	/**
	 * error
	 * @param arg0
	 * @param arg1
	 */
	public void error(Object arg0, Throwable arg1) {
		log.error(GsonUtils.toJsonStr(arg0), arg1);
	}

	/**
	 * fatal
	 * @param arg0
	 */
	public void fatal(Object arg0) {
		log.error(GsonUtils.toJsonStr(arg0));
	}

	/**
	 * fatal
	 * @param arg0
	 * @param arg1
	 */
	public void fatal(Object arg0, Throwable arg1) {
		log.error(GsonUtils.toJsonStr(arg0), arg1);
	}
	
	public Boolean isDebugEnabled(){
		return log.isDebugEnabled();
	}
	
	public Boolean isErrorEnabled(){
		return log.isErrorEnabled();
	}
	
	public Boolean isWarnEnabled(){
		return log.isWarnEnabled();
	}	

	public Boolean isInfoEnabled(){
		return log.isInfoEnabled();
	}

	public Boolean isFatalEnabled(){
		return log.isErrorEnabled();
	}	

	public Boolean isTraceEnabled(){
		return log.isTraceEnabled();
	}
}
