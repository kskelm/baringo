/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.model.Conversation;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import retrofit.Call;
import retrofit.Response;


/**
 * 
 * Manages messages between users
 * @author Kevin Kelm (triggur@gmail.com)
 */
public class ConversationService {

	/**
	 * Returns the list of conversations in which the currently-authenticated
	 * user took part (all of them).
	 * <p>NOTE: the getMessages() method will not
	 * return the messages for the conversation in this call; If you want
	 * all of that content, see @see #getConversationMessage .
	 * <p>
	 * Here we have another situation where, instead of returning an empty
	 * list when there are no conversations to list, the Imgur API is
	 * returning false.  Which of course breaks everything.
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @return list of Conversation objects.
	 * @throws BaringoApiException Imgur crashed
	 */
	public List<Conversation> getConversations() throws BaringoApiException {
		Call<ImgurResponseWrapper<String>> call =
				client.getApi().getConversations();

		try {
			Response<ImgurResponseWrapper<String>> res = call.execute();
			ImgurResponseWrapper<String> test = res.body();
			if( test.getData().equals( "false" )) { // laaaaaaame
				return new ArrayList<Conversation>();
			} // if
			// laaaaaaame
			List<Conversation> out = new Gson().fromJson(
					test.getData(), ConversationList.class);
			
			client.throwOnWrapperError( res );
			return out;
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Given a specific conversation id, return its information
	 * as well as the messages in it.  Messages are paged 25 at
	 * a time.  The currently-authenticated user must be one of
	 * the participants in the conversation.
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param conversationId numeric id of the conversation, probably derived from {@link #getConversations}
	 * @param page the page number <strong>STARTING AT 1</strong> and not 0 like everything else around here.
	 * @return Conversation with a list of messages
	 * @throws BaringoApiException argh
	 */
	public Conversation getConversationWithMessages(
			long conversationId,
			int page) throws BaringoApiException {

		Call<ImgurResponseWrapper<Conversation>> call =
				client.getApi().getConversationMessages( conversationId, page );

		try {
			Response<ImgurResponseWrapper<Conversation>> res = call.execute();
			ImgurResponseWrapper<Conversation> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Send a message to another user.  New messages automatically
	 * become part of the overall "conversation" with another user,
	 * basically like text messaging.
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param toUserName userName of the user to send to
	 * @param body the body of the message.
	 * @return the resulting Message object
	 * @throws BaringoApiException ouch
	 */
	public boolean sendMessage(
			String toUserName,
			String body ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().sendMessage( toUserName, body );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Given a conversation id, delete it and all messages inside it.
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param conversationId id of the conversation to delete
	 * @return true if success
	 * @throws BaringoApiException indicator of not-success
	 */
	public boolean deleteConversation(
			long conversationId ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().deleteConversation( conversationId );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Given a userName, report that user for sending messages
	 * that violate the Imgur Terms of Service.
	 * See <a href="https://imgur.com/tos">Imgur terms of service</a>
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param userName name of the user to report
	 * @return true if successful
	 * @throws BaringoApiException unsuccessful
	 */
	public boolean reportSender(
			String userName ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().reportMessageSender( userName );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Given a userName, permanently block that user from
	 * sending messages (actually it can be undone in the UI).
	 * <p>
     * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param userName name of the user to block
	 * @return true if successful
	 * @throws BaringoApiException unsuccessful
	 */
	public boolean blockSender(
			String userName ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().blockMessageSender( userName );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}



	// ===================================================

	// laaaaaaame
	@SuppressWarnings("serial")
	public class ConversationList extends ArrayList<Conversation> {};
	
	protected ConversationService(BaringoClient imgurClient, GsonBuilder gsonBuilder) {
		this.client = imgurClient;
	}

	private BaringoClient client = null;

}
