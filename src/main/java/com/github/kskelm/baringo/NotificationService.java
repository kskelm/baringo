/**
 * Imgur API notification service
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.List;

import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.model.Notification;
import com.github.kskelm.baringo.model.NotificationList;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.github.kskelm.baringo.util.BaringoAuthException;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Response;


/**
 * @author kskelm
 *
 */
public class NotificationService {

	/**
	 * Specifically, returns only the notifications that someone
	 * has replied to the user in comments or somewhere.
	 * ACCESS: AUTHENTICATED USER
	 * @param onlyNew - true if the request is for only the unviewed notifications
	 * @return A list of Notification objects
	 * @throws BaringoApiException - something failed
	 */
	/**
	 * Returns the currently-logged-in user's list of message notifications
	 * ACCESS: AUTHENTICATED USER
	 * @param newOnly - whether to get non-viewed notifications
	 * @return the list of notifications
	 * @throws BaringoApiException - it couldn't be done
	 */
	public List<Notification> listReplyNotifications(
			boolean onlyNew ) throws BaringoApiException {
		String userName = client.getAuthenticatedUserName();
		if( userName == null ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<NotificationList>> call =
				client.getApi().listNotifications( onlyNew );
		try {
			Response<ImgurResponseWrapper<NotificationList>> res = call.execute();
			ImgurResponseWrapper<NotificationList> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData().getReplyNotifications();
			
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // listReplyNotifications


	/**
	 * Returns the currently-logged-in user's list of message notifications
	 * ACCESS: AUTHENTICATED USER
	 * @param newOnly - whether to get non-viewed notifications
	 * @return the list of notifications
	 * @throws BaringoApiException - it couldn't be done
	 */
	public List<Notification> listMessageNotifications(
			boolean onlyNew ) throws BaringoApiException {
		String userName = client.getAuthenticatedUserName();
		if( userName == null ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<NotificationList>> call =
				client.getApi().listNotifications( onlyNew );
		try {
			Response<ImgurResponseWrapper<NotificationList>> res = call.execute();
			ImgurResponseWrapper<NotificationList> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData().getMessageNotifications();
			
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // listMessageNotifications

	/**
	 * Returns information about a specific notification
	 * ACCESS: AUTHENTICATED USER
	 * @param id - the id of the notification to return
	 * @return a Notification object
	 * @throws BaringoApiException - something failed
	 */
	public Notification getNotification( long id ) throws BaringoApiException {
		String userName = client.getAuthenticatedUserName();
		if( userName == null ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<Notification>> call =
				client.getApi().getNotification( id );
		try {
			Response<ImgurResponseWrapper<Notification>> res = call.execute();
			ImgurResponseWrapper<Notification> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
			
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}
	
	/**
	 * Marks a notification as having been viewed
	 * ACCESS: AUTHENTICATED USER
	 * @param id - the id of the notification to mark viewed
	 * @return true if successful... false probably means it was already marked viewed
	 * @throws BaringoApiException - something failed
	 */
	public boolean markNotificiationViewed( long id ) throws BaringoApiException {
		String userName = client.getAuthenticatedUserName();
		if( userName == null ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().markNotificationViewed( id );
		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
			
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}
	
	
	
	
	
	// ================================================
	protected NotificationService( BaringoClient imgurClient, GsonBuilder gsonBuilder ) {
		this.client = imgurClient;
	} // constructor

	
	private BaringoClient client = null;

}
