/**
 * Imgur Gallery service
 */
package com.github.kskelm.imgurapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.kskelm.imgurapi.model.GalleryAlbum;
import com.github.kskelm.imgurapi.model.GalleryImage;
import com.github.kskelm.imgurapi.model.GalleryItem;
import com.github.kskelm.imgurapi.model.GalleryItemProxy;
import com.github.kskelm.imgurapi.model.Image;
import com.github.kskelm.imgurapi.model.ImgurResponseWrapper;
import com.github.kskelm.imgurapi.util.ImgurApiException;
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
	 * @throws ImgurApiException - something went sideways
	 */
	List<GalleryItem> getGallery(
			GalleryImage.Section section,
			GalleryImage.Sort sort,
			int page ) throws ImgurApiException {

		String sectionStr = section.name().toLowerCase();
		String sortStr = sort.name().toLowerCase();
		
		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi().getGallery( sectionStr, sortStr, page);

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
 			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// Can't figure out how else to make type-safe classes
			// to represent both album and image objects, given
			// how Gson and Retrofit work.
			// This is so filthy that I need a shower.
			ArrayList<GalleryItem> out = new ArrayList<GalleryItem>();
			for( GalleryItemProxy proxy : list.getData() ) {
				if( proxy.is_album ) {
					GalleryAlbum album = new GalleryAlbum( proxy );
					out.add( album );
				} else {
					GalleryImage image = new GalleryImage( proxy );
					out.add( image );
				} // if-else
			} // for
			return out;
		} catch (IOException e) {
			throw new ImgurApiException( e.getMessage() );
		} 
	} // getGallery

	
	// ================================================
	
	protected GalleryService( ImgurClient client, GsonBuilder gsonBuilder ) {
		this.client = client;
	} // constructor
	
	ImgurClient client = null;

} // class AccountService
