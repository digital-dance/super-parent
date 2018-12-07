package com.digital.dance.framework.identity.dao;

import com.digital.dance.framework.identity.entity.IdentityEO;

public interface IdentityDao {

	/**
	 * find next identity value
	 * @param identityEO
	 * @return
	 */
	public Long findNextIdentityValue(IdentityEO identityEO);
	/**
	 * reset Identity Value
	 * @param identityEO
	 * @return
	 */
	public Long resetIdentityValue(IdentityEO identityEO);

}
