/**
 * Imgur Account API services
 * See {@link http://api.imgur.com/endpoints/account} for API details
 */
package com.github.kskelm.imgurapi;

import java.io.IOException;
import java.util.List;

import com.github.kskelm.imgurapi.model.Account;
import com.github.kskelm.imgurapi.model.AccountSettings;
import com.github.kskelm.imgurapi.model.Album;
import com.github.kskelm.imgurapi.model.ChangedAccountSettings;
import com.github.kskelm.imgurapi.model.Comment;
import com.github.kskelm.imgurapi.model.GalleryItem;
import com.github.kskelm.imgurapi.model.GalleryProfile;
import com.github.kskelm.imgurapi.model.Image;
import com.github.kskelm.imgurapi.model.ImgurResponseWrapper;
import com.github.kskelm.imgurapi.model.Notification;
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
	public List<GalleryItem> listGalleryFavorites( String userName,
			int page ) throws ImgurApiException {

		return listGalleryFavorites( userName, page, Account.GallerySort.newest);
	} // listFavorites

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
	public List<GalleryItem> listGalleryFavorites( String userName,
			int page,
			Account.GallerySort sort
			) throws ImgurApiException {
		Call<ImgurResponseWrapper<List<GalleryItem>>> call =
				client.getApi().listAccountGalleryFavorites( userName, page, sort );

		try {
			Response<ImgurResponseWrapper<List<GalleryItem>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItem>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listFavorites


	/**
	 * Return a list of GalleryItems this user has favorited.
	 * GalleryItem is the superclass for GalleryImage and
	 * GallerAlbum, so cast the results as appropriate for access
	 * to more specific fields.
	 * ACCESS: AUTHENTICATED USER
	 * @return a list of gallery items
	 * @throws ImgurApiException
	 */
	public List<GalleryItem> listFavorites() throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount();
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if

		Call<ImgurResponseWrapper<List<GalleryItem>>> call =
				client.getApi().listAccountFavorites( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<List<GalleryItem>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItem>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listFavorites

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
	public List<GalleryItem> listSubmissions( String userName,
			int page ) throws ImgurApiException {
		Call<ImgurResponseWrapper<List<GalleryItem>>> call =
				client.getApi().listAccountSubmissions( userName, page );

		try {
			Response<ImgurResponseWrapper<List<GalleryItem>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItem>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listSubmissions

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
// TODO: remove me?
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
				client.getApi().getAccountGalleryProfile( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<GalleryProfile>> res = call.execute();
			ImgurResponseWrapper<GalleryProfile> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getGalleryProfile

	/**
	 * Returns whether or not the currently-authenticated account's
	 * email address has been verified.
	 * ACCESS: AUTHENTICATED USER
	 * @return The account's GalleryProfile
	 * @throws ImgurApiException
	 */
	public boolean isVerified() throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount() ;
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if

		Call<ImgurResponseWrapper<Object>> call =
				client.getApi().isAccountVerified( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<Object>> res = call.execute();
			ImgurResponseWrapper<Object> out = res.body();

			client.throwOnWrapperError( res );
// TODO: address me!
System.err.println("TODO: INSPECT ACTUAL isVerified() RESPONSE TO SEE WHAT THIS SHOULD BE");
			return out.equals( true );
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // isVerified

	/**
	 * Initiates re-sending of the verification email.
	 * ACCESS: AUTHENTICATED USER
	 * @throws ImgurApiException
	 */
	public void sendVerificationEmail() throws ImgurApiException {
		Account acct = client.getAuthenticatedAccount() ;
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if

		Call<ImgurResponseWrapper<Object>> call =
				client.getApi().sendAccountVerificationEmail( acct.getUserName() );

		try {
			Response<ImgurResponseWrapper<Object>> res = call.execute();
// TODO: address me!
//			ImgurResponseWrapper<Object> out = res.body();
			res.body();
			client.throwOnWrapperError( res );
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // sendVerificationEmail

	/**
	 * Returns a paged list of albums associated with the given
	 * userName, paged 50 at a time.
	 * ACCESS: ANONYMOUS
	 * @param userName - the name of the user to fetch albums for
	 * @param page - the page number, starting at 0
	 * @return A list of Album objects
	 * @throws ImgurApiException
	 */
	public List<Album> listAlbums(
			String userName,
			int page ) throws ImgurApiException {

		Call<ImgurResponseWrapper<List<Album>>> call =
				client.getApi().listAccountAlbums( userName, page );

		try {
			Response<ImgurResponseWrapper<List<Album>>> res = call.execute();
			ImgurResponseWrapper<List<Album>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
			
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listAlbums

	/**
	 * Returns a list of album IDs associated with the given
	 * userName, paged 50 at a time
	 * ACCESS: ANONYMOUS
	 * @param userName - the name of the user to fetch album ids for
	 * @param page - The page number to fetch, starting at 0
	 * @return A list of album IDs (integers)
	 * @throws ImgurApiException
	 */
	public List<Integer> listAlbumIds(
			String userName,
			int page ) throws ImgurApiException {

		Call<ImgurResponseWrapper<List<Integer>>> call =
				client.getApi().listAccountAlbumIds( userName, page );
		try {
			Response<ImgurResponseWrapper<List<Integer>>> res = call.execute();
			ImgurResponseWrapper<List<Integer>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listAlbumIds

	/**
	 * Returns the total number of Albums the given user
	 * owns.
	 * @param userName - The name of the user to fetch an album count for
	 * @return Integer - the number of albums the user owns
	 * @throws ImgurApiException
	 */
	public int getAlbumCount(
				String userName ) throws ImgurApiException {
// TODO: FIX <Object>?
		Call<ImgurResponseWrapper<Object>> call =
				client.getApi().isAccountVerified( userName );

		try {
			Response<ImgurResponseWrapper<Object>> res = call.execute();
			ImgurResponseWrapper<Object> out = res.body();

			client.throwOnWrapperError( res );
// TODO: address me!
System.err.println("TODO: RETURN CORRECT AccountService.getAlbumCount DATA HERE!");
			return (Integer)out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getAlbumCount

	/**
	 * Returns a paged list of comments associated with the given
	 * userName, paged 50 at a time.
	 * ACCESS: ANONYMOUS
	 * @param userName - the name of the user to fetch comments for
	 * @param page - the page number, starting at 0
	 * @return A list of Comment objects
	 * @throws ImgurApiException
	 */
	public List<Comment> listComments(
			String userName,
			Comment.Sort sort,
			int page ) throws ImgurApiException {

		Call<ImgurResponseWrapper<List<Comment>>> call =
				client.getApi().listAccountComments( userName, sort, page );

		try {
			Response<ImgurResponseWrapper<List<Comment>>> res = call.execute();
			ImgurResponseWrapper<List<Comment>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
			
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listComments
	
	/**
	 * Returns a list of comment IDs associated with the given
	 * userName, paged 50 at a time
	 * ACCESS: ANONYMOUS
	 * @param userName - the name of the user to fetch comment ids for
	 * @param page - The page number to fetch, starting at 0
	 * @return A list of comment IDs (integers)
	 * @throws ImgurApiException
	 */
	public List<Integer> listCommentIds(
			String userName,
			int page ) throws ImgurApiException {

		Call<ImgurResponseWrapper<List<Integer>>> call =
				client.getApi().listAccountCommentIds( userName, page );
		try {
			Response<ImgurResponseWrapper<List<Integer>>> res = call.execute();
			ImgurResponseWrapper<List<Integer>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listCommentIds

	/**
	 * Returns the total number of Comments the given user
	 * owns.
	 * @param userName - The name of the user to fetch a comment count for
	 * @return int - the number of comments the user owns
	 * @throws ImgurApiException
	 */
	public int getCommentCount(
				String userName ) throws ImgurApiException {
		Call<ImgurResponseWrapper<Integer>> call =
				client.getApi().getAccountImageCount( userName );

		try {
			Response<ImgurResponseWrapper<Integer>> res = call.execute();
			ImgurResponseWrapper<Integer> out = res.body();

			client.throwOnWrapperError( res );
// TODO: address me!
System.err.println("TODO: RETURN CORRECT AccountService.getCommentCount DATA HERE!");
			return (Integer)out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getCommentCount
	
	/**
	 * Returns a paged list of images associated with the given
	 * userName, paged 50 at a time.
	 * ACCESS: ANONYMOUS
	 * @param userName - the name of the user to fetch images for
	 * @param page - the page number, starting at 0
	 * @return A list of Image objects
	 * @throws ImgurApiException
	 */
	public List<Image> listImages(
			String userName,
			int page ) throws ImgurApiException {

		Call<ImgurResponseWrapper<List<Image>>> call =
				client.getApi().listAccountImages( userName, page );

		try {
			Response<ImgurResponseWrapper<List<Image>>> res = call.execute();
			ImgurResponseWrapper<List<Image>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
			
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listImages
	
	/**
	 * Returns a list of image IDs associated with the given
	 * userName, paged 50 at a time
	 * ACCESS: ANONYMOUS
	 * @param userName - the name of the user to fetch image ids for
	 * @param page - The page number to fetch, starting at 0
	 * @return A list of Image IDs (strings)
	 * @throws ImgurApiException
	 */
	public List<String> listImageIds(
			String userName,
			int page ) throws ImgurApiException {

		Call<ImgurResponseWrapper<List<String>>> call =
				client.getApi().listAccountImageIds( userName, page );
		try {
			Response<ImgurResponseWrapper<List<String>>> res = call.execute();
			ImgurResponseWrapper<List<String>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listImageIds
	
	/**
	 * Returns the total number of Images the given user
	 * owns.
	 * @param userName - The name of the user to fetch a image count for
	 * @return int - the number of images the user owns
	 * @throws ImgurApiException
	 */
	public int getImageCount(
				String userName ) throws ImgurApiException {
		Call<ImgurResponseWrapper<Integer>> call =
				client.getApi().getAccountImageCount( userName );

		try {
			Response<ImgurResponseWrapper<Integer>> res = call.execute();
			ImgurResponseWrapper<Integer> out = res.body();

			client.throwOnWrapperError( res );
// TODO: address me!
int foo; // to trigger a warning on the icon to remind me to come back for this
System.err.println("TODO: RETURN CORRECT AccountService.getImageCount DATA HERE!");
			return (Integer)out.getData();
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getImageCount
	
	/**
	 * Returns a list of notifications to the given account
	 * ACCESS: AUTHENTICATED USER
	 * @param userName - the name of the user to fetch images for
	 * @param onlyNew - true if the request is for only the unviewed notifications
	 * @return A list of Notification objects
	 * @throws ImgurApiException
	 */
	public List<Notification> listNotifications(
			String userName,
			boolean onlyNew ) throws ImgurApiException {

		Account acct = client.getAuthenticatedAccount();
		if( acct == null ) {
			throw new ImgurApiException( "No user logged in", 401 );
		} // if

		Call<ImgurResponseWrapper<List<Notification>>> call =
				client.getApi().listAccountReplyNotifications( userName, onlyNew );

		try {
			Response<ImgurResponseWrapper<List<Notification>>> res = call.execute();
			ImgurResponseWrapper<List<Notification>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData();
			
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // listNotifications

	// ================================================

	public AccountService(ImgurClient imgurClient, GsonBuilder gsonBuilder) {
		this.client = imgurClient;
	}

	ImgurClient client = null;

} // class AccountService
