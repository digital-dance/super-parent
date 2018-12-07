package com.digital.dance.framework.infrastructure.commons;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import java.io.InputStreamReader;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;

/**
 * 
 * @author liuxiny
 *
 */
public class HttpClientUtil {
	public static final String CONTENT_T = "content_type";
	public static final String JSON_STR = "jsonString";
	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";
	
	public static final int TIME_OUT = 50000;
	
	  static MultiThreadedHttpConnectionManager conManager = new MultiThreadedHttpConnectionManager();

	    static int timeout = 50 * 1000;
	    
	    static int MAX_CONNECTION = 100;
	    
	    static int DEF_MAX_CONNECTION = MAX_CONNECTION / 2;

	    static {
	        
	        HttpConnectionManagerParams httpConManagerParams = new HttpConnectionManagerParams();
	        httpConManagerParams.setMaxTotalConnections(MAX_CONNECTION);
	        httpConManagerParams.setDefaultMaxConnectionsPerHost(DEF_MAX_CONNECTION);
	        httpConManagerParams.setConnectionTimeout(timeout);
	        httpConManagerParams.setSoTimeout(timeout);
	        conManager.setParams(httpConManagerParams);
	    }

	/**
	 * 远程资源请求
	 * @param httpMethodName
	 * @param headers
	 * @param url 远程资源的url
	 * @param map 远程资源的数据key-value对
	 * @return 远程资源的响应
	 * @throws IOException
	 */
	public static String remoteCall(String httpMethodName, Map<String, String> headers, String url, Map<String, String> map) throws IOException {
		
		if ("get".equalsIgnoreCase(httpMethodName)){
			return executeGetReq(url, headers, map);
		} else {
			return executePostRequest(url, headers, map);
		}
	}
	
	public static String remoteCall(String httpMethodName, String url, Map<String, String> map) throws IOException {
		Map<String, String> headers = new HashMap<String, String>();
		if ("get".equalsIgnoreCase(httpMethodName)){
			return executeGetReq(url, headers, map);
		} else {
			return executePostRequest(url, headers, map);
		}
	}
	
	public static String executePostMethod(String url, Map<String, String> map) throws IOException {
		return executePostRequest(url, new HashMap<String, String>(), map);
	}
	public static String executeGetMethod4GBK(String url, Map<String, String> map) throws IOException {
		return executeGetReq4GBK(url, new HashMap<String, String>(), map);
	}
	public static String executeGetMethod(String url, Map<String, String> map) throws IOException {
		return executeGetReq(url, new HashMap<String, String>(), map);
	}

/**
 * Post远程资源
 * @param pUrl 远程资源的url
 * @param pHeaders
 * @param pMap
 * @return
 * @throws IOException
 */
	public static String executePostRequest(String pUrl, Map<String, String> pHeaders, Map<String, String> pMap) throws IOException {
		String retStr = null;
		
		PostMethod postM = new PostMethod(pUrl);
		postM.getParams().setContentCharset(UTF_8);
		
        if (pHeaders != null && pHeaders.size() > 0) {
            //自定义header
            for (Map.Entry<String, String> headerEntry : pHeaders.entrySet()) {
            	postM.setRequestHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
		
		if(pMap != null && !pMap.isEmpty()){
			if(pMap.get(JSON_STR) != null){
				String jsonStr = pMap.get(JSON_STR);
				StringRequestEntity rEntity = new StringRequestEntity(jsonStr, "application/json", UTF_8);
				postM.setRequestEntity(rEntity);
			}else{
				int pMapSize = pMap.size();
				NameValuePair[] nameValuePairs = new NameValuePair[pMapSize];
				Iterator<Entry<String, String>> pMapIterator = pMap.entrySet().iterator();
				Entry<String, String> pMapEntry = null;
				
				for(int i = 0 ; i < pMapSize ; i++){
					pMapEntry = pMapIterator.next();
					nameValuePairs[i] = new NameValuePair(pMapEntry.getKey(), pMapEntry.getValue());
				}
				
				// 将表单的值放入postMethod中
				postM.setRequestBody(nameValuePairs);
			}
		}
		
		HttpClient hClient = new HttpClient(); 
		
		hClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);  
		
		hClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);
		
		int statusC = hClient.executeMethod(postM);

		if (statusC == HttpStatus.SC_OK) {
			StringBuffer contentBuffer = new StringBuffer();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
					postM.getResponseBodyAsStream(), postM.getResponseCharSet()));

