/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * The counters for upvotes and downvotes for an item
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */

public class Votes {

	/**
	 * The number of upvotes the item has received
	 * @return the upVotes
	 */
	public int getUpVotes() {
		return upVotes;
	}

	/**
	 * The number of downvotes the item has received
	 * @return the downVotes
	 */
	public int getDownVotes() {
		return downVotes;
	}

	// ===================================================
	
	protected Votes() {}
	
	@SerializedName("ups")
	private int upVotes;
	@SerializedName("downs")
	private int downVotes;
	
	@Override
	public String toString() {
		return Utils.toString( this );
	}

} // class AccountSettings
