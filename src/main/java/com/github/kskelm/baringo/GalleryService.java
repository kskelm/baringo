/**
 * Imgur Gallery service {@link https://api.imgur.com/endpoints/gallery}
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.kskelm.baringo.model.BasicResponse;
import com.github.kskelm.baringo.model.Comment;
//import com.github.kskelm.baringo.model.GalleryMemeAlbum;
//import com.github.kskelm.baringo.model.GalleryMemeImage;
//import com.github.kskelm.baringo.model.GallerySubredditAlbum;
//import com.github.kskelm.baringo.model.GallerySubredditImage;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.model.ReportReason;
import com.github.kskelm.baringo.model.TagGallery;
import com.github.kskelm.baringo.model.TagVote;
import com.github.kskelm.baringo.model.TagVoteList;
import com.github.kskelm.baringo.model.Vote;
import com.github.kskelm.baringo.model.Votes;
import com.github.kskelm.baringo.model.gallery.GalleryAlbum;
import com.github.kskelm.baringo.model.gallery.GalleryImage;
import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.model.gallery.GalleryItemProxy;
import com.github.kskelm.baringo.model.search.CompoundSearchQuery;
import com.github.kskelm.baringo.model.search.SearchQuery;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import retrofit.Call;
import retrofit.Response;


/**
 * @author kskelm
 * Imgur Gallery service {@link https://api.imgur.com/endpoints/gallery}
 */
public class GalleryService {

