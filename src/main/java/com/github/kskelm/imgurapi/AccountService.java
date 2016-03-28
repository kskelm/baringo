/**
 * Imgur Account API services
 * See {@link http://api.imgur.com/endpoints/account} for API details
 */
package com.github.kskelm.imgurapi;

import java.io.IOException;
import java.util.List;

import com.github.kskelm.imgurapi.model.Account;
import com.github.kskelm.imgurapi.model.AccountSettings;
import com.github.kskelm.imgurapi.model.ChangedAccountSettings;
import com.github.kskelm.imgurapi.model.GalleryItem;
import com.github.kskelm.imgurapi.model.GalleryProfile;
import com.github.kskelm.imgurapi.model.ImgurResponseWrapper;
import com.github.kskelm.imgurapi.util.ImgurApiException;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Response;


/**
 * @author kskelm
 *
 */
public class AccountService {

	/**
	 * Given an account name, return the Account object for it.
	 * Note that some accounts cannot be returned, notably ones
	 * that authenticate in via external systems such as Facebook.
	 * ACCESS: ANONYMOUS
	 * @param userName - the name of the account
	 * @return Account object
	 * @throws ImgurApiException - something went pear-shaped
	 */
	public Account getAccount( String userName ) throws ImgurApiException {
		Call<ImgurResponseWrapper<Account>> call =
				client.getApi().getAccount( userName );

		try {
			Response<ImgurResponseWrapper<Account>> res = call.execute();
			ImgurResponseWrapper<Account> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getAccount

	/**
	 * Return whether or not the currently authenticated account's
	 * email address has been verified
	 * ACCESS: AUTHENTICATED USER
	 * @return boolean - whether or not this account is verified
	 * @throws ImgurApiException
	 */
	public boolean isVerified() throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount() ;
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<Object>> call =
				client.getApi().getAccountVerified( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<Object>> res = call.execute();
			ImgurResponseWrapper<Object> out = res.body();
			client.throwOnWrapperError( res );
System.err.println( "TODO: not sure we want isSuccess here, maybe payload");
			return out.isSuccess();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // isVerified

	/**
	 * Given an account name and a page number (starting at 0),
	 * return a list of GalleryItems that user has favorited.
	 * GalleryItem is the superclass for GalleryImage and
	 * GallerAlbum, so cast the results as appropriate for access
	 * to more specific fields.  Defaults to sorting by newest.
	 * ACCESS: ANONYMOUS
	 * @param userName
	 * @param page
	 * @return a list of gallery items
	 * @throws ImgurApiException
	 */
	public List<GalleryItem> getGalleryFavorites( String userName,
			int page ) throws ImgurApiException {

		return getGalleryFavorites( userName, page, Account.GallerySort.newest);
	} // getGalleryFavorites
	
	/**
	 * Given an account name, a sort, and a page number (starting at 0),
	 * return a list of GalleryItems that user has favorited in the gallery.
	 * GalleryItem is the superclass for GalleryImage and
	 * GallerAlbum, so cast the results as appropriate for access
	 * to more specific fields.
	 * ACCESS: ANONYMOUS
	 * @param userName - name of the user to get favorites for
	 * @param sort - the sort direction for results
	 * @param page - the page number to return starting at 0
	 * @return a list of gallery items
	 * @throws ImgurApiException
	 */
	public List<GalleryItem> getGalleryFavorites( String userName,
			int page,
			Account.GallerySort sort
			 ) throws ImgurApiException {
		Call<ImgurResponseWrapper<List<GalleryItem>>> call =
				client.getApi().getAccountGalleryFavorites( userName, page, sort );

		try {
			Response<ImgurResponseWrapper<List<GalleryItem>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItem>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getGalleryFavorites
	
	
	/**
	 * Return a list of GalleryItems this user has favorited.
	 * GalleryItem is the superclass for GalleryImage and
	 * GallerAlbum, so cast the results as appropriate for access
	 * to more specific fields.
	 * ACCESS: AUTHENTICATED USER
	 * @return a list of gallery items
	 * @throws ImgurApiException
	 */
	public List<GalleryItem> getFavorites() throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount() ;
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<List<GalleryItem>>> call =
				client.getApi().getAccountFavorites( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<List<GalleryItem>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItem>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getFavorites
	
	/**
	 * Given an account name, a sort, and a page number (starting at 0),
	 * return a list of GalleryItems that user has submitted in the gallery.
	 * GalleryItem is the superclass for GalleryImage and
	 * GallerAlbum, so cast the results as appropriate for access
	 * to more specific fields.
	 * ACCESS: ANONYMOUS
	 * @param userName - name of the user to get favorites for
	 * @param page - the page number to return starting at 0
	 * @return a list of gallery items
	 * @throws ImgurApiException
	 */
	public List<GalleryItem> getSubmissions( String userName,
			int page ) throws ImgurApiException {
		Call<ImgurResponseWrapper<List<GalleryItem>>> call =
				client.getApi().getAccountSubmissions( userName, page );

		try {
			Response<ImgurResponseWrapper<List<GalleryItem>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItem>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getSubmissions

	/**
	 * Return the settings on the currently authenticated account.
	 * ACCESS: AUTHENTICATED USER
	 * @return The account's settings
	 * @throws ImgurApiException
	 */
	public AccountSettings getAccountSettings() throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount() ;
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<AccountSettings>> call =
				client.getApi().getAccountSettings( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<AccountSettings>> res = call.execute();
			ImgurResponseWrapper<AccountSettings> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getAccountSettings

	/**
	 * Saves the settings on the currently authenticated account.
	 * ACCESS: AUTHENTICATED USER
	 * @param settings - the settings to save
	 * @throws ImgurApiException
	 */
	public void setAccountSettings( ChangedAccountSettings settings ) throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount() ;
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<Object>> call =
				client.getApi().setAccountSettings( acct.getUserName(), settings );

		try {
			Response<ImgurResponseWrapper<Object>> res = call.execute();
//			ImgurResponseWrapper<Object> out = res.body();

			client.throwOnWrapperError( res );
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // setAccountSettings

	/**
	 * Return the gallery profile for the currently authenticated account.
	 * ACCESS: AUTHENTICATED USER
	 * @return The account's GalleryProfile
	 * @throws ImgurApiException
	 */
	public GalleryProfile getGalleryProfile() throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount() ;
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if
		
		Call<ImgurResponseWrapper<GalleryProfile>> call =
				client.getApi().getGalleryProfile( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<GalleryProfile>> res = call.execute();
			ImgurResponseWrapper<GalleryProfile> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getGalleryProfile

	// TODO: verify user's email
	// TODO: send verification email
	// TODO: get albums for this account
	// TODO: get information about an album id (xref to AlbumService)
	// TODO: get array of the user's album id's
	// TODO: return the total number of albums associated with the account
	// TODO: delete an album by id (xref to AlbumService)
	// TODO: return comments the user has created
	// TODO: return information about a specific comment (xref to CommentService)
	// TODO: return an array of all comment id's
	// TODO: return a count of all the comments on the account
	// TODO: delete a comment (xref to CommentService)
	// TODO: return all images associated with the account
	// TODO: return information about a specific image (xref to ImageService)
	// TODO: return array of image id's for the account
	// TODO: return number of images for the account
	// TODO: delete an image (xref to ImageService)
	// TODO: return all reply notifications for the user (xref to ConversationService?)
	
	
	// ================================================

	public AccountService(ImgurClient imgurClient, GsonBuilder gsonBuilder) {
		this.client = imgurClient;
	}

	ImgurClient client = null;

} // class AccountService
