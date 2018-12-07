package com.digital.dance.framework.commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * @author liuxiny
 *
 */
public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{  
	Log logger = new Log(DecryptPropertyPlaceholderConfigurer.class);
    /** 
     * 重写父类方法，解密指定属性名对应的属性值 
     */  
    @Override  
	protected String convertProperty(String propertyName, String propertyValue) {

		String propertyValueNew = propertyValue;
		if ( isEncryptPropertyVal( propertyName ) && StringUtils.isNotBlank( propertyValue ) ) {
			logger.info( "propertyName:" + propertyName + ", propertyValue:" + propertyValue );
			propertyValueNew = CookieUtil.decode( propertyValue );// 调用解密方法
		}
		
		return super.convertProperty( propertyName, propertyValueNew );
	}

    /** 
     * 判断属性值是否需要解密，这里我约定需要解密的属性名用encrypt开头 
     * @param propertyName 
     * @return 
     */  
    private boolean isEncryptPropertyVal(String propertyName){
    	if(propertyName == null){
    		return false;
    	}
        if(propertyName.toLowerCase().startsWith("encrypt")){  
            return true;  
        }else{  
            return false;
        }  
    }  
}
