/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a notification sent to a user about new activity.
 * <p>
 * <b>NOTE:</b> In 1.0.0 the content is currently just a Map of key values,
 * pending figuring out how to turn it into the appropriate
 * kind of object that the notification is about.
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */

public class Notification {

	/**
	 * Get the ID of the notification
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get the User ID for the notification
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Whether or not the associated entity has been viewed yet
	 * @return the viewed
	 */
	public boolean isViewed() {
		return viewed;
	}

	/**
	 * Returns metadata about the content the notification is for
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}


	// ==================================================
	private long id;
	@SerializedName("account_id")
	private int userId;
	private boolean viewed;
// TODO look into why this needs to exist
private int thisTriggersWarningIconSoILookAtThisLater;
	private Object content; // likely to be a comment, conversation, or message
	
	public String toString() {
		return Utils.toString( this );
	} // toString
	
} 
