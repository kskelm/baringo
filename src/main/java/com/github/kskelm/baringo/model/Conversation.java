/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * This is the model for a conversation
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class Conversation {

	
	/**
	 * Returns the id of the conversation
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * Returns a short summary of the beginning of the message
	 * @return the lastMessagePreview
	 */
	public String getLastMessagePreview() {
		return lastMessagePreview;
	}
	/**
	 * Returns the date/time on which the most recent message was
	 * posted (could be used to keep track of badging or whatever)
	 * @return the lastMessageDate
	 */
	public Date getLastMessageDate() {
		return lastMessageDate;
	}
	/**
	 * Returns the userId of the author
	 * @return the authorId
	 */
	public int getAuthorId() {
		return authorId;
	}
	/**
	 * Returns the userName of the author
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}
	
	/**
	 * Returns the list of Message objects associated
	 * with this conversation.  Messages are returned
	 * by the API in pages. They are returned in reverse-
	 * chronological order; the most recent messages will
	 * be at the top.
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}
	
	/**
	 * Returns the total number of messages in the conversation.
	 * @return the messageCount
	 */
	public int getMessageCount() {
		return messageCount;
	}
	
	public String toString() {
		return Utils.toString( this );
	} // toString

	
	// ================================================
	private long id;
	@SerializedName("last_message_preview")
	private String lastMessagePreview;
	@SerializedName("datetime")
	private Date lastMessageDate;
	@SerializedName("with_account_id")
	private int authorId;
	@SerializedName("with_account")
	private String authorName;
	@SerializedName("message_count")
	private int messageCount;
	private List<Message> messages = new ArrayList<>();
	
}
