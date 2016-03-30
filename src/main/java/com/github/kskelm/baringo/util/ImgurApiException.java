/**
 * Generic exceptions
 */
package com.github.kskelm.baringo.util;

/**
 * @author kskelm
 *
 */
public class ImgurApiException extends Exception {

	/**
	 * Behaves just like every other exception constructor
	 * @param msg - The message text for the exception
	 */
	public ImgurApiException( String msg ) {
		super( msg );
	}
	
	/**
	 * Adds support to include HTTP codes in the exception
	 * @param msg - The message text for the exception
	 * @param httpCode - The HTTP code associated with the exception
	 */
	public ImgurApiException( String msg, int httpCode ) {
		super( msg );
		this.httpCode = httpCode;
	}

	/**
	 * Returns the HTTP code of the exception, or 0 if none
	 * @return - the httpCode
	 */
	public int getHttpCode() {
		return this.httpCode;
	}
	
	private int httpCode = 0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3049907337921263740L;


}
