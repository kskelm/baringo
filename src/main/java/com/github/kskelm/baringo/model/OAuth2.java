/**
 * Authentication information.  This should be enthusiastically
 * accessed by the application and saved somewhere.
 */
package com.github.kskelm.baringo.model;


import java.util.Date;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class OAuth2 {

	public enum TokenType {
		/**
		 * Bearer tokens are the keys to the kingdom if you
		 * have them.
		 */
		@SerializedName( "bearer" )
		Bearer,
		/**
		 * Basic authentication
		 */
		@SerializedName( "basic" )
		Basic,
		/**
		 * Authentication via "anonymous" client id
		 */
		@SerializedName( "client" )
		Client
	}

	public static final int MIN_EXPIRE_THRESHOLD = 60; // if < 60 secs til expire, refresh

	/**
	 * Returns true if we should go ahead and update the access token.
	 * @return is expiring within our expiration window?
	 */
	public boolean isExpiringSoon() {
		return getExpiresIn() < MIN_EXPIRE_THRESHOLD;
	}
	
	/**
	 * Returns the access token that is used on a per-call basis
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Returns the refresh token that's used to update the access
	 * token when it expires
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Returns the number of seconds the access token expires in.
	 * <i>Note that this is only valid if Baringo fetched the tokens
	 * itself in this same session, rather than them being set
	 * manually.</i>  Of course you should set them manually, so there's
	 * that.
	 * @return the expires_in
	 */
	public int getExpiresIn() {
		long start = createdDate.getTime();
		long now = System.currentTimeMillis();
		int secsOld = (int)((now - start) / 1000);
		return expiresIn - secsOld;
	}

	/**
	 * Returns the date/time when the access token expires, subject
	 * to the same conditions as {Ssee getExpiresIn}
	 * @return the date/time on which the access token expires
	 */
	public Date getExpiresOn() {
		long expSecs = createdDate.getTime() + (long)expiresIn * 1000;
		return new Date( expSecs );
	}

	/**
	 * Returns the token type.  Probably Bearer.
	 * @return the tokenType
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Returns the scope of the token's authorization.
	 * Probably null = everything.
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Returns the numeric ID of the authenticated user.
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Returns the userName of the authenticated user.
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	// ===================================================
	@SerializedName( "access_token")
	private String accessToken;
	@SerializedName( "refresh_token")
	private String refreshToken;
	@SerializedName( "expires_in")
	private int expiresIn;
	@SerializedName( "token_type")
	private TokenType tokenType;
	private String scope;
	@SerializedName( "account_id")
	private int userId;
	@SerializedName( "account_username")
	private String userName;
	private Date createdDate = new Date( System.currentTimeMillis() );
	
	@Override
	public String toString() {
		return Utils.toString( this );
	}

} // class OAuth2
