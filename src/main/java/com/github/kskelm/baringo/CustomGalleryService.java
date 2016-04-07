/**
 * Imgur CustomGallery service {@link https://api.imgur.com/endpoints/custom_gallery}
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Response;

import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.model.gallery.CustomGallery;
import com.github.kskelm.baringo.model.gallery.GalleryImage;
import com.github.kskelm.baringo.util.BaringoApiException;

/**
 * @author kskelm
 * Imgur CustomGallery service {@link https://api.imgur.com/endpoints/custom_gallery}
 */
public class CustomGalleryService {

	/**
	 * Returns the currently authenticated user's custom
	 * gallery, which is based on tags that the user selects
	 * over time.
	 * ACCESS: AUTHENTICATED USER
	 * @param sort - the sort direction
	 * @param window - when the sort is Top, what the time range is
	 * @param page - the page number, starting from 0
	 * @return a CustomGallery object with the results
	 * @throws BaringoApiException - aw heck
	 */
	public CustomGallery getCustomGallery(
			GalleryImage.Sort sort,
			GalleryImage.Window window,
			int page ) throws BaringoApiException {

		return getSubGallery( "custom", sort, window, page );
	} // getCustomGallery

	/**
	 * Returns the currently authenticated user's filtered
	 * gallery, which is based on tags that the user selects
	 * over time.  This is a gallery of things that the user
	 * <i>explicitly does not</i> want to see, at least based
	 * on the tags on the images.
	 * ACCESS: AUTHENTICATED USER
	 * @param sort - the sort direction
	 * @param window - when the sort is Top, what the time range is
	 * @param page - the page number, starting from 0
	 * @return a CustomGallery object with the results
	 * @throws BaringoApiException - noooooo
	 */
	public CustomGallery getFilteredGallery(
			GalleryImage.Sort sort,
			GalleryImage.Window window,
			int page ) throws BaringoApiException {

		return getSubGallery( "filtered", sort, window, page );
	} // getFilteredGallery

	/**
	 * Adds a tag to the user's custom gallery.  Tags are "ored",
	 * not "anded".  If a tag already existed, no action is taken.
	 * ACCESS: AUTHENTICATED USER
	 * @param tag - the tag string to add
	 * @return true if work was done
	 * @throws BaringoApiException - something went sour
	 */
	public boolean addCustomGalleryTag( String tag ) throws BaringoApiException {
		
		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().addCustomGalleryTags( tag );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			// for some reason, Imgur throws a 500 if tags already exist (?)
			if( res.raw().code() == 500 ) {
				return false;
			} // if
			client.throwOnWrapperError( res );
			if( res.body() == null ) { // no data = nothing done (tags already set?)
				return false;
			} // if
			ImgurResponseWrapper<Boolean> out = res.body();

			return out == null ? false : out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // addCustomGalleryTag

	/**
	 * Removes a tag from the user's custom gallery tag list. No
	 * action is taken if it wasn't in there already.
	 * ACCESS: AUTHENTICATED USER
	 * @param tag - the tag string to remove
	 * @return true if action was taken
	 * @throws BaringoApiException - something went wrong
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteCustomGalleryTag( String tag ) throws BaringoApiException {
		
		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().deleteCustomGalleryTags( tag );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			// Imgur throws a 400 if the tag wasn't in the list but we just
			// want to eat it quietly, so we have to do this...
			if( res.raw().code() == 400 ) {
				String errBody = res.errorBody().string();
				Map root = new Gson().fromJson( errBody, Map.class );
				Map data = (Map) root.get( "data" );
				String err = (String)data.get( "error" );
				if( err.startsWith( "Invalid tag" ) ) {
					return false; // man that was a long way to go
				} // if
			} // if
			client.throwOnWrapperError( res );

			ImgurResponseWrapper<Boolean> out = res.body();

			return out == null ? false : out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // deleteCustomGalleryTag

	/**
	 * This adds a tag to the user's global gallery block list.
	 * ACCESS: AUTHENTICATED USER
	 * @param tag - a tag string the user no longer wants to see
	 * @return true if action was taken
	 * @throws BaringoApiException - something terrible happened
	 */
	public boolean blockGalleryTag( String tag ) throws BaringoApiException {
		
		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().blockGalleryTag( tag );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			client.throwOnWrapperError( res );
			ImgurResponseWrapper<Boolean> out = res.body();

			return out == null ? false : out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // blockGalleryTag

	/**
	 * Remove a tag from the user's global gallery block list.
	 * ACCESS: AUTHENTICATED USER
	 * @param tag - the tag to remove
	 * @return true if action was taken
	 * @throws BaringoApiException - something went sideways
	 */
	public boolean unblockGalleryTag( String tag ) throws BaringoApiException {
		
		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().unblockGalleryTag( tag );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			client.throwOnWrapperError( res );
			ImgurResponseWrapper<Boolean> out = res.body();

			return out == null ? false : out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // unblockGalleryTag

	// ====================================================================


	private CustomGallery getSubGallery(
			String type,
			GalleryImage.Sort sort,
			GalleryImage.Window window,
			int page ) throws BaringoApiException {

		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();
		
		Call<ImgurResponseWrapper<CustomGallery>> call =
				client.getApi().getCustomGallery( type, sortStr, windowStr, page);

		try {
			Response<ImgurResponseWrapper<CustomGallery>> res = call.execute();
 			ImgurResponseWrapper<CustomGallery> out = res.body();

			client.throwOnWrapperError( res );
			CustomGallery gal = out.getData();
			
			// TODO This is so filthy that I need a shower. 
			gal.setConvertedItems(
					client.galleryService()
							.convertToGalleryItems(  gal.getInternalItems() ) );
			return gal;
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // listStarGallery

	protected CustomGalleryService( BaringoClient client, GsonBuilder gsonBuilder ) {
		this.client = client;
	} 
	
	private BaringoClient client = null;

}
