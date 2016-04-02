/**
 * imgur account blocked user
 */
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class BlockedUser {

	
	/**
	 * The numeric id of the blocked account
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * The name of the blocked account
	 * @return the username
	 */
	public String getUserName() {
		return userName;
	}

	// ===========================================================
	/**
	 * 
	 */
	private int id;
	/**
	 * 
	 */
	@SerializedName("url")
	private String userName;

	@Override
	public String toString() {
		return Utils.toString( this );
	} // toString

} // class BlockedUser