			String inLine = null;
			while ((inLine = bufferedReader.readLine()) != null) {
				contentBuffer.append(inLine);
			}
			bufferedReader.close();
			retStr = contentBuffer.toString();
		}
		
		return retStr;
	}
	
	public static String executeGetReq4GBK(String pUrl, Map<String, String> pHeaders, Map<String, String> pMap) throws IOException {
		String retStr = "";
		
		GetMethod getM = new GetMethod(pUrl); 
		getM.getParams().setContentCharset(GBK);
		
        if (pHeaders != null && pHeaders.size() > 0) {
            
            for (Map.Entry<String, String> headerEntry : pHeaders.entrySet()) {
            	getM.setRequestHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
		if(pMap != null && !pMap.isEmpty()){
			int pMapSize = pMap.size();
			NameValuePair[] nameValuePairs = new NameValuePair[pMapSize];
			Iterator<Entry<String, String>> iterator = pMap.entrySet().iterator();
			Entry<String, String> entry = null;
			
			for(int i = 0 ; i < pMapSize ; i++){
				entry = iterator.next();
				nameValuePairs[i] = new NameValuePair(entry.getKey(), entry.getValue());
			}
			
			getM.setQueryString(nameValuePairs);
		}
		HttpClient hClient = new HttpClient();
		
		hClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);  
		
		hClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);
		int status = hClient.executeMethod(getM);
		
		if (status == HttpStatus.SC_OK) {
			StringBuffer contentBuffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					getM.getResponseBodyAsStream(), getM.getResponseCharSet()));

			String inLine = null;
			while ((inLine = reader.readLine()) != null) {
				contentBuffer.append(inLine);
			}
			reader.close();
			retStr = contentBuffer.toString();
		}
		
		return retStr;
	}
	
	public static String executeGetReq(String pUrl, Map<String, String> pHeaders, Map<String, String> pMap) throws IOException {
		String retStr = "";
		
		GetMethod getM = new GetMethod(pUrl); 
		getM.getParams().setContentCharset(UTF_8);
		
        if (pHeaders != null && pHeaders.size() > 0) {
            
            for (Map.Entry<String, String> headerEntry : pHeaders.entrySet()) {
            	getM.setRequestHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
		if(pMap != null && !pMap.isEmpty()){
			int pMapSize = pMap.size();
			NameValuePair[] dataPairs = new NameValuePair[pMapSize];
			Iterator<Entry<String, String>> pMapIterator = pMap.entrySet().iterator();
			Entry<String, String> pMapEntry = null;
			
			for(int i = 0 ; i < pMapSize ; i++){
				pMapEntry = pMapIterator.next();
				dataPairs[i] = new NameValuePair(pMapEntry.getKey(), pMapEntry.getValue());
			}
			
			getM.setQueryString(dataPairs);
		}
		HttpClient hClient = new HttpClient();
		
		hClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);  
		
		hClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);
		int status = hClient.executeMethod(getM);
		
		if (status == HttpStatus.SC_OK) {
			StringBuffer contentB = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					getM.getResponseBodyAsStream(), getM.getResponseCharSet()));

			String inLine = null;
			while ((inLine = reader.readLine()) != null) {
				contentB.append(inLine);
			}
			reader.close();
			retStr = contentB.toString();
		}
		
		return retStr;
	}
	
	 public static String sendPost(String pUrl, Map<String, String> pHeaders, Map<String, String> pParams, String paramString,  String pEncoding) throws Exception{
	        String retStr = null;
	        if (pEncoding==null)
	            pEncoding = UTF_8;
	        HttpClient hClient = new HttpClient(conManager);
	        PostMethod m = new PostMethod(pUrl);
	        
	        if (pHeaders != null && pHeaders.size() > 0) {
	            
	            for (Map.Entry<String, String> headerEntry : pHeaders.entrySet()) {
	                m.setRequestHeader(headerEntry.getKey(), headerEntry.getValue());
	            }
	        }
	        
	        if (pParams != null && pParams.size() > 0 && paramString == null) {
	            List<NameValuePair> nvs = new ArrayList<NameValuePair>();
	            for (Map.Entry<String, String> paramEntry : pParams.entrySet()) {
	                nvs.add(new NameValuePair(paramEntry.getKey(), paramEntry.getValue()));
	            }
	            m.setRequestBody(nvs.toArray(new NameValuePair[]{}));
	        } else if (paramString != null) {
				m.setRequestEntity(new StringRequestEntity(paramString, pHeaders.get(CONTENT_T), pEncoding));
	        }
	        try {
	            
	            hClient.executeMethod(m);
	            int status = m.getStatusCode();  
	            if(status != HttpStatus.SC_OK){
	            	throw new Exception("post error,status code :"+status);
	            }else{
	            	
	                retStr = new String(m.getResponseBody(), pEncoding);
	            }
	            
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            
	            m.releaseConnection();
	        }
	        return retStr;
	    }

}
