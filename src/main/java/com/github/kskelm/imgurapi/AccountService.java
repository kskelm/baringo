/**
 * Imgur Account API services
 */
package com.github.kskelm.imgurapi;

import java.io.IOException;
import java.util.List;

import com.github.kskelm.imgurapi.model.Account;
import com.github.kskelm.imgurapi.model.GalleryItem;
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
	 * Given an account name, return whether or not that account's email
	 * address has been verified
	 * ACCESS: USER AUTHENTICATED
	 * @param userName - name of user to check
	 * @return boolean - whether or not that account is verified
	 * @throws ImgurApiException
	 */
	public boolean getAccountVerified( String userName ) throws ImgurApiException {
		Call<ImgurResponseWrapper<Object>> call =
				client.getApi().getAccountVerified( userName );

		try {
			Response<ImgurResponseWrapper<Object>> res = call.execute();
			ImgurResponseWrapper<Object> out = res.body();
			client.throwOnWrapperError( res );

			return out.isSuccess();
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
	public List<GalleryItem> getAccountGalleryFavorites( String userName,
			int page ) throws ImgurApiException {

		return getAccountGalleryFavorites( userName, page, Account.GallerySort.newest);
	} // getAccountGalleryFavorites
	
	/**
	 * Given an account name, a sort, and a page number (starting at 0),
	 * return a list of GalleryItems that user has favorited.
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
	public List<GalleryItem> getAccountGalleryFavorites( String userName,
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
	} // getAccountGalleryFavorites
	
	
	
	
	
	
	// ================================================

	public AccountService(ImgurClient imgurClient, GsonBuilder gsonBuilder) {
		this.client = imgurClient;
	}

	ImgurClient client = null;

} // class AccountService
