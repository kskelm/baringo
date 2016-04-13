/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * Id and name of a user that the currently authenticated user has blocked
 * @author Kevin Kelm (triggur@gmail.com)
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

	protected BlockedUser() {}
	
	private int id;
	@SerializedName("url")
	private String userName;

	@Override
	public String toString() {
		return Utils.toString( this );
	} // toString

} // class BlockedUser