	/**
	 * This returns a list of items in a gallery.
	 * <p>
	 * It returns a list of abstract class GalleryItem,
	 * each of which may actually be a GalleryAlbum or a
	 * GalleryImage.  Imgur API 3 is a little unfriendly to
	 * statically typed languages.
	 * ACCESS: ANONYMOUS
	 * @param string - the section of the gallery.  May
	 * be one of {@link Image.Section}
	 * @param sort - the sort for the results.  May be
	 * one {@link Image.Sort}
	 * @param window - the window of time when the section 
	 * @param page - the page number to return, starting at 0
	 * @return A list of GalleryItem objects
	 * @throws BaringoApiException - something went sideways
	 */
	public List<GalleryItem> listGallery(
			GalleryItem.Section section,
			GalleryItem.Sort sort,
			GalleryItem.Window window,
			boolean viral,
			int page ) throws BaringoApiException {

		String sectionStr = section.name().toLowerCase();
		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();

		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi().listGallery( sectionStr, sortStr, windowStr, page, viral );

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			return convertToGalleryItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Exactly like {@see #getGallery} except this returns
	 * items from the meme gallery.  <strike>Items will include the
	 * text on them via their getMemeMetaData() methods,
	 * and they will be the extended classes GalleryMemeImage
	 * and GalleryMemeAlbum.</strike>
	 * ACCESS: ANONYMOUS
	 * @param sort - the GalleryItem.Sort direction
	 * @param window - the GalleryItem.Window time range
	 * @param page - the page number to return, starting at 0
	 * @return a list of gallery items
	 * @throws BaringoApiException - something bad
	 */
	public List<GalleryItem> listMemeGallery(
			GalleryItem.Sort sort,
			GalleryItem.Window window,
			int page ) throws BaringoApiException {

		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();


		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi()
				.listMemeGallery( sortStr, windowStr, page);

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			return convertToGalleryItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	} // listMemeGallery

	/**
	 * Exactly like {@see #getGallery} except this returns
	 * items from the meme gallery.  <strike>Items will include the
	 * a link to the reddit url as a reference
	 * and they will be the extended classes GalleryMemeImage
	 * and GalleryMemeAlbum.</strike>
	 * ACCESS: ANONYMOUS
	 * @param subreddit - the name of the subreddit in question
	 * @param sort - the GalleryItem.Sort direction
	 * @param window - the GalleryItem.Window time range
	 * @param page - the page number to return, starting at 0
	 * @return a list of gallery items
	 * @throws BaringoApiException - something bad
	 */
	public List<GalleryItem> listSubredditGallery(
			String subreddit,
			GalleryItem.Sort sort,
			GalleryItem.Window window,
			int page ) throws BaringoApiException {

		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();

		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi()
				.listSubredditGallery( subreddit, sortStr, windowStr, page);

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			return convertToGalleryItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	// apparently this isn't supported anymore
	//	/**
	//	 * Returns info about an image in a meme gallery
	//	 * ACCESS: ANONYMOUS
	//	 * @param id - id of the image to return
	//	 * @return the GalleryMemeImage
	//	 * @throws BaringoApiException - something bad
	//	 */
	//	public GalleryMemeImage getMemeImageInfo( String id ) throws BaringoApiException {
	//		
	//		Call<ImgurResponseWrapper<GalleryMemeImage>> call =
	//				client.getApi().getMemeImageInfo( id );
	//
	//		try {
	//			Response<ImgurResponseWrapper<GalleryMemeImage>> res = call.execute();
	//			ImgurResponseWrapper<GalleryMemeImage> out = res.body();
	//
	//			client.throwOnWrapperError( res );
	//
	//			return out.getData();
	//		} catch (IOException e) {
	//			throw new BaringoApiException( e.getMessage() );
	//		} // try-catch
	//	}

	/**
	 * Returns info about an image in a subreddit gallery
	 * ACCESS: ANONYMOUS
	 * @param subreddit - the subreddit the image is in
	 * @param id - id of the image to return
	 * @return the GalleryImage
	 * @throws BaringoApiException - something bad
	 */
	public GalleryImage getSubredditImageInfo(
			String subreddit,
			String id ) throws BaringoApiException {

		Call<ImgurResponseWrapper<GalleryImage>> call =
				client.getApi().getSubredditImageInfo( subreddit, id );

		try {
			Response<ImgurResponseWrapper<GalleryImage>> res = call.execute();
			ImgurResponseWrapper<GalleryImage> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Returns a TagGallery object representing gallery
	 * information for the results of a specific tag.
	 * ACCESS: ANONYMOUS
	 * @throws BaringoApiException - something bad
	 */
	public TagGallery getTagGallery(
			String tag,
			GalleryItem.Sort sort,
			GalleryItem.Window window,
			int page ) throws BaringoApiException {

		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();

		Call<ImgurResponseWrapper<TagGallery>> call =
				client.getApi()
				.getTagGallery( tag, sortStr, windowStr, page );

		try {
			Response<ImgurResponseWrapper<TagGallery>> res = call.execute();
			ImgurResponseWrapper<TagGallery> out = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			TagGallery gal = out.getData();
			gal.setConvertedItems( convertToGalleryItems( gal.getInternalItems() ));
			return gal;
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Given an image id, return info about the Image object for it.
	 * This just points to ImageService.getImageInfo()
	 * ACCESS: ANONYMOUS
	 * @param id - the id of the image, for example "PgZtz0j".
	 * If a user is logged in and this image is theirs, the
	 * deleteHash property will be filled in.  It will otherwise
	 * be null
	 * @return Image object
	 * @throws BaringoApiException - something went pear-shaped
	 */
	public Image getImageInfo( String id ) throws BaringoApiException {
		return client.imageService().getImageInfo( id );
	}

	/**
	 * Given a gallery item id, return the tag votes associated with it
	 * ACCESS: ANONYMOUS
	 * @param id - the image/album id to return tag votes for
	 * @return a list of TagVotes
	 * @throws BaringoApiException - well bummer
	 */
	public List<TagVote> getGalleryItemTagVotes( String id ) throws BaringoApiException {
		Call<ImgurResponseWrapper<TagVoteList>> call =
				client.getApi().getGalleryItemTagVotes( id );

		try {
			// instead of returning a list, returns standard wrapper
			// object except the data is an object with only one item,
			// "tags".  So we have to write a special object just for that.
			Response<ImgurResponseWrapper<TagVoteList>> res = call.execute();
			ImgurResponseWrapper<TagVoteList> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData().getList();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	}

	/**
	 * Given an item id and a tag name,
	 * vote it either up or down.
	 * ACCESS: AUTHENTICATED USER
	 * @param id - the id of the item to vote on
	 * @param tag - the tag to vote up or down
	 * @param vote - the vote, Up or Down
	 * @return true if it worked
	 * @throws BaringoApiException - nope
	 */
	public Boolean voteGalleryItemTag(
			String id,
			String tag,
			Vote vote ) throws BaringoApiException {

		String voteStr = vote.name().toLowerCase();

		Call<ImgurResponseWrapper<Boolean>> call =
				client.getApi().voteGalleryItemTag( id, tag, voteStr );

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
	 * Performs a gallery search, returning GalleryItems
	 * that fits the SearchQuery criteria.
	 * ACCESS: AUTHENTICATED USER
	 * @param query - the search query to perform
	 * @param sort - the method of sorting
	 * @param window - the time range to return
	 * @param page - the page number to return, starting at 0
	 * @return a list of GalleryItem objects
	 * @throws BaringoApiException - badness
	 */
	public List<GalleryItem> searchGallery(
			SearchQuery query,
			GalleryItem.Sort sort,
			GalleryItem.Window window,
			int page ) throws BaringoApiException {

		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();
		SearchQuery.ItemType type = query.getItemType();
		if( type == SearchQuery.ItemType.any ) {
			type = null;
		} // if
		String typeStr = type == null ? null : type.name().toLowerCase();
		SearchQuery.SizeRange range = query.getSizeRange();
		if( range == SearchQuery.SizeRange.any ) {
			range = null;
		} // if
		String rangeStr = range == null ? null : range.name().toLowerCase();

		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi()
				.searchGallery(
						sortStr, windowStr, page,
						query.getAllWords(),
						query.getAnyWords(),
						query.getThisPhrase(),
						query.getNotPhrase(),
						typeStr,
						rangeStr );

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			return convertToGalleryItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	
	/**
	 * Performs a compound gallery search, returning GalleryItems
	 * that fits the {@see CompoundSearchQuery} criteria.
	 * ACCESS: AUTHENTICATED USER
	 * @param query - the search query to perform
	 * @param sort - the method of sorting
	 * @param window - the time range to return
	 * @param page - the page number to return, starting at 0
	 * @return a list of GalleryItem objects
	 * @throws BaringoApiException - badness
	 */
	public List<GalleryItem> searchGallery(
			CompoundSearchQuery query,
			GalleryItem.Sort sort,
			GalleryItem.Window window,
			int page ) throws BaringoApiException {

		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();

		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi()
				.compoundSearchGallery(
						sortStr, windowStr, page,
						query.toString() );

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			return convertToGalleryItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	
	/**
	 * This returns a list of random gallery items.  Imgur
	 * refreshes this random list once per hour.
	 * ACCESS: ANONYMOUS
	 * @param page - the page number to return, starting at 0
	 * @return A list of GalleryItem objects
	 * @throws BaringoApiException - something went sideways
	 */
	public List<GalleryItem> getRandomGallery( int page ) throws BaringoApiException {

		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi().listRandomGallery( page);

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			return convertToGalleryItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 

	}

	/**
	 * Shares the given item (image or album) with the gallery
	 * community.
	 * <strong>NOTE: YOU CAN SHARE AN ITEM AT MOST ONCE EVERY 15
	 * MINUTES FROM THE SAME USER!</strong>
	 * ACCESS: AUTHENTICATED USER
	 * @param itemId - id of the item to share
	 * @param title - title of the post
	 * @param topic - topic to post in, e.g. "funny" or "wtf"
	 * @param agreedToTerms - whether or not the user agreed
	 * to the terms.  Apparently Imgur doesn't mind if you lie.
	 * @param nsfw - True if this item should be marked as "mature"
	 * @return true if it worked
	 * @throws BaringoApiException - it didn't
	 */
	public boolean shareItem(
			String itemId,
			String title,
			int topicId,
			boolean agreedToTerms,
			boolean nsfw ) throws BaringoApiException {
		Call<BasicResponse<Boolean>> call =
				client.getApi().shareGalleryItem(
						itemId, title, topicId,
						agreedToTerms ? 1 : 0,
								nsfw ? 1 : 0 );

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
	 * Removes the given item (image or album) from the public
	 * gallery.  The currently-logged-in user must be the owner
	 * of the item.
	 * ACCESS: AUTHENTICATED USER
	 * @param itemId - the id of the item to remove
	 * @return true if it worked
	 * @throws BaringoApiException - it didn't
	 */
	public boolean unshareItem( String itemId ) throws BaringoApiException {
		Call<BasicResponse<Boolean>> call =
				client.getApi().unshareGalleryItem( itemId );

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
	 * Report a gallery image or album as bad content.
	 * ACCESS: AUTHENTICATED USER
	 * @param itemId - the item id to report
	 * @param reason - why
	 * @return true if it worked
	 * @throws BaringoApiException - it didn't work
	 */
	public boolean reportItem(
			String itemId,
			ReportReason reason ) throws BaringoApiException {

		int reasonNum = reason.ordinal() + 1;
		Call<BasicResponse<Boolean>> call =
				client.getApi().reportGalleryItem( itemId, reasonNum );

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
	 * Returns the upvote/downvote counts for an image or album
	 * ACCESS: ANONYMOUS
	 * @param itemId - the item to get votes for
	 * @return its vote counts
	 * @throws BaringoApiException - Imgur voted us off the island I guess
	 */
	public Votes getItemVotes( String itemId ) throws BaringoApiException {

		Call<ImgurResponseWrapper<Votes>> call =
				client.getApi().getGalleryItemVotes( itemId );

		try {
			Response<ImgurResponseWrapper<Votes>> res = call.execute();
			ImgurResponseWrapper<Votes> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Returns the list of comments on a gallery image or album
	 * ACCESS: ANONYMOUS
	 * @param itemId - the id of the item to get comments for
	 * @param sort - @{See GalleryImage#Sort}
	 * @return a list of comments
	 * @throws BaringoApiException - something went south
	 */
	public List<Comment> getItemComments(
			String itemId,
			GalleryItem.Sort sort ) throws BaringoApiException {

		String sortStr = sort.name().toLowerCase();
		Call<ImgurResponseWrapper<List<Comment>>> call =
				client.getApi().getGalleryItemComments( itemId, sortStr );

		try {
			Response<ImgurResponseWrapper<List<Comment>>> res = call.execute();
			ImgurResponseWrapper<List<Comment>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Returns the list of comment Ids on a gallery image or album
	 * ACCESS: ANONYMOUS
	 * @param itemId - the id of the item to get comment ids for
	 * @return a list of longs of the comment Ids
	 * @throws BaringoApiException - something went south
	 */
	public List<Long> getItemCommentIds(
			String itemId) throws BaringoApiException {

		Call<ImgurResponseWrapper<List<Long>>> call =
				client.getApi().getGalleryItemCommentIds( itemId );

		try {
			Response<ImgurResponseWrapper<List<Long>>> res = call.execute();
			ImgurResponseWrapper<List<Long>> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}

	/**
	 * Returns the total number of comments for a gallery image or album.
	 * ACCESS: ANONYMOUS
	 * @param itemId - the id of the item to get comment ids for
	 * @return the total number of comments
	 * @throws BaringoApiException - something went south
	 */
	public int getItemCommentCount(
			String itemId) throws BaringoApiException {

		Call<ImgurResponseWrapper<Integer>> call =
				client.getApi().getGalleryItemCommentCount( itemId );

		try {
			Response<ImgurResponseWrapper<Integer>> res = call.execute();
			ImgurResponseWrapper<Integer> out = res.body();

			client.throwOnWrapperError( res );

			return out.getData();
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} 
	}


	// ================================================

	// this approach feels filthy. Convert lame proxy objects to
	// type-safe GalleryItem derivatives.  
	protected List<GalleryItem> convertToGalleryItems( List<GalleryItemProxy> list ) {
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
	} 

	protected GalleryItem convertProxyItem( GalleryItemProxy proxy ) {
		GalleryItem item = null;
		if( proxy.isAlbum() ) {
			item = new GalleryAlbum( proxy );
		} else {
			item = new GalleryImage( proxy );
		} // if-else
		return item;
	}

	// ditto
	// UPDATE: apparently Imgur doesn't really support this anymore at least
	// as of 6/24/15.  https://groups.google.com/forum/#!msg/imgur/BEyZryAhGi0/yfOFyixuPy4J
	//	protected List<GalleryItem> convertToMemeGalleryItems( List<GalleryItemProxy> list ) {
	//		ArrayList<GalleryItem> items = new ArrayList<>();
	//
	//		for( GalleryItemProxy proxy : list ) {
	//			GalleryItem item = null;
	//			if( proxy.isAlbum() ) {
	//				item = new GalleryMemeAlbum( proxy );
	//			} else {
	//				item = new GalleryMemeImage( proxy );
	//			} // if-else
	//			if( item != null ) {
	//				items.add( item );
	//			} // if
	//		} // for
	//		return items;
	//	}

	// double ditto
	//	// UPDATE: apparently Imgur doesn't really support this anymore
	//	protected List<GalleryItem> convertToSubredditGalleryItems( List<GalleryItemProxy> list ) {
	//		ArrayList<GalleryItem> items = new ArrayList<>();
	//
	//		for( GalleryItemProxy proxy : list ) {
	//			GalleryItem item = null;
	//			if( proxy.isAlbum() ) {
	//				item = new GallerySubredditAlbum( proxy );
	//			} else {
	//				item = new GallerySubredditImage( proxy );
	//			} // if-else
	//			if( item != null ) {
	//				items.add( item );
	//			} // if
	//		} // for
	//		return items;
	//	} 


	/*
	 * Because Imgur gallery lists come in with both GalleryImages and
	 * GalleryAlbums in the same GalleryItemList, and because the only
	 * distinguishing thing is is_album==true, AND because we like type
	 * safety and proper subclassing, we need an Adapter to read
	 * Gallery lists and convert them on the fly from being GalleryItemProxies
	 * to GalleryImages and GalleryAlbums.
	 */
/* WIP .. nice idea but not yet
	class GalleryItemAdapter extends TypeAdapter<GalleryItem> {

		@Override
		public void write(JsonWriter out, GalleryItem value) throws IOException {
			if( value == null ){
				out.nullValue();
				return;
			} // if

			out.value( value.toString() );
		}

		// We used to load an entire array of GalleryItemProxy objects
		// and then wholesale convert them.  That was probably twice the
		// heap pressure as doing it piecemeal as they come in.
		@Override
		public GalleryItem read(JsonReader in) throws IOException {
			if (in.peek() != JsonToken.BEGIN_OBJECT) {
				in.nextNull();
				return null;
			} // if-else
			// read in a temporary proxy placeholder
			GalleryItemProxy proxy = new Gson().fromJson( in, GalleryItemProxy.class );
			if( proxy == null ) {
				return null;
			} // if
			// turn it into the appropriate element and return
			if( proxy.isAlbum() ) {
				return new GalleryAlbum( proxy );
			} else {
				return new GalleryImage( proxy );
			} // if-else
		}
	}
*/

	protected GalleryService( BaringoClient client, GsonBuilder gsonBuilder ) {
		this.client = client;
//		gsonBuilder.registerTypeAdapter( GalleryItem.class, new GalleryItemAdapter() );
	} // constructor

	private BaringoClient client = null;

}
