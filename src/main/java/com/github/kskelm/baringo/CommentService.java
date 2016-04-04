/**
 * Imgur Comment API services
 * See <a href="http://api.imgur.com/endpoints/comment">Imgur Comments</a> for API details
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.kskelm.baringo.model.BasicResponse;
import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import retrofit.Call;
import retrofit.Response;


/**
 * @author kskelm
 *
 */
public class CommentService {
	
	/**
	 * Given a comment id, return the Comment object for it.
	 * ACCESS: ANONYMOUS
	 * @param commentId - the id of the comment to fetch
	 * @return a Comment object
	 * @throws BaringoApiException - daaang
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
	 * ACCESS: AUTHENTICATED USER
	 * @param imageOrAlbumId - id of the thing to attach the comment to
	 * @param text - text body of the comment
	 * @return the id of the new comment
	 * @throws BaringoApiException - danger
	 */
	public long addComment(
			String imageOrAlbumId,
			String text ) throws BaringoApiException {

		Call<BasicResponse<Map<String,Long>>> call =
				client.getApi().addComment( imageOrAlbumId, text );

		try {
			Response<BasicResponse<Map<String,Long>>> res = call.execute();
			BasicResponse<Map<String,Long>> out = res.body();
			client.throwOnBasicWrapperError( res );

			return out.getData().get( "id" );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	
	/**
	 * Deletes a comment.
	 * ACCESS: AUTHENTICATED USER
	 * @param comment the comment object to delete from Imgur
	 * @return whether it worked
	 * @throws BaringoApiException - bummer
	 */
	public boolean deleteComment(
			Comment comment ) throws BaringoApiException {
		return deleteComment( comment.getId() );
	}


	/**
	 * Deletes a comment.
	 * ACCESS: AUTHENTICATED USER
	 * @param commentId the comment object to delete from Imgur
	 * @return whether it worked
	 * @throws BaringoApiException - bummer
	 */
	public boolean deleteComment(
			long commentId ) throws BaringoApiException {

		Call<BasicResponse<Boolean>> call =
				client.getApi().deleteComment( commentId );

		try {
			Response<BasicResponse<Boolean>> res = call.execute();
			BasicResponse<Boolean> out = res.body();
			client.throwOnBasicWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	}

	/**
	 * Return the list of reply comments for a given comment
	 * ACCESS: ANONYMOUS
	 * @param commentId - the id of the parent comment
	 * @return a list of comment objects, non-paged
	 * @throws BaringoApiException - oh no
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
	 * @param parent - the parent comment
	 * @param text - the text body of the comment to add
	 * @return the id of the new comment
	 * @throws BaringoApiException - i can't keep making up dummy explanations here
	 */
	public long addReply(
			Comment parent,
			String text ) throws BaringoApiException {

		Call<BasicResponse<Map<String,Long>>> call =
				client.getApi().replyComment( parent.getImageId(), parent.getId(), text );

		try {
			Response<BasicResponse<Map<String,Long>>> res = call.execute();
			BasicResponse<Map<String,Long>> out = res.body();
			client.throwOnBasicWrapperError( res );
			return out.getData().get( "id" );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	}

	/**
	 * Sets the vote on the comment for the currently-authenticated
	 * user.  For some reason this doesn't behave like the user interface;
	 * the best you can do is nullify your previous vote, not undo it;
	 * if your previous vote was "up" then downvoting it turns it to null.
	 * If you downvote again, nothing happens, whereas in the web UI,
	 * downvoting turns the result to an actual "down" the nex time you
	 * load the comment.  Odd.
	 * ACCESS: AUTHENTICATED USER
	 * @param commentId - id of the comment to vote on
	 * @param vote - vote up or down
	 * @return true if it worked
	 * @throws BaringoApiException - imgur didn't like that
	 */
	public boolean setVote(
			long commentId,
			Comment.Vote vote) throws BaringoApiException {

		Call<BasicResponse<Boolean>> call =
				client.getApi().voteComment( commentId, vote );

		try {
			Response<BasicResponse<Boolean>> res = call.execute();
			BasicResponse<Boolean> out = res.body();
			client.throwOnBasicWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Report the given comment as abusive or whatever
	 * @param commentId - the id of the comment to report
	 * @param reason - supply a reason
	 * @throws BaringoApiException - ouch
	 */
	public void report(
			long commentId,
			Comment.ReportReason reason ) throws BaringoApiException {

		Call<BasicResponse<Object>> call =
				client.getApi().reportComment( commentId, reason );

		try {
			Response<BasicResponse<Object>> res = call.execute();
			client.throwOnBasicWrapperError( res );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	// ===================================================

	protected CommentService(BaringoClient imgurClient, GsonBuilder gsonBuilder) {
		this.client = imgurClient;
		gsonBuilder.registerTypeAdapter( CommentListWrapper.class, new CommentListWrapper() );
	}

	// Imgur's Json serializer-- like many-- doesn't know when
	// it should be generating an array if it only finds one item
	// in a list so instead of an array with one item, it just
	// generates the item as an object, and parsers the whole
	// world over break.  This is ridiculous
	// and it's been the source of so many unexpected crashes
	// in enterprise applications that I cannot count them all.
	// So here we need to resort to crappy tricks.

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

} // class CommentService
