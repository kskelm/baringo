/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import java.util.Date;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * This represents a message sent from one user to another
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class Message {

	/**
	 * Returns the id of the conversation
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * This is the userId of the user that sent this
	 * message.
	 * @return the fromUserId
	 */
	public int getFromUserId() {
		return fromUserId;
	}
	
	/**
	 * This is the userName of the user that sent this message.
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return fromUserName;
	}
	
	/**
	 * This is the userId of the user receiving the message
	 * @return the toUserId
	 */
	public int getToUserId() {
		return toUserId;
	}
	
	/**
	 * This is the body of the message. Does not support markdown.
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * References up to the id of the conversation as a whole
	 * @return the conversationId
	 */
	public long getConversationId() {
		return conversationId;
	}
	
	/**
	 * Returns the date/time at which this message was sent.
	 * @return the sentAt
	 */
	public Date getSendDate() {
		return sendDate;
	}
	
	public String toString() {
		return Utils.toString( this );
	} // toString
	
	
	// ================================================
	private long id;
	
	@SerializedName("sender_id")
	private int fromUserId;
	@SerializedName("from")
	private String fromUserName;
	@SerializedName("account_id")
	private int toUserId;
	
	private String body;
	@SerializedName("conversation_id")
	private long conversationId;
	@SerializedName("datetime")
	private Date sendDate;

}
