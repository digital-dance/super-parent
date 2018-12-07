package com.digital.dance.framework.identity.utils;

import com.digital.dance.framework.identity.service.IdentityService;
import com.digital.dance.framework.identity.service.IdentityService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class IdentityUtil
{
  private static IdentityService identityService;
  private static final Map< String, AtomicLong > identityMap = new HashMap< String, AtomicLong>();

  public static synchronized Long generateId( String system, String subSys, String module, String table ) {
    String cachedKey = system + subSys + module + table;
    AtomicLong identityAtomicLong = ( AtomicLong )identityMap.get( cachedKey );
    if ( identityAtomicLong == null ) {
    	//每次执行都必定产生不同的id前缀, 每个节点都必定得到不同的id前缀
      Long idprefix = identityService.generateId( system, subSys, module, table );
      identityAtomicLong = new AtomicLong( idprefix.longValue() * 1000L );
      identityMap.put( cachedKey, identityAtomicLong );
    }

    Long idvalue = Long.valueOf( identityAtomicLong.getAndIncrement() );
    if ( idvalue.longValue() % 1000L == 999L ) {
    	//每次执行都必定产生不同的id前缀, 每个节点都必定得到不同的id前缀
      Long idprefix = identityService.generateId( system, subSys, module, table );
      identityAtomicLong = new AtomicLong( idprefix.longValue() * 1000L );
      identityMap.put( cachedKey, identityAtomicLong );
    }
    return idvalue;
  }
  
  public static IdentityService getIdentityService() {
	  return IdentityUtil.identityService;
  }

  public static void setIdentityService( IdentityService identityService ) {
	  IdentityUtil.identityService = identityService;
  }
}