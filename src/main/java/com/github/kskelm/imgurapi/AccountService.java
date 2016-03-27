/**
 * Imgur Account API service
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

	public List<GalleryItem> getAccountGalleryFavorites( String userName,
			int page ) throws ImgurApiException {

		return getAccountGalleryFavorites( userName, page, Account.GallerySort.newest);
	} // getAccountGalleryFavorites
	
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
