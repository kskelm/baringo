/**
 * Retrofit implementation of the Imgur 3 API
 */
package com.github.kskelm.imgurapi.util;

import java.util.List;

import com.github.kskelm.imgurapi.model.Account;
import com.github.kskelm.imgurapi.model.AccountSettings;
import com.github.kskelm.imgurapi.model.Album;
import com.github.kskelm.imgurapi.model.ChangedAccountSettings;
import com.github.kskelm.imgurapi.model.Comment;
import com.github.kskelm.imgurapi.model.GalleryItem;
import com.github.kskelm.imgurapi.model.GalleryItemProxy;
import com.github.kskelm.imgurapi.model.GalleryProfile;
import com.github.kskelm.imgurapi.model.Image;
import com.github.kskelm.imgurapi.model.ImgurResponseWrapper;
import com.github.kskelm.imgurapi.model.Notification;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author kskelm
 *
 */
public interface RetrofittedImgur {

	// apparently Retrofit 2 doesn't want to support multiple
	// interfaces in the same API.  It's onerous and a pain to
	// create multiple clients for the same API, so we're just
	// going to jam them all in here.
	
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ACCOUNT CALLS
	// ============================================================

	@GET("/3/account/{username}")
	Call<ImgurResponseWrapper<Account>> getAccount(
			@Path("username") String userName );

	@GET("/3/account/{username}/gallery_favorites/{page}/{sort}")
	Call<ImgurResponseWrapper<List<GalleryItem>>> listAccountGalleryFavorites(
			@Path("username") String userName,
			@Path("page") int page,
			@Path("sort") Account.GallerySort sort );

	@GET("/3/account/{username}/favorites")
	Call<ImgurResponseWrapper<List<GalleryItem>>> listAccountFavorites(
			@Path("username") String userName );

	@GET("/3/account/{username}/submissions/{page}")
	Call<ImgurResponseWrapper<List<GalleryItem>>> listAccountSubmissions(
			@Path("username") String userName,
			@Path("page") int page );

	@GET("/3/account/{username}/settings")
	Call<ImgurResponseWrapper<AccountSettings>> getAccountSettings(
			@Path("username") String userName );

	@PUT("/3/account/{username}/settings")
	Call<ImgurResponseWrapper<Object>> setAccountSettings(
			@Path("username") String userName,
			@Body ChangedAccountSettings settings );

	@GET("/3/account/{username}/gallery_profile")
	Call<ImgurResponseWrapper<GalleryProfile>> getGalleryProfile(
			@Path("username") String userName );
	
	// TODO PICK UP HERE -- MAY REQUIRE REMOVING USERNAME FROM SIGNATURE
	@GET("/3/account/{username}/verifyemail")
	Call<ImgurResponseWrapper<Object>> isVerified(
			@Path("username") String userName );
	// TODO PICK UP HERE -- MAY REQUIRE REMOVING USERNAME FROM SIGNATURE
	@POST("/3/account/{username}/verifyemail")
	Call<ImgurResponseWrapper<Object>> sendVerificationEmail(
			@Path("username") String userName );

	// TODO PICK UP HERE
	@GET("/3/account/{username}/album/{page}")
	Call<ImgurResponseWrapper<List<Album>>> listAlbums(
			@Path("username") String userName,
			@Path("page") int page );
	// TODO PICK UP HERE
	@GET("/3/account/{username}/album/{page}")
	Call<ImgurResponseWrapper<List<Object>>> listAlbumIds(
			@Path("username") String userName,
			@Path("page") int page );
	// TODO PICK UP HERE
	@GET("/3/account/{username}/album/count")
	Call<ImgurResponseWrapper<Object>> getAlbumCount(
			@Path("username") String userName );

	// TODO PICK UP HERE
	@GET("/3/account/{username}/comments/{sort}/{page}")
	Call<ImgurResponseWrapper<List<Comment>>> listComments(
			@Path("username") String userName,
			@Path("sort") Comment.Sort sort,
			@Path("page") int page
			);
	// TODO PICK UP HERE
	@GET("/3/account/{username}/comments/ids/{page}")
	Call<ImgurResponseWrapper<List<Object>>> listCommentIds(
			@Path("username") String userName,
			@Path("page") int page
			);
	// TODO PICK UP HERE
	@GET("/3/account/{username}/comments/count")
	Call<ImgurResponseWrapper<Object>> getCommentCount(
			@Path("username") String userName);

	// TODO PICK UP HERE
	@GET("/3/account/{username}/images/{page}")
	Call<ImgurResponseWrapper<List<Image>>> listImages(
			@Path("username") String userName,
			@Path("page") int page
			);
	// TODO PICK UP HERE
	@GET("/3/account/{username}/images/ids/{page}")
	Call<ImgurResponseWrapper<List<Object>>> listImageIds(
			@Path("username") String userName,
			@Path("page") int page
			);
	// TODO PICK UP HERE
	@GET("/3/account/{username}/images/count")
	Call<ImgurResponseWrapper<Object>> getImageCount(
			@Path("username") String userName);

	// TODO PICK UP HERE
	@GET("/3/account/{username}/notifications/replies")
	Call<ImgurResponseWrapper<List<Notification>>> getReplyNotifications(
			@Path("username") String userName);

	
	
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// IMAGE CALLS
	// ============================================================

	@GET("/3/image/{id}")
	Call<ImgurResponseWrapper<Image>> getImageInfo(
			@Path("id") String id );

	
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// GALLERY CALLS
	// ============================================================
	@GET("/3/gallery/{section}/{sort}/{window}/{page}?showViral={viral}")
	Call<List<GalleryItemProxy>> getGallery(
			@Query("page") int page,
			@Query("section") GalleryItem.Section section,
			@Query("sort") GalleryItem.Sort sort,
			@Query("window") GalleryItem.Window window,
			@Query("viral") boolean viral
			);	

	@GET("/3/gallery/{section}/{sort}/{page}.json")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> getGallery(
			@Path("section") String section,
			@Path("sort") String sort,
			@Path("page") int page );	

} // interface RetrofittedImgur

