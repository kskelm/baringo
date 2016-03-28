/**
 * Imgur API 3 Notification POJO
 */
package com.github.kskelm.imgurapi.model;

import com.github.kskelm.imgurapi.util.Utils;

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
	 * Get the Account ID for the notification
	 * @return the accountId
	 */
	public int getAccountId() {
		return accountId;
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
	public NotifiableContent getContent() {
		return content;
	}


	// ==================================================
	private int id;
	private int accountId;
	private boolean viewed;
	private NotifiableContent content; // likely to be a comment, conversation, or message
	
	public String toString() {
		return Utils.toString( this );
	} // toString
	
} // class Notification
