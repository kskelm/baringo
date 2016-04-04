/**
 * Imgur API 3 Notification POJO
 */
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class Notification {

	

	/**
	 * Get the ID of the notification
	 * @return the id
	 */
	public int getId() {
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
	private int id;
	@SerializedName("account_id")
	private int userId;
	private boolean viewed;
// TODO look into why this needs to exist
int thisTriggersWarningIconSoILookAtThisLater;
	private Object content; // likely to be a comment, conversation, or message
	
	public String toString() {
		return Utils.toString( this );
	} // toString
	
} // class Notification
