/**
 * Imgur API service
 */
package com.github.kskelm.baringo;

import java.io.IOException;

import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Response;


/**
 * @author kskelm
 *
 */
public class ImageService {

	/**
	 * Given an image id, return info about the Image object for it.
	 * @param id - the id of the image, for example "PgZtz0j".
	 * If a user is logged in and this image is theirs, the
	 * deleteHash property will be filled in.  It will otherwise
	 * be null
	 * @return Image object
	 * @throws BaringoApiException - something went pear-shaped
	 */
	Image getImageInfo( String id ) throws BaringoApiException {
		
		Call<ImgurResponseWrapper<Image>> call =
				client.getApi().getImageInfo( id );

		try {
			Response<ImgurResponseWrapper<Image>> res = call.execute();
			ImgurResponseWrapper<Image> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	} // getImageInfo


	
	// ================================================
	protected ImageService( BaringoClient imgurClient, GsonBuilder gsonBuilder ) {
		this.client = imgurClient;
	} // constructor

	private BaringoClient client = null;

} // class AccountService
