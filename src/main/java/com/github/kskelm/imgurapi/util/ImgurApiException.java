/**
 * 
 */
package com.github.kskelm.imgurapi.util;

/**
 * @author kskelm
 *
 */
public class ImgurApiException extends Exception {

	public ImgurApiException( String msg ) {
		super( msg );
	}
	
	public ImgurApiException( String msg, int httpCode ) {
		super( msg );
		this.httpCode = httpCode;
	}

	public int getHttpCode() {
		return this.httpCode;
	}
	
	private int httpCode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3049907337921263740L;


}
