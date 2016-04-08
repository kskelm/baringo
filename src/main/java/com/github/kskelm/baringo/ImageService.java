/**
 * API service for handling images
 */
package com.github.kskelm.baringo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.github.kskelm.baringo.util.BaringoAuthException;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

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
	public Image getImageInfo( String id ) throws BaringoApiException {

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
	}

	/**
	 * Upload an image to Imgur by pointing at a Url on the internet.
	 * Must be available openly without authentication.
	 * ACCESS: ANONYMOUS
	 * @param Url - the full URL of the image.
	 * @param fileName - original of the file being uploaded (pick something)
	 * @param albumId - the name of the album, the album's deleteHash if it's anonymous, or null if none
	 * @param title - title of image or null if none
	 * @param description - description of image or null if none
	 * @return The new Image object.  If this is anonymous, <i>hang on to the delete hash</i> or you won't be able to manipulate it in the future!</i>
	 */
	public Image uploadUrlImage(
			String Url,
			String fileName,
			String albumId,
			String title,
			String description ) throws BaringoApiException {

		RequestBody body = RequestBody.create(
				MediaType.parse("text/plain"), Url );

		Call<ImgurResponseWrapper<Image>> call =
				client.getApi().uploadUrlImage(
						albumId,
						"URL",
						title,
						description,
						body );

		try {
			Response<ImgurResponseWrapper<Image>> res = call.execute();
			ImgurResponseWrapper<Image> out = res.body();

			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Upload an image to Imgur as a stream from the local filesystem.
	 * Use a buffered stream wherever possible!
	 * ACCESS: ANONYMOUS
	 * @param mimeType - mime type like image/png.  If null, Baringo will try to infer this from the fileName.
	 * @param fileName - name of the file being uploaded
	 * @param albumId - the name of the album, the album's deleteHash if it's anonymous, or null if none
	 * @param title - title of image or null if none
	 * @param description - description of image or null if none
	 * @return The new Image object.  If this is anonymous, <i>hang on to the delete hash</i> or you won't be able to manipulate it in the future!</i>
	 * @throws IOException - Something was wrong with the file or streaming didn't work
	 * @throws BaringoApiException - que sera sera
	 */
	public Image uploadLocalImage(
			String mimeType,
			String fileName,
			String albumId,
			String title,
			String description ) throws IOException, BaringoApiException { // can be null
		
		File file = new File( fileName );

		if( !file.exists() ) {
			throw new FileNotFoundException( "File not found: " + fileName );
		} // if
		if( !file.canRead() ) {
			throw new IOException( "Cannot access file " + fileName );
		} // if
		if( mimeType == null ) { // infer from file prefix
			int dotAt = fileName.lastIndexOf( '.' );
			if( dotAt == -1 ) {
				throw new BaringoApiException( "Could not infer mime type"
						+ " from file name; no extension" );
			} // if
			String ext = fileName.substring( dotAt + 1 ).toLowerCase();
			mimeType = extensionToMimeType.get( ext );
			if( mimeType == null ) {
				throw new BaringoApiException( "Could not infer mime type"
						+ " from extension '" + ext + "'" );
			} // if
		} // if

		// strip the directory hierarchy off the filename.
		Path path = Paths.get( fileName );
		fileName = path.getFileName().toString();
		
		RequestBody body = RequestBody.create( MediaType.parse(mimeType), file );
		Call<ImgurResponseWrapper<Image>> call =
				client.getApi().uploadLocalImage(
						albumId,
						"file",
						title,
						description,
						fileName,
						body );
		
		try {
			Response<ImgurResponseWrapper<Image>> res = call.execute();
			ImgurResponseWrapper<Image> out = res.body();

			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch

	}

	/**
	 * Given an image id and an output stream, download the image
	 * and write it to the stream. It is the caller's responsibility
	 * to close everything.
	 * NOTE: This is synchronous.
	 * @param imageLink - the image link to download (could be a thumb too)
	 * @param outStream - an output stream to write the data to
	 * @return the number of bytes written
	 * @throws IOException - could be anything really
	 * @throws BaringoApiException - Imgur didn't like something
	 */
	public long downloadImage(
			String imageLink,
			OutputStream outStream ) throws IOException, BaringoApiException {

		Request request = new Request
				.Builder()
				.url( imageLink )
				.build();

		OkHttpClient client = new OkHttpClient();
		com.squareup.okhttp.Response resp = client
				.newCall( request )
				.execute();

		if( resp.code() != 200 || !resp.isSuccessful() ) {
			throw new BaringoApiException( request.urlString()
					+ ": " +  resp.message(), resp.code() );
		} // if
		if( resp.body() == null ) {
			throw new BaringoApiException( "No response body found" );
		} // if

		InputStream is = resp.body().byteStream();

		BufferedInputStream input = new BufferedInputStream(is);
		byte[] data = new byte[8192]; // because powers of two are magic

		long total = 0;
		int count = 0;
		while ((count = input.read(data)) != -1) {
			total += count;
			outStream.write(data, 0, count);
		} // while

		return total;
	}

	/**
	 * Given an image id and a file path to store it to, download
	 * the image.  File must be writeable and the path must exist.
	 * NOTE: This is synchronous.
	 * @param imageLink - the image link to download (could be a thumb too)
	 * @param fileName - name of the file to write to
	 * @return the number of bytes written
	 * @throws IOException - myriad
	 * @throws BaringoApiException - Imgur didn't like something
	 */
	public long downloadImage(
			String imageLink,
			String fileName ) throws IOException, BaringoApiException {

		OutputStream output = null;
		try {
			output = new BufferedOutputStream(
					new FileOutputStream( fileName) );

			return downloadImage( imageLink, output );
		} finally {
			output.close();
		} // try-finally
	}

	/**
	 * Updates an image with a new title and description
	 * ACCESS: ANONYMOUS or AUTHENTICATED USER
	 * @param idOrDeleteHash - If the image is anonymous, this is the delete hash. If not then it's an imageId and the currently-authenticated account must own it.
	 * @param title - title of the image or null if none
	 * @param description - description of the image or null if none
	 * @return true if it worked
	 * @throws BaringoApiException - C'est la vie
	 */
	public boolean updateImage(
			String idOrDeleteHash,
			String title,
			String description ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().updateImageInfo( idOrDeleteHash, title, description);

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();

			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch

	}

	/**
	 * Deletes an image
	 * ACCESS: ANONYMOUS or AUTHENTICATED USER
	 * @param idOrDeleteHash - If the image is anonymous, this is the delete hash. If not then it's an imageId and the currently-authenticated account must own it.
	 * @return true if it worked
	 * @throws BaringoApiException - C'est la vie
	 */
	public boolean deleteImage(
			String idOrDeleteHash ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().deleteImage( idOrDeleteHash );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();

			client.throwOnWrapperError( res );
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch

	}

	/**
	 * Marks the image as favorite for the currently-authenticated user.
	 * Note that the Image object needs to be in sync with Imgur because
	 * the site's API only acknowledges a toggle of the value.  The image
	 * object is updated with the new status.  If the image is already
	 * marked as a favorite, no action is taken.
	 * NOTE: An account can't favorite its own images.
	 * ACCESS: AUTHENTICATED USER
	 * @param image - the image to favorite.
	 * @return the updated image object
	 * @throws BaringoApiException - argh
	 */
	public Image favoriteImage( Image image ) throws BaringoApiException {
		if( !client.authService().isUserAuthenticated() ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if

		if( image.isFavorite() ) { 
			return image; // already done
		} // if

		Call<ImgurResponseWrapper<Image>> call =
				client.getApi().toggleImageFavorite( image.getId() );

		try {
			Response<ImgurResponseWrapper<Image>> res = call.execute();
			ImgurResponseWrapper<Image> out = res.body();
			client.throwOnWrapperError( res );
			
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Unfavorites the image for the currently-authenticated user.
	 * Note that the Image object needs to be in sync with Imgur because
	 * the site's API only acknowledges a toggle of the value.  The image
	 * object is updated with the new status.  If the image is already
	 * not favorited, no action is taken.
	 * NOTE: An account can't favorite its own images.
	 * ACCESS: AUTHENTICATED USER
	 * @param image - the image to favorite.
	 * @return the updated image object
	 * @throws BaringoApiException - argh
	 */
	public Image unfavoriteImage( Image image ) throws BaringoApiException {
		if( !client.authService().isUserAuthenticated() ) {
			throw new BaringoAuthException( "No user logged in", 401 );
		} // if

		if( !image.isFavorite() ) { 
			return image; // already done
		} // if

		Call<ImgurResponseWrapper<Image>> call =
				client.getApi().toggleImageFavorite( image.getId() );

		try {
			Response<ImgurResponseWrapper<Image>> res = call.execute();
			ImgurResponseWrapper<Image> out = res.body();
			client.throwOnWrapperError( res );
			
			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}


	// ================================================
	protected ImageService( BaringoClient imgurClient, GsonBuilder gsonBuilder ) {
		this.client = imgurClient;
		
		extensionToMimeType.put( "apng", "image/png" );
		extensionToMimeType.put( "gif", "image/gif" );
		extensionToMimeType.put( "jpeg", "image/jpeg" );
		extensionToMimeType.put( "jpg", "image/jpeg" );
		extensionToMimeType.put( "pdf", "application/pdf" );
		extensionToMimeType.put( "png", "image/png" );
		extensionToMimeType.put( "tif", "image/tiff" );
		extensionToMimeType.put( "tiff", "image/tiff" );
		extensionToMimeType.put( "xcf", "image/xcf" );
	} // constructor

	private BaringoClient client = null;
	private HashMap<String,String> extensionToMimeType = new HashMap<>();
	
} // class AccountService
