/**
 * The count of votes on an item
 */
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
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
	@SerializedName("ups")
	private int upVotes;
	@SerializedName("downs")
	private int downVotes;
	
	@Override
	public String toString() {
		return Utils.toString( this );
	}

} // class AccountSettings
