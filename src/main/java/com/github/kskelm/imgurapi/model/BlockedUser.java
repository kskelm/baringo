/**
 * imgur account blocked user
 */
package com.github.kskelm.imgurapi.model;


/**
 * @author kskelm
 *
 */

public class BlockedUser {

	/**
	 * Blocked account ID
	 */
	private int id;
	/**
	 * Blocked account username,
	 */
	private String url;

	@Override
	public String toString() {
		return String.format(
				"BlockedUser[id=%d, url=%s]",
				id, url );
	} // toString

} // class BlockedUser
