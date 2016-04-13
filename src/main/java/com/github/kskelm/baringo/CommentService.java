/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.ReportReason;
import com.github.kskelm.baringo.model.Vote;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import retrofit.Call;
import retrofit.Response;


/**
 * Manages comments, which can be attached to images and albums.
 * @author Kevin Kelm (triggur@gmail.com)
 */
public class CommentService {
	
	/**
	 * Given a comment id, return the Comment object for it.
	 * <p>
        * <b>ACCESS: ANONYMOUS</b>
	 * @param commentId the id of the comment to fetch
	 * @return a Comment object
	 * @throws BaringoApiException daaang
	 */
	public Comment getComment( long commentId ) throws BaringoApiException {
		Call<ImgurResponseWrapper<Comment>> call =
				client.getApi().getComment( commentId );

		try {
			Response<ImgurResponseWrapper<Comment>> res = call.execute();
			ImgurResponseWrapper<Comment> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // getComment

	/**
	 * Add a comment to the given image or album.
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param imageOrAlbumId id of the thing to attach the comment to
	 * @param text text body of the comment
	 * @return the id of the new comment
	 * @throws BaringoApiException danger
	 */
	public long addComment(
			String imageOrAlbumId,
			String text ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Map<String,Long>>> call =
				client.getApi().addComment( imageOrAlbumId, text );

		try {
			Response<ImgurResponseWrapper<Map<String,Long>>> res = call.execute();
			ImgurResponseWrapper<Map<String,Long>> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData().get( "id" );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	
	/**
	 * Deletes a comment.
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param comment the comment object to delete from Imgur
	 * @return whether it worked
	 * @throws BaringoApiException bummer
	 */
	public boolean deleteComment(
			Comment comment ) throws BaringoApiException {
		return deleteComment( comment.getId() );
	}


	/**
	 * Deletes a comment.
	 * <p>
        * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param commentId the comment object to delete from Imgur
	 * @return whether it worked
	 * @throws BaringoApiException bummer
	 */
	public boolean deleteComment(
			long commentId ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().deleteComment( commentId );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	}

	/**
	 * Return the list of reply comments for a given comment
	 * <p>
        * <b>ACCESS: ANONYMOUS</b>
	 * @param commentId the id of the parent comment
	 * @return a list of comment objects, non-paged
	 * @throws BaringoApiException oh no
	 */
	public List<Comment> listReplies(
			long commentId ) throws BaringoApiException {

		Call<ImgurResponseWrapper<CommentListWrapper>> call =
				client.getApi().listCommentReplies( commentId );

		try {
			Response<ImgurResponseWrapper<CommentListWrapper>> res = call.execute();
			ImgurResponseWrapper<CommentListWrapper> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData().comms;
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	}

	/**
	 * Add a reply to the given parent comment
	 * <p>
	 * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param parent the parent comment
	 * @param text the text body of the comment to add
	 * @return the id of the new comment
	 * @throws BaringoApiException i can't keep making up dummy explanations here
	 */
	public long addReply(
			Comment parent,
			String text ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Map<String,Long>>> call =
				client.getApi().replyComment( parent.getImageId(), parent.getId(), text );

		try {
			Response<ImgurResponseWrapper<Map<String,Long>>> res = call.execute();
			ImgurResponseWrapper<Map<String,Long>> out = res.body();
			client.throwOnWrapperError( res );
			return out.getData().get( "id" );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	}

	/**
	 * Sets the vote on the comment for the currently-authenticated
	 * user.  For some reason this doesn't behave like the user interface;
	 * the best you can do is nullify your previous vote, not undo it.
	 * <p>
	 * If your previous vote was "up" then downvoting it turns it to null.
	 * If you downvote again, nothing happens, whereas in the web UI,
	 * downvoting turns the result to an actual "down" the next time you
	 * load the comment.  Odd.
	 * <p>
     * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param commentId id of the comment to vote on
	 * @param vote vote up or down
	 * @return true if it worked
	 * @throws BaringoApiException Imgur didn't like that
	 */
	public boolean setVote(
			long commentId,
			Vote vote) throws BaringoApiException {

		String voteStr = vote.name().toLowerCase();
		
		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().voteComment( commentId, voteStr );

		try {
			Response<ImgurResponseWrapper<Boolean>> res = call.execute();
			ImgurResponseWrapper<Boolean> out = res.body();
			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Report the given comment as abusive or whatever
	 * <p>
	 * <b>ACCESS: AUTHENTICATED USER</b>
	 * @param commentId the id of the comment to report
	 * @param reason supply a reason
	 * @throws BaringoApiException ouch
	 */
	public void report(
			long commentId,
			ReportReason reason ) throws BaringoApiException {

		String reasonStr = reason.name().toLowerCase();
		Call<ImgurResponseWrapper<Object>> call =
				client.getApi().reportComment( commentId, reasonStr );

		try {
			Response<ImgurResponseWrapper<Object>> res = call.execute();
			client.throwOnWrapperError( res );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	// ===================================================

	protected CommentService(BaringoClient imgurClient, GsonBuilder gsonBuilder) {
		this.client = imgurClient;
		gsonBuilder.registerTypeAdapter( CommentListWrapper.class, new CommentListWrapper() );
	}

	
   /**
 	* Imgur's Json serializer-- like many-- doesn't know when
	* it should be generating an array if it only finds one item
	* in a list so instead of an array with one item, it just
	* generates the item as an object, and parsers the whole
	* world over break.  This is ridiculous
	* and it's been the source of so many unexpected crashes
	* in enterprise applications that I cannot count them all.
	* So here we need to resort to crappy tricks.
	*
	*/
	public class CommentListWrapper implements JsonDeserializer<CommentListWrapper> {
		List<Comment> comms = null;

		@Override
		public CommentListWrapper deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

			JsonElement elem = json.getAsJsonObject();
			CommentListWrapper wrap = new CommentListWrapper();
			wrap.comms = new ArrayList<Comment>();
			if (elem.isJsonArray()) {
				Comment[] comms = context.deserialize(elem.getAsJsonArray(), Comment[].class);
				for( Comment comm : comms ) {
					wrap.comms.add( comm );
				} // for
			} else if (elem.isJsonObject()) {
				Comment comm = (Comment) context.deserialize(elem.getAsJsonObject(), Comment.class);
				wrap.comms.add( comm );
			} else {
				throw new JsonParseException("Unsupported type of comment list element");
			} // if-else
			return wrap;
		}
	}

	private BaringoClient client = null;

}
