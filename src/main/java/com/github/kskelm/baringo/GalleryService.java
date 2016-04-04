/**
 * Imgur Gallery service
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.kskelm.baringo.model.GalleryAlbum;
import com.github.kskelm.baringo.model.GalleryImage;
import com.github.kskelm.baringo.model.GalleryItem;
import com.github.kskelm.baringo.model.GalleryItemProxy;
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
public class GalleryService {

	/**
	 * This returns a list of items in a gallery.
	 * <p>
	 * It returns a list of abstract class GalleryItemProxy,
	 * each of which may actually be a GalleryAlbum or a
	 * GalleryImage.  Imgur API 3 is a little unfriendly to
	 * statically typed languages.
	 * @param section - the section of the gallery.  May
	 * be one of {@link Image.Section}
	 * @param sort - the sort for the results.  May be
	 * one {@link Image.Sort}
	 * @param page - the page number to return, starting at 0
	 * @return A list of GalleryItemProxy objects
	 * @throws BaringoApiException - something went sideways
	 */
	List<GalleryItem> getGallery(
			GalleryImage.Section section,
			GalleryImage.Sort sort,
			int page ) throws BaringoApiException {

		String sectionStr = section.name().toLowerCase();
		String sortStr = sort.name().toLowerCase();
		
		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi().listGallery( sectionStr, sortStr, page);

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
 			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// Can't figure out how else to make type-safe classes
			// to represent both album and image objects, given
			// how Gson and Retrofit work.
			// This is so filthy that I need a shower.
			return convertToItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	} // getGallery

	// ================================================

	// this approach feels filthy. Convert lame proxy objects to
	// type-safe GalleryItem derivatives.  
	protected List<GalleryItem> convertToItems( List<GalleryItemProxy> list ) {
		ArrayList<GalleryItem> items = new ArrayList<>();
		
		for( GalleryItemProxy proxy : list ) {
			GalleryItem item = null;
			if( proxy.isAlbum() ) {
				item = new GalleryAlbum( proxy );
			} else {
				item = new GalleryImage( proxy );
			} // if-else
			if( item != null ) {
				items.add( item );
			} // if
		} // for
		return items;
	} // convertToItems
	
	protected GalleryService( BaringoClient client, GsonBuilder gsonBuilder ) {
		this.client = client;
	} // constructor
	
	private BaringoClient client = null;

} // class AccountService
