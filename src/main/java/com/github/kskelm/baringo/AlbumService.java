/**
 * Imgur Album API services
 * See <a href="http://api.imgur.com/endpoints/album">Imgur Albums</a> for API details
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.kskelm.baringo.model.Album;
import com.github.kskelm.baringo.model.BasicResponse;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.github.kskelm.baringo.util.BaringoAuthException;
import com.github.kskelm.baringo.util.Utils;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Response;


/**
 * @author kskelm
 *
 */
public class AlbumService {

	/**
	 * Given an album id, return the Album object for it.
	 * ACCESS: ANONYMOUS
	 * @param albumId - the id of the album to fetch
	 * @return Album object
	 * @throws BaringoApiException - something went pear-shaped
	 */
	public Album getAlbum( String albumId ) throws BaringoApiException {
		Call<ImgurResponseWrapper<Album>> call =
				client.getApi().getAlbum( albumId );

		try {
			Response<ImgurResponseWrapper<Album>> res = call.execute();
			ImgurResponseWrapper<Album> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // getAlbum

	/**
	 * Given an album id, return a list of images in that album.
	 * This method is not paged, so you're going to get <i>all</i>
	 * of them.
	 * ACCESS: ANONYMOUS
	 * @param albumId - the album id to fetch images for
	 * @return list of Image objects
	 * @throws BaringoApiException - bad
	 */
	public List<Image> getAlbumImages( String albumId ) throws BaringoApiException {
		Call<ImgurResponseWrapper<List<Image>>> call =
				client.getApi().getAlbumImages( albumId );

		try {
			Response<ImgurResponseWrapper<List<Image>>> res = call.execute();
			ImgurResponseWrapper<List<Image>> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // getAlbumImages

	/**
	 * Create a new album. Only the following fields in the
	 * album object can be set at creation by the API:
	 *    title
	 *    description
	 *    privacy
	 *    layout
	 *    cover id 
	 * ACCESS: ANONYMOUS OR AUTHENTICATED USER
	 * If anonymous, <i>save the delete hash</i>
	 *  or you'll never be able to
	 * manipulate the album again.
	 * @param album - the new album to save to Imgur
	 * @return The new album
	 * @throws BaringoApiException - oops
	 */
	public Album addAlbum( Album album ) throws BaringoApiException {

		album.prepareForSave(); // create imageid array for saving

		Call<BasicResponse<Map<String,String>>> call =
				client.getApi().createAlbum( album );

		try {
			Response<BasicResponse<Map<String,String>>> res = call.execute();
			BasicResponse<Map<String,String>> out = res.body();
			client.throwOnBasicWrapperError( res );
			
			Album saved = getAlbum( out.getData().get( "id" ) );
			album.copyFrom( saved );
			album.setDeleteHash( out.getData().get( "deletehash" ) );
			
			return album;
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // addAlbum

	/**
	 * Update an existing album.
	 * ACCESS: ANONYMOUS OR AUTHENTICATED USER
	 * If anonymous, the album id must be the delete hash.
	 * NOTE: Only the following fields can be updated via the API:
	 * 	images
	 * 	title
	 * 	description
	 * 	privacy
	 * 	layout
	 * 	cover id 
	 * @param album - the album to update
	 * @return True if successful update
	 * @throws BaringoApiException - oops
	 */
	public boolean updateAlbum( Album album ) throws BaringoApiException {

		album.prepareForSave(); // create imageid array for saving


		Call<BasicResponse<Boolean>> call =
				client.getApi().updateAlbum( album.getAPIReferenceKey(), album );

		try {
			Response<BasicResponse<Boolean>> res = call.execute();
			BasicResponse<Boolean> out = res.body();
			client.throwOnBasicWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // updateAlbum

	/**
	 * Deletes an album from Imgur.  The images contained
	 * in it are not affected.
	 * ACCESS: ANONYMOUS OR AUTHENTICATED USER
	 * If anonymous, the album id must be the delete hash.
	 * If the album id is an actual album id and not a delete
	 * hash, the currently-logged-in user must own that album.
	 * @param album - the album to delete
	 * @return true if success
	 * @throws BaringoApiException - something bad
	 */
	public boolean deleteAlbum( Album album ) throws BaringoApiException {
//		String id = album.getId();
//		if( !client.authService().isUserAuthenticated() ) {
//			id = album.getDeleteHash();
//		} // if
//		if( id == null ) {
//			throw new BaringoApiException( "Album is not owned by logged-in user, or delete hash unknown" );
//		}
		Call<BasicResponse<Boolean>> call =
				client.getApi().deleteAlbum( album.getAPIReferenceKey() );

		try {
			Response<BasicResponse<Boolean>> res = call.execute();
			BasicResponse<Boolean> out = res.body();
			client.throwOnBasicWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	} // deleteAlbum

	/**
	 * Favorite the given album for the currently-logged-in user.
	 * If the album object is already marked as a favorite, nothing
	 * is done. <i>NOTE: Don't set the favorite status in the
	 * album object and then call this, let this update the album
	 * object.</i>
	 * This won't have the intended effect if the album object
	 * was created before the current user's authentication.
	 * ACCESS: AUTHENTICATED USER
	 * @param album - the album to mark as a favorite
	 * @throws BaringoApiException - well nuts
	 */
	public void favoriteAlbum( Album album ) throws BaringoApiException {
		if( !client.authService().isUserAuthenticated() ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if

		if( album.isFavorite() ) { 
			return; // already done
		} // if

		Call<BasicResponse<Object>> call =
				client.getApi().toggleAlbumFavorite( album.getId() );

		try {
			Response<BasicResponse<Object>> res = call.execute();
			client.throwOnBasicWrapperError( res );
			album.setFavorite( true );
			return;
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // favoriteAlbum

	/**
	 * Unfavorite the given album for the currently-logged-in user.
	 * If the album object is already not a favorite, nothing
	 * is done. <i>NOTE: Don't set the favorite status in the
	 * album object and then call this, let this update the album
	 * object.</i>
	 * This won't have the intended effect if the album object
	 * was created before the current user's authentication.
	 * ACCESS: AUTHENTICATED USER
	 * @param album - the album to mark as a favorite
	 * @throws BaringoApiException - well nuts
	 */
	public void unfavoriteAlbum( Album album ) throws BaringoApiException {
		if( !client.authService().isUserAuthenticated() ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if
		
		if( !album.isFavorite() ) { 
			return; // already done
		} // if

		Call<BasicResponse<Object>> call =
				client.getApi().toggleAlbumFavorite( album.getId() );

		try {
			Response<BasicResponse<Object>> res = call.execute();
			client.throwOnBasicWrapperError( res );
			album.setFavorite( false );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // unfavoriteAlbum


	/**
	 * Adds the given image id to the album.
	 * Though expensive, the image is loaded from Imgur
	 * so that getImages() returns the right data.
	 * @param album - the album to add images to
	 * @param imageId - the image to add
	 * @return the updated Album
	 * @throws BaringoApiException - oh no!
	 */
	public Album addAlbumImageId(
			Album album,
			String imageId ) throws BaringoApiException {
		List<String> imageIds = new ArrayList<>();
		imageIds.add( imageId );
		return addAlbumImageIds( album, imageIds );
	} // addAlbumImageId

	/**
	 * Adds the given list of image id's to the album.
	 * Though expensive, the album is reloaded from Imgur
	 * so that getImages() returns the right data.
	 * @param album - the album to add images to
	 * @param imageIds - a list of image ids to add
	 * @return If successful, the updated album, else null
	 * @throws BaringoApiException - ugh
	 */
	public Album addAlbumImageIds(
			Album album,
			List<String> imageIds ) throws BaringoApiException {

		Call<BasicResponse<Boolean>> call =
				client.getApi().addAlbumImageIds( album.getAPIReferenceKey(), imageIds );

		try {
			Response<BasicResponse<Boolean>> res = call.execute();
			BasicResponse<Boolean> out = res.body();
			client.throwOnBasicWrapperError( res );
			if( out.getData() ) { // success!
				if( imageIds.size() == 1 ) { // faster just to load the one image
					Image img = client.imageService().getImageInfo( imageIds.get( 0 ) );
					album.getImages().add( img );
					return album;
				} else {
					Album album2 = getAlbum( album.getId() );
					album.setImages( album2.getImages() );
					return album;
				} // if-else
			} else {
				return null;
			} // if

		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // addAlbumImageIds

	/**
	 * deletes the given image id from the album.
	 * @param album - the album to delete images from
	 * @param imageId - the id of the image to delete from the album
	 * @return If successful, the updated album, else null
	 * @throws BaringoApiException - argh
	 */
	public Album deleteAlbumImageId(
			Album album, String imageId ) throws BaringoApiException {
		List<String> list = new ArrayList<>();
		list.add( imageId );
		
		return deleteAlbumImageIds( album, list );
	} // deleteAlbumImageId


	/**
	 * deletes the given list of image id's from the album.
	 * @param album - the album to delete images from
	 * @param imageIds - a list of image ids to delete from the album
	 * @return If successful, the updated album, else null
	 * @throws BaringoApiException - ugh
	 */
	public Album deleteAlbumImageIds(
			Album album,
			List<String> imageIds ) throws BaringoApiException {
		
		
		String joinedIds = Utils.joinCSV( imageIds );
		
		Call<BasicResponse<Boolean>> call =
				client.getApi().deleteAlbumImageIds( album.getAPIReferenceKey(), joinedIds );

		try {
			Response<BasicResponse<Boolean>> res = call.execute();
			BasicResponse<Boolean> out = res.body();
			client.throwOnBasicWrapperError( res );
			if( out.getData() ) { // success!
				List<Image> newList = new ArrayList<>();
				for( Image image : album.getImages() ) {
					if( !imageIds.contains( image.getId() ) ) {
						newList.add( image );
					} // if
				} // for
				album.setImages( newList );
				return album;
			} else {
				return null;
			} // if-else

		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	} // deleteAlbumImageIds

	
	
	
	// ===================================================

	protected AlbumService(BaringoClient imgurClient, GsonBuilder gsonBuilder) {
		this.client = imgurClient;
	}

	private BaringoClient client = null;

} // class AlbumService
