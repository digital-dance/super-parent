package com.digital.dance.framework.commons;

import java.util.UUID;

import com.digital.dance.framework.identity.utils.IdentityUtil;

public class PrimaryKeyGenerator {
	public static final String SEQUENCES = "sequences";
	public static final String SYSTEM = "system";
	public static final String SUB_SYS = "subSys";
	public static final String MODULE = "module";
	public static String generatePrimaryKey(String tableName){

		String prefix = AppPropsConfig.getPropertyValue(tableName.toLowerCase());
		String system = AppPropsConfig.getPropertyValue(SYSTEM);
		String subSys = AppPropsConfig.getPropertyValue(SUB_SYS);
		String module = AppPropsConfig.getPropertyValue(MODULE);
		
		if(IdentityUtil.getIdentityService() == null 
				 || StringTools.isEmpty( system ) 
				 || StringTools.isEmpty( subSys ) 
				 || StringTools.isEmpty( module ) 
				 || StringTools.isEmpty( tableName ) 
				 || !AppPropsConfig.containsPropertyKey( tableName ) ){
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			
			String id = IdentityUtil.generateId(system, subSys, module, tableName).toString();
			if( StringTools.isEmpty( prefix ) || SEQUENCES.equals( prefix ) ){
				return id; 
			}
			return prefix + id;
		}
	}
	
	public static String generateBusinessPrimaryKey(String tableName){
		
		String prefix = AppPropsConfig.getPropertyValue(tableName);
		String system = AppPropsConfig.getPropertyValue(SYSTEM);
		String subSys = AppPropsConfig.getPropertyValue(SUB_SYS);
		String module = AppPropsConfig.getPropertyValue(MODULE);
		if(IdentityUtil.getIdentityService() == null 
			 || StringTools.isEmpty( system ) 
			 || StringTools.isEmpty( subSys )  
			 || StringTools.isEmpty( module )   
			 || StringTools.isEmpty( tableName ) ){
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			String id = IdentityUtil.generateId(system, subSys, module, tableName).toString();
			if( StringTools.isEmpty( prefix ) ){
				return id; 
			}
			return prefix + id;
		}
	}
}

