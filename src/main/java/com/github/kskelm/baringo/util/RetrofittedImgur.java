/**
 * Retrofit implementation of the Imgur 3 API
 */
package com.github.kskelm.baringo.util;

import java.util.List;
import java.util.Map;

import com.github.kskelm.baringo.CommentService.CommentListWrapper;
import com.github.kskelm.baringo.model.Account;
import com.github.kskelm.baringo.model.AccountSettings;
import com.github.kskelm.baringo.model.Album;
import com.github.kskelm.baringo.model.BasicResponse;
import com.github.kskelm.baringo.model.ChangedAccountSettings;
import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.CustomGallery;
import com.github.kskelm.baringo.model.GalleryImage;
import com.github.kskelm.baringo.model.GalleryItemProxy;
import com.github.kskelm.baringo.model.GalleryProfile;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.model.Notification;
import com.github.kskelm.baringo.model.OAuth2;
import com.github.kskelm.baringo.model.TagGallery;
import com.github.kskelm.baringo.model.TagVoteList;
import com.github.kskelm.baringo.model.Topic;
import com.github.kskelm.baringo.model.Votes;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author kskelm
 * 
 * This class is not meant to be seen by human eyes.
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
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listAccountGalleryFavorites(
			@Path("username") String userName,
			@Path("page") int page,
			@Path("sort") Account.GallerySort sort );

	@GET("/3/account/{username}/favorites")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listAccountFavorites(
			@Path("username") String userName );

	@GET("/3/account/{username}/submissions/{page}")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listAccountSubmissions(
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
	Call<ImgurResponseWrapper<GalleryProfile>> getAccountGalleryProfile(
			@Path("username") String userName );
	
	@GET("/3/account/{username}/verifyemail")
	Call<ImgurResponseWrapper<Boolean>> isAccountVerified(
			@Path("username") String userName );
	
	@POST("/3/account/{username}/verifyemail")
	Call<ImgurResponseWrapper<Boolean>> sendAccountVerificationEmail(
			@Path("username") String userName );

	@GET("/3/account/{username}/albums/{page}")
	Call<ImgurResponseWrapper<List<Album>>> listAccountAlbums(
			@Path("username") String userName,
			@Path("page") int page );

	@GET("/3/account/{username}/albums/ids/{page}")
	Call<ImgurResponseWrapper<List<String>>> listAccountAlbumIds(
			@Path("username") String userName,
			@Path("page") int page );

	@GET("/3/account/{username}/albums/count")
	Call<ImgurResponseWrapper<Integer>> getAccountAlbumCount(
			@Path("username") String userName );


	@GET("/3/account/{username}/comments/{sort}/{page}")
	Call<ImgurResponseWrapper<List<Comment>>> listAccountComments(
			@Path("username") String userName,
			@Path("sort") Comment.Sort sort,
			@Path("page") int page
			);

	@GET("/3/account/{username}/comments/ids/{sort}/{page}")
	Call<ImgurResponseWrapper<List<Integer>>> listAccountCommentIds(
			@Path("username") String userName,
			@Path("sort") Comment.Sort sort,
			@Path("page") int page
			);

	@GET("/3/account/{username}/comments/count")
	Call<ImgurResponseWrapper<Integer>> getAccountCommentCount(
			@Path("username") String userName);

	@GET("/3/account/{username}/images/{page}")
	Call<ImgurResponseWrapper<List<Image>>> listAccountImages(
			@Path("username") String userName,
			@Path("page") int page
			);

	@GET("/3/account/{username}/images/ids/{page}")
	Call<ImgurResponseWrapper<List<String>>> listAccountImageIds(
			@Path("username") String userName,
			@Path("page") int page
			);

	@GET("/3/account/{username}/images/count")
	Call<ImgurResponseWrapper<Integer>> getAccountImageCount(
			@Path("username") String userName);

	@GET("/3/account/{username}/notifications/replies")
	Call<ImgurResponseWrapper<List<Notification>>> listAccountReplyNotifications(
			@Path("username") String userName,
			@Query("new") boolean onlyNew );

	
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ALBUM CALLS
	// ============================================================
	
	@GET("/3/album/{albumId}")
	Call<ImgurResponseWrapper<Album>> getAlbum(
			@Path("albumId") String albumId );

	@GET("/3/album/{albumId}/images")
	Call<ImgurResponseWrapper<List<Image>>> getAlbumImages( @Path("albumId") String albumId );

	@POST("/3/album/")
	Call<BasicResponse<Map<String,String>>> createAlbum( @Body() Album album );
	
	@POST("/3/album/{albumId}")
	Call<BasicResponse<Boolean>> updateAlbum(
			@Path("albumId") String albumId,
			@Body() Album album );

	@DELETE("/3/album/{albumId}")
	Call<BasicResponse<Boolean>> deleteAlbum( @Path("albumId") String albumId );

	@GET("/3/album/{albumId}/favorite")
	Call<BasicResponse<Object>> toggleAlbumFavorite( @Path("albumId") String albumId );

	@FormUrlEncoded
	@PUT("/3/album/{albumId}/add")
	Call<BasicResponse<Boolean>> addAlbumImageIds(
			@Path("albumId") String albumId,
			@Field("ids") List<String> ids );

	@DELETE("/3/album/{albumId}/remove_images")
	Call<BasicResponse<Boolean>> deleteAlbumImageIds(
			@Path("albumId") String albumId,
			@Query("ids") String ids );

	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// COMMENT CALLS
	// ============================================================

	@GET("/3/comment/{id}")
	Call<ImgurResponseWrapper<Comment>> getComment(
			@Path("id") long commentId );
	
	@FormUrlEncoded
	@POST("/3/comment")
	Call<BasicResponse<Map<String,Long>>> addComment(
			@Field("image_id") String imageId,
			@Field("comment") String text );	
	
	@DELETE("/3/comment/{id}")
	Call<BasicResponse<Boolean>> deleteComment(
			@Path("id") long id );

	@GET("/3/comment/{id}/replies")
	Call<ImgurResponseWrapper<CommentListWrapper>> listCommentReplies(
			@Path("id") long commentId );
	
	@FormUrlEncoded
	@POST("/3/comment/{parent_id}")
	Call<BasicResponse<Map<String,Long>>> replyComment(
			@Field("image_id") String imageId,
			@Field("parent_id") long parentId,
			@Field("comment") String text );	
	
	@POST("/3/comment/{id}/vote/{vote}")
	Call<BasicResponse<Boolean>> voteComment(
			@Path("id") long commentId,
			@Path("vote") String vote );	

	@FormUrlEncoded
	@POST("/3/comment/{id}/report")
	Call<BasicResponse<Object>> reportComment(
			@Path("id") long commentId,
			@Field("reason") String reason );	

	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// CUSTOM GALLERY CALLS
	// ============================================================
	@GET("/3/g/{type}/{sort}/{window}/{page}")
	Call<ImgurResponseWrapper<CustomGallery>> getCustomGallery(
			@Path("type") String type,
			@Path("sort") String sort,
			@Path("window") String window,
			@Path("page") int page );

	@FormUrlEncoded
	@PUT("/3/g/custom/add_tags" )
	Call<BasicResponse<Boolean>> addCustomGalleryTags(
			@Field("tags") String tag );
			
	@DELETE("/3/g/custom/remove_tags" )
	Call<BasicResponse<Boolean>> deleteCustomGalleryTags(
			@Query("tags") String tag );
			
	@FormUrlEncoded
	@POST("/3/g/block_tag" )
	Call<BasicResponse<Boolean>> blockGalleryTag(
			@Field("tag") String tag );

	@FormUrlEncoded
	@POST("/3/g/unblock_tag" )
	Call<BasicResponse<Boolean>> unblockGalleryTag(
			@Field("tag") String tag );

	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// GALLERY CALLS
	// ============================================================
			
	@GET("/3/gallery/{section}/{sort}/{window}/{page}")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listGallery(
			@Path("section") String section,
			@Path("sort") String sort,
			@Path("window") String window,
			@Path("page") int page,
			@Query("showViral") boolean viral
			);	

	@GET("/3/g/memes/{sort}/{window}/{page}")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listMemeGallery(
			@Path("sort") String sort,
			@Path("window") String window,
			@Path("page") int page
			);	

// apparently no longer supported
//	@GET("/3/g/memes/{id}")
//	Call<ImgurResponseWrapper<GalleryMemeImage>> getMemeImageInfo(
//			@Path("id") String id );

	@GET("/3/gallery/r/{subreddit}/{sort}/{window}/{page}")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listSubredditGallery(
			@Path("subreddit") String subreddit,
			@Path("sort") String sort,
			@Path("window") String window,
			@Path("page") int page
			);	

	@GET("/3/gallery/r/{subreddit}/{id}")
	Call<ImgurResponseWrapper<GalleryImage>> getSubredditImageInfo(
			@Path("subreddit") String subreddit,
			@Path("id") String id );

	@GET("/3/gallery/t/{tag}/{sort}/{window}/{page}")
	Call<ImgurResponseWrapper<TagGallery>> getTagGallery(
			@Path("tag") String tag,
			@Path("sort") String sort,
			@Path("window") String window,
			@Path("page") int page
			);

	@GET("/3/gallery/image/{id}/tags")
	Call<ImgurResponseWrapper<TagVoteList>> getGalleryItemTagVotes(
			@Path("id") String id );
	
	
	@POST("/3/gallery/{id}/vote/tag/{tag}/{vote}")
	Call<ImgurResponseWrapper<Boolean>> voteGalleryItemTag(
			@Path("id") String id,
			@Path("tag") String tag,
			@Path("vote") String vote );
	
	@GET("/3/gallery/search/{sort}/{window}/{page}")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> searchGallery(
			@Path("sort") String sort,
			@Path("window") String window,
			@Path("page") int page,
			@Query("q") String compoundQuery,
			@Query("q_all") String allWords,
			@Query("q_any") String anyWords,
			@Query("q_exactly") String thisPhrase,
			@Query("q_not") String notThisPhrase,
			@Query("q_type") String queryType,
			@Query("q_size_px") String imageSize );
			
	@GET("/3/gallery/random/random/{page}")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listRandomGallery(
			@Path("page") int page );

	@FormUrlEncoded
	@POST("/3/gallery/{id}")
	Call<BasicResponse<Boolean>> shareGalleryItem(
			@Path("id") String itemId,
			@Field("title") String title,
			@Field("topic") int topicId,
			@Field("terms") int agreedToTerms,
			@Field("mature") int nsfw );
	
	@DELETE("/3/gallery/{id}" )
	Call<BasicResponse<Boolean>> unshareGalleryItem(
			@Path("id") String itemId );

	@POST("/3/gallery/{id}/report" )
	Call<BasicResponse<Boolean>> reportGalleryItem(
			@Path("id") String itemId,
			@Query("reason") int reason );

	@GET("/3/gallery/{id}/votes" )
	Call<ImgurResponseWrapper<Votes>> getGalleryItemVotes(
			@Path("id") String itemId );

	@GET("/3/gallery/{id}/comments/{sort}" )
	Call<ImgurResponseWrapper<List<Comment>>> getGalleryItemComments(
			@Path("id") String itemId,
			@Path("sort") String sort );

	@GET("/3/gallery/{id}/comments/ids" )
	Call<ImgurResponseWrapper<List<Long>>> getGalleryItemCommentIds(
			@Path("id") String itemId );

	@GET("/3/gallery/{id}/comments/count" )
	Call<ImgurResponseWrapper<Integer>> getGalleryItemCommentCount(
			@Path("id") String itemId );

	
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
	// TOPIC CALLS
	// ============================================================

	@GET("/3/topics/defaults")
	Call<ImgurResponseWrapper<List<Topic>>> listDefaultTopics();
	

	@GET("/3/topics/{topic_id}/{sort}/{window}/{page}")
	Call<ImgurResponseWrapper<List<GalleryItemProxy>>> listTopicItems(
			@Path("topic_id") int topicId,
			@Path("sort") String sort,
			@Path("window") String window,
			@Path("page") int page
			);
	
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// ============================================================
	// AUTHENTICATION CALLS
	// ============================================================
	@FormUrlEncoded
	@POST("/oauth2/token")
	Call<OAuth2> tradeAuthCodeForTokens(
			@Field("client_id") String clientId,
			@Field("client_secret") String clientSecret,
			@Field("grant_type") String grantType,
			@Field("code") String code );

	// returns 200 OK if the token is still good
	@HEAD("/oauth2/secret")
	Call<Object> validateToken();
	
	@FormUrlEncoded
	@POST("/oauth2/token")
	Call<OAuth2> refreshAccessToken(
			@Field("client_id") String clientId,
			@Field("client_secret") String clientSecret,
			@Field("grant_type") String grantType,
			@Field("refresh_token") String refreshToken );

} // interface RetrofittedImgur

