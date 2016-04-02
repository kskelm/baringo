/**
 * 
 */
package com.github.kskelm.baringo.util;

/**
 * @author kskelm
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
