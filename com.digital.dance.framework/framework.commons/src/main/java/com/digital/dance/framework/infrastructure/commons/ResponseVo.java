package com.digital.dance.framework.infrastructure.commons;

import java.io.Serializable;

/**
 * 
 * @author liuxiny
 *
 */
public class ResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private String code;

	public ResponseVo(String pCode){
		this.code = pCode;
	}
	
	public ResponseVo(String pCode,String pMessage){
		this.msg = pMessage;
		this.code = pCode;
	}
	
	public ResponseVo(String pCode,String pMessage, Object pObject){
		this.msg = pMessage;
		this.code = pCode;
		this.result = pObject;
	}
	
	public ResponseVo(){ }

	public void setCode(String pCode) {
		this.code = pCode;
	}

	public String getCode() {
		return code;
	}

	public void setResult(Object pResult) {
		this.result = pResult;
	}
	
	public Object getResult() {
		return result;
	}

	public void setMsg(String pMessage) {
		this.msg = pMessage;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "ResponseVo [code=" + code + ", result=" + result + ", msg=" + msg +  "]";
	}

	private Object result;
}
