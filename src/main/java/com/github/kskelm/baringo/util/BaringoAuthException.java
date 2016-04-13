/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.util;

/**
 * This is for when an authentication problem occurs with
 * Imgur itself, such as invalid client credentials or
 * user tokens.  This also happens if a refresh token expires,
 * an authorization code is stale, or if the user has revoked
 * the application's access.
 * 
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class BaringoAuthException extends BaringoApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaringoAuthException(String message) {
		super( message );
	}

	public BaringoAuthException(String message, int code) {
		super( message, code );
	}

}
