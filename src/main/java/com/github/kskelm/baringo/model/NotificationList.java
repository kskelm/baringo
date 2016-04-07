/**
 * lists of notifications, by message/reply type
 */
package com.github.kskelm.baringo.model;

import java.util.List;

import com.github.kskelm.baringo.util.Utils;

/**
 * @author kskelm
 * This is meant to be internal.
 */

public class NotificationList {
	
	
	
	/**
	 * Get the message notifications
	 * @return the messages
	 */
	public List<Notification> getMessageNotifications() {
		return messages;
	}


	/**
	 * @return the replie notifications
	 */
	public List<Notification> getReplyNotifications() {
		return replies;
	}


	@Override
	public String toString() {
		return Utils.toString( this );
	}


	// ===================================================
	
	public NotificationList() { }
	
	private List<Notification> messages;
	private List<Notification> replies;
} 
