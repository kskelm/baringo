/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.util;

/**
 * This is mostly for signaling back error conditions
 * returned by Imgur itself during API calls, as well as
 * when invalid search requests are being created.
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class BaringoApiException extends Exception {

	/**
	 * Behaves just like every other exception constructor
	 * @param msg The message text for the exception
	 */
	public BaringoApiException( String msg ) {
		super( msg );
	}
	
	/**
	 * Adds support to include HTTP codes in the exception
	 * @param msg The message text for the exception
	 * @param httpCode The HTTP code associated with the exception
	 */
	public BaringoApiException( String msg, int httpCode ) {
		super( msg );
		this.httpCode = httpCode;
	}

	/**
	 * Returns the HTTP code of the exception, or 0 if none
	 * @return the httpCode
	 */
	public int getHttpCode() {
		return this.httpCode;
	}
	
	public String toString() {
		if( httpCode == 0 ) {
			return super.toString();
		} // if
		return super.toString() + " (http code " + httpCode + ")";
	}
	
	private int httpCode = 0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3049907337921263740L;


}
