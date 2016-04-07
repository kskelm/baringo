/**
 * API service for memes {@link https://api.imgur.com/endpoints/memegen}
 */
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
 * @author kskelm
 * API service for topics {@link https://api.imgur.com/endpoints/memegen}
 */
public class MemeService {

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
