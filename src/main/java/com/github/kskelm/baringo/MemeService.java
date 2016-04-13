/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.List;

import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Response;


/**
 *
 * API services for memes.
 * See <a href="https://api.imgur.com/endpoints/memegen">Imgur docs</a>
 * @author Kevin Kelm (triggur@gmail.com)
 */
public class MemeService {

	/**
	 * Returns a list of the default meme images.
	 * @return image list
	 * @throws BaringoApiException - well, heck.
	 */
	public List<Image> listDefaultMemes() throws BaringoApiException {
		
		Call<ImgurResponseWrapper<List<Image>>> call =
				client.getApi().listDefaultMemes();

		try {
			Response<ImgurResponseWrapper<List<Image>>> res = call.execute();
			ImgurResponseWrapper<List<Image>> out = res.body();

			client.throwOnWrapperError( res );
			return out.getData();

		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	} 

	
	// ================================================
	protected MemeService( BaringoClient imgurClient, GsonBuilder gsonBuilder ) {
		this.client = imgurClient;
	} // constructor

	private BaringoClient client = null;

}
